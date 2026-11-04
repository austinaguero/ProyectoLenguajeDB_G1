# Revisión de integración

Revisión del 4 de noviembre de 2026. Repositorio: https://github.com/austinaguero/ProyectoLenguajeDB_G1

## Aportes incorporados

| Integrante | Rama | Commit de origen | Contenido |
|---|---|---|---|
| Austin | develop | 5c080a1187caf09496a137d98490b4b5875717d7 | 12 tablas con restricciones |
| Eduardo | Inventario | 07cba0ee0142573eaad5f2565da23e35ff9ce178 | CRUD de categorías, proveedores y productos |
| Daniela | procedimientos-almacenados | 063ef10fbbcab0d03aa67ac5c624f481bf860966 | CRUD de empleados, clientes y usuarios; encabezados, consulta y anulación inicial de facturas |
| Jefferson | Logica-negocio | 4a066617f4efda213d912362b96d7d06703c1f51 | Trigger de stock, funciones, vistas, anulación con devolución y borrado de facturas anuladas |
| Sebastián | sebastian-persona5-java | 4eb6a6110232e759d180b4e743037673aebf99dc | Conexión, menú, llamadas JDBC, apoyo de consultas y diccionario; base de esta actualización |

Los archivos originales de Austin, Eduardo, Daniela y Jefferson se conservan íntegros. Los cambios de integración se concentran en el apoyo SQL, Java y documentación.

## Ajustes aplicados

1. Se eliminó de 04_apoyo_java.sql el DELETE provisional de facturas. La definición activa es la de Jefferson, que exige que la factura esté ANULADA.
2. La anulación de Jefferson se instala después de la versión anterior de Daniela y recibe el mismo ID que ya enviaba Java.
3. sp_actualizar_factura ahora recibe cinco parámetros: ID, número, cliente, usuario y método de pago. Solo modifica facturas EMITIDAS. No recibe estado ni montos manuales y recalcula los importes desde los detalles.
4. Se agregó sp_agregar_detalle_factura: bloquea la factura, valida su estado, toma el precio del producto e inserta la línea. Ese INSERT dispara el trigger de Jefferson. Luego se recalculan los montos con sus funciones.
5. Se agregaron dos procedimientos OUT SYS_REFCURSOR que consultan las vistas de Jefferson y dos opciones Java para leerlas.
6. Facturas.java llama directamente a fn_calcular_iva y fn_calcular_total_factura con retorno NUMERIC. El encabezado se crea mediante el INSERT de siete parámetros de Daniela con montos cero.
7. Se actualizaron los scripts de instalación y el menú. El diccionario mantiene las mismas columnas porque no se cambió el modelo de tablas.

## Orden y dependencias

El script 06_actualizar_integracion.sql actualiza los objetos en un esquema con tablas existentes. El 00_instalacion_nueva.sql crea primero las tablas para un esquema nuevo. Ambos instalan después de Daniela los procedimientos de Jefferson y al final el apoyo Java.

El PR #3 de Jefferson se abrió hacia main; el PR #2 de Sebastián apunta a develop. Esta rama integra su aporte para revisar todos los componentes juntos. El equipo debe acordar el destino de los PR antes de fusionarlos.

## Comprobaciones y límites

Se comprueba compilación Java, correspondencia de parámetros, retornos de funciones, lectura de cursores y cierre de recursos mediante pruebas simuladas. No se dispone de la contraseña de Oracle, por lo que las pruebas del trigger, las restricciones, los importes almacenados y la devolución de stock requieren ejecutar Pruebas_manuales.md.

El flujo Java incorpora inserción de detalles, pero no edición ni borrado individual de líneas. El trigger original de Jefferson actúa solo en INSERT. Los importes y el inventario se mantienen mediante los objetos instalados; modificar manualmente detalles o tablas puede saltarse ese flujo.

## Resultado de las comprobaciones de esta actualización

- Las 10 clases Java compilaron con destino Java 11.
- Las 34 llamadas de Java coinciden en nombre y cantidad de parámetros con los objetos que deja instalados el script de actualización.
- Se probaron con JDBC simulado los cambios de parámetros, los retornos de funciones, la lectura de las dos vistas mediante cursores, el cierre de recursos y la propagación de errores.
- Se resolvieron las rutas de instalación y se comprobó que la actualización no contiene creación ni borrado de tablas. Las definiciones finales de anular y eliminar provienen de Jefferson.
- Los siete archivos SQL originales de los integrantes conservaron sus bytes.
- No se ejecutaron pruebas contra Oracle. Falta verificar allí la compilación de PL/SQL y el comportamiento completo de datos e inventario.
