package cr.ac.fidelitas.favesa;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class Categorias {
    private final Connection conexion;

    public Categorias(Connection conexion) {
        this.conexion = conexion;
    }

    public void insertar(String nombreCategoria) throws SQLException {
        try (CallableStatement llamada = conexion.prepareCall("{call sp_insertar_categoria(?)}")) {
            llamada.setString(1, nombreCategoria);
            llamada.execute();
        }
    }

    public void actualizar(int idCategoria, String nombreCategoria) throws SQLException {
        try (CallableStatement llamada = conexion.prepareCall("{call sp_actualizar_categoria(?, ?)}")) {
            llamada.setInt(1, idCategoria);
            llamada.setString(2, nombreCategoria);
            llamada.execute();
        }
    }

    public void eliminar(int idCategoria) throws SQLException {
        try (CallableStatement llamada = conexion.prepareCall("{call sp_eliminar_categoria(?)}")) {
            llamada.setInt(1, idCategoria);
            llamada.execute();
        }
    }

    public void listar() throws SQLException {
        // El parámetro OUT recibe el cursor abierto en Oracle.
        try (CallableStatement llamada = conexion.prepareCall("{call sp_listar_categorias(?)}")) {
            llamada.registerOutParameter(1, Types.REF_CURSOR);
            llamada.execute();
            try (ResultSet filas = (ResultSet) llamada.getObject(1)) {
                Consultas.mostrar(filas);
            }
        }
    }
}
