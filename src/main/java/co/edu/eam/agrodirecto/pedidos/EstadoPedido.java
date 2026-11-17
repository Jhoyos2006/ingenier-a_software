package co.edu.eam.agrodirecto.pedidos;

/**
 * EstadoPedido: los puntos por los que pasa un pedido desde que se registra hasta que se entrega.
 * El orden de las constantes es el del ciclo de vida, y PENDIENTE es el estado inicial de todo pedido.
 */
public enum EstadoPedido {

    PENDIENTE,   // el comprador hizo el pedido pero todavía no lo paga
    PAGADO,      // el pago se confirmó y el pedido espera ser despachado
    ENVIADO,     // el pedido salió del almacén y va en camino
    ENTREGADO,   // el comprador recibió el pedido: el ciclo termina aquí
    CANCELADO    // el pedido se anuló y no puede volver a avanzar
}
