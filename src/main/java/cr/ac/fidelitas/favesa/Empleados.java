package cr.ac.fidelitas.favesa;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class Empleados {
    private final Connection conexion;

    public Empleados(Connection conexion) {
        this.conexion = conexion;
    }

    public void insertar(String cedula, String nombre, String primerApellido, String segundoApellido, String correo, String telefono) throws SQLException {
        try (CallableStatement llamada = conexion.prepareCall("{call sp_insertar_empleado(?, ?, ?, ?, ?, ?)}")) {
            llamada.setString(1, cedula);
            llamada.setString(2, nombre);
            llamada.setString(3, primerApellido);
            llamada.setString(4, segundoApellido);
            llamada.setString(5, correo);
            llamada.setString(6, telefono);
            llamada.execute();
        }
    }

    public void actualizar(int idEmpleado, String cedula, String nombre, String primerApellido, String segundoApellido, String correo, String telefono) throws SQLException {
        try (CallableStatement llamada = conexion.prepareCall("{call sp_actualizar_empleado(?, ?, ?, ?, ?, ?, ?)}")) {
            llamada.setInt(1, idEmpleado);
            llamada.setString(2, cedula);
            llamada.setString(3, nombre);
            llamada.setString(4, primerApellido);
            llamada.setString(5, segundoApellido);
            llamada.setString(6, correo);
            llamada.setString(7, telefono);
            llamada.execute();
        }
    }

    public void eliminar(int idEmpleado) throws SQLException {
        try (CallableStatement llamada = conexion.prepareCall("{call sp_eliminar_empleado(?)}")) {
            llamada.setInt(1, idEmpleado);
            llamada.execute();
        }
    }

    public void listar() throws SQLException {
        // El parámetro OUT recibe el cursor abierto en Oracle.
        try (CallableStatement llamada = conexion.prepareCall("{call sp_listar_empleados(?)}")) {
            llamada.registerOutParameter(1, Types.REF_CURSOR);
            llamada.execute();
            try (ResultSet filas = (ResultSet) llamada.getObject(1)) {
                Consultas.mostrar(filas);
            }
        }
    }
}
