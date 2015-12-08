<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Argendata - Listado</title>
<c:import url="/WEB-INF/head.jsp" />
</head>
<body>
<div class="container"><c:import url="/WEB-INF/header.jsp" />

<div id="cuerpo" class="span-24">
<div id="breadcrumb" class="prepend-1 span-22 append-1">
Aplicaciones > Listado ></div>

<div class="prepend-1 prepend-top"><br />
<h2>Aplicaciones</h2>
</div>

<div class="prepend-1 append-1 span-16"><c:forEach
	items="${apps}" var="app">

	<div class="appPreview">
	
		<img class="appImagePreview"  with = "150px" src="../apps/iconOfApp?idApp=Semanticapp:<c:out value="${app.nameId}"/>"/><br/>
		<h2><a href="/<c:out value="${projectName}" />/bin/apps/detail?qName=Semanticapp:<c:out value="${app.nameId}"/>"> <c:out value="${app.name}" /> </a></h2>

		<small><c:out value="${app.publisher.username}" /></small>
		<p>Descripcion: <c:out value="${app.description}" /> <br />
		Puntos: <c:out value="${app.points}" /></p>
	</div>

</c:forEach></div>

</div>

<div style="clear: both"></div>



<c:import url="/WEB-INF/footer.jsp" /></div>


</body>
</html>
