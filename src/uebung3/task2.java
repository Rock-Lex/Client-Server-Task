package uebung3;

import java.sql.*;
import java.util.Scanner;

// CREATE DATABASE 'F:\\Programmieren\\Java\\ClientServerTask\\src\\uebung3\\db\\task2db.fdb' USER 'SYSDBA' Password 'masterkey';
// CONNECT 'F:\\Programmieren\\Java\\ClientServerTask\\src\\uebung3\\db\\task2db.fdb' USER 'SYSDBA' Password 'masterkey';
// org.firebirdsql.jdbc:jaybird-jdk18:4.0.10.java8

/* 

Bevor ich mich mit dem Java Code mit der Firebase Datenbank verbinden konnte, musste ich das Passwort ändern. Ich weiß nicht wieso da ich das offizielle Windows
Standardpasswort verwendet habe.

Ohne GUI war es notwendig mehrere, vermutlich unnötige Abfragen zu machen, um den aktuellen Stand der Datenbank zu überprüfen.

 */

public class task2 {
    public static void main(String[] args) {
        ResultSet rs = null;
        Scanner scanner = new Scanner(System.in);

        try {
            Connection conn = connect();
            if (conn != null) {
                while(true){
                    System.out.println("Was möchtest du tun?");
                    System.out.println("1. Select*");
                    System.out.println("2. Compare Prepared / Not Prepared");
                    System.out.println("3. Select* Where");
                    System.out.println("4. Daten einfügen");
                    System.out.println("5. Daten ändern");
                    System.out.println("6. Daten löschen");

                    System.out.println("0. Verbindung beenden");
                    switch (scanner.nextInt()){
                        case 0:
                            conn.close();
                            return;
                        case 1:
                            rs = selectAll(conn);
                            printResultSet(rs);
                            break;
                        case 2:
                            long startTimePrepared = System.nanoTime();
                            ResultSet rsPrepared = selectAllPrepared(conn);
                            long endTimePrepared = System.nanoTime();
                            printResultSet(rsPrepared);
                            long startTimeNichtPrepared = System.nanoTime();
                            ResultSet rsNichtPrepared = selectAllNonPrepared(conn);
                            long endTimeNichtPrepared = System.nanoTime();
                            printResultSet(rsNichtPrepared);

                            System.out.println();
                            System.out.println("Dauer mit Prepared Statement bei Select*: " + (endTimePrepared - startTimePrepared) + " Nanosekunden");
                            System.out.println("Dauer ohne Prepared Statement bei Select*: " + (endTimeNichtPrepared - startTimeNichtPrepared) + " Nanosekunden");

                            if ((endTimePrepared - startTimePrepared) < (endTimeNichtPrepared - startTimeNichtPrepared)) {
                                System.out.println("Das Select* mit Prepared Statement war schneller.");
                            } else if ((endTimePrepared - startTimePrepared) > (endTimeNichtPrepared - startTimeNichtPrepared)) {
                                System.out.println("Das Select* ohne Prepared Statement war schneller.");
                            } else {
                                System.out.println("Beide Methoden hatten die gleiche Ausführungsdauer.");
                            }

                            startTimePrepared = System.nanoTime();
                            insertPrepared(conn, "Pre", "pared", 1);
                            endTimePrepared = System.nanoTime();
                            startTimeNichtPrepared = System.nanoTime();
                            insertNonPrepared(conn, "Nicht", "Prepared", 2);
                            endTimeNichtPrepared = System.nanoTime();

                            System.out.println();
                            System.out.println("Dauer mit Prepared Statement bei Insert: " + (endTimePrepared - startTimePrepared) + " Nanosekunden");
                            System.out.println("Dauer ohne Prepared Statement bei Insert: " + (endTimeNichtPrepared - startTimeNichtPrepared) + " Nanosekunden");

                            if ((endTimePrepared - startTimePrepared) < (endTimeNichtPrepared - startTimeNichtPrepared)) {
                                System.out.println("Das Insert mit Prepared Statement war schneller.");
                            } else if ((endTimePrepared - startTimePrepared) > (endTimeNichtPrepared - startTimeNichtPrepared)) {
                                System.out.println("Das Inset ohne Prepared Statement war schneller.");
                            } else {
                                System.out.println("Beide Methoden hatten die gleiche Ausführungsdauer.");
                            }

                            startTimePrepared = System.nanoTime();
                            updatePrepared(conn, 3, "Pre", "pared", 25); // Aktualisiere Datensatz mit ID 3
                            endTimePrepared = System.nanoTime();
                            startTimeNichtPrepared = System.nanoTime();
                            updateNonPrepared(conn, 4, "Nicht", "Prepared", 30); // Aktualisiere Datensatz mit ID 4
                            endTimeNichtPrepared = System.nanoTime();

                            System.out.println();
                            System.out.println("Dauer mit Prepared Statement bei Update: " + (endTimePrepared - startTimePrepared) + " Nanosekunden");
                            System.out.println("Dauer ohne Prepared Statement bei Update: " + (endTimeNichtPrepared - startTimeNichtPrepared) + " Nanosekunden");

                            if ((endTimePrepared - startTimePrepared) < (endTimeNichtPrepared - startTimeNichtPrepared)) {
                                System.out.println("Das Update mit Prepared Statement war schneller.");
                            } else if ((endTimePrepared - startTimePrepared) > (endTimeNichtPrepared - startTimeNichtPrepared)) {
                                System.out.println("Das Update ohne Prepared Statement war schneller.");
                            } else {
                                System.out.println("Beide Methoden hatten die gleiche Ausführungsdauer.");
                            }
                        case 3:
                            System.out.println("Noch zu machen");
                            break;
                        case 4:
                            System.out.println("Nachname:");
                            String lastname = scanner.next();
                            System.out.println("Vorname:");
                            String firstname = scanner.next();
                            System.out.println("Alter:");
                            int age = scanner.nextInt();

                            insert(conn, lastname,firstname,age);
                            break;
                        case 5:
                            System.out.println("Noch zu machen");
                            break;
                        case 6:
                            System.out.println("ID:");
                            int delID = scanner.nextInt();
                            deleteById(conn, delID);
                            break;

                        default:
                            return;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Schließen der Verbindung.");
            e.printStackTrace();
        }
    }
    private static void updatePrepared(Connection conn, int id, String lastName, String firstName, int age) throws SQLException {
        String updateSQL = "UPDATE persons SET LastName = ?, FirstName = ?, Age = ? WHERE ID = ?";
        PreparedStatement pstmt = conn.prepareStatement(updateSQL);

        pstmt.setString(1, lastName);
        pstmt.setString(2, firstName);
        pstmt.setInt(3, age);
        pstmt.setInt(4, id);

        int affectedRows = pstmt.executeUpdate();
        System.out.println("Anzahl der aktualisierten Zeilen: " + affectedRows);

        pstmt.close();
    }
    private static void updateNonPrepared(Connection conn, int id, String lastName, String firstName, int age) throws SQLException {
        String updateSQL = "UPDATE persons SET LastName = '" + lastName + "', FirstName = '" + firstName + "', Age = " + age + " WHERE ID = " + id;

        Statement stmt = conn.createStatement();
        int affectedRows = stmt.executeUpdate(updateSQL);
        System.out.println("Anzahl der aktualisierten Zeilen: " + affectedRows);

        stmt.close();
    }

    private static ResultSet selectAll(Connection conn) throws SQLException {
        String selectSQL = "SELECT * FROM persons";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(selectSQL);
        return rs;
    }
    private static ResultSet selectAllPrepared(Connection conn) throws SQLException {
        String selectSQL = "SELECT * FROM persons";
        PreparedStatement pstmt = conn.prepareStatement(selectSQL);
        ResultSet rs = pstmt.executeQuery();
        return rs;
    }

    private static ResultSet selectAllNonPrepared(Connection conn) throws SQLException {
        String selectSQL = "SELECT * FROM persons";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(selectSQL);
        return rs;
    }



    private static void update(Connection conn) throws SQLException {
        String updateSQL = "UPDATE persons SET Age = ? WHERE ID = ?";
        PreparedStatement pstmtUpdate = conn.prepareStatement(updateSQL);
        pstmtUpdate.setInt(1, 31);
        pstmtUpdate.setInt(2, 1);
        pstmtUpdate.executeUpdate();
    }
    private static void insertNonPrepared(Connection conn, String lastName, String firstName, int age) throws SQLException {
        // Zuerst die aktuell höchste ID ermitteln
        String highestIdQuery = "SELECT MAX(ID) FROM persons";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(highestIdQuery);
        int highestId = 0;
        if (rs.next()) {
            highestId = rs.getInt(1);
        }
        rs.close();
        stmt.close();

        int newId = highestId + 1;

        String insertSQL = "INSERT INTO persons (ID, LastName, FirstName, Age) VALUES (" + newId + ", '" + lastName + "', '" + firstName + "', " + age + ")";

        stmt = conn.createStatement();
        stmt.executeUpdate(insertSQL);
        stmt.close();
    }
    private static void insertPrepared(Connection conn, String lastName, String firstName, int age) throws SQLException {
        // Zuerst die aktuell höchste ID ermitteln
        String highestIdQuery = "SELECT MAX(ID) FROM persons";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(highestIdQuery);
        int highestId = 0;
        if (rs.next()) {
            highestId = rs.getInt(1);
        }
        rs.close();
        stmt.close();

        int newId = highestId + 1; // Die neue ID ist die höchste ID plus 1
        String insertSQL = "INSERT INTO persons (ID, LastName, FirstName, Age) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(insertSQL);
        pstmt.setInt(1, newId);
        pstmt.setString(2, lastName);
        pstmt.setString(3, firstName);
        pstmt.setInt(4, age);
        pstmt.executeUpdate();
        pstmt.close();
    }

    private static void insert(Connection conn, String lastName, String firstName, int age) throws SQLException {
        // Zuerst die aktuell höchste ID ermitteln
        String highestIdQuery = "SELECT MAX(ID) FROM persons";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(highestIdQuery);
        int highestId = 0;
        if (rs.next()) {
            highestId = rs.getInt(1);
        }
        rs.close();
        stmt.close();

        int newId = highestId + 1; // Die neue ID ist die höchste ID plus 1
        String insertSQL = "INSERT INTO persons (ID, LastName, FirstName, Age) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(insertSQL);
        pstmt.setInt(1, newId);
        pstmt.setString(2, lastName);
        pstmt.setString(3, firstName);
        pstmt.setInt(4, age);
        pstmt.executeUpdate();
        pstmt.close();
    }
    private static void deleteById(Connection conn, int id) throws SQLException {
        String deleteSQL = "DELETE FROM persons WHERE ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Datensatz mit ID " + id + " wurde erfolgreich gelöscht.");
            } else {
                System.out.println("Kein Datensatz mit ID " + id + " gefunden.");
            }
        } catch (SQLException e) {
            System.err.println("SQL-Fehler beim Löschen des Datensatzes: " + e.getMessage());
            throw e;
        }
    }

    private static void printResultSet(ResultSet rs) throws SQLException {
        if (rs == null) {
            System.out.println("Das ResultSet ist leer oder wurde nicht initialisiert.");
            return;
        }

        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            if (i > 1) System.out.print(",  ");
            System.out.print(rsmd.getColumnName(i));
        }
        System.out.println();

        while (rs.next()) {
            for (int i = 1; i <= columnCount; i++) {
                if (i > 1) System.out.print(",  ");
                System.out.print(rs.getString(i));
            }
            System.out.println();
        }
    }

    private static Connection connect(){
        String projectPath = System.getProperty("user.dir");
        System.out.println(projectPath);
        String modifiedProjectPath = projectPath.replace("\\", "/");
        System.out.println(modifiedProjectPath);

        String dbPath = modifiedProjectPath + "/src/uebung3/db/task2db.FDB";
        System.out.println(dbPath);

        String url = "jdbc:firebirdsql:localhost/3050:" + dbPath + "?encoding=UTF8";

        System.out.println(url);

        String user = "SYSDBA";
        String password = "masterkey";

        System.out.println("conn");
        Connection conn=null;

        try {
            Class.forName("org.firebirdsql.jdbc.FBDriver");
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Verbindung erfolgreich hergestellt.");
        } catch (ClassNotFoundException e) {
            System.out.println("Firebird JDBC-Treiber nicht gefunden.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Verbindung fehlgeschlagen.");
            e.printStackTrace();
        }

        return conn;
    }
}
