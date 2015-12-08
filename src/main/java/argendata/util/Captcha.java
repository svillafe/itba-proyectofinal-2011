package argendata.util;

import javax.servlet.ServletRequest;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

public class Captcha {

	public static boolean check(ServletRequest servletRequest,String key, String challangeField, String responseField){
		
		String remoteAddress = servletRequest.getRemoteAddr();
		ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
		reCaptcha.setPrivateKey(key);
		ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(
				remoteAddress, challangeField, responseField);
		return reCaptchaResponse.isValid();
		
	}
} 
