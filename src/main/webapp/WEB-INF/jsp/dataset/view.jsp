<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Argendata -  <c:out value="${dto.dataset.title}" /></title>
		<c:import url = "/WEB-INF/head.jsp" />
	</head>
	<body>
	<div class="container">
		<c:import url = "/WEB-INF/header.jsp" />

		<div id="cuerpo" class="span-24">
			<div id="breadcrumb" class= "prepend-1 span-22 append-1" >
				Dataset > <c:out value="${dto.dataset.title}" />
			</div>
		
    
		<div class="prepend-1 prepend-top">
		<br/>
		<h2><c:out value="${dto.dataset.title}" /></h2>
		</div>
		
			    		    

		<div class="prepend-1 span-15 append-1 append-bottom">
			
			<span class="error" id="msjError"></span>
			<c:if test="${admin}"> 
				<div id="adminBotonera">
					<a style="color:orangered" href="../admin/editDataset?qName=Dataset:<c:out value="${dto.dataset.titleId}" />">Editar</a>  &nbsp;&nbsp; |
					<a style="color:orangered" id="deleteButton" href="">Eliminar</a>  &nbsp;&nbsp; 
					<!-- <form  style="display:inline" action="../admin/deleteDataset" method="POST"><input type="hidden" name="qName" value="Dataset:<c:out value="${dto.dataset.titleId}" />"/><input type="submit" value="Eliminar" style="border:none;background:none;cursor:pointer;text-decoration:underline;font-size:inherit;color:orangered;" /></form> -->
				</div>
			</c:if>
			
			<c:if test="${logged}">
	 				<a href="../feedback/reportProblem?type=dataset&title=<c:out value="${dto.dataset.title}" />">Reportar un problema</a><br/>
	 			</c:if>
			
			<div>
			<br />
			<i><c:out value="${dto.dataset.description}" /></i><br/><br/>
				<b>Publicante:</b> <a href="../search/search?type=dataset&terms=&filters=publisher:<c:out value="${dto.dataset.publisher.name}" />&sortBy=null&page=1&resPerPage=10&keyword=" ><c:out value="${dto.dataset.publisher.name}" /></a>
				
				<br/></br>
				<b>Palabras claves:</b> 
				<p>
				
				<ul>
				<c:forEach items="${dto.dataset.keyword}" var="keyword">
                       <li> <a href="../search/search?type=dataset&terms=&filters=&sortBy=null&page=1&resPerPage=10&keyword=<c:out value="${keyword}" />"><c:out value="${keyword}" /></a> </li> 
                </c:forEach>
				</ul>
				</p>
				
				
				<b>Granularidad temporal:</b>  <a href="../search/search?type=dataset&terms=&filters=temporal:<c:out value="${dto.dataset.temporal}" />&sortBy=null&page=1&resPerPage=5&keyword=" ><c:out value="${dto.dataset.temporal}" /></a>
			
				<br/><br/>
				<b>Granularidad espacial:</b>  <a href="../search/search?type=dataset&terms=&filters=spatial:<c:out value="${dto.dataset.spatial}" />&sortBy=null&page=1&resPerPage=5&keyword=" ><c:out value="${dto.dataset.spatial}" /></a>
				
				<br/><br/>
				<b>Ubicaci&oacute;n:</b>  <a href="../search/search?type=dataset&terms=&filters=spatial:<c:out value="${dto.dataset.spatial}" />&sortBy=null&page=1&resPerPage=5&keyword=" ><c:out value="${dto.dataset.location}" /></a>
								
				<br/><br/>
				<b>Calidad:</b>  <a href="../search/search?type=dataset&terms=&filters=dataQuality:<c:out value="${dto.dataset.dataQuality}" />&sortBy=null&page=1&resPerPage=5&keyword="><c:out value="${dto.dataset.dataQuality}" /></a>
				
				<br/></br>
				<b>URL:</b> 
				
				<c:choose>
					<c:when test="${dto.isDoc==true}">
						<c:out value="${dto.dataset.distribution.accessURL}" /><br/>
						<a target="_new" href="../external/doc?qName=Dataset:<c:out value="${dto.dataset.titleId}" />" >Ver archivo</a> | <a target="_new" href="<c:out value="${dto.dataset.distribution.accessURL}" />" >Descargar archivo</a>
						<br/> 
						<c:out value="${dto.dataset.distribution.size}" /> <i>(<c:out value="${dto.dataset.distribution.format}" />)</i>
					</c:when>
					<c:otherwise>
						<a target="_new" href="../external/link?qName=Dataset:<c:out value="${dto.dataset.titleId}" />" ><c:out value="${dto.dataset.distribution.accessURL}" /></a>
					</c:otherwise>
				</c:choose>
				
				
				<br/></br>
				<i> &Uacute;ltima Modificaci&oacute;n: <c:out value="${datasetDate}" /> </i>
			</div>
			
			
			
			<br/><hr/><br/>
			<h3>Comentarios</h3>
			<!-- --- --- --- DISQUS --- --- --- -->
			<div id="disqus_thread"></div>
			<script type="text/javascript">
			    var disqus_shortname = "argendata"; // required: replace example with your forum shortname
			
				var disqus_developer = 1; // developer mode is on
			
			    var disqus_identifier = 'argendata_<c:out value="${dto.dataset.titleId}" />';
			    var disqus_url = 'http://www.argendata.org.ar/dataset/view?qName=Dataset<c:out value="${dto.dataset.titleId}" />';
			
			    (function() {
			        var dsq = document.createElement('script'); dsq.type = 'text/javascript'; dsq.async = true;
			        dsq.src = 'http://' + disqus_shortname + '.disqus.com/embed.js';
			        (document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(dsq);
			    })();
			</script>
			<noscript>Please enable JavaScript to view the <a href="http://disqus.com/?ref_noscript">comments powered by Disqus.</a></noscript>
			<a href="http://disqus.com" class="dsq-brlink">blog comments powered by <span class="logo-disqus">Disqus</span></a>
			
		
  	</div>
  	<div class="span-6 append-1 last">
  			<div>
  				<h4>Compartir recurso:</h4>
				<a target="new" href="mailto:?subject=Información encontrada en argendata&body=Mirá lo que encontré en argendata: <c:out value="${mainURL}" />/dataset/view?qName=Dataset:<c:out value="${dto.dataset.titleId}"/>"><img border="0"  style="padding-top: 4px" height="32px" title="email"  alt="email" src="../../images/email.png"/></a>
				<span>&nbsp;&nbsp;</span>
					<a href="http://twitter.com/intent/tweet?url=<c:out value="${mainURL}" />/dataset/view?qName=Dataset:<c:out value="${dto.dataset.titleId}"/>&text=Mirá el dataset que descubrí en argendata:&via=argendata">
					
					<img border="0"  style="padding-top: 4px" height="32px" title="twitter"  alt="twitter" src="../../images/twitter-2.png"/></a>
				
				<span>&nbsp;&nbsp;</span>
				<a target="new" href="http://www.facebook.com/sharer.php?u=<c:out value="${mainURL}" />/dataset/view?qName=Dataset:<c:out value="${dto.dataset.titleId}"/>"><img border="0" style="padding-top: 4px" height="32px" title="facebook"  alt="facebook" src="../../images/facebook.png"/></a>
				<br/>
			</div>
			<br/>
			<div>
  				<h4>Us&aacute; nuestra informaci&oacute;n:</h4>
  				
				<a target="_blank" href="/<c:out value="${projectName}" />/api/dataset/by/title/<c:out value="${dto.dataset.titleId}" />.json">JSON</a> | <a target="_blank" href="/<c:out value="${projectName}"/>/api/dataset/by/title/<c:out value="${dto.dataset.titleId}" />.rdf">RDF</a> 
				
				
				<br/>
			</div>
  	</div>
  
	<div style="clear:both"></div>
		
		<c:import url = "/WEB-INF/footer.jsp" />
	</div>
		
	<script>
	$("#deleteButton").click(function(){
		$.ajax({
			url:'../admin/deleteDataset',
			type:'POST',
			data:'qName=Dataset:<c:out value="${dto.dataset.titleId}" />',
			success:function(data){
				
				if(data=='0'){
    				window.location.href='../search/search?type=dataset&terms=';
    			}
    			else if(data=='-1'){
    				window.location.href='../dataset/error';
    			}else if(data=='-2'){
    				window.location.href='../bin/static/error';
    			}
    			else{
    				var i=0;
    				var cadena="El dataset no se puede eliminar por que esta siendo apuntado por las siguientes aplicaciones: ";
    				cadena =cadena + data;
    				
    				
    				$('#msjError').html(cadena);
    			}
    			
			},
			error:function(){
				$("#login-errors").append("Ha ocurrido un error, reint&eacute;nte.");
			}
		});
		return false;
	});
	</script>
		
	</body>
</html>
