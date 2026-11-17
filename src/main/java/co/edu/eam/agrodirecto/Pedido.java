package co.edu.eam.agrodirecto;

/**
 * Pedido: los datos de una orden de compra que un comprador hace en el marketplace.
 * El estado se inicializa en PENDIENTE porque todo pedido nace registrado pero sin pagar.
 */
public class Pedido {

    private String compradorId;
    private String producto;
    private double cantidad;
    private long timestamp; // System.currentTimeMillis() al momento de crear el pedido
    private EstadoPedido estado; // en qué punto del ciclo de vida está el pedido

    /**
     * Constructor: deja el pedido en PENDIENTE, el estado de todo pedido recién creado.
     * Se conserva con los mismos cuatro parámetros para no romper las pruebas de la entrega anterior (doc/04).
     */
    public Pedido(String compradorId, String producto, double cantidad, long timestamp) {
        this.compradorId = compradorId;
        this.producto = producto;
        this.cantidad = cantidad;
        this.timestamp = timestamp;
        this.estado = EstadoPedido.PENDIENTE;
    }

    public String getCompradorId() {
        return compradorId;
    }

    public String getProducto() {
        return producto;
    }

    public double getCantidad() {
        return cantidad;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }
}
