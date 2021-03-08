package connection;

import java.sql.Connection;
import java.sql.DriverManager;

/* Responsável por realizar a conexão com o banco */

public class SingleConnection {

	private static String banco = "jdbc:postgresql://localhost:5432/postgres?autoReconnect=true";
	private static String password = "admin";
	private static String user = "postgres";
	
	private static Connection conn = null;
	
	static {
		conectar();
	}
	
	public SingleConnection () {
		conectar();
	}
	
	private static void conectar() {
		try {
			if (conn == null) {
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection(banco, user, password);
				conn.setAutoCommit(false);
			}
		} catch (Exception e) {
			throw new RuntimeException("Erro ao conectar com o banco de dados!"); 
		}
	}
	
	public static Connection getConnection() {
		return conn;
	}
	
}
