package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.BeanLogin;
import connection.SingleConnection;

public class UserDAO {

	private Connection conn;

	public UserDAO() {
		conn = SingleConnection.getConnection();
	}

	public void insert(BeanLogin user) {
		String sql = "insert into public.user (login,senha,nome,fone,cep,rua,bairro,cidade,estado,ibge,fotobase64, tipoconteudo,curriculobase64,tipoconteudo_curriculo,fotominiaturabase64, ativo) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement ps;

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getLogin());
			ps.setString(2, user.getSenha());
			ps.setString(3, user.getNome());
			ps.setString(4, user.getFone());
			ps.setString(5, user.getCep());
			ps.setString(6, user.getRua());
			ps.setString(7, user.getBairro());
			ps.setString(8, user.getCidade());
			ps.setString(9, user.getEstado());
			ps.setString(10, user.getIbge());
			ps.setString(11, user.getPhotoBase64());
			ps.setString(12, user.getContentType());
			ps.setString(13, user.getCurriculumBase64());
			ps.setString(14, user.getCurriculumContentType());
			ps.setString(15, user.getPhotoBase64Miniature());
			ps.setBoolean(16, user.isActive());
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
			String sql = "select * from public.user where login <> 'admin'";

			PreparedStatement ps = conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				BeanLogin user = new BeanLogin();
				user.setId(rs.getLong("id"));
				user.setLogin(rs.getString("login"));
				user.setSenha(rs.getString("senha"));
				user.setNome(rs.getString("nome"));
				user.setFone(rs.getString("fone"));
				user.setCep(rs.getString("cep"));
				user.setRua(rs.getString("rua"));
				user.setBairro(rs.getString("bairro"));
				user.setCidade(rs.getString("cidade"));
				user.setEstado(rs.getString("estado"));
				user.setIbge(rs.getString("ibge"));
				// user.setPhotoBase64(rs.getString("fotobase64"));
				user.setContentType(rs.getString("tipoconteudo"));
				user.setCurriculumBase64(rs.getString("curriculobase64"));
				user.setCurriculumContentType(rs.getString("tipoconteudo_curriculo"));
				user.setPhotoBase64Miniature(rs.getString("fotominiaturabase64"));
				list.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public void delete(String id) {
		try {
			String sql = "delete from public.user where id = '" + id + "' and login <> 'admin'";
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

	public BeanLogin findById(String id) {
		String sql = "select * from public.user where id = '" + id + "'  and login <> 'admin'";
		PreparedStatement ps;

		try {
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				BeanLogin user = new BeanLogin();
				user.setId(rs.getLong("id"));
				user.setLogin(rs.getString("login"));
				user.setSenha(rs.getString("senha"));
				user.setNome(rs.getString("nome"));
				user.setFone(rs.getString("fone"));
				user.setCep(rs.getString("cep"));
				user.setRua(rs.getString("rua"));
				user.setBairro(rs.getString("bairro"));
				user.setCidade(rs.getString("cidade"));
				user.setEstado(rs.getString("estado"));
				user.setIbge(rs.getString("ibge"));
				user.setPhotoBase64(rs.getString("fotobase64"));
				user.setContentType(rs.getString("tipoconteudo"));
				user.setCurriculumBase64(rs.getString("curriculobase64"));
				user.setCurriculumContentType(rs.getString("tipoconteudo_curriculo"));
				user.setPhotoBase64Miniature(rs.getString("fotominiaturabase64"));
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void update(BeanLogin user) {

		StringBuilder sql = new StringBuilder();

		sql.append("update public.user set login = ?, senha = ?, nome = ?, fone = ?, cep = ?, rua = ?, ");
		sql.append("bairro = ?, cidade = ?, estado = ?, ibge = ?, ativo = ? ");

		if (user.isUpdateImage()) {
			sql.append(", fotobase64 = ?, tipoconteudo = ?");
		}

		if (user.isUpdateCurriculum()) {
			sql.append(", curriculobase64 = ?, tipoconteudo_curriculo = ?");
		}

		if (user.isUpdateImage()) {
			sql.append(", fotominiaturabase64 = ? ");
		}
		sql.append(" where id = " + user.getId());

		PreparedStatement ps;

		try {
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, user.getLogin());
			ps.setString(2, user.getSenha());
			ps.setString(3, user.getNome());
			ps.setString(4, user.getFone());
			ps.setString(5, user.getCep());
			ps.setString(6, user.getRua());
			ps.setString(7, user.getBairro());
			ps.setString(8, user.getCidade());
			ps.setString(9, user.getEstado());
			ps.setString(10, user.getIbge());
			ps.setBoolean(11, user.isActive());

			if (user.isUpdateImage()) {
				ps.setString(12, user.getPhotoBase64());
				ps.setString(13, user.getContentType());
			}

			if (user.isUpdateCurriculum()) {
				if (!user.isUpdateImage()) {
					ps.setString(12, user.getCurriculumBase64());
					ps.setString(13, user.getCurriculumContentType());
				} else {
					ps.setString(14, user.getCurriculumBase64());
					ps.setString(15, user.getCurriculumContentType());
				}
			}

			if (user.isUpdateImage()) {
				if (!user.isUpdateCurriculum()) {
					ps.setString(14, user.getPhotoBase64Miniature());
				} else {
					ps.setString(16, user.getPhotoBase64Miniature());
				}
			}
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