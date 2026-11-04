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


## Avance de Java — Persona 5

La aplicación Java se abre como proyecto Maven en NetBeans. Incluye conexión JDBC y menú de consola para llamar a los procedimientos del equipo.

Leer [la guía de instalación](docs/LEEME_PRIMERO.md), [la revisión de integración](docs/Revision_integracion.md) y [el diccionario de datos](docs/Diccionario_datos.md).

Antes de ejecutar, copiar `config.example.properties` como `config.properties` y completar los datos locales. El Wallet y la contraseña no se suben al repositorio.

Este avance integra los encabezados de facturas; la lógica de detalle, impuestos calculados y stock del paso 3 sigue pendiente del aporte del equipo.
