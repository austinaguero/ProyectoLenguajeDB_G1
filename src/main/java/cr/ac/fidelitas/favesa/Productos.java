package cr.ac.fidelitas.favesa;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class Productos {
    private final Connection conexion;

    public Productos(Connection conexion) {
        this.conexion = conexion;
    }

    public void insertar(String codigoProducto, String nombreProducto, int idCategoria, int idProveedor, double precioVenta, int stockActual, int stockMinimo) throws SQLException {
        try (CallableStatement llamada = conexion.prepareCall("{call sp_insertar_producto(?, ?, ?, ?, ?, ?, ?)}")) {
            llamada.setString(1, codigoProducto);
            llamada.setString(2, nombreProducto);
            llamada.setInt(3, idCategoria);
            llamada.setInt(4, idProveedor);
            llamada.setDouble(5, precioVenta);
            llamada.setInt(6, stockActual);
            llamada.setInt(7, stockMinimo);
            llamada.execute();
        }
    }

    public void actualizar(int idProducto, String codigoProducto, String nombreProducto, int idCategoria, int idProveedor, double precioVenta, int stockActual, int stockMinimo) throws SQLException {
        try (CallableStatement llamada = conexion.prepareCall("{call sp_actualizar_producto(?, ?, ?, ?, ?, ?, ?, ?)}")) {
            llamada.setInt(1, idProducto);
            llamada.setString(2, codigoProducto);
            llamada.setString(3, nombreProducto);
            llamada.setInt(4, idCategoria);
            llamada.setInt(5, idProveedor);
            llamada.setDouble(6, precioVenta);
            llamada.setInt(7, stockActual);
            llamada.setInt(8, stockMinimo);
            llamada.execute();
        }
    }

    public void eliminar(int idProducto) throws SQLException {
        try (CallableStatement llamada = conexion.prepareCall("{call sp_eliminar_producto(?)}")) {
            llamada.setInt(1, idProducto);
            llamada.execute();
        }
    }

    public void listar() throws SQLException {
        // El parámetro OUT recibe el cursor abierto en Oracle.
        try (CallableStatement llamada = conexion.prepareCall("{call sp_listar_productos(?)}")) {
            llamada.registerOutParameter(1, Types.REF_CURSOR);
            llamada.execute();
            try (ResultSet filas = (ResultSet) llamada.getObject(1)) {
                Consultas.mostrar(filas);
            }
        }
    }

    public void consultarBajoMinimo() throws SQLException {
        try (CallableStatement llamada = conexion.prepareCall("{call sp_consultar_productos_bajo_minimo(?)}")) {
            llamada.registerOutParameter(1, Types.REF_CURSOR);
            llamada.execute();
            try (ResultSet filas = (ResultSet) llamada.getObject(1)) {
                Consultas.mostrar(filas);
            }
        }
    }
}
