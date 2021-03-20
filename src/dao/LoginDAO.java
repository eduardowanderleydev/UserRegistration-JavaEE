package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.SingleConnection;

public class LoginDAO {

	private Connection conn;

	public LoginDAO() {
		conn = SingleConnection.getConnection();
	}

	public boolean validaLoginSenha(String login, String senha) throws SQLException {
		
		String sql = "select * from public.user where login = '" + login + "' and senha = '" + senha + "'";

		PreparedStatement ps = conn.prepareStatement(sql);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			return true;
		} else {
			return false;
		}
	}
}