<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<div id="headding">
<header>
		
	<div id="firstTop" class="  last" style="width: 940px; padding-right:10px">
	<c:choose>
	      <c:when test="${logged==false}">
			<a id="loginAnchor" class="boldLink" href="">Ingresa</a> o <a href="../public/register"  class="boldLink"> Registrate</a>
		</c:when>
		<c:otherwise>
			<form method="POST" action="../auth/logout" style="display:inline">
				<input type="submit" value="logout" class="submit" style="background:green;color:white;border:none;cursor:pointer"/>
			</form>
		</c:otherwise>
	</c:choose>
	</div>
		
    <div id="menu" class="span-24 last" style="height:40px; background:white;">
      <div class="span-16">
	  <nav>    
	      <ul id="nav">
	        <li>
	        	<a href="../main/home">Inicio</a>
	        </li>
	        <li>
	        	<a href="#">Data</a>
	        	<ul>
		            <li>
		            	<a href="../dataset/findAll">Listar Datasets</a>
		            </li>
		            <li>
	        			<a href="../dataset/add">Proponer Dataset</a>
	        		</li>
	        		<li>
	        			<a href="../dataset/bulkUpload">Carga Masiva</a>
	        		</li>
	          	</ul>
	        </li>
	        <li>
	        	<a href="../search/view">Buscar</a>
	        </li>
	        <li>
	        	<a href="#">Aplicaciones</a>
	        	<ul>
		            <li>
		            	<a href="../apps/listAll">Listar Aplicaciones</a>
		            </li>
		            <li>
	        			<a href="../apps/add">Proponer Aplicaci&oacute;n</a>
	        		</li>
	        		
	          	</ul>
	        </li>
	        <li>
	        	<a href="../static/contactUs">Qui&eacute;nes somos</a>
	        </li>
	        <c:choose>
		    	<c:when test="${logged==true}">
					<li>
	        			<a href="../user/home">Usuario</a>
	        		</li>
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose>
	      </ul>
      </nav>
      
      </div>
      <div class="span-7 " style="text-align: right; margin-top:4px; margin-right:0px important!;">
    
      
	<!-- <form method="get" action="../search/search" id="search">
		<input type="input" id="terms" name="terms"  />
		<input type="submit" id="submit" class="submit" value="Buscar" />
		<br/>
		
	<form> -->
	<form:form action="../search/search" method="get" name="searchForm" commandName="searchForm" >
	<form:input type="text" path="terms" />
	<input type="submit" id="submit" class="submit" value="Buscar" />
	
	</form:form>
      
      </div>
      
     <c:choose>
      <c:when test="${logged==false}">
      <div id="loginForm" >
      	<div style="width:100%;text-align:right;"><a style="color:red !important;" id="closeLoginAnchor" href="">[x]</a></div>
      	
      	<form:form action="../auth/login" method="post" name="userLoginForm" commandName="userLoginForm">
      	
      		<label> Usuario: </label><br/>
      		<form:input type="text" path="username" />
	      	<br/>
      	
      		<label> Contrase&ntilde;a: </label><br/>
      		<form:input type="password" path="password" />
	      	
      		<div style="width:auto; text-align:center; margin-top: 3px; margin-right:40px;" > 
	      		<input type="submit" value="ingresar" />
      		</div>
      		
      		<form:errors path="*" />
		
      	</form:form>
      	<a>¿Se olvido la contrase&ntilde;a?</a>
      </div>
      </c:when>
      <c:otherwise>
      </c:otherwise>
     </c:choose>
      
    </div>
	<div id="logoPan" class="span-24 last">
  		<div class="span-12" style="padding-top:45px">
  		<a href="../main/home"><img border="0" src="../../images/agendata-v2 copia.png" alt="logo" width="481" height="96" class="logo" title="logo" /></a>
  		</div>
  		<div class="span-11 append-1 last" style="text-align:right; ">
  		<a href="http://linkeddata.org/" ><img border="0" src="../../images/blue.png" alt="linkeddata" title="we will rock you!" class="logoimg" height="160" /></a>
   		</div>
	
	</div>
  
	  
	
</header>
</div>

