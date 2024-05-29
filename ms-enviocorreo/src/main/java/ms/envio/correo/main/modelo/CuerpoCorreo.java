package ms.envio.correo.main.modelo;

import java.util.Map;

public class CuerpoCorreo {
	private String contenido;
	private String tipo;
	private Map<String,String> imagenes;
	
	public String getContenido() {
		return contenido;
	}
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Map<String, String> getImagenes() {
		return imagenes;
	}
	public void setImagenes(Map<String, String> imagenes) {
		this.imagenes = imagenes;
	}
	
	@Override
	public String toString() {
		return "CuerpoCorreo [contenido=" + contenido + ", tipo=" + tipo + ", imagenes=" + imagenes + "]";
	}
	
	
}
