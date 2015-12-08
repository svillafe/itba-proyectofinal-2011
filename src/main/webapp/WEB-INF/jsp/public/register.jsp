<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!-- reCaptcha things -->
<%@ page import="net.tanesha.recaptcha.ReCaptcha" %>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory" %>
<!--  end things -->


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Argendata - Registrate</title>
		<c:import url = "/WEB-INF/head.jsp" />
	</head>
	<body>
	<script type="text/javascript">
	var RecaptchaOptions = {
   		lang : 'es',
	};
	</script>
	<div class="container">
		<c:import url = "/WEB-INF/header.jsp" />

		<div id="cuerpo" class="span-24">
			<div id="breadcrumb" class= "prepend-1 span-22 append-1" >
				Registrar usuario >
			</div>
	
<div class="prepend-1 prepend-top append-1">

	<p style="margin-top:48px;">
		<span  class="notice"><c:out value="${msg}"/></span>
	</p>
	
	
  <h2>Registrate!</h2>
  	
  
	<form:form method="post" action="register"	commandName="userForm" class="myForm">
	
	<br />
	<fieldset>
	<legend>Complet&aacute; con tus datos</legend>
	<div class="clear"><p>Todos los campos son obligatorios.</p></div>
	<div class="prepend-1 span-8 append-1">	
	
	<label for="username">Username:</label>
	<br />
	<form:input path="username" />
	<br/>
	<small>Con este nombre se te identificar&aacute; dentro del sistema.</small>
	<br/><span class="error"><form:errors path="username" /></span><br/>
	<br />
	
	<label for="password">Contrase&ntilde;a:</label>
	<br />
	<form:input type="password" path="password" />
	<br/>
	<small>Tu contrase&ntilde;a.</small>
	<br/><span class="error"><form:errors path="password" /></span><br/>
	<br />
	
	<label for="rePassword">Reingrese contrase&ntilde;a:</label>
	<br />
	<form:input path="rePassword" type="password"/>
	<br/>
	<small>Ingres&aacute; nuevamente la contrase&ntilde;a para evitar errores.</small>
	<br/><span class="error"><form:errors path="rePassword" /></span><br/>
	<br />
	
	<label>Captcha:</label> <br />
		<%
          ReCaptcha c = ReCaptchaFactory.newReCaptcha((String)request.getAttribute("publicKey"), (String)request.getAttribute("privateKey"), false);
          out.print(c.createRecaptchaHtml(null, null));
        %>
        <small>Esto es solicitado para verificar que no se trata de un robot.</small>
        <p><br/></p>
    
	
	</div>
	<div class="prepend-1 span-8 append-1">
	
	<label for="email">Email:</label>
	<br />
	<form:input path="email" />
	<br/>
	<small>Tu email. Lo usaremos para poder enviarle su contrase&ntilde;a</small>
	<br/><span class="error"><form:errors path="email" /></span><br/>
	<br />
	
	
	<label for="name">Nombre:</label>
	<br />
	<form:input path="name" />
	<br/>
	<small>Tu nombre.</small>
	<br/><span class="error"><form:errors path="name" /></span><br/>
	<br />
	
	<label for="lastName">Apellido:</label>
	<br />
	<form:input path="lastName" />
	<br/>
	<small>Tu apellido.</small>
	<br/><span class="error"><form:errors path="lastName" /></span><br/>
	<br />
	
	</div>
	
	    
	<div class="clear">
		<input type="submit" value="Registrar" />
	</div>
	</fieldset>
</form:form>
<p><br/><br/></p>
<!--<div class="backToMainPage"><a href="../main/home">Volver a la pagina principal&nbsp;&nbsp;</a></div></div>--!>
  
  

</div>	
	
	<div style="clear:both"></div>
		
		<c:import url = "/WEB-INF/footer.jsp" />
	</div>
		
	
	</body>
</html>