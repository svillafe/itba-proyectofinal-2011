<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Argendata</title>
		<c:import url = "/WEB-INF/head.jsp" />
		<script type="text/javascript" src="http://www.google.com/jsapi"></script>
    <script type="text/javascript">
      google.load('visualization', '1', {packages: ['corechart']});
    </script>
	</head>
	<body>
	<div class="container">
		<c:import url = "/WEB-INF/header.jsp" />

		<div id="cuerpo" class="span-24">
			<div id="breadcrumb" class= "prepend-1 span-22 append-1 " >
				Inicio >
			</div>
			
			<div style="margin:40px; ">
			<h1> Inicio </h1>
			<p class="highlighted">
				Bienvenido a ARGENDATA, el cat&aacute;logo de datasets y aplicaciones que utilizan informaci&oacute;n p&uacute;blica de la Rep&uacute;blica Argentina. Particip&aacute; activamente de nuestra comunidad para brindar mayor transparencia a nuestros gobiernos. 
				<br/>
			</p>
			 <div id="whatis" class="span-22 last append-bottom">

				<div class="sectionTitle span-10 append-2" style="text-align:justify;">
					<h4>&iquest;Qu&eacute; hace ARGENDATA?</h4>
					<div style="margin:15px;">
						Busca reunir los datasets que se encuentran diseminados en diferentes sitios gubernamentales y diversos formatos, extraer de ellos informaci&oacute;n &uacute;til para transformarla en datos usables, accesibles y comprendibles por humanos y computadoras.</br>
						<div style="margin:0px auto;padding:0px; text-align:center;"><img src="../../images/whatis.png" alt="quees"/>
						</div>
					</div>
				</div>
				
				<div class="sectionTitle prepenend-1 span-10 last" style="text-align:justify">
				<h4>&iquest;Qu&eacute; puedo hacer?</h4>
					<div style="margin:15px;">
						<p> <b>ARGENDATA</b> te permite <b>navegar la informaci&oacute;n</b> para ver que datasets est&aacute;n disponibles.</p>
						<p> Cuenta con un endpoint el cual responde <b>consultas</b> de tipo <i><a target="_new" href="http://www.w3.org/TR/rdf-sparql-query/">SPARQL</a></i> sobre los datos almacenados en <i>RDF</i>.</p>
						<p> Pod&eacute;s <b>contribuir</b> con datasets que no existan. Para esto solo ten&eacute;s que <a href="../public/register">registrarte</a>
					y proponer tu dataset.</p>
						<p> Cre&aacute; tu <b> aplicaci&oacute;n</b> utilizando los datos publicados y avisanos que la pondremos en nuestra <i>galer&iacute;a</i>.</p>
						
					</div>
					
				</div>
				<div class="sectionTitle span-22" style="text-align:justify;">
					<h4>Estad&iacute;sticas</h4>
					<div class="span-10">
						<script type="text/javascript">
						$(document).ready(function(){
							function drawVisualization2() {
							  // Create and populate the data table.
							  var data = new google.visualization.DataTable();
							  data.addColumn('string', 'Provincia');
							  data.addColumn('number', 'Datasets');
							  data.addRows(24);
							  <c:forEach items="${dataSession.provincias}" var="provincia" varStatus="status" begin="0">
							  data.setValue(<c:out value="${status.count-1}"/>, 0, '<c:out value="${provincia.key}"/>');
							  data.setValue(<c:out value="${status.count-1}"/>, 1, <c:out value="${provincia.value}"/>);
							  </c:forEach>
							
							  // Create and draw the visualization.
							  new google.visualization.PieChart(document.getElementById('visualization-pie')).
							      draw(data, {title:"Datasets por provincia",width: 380, height: 400});
							}
							drawVisualization2();
						});
						</script>
						<div id="visualization-pie">
						</div>
					</div>
					
					<div class="span-3 ">
					
						<div class="span-2 last"  style="text-align:center">
							<span style="color:orangered; font-size:58px;" ><c:out value="${dataSession.datasetsQty}"/></span>
							<h3>datasets</h3>
						</div>
						<div class="clear"></div>
						<div class="span-2  last" style="text-align:center">
							<span style="color:turquoise; font-size:58px;" ><c:out value="${dataSession.appsQty}"/></span>
							<h3>apps</h3>
						</div>
						<div class="clear"></div>
						<div class="span-2  last" style="text-align:center">
							<span style="color:gold; font-size:58px;" ><c:out value="${dataSession.usersQty}"/></span>
							<h3>usuarios</h3>
						</div>
					</div>
					
					<div class="span-8 last">
						<script type="text/javascript"> $(document).ready(function(){function drawVisualization() {
	  						// http://code.google.com/apis/chart/interactive/docs/reference.html#visdraw
	  						var data = new google.visualization.DataTable();
						  data.addColumn('string','Dia');
						  data.addColumn('number','Datasets');
						  data.addRows(7);
						  <c:forEach items="${dataSession.sevendays}" var="day" varStatus="status" begin="0">
						  	data.setValue(<c:out value="${status.count-1}" />,0,"<c:out value="${-7 + status.count}" />");
						  	data.setValue(<c:out value="${status.count-1}" />,1, <c:out value="${day}"/> );
						  </c:forEach>
						 
						  // Create and draw the visualization.
						  new google.visualization.LineChart(document.getElementById('visualization')).
						      draw(data, {curveType: "function",
						                  width: 380, height: 400,
						                  title:'Datasets agregados en la ultima semana',
						                  colors:['YellowGreen','#9ACD32'] }
						          );
						}
							drawVisualization();
						});
						</script>
						<div id="visualization">
						</div>
					</div>
					
					<div class="clear"><p><br/></p></div>	
					
					<div class="prepend-1 append-1  span-20 last">
					
					<div style="text-align:center"><b> Usuarios que m&aacute;s datasets propusieron</b></br><br/></div>
					<c:forEach items="${dataSession.topDatasetsPublishers}" var="user" varStatus="status" begin="0">
					 <span class="span-4 last" style="font-size:140%; font-weight:bold;"><c:out value="${status.count}"/>. <a href="../user/profile?username=<c:out value="${user.username}"/>"><c:out value="${user.username}"/></a> (<c:out value="${user.datasetsQty}"/>)</span>
					</c:forEach>
					</div>
					
				</div>
			
				
				<div class="clear"><p><br/></p></div>
			
				<div class="sectionTitle span-22" style="text-align:justify;">
					<h4 >&iquest;A qu&eacute; tipo de informaci&oacute;n puedo acceder seg&uacute;n la ley?</h4>
					<div style="margin:15px;">
						El <em>Decreto 1172/2003</em> establece que todos los ciudadanos pueden acceder libremente a <strong>cualquier tipo de informaci&oacute;n</strong> (documentos escritos, fotogr&aacute;ficos, grabaciones, soporte magn&eacute;tico, digital, o en cualquier otro formato) creada u obtenida por el Poder Ejecutivo Nacional o que obre en su poder o bajo su control o cuya producci&oacute;n haya sido financiada total o parcialmente con fondos p&uacute;blicos.<br>
						<br>
						La <em>Ley de Responsabilidad Fiscal</em> manifiesta en su Art&iacute;culo 8 que el Estado Nacional debe permitir el libre acceso a informaci&oacute;n completa y detallada sobre la administraci&oacute;n de los recursos p&uacute;blicos, incluyendo los estados de ejecuci&oacute;n presupuestaria, &oacute;rdenes de compra, datos referidos al personal estatal y an&aacute;lisis de la deuda p&uacute;blica, entre otros. ( <a href="http://www.argentina.gov.ar/argentina/portal/paginas.dhtml?pagina=308">Ir</a> )
						<br>
						
					</div>
				</div>

				
				<div class="clear"><p><br/></p></div>
				
			</div>
			
			<div class="sectionTitle span-22 ">
				<h4>Datasets m&aacute;s recientes</h4>
			</div>
			<div id="homeDatasets" class= "span-22 " >
				
				<div>
					<c:forEach items="${dataSession.datasets}" var="dataset">
					<a style="text-decoration:none;" href="../dataset/view?qName=Dataset:<c:out value="${dataset.titleId}"/>">
					<div class="miniDataset column span-6">
					<div >
						<img src="../../images/minidataset.png"/>
					</div>
					<div class="miniTitle" >
						<h3><c:out value="${dataset.title}"/></h3>
					</div>
					<hr/>
					<p>
						<span style="color:#333366;"><b>Publicante:</b> <c:out value="${dataset.publisher.name}"/></span><br/>
					</p>
					<p style="text-align:center; color:#525252;">
						<c:out value="${dataset.description}"/>
					</p>
					</div>
					</a>
					</c:forEach>
				</div>
				<div class="clear"></div>
				<div style="margin-top:10px;" ><br/>
					Estos algunos de los datasets que contamos en nuestra base. Ingresa a la secci&oacute;n <a href="../search/search?type=dataset&terms=">Datasets</a> para ver el resto!
				</div>
				<div class="clear"><p><br/></p></div>
			</div>
		
			<div class="clear"></div>
			
			<div class="sectionTitle span-22 ">
				<h4>Aplicaciones m&aacute;s recientes</h4>				
			</div>
			<div id="homeApps" class= " span-22 " >
				<div>
					<c:forEach items="${dataSession.apps}" var="app">
					<a style="text-decoration:none;" href="../apps/detail?qName=Semanticapp:<c:out value="${app.nameId}"/>">
					<div class="miniApp column span-6">
					<div >
						<img src="../../images/miniapp.png"/>
					</div>
					<div class="miniTitle">
						<h3><c:out value="${app.name}"/></h3>
					</div>
					<hr/>
					<p style="text-align:center !important;">
						<img class="appScreenHomeDetail" style="width:200px;" src="../apps/screenshotOfApp?idApp=Semanticapp:<c:out value="${app.nameId}"/>" /><br/><br/>
						<span style=""><c:out value="${app.url}"/></span><br/>
						
					</p>
					</div>
					</a>
					</c:forEach>
				</div>
				
				<div class="clear"></div>
				<div style="margin-top:10px;" ><br/>
					Para ver m&aacute;s aplicaciones realizadas a partir de la informaci&oacute;n almacenada en nuestro store visti&aacute; la secci&oacute;n de <a href="../search/search?type=app&terms=">Apps</a>.
				</div>
				<div class="clear"><p><br/></p></div>
			</div>
		
			</div>
			
		</div>

		<div style="clear:both"></div>
		
		
		
		<c:import url = "/WEB-INF/footer.jsp" />
	</div>
		
	
	</body>
</html>
