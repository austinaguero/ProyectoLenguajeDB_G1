# Correspondencia de Java y Oracle

El orden de la lista coincide con las posiciones 1, 2, 3... de CallableStatement.

| Clase | Método | Procedimiento | Parámetros en orden |
|---|---|---|---|
| Empleados | insertar | sp_insertar_empleado | cedula, nombre, primerApellido, segundoApellido, correo, telefono |
| Empleados | actualizar | sp_actualizar_empleado | idEmpleado, cedula, nombre, primerApellido, segundoApellido, correo, telefono |
| Empleados | eliminar | sp_eliminar_empleado | idEmpleado |
| Empleados | listar | sp_listar_empleados | p_cursor OUT SYS_REFCURSOR |
| Clientes | insertar | sp_insertar_cliente | identificacion, nombreCliente, telefono, correo, direccion |
| Clientes | actualizar | sp_actualizar_cliente | idCliente, identificacion, nombreCliente, telefono, correo, direccion |
| Clientes | eliminar | sp_eliminar_cliente | idCliente |
| Clientes | listar | sp_listar_clientes | p_cursor OUT SYS_REFCURSOR |
| Usuarios | insertar | sp_insertar_usuario | idEmpleado, username, passwordHash, estado |
| Usuarios | actualizar | sp_actualizar_usuario | idUsuario, username, passwordHash, estado |
| Usuarios | eliminar | sp_eliminar_usuario | idUsuario |
| Usuarios | listar | sp_listar_usuarios | p_cursor OUT SYS_REFCURSOR |
| Facturas | insertar | sp_insertar_factura | numeroFactura, idCliente, idUsuario, idMetodoPago, subtotal, impuesto, montoTotal |
| Facturas | actualizar | sp_actualizar_factura | idFactura, numeroFactura, idCliente, idUsuario, idMetodoPago, subtotal, impuesto, montoTotal, estado |
| Facturas | eliminar | sp_eliminar_factura | idFactura |
| Facturas | listar | sp_listar_facturas | p_cursor OUT SYS_REFCURSOR |
| Facturas | anular | sp_anular_factura | idFactura |
| Categorias | insertar | sp_insertar_categoria | nombreCategoria |
| Categorias | actualizar | sp_actualizar_categoria | idCategoria, nombreCategoria |
| Categorias | eliminar | sp_eliminar_categoria | idCategoria |
| Categorias | listar | sp_listar_categorias | p_cursor OUT SYS_REFCURSOR |
| Proveedores | insertar | sp_insertar_proveedor | cedulaJuridica, nombreProveedor, telefono, correo, direccion |
| Proveedores | actualizar | sp_actualizar_proveedor | idProveedor, cedulaJuridica, nombreProveedor, telefono, correo, direccion |
| Proveedores | eliminar | sp_eliminar_proveedor | idProveedor |
| Proveedores | listar | sp_listar_proveedores | p_cursor OUT SYS_REFCURSOR |
| Productos | insertar | sp_insertar_producto | codigoProducto, nombreProducto, idCategoria, idProveedor, precioVenta, stockActual, stockMinimo |
| Productos | actualizar | sp_actualizar_producto | idProducto, codigoProducto, nombreProducto, idCategoria, idProveedor, precioVenta, stockActual, stockMinimo |
| Productos | eliminar | sp_eliminar_producto | idProducto |
| Productos | listar | sp_listar_productos | p_cursor OUT SYS_REFCURSOR |
