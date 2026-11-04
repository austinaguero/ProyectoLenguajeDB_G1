# Revisión del repositorio e integración

Revisión del 4 de noviembre de 2026. Repositorio: https://github.com/austinaguero/ProyectoLenguajeDB_G1

## Fuentes del equipo

| Integrante | Rama | Commit revisado | Aporte |
|---|---|---|---|
| Austin Agüero | develop | 5c080a1187caf09496a137d98490b4b5875717d7 | database/01_tablas.sql: 12 tablas |
| Eduardo Andrés Guzmán | Inventario | 07cba0ee0142573eaad5f2565da23e35ff9ce178 | 02_crud_inventario.sql: CRUD de categorías, proveedores y productos |
| Daniela Muñoz | procedimientos-almacenados | 063ef10fbbcab0d03aa67ac5c624f481bf860966 | PROYECTO_DANIELA.sql: procedimientos de empleados, clientes, usuarios y facturas |

Los tres archivos SQL originales se conservan íntegros. El TXT de tablas recibido tiene las mismas definiciones que el SQL de Austin. El nuevo script de instalación reutiliza esas definiciones sin ejecutar el bloque destructivo inicial.

## Revisión de Daniela

- Empleados, clientes y usuarios tienen INSERT, UPDATE, DELETE y SELECT mediante procedimientos.
- El UPDATE de usuarios recibe ID de usuario, username, password_hash y estado. No recibe ID de empleado. Java respeta ese orden y no añade un parámetro inexistente.
- INSERT de factura recibe número, ID de cliente, ID de usuario, ID de método de pago, subtotal, impuesto y total: siete parámetros. Java usa exactamente ese orden.
- Las cuatro consultas devuelven un parámetro OUT SYS_REFCURSOR. Java lo registra con Types.REF_CURSOR y lee un ResultSet.
- Su archivo contiene tablas y un ejemplo antiguo dentro de un comentario inicial. Esa sección no se ejecuta.
- Contiene COMMIT en las operaciones de escritura. Las clases Java no intentan agruparlas como una transacción reversible.
- Facturas tiene insertar, listar y anular. Para cubrir también el UPDATE general y DELETE pedidos en la división, Persona 5 aporta sp_actualizar_factura y sp_eliminar_factura en 04_apoyo_java.sql. No se sustituyó ni se renombró ningún procedimiento de Daniela.

## Revisión de Eduardo

Sus procedimientos de escritura permiten reutilizar las llamadas sin cambios de parámetros. Sus consultas sp_select_categorias, sp_select_proveedores y sp_select_productos imprimen DBMS_OUTPUT. Se mantienen y se agregan consultas independientes sp_listar_* que devuelven cursores para Java. Las consultas nuevas usan SELECT, JOIN y OUT SYS_REFCURSOR de la materia recibida.

## Pendientes del equipo

1. Incorporar el trigger trg_validar_stock_factura, las funciones de IVA y total, y las dos vistas del paso 3. No estaban presentes en las ramas revisadas.
2. Acordar con Persona 4 cómo insertar detalles, recalcular importes y tratar anulaciones/eliminaciones, para no descontar o reponer stock dos veces.
3. Ejecutar los procedimientos en Oracle y completar las pruebas manuales con datos válidos.
4. Agregar capturas reales y el enunciado completo para comprobar los requisitos formales del paso 5. No se inventaron evidencias.
5. Revisar el diccionario generado a partir de las 12 tablas.
6. Mantener una rama de trabajo de Persona 5 e integrar en develop tras las pruebas. main aún no representa la versión final.

El Wallet y las contraseñas son configuración local y no forman parte del código versionado.

## Validaciones de este avance

- Las 10 clases Java compilaron con destino Java 11.
- Se compararon el nombre, número y orden de los parámetros de las 29 llamadas con las firmas de los procedimientos SQL.
- Una prueba JDBC simulada ejecutó las 29 llamadas, comprobó la lectura de cursores, el cierre de recursos y la propagación de SQLException. Esta prueba no se conectó a Oracle.
- Se comprobó la salida de configuración pendiente al ejecutar sin config.properties.
- No se ejecutaron operaciones en la base de Oracle: falta la contraseña del esquema. El Wallet por sí solo no permite completar la autenticación de esta aplicación. Las pruebas reales están descritas en Pruebas_manuales.md.

## Estado de publicación

Rama local: sebastian-persona5-java. Los aportes del equipo se integraron en un commit de merge y los nuevos archivos en un commit de Persona 5. GitHub rechazó la escritura remota por permisos de la conexión, por lo que no se publicó la rama ni se creó un pull request. El ZIP conserva el historial para publicar desde NetBeans.
