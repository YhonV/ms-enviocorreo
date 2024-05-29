package ms.envio.correo.main.util;

import org.springframework.beans.factory.annotation.Value;

public class EntryPoint {
	
	@Value("${servidor_smtp}")
	private String host;
	
	@Value("${usuario_correo}")
	private String usernameMail;
	
	@Value("${pass_correo}")
	private String password;
	
	@Value("${puerto_correo}")
	private String portMail;
	
	@Value("${from_correo}")
	private String fromMail;
	
	private String rutaBase;
	
	private String entrypointMotorDocs;
	
	private String datasource;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUsernameMail() {
		return usernameMail;
	}

	public void setUsernameMail(String usernameMail) {
		this.usernameMail = usernameMail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPortMail() {
		return portMail;
	}

	public void setPortMail(String portMail) {
		this.portMail = portMail;
	}

	public String getFromMail() {
		return fromMail;
	}

	public void setFromMail(String fromMail) {
		this.fromMail = fromMail;
	}

	public String getRutaBase() {
		return rutaBase;
	}

	public void setRutaBase(String rutaBase) {
		this.rutaBase = rutaBase;
	}

	public String getEntrypointMotorDocs() {
		return entrypointMotorDocs;
	}

	public void setEntrypointMotorDocs(String entrypointMotorDocs) {
		this.entrypointMotorDocs = entrypointMotorDocs;
	}

	public String getDatasource() {
		return datasource;
	}

	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}

	@Override
	public String toString() {
		return "EntryPoint [host=" + host + ", usernameMail=" + usernameMail + ", password=" + password + ", portMail="
				+ portMail + ", fromMail=" + fromMail + ", rutaBase=" + rutaBase + ", entrypointMotorDocs="
				+ entrypointMotorDocs + ", datasource=" + datasource + "]";
	}
	
	
}
