package beans;

public class BeanLogin {

	private Long id;
	private String nome;
	private String sexo;
	private String fone;
	private String profile;
	private String login;
	private String senha;
	private String cep;
	private String rua;
	private String bairro;
	private String cidade;
	private String estado;
	private String ibge;
	private String photoBase64;
	private String photoBase64Miniature;
	private String contentType;
	private String curriculumBase64;
	private String curriculumContentType;
	private String tempPhotoUser;
	private boolean active;
	
	private boolean updateImage = true;
	private boolean updateCurriculum = true;
	
	public boolean isUpdateImage() {
		return updateImage;
	}
	
	public String getProfile() {
		return profile;
	}


	public void setProfile(String profile) {
		this.profile = profile;
	}


	public String getSexo() {
		return sexo;
	}


	public void setSexo(String sexo) {
		this.sexo = sexo;
	}


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}


	public void setUpdateImage(boolean updateImage) {
		this.updateImage = updateImage;
	}


	public boolean isUpdateCurriculum() {
		return updateCurriculum;
	}


	public void setUpdateCurriculum(boolean updateCurriculum) {
		this.updateCurriculum = updateCurriculum;
	}


	public String getPhotoBase64Miniature() {
		return photoBase64Miniature;
	}


	public void setPhotoBase64Miniature(String photoBase64Miniature) {
		this.photoBase64Miniature = photoBase64Miniature;
	}


	public String getTempPhotoUser() {
		tempPhotoUser = "data:" + this.contentType + ";base64," + photoBase64;
		return tempPhotoUser;
	}

	
	public String getCurriculumContentType() {
		return curriculumContentType;
	}

	public void setCurriculumContentType(String curriculumContentType) {
		this.curriculumContentType = curriculumContentType;
	}


	public String getCurriculumBase64() {
		return curriculumBase64;
	}


	public void setCurriculumBase64(String curriculumBase64) {
		this.curriculumBase64 = curriculumBase64;
	}

	public String getPhotoBase64() {
		return photoBase64;
	}

	public void setPhotoBase64(String photoBase64) {
		this.photoBase64 = photoBase64;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getIbge() {
		return ibge;
	}

	public void setIbge(String ibge) {
		this.ibge = ibge;
	}

	public Long getId() {
		return id;
	}

	public String getFone() {
		return fone;
	}

	public void setFone(String fone) {
		this.fone = fone;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
