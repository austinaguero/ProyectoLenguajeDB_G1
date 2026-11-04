package cr.ac.fidelitas.favesa;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class Clientes {
    private final Connection conexion;

    public Clientes(Connection conexion) {
        this.conexion = conexion;
    }

    public void insertar(String identificacion, String nombreCliente, String telefono, String correo, String direccion) throws SQLException {
        try (CallableStatement llamada = conexion.prepareCall("{call sp_insertar_cliente(?, ?, ?, ?, ?)}")) {
            llamada.setString(1, identificacion);
            llamada.setString(2, nombreCliente);
            llamada.setString(3, telefono);
            llamada.setString(4, correo);
            llamada.setString(5, direccion);
            llamada.execute();
        }
    }

    public void actualizar(int idCliente, String identificacion, String nombreCliente, String telefono, String correo, String direccion) throws SQLException {
        try (CallableStatement llamada = conexion.prepareCall("{call sp_actualizar_cliente(?, ?, ?, ?, ?, ?)}")) {
            llamada.setInt(1, idCliente);
            llamada.setString(2, identificacion);
            llamada.setString(3, nombreCliente);
            llamada.setString(4, telefono);
            llamada.setString(5, correo);
            llamada.setString(6, direccion);
            llamada.execute();
        }
    }

    public void eliminar(int idCliente) throws SQLException {
        try (CallableStatement llamada = conexion.prepareCall("{call sp_eliminar_cliente(?)}")) {
            llamada.setInt(1, idCliente);
            llamada.execute();
        }
    }

    public void listar() throws SQLException {
        // El parámetro OUT recibe el cursor abierto en Oracle.
        try (CallableStatement llamada = conexion.prepareCall("{call sp_listar_clientes(?)}")) {
            llamada.registerOutParameter(1, Types.REF_CURSOR);
            llamada.execute();
            try (ResultSet filas = (ResultSet) llamada.getObject(1)) {
                Consultas.mostrar(filas);
            }
        }
    }
}
