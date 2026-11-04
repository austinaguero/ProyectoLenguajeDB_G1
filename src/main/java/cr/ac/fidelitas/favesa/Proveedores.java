package cr.ac.fidelitas.favesa;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class Proveedores {
    private final Connection conexion;

    public Proveedores(Connection conexion) {
        this.conexion = conexion;
    }

    public void insertar(String cedulaJuridica, String nombreProveedor, String telefono, String correo, String direccion) throws SQLException {
        try (CallableStatement llamada = conexion.prepareCall("{call sp_insertar_proveedor(?, ?, ?, ?, ?)}")) {
            llamada.setString(1, cedulaJuridica);
            llamada.setString(2, nombreProveedor);
            llamada.setString(3, telefono);
            llamada.setString(4, correo);
            llamada.setString(5, direccion);
            llamada.execute();
        }
    }

    public void actualizar(int idProveedor, String cedulaJuridica, String nombreProveedor, String telefono, String correo, String direccion) throws SQLException {
        try (CallableStatement llamada = conexion.prepareCall("{call sp_actualizar_proveedor(?, ?, ?, ?, ?, ?)}")) {
            llamada.setInt(1, idProveedor);
            llamada.setString(2, cedulaJuridica);
            llamada.setString(3, nombreProveedor);
            llamada.setString(4, telefono);
            llamada.setString(5, correo);
            llamada.setString(6, direccion);
            llamada.execute();
        }
    }

    public void eliminar(int idProveedor) throws SQLException {
        try (CallableStatement llamada = conexion.prepareCall("{call sp_eliminar_proveedor(?)}")) {
            llamada.setInt(1, idProveedor);
            llamada.execute();
        }
    }

    public void listar() throws SQLException {
        // El parámetro OUT recibe el cursor abierto en Oracle.
        try (CallableStatement llamada = conexion.prepareCall("{call sp_listar_proveedores(?)}")) {
            llamada.registerOutParameter(1, Types.REF_CURSOR);
            llamada.execute();
            try (ResultSet filas = (ResultSet) llamada.getObject(1)) {
                Consultas.mostrar(filas);
            }
        }
    }
}
