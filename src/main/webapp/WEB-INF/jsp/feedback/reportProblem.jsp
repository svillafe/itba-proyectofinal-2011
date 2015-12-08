<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!-- reCaptcha things -->
<%@ page import="net.tanesha.recaptcha.ReCaptcha" %>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory" %>
<!--  end things -->




<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Argendata - Reportar problema</title>
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
			<div id="breadcrumb" class= "prepend-1 span-22 append-1 " >
				FeedBack > Reportar un problema >
			</div>

		<div class="prepend-1 prepend-top append-1">
		<br />			
		<h2>Reportar un problema:</h2>
		<form:form action="reportProblem" method="post" commandName="reportProblemForm" class="myForm">
		<fieldset>
			<legend>Complet&aacute; los datos</legend>
		
		<form:errors path="*" />
		<br />
		<div class="append-1 prepend-1 span-16">
		
		<label>Tipo de Recurso:</label> <br />
		<form:input disabled="true" path="type"/> <br />
		
		
		<label>Nombre:</label> <br />
		<form:input disabled="true" path="title"/> <br />
	
				
		<label>Problema:</label> <br />
		<form:textarea  path="problem" rows="5" cols="10"/> <br />
		<small>Descripci&oacute;n del problema encontrado.</small>
		<p><br/></p>
		
		<label>Captcha:</label> <br />
		<%
          ReCaptcha c = ReCaptchaFactory.newReCaptcha((String)request.getAttribute("publicKey"), (String)request.getAttribute("privateKey"), false);
          out.print(c.createRecaptchaHtml(null, null));
        %>
        <small>Esto es solicitado para verificar que no se trata de un robot.</small>
        <p><br/></p>
				
		<form:hidden path="title" />
		<form:hidden path="type" />
		<form:hidden path="username" />
		
		<input type="submit" value="Aceptar" />
		</div>
		
		</fieldset>
	
	</form:form>
		</div>
		</div>
		<c:import url = "/WEB-INF/footer.jsp" />
	</div>
		
	
	</body>
</html>
