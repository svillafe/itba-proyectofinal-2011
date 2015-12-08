<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>


<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>           


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Argendata - Lista para aprobaci&oaucte;n</title>
		<c:import url = "/WEB-INF/head.jsp" />
	</head>
	<body>
	<div class="container">
		<c:import url = "/WEB-INF/header.jsp" />

		<div id="cuerpo" class="span-24">
			<div id="breadcrumb" class= "prepend-1 span-22 append-1" >
				Administraci&oacute;n > Lista para aprobaci&oacute;n
			</div>
	      
	      
			<div id="whatis" class="prepend-1 span-22 append-1 last append-bottom" style="margin-top:20px;">
			<h1> Datasets por aprobar/rechazar </h1>
			
			
				<c:import url="adminMenu.jsp" /> 
				
	      
				<div class=" prepenend-1 span-15 last" style="text-align:justify">
        		<c:if test="${empty datasets}">

					<h3 style="color: navy; font-weight: lighter;">No hay n&iacute;ngun dataset para aprobar.</h3>
				</c:if> 
		
		
			    <c:forEach items="${datasets}" var="dataset">
			    
					<div class="datasetPreview ">
						<h2><c:out value="${dataset.title}" /> </h2> 
						<div class="span-15 last">
							<b>Palabras claves:</b> <c:out value="${dataset.keyword}" />
						</div>
						<div class="span-6">					
						<strong><c:out value="${dataset.publisher}" /></strong>
						<p>
							<strong>Granularidad temporal:</strong> <c:out value="${dataset.temporal}" />
						</p>
						</div>
						
						<div class="span-5">
						
						<c:set var="string" value="${dataset.modified}"/>
                		<c:set var="end" value="10"/>
                		<c:set var="start" value="0"/>
						<c:set var="modifyDate" value="${fn:substring(string,start,end)}"/>
						<strong> &Uacute;ltima Modificaci&oacute;n:</strong> <c:out value="${modifyDate}"/>  <br/>
						
						<strong>Propuesto por el usuario:</strong> <c:out value="${dataset.username}" /> 
						</div>
						
						<div class="span-4 last">
						<c:choose>
	      					<c:when test="${dataset.approved==false}">
								<ul>
								<li><a href="/<c:out value="${projectName}" />/bin/admin/decisionDataset?id=<c:out value="${dataset.id}" />">Tomar desici&oacute;n</a></li>
								<li><a href="/<c:out value="${projectName}" />/bin/admin/editPlainDataset?id=<c:out value="${dataset.id}" />">Editar</a></li>
								</ul>
							</c:when>
							<c:otherwise>
								<h4 style="color:red;font-weight:bold;">Ya aprobado</h4>
							</c:otherwise>
						</c:choose>
						</div>
						<div class="clear"></div>
					</div>
				</c:forEach>
		
		</div>
  </div>
	<div style="clear:both"></div>
		
		
		
		<c:import url = "/WEB-INF/footer.jsp" />
	</div>
		
	
	</body>
</html>

