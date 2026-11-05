-- Funciones de cálculo de facturación

CREATE OR REPLACE FUNCTION fn_calcular_iva(p_subtotal IN NUMBER)
RETURN NUMBER
IS
    v_iva NUMBER(12,2);
BEGIN
    IF p_subtotal IS NULL OR p_subtotal < 0 THEN
        RAISE_APPLICATION_ERROR(-20003,
            'El subtotal no puede ser negativo ni nulo.');
    END IF;
    v_iva := ROUND(p_subtotal * 0.13, 2);
    RETURN v_iva;
END;
/

CREATE OR REPLACE FUNCTION fn_calcular_total_factura(p_subtotal IN NUMBER)
RETURN NUMBER
IS
    v_iva NUMBER(12,2);
    v_total NUMBER(12,2);
BEGIN
    IF p_subtotal IS NULL OR p_subtotal < 0 THEN
        RAISE_APPLICATION_ERROR(-20004,
            'El subtotal no puede ser negativo ni nulo.');
    END IF;
    v_iva := fn_calcular_iva(p_subtotal);
    v_total := p_subtotal + v_iva;
    RETURN v_total;
END;
/
