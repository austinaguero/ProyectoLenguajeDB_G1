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

    public void insertar(String numeroFactura, int idCliente, int idUsuario, int idMetodoPago) throws SQLException {
        // La factura comienza vacía. Los detalles determinarán sus montos.
        double impuesto = calcularIva(0);
        double total = calcularTotal(0);
        try (CallableStatement llamada = conexion.prepareCall("{call sp_insertar_factura(?, ?, ?, ?, ?, ?, ?)}")) {
            llamada.setString(1, numeroFactura);
            llamada.setInt(2, idCliente);
            llamada.setInt(3, idUsuario);
            llamada.setInt(4, idMetodoPago);
            llamada.setDouble(5, 0);
            llamada.setDouble(6, impuesto);
            llamada.setDouble(7, total);
            llamada.execute();
        }
    }

    public void actualizar(int idFactura, String numeroFactura, int idCliente, int idUsuario, int idMetodoPago) throws SQLException {
        // El estado se cambia únicamente mediante anular().
        try (CallableStatement llamada = conexion.prepareCall("{call sp_actualizar_factura(?, ?, ?, ?, ?)}")) {
            llamada.setInt(1, idFactura);
            llamada.setString(2, numeroFactura);
            llamada.setInt(3, idCliente);
            llamada.setInt(4, idUsuario);
            llamada.setInt(5, idMetodoPago);
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

    public void agregarDetalle(int idFactura, int idProducto, int cantidad) throws SQLException {
        try (CallableStatement llamada = conexion.prepareCall("{call sp_agregar_detalle_factura(?, ?, ?)}")) {
            llamada.setInt(1, idFactura);
            llamada.setInt(2, idProducto);
            llamada.setInt(3, cantidad);
            llamada.execute();
        }
    }

    public double calcularIva(double subtotal) throws SQLException {
        try (CallableStatement llamada = conexion.prepareCall("{? = call fn_calcular_iva(?)}")) {
            llamada.registerOutParameter(1, Types.NUMERIC);
            llamada.setDouble(2, subtotal);
            llamada.execute();
            return llamada.getDouble(1);
        }
    }

    public double calcularTotal(double subtotal) throws SQLException {
        try (CallableStatement llamada = conexion.prepareCall("{? = call fn_calcular_total_factura(?)}")) {
            llamada.registerOutParameter(1, Types.NUMERIC);
            llamada.setDouble(2, subtotal);
            llamada.execute();
            return llamada.getDouble(1);
        }
    }

    public void consultarDetalle() throws SQLException {
        try (CallableStatement llamada = conexion.prepareCall("{call sp_consultar_detalle_facturas(?)}")) {
            llamada.registerOutParameter(1, Types.REF_CURSOR);
            llamada.execute();
            try (ResultSet filas = (ResultSet) llamada.getObject(1)) {
                Consultas.mostrar(filas);
            }
        }
    }
}
