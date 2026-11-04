-- Persona 5: apoyo para integrar Java con los procedimientos del grupo.
-- Ejecutar después de las 12 tablas. No elimina tablas ni modifica datos.
-- Las tres consultas originales sp_select_* con DBMS_OUTPUT se conservan.

CREATE OR REPLACE PROCEDURE sp_listar_categorias (
    p_cursor OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_cursor FOR
        SELECT id_categoria, nombre_categoria
        FROM categorias
        ORDER BY id_categoria;
END;
/

CREATE OR REPLACE PROCEDURE sp_listar_proveedores (
    p_cursor OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_cursor FOR
        SELECT id_proveedor, cedula_juridica, nombre_proveedor,
               telefono, correo, direccion
        FROM proveedores
        ORDER BY id_proveedor;
END;
/

CREATE OR REPLACE PROCEDURE sp_listar_productos (
    p_cursor OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_cursor FOR
        SELECT p.id_producto, p.codigo_producto, p.nombre_producto,
               c.nombre_categoria, pr.nombre_proveedor, p.precio_venta,
               p.stock_actual, p.stock_minimo
        FROM productos p
        INNER JOIN categorias c ON p.id_categoria = c.id_categoria
        INNER JOIN proveedores pr ON p.id_proveedor = pr.id_proveedor
        ORDER BY p.id_producto;
END;
/

-- Complementos para el UPDATE y DELETE de encabezados de facturas.
-- Anular (procedimiento de Daniela) y eliminar son operaciones diferentes.
CREATE OR REPLACE PROCEDURE sp_actualizar_factura (
    p_id_factura IN NUMBER,
    p_numero_factura IN VARCHAR2,
    p_id_cliente IN NUMBER,
    p_id_usuario IN NUMBER,
    p_id_metodo_pago IN NUMBER,
    p_subtotal IN NUMBER,
    p_impuesto IN NUMBER,
    p_monto_total IN NUMBER,
    p_estado IN VARCHAR2
) AS
BEGIN
    UPDATE facturas
    SET numero_factura = p_numero_factura,
        id_cliente = p_id_cliente,
        id_usuario = p_id_usuario,
        id_metodo_pago = p_id_metodo_pago,
        subtotal = p_subtotal,
        impuesto = p_impuesto,
        monto_total = p_monto_total,
        estado = p_estado
    WHERE id_factura = p_id_factura;
END;
/

CREATE OR REPLACE PROCEDURE sp_eliminar_factura (
    p_id_factura IN NUMBER
) AS
BEGIN
    DELETE FROM facturas
    WHERE id_factura = p_id_factura;
END;
/
