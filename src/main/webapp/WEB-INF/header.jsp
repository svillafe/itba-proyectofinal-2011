<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<div id="headding">
<header>
		
	<div id="firstTop" class="  last" style="width: 940px; padding-right:10px">
	    <div id="no-logged-user">
			<a id="loginAnchor" class="boldLink" href="">Ingresa</a> o <a href="/<c:out value="${projectName}" />/bin/public/register"  class="boldLink"> Registrate</a>
		</div>
		<div id="logged-user">
			<form method="POST" action="../auth/logout" style="display:inline">
				<input id="logout-button" type="submit" value="logout<c:if test="${logged==true}"> (<c:out value="${username}"/>)</c:if>" class="submit" style="background:green;color:white;border:none;cursor:pointer"/>
			</form>
		</div>
	</div>
		
    <div id="menu" class="span-24 last" style="height:40px; background:white;">
      <div class="span-16">
	  <nav>    
	      <ul id="nav">
	        <li>
	        	<a href="/<c:out value="${projectName}" />/bin/main/home">Inicio</a>
	        </li>
	        <li>
	        	<a href="../search/searchDataHome">Data</a>
	        	<ul>
		            <li>
		            	<a href="../search/searchDataHome">Comenzar a buscar</a>
		            </li>
		            <li>
		            	<a href="../search/search?type=dataset&terms=">Listar Datasets</a>
		            </li>
		            <li>
	        			<a href="../dataset/add">Proponer Dataset</a>
	        		</li>
	          	</ul>
	        </li>
	        <li>
	        	<a href="../search/searchAppMain">Apps</a>
	        	<ul>
	        	 	<li>
		            	<a href="../search/searchAppMain">Comenzar a buscar</a>
		            </li>
		            <li>
		            	<a href="../search/search?type=app&terms=">Listar Aplicaciones</a>
		            </li>
		            <li>
	        			<a href="../apps/add">Proponer Aplicaci&oacute;n</a>
	        		</li>
	        		
	          	</ul>
	        </li>
	         <li>
	        	<a href="../static/api">API</a>
	        </li>
	        <li>
	        	<a href="../main/sparql">LinkedData</a>
	        </li>
	        <li>
	        	<a href="../static/contactUs">Nosotros</a>
	        </li>
			<li id="user-home-link">
    			<a href="../user/home">Usuario</a>
    		</li>
	      </ul>
      </nav>
      
      </div>
      <div class="span-7 " style="text-align: right; margin-top:4px; margin-right:0px important!;">
    
    <script type="text/javascript">
    $(document).ready(function(){
    		<c:choose>
    	<c:when test="${logged==true}">
    		$('#user-home-link').show();
    		$('#no-logged-user').hide();
    	</c:when>
    	<c:otherwise>
    		$('#logged-user').hide();
    		$('#user-home-link').hide();
    	</c:otherwise>
	</c:choose>
    
    });
    </script>  


	<form:form action="../search/search" method="get" name="searchForm" commandName="searchForm" >
		<form:input type="text" path="terms" />
		<input type="submit" id="submit" class="submit" value="Buscar" />
	</form:form>
      
      </div>
      
     <c:choose>
      <c:when test="${logged==false}">
      <div id="loginForm" >
      	<div style="width:100%;text-align:right;"><a style="color:red !important;" id="closeLoginAnchor" href="">[x]</a></div>
      	<div id="iremember">
      		<form action="../auth/login" method="post" id="userLoginForm" >
	      	
      			<label> Usuario: </label><br/>
      			<input type="text" id="username" style="width:160px" />
	      		<br/>
	      	
      			<label> Contrase&ntilde;a: </label><br/>
      			<input type="password" id="password"  style="width:160px" />
	      	
      			<div style="width:auto; text-align:center; margin-top: 3px; margin-right:40px;" > 
	      			<input type="submit" id="loginButton" value="ingresar" />
      			</div>
      			<div class="error" style="margin-right:20px;" id="login-errors">
      			
      			</div>
		
      		</form>
      		<a id="dontrememberlink" href="">Se olvido la contrase&ntilde;a?</a>
      	</div>
      	<div id="dontremember" style="padding-right:28px;">
      		Coloque su <b>direcci&oacute;n de email</b> y le ser&aacute; enviado uno para que renueve su contrase&ntilde;a.
      		<form:form action="../auth/forget" method="post" name="forgetPasswordForm" commandName="forgetPasswordForm">
      			<form:input type="text" path="email"/>
      		
      			<div style="width:auto; text-align:center; margin-top: 3px; margin-right:40px;" > 
		      		<input type="submit" value="enviar" />
      			</div>
      		
      			<form:errors path="*" />
      		</form:form>
      	
      		<a id="irememberlink" href="">Record&eacute; mi contrase&ntilde;a</a>
      	</div>

      </div>
      </c:when>
      <c:otherwise>
      </c:otherwise>
     </c:choose>
      
    </div>
	<div id="logoPan" class="span-24 last">
  		
  		<div class="span-12" style="padding-top:45px">
  			<a href="/<c:out value="${projectName}" />/bin/main/home"><img border="0" src="../../images/agendata-v2 copia.png" alt="logo" width="481" height="96" class="logo" title="logo" /></a>
  		</div>
   		<div class="span-6 " style="padding-top:50px;">
   			<div>
	   			<div class="span-2 last"  style="text-align:center">
					<span style="color:orangered; font-size:38px;" ><c:out value="${datasetsQty}"/></span>
					<h4>datasets</h4>
				</div>
				
				<div class="span-2  last" style="text-align:center">
					<span style="color:green; font-size:38px;" ><c:out value="${appsQty}"/></span>
					<h4>apps</h4>
				</div>
				
				<div class="span-2  last" style="text-align:center">
					<span style="color:gold; font-size:38px;" ><c:out value="${usersQty}"/></span>
					<h4>usuarios</h4>
				</div>
			</div>
			
   		</div>
   		<div class="span-5 append-1 last" style="text-align:right; ">
  			<a target="new" href="http://linkeddata.org/" ><img border="0" src="../../images/blue.png" alt="linkeddata" title="we will rock you!" class="logoimg" height="160" /></a>
   		</div>
   		
   		<div class="clear"/>
   		
		<div class="prepend-1">
    <a href="http://twitter.com/share" class="twitter-share-button" data-url="<c:out value="${mainUrl}"/>/main/home" data-text="Yo descubr&iacute; argendata, desc&uacute;brelo vos tambi&eacute;n" data-count="horizontal" data-lang="es">Tweet</a><script type="text/javascript" src="http://platform.twitter.com/widgets.js"></script>
	<script src="http://connect.facebook.net/en_US/all.js#xfbml=1"></script><fb:like href="<c:out value="${mainUrl}"/>/main/home" send="false" layout="button_count" width="50" show_faces="false" action="like" font="" style="top:-3px;"></fb:like>
    
    <!-- Place this tag where you want the +1 button to render -->
<g:plusone size="medium"></g:plusone>

<!-- Place this tag after the last plusone tag -->
<script type="text/javascript">
  window.___gcfg = {lang: 'es'};

  (function() {
    var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;
    po.src = 'https://apis.google.com/js/plusone.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);
  })();
</script>
		</div>
	</div>
	
</header>
</div>

