package uebung3;

import java.sql.*;

public class JDBCTestClient {

	// A. Schmietendorf � HWR Berlin - SoSe2020
	// JDBC-Testbeispiel - Vorlesung Programmierung C/S-Systeme
	// Achtung der Client setzt ein laufende RDBMS Firebird voraus
	// ebenso die Nutzung der Beispieldatenbank "employee.fdb"

	private Connection con;
	private Statement stm;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new JDBCTestClient().access();
	}

	public void access() {

		String sUser = "sysdba";
		String sPawd = "masterkey";

		String sDBDriver = "org.firebirdsql.jdbc.FBDriver";
		
		//Achtung: Details f�r neuere Versionen siehe im Handbuch unter folgender URL:
		//https://firebirdsql.github.io/jaybird-manual/jaybird_manual.html#connection-drivermanager
		
		String sDBUrl = "jdbc:firebirdsql:localhost/3050:" +
				"C:/Programme/Firebird/Firebird_1_5/examples/employee.fdb";
		
		

		try {
			Class.forName(sDBDriver);
		} catch (ClassNotFoundException ex) {
			System.out.println("Class.forName : " + ex.getMessage());
		}

		try {

			con = DriverManager.getConnection(sDBUrl, sUser, sPawd);

			stm = con.createStatement();
			
			//Achtung nach dem ersten Eintrag kommt es zur Prim�rschl�sselverletzung
			stm.executeUpdate("INSERT INTO COUNTRY VALUES ('Frankreich','Euro')");

			System.out.println("Daten erfolgreich in die DB eingetragen");

			System.out.println("Daten aus der Datenbanktabelle auslesen");

			ResultSet rs = stm.executeQuery("SELECT * FROM country");

			while (rs.next()) {
				String country = rs.getString(1);
				String currency = rs.getString(2);
				System.out.println(country + " " + currency);
			}

			System.out.println("Ende der Datenausgabe - A. Schmietendorf");

			// Ressourcenffreigabe

			rs.close();
			con.close();

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}
}
