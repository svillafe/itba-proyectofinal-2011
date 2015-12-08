<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Argendata</title>
		<c:import url = "/WEB-INF/head.jsp" />
	</head>
	<body>
	<div class="container">
		<c:import url = "/WEB-INF/header.jsp" />

		<div id="cuerpo" class="span-24">
			<div id="breadcrumb" class= "prepend-1 span-22 append-1" >
				Prueba >
			</div>
			
	<div id="fromMecon">
	<h2 >Datasets (no filter)</h2>
			<br/>
			<ul>
			<c:forEach items="${datasets}" var="dataset">
				<li>
					<a href="/<c:out value="${projectName}" />/bin/dataset/view?qName=Dataset:<c:out value="${dataset.titleId}" />"><c:out value="${dataset.title}" /></a>
				</li>
			</c:forEach>
			</ul>
	</div>

	<div id="recently">
		<h2 >Testing Facets :)</h2>
		<br/>
		<h4> Filter by Keywords </h4>
		<ul>
		<c:forEach items="${keywords}" var="keyword">
			<li>
				<c:out value="${keyword.name}" /> <em>(<c:out value="${keyword.quantity}" />)</em>
			</li>
		</c:forEach>
		</ul>

		<h4> Filter by DataQuality </h4>
		<ul>
		<c:forEach items="${dataQualities}" var="dataQuality">
			<li>
				<c:out value="${dataQuality.name}" /> <em>(<c:out value="${dataQuality.quantity}" />)</em>
			</li>
		</c:forEach>
		</ul>
	</div>
	
</div>
	
	<div style="clear:both"></div>
		
		<c:import url = "/WEB-INF/footer.jsp" />
	</div>
		
	
	</body>
</html>

