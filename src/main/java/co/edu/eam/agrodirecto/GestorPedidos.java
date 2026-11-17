package co.edu.eam.agrodirecto;

import java.util.List;

/**
 * GestorPedidos: las reglas de negocio del módulo de pedidos del marketplace.
 * Aquí viven las validaciones, los cálculos de totales y de envío, las transiciones de estado
 * y la consulta del historial del comprador.
 */
public class GestorPedidos {

    private static final long VENTANA_MS = 2 * 60 * 1000; // 2 minutos en milisegundos

    /**
     * Detecta si un pedido ya existe en el historial reciente del mismo comprador.
     * Existe porque el riesgo R8 (pedidos duplicados) es de nivel alto en la matriz de riesgos
     * y la acción preventiva del plan de contingencia es probar el módulo de pedidos antes de publicar.
     */
    public boolean esDuplicado(List<Pedido> recientes, Pedido nuevo) {
        for (Pedido p : recientes) {
            boolean mismoComprador = p.getCompradorId().equals(nuevo.getCompradorId());
            boolean mismoProducto  = p.getProducto().equals(nuevo.getProducto());
            boolean mismaCantidad  = p.getCantidad() == nuevo.getCantidad();
            boolean dentroVentana  = (nuevo.getTimestamp() - p.getTimestamp()) < VENTANA_MS;

            if (mismoComprador && mismoProducto && mismaCantidad && dentroVentana) {
                return true;
            }
        }
        return false;
    }

    /**
     * Dice si los datos de un pedido sirven para registrarlo: producto, cantidad, comprador y fecha.
     * Se eligió devolver false y no lanzar excepción porque un pedido con datos raros es un negocio
     * inválido y no un error de programación, así que el módulo debe seguir trabajando con ese dato.
     */
    public boolean esValido(Pedido pedido) {
        if (pedido == null) {
            return false;
        }
        boolean productoOk = pedido.getProducto() != null && !pedido.getProducto().isBlank();
        boolean compradorOk = pedido.getCompradorId() != null && !pedido.getCompradorId().isBlank();

        return productoOk && compradorOk && pedido.getCantidad() > 0 && pedido.getTimestamp() > 0;
    }

    /**
     * Calcula el total a pagar multiplicando la cantidad pedida por el precio unitario del producto.
     * Se eligió un solo punto de cálculo porque el precio vive en el catálogo y la cantidad en el
     * pedido, y unirlos aquí evita que cada pantalla del marketplace calcule el total por su cuenta.
     */
    public double calcularTotal(Pedido pedido, double precioUnitario) {
        if (precioUnitario <= 0) {
            throw new IllegalArgumentException("El precio unitario debe ser mayor que cero");
        }
        return pedido.getCantidad() * precioUnitario;
    }

    /**
     * Devuelve el total del pedido después de aplicarle un porcentaje de descuento.
     * Se eligió apoyarse en calcularTotal para que el total descontado nunca se desvíe del total real,
     * y se lanza excepción si el porcentaje no está entre 0 y 100 porque ese rango es una invariante.
     */
    public double aplicarDescuento(Pedido pedido, double precioUnitario, double porcentaje) {
        if (porcentaje < 0 || porcentaje > 100) {
            throw new IllegalArgumentException("El porcentaje de descuento debe estar entre 0 y 100");
        }
        double total = calcularTotal(pedido, precioUnitario);
        return total * (1 - porcentaje / 100);
    }

    /**
     * Calcula el costo de despacho sumando una base fija más el costo por kilo que pesa el pedido.
     * Se eligió no validar la base ni el costo por kilo porque son valores de configuración del
     * sistema y no datos del pedido: el módulo responde con la fórmula, la configuración por sus números.
     */
    public double calcularCostoEnvio(Pedido pedido, double costoBase, double costoPorKilo) {
        return costoBase + pedido.getCantidad() * costoPorKilo;
    }

    /**
     * Consulta la tabla de transiciones y dice si un pedido puede pasar de un estado a otro.
     * Se eligió una tabla explícita que niega por omisión porque así ninguna transición no pensada
     * se activa por error, y es el único lugar donde vive la regla del ciclo de vida del pedido.
     */
    public boolean puedePasarA(EstadoPedido actual, EstadoPedido destino) {
        if (actual == null || destino == null) {
            return false;
        }
        return switch (actual) {
            case PENDIENTE -> destino == EstadoPedido.PAGADO || destino == EstadoPedido.CANCELADO;
            case PAGADO    -> destino == EstadoPedido.ENVIADO || destino == EstadoPedido.CANCELADO;
            case ENVIADO   -> destino == EstadoPedido.ENTREGADO;
            case ENTREGADO, CANCELADO -> false;
        };
    }

    /**
     * Cambia el estado del pedido y devuelve el nuevo estado, siempre que la transición esté permitida.
     * Se eligió apoyarse en puedePasarA para no duplicar la tabla de transiciones en dos métodos
     * y para que toda transición inválida termine en el mismo error: el de invariante rota.
     */
    public EstadoPedido cambiarEstado(Pedido pedido, EstadoPedido destino) {
        if (pedido == null) {
            throw new IllegalArgumentException("El pedido no puede ser nulo");
        }
        if (!puedePasarA(pedido.getEstado(), destino)) {
            throw new IllegalArgumentException(
                    "Transición de estado no permitida: " + pedido.getEstado() + " -> " + destino);
        }
        pedido.setEstado(destino);
        return destino;
    }

    /**
     * Suma los kilos que un comprador ha pedido en el historial que recibe.
     * Se eligió devolver 0.0 cuando no hay pedidos en vez de lanzar excepción porque un historial
     * vacío es un caso normal al abrir la pantalla del comprador y no un error de programación.
     */
    public double totalKgDe(List<Pedido> pedidos, String compradorId) {
        if (pedidos == null || pedidos.isEmpty()) {
            return 0.0;
        }
        double total = 0.0;
        for (Pedido pedido : pedidos) {
            if (pedido.getCompradorId().equals(compradorId)) {
                total += pedido.getCantidad();
            }
        }
        return total;
    }
}
