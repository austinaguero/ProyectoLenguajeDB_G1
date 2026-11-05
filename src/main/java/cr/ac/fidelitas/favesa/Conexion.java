package cr.ac.fidelitas.favesa;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexion {

    public static Connection conectar() throws IOException, SQLException {
        Properties datos = new Properties();
        try (FileInputStream archivo = new FileInputStream("config.properties")) {
            datos.load(archivo);
        }
        String url = datos.getProperty("db.url", "").trim();
        String usuario = datos.getProperty("db.usuario", "").trim();
        String password = datos.getProperty("db.password", "");
        String wallet = datos.getProperty("db.wallet", "").trim();
        if (url.isEmpty() || usuario.isEmpty() || password.isEmpty()
                || password.equals("CAMBIAR_CONTRASENA")) {
            throw new IOException("Complete la URL, el usuario y la contraseña en config.properties.");
        }
        if (!wallet.isEmpty()) {
            // Oracle busca tnsnames.ora y ojdbc.properties en esta carpeta.
            System.setProperty("oracle.net.tns_admin", wallet);
        }
        return DriverManager.getConnection(url, usuario, password);
    }
}
