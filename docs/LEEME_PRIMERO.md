# Java e integración de facturación e inventario

Sebastián Campos Rojas · SC-504 · Laboratorios Fave S.A.

Este avance integra los procedimientos de Daniela y Eduardo con la lógica de negocio de Jefferson. Java usa un menú de consola, clases por módulo y CallableStatement. Los SQL originales de los compañeros se conservan.

## Abrir en NetBeans

1. Descomprimir en una carpeta nueva, por ejemplo `LenguajeBD_Actualizado`. No abrir el proyecto desde el ZIP.
2. Usar File > Open Project y abrir `ProyectoLenguajeDB_G1`, donde está `pom.xml`.
3. Usar JDK 11 o superior y permitir que Maven descargue las dependencias de Oracle.
4. Copiar `config.example.properties` como `config.properties`, junto a `pom.xml`.
5. Completar usuario y contraseña del esquema. `FAVE_APP` es el esquema señalado en el aporte del equipo; verificar que sea la cuenta utilizada.
6. Descomprimir el Wallet recibido en una carpeta local y escribir su ruta en `db.wallet`, por ejemplo `C:/Oracle/Wallet_LenguajeDB`. Usar la carpeta, no el ZIP. Mantener juntos sus archivos.
7. El alias recibido es `lenguajedb_low`. No hay que editar el Wallet ni publicar sus certificados.
8. Ejecutar Clean and Build y luego Run Project. La clase principal es `cr.ac.fidelitas.favesa.Main`.

Si no se encuentra config.properties, revisar que el directorio de trabajo sea el que contiene pom.xml. Para ejecutar desde una terminal con Maven: `mvn clean compile exec:java`.

## Actualizar la base que ya tiene las 12 tablas

Abrir `database/06_actualizar_integracion.sql` en SQL Developer, en la conexión del esquema del proyecto, y ejecutarlo completo como script con F5. No crea ni borra tablas: crea o reemplaza los procedimientos, las funciones, las vistas y el trigger. Guardar los archivos descomprimidos juntos para resolver las rutas `@@`.

El orden es: procedimientos de Eduardo, procedimientos de Daniela, funciones de Jefferson, vistas de Jefferson, trigger de Jefferson, procedimientos de Jefferson y apoyo Java. Así queda instalada la versión de Jefferson de `sp_anular_factura` y `sp_eliminar_factura`.

Al terminar, revisar las consultas de verificación: los objetos deben quedar VALID y no debe haber errores de compilación para estos objetos. Si hay errores, corregirlos antes de probar el menú.

Si el esquema está vacío, usar `database/00_instalacion_nueva.sql`. El archivo original `database/01_tablas.sql` empieza eliminando tablas; se conserva como fuente del grupo. Para una instalación inicial, `01_estructura_nueva.sql` reutiliza las definiciones sin ese bloque de borrado.

## Usar las facturas

1. Crear o consultar categorías y proveedores, luego productos con existencias. Crear o consultar empleados, usuarios y clientes. Debe existir también al menos un método de pago en la base.
2. En Facturas, opción 1, ingresar número, ID de cliente, ID de usuario e ID de método de pago. Se usa el procedimiento de Daniela y se crea un encabezado con importes cero.
3. Usar opción 4, Listar, y copiar el ID de la factura recién creada.
4. Usar opción 6, Agregar producto: indicar ID de factura, ID de producto y cantidad.
5. Oracle toma el precio del producto. El trigger de Jefferson valida y descuenta el stock y registra la salida. El procedimiento de apoyo suma los detalles y utiliza sus funciones para guardar el impuesto y total.
6. Agregar otros productos del mismo modo. La restricción UNIQUE permite una sola línea de cada producto por factura; no repetir un producto ya añadido. Este avance no incluye editar ni borrar líneas de detalle.
7. Consultar el encabezado con opción 4 y la vista de detalle con opción 7. La opción 8 permite consultar IVA y total para un subtotal de ejemplo, sin guardar una factura.
8. Para anular, usar opción 5: el procedimiento de Jefferson devuelve las cantidades al inventario y cambia el estado. Repetir la anulación se rechaza para evitar otra devolución.
9. Para eliminar, la factura debe estar anulada. La opción 3 usa la versión de Jefferson; la FK con cascada elimina sus detalles. No se devuelve inventario otra vez.
10. La opción Actualizar modifica los datos del encabezado de una factura EMITIDA y recalcula sus importes desde los detalles. No permite cambiar el estado ni ingresar montos manuales.

En Productos, opción 5, se consulta la vista de inventario bajo el mínimo. La vista de Jefferson incluye también productos exactamente en su stock mínimo (`<=`).

Si existen detalles de pruebas anteriores a instalar el trigger, comprobar primero sus movimientos: la actualización no reconstruye ni corrige salidas históricas. Para verificar esta integración, crear facturas de prueba nuevas después de instalarla.

## Configuración y comportamiento

- Estados de usuario: ACTIVO o INACTIVO. Las facturas se crean EMITIDAS; la anulación se realiza por su procedimiento.
- `password_hash` recibe un hash ya calculado; no se implementó autenticación ni generación de hashes. No introducir contraseñas reales como hash de prueba.
- Los IDs relacionados deben existir. Las llaves foráneas y restricciones de las tablas siguen activas.
- Java conserva auto-commit de JDBC. Los procedimientos originales de Daniela y el de anulación de Jefferson incluyen COMMIT; no se agrupan varias acciones del menú como una sola transacción reversible.
- Los campos opcionales vacíos se reciben como NULL en Oracle.
- La app está preparada para este orden de instalación. Ejecutar posteriormente solo PROYECTO_DANIELA.sql volvería a instalar su anulación anterior; en ese caso ejecutar de nuevo 06_actualizar_integracion.sql completo.

## Publicar la actualización

La copia conserva la rama `sebastian-persona5-java`, partiendo del commit cuyo mensaje se corrigió, e integra el historial de Jefferson. Los nuevos commits se pueden publicar con Git > Remote > Push, seleccionando únicamente esa rama. El pull request #2 se actualiza al subir nuevos commits a la misma rama.

El Wallet y config.properties quedan fuera de Git. No se modifican main ni develop al publicar la rama.

## Materia y alcance

SQL y PL/SQL utilizan tablas, DML, JOIN, bloques, procedimientos, funciones y OUT SYS_REFCURSOR de las presentaciones recibidas. Los triggers y procedimientos de negocio se incorporan desde el aporte de Jefferson. Java/JDBC se añadió con autorización expresa de Sebastián.

La integración se revisó y compiló, con pruebas JDBC simuladas. Aún requiere ejecución y verificación en Oracle con la cuenta real del esquema.
