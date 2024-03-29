package uebung3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.File;

// Library: org.xerial:sqlite-jdbc:3.39.4.1

public class task1 {
    public static void main(String[] args) throws SQLException {
        String url = "src/uebung3/db/meineDatenbank.db";

        File dbFile = new File(url);
        if (!dbFile.exists()) {
            createDB("jdbc:sqlite:" + url);
            System.out.println("Datenbank wurde erstellt.");
        } else {
            System.out.println("Datenbank existiert bereits.");
        }

        try {
            Connection conn = connect("jdbc:sqlite:" + url);
            conn.close();
        } catch (SQLException e) {
            System.out.println("Ein Fehler ist aufgetreten: " + e.getMessage());
        }
    }


    public static Connection connect(String url) throws SQLException{

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Verbindung zur Datenbank erfolgreich hergestellt.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
        return conn;
    }
    public static void createDB(String url){
        String sql = "CREATE TABLE IF NOT EXISTS meineTabelle (\n"
                + " id integer PRIMARY KEY,\n"
                + " name text NOT NULL\n"
                + ");";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabelle wurde erstellt.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}