# Sistema de Facturación y Control de Inventario - Laboratorios Fave S.A.

Proyecto del curso **SC-504 Lenguajes de Base de Datos**, Universidad Fidélitas.

## Descripción

Sistema de facturación y control de inventario para Laboratorios Fave S.A., una empresa familiar costarricense dedicada a la creación y comercialización de productos para vehículos.

El sistema permitirá gestionar productos, categorías, clientes, proveedores, usuarios, movimientos de inventario y facturas, utilizando una base de datos relacional en Oracle y una aplicación en Java.

## Tecnologías

- Oracle Database (SQL y PL/SQL)
- Java
- JDBC

## Módulos

- Productos y categorías
- Proveedores e inventario
- Clientes y usuarios
- Facturación
- Consultas y reportes

## Integrantes

- Daniela Muñoz Valverde
- Eduardo Andrés Guzmán Jiménez
- Sebastián Campos Rojas
- Austin Agüero Montero

## Estado

En desarrollo. Entrega 1: análisis y diseño.


## Avance de Java e integración

Aplicación de consola para NetBeans con conexión JDBC y llamadas a los procedimientos del equipo. Integra el aporte de Jefferson: detalles con validación de stock, funciones de impuestos y total, consultas de vistas y anulación con devolución de inventario.

- [Instalación y uso](docs/LEEME_PRIMERO.md).
- [Revisión de integración](docs/Revision_integracion.md).
- [Diccionario de datos](docs/Diccionario_datos.md).
- [Pruebas manuales pendientes en Oracle](docs/Pruebas_manuales.md).

Para un esquema que ya tiene las tablas, ejecutar `database/06_actualizar_integracion.sql`. Para uno nuevo, usar `database/00_instalacion_nueva.sql`.

Copiar `config.example.properties` como `config.properties` y completar los datos locales. El Wallet y la contraseña no forman parte del repositorio.

La compilación y las llamadas se verifican localmente; falta comprobar la ejecución real con Oracle.
