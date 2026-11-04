# Pruebas manuales en Oracle y Java

Estas pruebas requieren la cuenta del esquema y un Wallet válido. No se presentan como ejecutadas contra Oracle.

1. Ejecutar los scripts de procedimientos y luego `database/05_verificar_objetos.sql`. Esperado: procedimientos VALID y ninguna fila de error para estos objetos.
2. Configurar `config.properties` y ejecutar Main. Esperado: mensaje de conexión correcta y siete módulos.
3. Insertar una categoría de prueba; listar; copiar su ID; actualizar su nombre; listar nuevamente. Esperado: datos correctos en ambas consultas.
4. Insertar un proveedor válido y luego un producto que use los IDs reales de categoría y proveedor. Listar productos. Esperado: nombres de categoría y proveedor junto con precio y stock.
5. Intentar insertar un producto con precio negativo. Esperado: Oracle rechaza el registro por CHECK; no aparece al listar.
6. Insertar un empleado con cédula de prueba no utilizada, listar y copiar su ID. Actualizarlo y volver a listar. Esperado: se mantienen todos los datos y cambia el campo editado.
7. Insertar un usuario asociado a ese empleado usando un hash de prueba ya calculado. Listar y comprobar nombre del empleado, username y estado; no se muestra password_hash.
8. Insertar un cliente de prueba. Listar, actualizar y volver a listar. Esperado: cursor leído sin errores y datos actualizados.
9. Repetir una identificación de cliente. Esperado: Oracle rechaza el duplicado por UNIQUE.
10. Usando un método de pago existente, insertar una factura con subtotal 1000, impuesto 130 y total 1130. Listar. Esperado: encabezado con relaciones y montos ingresados. Son datos de prueba, no una validación de tasa fiscal.
11. Actualizar la factura con la opción 2 y listar; anular con la opción 5 y listar. Esperado: valores actualizados y luego estado ANULADA. No se espera cambio automático de stock en este avance.
12. Eliminar solo la factura de prueba, el usuario de prueba, el empleado, el cliente, el producto, el proveedor y la categoría, en ese orden. Listar para comprobar. Responder n en una confirmación y comprobar que no se borre.
13. Intentar borrar una categoría mientras aún tiene un producto. Esperado: la FK impide el borrado.
14. Probar un listado vacío, una entrada no numérica en el menú y volver con 0. Esperado: mensaje sin registros, solicitud de número válido y navegación correcta.
15. Quitar temporalmente config.properties. Esperado: mensaje de configuración pendiente, sin publicar credenciales.

Registrar las capturas reales después de ejecutar estas pruebas. La validación de stock, IVA calculado y vistas se añadirá con la lógica de Persona 4.
