package cr.ac.fidelitas.favesa;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Consultas {

    public static void mostrar(ResultSet filas) throws SQLException {
        ResultSetMetaData columnas = filas.getMetaData();
        int cantidad = columnas.getColumnCount();
        for (int i = 1; i <= cantidad; i++) {
            System.out.print(columnas.getColumnLabel(i) + " | ");
        }
        System.out.println();
        boolean hayDatos = false;
        while (filas.next()) {
            hayDatos = true;
            for (int i = 1; i <= cantidad; i++) {
                String valor = filas.getString(i);
                System.out.print((valor == null ? "" : valor) + " | ");
            }
            System.out.println();
        }
        if (!hayDatos) {
            System.out.println("No hay registros.");
        }
    }
}
