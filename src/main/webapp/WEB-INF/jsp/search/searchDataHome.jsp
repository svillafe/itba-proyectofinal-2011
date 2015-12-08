<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Argendata - Datasets</title>
		<c:import url = "/WEB-INF/head.jsp" />
	</head>
	<body>
	<div class="container">
		<c:import url = "/WEB-INF/header.jsp" />

		<div id="cuerpo" class="span-24">
			<div id="breadcrumb" class= "prepend-1 span-22 append-1" >
				Data >
			</div>
			
			
			
			<div style="margin:40px;">
				<h1>Datasets</h1>
				<p>
					<form:form class="big-inputs" style="text-align:center;" action="../search/search" method="get" name="searchForm" commandName="searchForm" >
						<form:input type="hidden" path="type" value="dataset" ></form:input>
						<form:input type="text" path="terms" />
						
						<input type="submit" id="submit" class="submit" value="Buscar" />
					</form:form>
				</p>
				<p class="highlighted">
					Ingresa un criterio de b&uacute;squeda para comenzar a encontrar los datasets que te interesan. Sino pod&eacute;s empezar por aplicar filtros a tu b&uacute;squeda.
				</p>
			</div>
			
			
			
			<div class="prepend-1 span-23 last append-bottom">
				
		
				<div class="sectionTitle span-7 append-1" style="text-align:justify;">
						<h4>Ver por Extensión de archivo</h4>
						<div class="search-tag-container">
						<p>
							Estas son las extensiones de los documentos presentes:
						</p> 
							<c:forEach items="${listFormats}" var="format">
								<a class="search-tag-item" href="../search/search?type=dataset&terms=&filters=format:<c:out value="${format.name}"/>&sortBy=null&page=1&resPerPage=10&keyword=" ><c:out value="${format.name}" />(<c:out value="${format.quantity}" />)</a> &nbsp;  
							</c:forEach>
							
						</div>
				</div>
								
				<div class="sectionTitle span-7 append-1" style="text-align:justify;">
						<h4>Ver por palabras claves</h4>
						<div class="search-tag-container">
							<p>Estos son las palabras claves destacadas:</p> 
							<c:forEach items="${listKeywords}" var="kw">
								<a class="search-tag-item" href="../search/search?type=dataset&terms=&filters=&sortBy=null&page=1&resPerPage=10&keyword=<c:out value="${kw.name}"/>" ><c:out value="${kw.name}" />(<c:out value="${kw.quantity}" />)</a> &nbsp;  
							</c:forEach>
							
						</div>
				</div>		
				
				
			 	<div class="sectionTitle span-6 append-1 last" >
						<h4>Ver por ubicac&oacute;n</h4>
						<div class="search-tag-container">
							 
							<c:forEach items="${listLocations}" var="rt">
								<a class="search-tag-item" href="../search/search?type=dataset&terms=&filters=location:<c:out value="${rt.name}"/>&sortBy=null&page=1&resPerPage=10&keyword=" ><c:out value="${rt.name}" />(<c:out value="${rt.quantity}" />)</a> &nbsp;  
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
