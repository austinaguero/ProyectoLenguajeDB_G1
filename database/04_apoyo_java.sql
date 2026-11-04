-- Apoyo para integrar Java con los procedimientos del grupo y la lógica de negocio.
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

-- Actualiza el encabezado sin permitir cambios directos de estado ni montos manuales.
CREATE OR REPLACE PROCEDURE sp_actualizar_factura (
    p_id_factura IN NUMBER,
    p_numero_factura IN VARCHAR2,
    p_id_cliente IN NUMBER,
    p_id_usuario IN NUMBER,
    p_id_metodo_pago IN NUMBER
) AS
    v_estado facturas.estado%TYPE;
    v_subtotal facturas.subtotal%TYPE;
BEGIN
    SELECT estado INTO v_estado
    FROM facturas
    WHERE id_factura = p_id_factura
    FOR UPDATE;

    IF v_estado <> 'EMITIDA' THEN
        RAISE_APPLICATION_ERROR(-20020, 'No se puede modificar una factura anulada.');
    END IF;

    SELECT SUM(subtotal_linea) INTO v_subtotal
    FROM detalle_factura
    WHERE id_factura = p_id_factura;

    IF v_subtotal IS NULL THEN
        v_subtotal := 0;
    END IF;

    UPDATE facturas
    SET numero_factura = p_numero_factura,
        id_cliente = p_id_cliente,
        id_usuario = p_id_usuario,
        id_metodo_pago = p_id_metodo_pago,
        subtotal = v_subtotal,
        impuesto = fn_calcular_iva(v_subtotal),
        monto_total = fn_calcular_total_factura(v_subtotal)
    WHERE id_factura = p_id_factura;
END;
/

-- Una llamada desde Java registra la línea y actualiza los montos.
-- El trigger de Jefferson valida y descuenta stock al insertar el detalle.
CREATE OR REPLACE PROCEDURE sp_agregar_detalle_factura (
    p_id_factura IN NUMBER,
    p_id_producto IN NUMBER,
    p_cantidad IN NUMBER
) AS
    v_estado facturas.estado%TYPE;
    v_precio productos.precio_venta%TYPE;
    v_subtotal facturas.subtotal%TYPE;
BEGIN
    IF p_cantidad IS NULL OR p_cantidad <= 0 THEN
        RAISE_APPLICATION_ERROR(-20021, 'La cantidad debe ser mayor que cero.');
    END IF;

    SELECT estado INTO v_estado
    FROM facturas
    WHERE id_factura = p_id_factura
    FOR UPDATE;

    IF v_estado <> 'EMITIDA' THEN
        RAISE_APPLICATION_ERROR(-20022, 'No se pueden agregar productos a una factura anulada.');
    END IF;

    SELECT precio_venta INTO v_precio
    FROM productos
    WHERE id_producto = p_id_producto;

    INSERT INTO detalle_factura
        (id_factura, id_producto, cantidad, precio_unitario, subtotal_linea)
    VALUES
        (p_id_factura, p_id_producto, p_cantidad, v_precio, p_cantidad * v_precio);

    SELECT SUM(subtotal_linea) INTO v_subtotal
    FROM detalle_factura
    WHERE id_factura = p_id_factura;

    UPDATE facturas
    SET subtotal = v_subtotal,
        impuesto = fn_calcular_iva(v_subtotal),
        monto_total = fn_calcular_total_factura(v_subtotal)
    WHERE id_factura = p_id_factura;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE;
END;
/

CREATE OR REPLACE PROCEDURE sp_consultar_detalle_facturas (
    p_cursor OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_cursor FOR SELECT * FROM vw_detalle_facturas;
END;
/

CREATE OR REPLACE PROCEDURE sp_consultar_productos_bajo_minimo (
    p_cursor OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_cursor FOR SELECT * FROM vw_productos_bajo_minimo;
END;
/
