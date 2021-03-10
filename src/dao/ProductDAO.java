package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.BeanProduct;
import connection.SingleConnection;

public class ProductDAO {

	private Connection conn;

	public ProductDAO() {
		conn = SingleConnection.getConnection();
	}

	public void insert(BeanProduct product) {
		String sql = "INSERT INTO public.product (nome, qtd, valor) VALUES (?,?,?);";

		try {
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, product.getName());
			ps.setInt(2, product.getQuantity());
			ps.setDouble(3, product.getPrice());

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

	public void delete(String id) {
		String sql = "delete from public.product where id = '" + id + "'";

		try {
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

	public List<BeanProduct> findAll() {
		String sql = "select * from public.product";
		List<BeanProduct> list = new ArrayList<BeanProduct>();

		try {
			PreparedStatement ps = conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				BeanProduct product = new BeanProduct();
				product.setId(rs.getLong("id"));
				product.setName(rs.getString("nome"));
				product.setPrice(rs.getDouble("valor"));
				product.setQuantity(rs.getInt("qtd"));
				list.add(product);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public BeanProduct findById(String id) {
		String sql = "SELECT id, nome, qtd, valor FROM public.product WHERE id = " + id;
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				BeanProduct product = new BeanProduct();
				product.setId(rs.getLong("id"));
				product.setName(rs.getString("nome"));
				product.setPrice(rs.getDouble("valor"));
				product.setQuantity(rs.getInt("qtd"));
				
				return product;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void update(BeanProduct product) {
		String sql = "update public.product set nome = ?, qtd = ?, valor = ? where id = " + product.getId();

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, product.getName());
			ps.setInt(2, product.getQuantity());
			ps.setDouble(3, product.getPrice());

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
	
	public boolean validateProductName(String name) {
		String sql = "select count (1) as qtd from public.product where nome = '" + name + "'";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				return rs.getInt("qtd") <= 0;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}