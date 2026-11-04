package cr.ac.fidelitas.favesa;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class Usuarios {
    private final Connection conexion;

    public Usuarios(Connection conexion) {
        this.conexion = conexion;
    }

    public void insertar(int idEmpleado, String username, String passwordHash, String estado) throws SQLException {
        try (CallableStatement llamada = conexion.prepareCall("{call sp_insertar_usuario(?, ?, ?, ?)}")) {
            llamada.setInt(1, idEmpleado);
            llamada.setString(2, username);
            llamada.setString(3, passwordHash);
            llamada.setString(4, estado);
            llamada.execute();
        }
    }

    public void actualizar(int idUsuario, String username, String passwordHash, String estado) throws SQLException {
        try (CallableStatement llamada = conexion.prepareCall("{call sp_actualizar_usuario(?, ?, ?, ?)}")) {
            llamada.setInt(1, idUsuario);
            llamada.setString(2, username);
            llamada.setString(3, passwordHash);
            llamada.setString(4, estado);
            llamada.execute();
        }
    }

    public void eliminar(int idUsuario) throws SQLException {
        try (CallableStatement llamada = conexion.prepareCall("{call sp_eliminar_usuario(?)}")) {
            llamada.setInt(1, idUsuario);
            llamada.execute();
        }
    }

    public void listar() throws SQLException {
        // El parámetro OUT recibe el cursor abierto en Oracle.
        try (CallableStatement llamada = conexion.prepareCall("{call sp_listar_usuarios(?)}")) {
            llamada.registerOutParameter(1, Types.REF_CURSOR);
            llamada.execute();
            try (ResultSet filas = (ResultSet) llamada.getObject(1)) {
                Consultas.mostrar(filas);
            }
        }
    }
}
