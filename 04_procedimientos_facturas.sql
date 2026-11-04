
-- Procedimientos de gestión de facturas

-- Anula una factura y devuelve al inventario los productos vendidos.
CREATE OR REPLACE PROCEDURE sp_anular_factura(p_id_factura IN NUMBER) AS
    v_estado facturas.estado%TYPE;
    v_id_usuario facturas.id_usuario%TYPE;
BEGIN
    BEGIN
        SELECT estado, id_usuario
        INTO v_estado, v_id_usuario
        FROM facturas
        WHERE id_factura = p_id_factura
        FOR UPDATE;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RAISE_APPLICATION_ERROR(-20010, 'La factura indicada no existe.');
    END;

    IF v_estado <> 'EMITIDA' THEN
        RAISE_APPLICATION_ERROR(-20011,
            'La factura no está emitida o ya fue anulada.');
    END IF;

    FOR r IN (
        SELECT id_producto, cantidad
        FROM detalle_factura
        WHERE id_factura = p_id_factura
    ) LOOP
        UPDATE productos
        SET stock_actual = stock_actual + r.cantidad
        WHERE id_producto = r.id_producto;

        INSERT INTO movimientos_inventario
            (id_producto, id_usuario, tipo_movimiento, cantidad, motivo)
        VALUES
            (r.id_producto, v_id_usuario, 'ENTRADA', r.cantidad,
             'Devolucion por anulacion de factura ' || p_id_factura);
    END LOOP;

    UPDATE facturas
    SET estado = 'ANULADA'
    WHERE id_factura = p_id_factura;

    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE;
END sp_anular_factura;
/

-- Elimina únicamente facturas que ya fueron anuladas.
CREATE OR REPLACE PROCEDURE sp_eliminar_factura(p_id_factura IN NUMBER) AS
    v_estado facturas.estado%TYPE;
BEGIN
    BEGIN
        SELECT estado
        INTO v_estado
        FROM facturas
        WHERE id_factura = p_id_factura
        FOR UPDATE;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RAISE_APPLICATION_ERROR(-20012, 'La factura indicada no existe.');
    END;

    IF v_estado <> 'ANULADA' THEN
        RAISE_APPLICATION_ERROR(-20013,
            'Solo se pueden eliminar facturas anuladas.');
    END IF;

    DELETE FROM facturas
    WHERE id_factura = p_id_factura;
END sp_eliminar_factura;
/
