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
		<title>Argendata - Cambiar contrase&ntilde;a</title>
		<c:import url = "/WEB-INF/head.jsp" />
	</head>
	<body>
	
	<div class="container">
		<c:import url = "/WEB-INF/header.jsp" />

		<div id="cuerpo" class="span-24">
			<div id="breadcrumb" class= "prepend-1 span-22 append-1" >
				Usuario > Cambiar contrase&ntilde;a
			</div>
	
	<div id="whatis" class="prepend-1 span-22 append-1 last append-bottom" style="margin-top:20px;">
			<h1>Cambiar contrase&ntilde;a</h1>
			
			<p class="notice" style="font-size:15px;padding-left:10px;">
			<c:out value="${msg}" />
			</p>
			
				<c:choose>
					<c:when test="${user.admin}">
						<c:import url="../admin/adminMenu.jsp"/>
					</c:when>
					<c:otherwise>
						<c:import url="userMenu.jsp"/>
					</c:otherwise>
				</c:choose>
				
				<div class="sectionTitle prepenend-1 span-15 last" style="text-align:justify">
				<form:form method="POST" action="../user/changePassword"	commandName="changePasswordForm">
					<fieldset>
					<legend>Complete</legend>
					<span class="error"><form:errors path="*" /></span><br/>
					<div class="clear"><p>Todos los campos son obligatorios.</p></div>
					
					<div class="prepend-1 span-8 append-1">	
					
					<label for="oldPassword">Contrase&ntilde;a actual:</label>
					<br />
					<form:input type="password" path="oldPassword" />
					<br/>
						
						
					<label for="newPassword">Nueva contrase&ntilde;a:</label>
					<br />
					<form:input type="password" path="newPassword" />
					<br/>
					<small>Por su seguridad debe tener al menos 6 caracteres.</small>
					<br />
					
					<label for="reNewPassword">Re-ingrese contrase&ntilde;a:</label>
					<br />
					<form:input path="reNewPassword" type="password"/>
					<br/>
					<small>Ingrese nuevamente la contrase&ntilde;a para evitar errores.</small>
					<br/>	
					
					<br/>
					
					
					</div>
					
					<div class="clear">
						<input type="submit" value="Cambiar" />
						</div>
					</fieldset>
				</form:form>
					
				</div>
			</div>
	
	
	<div style="clear:both"></div>
		
		<c:import url = "/WEB-INF/footer.jsp" />
	</div>
		
	
	</body>
</html>