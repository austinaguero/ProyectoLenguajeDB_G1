# Pruebas manuales de integración en Oracle y Java

Pendientes de ejecutar con la cuenta real del esquema. Usar datos de prueba nuevos después de instalar la lógica de negocio.

1. Ejecutar database/06_actualizar_integracion.sql con F5 en el esquema que ya tiene las 12 tablas. Comprobar que los objetos estén VALID y no haya errores de compilación.
2. Configurar config.properties y ejecutar Main. Esperado: conexión correcta y menú.
3. Crear o consultar un producto de prueba con precio 1000, stock 10 y mínimo 5, con categoría y proveedor válidos. Preparar cliente, empleado, usuario y método de pago existentes.
4. Crear una factura desde Java y listar. Esperado: encabezado EMITIDA con subtotal, impuesto y total cero.
5. Agregar 2 unidades del producto a esa factura. Esperado: detalle de cantidad 2, precio unitario 1000 y subtotal de línea 2000; stock 8; movimiento SALIDA de 2 unidades; encabezado con subtotal 2000, impuesto 260 y total 2260.
6. Consultar detalle desde el menú. Esperado: la fila de la vista con cliente, producto, cantidades e importes.
7. Consultar el cálculo de IVA y total para 2000. Esperado: 260 y 2260, sin guardar cambios. Un subtotal negativo debe producir error.
8. Crear otra factura vacía y tratar de agregar 9 unidades del mismo producto cuando quedan 8. Esperado: error de stock; sin nuevo detalle, sin salida y sin alteración de los importes de esa factura. El stock sigue en 8.
9. Actualizar número o cliente de la primera factura. Esperado: se conservan los montos derivados de sus detalles y no se solicita cambiar el estado.
10. Anular la primera factura. Esperado: estado ANULADA, stock restaurado a 10 y movimiento ENTRADA de 2. Una segunda anulación debe rechazarse, sin otra devolución.
11. Intentar actualizar o agregar un producto a esa factura anulada. Esperado: rechazo sin cambio de estado ni stock.
12. Intentar eliminar la segunda factura mientras está EMITIDA. Esperado: error que exige anular primero. Anularla y eliminarla para limpiar esa prueba.
13. Eliminar la primera factura que ya fue anulada. Esperado: se eliminan encabezado y detalles, pero el stock continúa en 10.
14. Para comprobar la vista bajo el mínimo, crear una factura nueva y vender 5 unidades del producto. Esperado: stock 5 e inclusión en la vista, pues Jefferson usa <=. Anularla; esperado: stock 10 y ya no aparece si el mínimo sigue en 5.
15. Mantener las pruebas previas de empleados, usuarios, clientes, productos, categorías y proveedores: insertar, listar, actualizar y eliminar respetando las FK; duplicados UNIQUE y valores negativos CHECK deben rechazarse.

Capturas útiles: objetos VALID, factura con detalle e importes, rechazo por stock insuficiente, anulación con devolución y consulta de productos bajo el mínimo. Registrar resultados reales después de ejecutar las pruebas; no se proporcionan capturas simuladas como evidencia.
