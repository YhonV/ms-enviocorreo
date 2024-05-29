package ms.envio.correo.main.modelo;

import java.util.List;

public class EntradaEnvioCorreo {
	private String asunto;
	private List<String> destinatarios;
	private CuerpoCorreo cuerpo;
	private List<Adjunto> adjuntos;
	
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public List<String> getDestinatarios() {
		return destinatarios;
	}
	public void setDestinatarios(List<String> destinatarios) {
		this.destinatarios = destinatarios;
	}
	public CuerpoCorreo getCuerpo() {
		return cuerpo;
	}
	public void setCuerpo(CuerpoCorreo cuerpo) {
		this.cuerpo = cuerpo;
	}
	public List<Adjunto> getAdjuntos() {
		return adjuntos;
	}
	public void setAdjuntos(List<Adjunto> adjuntos) {
		this.adjuntos = adjuntos;
	}
	
	@Override
	public String toString() {
		return "EntradaEnvioCorreo [asunto=" + asunto + ", destinatarios=" + destinatarios + ", cuerpo=" + cuerpo
				+ ", adjuntos=" + adjuntos + "]";
	}
	
	
}
