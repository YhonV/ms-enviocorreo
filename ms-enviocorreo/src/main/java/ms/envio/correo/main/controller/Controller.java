package ms.envio.correo.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ms.envio.correo.main.modelo.EntradaEnvioCorreo;
import ms.envio.correo.main.modelo.RespuestaServicio;
import ms.envio.correo.main.service.EnvioCorreoImpl;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class Controller {
	
	private static final Logger log = LoggerFactory.getLogger(Controller.class);
	
	@Autowired
	private EnvioCorreoImpl helper;
	
	@RequestMapping(value = {"/envioCorreo"}, method = {RequestMethod.POST}, produces = {"application/json"})
	@ResponseBody
	public RespuestaServicio enviarCorrreo(@RequestBody EntradaEnvioCorreo entrada) throws SQLException {
	    log.info("Ingreso llamada con datos de entrada: " + entrada.toString());
	    RespuestaServicio respuesta = new RespuestaServicio();
	    if (!helper.validarData(entrada)) {
	        respuesta.setCodigoRespuesta(2);
	        respuesta.setMensajeRespuesta("Error datos de entrada");
	      } else if (!helper.enviarCorreo(entrada)) {
	        respuesta.setCodigoRespuesta(1);
	        respuesta.setMensajeRespuesta("Error al enviar e-mail");
	      } else {
	        respuesta.setCodigoRespuesta(0);
	        respuesta.setMensajeRespuesta("Envío mail correcto");
	      } 
	    return respuesta;
	  }
}
