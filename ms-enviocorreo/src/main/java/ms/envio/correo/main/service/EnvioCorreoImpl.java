package ms.envio.correo.main.service;

import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.eclipse.angus.mail.smtp.SMTPTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.mail.Address;
import jakarta.mail.BodyPart;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.Session;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.util.ByteArrayDataSource;
import ms.envio.correo.main.modelo.Adjunto;
import ms.envio.correo.main.modelo.EntradaEnvioCorreo;
import ms.envio.correo.main.util.EntryPoint;
import ms.envio.correo.main.util.StringUtil;

public class EnvioCorreoImpl implements IEnvioCorreo{

	private static final Logger log = LoggerFactory.getLogger(EnvioCorreoImpl.class);
	
	@SuppressWarnings("unused")
	private static final String TIPO_TXT = "TXT";
	
	@SuppressWarnings("unused")
	private static final String TIPO_HTML = "HTML";

	@SuppressWarnings("unused")
	private static final String ADJUNTO_TIPO_PDF = "PDF";
	  
	@SuppressWarnings("unused")
	private static final String ADJUNTO_TIPO_ZIP = "ZIP";
	
	@Autowired
	private EntryPoint entryPoint;
	
	@Override
	public boolean validarData(EntradaEnvioCorreo entrada) {
		try {
			log.info("Ingresando a validación");
			
			if(entrada.getDestinatarios() == null &&
				entrada.getDestinatarios().isEmpty() ||
				entrada.getCuerpo() == null ||
				entrada.getAsunto().equals("")) {
				log.info("No cumples con los parametros de entrada");
				return false;
			}
		}catch (Exception e) {
			log.info("Error en validaciones de enrtada: "+e.getMessage());
		}
		log.info("Parametros completos");
		return true;
	}

	@Override
	public boolean enviarCorreo(EntradaEnvioCorreo entrada) {
		log.info("Comienza envío de correo");
		Properties props = System.getProperties();
		String host = entryPoint.getHost();
		String user = entryPoint.getUsernameMail();
		String pass = entryPoint.getPassword();
		String from = entryPoint.getFromMail();
		
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		
		Session session = Session.getDefaultInstance(props,null);
		MimeMessage message = new MimeMessage(session);
		
		try {
			log.info("Agregando datos en clase mensaje");
			log.info("Agregando from");
			message.setFrom((Address) new InternetAddress(from));
			log.info("Agregando destinatarios");
			for (String to : entrada.getDestinatarios()) {
				message.addRecipient(Message.RecipientType.TO, (Address) new InternetAddress(to));
			}
			log.info("Agregando subject");
			message.setSubject(entrada.getAsunto());
			log.info("Agregando fecha actual");
			message.setSentDate(new Date());
			log.info("Instanciando multipart");
			MimeMultipart mimeMultipart = new MimeMultipart();
			log.info("Instanciando Cuerpo Correo");
			Multipart multipart = agregarCuerpo(entrada, (Multipart)mimeMultipart);
		    multipart = agregarAdjuntos(entrada, multipart);
		    log.info("Seteando contenido del mensaje");
		    message.setContent(multipart);
		    log.info("Instanciando transporte SMTP");
		    SMTPTransport transporte = (SMTPTransport)session.getTransport("smtp");
			log.info("Conectando...");
			transporte.connect(host, user, pass);
			log.info("Enviando...");
			transporte.sendMessage((Message)message, message.getAllRecipients());
			log.info("Cerrando transporte");
			transporte.close();
			log.info("Final exitoso de envio de correo para destinatario(s) " + entrada.getDestinatarios().toString());
			return true;
		}catch (AddressException ae) {
			log.info(ae.getMessage());
			return false;
		}catch (MessagingException me) {
			log.info("MessagingException: "+ (Throwable)me);
			return false;
		}
	}
	
	
	private Multipart agregarCuerpo(EntradaEnvioCorreo entrada, Multipart multiPart) {
	    try {
	      if ("TXT".equals(entrada.getCuerpo().getTipo())) {
	        multiPart.addBodyPart(agregarCuerpoText(entrada));
	      } else if ("HTML".equals(entrada.getCuerpo().getTipo())) {
	        multiPart.addBodyPart(agregarCuerpoHtml(entrada));
	        Map<String, String> mapInlineImages = entrada.getCuerpo().getImagenes();
	        if (mapInlineImages != null && mapInlineImages.size() > 0) {
	          Set<String> setImageID = mapInlineImages.keySet();
	          for (String contentId : setImageID) {
	            log.info("Procesando imagen con content id: " + contentId);
	            MimeBodyPart imagePart = new MimeBodyPart();
	            imagePart.setHeader("Content-ID", "<" + contentId + ">");
	            imagePart.setDisposition("inline");
	            log.info("convirtiendo base64 a byte[]");
	            byte[] imgBytes = Base64.decodeBase64(mapInlineImages.get(contentId));
	            log.info("Seteando byte[] en datasource");
	            ByteArrayDataSource byteArrayDataSource = new ByteArrayDataSource(imgBytes, "image/*");
	            log.info("Seteando datasource en handler");
	            imagePart.setDataHandler(new DataHandler((DataSource)byteArrayDataSource));
	            imagePart.setFileName("contentId.png");
	            log.info("agregando datahandler en Multipart");
	            multiPart.addBodyPart((BodyPart)imagePart);
	          } 
	        } 
	      } 
	    } catch (MessagingException e) {
	      log.info("Error al agregar cuerpo txt a correo "+ (Throwable)e);
	    } 
	    return multiPart;
	  }
	
	
	private BodyPart agregarCuerpoText(EntradaEnvioCorreo entrada) {
	    MimeBodyPart mimeBodyPart = new MimeBodyPart();
	    log.info("Agregando text");
	    try {
	      mimeBodyPart.setText(entrada.getCuerpo().getContenido());
	    } catch (MessagingException e) {
	      log.info("Error al agregar cuerpo txt a correo "+ (Throwable)e);
	    } 
	    return (BodyPart)mimeBodyPart;
	  }
	
	private BodyPart agregarCuerpoHtml(EntradaEnvioCorreo entrada) {
	    MimeBodyPart mimeBodyPart = new MimeBodyPart();
	    log.info("Agregando html");
	    try {
	      mimeBodyPart.setContent(entrada.getCuerpo().getContenido(), "text/html; charset=utf-8");
	    } catch (MessagingException e) {
	      log.info("Error al agregar cuerpo html a correo "+ (Throwable)e);
	    } 
	    return (BodyPart)mimeBodyPart;
	  }
	
	private Multipart agregarAdjuntos(EntradaEnvioCorreo entrada, Multipart multipart) {
	    try {
	      log.info("Cantidad de archivos adjuntos a agregar : " + entrada.getAdjuntos().size());
	      for (Adjunto adjunto : entrada.getAdjuntos()) {
	        if ("PDF".equals(adjunto.getExtension())) {
	          multipart.addBodyPart(agregarPDF(adjunto));
	          continue;
	        } 
	        if ("ZIP".equals(adjunto.getExtension()))
	          multipart.addBodyPart(agregarZIP(adjunto)); 
	      } 
	    } catch (MessagingException me) {
	      log.info("Error MessagingException al agregar archivos adjuntos a correo "+ (Throwable)me);
	    } catch (Exception e) {
	      log.info("Error generico al agregar archivos adjuntos a correo "+ e);
	    } 
	    return multipart;
	  }
	
	
	private BodyPart agregarPDF(Adjunto adjunto) {
	    log.info("Instanciando BodyPart para adjunto PDF : " + adjunto.getNombre());
	    MimeBodyPart mimeBodyPart = new MimeBodyPart();
	    try {
	      log.info("Adjuntando documento " + adjunto.getNombre());
	      ByteArrayDataSource ds = new ByteArrayDataSource(Base64.decodeBase64(adjunto.getArchivoBase64()), "application/pdf");
	      mimeBodyPart.setDataHandler(new DataHandler((DataSource)ds));
	      mimeBodyPart.setFileName(StringUtil.caracteresEspeciales(adjunto.getNombre()) + ".pdf");
	      log.info("Se adjunto documento " + adjunto.getNombre());
	    } catch (MessagingException e) {
	      log.info("Error al agregar PDF como adjunto a correo "+ (Throwable)e);
	    } 
	    return (BodyPart)mimeBodyPart;
	  }
	
	private BodyPart agregarZIP(Adjunto adjunto) {
	    log.info("Instanciando BodyPart para adjunto ZIP :" + adjunto.getNombre());
	    MimeBodyPart mimeBodyPart = new MimeBodyPart();
	    try {
	      mimeBodyPart = new MimeBodyPart();
	      ByteArrayDataSource byteArrayDataSource = new ByteArrayDataSource(Base64.decodeBase64(adjunto.getArchivoBase64()), "application/zip");
	      mimeBodyPart.setDataHandler(new DataHandler((DataSource)byteArrayDataSource));
	      mimeBodyPart.setFileName(StringUtil.caracteresEspeciales(adjunto.getNombre()) + ".zip");
	      log.info("Se adjunto documento " + adjunto.getNombre());
	    } catch (MessagingException e) {
	    	log.info("Error al agregar ZIP como adjunto a correo "+ (Throwable)e);
	    } 
	    return (BodyPart)mimeBodyPart;
	  }

}
