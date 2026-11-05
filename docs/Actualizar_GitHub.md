# Subir esta actualización al mismo pull request

1. Cerrar el proyecto anterior en NetBeans.
2. Descomprimir el ZIP actualizado en una carpeta nueva y abrir ProyectoLenguajeDB_G1, donde está pom.xml.
3. Elegir Git > Remote > Push. Si se solicita la URL, usar https://github.com/austinaguero/ProyectoLenguajeDB_G1.git.
4. Seleccionar únicamente sebastian-persona5-java. Mantener ese mismo nombre en el destino. No usar force push.
5. Terminar la subida e iniciar sesión con la cuenta de colaborador si se solicita. Esta copia contiene dos commits locales nuevos: la integración del historial de Jefferson y los ajustes de Java/SQL.
6. Abrir https://github.com/austinaguero/ProyectoLenguajeDB_G1/pull/2. Al publicar en la misma rama, la solicitud incorpora los nuevos commits automáticamente.
7. Editar la descripción del PR para reemplazar la frase que decía que el paso 3 estaba pendiente. Puede quedar así:

Se integra la conexión JDBC y el menú Java con los procedimientos de Daniela y Eduardo y la lógica de facturación e inventario de Jefferson.

Se agrega el registro de detalles que activa el trigger de stock, el cálculo de IVA y total mediante las funciones de Oracle, y la consulta de las dos vistas. La anulación devuelve el inventario y solo se permite eliminar facturas anuladas. Se actualizan los scripts y el diccionario de datos.

El código Java compiló y pasó pruebas JDBC simuladas. Queda pendiente verificar la ejecución real en Oracle.

## Actualizar Oracle

Para la base del grupo que ya tiene las tablas, ejecutar database/06_actualizar_integracion.sql con F5 en SQL Developer. No volver a crear las tablas. El menú actualizado y este script deben utilizarse juntos porque cambió la firma de sp_actualizar_factura.

Si todavía no vas a realizar la prueba con Oracle, puedes publicar el avance e indicar esa prueba pendiente. No publicar el Wallet ni config.properties.
