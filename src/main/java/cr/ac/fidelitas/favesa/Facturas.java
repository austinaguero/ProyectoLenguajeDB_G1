package cr.ac.fidelitas.favesa;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class Facturas {
    private final Connection conexion;

    public Facturas(Connection conexion) {
        this.conexion = conexion;
    }

    public void insertar(String numeroFactura, int idCliente, int idUsuario, int idMetodoPago, double subtotal, double impuesto, double montoTotal) throws SQLException {
        try (CallableStatement llamada = conexion.prepareCall("{call sp_insertar_factura(?, ?, ?, ?, ?, ?, ?)}")) {
            llamada.setString(1, numeroFactura);
            llamada.setInt(2, idCliente);
            llamada.setInt(3, idUsuario);
            llamada.setInt(4, idMetodoPago);
            llamada.setDouble(5, subtotal);
            llamada.setDouble(6, impuesto);
            llamada.setDouble(7, montoTotal);
            llamada.execute();
        }
    }

    public void actualizar(int idFactura, String numeroFactura, int idCliente, int idUsuario, int idMetodoPago, double subtotal, double impuesto, double montoTotal, String estado) throws SQLException {
        try (CallableStatement llamada = conexion.prepareCall("{call sp_actualizar_factura(?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {
            llamada.setInt(1, idFactura);
            llamada.setString(2, numeroFactura);
            llamada.setInt(3, idCliente);
            llamada.setInt(4, idUsuario);
            llamada.setInt(5, idMetodoPago);
            llamada.setDouble(6, subtotal);
            llamada.setDouble(7, impuesto);
            llamada.setDouble(8, montoTotal);
            llamada.setString(9, estado);
            llamada.execute();
        }
    }

    public void eliminar(int idFactura) throws SQLException {
        try (CallableStatement llamada = conexion.prepareCall("{call sp_eliminar_factura(?)}")) {
            llamada.setInt(1, idFactura);
            llamada.execute();
        }
    }

    public void listar() throws SQLException {
        // El parámetro OUT recibe el cursor abierto en Oracle.
        try (CallableStatement llamada = conexion.prepareCall("{call sp_listar_facturas(?)}")) {
            llamada.registerOutParameter(1, Types.REF_CURSOR);
            llamada.execute();
            try (ResultSet filas = (ResultSet) llamada.getObject(1)) {
                Consultas.mostrar(filas);
            }
        }
    }

    public void anular(int idFactura) throws SQLException {
        try (CallableStatement llamada = conexion.prepareCall("{call sp_anular_factura(?)}")) {
            llamada.setInt(1, idFactura);
            llamada.execute();
        }
    }
}
