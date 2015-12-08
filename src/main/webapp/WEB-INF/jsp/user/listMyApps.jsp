<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Argendata - Mis apps</title>
<c:import url="/WEB-INF/head.jsp" />
</head>
<body>
<div class="container"><c:import url="/WEB-INF/header.jsp" />

<div id="cuerpo" class="span-24">
<div id="breadcrumb" class="prepend-1 span-22 append-1">Usuario >
Mis Aplicaciones</div>

<div id="whatis" class="prepend-1 span-22 append-1 last append-bottom" style="margin-top:20px;">
			<h1> Mis aplicaciones </h1>
			
			
				<c:import url="userMenu.jsp"/>
				
				<div class="sectionTitle prepenend-1 span-15 last" style="text-align:justify">
				<table>
	<th>Nombre</th>
	<th>Estado</th>
	<!-- <th style="padding-left: 35px ">Accion</th> -->
	<c:forEach items="${myApps}" var="app">

		<tr>
			<td><c:out value="${app.name}" /></td>
			<td><c:if test="${app.isAllowed==true}">
		<strong>Aprobado</strong></c:if> <c:if test="${app.isAllowed==false}">
		No visto aun por un administrador</c:if></td>
		<!-- <td><a title="Editar una App" href="editApp?appId=<c:out value="${app.nameId }"/>" style="border:outset green; background-color: green; font:italic bold 15px/30px Georgia,serif;  text-decoration: none;"><text  style="color:black;">editar</text></a> <a title="Borrar una App"  href="deleteApp?appId=<c:out value="${app.name}"/>" style="border:outset; border-color:red;  background-color: red; font:italic bold 15px/30px Georgia,serif; text-decoration: none;"><text style=" color:black; ">borrar</text></a></td> -->
		</tr>
	</c:forEach>

</table>
					
				</div>
			</div>


</div>

<div style="clear: both"></div>



<c:import url="/WEB-INF/footer.jsp" /></div>


</body>
</html>
