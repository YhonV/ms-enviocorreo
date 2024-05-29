package ms.envio.correo.main.modelo;

public class Adjunto {
	private String nombre;
	private String archivoBase64;
	private String extension;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getArchivoBase64() {
		return archivoBase64;
	}
	public void setArchivoBase64(String archivoBase64) {
		this.archivoBase64 = archivoBase64;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	@Override
	public String toString() {
		return "Adjunto [nombre=" + nombre + ", archivoBase64=" + archivoBase64 + ", extension=" + extension + "]";
	}
	
	
}
