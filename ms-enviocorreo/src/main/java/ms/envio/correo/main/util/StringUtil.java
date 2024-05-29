package ms.envio.correo.main.util;

import java.text.Normalizer;

public class StringUtil {
	public static String caracteresEspeciales(String dato) {
	    return Normalizer.normalize(dato, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").replaceAll("\\p{M}", "");
	  }
}
