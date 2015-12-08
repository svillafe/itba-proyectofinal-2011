<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Argendata - Olvid&eacute; la contrase&ntilde;a</title>
		<c:import url = "/WEB-INF/head.jsp" />
	</head>
	<body>
	<div class="container">
		<c:import url = "/WEB-INF/header.jsp" />

		<div id="cuerpo" class="span-24">
			<div id="breadcrumb" class= "prepend-1 span-22 append-1" >
				Usuario > Olvid&eacute; mi contrase&ntilde;a
			</div>
		
			<div style="margin:20px; ">
			<h2> Nueva Contrase&ntilde;a </h2>
			
			<p class="success" style="font-size:15px;padding-left:10px;">
				<p>
					<b><c:out value="${user.username}" /></b> tu nueva contrase&ntilde;a ahora es: <b><c:out value="${newPassword}" /></b>
				</p>
				<p>
					Pod&eacute;s cambiarla, una vez que ingreses, en la opci&oacute;n de modificar tu contrase&ntilde;a. 
				</p>
				
			</p>
			<p>
			<br/> 
			</p>
			</div>
			
		</div>

		<div style="clear:both"></div>
		
		
		
		<c:import url = "/WEB-INF/footer.jsp" />
	</div>
		
	
	</body>
</html>
