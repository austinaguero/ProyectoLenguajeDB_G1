-- Vistas de facturación e inventario

CREATE OR REPLACE VIEW vw_detalle_facturas AS
SELECT
    f.id_factura,
    f.numero_factura,
    f.fecha_emision,
    c.nombre_cliente,
    p.nombre_producto,
    d.cantidad,
    d.precio_unitario,
    d.subtotal_linea,
    f.subtotal,
    f.impuesto,
    f.monto_total,
    f.estado
FROM facturas f
INNER JOIN clientes c ON f.id_cliente = c.id_cliente
INNER JOIN detalle_factura d ON f.id_factura = d.id_factura
INNER JOIN productos p ON d.id_producto = p.id_producto;
/

CREATE OR REPLACE VIEW vw_productos_bajo_minimo AS
SELECT
    p.id_producto,
    p.codigo_producto,
    p.nombre_producto,
    c.nombre_categoria,
    pr.nombre_proveedor,
    p.stock_actual,
    p.stock_minimo,
    (p.stock_minimo - p.stock_actual) AS unidades_faltantes
FROM productos p
INNER JOIN categorias c ON p.id_categoria = c.id_categoria
INNER JOIN proveedores pr ON p.id_proveedor = pr.id_proveedor
WHERE p.stock_actual <= p.stock_minimo;
/
