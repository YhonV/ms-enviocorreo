package ms.envio.correo.main.modelo;

public class RespuestaServicio {
	private int codigoRespuesta;
	  
	private String mensajeRespuesta;

	public int getCodigoRespuesta() {
		return codigoRespuesta;
	}

	public void setCodigoRespuesta(int codigoRespuesta) {
		this.codigoRespuesta = codigoRespuesta;
	}

	public String getMensajeRespuesta() {
		return mensajeRespuesta;
	}

	public void setMensajeRespuesta(String mensajeRespuesta) {
		this.mensajeRespuesta = mensajeRespuesta;
	}

	@Override
	public String toString() {
		return "RespuestaServicio [codigoRespuesta=" + codigoRespuesta + ", mensajeRespuesta=" + mensajeRespuesta + "]";
	}
	
	
}
