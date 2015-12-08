<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Argendata - <c:out value="${app.name}" /></title>
	<c:import url="/WEB-INF/head.jsp" />
</head>
<body>

	<div class="container">
		<c:import url="/WEB-INF/header.jsp" />

		<div id="cuerpo" class="span-24">

			<div id="breadcrumb" class="prepend-1 span-22 append-1">
				Apps > <c:out value="${app.name}" />
			</div>

			<div class="prepend-1 prepend-top">
			<br/>
				<h2><img class="appIconDetail" src="../apps/iconOfApp?idApp=Semanticapp:<c:out value="${app.nameId}"/>"  /><c:out value="${app.name}" /></h2>
			</div>

			<div class="prepend-1 span-15 append-1 append-bottom">

				<div class="">		
					<c:if test="${admin}"> 
						<div id="adminBotonera">
							<a style="color:orangered" href="../admin/editApp?qName=Semanticapp:<c:out value="${app.nameId}" />">Editar</a>  &nbsp;&nbsp; |
							<form style="display:inline" action="../admin/deleteApp" method="POST"><input type="hidden" name="qName" value="Semanticapp:<c:out value="${app.nameId}" />"/><input type="submit" value="Eliminar" style="border:none;background:none;cursor:pointer;text-decoration:underline;font-size:inherit;color:orangered;" /></form>
						</div>
					</c:if>
	
		 			<c:if test="${logged}">
		 				<a href="../feedback/reportProblem?type=Aplicacion&title=<c:out value="${app.name}" />">Reportar un problema</a><br/>
		 			</c:if>
		 			
					<br/>
				</div>
				
					<i><c:out value="${app.description}" /></i><br/><br/>
				<div class="span-8">
				
					
					<b>Publicante:</b> <c:out value="${app.publisher.name}" /> 
					<br /><br />
					
					<b>URL:</b> <a target="_new" href="../external/link?qName=Semanticapp:<c:out value="${app.nameId}" />">
						<c:out value="${app.url}" /></a> 
					<br /><br />
					
					<b>Screenshot:</b> <c:out value="${screenError}" /> 
					<br /><br />
					
					<a target="new" href="../apps/screenshotOfApp?idApp=Semanticapp:<c:out value="${app.nameId}"/>"><img class="appScreenDetail" src="../apps/screenshotOfApp?idApp=Semanticapp:<c:out value="${app.nameId}"/>" /></a><br />
					<br />

				</div>

				<div class="span-4 append-2 " >
					<b>Palabras claves:</b> 
					<p>
					
					<ul>
					<c:forEach items="${app.keyword}" var="keyword">
	                       <li> <a href="../search/search?type=app&terms=&filters=&sortBy=null&page=1&resPerPage=10&keyword=<c:out value="${keyword}" />"><c:out value="${keyword}" /></a> </li> 
	                </c:forEach>
					</ul>
					</p>
					
					<b>Dataset que utiliza:</b> 
					<p>
					
					<ul>
					<c:forEach items="${datasets}" var="dataset">
	                       <li> <a href="../dataset/view?qName=Dataset:<c:out value="${dataset.titleId}"/>"><c:out value="${dataset.title}" /></a> </li> 
	                </c:forEach>
					</ul>
					</p>
				</div>
				<div class="clear"></div>
				<div>	
					<br/><hr/><br/>
					<h3>Comentarios</h3>
					<!-- --- --- --- DISQUS --- --- --- -->
					<div id="disqus_thread"></div>
					<script type="text/javascript">
					    var disqus_shortname = "argendata"; // required: replace example with your forum shortname
					
						var disqus_developer = 1; // developer mode is on
					
					    var disqus_identifier = 'argendata_apps_<c:out value="${app.nameId}"/>';
					    var disqus_url = 'http://www.argendata.org.ar/apps/detail?qName=Semanticapp:<c:out value="${app.nameId}"/>"';
					
					    (function() {
					        var dsq = document.createElement('script'); dsq.type = 'text/javascript'; dsq.async = true;
					        dsq.src = 'http://' + disqus_shortname + '.disqus.com/embed.js';
					        (document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(dsq);
					    })();
					</script>
					<noscript>Please enable JavaScript to view the <a href="http://disqus.com/?ref_noscript">comments powered by Disqus.</a></noscript>
					<a href="http://disqus.com" class="dsq-brlink">blog comments powered by <span class="logo-disqus">Disqus</span></a>
				</div>
	</div>		
	<div class="span-5 prepend-1 last" >
		<div>
			<h4>Compartir recurso:</h4>
			
			<a
				target="new"  href="mailto:?subject=Información encontrada en argendata&body=Mirá lo que encontré en argendata: <c:out value="${mainURL}"/>/bin/external/linkWP?webPage=<c:out value="${app.url}" />">
			<img border="0" style="padding-top: 4px" height="32px" title="email"
				alt="email" src="../../images/email.png" /></a> <span>&nbsp;&nbsp;</span>
			<a
				target="new"  href="http://twitter.com/intent/tweet?url=<c:out value="${mainURL}"/>/apps/detail?qName=Semanticapp:<c:out value="${app.nameId}"/>&text=Mirá la aplicación que descubrí en argendata:&via=argendata">
			<img border="0" style="padding-top: 4px" height="32px" title="twitter"
				alt="twitter" src="../../images/twitter-2.png" /></a> <span>&nbsp;&nbsp;</span>
			<a
				target="new" href="http://www.facebook.com/sharer.php?u=<c:out value="${mainURL}"/>/apps/detail?qName=Semanticapp:<c:out value="${app.nameId}"/>&t=Lo%20que%20descubri%20en%20argendata">
			<img border="0" style="padding-top: 4px" height="32px" title="facebook"
				alt="facebook" src="../../images/facebook.png" /></a>
		
		</div>
		<br/>
		<div>
  				<h4>Us&aacute; nuestra informaci&oacute;n:</h4>
  				
				<a class="appLinkJson" href="../../api/app/by/title/<c:out value="${app.nameId}" />.json">JSON</a>
				
				<br/>
		</div>
	</div>
	</div>
<div style="clear: both">
<c:import url="/WEB-INF/footer.jsp" />
</div>

</body>
</html>
