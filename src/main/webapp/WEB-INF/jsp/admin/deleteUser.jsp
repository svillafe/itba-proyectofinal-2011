<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Argendata - Listar Usuarios</title>
		<c:import url = "/WEB-INF/head.jsp" />
	</head>
	<body>
	<div class="container">
		<c:import url = "/WEB-INF/header.jsp" />

		<div id="cuerpo" class="span-24">
			<div id="breadcrumb" class= "prepend-1 span-22 append-1" >
				Administrador > Listar Usuarios
			</div>
		
     
      
       
		<div id="whatis" class="prepend-1 span-22 append-1 last append-bottom" style="margin-top:20px;">
			<h1> Borrar usuario </h1>
			
				<c:import url="adminMenu.jsp" /> 
				
				<div class="sectionTitle prepenend-1 span-15 last" style="text-align:justify; margin-top:90px;">
			   
				<c:choose>
					<c:when test="${not empty apps}">
						<p>
						El usuario <c:out value="${userToDelete}"/> tiene aplicaciones a su nombre. No podr&aacute; eliminarlo hasta no borrarlas. Estas son:
						<ul>
						<c:forEach items="${apps}" var="app">
							<li><a href="/<c:out value="${projectName}"/>/bin/apps/detail?qName=Semanticapp:<c:out value="${app.nameId}"/>"><c:out value="${app.name}"/></a></li>
						</c:forEach>
						</ul>
						</p>
					</c:when>
					
					<c:otherwise>
						<div style="text-align:center;">
							<h3>Est&aacute;s por borrar a <c:out value="${userToDelete}"/>.</h3>
							<form method="POST" action="deleteUserPost" style="display:inline">
								<input type="hidden" name="id" value="<c:out value="${id}"/>" />
								<input id="confirmar-button" type="submit" value="Confirmar" class="submit" style="background:green;color:white;border:none;cursor:pointer"/>
								<input id="cancelar-button" type="button" value="Cancelar" class="submit" style="background:red;color:white;border:none;cursor:pointer"/>
							</form>
						</div>
					</c:otherwise>
				</c:choose>
			   
				<script type="text/javascript">
					$(document).ready(function(){
						$("#cancelar-button").click(function(){
							window.location="<c:out value="${mainUrl}"/>/admin/listUsers";
							return false;
						});
					
					});
				</script>
				
				</div>
			</div>
		
  </div>
	<div style="clear:both"></div>
		
		
		
		<c:import url = "/WEB-INF/footer.jsp" />
	</div>
		
	
	</body>
</html>

