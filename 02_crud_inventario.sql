SET SERVEROUTPUT ON;

---------------- CATEGORIAS---------------------
-- INSERT
CREATE OR REPLACE PROCEDURE sp_insertar_categoria (
    p_nombre_categoria IN categorias.nombre_categoria%TYPE
)
AS
BEGIN
    INSERT INTO categorias (nombre_categoria)
    VALUES (p_nombre_categoria);
END;
/

-- UPDATE
CREATE OR REPLACE PROCEDURE sp_actualizar_categoria (
    p_id_categoria     IN categorias.id_categoria%TYPE,
    p_nombre_categoria IN categorias.nombre_categoria%TYPE
)
AS
BEGIN
    UPDATE categorias
    SET nombre_categoria = p_nombre_categoria
    WHERE id_categoria = p_id_categoria;
END;
/

-- DELETE
CREATE OR REPLACE PROCEDURE sp_eliminar_categoria (
    p_id_categoria IN categorias.id_categoria%TYPE
)
AS
BEGIN
    DELETE FROM categorias
    WHERE id_categoria = p_id_categoria;
END;
/

-- SELECT
CREATE OR REPLACE PROCEDURE sp_select_categorias
AS
BEGIN
    FOR r IN (
        SELECT id_categoria, nombre_categoria
        FROM categorias
        ORDER BY id_categoria
    )
    LOOP
        DBMS_OUTPUT.PUT_LINE(
            'ID: ' || r.id_categoria ||
            ' | Categoria: ' || r.nombre_categoria
        );
    END LOOP;
END;
/

-------------- PROVEEDORES -----------------
-- INSERT
CREATE OR REPLACE PROCEDURE sp_insertar_proveedor (
    p_cedula_juridica  IN proveedores.cedula_juridica%TYPE,
    p_nombre_proveedor IN proveedores.nombre_proveedor%TYPE,
    p_telefono         IN proveedores.telefono%TYPE,
    p_correo           IN proveedores.correo%TYPE,
    p_direccion        IN proveedores.direccion%TYPE
)
AS
BEGIN
    INSERT INTO proveedores (
        cedula_juridica,
        nombre_proveedor,
        telefono,
        correo,
        direccion
    )
    VALUES (
        p_cedula_juridica,
        p_nombre_proveedor,
        p_telefono,
        p_correo,
        p_direccion
    );
END;
/

-- UPDATE
CREATE OR REPLACE PROCEDURE sp_actualizar_proveedor (
    p_id_proveedor     IN proveedores.id_proveedor%TYPE,
    p_cedula_juridica  IN proveedores.cedula_juridica%TYPE,
    p_nombre_proveedor IN proveedores.nombre_proveedor%TYPE,
    p_telefono         IN proveedores.telefono%TYPE,
    p_correo           IN proveedores.correo%TYPE,
    p_direccion        IN proveedores.direccion%TYPE
)
AS
BEGIN
    UPDATE proveedores
    SET
        cedula_juridica  = p_cedula_juridica,
        nombre_proveedor = p_nombre_proveedor,
        telefono         = p_telefono,
        correo           = p_correo,
        direccion        = p_direccion
    WHERE id_proveedor = p_id_proveedor;
END;
/

-- DELETE
CREATE OR REPLACE PROCEDURE sp_eliminar_proveedor (
    p_id_proveedor IN proveedores.id_proveedor%TYPE
)
AS
BEGIN
    DELETE FROM proveedores
    WHERE id_proveedor = p_id_proveedor;
END;
/

-- SELECT
CREATE OR REPLACE PROCEDURE sp_select_proveedores
AS
BEGIN
    FOR r IN (
        SELECT id_proveedor,
               cedula_juridica,
               nombre_proveedor,
               telefono,
               correo,
               direccion
        FROM proveedores
        ORDER BY id_proveedor
    )
    LOOP
        DBMS_OUTPUT.PUT_LINE(
            'ID: ' || r.id_proveedor ||
            ' | Cedula: ' || r.cedula_juridica ||
            ' | Proveedor: ' || r.nombre_proveedor ||
            ' | Telefono: ' || r.telefono ||
            ' | Correo: ' || r.correo ||
            ' | Direccion: ' || r.direccion
        );
    END LOOP;
END;
/

--------------- PRODUCTOS -----------------
-- INSERT
CREATE OR REPLACE PROCEDURE sp_insertar_producto (
    p_codigo_producto IN productos.codigo_producto%TYPE,
    p_nombre_producto IN productos.nombre_producto%TYPE,
    p_id_categoria    IN productos.id_categoria%TYPE,
    p_id_proveedor    IN productos.id_proveedor%TYPE,
    p_precio_venta    IN productos.precio_venta%TYPE,
    p_stock_actual    IN productos.stock_actual%TYPE,
    p_stock_minimo    IN productos.stock_minimo%TYPE
)
AS
BEGIN
    INSERT INTO productos (
        codigo_producto,
        nombre_producto,
        id_categoria,
        id_proveedor,
        precio_venta,
        stock_actual,
        stock_minimo
    )
    VALUES (
        p_codigo_producto,
        p_nombre_producto,
        p_id_categoria,
        p_id_proveedor,
        p_precio_venta,
        p_stock_actual,
        p_stock_minimo
    );
END;
/

-- UPDATE
CREATE OR REPLACE PROCEDURE sp_actualizar_producto (
    p_id_producto     IN productos.id_producto%TYPE,
    p_codigo_producto IN productos.codigo_producto%TYPE,
    p_nombre_producto IN productos.nombre_producto%TYPE,
    p_id_categoria    IN productos.id_categoria%TYPE,
    p_id_proveedor    IN productos.id_proveedor%TYPE,
    p_precio_venta    IN productos.precio_venta%TYPE,
    p_stock_actual    IN productos.stock_actual%TYPE,
    p_stock_minimo    IN productos.stock_minimo%TYPE
)
AS
BEGIN
    UPDATE productos
    SET
        codigo_producto = p_codigo_producto,
        nombre_producto = p_nombre_producto,
        id_categoria    = p_id_categoria,
        id_proveedor    = p_id_proveedor,
        precio_venta    = p_precio_venta,
        stock_actual    = p_stock_actual,
        stock_minimo    = p_stock_minimo
    WHERE id_producto = p_id_producto;
END;
/

-- DELETE
CREATE OR REPLACE PROCEDURE sp_eliminar_producto (
    p_id_producto IN productos.id_producto%TYPE
)
AS
BEGIN
    DELETE FROM productos
    WHERE id_producto = p_id_producto;
END;
/

-- SELECT
CREATE OR REPLACE PROCEDURE sp_select_productos
AS
BEGIN
    FOR r IN (
        SELECT
            p.id_producto,
            p.codigo_producto,
            p.nombre_producto,
            c.nombre_categoria,
            pr.nombre_proveedor,
            p.precio_venta,
            p.stock_actual,
            p.stock_minimo
        FROM productos p
        INNER JOIN categorias c
            ON p.id_categoria = c.id_categoria
        INNER JOIN proveedores pr
            ON p.id_proveedor = pr.id_proveedor
        ORDER BY p.id_producto
    )
    LOOP
        DBMS_OUTPUT.PUT_LINE(
            'ID: ' || r.id_producto ||
            ' | Codigo: ' || r.codigo_producto ||
            ' | Producto: ' || r.nombre_producto ||
            ' | Categoria: ' || r.nombre_categoria ||
            ' | Proveedor: ' || r.nombre_proveedor ||
            ' | Precio: ' || r.precio_venta ||
            ' | Stock: ' || r.stock_actual ||
            ' | Stock minimo: ' || r.stock_minimo
        );
    END LOOP;
END;
/

--------------- PRUEBAS ----------------
