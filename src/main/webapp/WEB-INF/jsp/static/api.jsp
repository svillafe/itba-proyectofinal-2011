<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Argendata - API</title>
		<c:import url = "/WEB-INF/head.jsp" />
	</head>
	<body>
	<div class="container">
		<c:import url = "/WEB-INF/header.jsp" />

		<div id="cuerpo" class="span-24">
			<div id="breadcrumb" class= "prepend-1 span-22 append-1" >
				API >
			</div>
		
			<div style="margin:40px; ">
			
			<h1> API </h1>
			<div style="font-size:16px; color:#444; font-weight:bold;">
				La api se compone de diferentes m&eacute;todos para consultar datos en nuestra base y exponerlos en formatos amigables como lo son RDF y JSON.
			
			
			
			 	Para utilizarla la direcci&oacute;n ser&aacute; relativa a <em>/<c:out value="${projectName}" />/api/</em> 
				<br/>
			</div>
			<br/>
			
			<h2>Consultas</h2>
			<ul>
				<li><h3><b>Generar una consulta al repositorio</b></h3>
					<div id="" class="prepend-1 span-19 append-1 append-bottom prepend-top">
					<table>
					<tr>
						<td class="table-item span-5"> URL:</td>
						<td> /<c:out value="${projectName}" />/api/repo/query.formato?q={search} </td>
					</tr>
					<tr>
						<td class="table-item span-5"> M&eacute;todo:</td>
						<td>GET</td>
					</tr>
					<tr>
						<td class="table-item span-5"> Campos:</td>
						<td>
							<b>formato</b> Formato de la salida (rdf o json).<br/>
							<b>search</b> Consulta en lenguaje SPARQL. No debe superar los 600 caracteres.<br/>
						</td>
					</tr>
					<tr>
						<td class="table-item span-5"> HTTP Status Code:</td>
						<td>
							<b>200</b> OK.<br/>
							<b>400</b> Formato de URL no v&aacute;lido.<br/>
							<b>404</b> Recurso no encontrado.<br/>
							<b>413</b> La consulta es m&aacute;s larga de los debido.<br/>
							<b>420</b> La consulta posee clausulas no soportadas.<br/>
							<b>421</b> Consulta mal formada.<br/>
						</td>
					</tr>
					<tr>
						<td class="table-item span-5" > Ejemplo:</td>
						<td><em> "/<c:out value="${projectName}" />/api/repo/query.rdf?q=SELECT%20*%20WHERE%20{?s%20?p%20?o%20}LIMIT%205" </em></td>
					</tr>
					<tr>
						<td class="table-item span-5"> Links:</td>
						<td><a target="_new" href="/<c:out value="${projectName}" />/api/repo/query.json?q=SELECT%20*%20WHERE%20{?s%20?p%20?o%20}LIMIT%205">JSON</a> | <a target="_new" href="/<c:out value="${projectName}" />/api/repo/query.rdf?q=SELECT%20*%20WHERE%20{?s%20?p%20?o%20}LIMIT%205">RDF</a></td>
					</tr>
					<tr>
						<td class="table-item span-5"> Devuelve:</td>
						<td>El resultado de la consulta. Se limita autom&aacute;ticamente la cantidad de resultados a 200.</td>
					</tr>
					</table>
					</div>
					<hr/>
				</li>
				</ul>
			<h2>Datasets</h2>
			
			<ul>
				<li><h3><b>Obtener un dataset por t&iacute;tulo</b></h3>
					<div id="" class="prepend-1 span-19 append-1 append-bottom prepend-top">
					<table>
					<tr>
						<td class="table-item span-5"> URL:</td>
						<td> /<c:out value="${projectName}" />/api/dataset/by/title/{t}.formato </td>
					</tr>
					<tr>
						<td class="table-item span-5"> M&eacute;todo:</td>
						<td>GET</td>
					</tr>
					<tr>
						<td class="table-item span-5"> Campos:</td>
						<td>
							<b>t</b> T&iacute;tulo del dataset.<br/>
							<b>formato</b> Formato de la salida (json o rdf).<br/>
						</td>
					</tr>
					<tr>
						<td class="table-item span-5"> HTTP Status Code:</td>
						<td>
							<b>200</b> OK.<br/>
							<b>404</b> Recurso no encontrado.<br/>
						</td>
					</tr>
					<tr>
						<td class="table-item span-5" > Ejemplo:</td>
						<td><em> "/<c:out value="${projectName}" />/api/dataset/by/title/Tucumán%20Compra.json" </em></td>
					</tr>
					<tr>
						<td class="table-item span-5"> Links:</td>
						<td><a target="_new" href="/<c:out value="${projectName}" />/api/dataset/by/title/Tucumán%20Compra.json">JSON</a> | <a target="_new" href="/<c:out value="${projectName}" />/api/dataset/by/title/Tucumán%20Compra.rdf">RDF</a></td>
					</tr>
					</table>
					</div>
					<hr/>
				</li>
				
				<li><h3><b>Obtener datasets por entidad publicante</b></h3>
					<div id="" class="prepend-1 span-19 append-1 append-bottom prepend-top">
					<table>
					<tr>
						<td class="table-item span-5"> URL:</td>
						<td>/<c:out value="${projectName}" />/api/dataset/by/publisher/{p}.formato </td>
					</tr>
					<tr>
						<td class="table-item span-5"> M&eacute;todo:</td>
						<td>GET</td>
					</tr>
					<tr>
						<td class="table-item span-5"> Campos:</td>
						<td>
							<b>p</b> Nombre del publicante.<br/>
							<b>formato</b> Formato de la salida (json o rdf).<br/>
						</td>
					</tr>
					<tr>
						<td class="table-item span-5"> HTTP Status Code:</td>
						<td>
							<b>200</b> OK.<br/>
						</td>
					</tr>
					<tr>
						<td class="table-item span-5" > Ejemplo:</td>
						<td><em> "/<c:out value="${projectName}" />/api/dataset/by/publisher/Gobierno%20de%20Chubut.json" </em></td>
					</tr>
					<tr>
						<td class="table-item span-5"> Links:</td>
						<td><a target="_new" href="/<c:out value="${projectName}" />/api/dataset/by/publisher/Gobierno%20de%20Chubut.json">JSON</a> | <a target="_new" href="/<c:out value="${projectName}" />/api/dataset/by/publisher/Gobierno%20de%20Chubut.rdf">RDF</a></td>
					</tr>
					</table>
					</div>
					
					<hr/>
				</li>
				
				<li><h3><b>Obtener datasets por palabra clave</b></h3>
					<div id="" class="prepend-1 span-19 append-1 append-bottom prepend-top">
					<table>
					<tr>
						<td class="table-item span-5"> URL:</td>
						<td>/<c:out value="${projectName}" />/api/dataset/by/keyword/{k}.formato </td>
					</tr>
					<tr>
						<td class="table-item span-5"> M&eacute;todo:</td>
						<td>GET</td>
					</tr>
					<tr>
						<td class="table-item span-5"> Campos:</td>
						<td>
							<b>k</b> Palabra clave.<br/>
							<b>formato</b> Formato de la salida (json o rdf).<br/>
						</td>
					</tr>
					<tr>
						<td class="table-item span-5"> HTTP Status Code:</td>
						<td>
							<b>200</b> OK.<br/>
						</td>
					</tr>
					<tr>
						<td class="table-item span-5" > Ejemplo:</td>
						<td><em> "/<c:out value="${projectName}" />/api/dataset/by/keyword/presupuesto.json" </em></td>
					</tr>
					<tr>
						<td class="table-item span-5"> Links:</td>
						<td><a target="_new" href="/<c:out value="${projectName}" />/api/dataset/by/keyword/presupuesto.json">JSON</a> | <a target="_new" href="/<c:out value="${projectName}" />/api/dataset/by/keyword/presupuesto.rdf">RDF</a></td>
					</tr>
					</table>
					</div>
					
					<hr/>
				</li>
				
				<li><h3><b>Obtener &uacute;ltimos datasets publicados</b></h3>
					<div id="" class="prepend-1 span-19 append-1 append-bottom prepend-top">
					<table>
					<tr>
						<td class="table-item span-5"> URL:</td>
						<td>/<c:out value="${projectName}" />/api/dataset/latest.json</td>
					</tr>
					<tr>
						<td class="table-item span-5"> M&eacute;todo:</td>
						<td>GET</td>
					</tr>
					<tr>
						<td class="table-item span-5"> HTTP Status Code:</td>
						<td>
							<b>200</b> OK.<br/>
						</td>
					</tr>
					<tr>
						<td class="table-item span-5" > Ejemplo:</td>
						<td><em>"/<c:out value="${projectName}" />/api/dataset/latest.json" </em></td>
					</tr>
					<tr>
						<td class="table-item span-5"> Links:</td>
						<td> <a target="_new" href="/<c:out value="${projectName}" />/api/dataset/latest.json">JSON</a></td>
					</tr>
					</table>
					</div>
					
					<hr/>
				</li>
				
			</ul>
			
			<h2>Aplicaciones</h2>
			
			<ul>
				<li><h3>Obtener una aplicaci&oacute;n por t&iacute;tulo</b></h3>
					<div id="" class="prepend-1 span-19 append-1 append-bottom prepend-top">
					<table>
					<tr>
						<td class="table-item span-5"> URL:</td>
						<td>/<c:out value="${projectName}" />/api/dataset/by/title/{t}.json</td>
					</tr>
					<tr>
						<td class="table-item span-5"> M&eacute;todo:</td>
						<td>GET</td>
					</tr>
					<tr>
						<td class="table-item span-5"> Campos:</td>
						<td>
							<b>t</b> T&iacute;tulo de la aplicaci&oacute;n.<br/>
						</td>
					</tr>
					<tr>
						<td class="table-item span-5"> HTTP Status Code:</td>
						<td>
							<b>200</b> OK.<br/>
							<b>404</b> Recurso no encontrado.<br/>
						</td>
					</tr>
					<tr>
						<td class="table-item span-5" > Ejemplo:</td>
						<td><em> "/<c:out value="${projectName}" />/api/app/by/title/Aplicacion-1.json" </em></td>
					</tr>
					<tr>
						<td class="table-item span-5"> Links:</td>
						<td> <a target="_new" href="/<c:out value="${projectName}" />/api/app/by/title/Aplicacion-1.json">JSON</a> | <a target="_new" href="/<c:out value="${projectName}" />/api/app/by/title/Aplicacion-1.rdf">RDF</a></td>
					</tr>
					</table>
					</div>

					<hr/>
				</li>
				
			</ul>
			
			
			</div>
			
		</div>

		<div style="clear:both"></div>
		
		
		
		<c:import url = "/WEB-INF/footer.jsp" />
	</div>
		
	
	</body>
</html>
