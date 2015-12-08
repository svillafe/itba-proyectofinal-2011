<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
	<head>
		<title>Argendata - B&uacute;squeda</title>
		<c:import url = "/WEB-INF/head.jsp" />
		
		<c:if test="${type == 'app'}" >
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
		</c:if>
		
		<c:if test="${type == 'dataset'}" >
		<script>
			$(function() {
			var dictionaryPubs=[];
			var dictionaryKeywords=[];
		
			var name;
			<c:forEach items="${publisherList}" var="publisher">
				dictionaryPubs["<c:out value="${publisher.label}" />"]=("<c:out value="${publisher.destURL}" />").replace(/&amp;/g,"&");
			</c:forEach>
		
			var availablePubs = [
			<c:forEach items="${publisherList}" var="publisher">
				"<c:out value="${publisher.label}" /> (<c:out value="${publisher.quantity}" />)",
			</c:forEach>
			];
		
			<c:forEach items="${keywordsList}" var="keyword">
				dictionaryKeywords["<c:out value="${keyword.label}" />"]=("<c:out value="${keyword.destURL}" />").replace(/&amp;/g,"&");
			</c:forEach>
		
			var availableKeywords = [
				<c:forEach items="${keywordsList}" var="keyword">
					"<c:out value="${keyword.label}" /> (<c:out value="${keyword.quantity}" />)",
				</c:forEach>
			];
		
			
			var cache = {},
			lastXhr;
		$( "#publisher" ).autocomplete({
			minLength: 1,
			source: function( request, response ) {
				var term = request.term;
				if ( term in cache ) {
					response( cache[ term ] );
					return;
				}

				lastXhr = $.getJSON( "../../api/search/publisher/"+request.term, function( data, status, xhr ) {
					
					cache[ term ] = data.source;
					if ( xhr === lastXhr ) {
						response( data.source );
					}
				});
			}
		});
			
		
			$( "#publishersFilter" ).click(function(){
				window.location= "<c:out value="${beforeFilter}"/>".replace(/&amp;/g,"&")+$("#publisher").val()+"<c:out value="${afterFilter}" />".replace(/&amp;/g,"&");
			return false;
		});
		
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
		
		$( "#addTerm" ).click(function(){
			var val = $("#add-term").val();
			
			window.location= "../search/search?terms="+val+("<c:out value="${urlWithoutTerm}"/>").replace(/&amp;/g,"&");
			
			return false;
			
		});
		
	});
	</script>
	</c:if>	

	</head>
	<body>
	<div class="container">
		<c:import url = "/WEB-INF/header.jsp" />

		<div id="cuerpo" class="span-24">
			<c:if test="${type == 'app'}">
			<div id="breadcrumb" class= "prepend-1 span-22 append-1" >
				B&uacute;squeda > Aplicaciones
			</div>
			</c:if>
			<c:if test="${type == 'dataset'}" >
			<div id="breadcrumb" class= "prepend-1 span-22 append-1" >
				B&uacute;squeda > Dataset
			</div>
			</c:if>
			
			<div id="results" class="prepend-1 span-14">
			<br/>
			<h1> B&uacute;squeda </h1>
			
			<c:if test="${type == 'app'}" >
			<c:if test="${searchTerm != ''}">
			
				<h2>Resultados para "<strong><c:out value="${searchTerm}" /></strong>"</h2>
			</c:if>
			</c:if>
			<c:if test="${type == 'dataset'}" >
			<c:if test="${searchTerm != '*' and searchTerm!= ''}">
				<h2>Resultados para "<strong><c:out value="${searchTerm}" /></strong>"</h2>
			</c:if>
			</c:if>
			
			<strong><p>Se encontraron <c:out value="${cantResults}" /> resultados </p></strong>			
			<c:if test="${not empty apps}">
				<strong><p>P&aacute;gina <c:choose><c:when test="${not empty pageNumber}"><c:out value="${pageNumber}" /></c:when><c:otherwise>1</c:otherwise></c:choose> - Mostrando resultados resultados del <c:out value="${showingMin}" /> al <c:out value="${showingMax}" /></p></strong>
			</c:if>
			<c:if test="${not empty datasets}">
				<strong><p>P&aacute;gina <c:choose><c:when test="${not empty pageNumber}"><c:out value="${pageNumber}" /></c:when><c:otherwise>1</c:otherwise></c:choose> - Mostrando resultados resultados del <c:out value="${showingMin}" /> al <c:out value="${showingMax}" /></p></strong>
			</c:if>
			
			<c:if test="${type == 'dataset'}" >
			<c:if test="${empty datasets}">
			<p><c:out value="${notFound}" /></p>
			</c:if>
			</c:if>
			
			<c:if test="${type == 'app'}" >
				<c:if test="${empty apps}">
					<p><c:out value="${notFound}" /></p>
				</c:if>
			<c:forEach items="${apps}" var="app">
			
				<div class="datasetPreview-top">
				
					<h2><a class="anchorTitle" href="../apps/detail?qName=Semanticapp:<c:out value="${app.nameId}" />"><c:out value="${app.name}" /></a></h2>
					<p><b>Descripci&oacute;n:</b> <c:out value="${app.description}" /> <br /></p>
				</div>
			</c:forEach>
			</c:if>
			<c:if test="${type == 'dataset'}" >
			<c:forEach items="${datasets}" var="dataset">
				<div class="datasetPreview-top">
					<h2><a class="anchorTitle" title="Detalles" href="../dataset/view?qName=Dataset:<c:out value="${dataset.titleId}" />"><c:out value="${dataset.title}" /></a></h2>
					<small><b><c:out value="${dataset.publisher.name}" /></b></small>
					<p>Palabras claves: <c:out value="${dataset.keyword}" /> <br /></p>
				</div>
			</c:forEach>
			</c:if>

			<p>
				<br/>
			</p>
			
			<hr/>
			<c:if test="${not empty datasets or not empty apps}">
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
				<a href="<c:out value="${urlWithoutResPerPage}" />20<c:out value="${actualKeyword}" />">20</a> 
			</div>
			</c:if>
	</div>
	
	<div id="sidebar" class="prepend-1 span-7 ">
		
		<br/>
		<h2>Filtros aplicados</h2>
		<div id="currentFilters">
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
		<br/>
		<h2>Filtre u ordene</h2>
		<div id="filterList">
		
		<c:if test="${type == 'app'}">
		<h4> Ordenar por</h4>
		<ul>
			<c:forEach items="${orderAttributesList}" var="orderAttribute">
				<li>
					<a href="<c:out value="${orderAttribute.destURL}" />"> <c:out value="${orderAttribute.showName}" />  </a>
				</li>
			</c:forEach>
		</ul>
		</c:if>
		
		<c:if test="${type == 'dataset'}">
			<h4> <c:if test="${searchTerm != '*'}">Cambiar </c:if> T&eacute;rmino </h4>
			<div class="ui-widget">
		
				<input id="add-term" style="margin-left: 22px;" value="<c:choose><c:when test="${searchTerm != '*'}"><c:out value="${searchTerm}" /></c:when><c:otherwise></c:otherwise></c:choose>" />
				<a id="addTerm" href="">Filtrar</a>
				<br/>
			</div>
			<br/> 
			<c:if test="${not empty datasets or not empty apps}">
				<h4> Filtrar por Publicante </h4>
				<c:choose>
					<c:when test="${empty thePublisher}">
						<div class="ui-widget">
							<input id="publisher" style="margin-left: 22px; "/>
							<a id="publishersFilter" href="">Filtrar</a>
							<br/><br/>
						</div>
					</c:when>
					<c:otherwise>
						<p><strong><c:out value="${publisherSelected}" />:</br><c:out value="${thePublisher}" /></strong></p>
					</c:otherwise>
				</c:choose>
			</c:if>
		</c:if>
		
		<c:if test="${not empty datasets or not empty apps}">
		<h4> Filtrar por Palabras Clave </h4>
				
		<div class="ui-widget">
			
			<input id="keywords-suggest" style="margin-left: 22px; "/>
			<a id="keywordsFilter" href="">Filtrar</a>
			<br/>
		</div>
		<br/>
		
		
		<c:if test="${type == 'dataset'}">
			<h4> Filtrar por calidad de datos </h4>
			<ul>
				<c:forEach items="${dataQualityList}" var="dataQuality">
				<li>
					<a href="<c:out value="${dataQuality.destURL}" />"> <c:out value="${dataQuality.name}" /> (<c:out value="${dataQuality.quantity}" />) </a>
				</li>
				</c:forEach>
			</ul>
		
			<h4> Filtrar por licencia </h4>
			<ul>
			<c:forEach items="${licenseList}" var="license">
				<li>
					<a href="<c:out value="${license.destURL}" />"> <c:out value="${license.name}" /> (<c:out value="${license.quantity}" />) </a>
				</li>
			</c:forEach>
			</ul>
		
			<h4> Filtrar por granuralidad temporal </h4>
			<ul>
			<c:forEach items="${temporalList}" var="temporal">
				<li>
					<a href="<c:out value="${temporal.destURL}" />"> <c:out value="${temporal.name}" /> (<c:out value="${temporal.quantity}" />) </a>
				</li>
			</c:forEach>
			</ul>
		
			<h4> Filtrar por granuralidad espacial </h4>
			<ul>
			<c:forEach items="${spatialList}" var="spatial">
				<li>
					<a href="<c:out value="${spatial.destURL}" />"> <c:out value="${spatial.name}" /> (<c:out value="${spatial.quantity}" />) </a>
				</li>
			</c:forEach>
			</ul>
		
			<h4> Filtrar por Ubicación </h4>
			<ul>
			<c:forEach items="${locationList}" var="location">
				<li>
					<a href="<c:out value="${location.destURL}" />"> <c:out value="${location.name}" /> (<c:out value="${location.quantity}" />) </a>
				</li>
			</c:forEach>
			</ul>
		
			<h4> Filtrar por Tipo de Recurso </h4>
			<ul>
			<c:forEach items="${resourceTypeList}" var="resource">
				<li>
					<a href="<c:out value="${resource.destURL}" />"> <c:out value="${resource.name}" /> (<c:out value="${resource.quantity}" />) </a>
				</li>
			</c:forEach>
			</ul>
		
			
			<h4> Filtrar por Extensión de Recurso </h4>
			<ul>
			<c:forEach items="${formatList}" var="extension">
				<li>
					<a href="<c:out value="${extension.destURL}" />"> <c:out value="${extension.name}" /> (<c:out value="${extension.quantity}" />) </a>
				</li>
			</c:forEach>
			</ul>
			
			<h4> Filtrar por Tamaño de Recurso </h4>
			<ul>
			<c:forEach items="${extentionList}" var="extention">
				<li>
					<a href="<c:out value="${extention.destURL}" />"> <c:out value="${extention.name}" /> </a>
				</li>
			</c:forEach>
			</ul>
		
			<h4>Filtrar por fecha de modificaci&oacute;n </h4>
			<ul>
			<c:forEach items="${modifiedList}" var="modified">
				<li>
					<a href="<c:out value="${modified.destURL}" />"> <c:out value="${modified.name}" /> </a>
				</li>
			</c:forEach>
			</ul>
			
			<h4> Ordenar por</h4>
			<ul>
				<c:if test="${not empty datasets or not empty apps}">
				<c:forEach items="${orderAttributesList}" var="orderAttribute">
					<li>
						<a href="<c:out value="${orderAttribute.destURL}" />"> <c:out value="${orderAttribute.showName}" />  </a>
					</li>
				</c:forEach>
				</c:if>
			</ul>
		
		</c:if>
		</c:if>
		</div>
	</div>
	
	
	
</div>
	
	<div style="clear:both"></div>
		
		<c:import url = "/WEB-INF/footer.jsp" />
	</div>
		
	
	</body>
	

</html>

