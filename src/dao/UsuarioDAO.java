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
		String sql = "insert into public.user (login,senha) values (?,?)";
		PreparedStatement ps;

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getLogin());
			ps.setString(2, user.getSenha());
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
				list.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public void delete(String login) {
		try {
			String sql = "delete from public.user where login = '" + login + "'";
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

	public BeanLogin findByName(String login) {
		String sql = "select * from public.user where login = '" + login + "'";
		PreparedStatement ps;
		
		try {
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				BeanLogin user = new BeanLogin();
				user.setId(rs.getLong("id"));
				user.setLogin(rs.getString("login"));
				user.setSenha(rs.getString("senha"));
				
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public void update(BeanLogin user) {
		String sql = "update public.user set login = ?, senha = ? where id = " + user.getId();
		PreparedStatement ps;

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getLogin());
			ps.setString(2, user.getSenha());
			
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
	
}