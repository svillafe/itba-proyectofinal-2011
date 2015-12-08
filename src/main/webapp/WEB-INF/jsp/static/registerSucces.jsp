<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Argendata - Registaci&oacute;n exitosa</title>
		<c:import url = "/WEB-INF/head.jsp" />
	</head>
	<body>
	<div class="container">
		<c:import url = "/WEB-INF/header.jsp" />

		<div id="cuerpo" class="span-24">
			<div id="breadcrumb" class= "prepend-1 span-22 append-1" >
				Registrar usuario >
			</div>
		
			<div style="margin:40px; color:green; ">
			<h2> Registrate! </h2>
			<div style="font-size:16px; color:#444; font-weight:bold;">
				La registraci&oacute;n se completo exitosamente. Le hemos mandado un e-mail a su casilla para activar su cuenta en Argendata.
			</div>
			 <br/>
			<br/>
			<div class="backToMainPage"><a href="../main/home">Volver a la pagina principal&nbsp;&nbsp;</a></div>
			</div>
			
		</div>

		<div style="clear:both"></div>
		
		
		
		<c:import url = "/WEB-INF/footer.jsp" />
	</div>
		
	
	</body>
</html>
