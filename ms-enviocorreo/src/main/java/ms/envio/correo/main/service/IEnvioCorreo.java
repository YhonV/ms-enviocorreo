package ms.envio.correo.main.service;

import ms.envio.correo.main.modelo.EntradaEnvioCorreo;

public interface IEnvioCorreo {
	boolean validarData(EntradaEnvioCorreo entrada);
	
	boolean enviarCorreo(EntradaEnvioCorreo entrada);
}
