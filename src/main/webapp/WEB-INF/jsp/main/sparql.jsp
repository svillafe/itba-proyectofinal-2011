<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Argendata - Consulta</title>
		<c:import url = "/WEB-INF/head.jsp" />
		<script type="text/javascript">
			$(document).ready(function(){
				$('#queryLanguage').change(function(){
					if($('#queryLanguage').val()=="SeRQL"){
						$("#responseFormat option[value='RDFXML']").attr("selected","selected");
						$("#responseFormat").hide();
						$('#responseFormatLabel').hide();
					}else{
						$("#responseFormat").show();
						$('#responseFormatLabel').show();
					}
				});
				
				if($('#queryLanguage').val()=="SeRQL"){
						$("#responseFormat option[value='RDFXML']").attr("selected","selected");
						$("#responseFormat").hide();
						$('#responseFormatLabel').hide();
				}
			});
		</script>
	</head>
	<body>
	<div class="container">
		<c:import url = "/WEB-INF/header.jsp" />

		<div id="cuerpo" class="span-24">
			<div id="breadcrumb" class= "prepend-1 span-22 append-1" >
				Consulta >
			</div>
			
			
			
			<div class="prepend-1 span-22 append-1 last" style="margin-top:20px">
				<h1>Consult&aacute; al repositorio</h1>
				<p>
				</p>
				<p>
					<form:form action="sparql" method="post" commandName="sparqlSearchForm" class="myForm">
						<div class="span-10 append-1">
							<label>Consulta:</label><br/>
							<form:textarea type="text" path="query" />
							
							<div>
								<div class="span-4">
									<label>Lenguaje de la consulta:</label><br/>
									<form:select path="queryLanguage" id="queryLanguage">
										<form:options/>
									</form:select>
								</div>
								<div class="span-4">
									<label id="responseFormatLabel">Formato de respuesta:</label><br/>
									<form:select path="responseFormat" id="responseFormat">
										<form:options/>
									</form:select>
								</div>
								<div class="span-1 last">
									<br/>
									<input type="submit" id="submit" class="submit" value="Buscar" />
								</div>
							</div>
							<div class="clear"></div>
							<br/>
							<span class="error" style="">
	    						<form:errors path="*" />
	    						<c:if test="${!empty error}">
	    							<c:out value="${error}"/>
	    						</c:if>
	    					</span>

						</div>
						
						<div class="span-10 prepend-1 last">
						
							<label>Resultado:</label>
							<form:textarea type="text" path="result" />
							
						</div>   
						
						<div class="clear"/>
									
						<br/>
						
					</form:form>
				</p>
				<p class="highlighted">
					Para consultar nuestro repositorio de datasets ingresa una consulta en <a target="_new" href="http://www.w3.org/TR/rdf-sparql-query/">SPARQL</a> o <a target="_new" href="http://www.openrdf.org/doc/sesame2/users/ch09.html#section-select-construct-queries">SeRQL</a> y el formato de salida JSON o RDF-XML. Las consultas se auto-limitar&aacute;n a 200 resultados como m&aacute;ximo.<br/>
					La consulta de ejemplo trae del repositorio los datasets de la Ciudad de Buenos Aires que est&aacute;n en formato XLS.
				</p>
			</div>
			
	</div>
	
	
<div style="clear:both"></div>
		
		<c:import url = "/WEB-INF/footer.jsp" />
	</div>
		
	
	</body>
</html>
