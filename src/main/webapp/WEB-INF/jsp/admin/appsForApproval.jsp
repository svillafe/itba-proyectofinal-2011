<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Argendata - Lista para aprobaci&oacute;n</title>
<c:import url="/WEB-INF/head.jsp" />
</head>
<body>
<div class="container"><c:import url="/WEB-INF/header.jsp" />

<div id="cuerpo" class="span-24">
<div id="breadcrumb" class="prepend-1 span-22 append-1">
Administraci&oacute;n > Lista para aprobaci&oacute;n</div>

<div id="whatis" class="prepend-1 span-22 append-1 last append-bottom" style="margin-top:20px;">
			<h1> Datasets por aprobar/rechazar </h1>
			
			
				<c:import url="adminMenu.jsp" /> 
				
	      
				<div class="prepenend-1 span-15 last" style="text-align:justify">

					
					
					<c:if test="${empty apps}">
						<h3 style="color: navy; font-weight: lighter;">No hay n&iacute;nguna app para aprobar.</h3>
					</c:if>

					<c:forEach items="${apps}"	var="app">
					
						<div class="appPreview append-bottom">
						<h2><c:out value="${app.name}" /></h2>
						<p><b>Publicante:</b> <c:out value="${app.publisher.name}" /> <br />
						<b>Descripci&oacute;n:</b> <c:out value="${app.description}" /> <br />
						<br />
						<b>URL:</b> <a target="_new" href="../external/linkWP?webPage=<c:out value="${app.url}" />" ><c:out value="${app.url}" /></a> <br />
						<b>Screenshot:</b> <c:out value="${screenError}" /> <br />
						<img class="appScreenDetail"
							src="../apps/screenshotOfApp?idApp=Semanticapp:<c:out value="${app.nameId}"/>" /><br />
					
					
						<br />
						<a style="color:orangered" href="../admin/editApp?qName=Semanticapp:<c:out value="${app.nameId}" />">Editar</a>
						</p>
						<c:choose>
							<c:when test="${app.isAllowed==false}">
								<h4><a
									href="/<c:out value="${projectName}" />/bin/admin/decisionApp?id=<c:out value="${app.id}" />">Tomar
								desici&oacute;n</a></h4>
							</c:when>
							<c:otherwise>
								<h4 style="color: red; font-weight: bold;">Ya aprobado</h4>
							</c:otherwise>
						</c:choose></div>
					</c:forEach>
				</div>

			</div>
			
			<div style="clear: both"></div>

			<c:import url="/WEB-INF/footer.jsp" /></div>

</body>

</html>

