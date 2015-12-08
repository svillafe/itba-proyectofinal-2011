<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Argendata - Apps</title>
		<c:import url = "/WEB-INF/head.jsp" />
	</head>
	<body>
	<div class="container">
		<c:import url = "/WEB-INF/header.jsp" />

		<div id="cuerpo" class="span-24">
			<div id="breadcrumb" class= "prepend-1 span-22 append-1" >
				Apps >
			</div>
			
			<div style="margin:40px;">
			
			<div class="span-15 ">
				<h1>Aplicaciones</h1>
				<p>
					<form class="big-inputs" action="../search/search" method="get" name="searchForm"  >
						<input type="hidden" name="type" value="app"/>
						<input type="text" name="terms"/>
						<input type="submit" id="submit" class="submit" value="Buscar" />
					</form>
					
				</p>
				<p class="highlighted">
					Ingresa un criterio de b&uacute;squeda para comenzar a encontrar las apps que utilizan nuestros datasets. Sino pod&eacute;s empezar por aplicar filtros a tu b&uacute;squeda.
				</p>
			</div>
			
			
			<div class="sectionTitle span-7 last" >
						<h4>Ver por palabras claves</h4>
						<div class="search-tag-container">
							 
							<c:forEach items="${myapps}" var="app">
								<a class="search-tag-item" href="../search/search?type=app&terms=&keyword=<c:out value="${app.name}"/>"><c:out value="${app.name}" />(<c:out value="${app.quantity}" />)</a> &nbsp;  
							</c:forEach>
							
						</div>
			</div>
			
			</div>	
			
	</div>
	
	
<div style="clear:both"></div>
		
		<c:import url = "/WEB-INF/footer.jsp" />
	</div>
		
	
	</body>
</html>
