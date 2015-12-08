<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<c:if test="${type!='app'}" >
	<head>
		<title>Argendata - Buscar Apps</title>
		<c:import url = "/WEB-INF/head.jsp" />
		
		<script>
	$(function() {
		
		var dictionaryKeywords=[];
		
		var name;
		
		
		<c:forEach items="${keywordsList}" var="keyword">
			dictionaryKeywords["<c:out value="${keyword.label}" />"]=("<c:out value="${keyword.destURL}" />").replace(/&amp;/g,"&");
		</c:forEach>
		
		var availableKeywords = [
		<c:forEach items="${keywordsList}" var="keyword">
				"<c:out value="${keyword.label}" /> (<c:out value="${keyword.quantity}" />)",
		</c:forEach>
		];
		
		
		
		
		$( "#keywords-suggest" ).autocomplete({
			source: availableKeywords,
			select: function(event, ui){ 
						$("#keywords-suggest").val(ui.item.value.substring(0, ui.item.value.length-4));
						return false;
				}
		});
		
		$( "#keywordsFilter" ).click(function(){
			var val = $("#keywords-suggest").val();
			
			if(val!="" ){
				if(dictionaryKeywords[val]!=undefined){
					window.location= dictionaryKeywords[val] ;
				}
				else{
					$("#keywords-suggest").val("");
				}
				
			}
			
			return false;
		});
		
	
		
	});
	</script>
		
	</head>
	<body>
	<div class="container">
		<c:import url = "/WEB-INF/header.jsp" />

		<div id="cuerpo" class="span-24">
			<div id="breadcrumb" class= "prepend-1 span-22 append-1" >
				B&uacute;squeda > Aplicaciones
			</div>
			
			<div id="results" class="prepend-1 span-13">
			<br/>
			<c:if test="${searchTerm != ''}">
				<h2>Resultados para "<strong><c:out value="${searchTerm}" /></strong>"</h2>
			</c:if>
			
			<c:if test="${not empty apps}">
				<strong><p>P&aacute;gina <c:out value="${pageNumber}" /> - Mostrando <c:out value="${resultsPerPage}" /> resultados por p&aacute;gina </p></strong>
			</c:if>
			
			<br/>
			<br/> 
			<p><c:out value="${notFound}" /></p>
			
			<c:forEach items="${apps}" var="app">
			
				<div class="datasetPreview-top">
				
					<h2><a class="anchorTitle" href="/<c:out value="${projectName}" />/bin/apps/detail?qName=Semanticapp:<c:out value="${app.nameId}" />"><c:out value="${app.name}" /></a></h2>
					<p><b>Descripci&oacute;n:</b> <c:out value="${app.description}" /> <br /></p>
				</div>
			
				
			</c:forEach>
			
			
			<p>
				<br/>
			</p>
			
			<hr/>
			<div align=center>
				<strong>P&aacute;gina: </strong>
				<c:if test="${not empty prevPage}">
				<a href="<c:out value="${prevPage}" />">prev</a>
				</c:if>
				
				<c:forEach items="${numberList}" var="number">
					<a href="<c:out value="${urlWithoutPage}${number}${actualResPerPage}${actualKeyword}" />"><c:out value="${number}" /></a>
				</c:forEach>
				
				<c:if test="${not empty nextPage}">
					<a href="<c:out value="${nextPage}" />">sig</a>
				</c:if>
			</div>
			<div align=center>
			<strong>Cantidad de resultados por p&aacute;gina: </strong>
			<a href="<c:out value="${urlWithoutResPerPage}" />5<c:out value="${actualKeyword}" />">5</a> 
			<a href="<c:out value="${urlWithoutResPerPage}" />10<c:out value="${actualKeyword}" />">10</a> 
			</div>
	</div>
	
	
	

	<div id="sidebar" class="prepend-1 span-8 ">
		
		<br/>
		<h2>Filtros aplicados</h2>
		<div style=" background:#EFF7DF; border: 1px solid #cdcaa3; border-radius:4px; -moz-border-radius:4px; -webkit-border-radius:4px; padding:4px;">
		<c:if test="${empty currentFiltersList}">
			<br/>
			&nbsp;No hay filtros aplicados.
		</c:if>
		<ul>
		<c:forEach items="${currentFiltersList}" var="currentFilter">
			<li>
				<a href="<c:out value="${currentFilter.destURL}" />"> <c:out value="${currentFilter.name}" /> (<c:out value="${currentFilter.quantity}" />) </a>
			</li>
		</c:forEach>
		</ul>
		</div>
		
		<h2>Filtre u ordene</h2>
		<div style=" background:#EFF7DF; border: 1px solid #cdcaa3; border-radius:4px; -moz-border-radius:4px; -webkit-border-radius:4px; padding:5px; margin-bottom:10px;">
		
		
		<h4> Ordenar por</h4>
		<ul>
			<c:forEach items="${orderAttributesList}" var="orderAttribute">
				<li>
					<a href="<c:out value="${orderAttribute.destURL}" />"> <c:out value="${orderAttribute.showName}" />  </a>
				</li>
			</c:forEach>
		</ul>
		
		
		<h4> Filtrar por Palabras Clave </h4>
				
		<div class="ui-widget">
			
			<input id="keywords-suggest" style="margin-left: 22px; "/>
			<a id="keywordsFilter" href="">Filtrar</a>
			<br/>
		</div>
		<br/>
		
		
		
		</div>
	</div>
	
	
	
</div>
	
	<div style="clear:both"></div>
		
		<c:import url = "/WEB-INF/footer.jsp" />
	</div>
		
	
	</body>
	
	</c:if>
</html>

