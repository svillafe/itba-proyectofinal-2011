<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Argendata - Perfil(<c:out value="${user.username}" />)</title>
		<c:import url = "/WEB-INF/head.jsp" />
	</head>
	<body>
	<div class="container">
		<c:import url = "/WEB-INF/header.jsp" />

		<div id="cuerpo" class="span-24">
			<div id="breadcrumb" class= "prepend-1 span-22 append-1" >
				Usuario > Perfil
			</div>
		
			<div class="prepend-1 appen-1 span-22" style="margin:20px; ">
				<h1> Perfil de Usuario (<c:out value="${user.username}" />)</h1>
				<c:if test="${user.username==actualUser}">
					<a style="text-decoration: none;" href="editProfile">editar</a><br/>
				</c:if>	
				
				<div class="span-4">
					<h2> Avatar </h2>
					<img src="http://www.gravatar.com/avatar/<c:out value="${hash}"/>?d=mm" />
					<br/>
					<small>desde <a href="http://www.gravatar.com" target="_new">Gravatar</a></small>
				</div>
				
				<div class="prepend-1 span-11">
				
					<h2>Informaci&oacute;n de contacto</h2>
					<label>Twitter:</label> <c:choose><c:when test="${empty user.twitter}"><i>no disponible</i></c:when><c:otherwise><c:out value="${user.twitter}" /></c:otherwise></c:choose><br/><br/>
					<label>Facebook:</label> <c:choose><c:when test="${empty user.facebook}"><i>no disponible</i></c:when><c:otherwise><c:out value="${user.facebook}" /></c:otherwise></c:choose><br/><br/>
					<label>Minibio:</label> <c:choose><c:when test="${empty user.minibio}"><i>no completada</i></c:when><c:otherwise><c:out value="${user.minibio}" /></c:otherwise></c:choose> <br/><br/>
	
				</div>
				
				<div class="span-5 last" style="text-align:justify">
					<h2>  Participaci&oacute;n</h2>
					<div style=" text-align:center;">
						<div class="span-2">
							<span style="color:orangered; font-size:54px;"><c:out value="${user.appsQty}"/></span><br/>
							<b> APPS</b><br/>
						</div>
						<div class="span-2 last">
							<span style="color:yellowgreen; font-size:54px;"><c:out value="${user.datasetsQty}"/></span><br/>
							<b> DATASETS</b><br/>
						</div>
					</div>
					
				</div>
						
						
			</div>
			<p><br/><br/></p>
		</div>

		<div style="clear:both"></div>
		
		
		
		<c:import url = "/WEB-INF/footer.jsp" />
	</div>
		
	
	</body>
</html>
