package cr.ac.fidelitas.favesa;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static final Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Laboratorios Fave S.A. - Avance de integración Java");
        System.out.println("Facturación: encabezados, detalles, impuestos y stock integrados con Oracle.");
        try (Connection conexion = Conexion.conectar()) {
            System.out.println("Conexión correcta a Oracle.");
            int opcion;
            do {
                System.out.println("\n1. Empleados\n2. Clientes\n3. Usuarios\n4. Facturas"
                        + "\n5. Categorías\n6. Proveedores\n7. Productos\n0. Salir");
                opcion = entero("Opción");
                try {
                    switch (opcion) {
                        case 1: menuEmpleados(conexion); break;
                        case 2: menuClientes(conexion); break;
                        case 3: menuUsuarios(conexion); break;
                        case 4: menuFacturas(conexion); break;
                        case 5: menuCategorias(conexion); break;
                        case 6: menuProveedores(conexion); break;
                        case 7: menuProductos(conexion); break;
                        case 0: System.out.println("Programa finalizado."); break;
                        default: System.out.println("Opción incorrecta.");
                    }
                } catch (SQLException e) {
                    System.out.println("Oracle (" + e.getErrorCode() + "): " + e.getMessage());
                }
            } while (opcion != 0);
        } catch (IOException e) {
            System.out.println("Configuración: " + e.getMessage());
            System.out.println("Copie config.example.properties como config.properties y complete sus datos.");
        } catch (SQLException e) {
            System.out.println("No se pudo conectar con Oracle: " + e.getMessage());
        }
    }

    private static String texto(String etiqueta) {
        System.out.print(etiqueta + ": ");
        return teclado.nextLine().trim();
    }

    private static int entero(String etiqueta) {
        while (true) {
            try {
                return Integer.parseInt(texto(etiqueta));
            } catch (NumberFormatException e) {
                System.out.println("Escriba un número entero.");
            }
        }
    }

    private static double decimal(String etiqueta) {
        while (true) {
            try {
                double valor = Double.parseDouble(texto(etiqueta).replace(',', '.'));
                if (Double.isFinite(valor)) {
                    return valor;
                }
            } catch (NumberFormatException e) {
                // La siguiente línea también explica el error de formato.
            }
            System.out.println("Escriba un número, por ejemplo 1500.50.");
        }
    }

    private static boolean confirmar(String mensaje) {
        return texto(mensaje + " (s/n)").equalsIgnoreCase("s");
    }

    private static void menuEmpleados(Connection conexion) throws SQLException {
        Empleados datos = new Empleados(conexion);
        System.out.println("\nEmpleados: 1. Insertar  2. Actualizar  3. Eliminar  4. Listar  0. Volver");
        int accion = entero("Acción");
        switch (accion) {
            case 1:
                datos.insertar(texto("Cedula"), texto("Nombre"), texto("Primer apellido"), texto("Segundo apellido"), texto("Correo"), texto("Telefono"));
                System.out.println("Procedimiento ejecutado. Use Listar para comprobar el resultado.");
                break;
            case 2:
                datos.actualizar(entero("ID del empleado"), texto("Cedula"), texto("Nombre"), texto("Primer apellido"), texto("Segundo apellido"), texto("Correo"), texto("Telefono"));
                System.out.println("Procedimiento ejecutado. Use Listar para comprobar el resultado.");
                break;
            case 3:
                if (!confirmar("¿Eliminar el registro?")) { return; }
                datos.eliminar(entero("ID del empleado"));
                System.out.println("Procedimiento ejecutado. Use Listar para comprobar el resultado.");
                break;
            case 4:
                datos.listar();
                break;
            case 0: break;
            default: System.out.println("Acción incorrecta.");
        }
    }

    private static void menuClientes(Connection conexion) throws SQLException {
        Clientes datos = new Clientes(conexion);
        System.out.println("\nClientes: 1. Insertar  2. Actualizar  3. Eliminar  4. Listar  0. Volver");
        int accion = entero("Acción");
        switch (accion) {
            case 1:
                datos.insertar(texto("Identificacion"), texto("Nombre cliente"), texto("Telefono"), texto("Correo"), texto("Direccion"));
                System.out.println("Procedimiento ejecutado. Use Listar para comprobar el resultado.");
                break;
            case 2:
                datos.actualizar(entero("ID del cliente"), texto("Identificacion"), texto("Nombre cliente"), texto("Telefono"), texto("Correo"), texto("Direccion"));
                System.out.println("Procedimiento ejecutado. Use Listar para comprobar el resultado.");
                break;
            case 3:
                if (!confirmar("¿Eliminar el registro?")) { return; }
                datos.eliminar(entero("ID del cliente"));
                System.out.println("Procedimiento ejecutado. Use Listar para comprobar el resultado.");
                break;
            case 4:
                datos.listar();
                break;
            case 0: break;
            default: System.out.println("Acción incorrecta.");
        }
    }

    private static void menuUsuarios(Connection conexion) throws SQLException {
        Usuarios datos = new Usuarios(conexion);
        System.out.println("\nUsuarios: 1. Insertar  2. Actualizar  3. Eliminar  4. Listar  0. Volver");
        int accion = entero("Acción");
        switch (accion) {
            case 1:
                datos.insertar(entero("ID del empleado"), texto("Username"), texto("Hash de contraseña ya calculado"), texto("Estado"));
                System.out.println("Procedimiento ejecutado. Use Listar para comprobar el resultado.");
                break;
            case 2:
                datos.actualizar(entero("ID del usuario"), texto("Username"), texto("Hash de contraseña ya calculado"), texto("Estado"));
                System.out.println("Procedimiento ejecutado. Use Listar para comprobar el resultado.");
                break;
            case 3:
                if (!confirmar("¿Eliminar el registro?")) { return; }
                datos.eliminar(entero("ID del usuario"));
                System.out.println("Procedimiento ejecutado. Use Listar para comprobar el resultado.");
                break;
            case 4:
                datos.listar();
                break;
            case 0: break;
            default: System.out.println("Acción incorrecta.");
        }
    }

    private static void menuFacturas(Connection conexion) throws SQLException {
        Facturas datos = new Facturas(conexion);
        System.out.println("\nFacturas: 1. Insertar  2. Actualizar  3. Eliminar  4. Listar  5. Anular  6. Agregar producto  7. Consultar detalle  8. Calcular IVA y total  0. Volver");
        int accion = entero("Acción");
        switch (accion) {
            case 1:
                datos.insertar(texto("Numero factura"), entero("ID del cliente"), entero("ID del usuario"), entero("ID del método de pago"));
                System.out.println("Factura vacía creada. Use Listar para conocer su ID y Agregar producto para completarla.");
                System.out.println("Procedimiento ejecutado. Use Listar para comprobar el resultado.");
                break;
            case 2:
                datos.actualizar(entero("Id factura"), texto("Numero factura"), entero("ID del cliente"), entero("ID del usuario"), entero("ID del método de pago"));
                System.out.println("Procedimiento ejecutado. Use Listar para comprobar el resultado.");
                break;
            case 3:
                if (!confirmar("¿Eliminar una factura previamente anulada?")) { return; }
                datos.eliminar(entero("Id factura"));
                System.out.println("Procedimiento ejecutado. Use Listar para comprobar el resultado.");
                break;
            case 4:
                datos.listar();
                break;
            case 5:
                if (!confirmar("¿Anular la factura?")) { return; }
                datos.anular(entero("ID de factura"));
                System.out.println("Procedimiento ejecutado. Use Listar para comprobar el estado.");
                break;
            case 6:
                datos.agregarDetalle(entero("ID de factura"), entero("ID de producto"), entero("Cantidad"));
                System.out.println("Detalle registrado; Oracle actualizó los montos y el inventario.");
                break;
            case 7:
                datos.consultarDetalle();
                break;
            case 8:
                double subtotal = decimal("Subtotal para consultar el cálculo");
                System.out.println("IVA: " + datos.calcularIva(subtotal));
                System.out.println("Total: " + datos.calcularTotal(subtotal));
                break;
            case 0: break;
            default: System.out.println("Acción incorrecta.");
        }
    }

    private static void menuCategorias(Connection conexion) throws SQLException {
        Categorias datos = new Categorias(conexion);
        System.out.println("\nCategorias: 1. Insertar  2. Actualizar  3. Eliminar  4. Listar  0. Volver");
        int accion = entero("Acción");
        switch (accion) {
            case 1:
                datos.insertar(texto("Nombre categoria"));
                System.out.println("Procedimiento ejecutado. Use Listar para comprobar el resultado.");
                break;
            case 2:
                datos.actualizar(entero("ID de categoría"), texto("Nombre categoria"));
                System.out.println("Procedimiento ejecutado. Use Listar para comprobar el resultado.");
                break;
            case 3:
                if (!confirmar("¿Eliminar el registro?")) { return; }
                datos.eliminar(entero("ID de categoría"));
                System.out.println("Procedimiento ejecutado. Use Listar para comprobar el resultado.");
                break;
            case 4:
                datos.listar();
                break;
            case 0: break;
            default: System.out.println("Acción incorrecta.");
        }
    }

    private static void menuProveedores(Connection conexion) throws SQLException {
        Proveedores datos = new Proveedores(conexion);
        System.out.println("\nProveedores: 1. Insertar  2. Actualizar  3. Eliminar  4. Listar  0. Volver");
        int accion = entero("Acción");
        switch (accion) {
            case 1:
                datos.insertar(texto("Cedula juridica"), texto("Nombre proveedor"), texto("Telefono"), texto("Correo"), texto("Direccion"));
                System.out.println("Procedimiento ejecutado. Use Listar para comprobar el resultado.");
                break;
            case 2:
                datos.actualizar(entero("ID de proveedor"), texto("Cedula juridica"), texto("Nombre proveedor"), texto("Telefono"), texto("Correo"), texto("Direccion"));
                System.out.println("Procedimiento ejecutado. Use Listar para comprobar el resultado.");
                break;
            case 3:
                if (!confirmar("¿Eliminar el registro?")) { return; }
                datos.eliminar(entero("ID de proveedor"));
                System.out.println("Procedimiento ejecutado. Use Listar para comprobar el resultado.");
                break;
            case 4:
                datos.listar();
                break;
            case 0: break;
            default: System.out.println("Acción incorrecta.");
        }
    }

    private static void menuProductos(Connection conexion) throws SQLException {
        Productos datos = new Productos(conexion);
        System.out.println("\nProductos: 1. Insertar  2. Actualizar  3. Eliminar  4. Listar  5. Productos bajo el mínimo  0. Volver");
        int accion = entero("Acción");
        switch (accion) {
            case 1:
                datos.insertar(texto("Codigo producto"), texto("Nombre producto"), entero("ID de categoría"), entero("ID de proveedor"), decimal("Precio venta"), entero("Stock actual"), entero("Stock minimo"));
                System.out.println("Procedimiento ejecutado. Use Listar para comprobar el resultado.");
                break;
            case 2:
                datos.actualizar(entero("Id producto"), texto("Codigo producto"), texto("Nombre producto"), entero("ID de categoría"), entero("ID de proveedor"), decimal("Precio venta"), entero("Stock actual"), entero("Stock minimo"));
                System.out.println("Procedimiento ejecutado. Use Listar para comprobar el resultado.");
                break;
            case 3:
                if (!confirmar("¿Eliminar el registro?")) { return; }
                datos.eliminar(entero("Id producto"));
                System.out.println("Procedimiento ejecutado. Use Listar para comprobar el resultado.");
                break;
            case 4:
                datos.listar();
                break;
            case 5:
                datos.consultarBajoMinimo();
                break;
            case 0: break;
            default: System.out.println("Acción incorrecta.");
        }
    }
}
