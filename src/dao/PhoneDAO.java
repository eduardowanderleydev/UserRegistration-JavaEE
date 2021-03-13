package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.BeanPhone;
import connection.SingleConnection;

public class PhoneDAO {

	private Connection conn;

	public PhoneDAO() {
		conn = SingleConnection.getConnection();
	}

	public void insert(BeanPhone phone) {
		String sql = "INSERT INTO public.telefone (numero, tipo, usuario) VALUES (?,?,?);";

		try {
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, phone.getNumber());
			ps.setString(2, phone.getType());
			ps.setLong(3, phone.getUser());

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

	public List<BeanPhone> findAll(long user) {
		List<BeanPhone> list = new ArrayList<>();

		try {
			String sql = "select * from public.telefone where usuario = '" + user + "'";

			PreparedStatement ps = conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				BeanPhone phone = new BeanPhone();

				phone.setId(rs.getLong("id"));
				phone.setNumber(rs.getString("numero"));
				phone.setType(rs.getString("tipo"));
				phone.setUser(rs.getLong("usuario"));

				list.add(phone);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void delete(Long id) {
		try {
			String sql = "delete from public.telefone where id = '" + id + "'";
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

}
