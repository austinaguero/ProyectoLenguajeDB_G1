# Avance de Persona 5: Java e integración

Sebastián Campos Rojas · SC-504 · Laboratorios Fave S.A.

Este avance permite llamar desde Java a los procedimientos de empleados, clientes, usuarios, facturas, categorías, proveedores y productos. La aplicación usa un menú de consola y una clase sencilla para cada módulo. No requiere una interfaz gráfica.

## Abrir en NetBeans

1. Descomprimir el proyecto.
2. En NetBeans, seleccionar **File > Open Project** y abrir la carpeta que contiene `pom.xml`.
3. Usar un JDK 11 o superior. NetBeans descargará con Maven las dependencias de Oracle del `pom.xml`.
4. Copiar `config.example.properties` y llamar a la copia `config.properties`, junto a `pom.xml`.
5. Completar `db.usuario` y `db.password` con la cuenta del esquema del proyecto. `FAVE_APP` es el esquema señalado en el aporte de tablas del grupo; verificar que sea el que usan.
6. Descomprimir el Wallet recibido en una carpeta local y poner esa ruta en `db.wallet`, por ejemplo `C:/Oracle/Wallet_LenguajeDB`. No escribir la ruta del ZIP. Conservar sus archivos `tnsnames.ora`, `ojdbc.properties` y certificados juntos.
7. El alias `lenguajedb_low` se tomó del `tnsnames.ora` recibido. No hace falta editar el Wallet.
8. Ejecutar **Clean and Build** y después **Run Project**. Si NetBeans pide la clase principal, seleccionar `cr.ac.fidelitas.favesa.Main`.
9. Si aparece que no encuentra `config.properties`, revisar que el directorio de trabajo sea la carpeta del proyecto, donde está `pom.xml`.

También se puede ejecutar con Maven: `mvn clean compile exec:java`.

## Preparación de Oracle

**Si las tablas ya existen**, no volver a crearlas. Ejecutar como script (F5), en la conexión del esquema del proyecto:

1. `02_crud_inventario.sql`.
2. `PROYECTO_DANIELA.sql`.
3. `database/04_apoyo_java.sql`.
4. `database/05_verificar_objetos.sql` y comprobar que no haya errores de compilación y que los procedimientos estén VALID.

Estos scripts crean o reemplazan procedimientos. Los bloques de creación de tablas del archivo de Daniela están comentados.

**Solo si el esquema está vacío**, ejecutar `database/00_instalacion_nueva.sql` con F5. Las referencias `@@` cargan los scripts en orden. Si SQL Developer no resuelve las rutas, abrir y ejecutar manualmente los cuatro scripts indicados allí.

`database/01_tablas.sql` es el aporte original de Austin y empieza borrando las tablas del proyecto. Se conserva como fuente del equipo. Para instalar desde cero usar `01_estructura_nueva.sql`, que contiene las mismas definiciones sin ese bloque de borrado.

## Uso del programa

- Seleccionar un módulo y luego insertar, actualizar, eliminar o listar. Tras cada operación se vuelve al menú principal.
- Para referencias a otras tablas, usar IDs existentes; se pueden consultar en los módulos respectivos.
- Crear primero categorías y proveedores antes de productos; empleados antes de usuarios; clientes, usuarios y métodos de pago antes de facturas.
- Los métodos de pago pertenecen a las tablas de apoyo. Si no tienen registros, el responsable de la base debe cargarlos antes de probar facturas.
- El estado de usuario acepta `ACTIVO` o `INACTIVO`. El de factura acepta `EMITIDA` o `ANULADA`.
- `password_hash` recibe un hash ya calculado. Este avance no implementa autenticación ni generación de hashes; no introducir contraseñas reales en ese campo.
- Si no se proporciona un campo opcional, Oracle recibe una cadena vacía, que trata como NULL.
- La opción eliminar pide confirmación. Las llaves foráneas impiden borrar registros referenciados, salvo las relaciones configuradas con cascada.
- Anular una factura cambia el estado. Eliminar una factura la borra y su FK con cascada también borra sus detalles. Estas opciones no implementan reversión de stock.
- La aplicación conserva auto-commit de JDBC. Los procedimientos de Daniela también contienen COMMIT; por eso las llamadas completadas no forman una única transacción que se pueda deshacer desde Java.
- Si un ID no existe, un UPDATE o DELETE puede afectar cero filas. El programa informa que ejecutó el procedimiento y pide comprobar con Listar; no afirma que haya encontrado el registro.

## Alcance de facturación

El módulo de facturas trabaja con **encabezados**. Los montos se ingresan para probar las llamadas existentes de Daniela. No crea detalles, no calcula IVA automáticamente ni descuenta o repone stock. La integración del trigger, las funciones y las vistas del paso 3 queda pendiente del aporte de Persona 4 y de las pruebas con el equipo. Este avance no representa todavía una venta completa.

## Archivos principales

| Archivo o carpeta | Contenido |
|---|---|
| `src/main/java/cr/ac/fidelitas/favesa/Conexion.java` | Lee la configuración local y abre la conexión JDBC |
| `Main.java` | Menú de consola |
| `Empleados.java`, `Clientes.java`, `Usuarios.java`, `Facturas.java` | Llamadas a la parte de Daniela y complementos de factura |
| `Categorias.java`, `Proveedores.java`, `Productos.java` | Llamadas a la parte de Eduardo |
| `Consultas.java` | Imprime las filas del cursor |
| `database/04_apoyo_java.sql` | Tres consultas con cursor y dos procedimientos complementarios de factura |
| `docs/Diccionario_datos.md` | Diccionario de las 12 tablas recibidas |
| `docs/Revision_integracion.md` | Revisión de los aportes y pendientes |
| `docs/Pruebas_manuales.md` | Pasos de comprobación en Oracle y NetBeans |

## Materia utilizada

SQL y PL/SQL se apoyan en las presentaciones recibidas: DML y tablas en semanas 1 y 2; JOIN y bloques en clase 5; variables y condiciones en clase 6; procedimientos en clases 7-8; `OUT SYS_REFCURSOR` y `OPEN ... FOR` en clase 9. El manejo de recursos de los cursores se relaciona con clase 10.

Java/JDBC se añadió con autorización expresa de Sebastián, aunque no figure en las presentaciones. No se usaron frameworks, ORM, interfaces de servicio ni conexiones agrupadas. Se usan clases, métodos, Scanner, switch, try/catch y JDBC.

Documentación técnica consultada para la conexión y cursores:

- [Oracle: conexión JDBC con Wallet](https://docs.oracle.com/en/cloud/paas/autonomous-database/serverless/adbsb/connect-jdbc-thin-wallet.html).
- [Oracle: REF CURSOR y ResultSet](https://docs.oracle.com/en/database/oracle/oracle-database/19/jjdbc/Oracle-extensions.html).

## Continuar en GitHub desde NetBeans

La copia de trabajo conserva una rama local `sebastian-persona5-java`, con el historial de los integrantes y los commits del avance. La publicación remota no se pudo completar: GitHub devolvió un error de permisos de la conexión al intentar escribir.

1. Abrir el proyecto extraído en NetBeans.
2. En el menú Git, elegir **Remote > Push** (los nombres pueden variar según el idioma).
3. Usar la cuenta que tenga acceso de colaborador al repositorio del grupo.
4. Publicar la rama `sebastian-persona5-java`, sin forzar cambios en otras ramas.
5. En GitHub, comparar esa rama contra `develop` y abrir el pull request después de las pruebas reales. La rama `main` no se modificó.

El commit de integración conserva como antecesores los aportes de Eduardo y Daniela. El siguiente commit agrega el trabajo de Persona 5. No se atribuyeron los archivos originales a Sebastián.
