package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.BeanLogin;
import connection.SingleConnection;

public class UsuarioDAO {

	private Connection conn;

	public UsuarioDAO() {
		conn = SingleConnection.getConnection();
	}

	public void insert(BeanLogin user) {
		String sql = "insert into public.user (login,senha,nome,fone) values (?,?,?,?)";
		PreparedStatement ps;

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getLogin());
			ps.setString(2, user.getSenha());
			ps.setString(3, user.getNome());
			ps.setString(4, user.getFone());
			ps.execute();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public List<BeanLogin> findAll() {
		List<BeanLogin> list = new ArrayList<>();

		try {
			String sql = "select * from public.user";

			PreparedStatement ps = conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				BeanLogin user = new BeanLogin();
				user.setId(rs.getLong("id"));
				user.setLogin(rs.getString("login"));
				user.setSenha(rs.getString("senha"));
				user.setNome(rs.getString("nome"));
				user.setFone(rs.getString("fone"));
				list.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public void delete(String id) {
		try {
			String sql = "delete from public.user where id = '" + id + "'";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.execute();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public BeanLogin findByName(String id) {
		String sql = "select * from public.user where id = '" + id + "'";
		PreparedStatement ps;
		
		try {
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				BeanLogin user = new BeanLogin();
				user.setId(rs.getLong("id"));
				user.setLogin(rs.getString("login"));
				user.setSenha(rs.getString("senha"));
				user.setNome(rs.getString("nome"));
				user.setFone(rs.getString("fone"));
				
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void update(BeanLogin user) {
		String sql = "update public.user set login = ?, senha = ?, nome = ?, fone = ? where id = " + user.getId();
		PreparedStatement ps;

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getLogin());
			ps.setString(2, user.getSenha());
			ps.setString(3, user.getNome());
			ps.setString(4, user.getFone());
			
			ps.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/* Validations */
	
	public boolean validateLogin(String login) {

		try {
			String sql = "select count(1) as qtd from public.user where login = '" + login + "'";

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				return rs.getInt("qtd") <= 0;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean validateLoginUpdate(String login, String id) {

		try {
			String sql = "select count(1) as qtd from public.user where login = '" + login + "' and id <> " + id;

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				return rs.getInt("qtd") <= 0;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean validatePassword(String password) {
		try {
			String sql = "select count(1) as qtd from public.user where senha = '" + password + "'";

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				return rs.getInt("qtd") <= 0;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean validatePasswordUpdate(String password, String id) {

		try {
			String sql = "select count(1) as qtd from public.user where senha = '" + password + "' and id <> " + id;

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				return rs.getInt("qtd") <= 0;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}