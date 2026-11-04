-- Trigger de validación de stock

CREATE OR REPLACE TRIGGER trg_validar_stock_factura
AFTER INSERT ON detalle_factura
FOR EACH ROW
DECLARE
    v_stock_actual NUMBER;
    v_id_usuario NUMBER;
    v_estado_factura VARCHAR2(10);
BEGIN
    SELECT id_usuario, estado
    INTO v_id_usuario, v_estado_factura
    FROM facturas
    WHERE id_factura = :NEW.id_factura;

    IF v_estado_factura <> 'EMITIDA' THEN
        RAISE_APPLICATION_ERROR(-20001,
            'No se pueden agregar productos a una factura anulada.');
    END IF;

    SELECT stock_actual
    INTO v_stock_actual
    FROM productos
    WHERE id_producto = :NEW.id_producto
    FOR UPDATE;

    IF v_stock_actual < :NEW.cantidad THEN
        RAISE_APPLICATION_ERROR(-20002,
            'Stock insuficiente para completar la factura.');
    END IF;

    UPDATE productos
    SET stock_actual = stock_actual - :NEW.cantidad
    WHERE id_producto = :NEW.id_producto;

    INSERT INTO movimientos_inventario
        (id_producto, id_usuario, tipo_movimiento, cantidad, motivo)
    VALUES
        (:NEW.id_producto, v_id_usuario, 'SALIDA', :NEW.cantidad,
         'Venta mediante factura ' || :NEW.id_factura);
END;
/
