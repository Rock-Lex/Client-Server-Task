package uebung3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// Library: org.xerial:sqlite-jdbc:3.39.4.1

public class task1 {
    public static void main(String[] args) throws SQLException {
        String url = "src/uebung3/db/meineDatenbank.db";

        createDB("jdbc:sqlite:" + url);

        try {
            Connection conn = connect("jdbc:sqlite:" + url);

            if (istTabelleLeer(conn)) {
                insertDatensatz(conn, "Max Mustermann");
                insertDatensatz(conn, "Max Mustermann");
                insertDatensatz(conn, "Max Mustermann");
            } else {
                System.out.println("Die Tabelle ist nicht leer. Keine Datensätze eingefügt.");
            }

            alleDatensaetzeAusgeben(conn);

            updateNameById(conn, 2, "Peter Parker");
            updateErstenNamen(conn, "Max Mustermann", "Benjamin Blümchen");
            updateName(conn, "Max Mustermann", "Hans Wurst");

            alleDatensaetzeAusgeben(conn);

            conn.close();
        } catch (SQLException e) {
            System.out.println("Ein Fehler ist aufgetreten: " + e.getMessage());
        }
    }

    public static void alleDatensaetzeAusgeben(Connection conn) {
        String sql = "SELECT id, name FROM meineTabelle";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");

                System.out.println(id + "\t" + name);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void updateErstenNamen(Connection conn, String alterName, String neuerName) {
        // SQL-Befehl für das Aktualisieren des ersten Datensatzes, der den alten Namen hat
        String sql = "UPDATE meineTabelle SET name = ? WHERE id = (SELECT id FROM meineTabelle WHERE name = ? ORDER BY id LIMIT 1)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, neuerName);
            pstmt.setString(2, alterName);

            // Führe das Update aus
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Erster Datensatz mit dem Namen '" + alterName + "' wurde erfolgreich zu '" + neuerName + "' aktualisiert.");
            } else {
                System.out.println("Kein Datensatz mit dem Namen '" + alterName + "' gefunden.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void updateName(Connection conn, String alterName, String neuerName) {
        String sql = "UPDATE meineTabelle SET name = ? WHERE name = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, neuerName);
            pstmt.setString(2, alterName);

            // Führe das Update aus
            int affectedRows = pstmt.executeUpdate();

            System.out.println(affectedRows + " Datensätze aktualisiert.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void updateNameById(Connection conn, int id, String neuerName) {
        String sql = "UPDATE meineTabelle SET name = ? WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, neuerName);
            pstmt.setInt(2, id);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Datensatz mit ID " + id + " wurde erfolgreich aktualisiert.");
            } else {
                System.out.println("Kein Datensatz mit der angegebenen ID gefunden.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static boolean istTabelleLeer(Connection conn) {
        String sql = "SELECT COUNT(*) FROM meineTabelle";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            return rs.next() && rs.getInt(1) == 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public static void insertDatensatz(Connection conn, String name) {
        String sql = "INSERT INTO meineTabelle(name) VALUES(?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);

            pstmt.executeUpdate();

            System.out.println("Datensatz erfolgreich eingefügt.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " name TEXT NOT NULL\n"
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