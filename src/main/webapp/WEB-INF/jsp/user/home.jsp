<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Argendata - Inicio</title>
		<c:import url = "/WEB-INF/head.jsp" />
	</head>
	<body>
	<div class="container">
		<c:import url = "/WEB-INF/header.jsp" />

		<div id="cuerpo" class="span-24">
			<div id="breadcrumb" class= "prepend-1 span-22 append-1" >
				Usuario > Principal
			</div>
		
			
			<div id="whatis" class="prepend-1 span-22 append-1 last append-bottom" style="margin-top:20px;">
			<h1> Principal </h1>
			
			<p class="notice" style="font-size:15px;padding-left:10px;">
			<c:out value="${msg}" />
			</p>
			
				<c:import url="userMenu.jsp"/>
				
				<div class="sectionTitle prepenend-1 span-9 " style="text-align:justify">
					<h4>&iquest;Qu&eacute; puedo hacer?</h4>
					<div style="margin:15px;">
						<p> <b>ARGENDATA</b> te permite <b>navegar la informaci&oacute;n</b> para ver que datasets est&aacute;n disponibles.</p>
						<p> Cuenta con un endpoint el cual responde <b>consultas</b> de tipo <i>SPARQL</i> sobre los datos almacenados en <i>RDF</i>.</p>
						<p> Pod&eacute;s <b>contribuir</b> con datasets que no existan. Para esto solo ten&eacute;s que <a href="../public/register">registrarte</a>
					y proponer tu dataset.</p>
						<p> Cre&aacute; tu <b> aplicaci&oacute;n</b> utilizando nuestros datos y avisanos que la pondremos en nuestra <i>galer&iacute;a</i>.</p>
						
						
					</div>
					
				</div>
				<div class="sectionTitle prepenend-1 span-5 last" style="text-align:justify">
					<h4>Tu participaci&oacute;n</h4>
					<div style="margin:15px; text-align:center;">
						<p>
							<span style="color:orangered; font-size:54px;"><c:out value="${user.appsQty}"/></span><br/>
							<b> APPS</b><br/>
						</p>
						<p>
							<span style="color:yellowgreen; font-size:54px;"><c:out value="${user.datasetsQty}"/></span><br/>
							<b> DATASETS</b><br/>
						</p>
					</div>
					
				</div>
			</div>
			
			
			
		</div>

		<div style="clear:both"></div>
		
		
		
		<c:import url = "/WEB-INF/footer.jsp" />
	</div>
		
	
	</body>
</html>
