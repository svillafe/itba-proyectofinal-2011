<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Argendata - Externo</title>
		<c:import url = "/WEB-INF/head.jsp" />
		<script type="text/javascript">
		$(document).ready(

	function(){
		var g=setTimeout(function(){
				$("div.avisoMsg").slideDown(
						"slow",
						function(){var h=setTimeout(function(){$("div.avisoMsg").slideUp()},
						10000
				)
				})
			},
			5000);

		c();
		window.onresize=c
	}
);

	function c(){
		var g=f();
		var h=document.getElementById("frame");
		h.height=g-document.getElementById("bar").clientHeight-5+"px";
		return false
		}

	function f(){
		var g=0;
		if(typeof(window.innerWidth)=="number"){
			g=window.innerHeight
		}
		else{
			if(document.documentElement&&(document.documentElement.clientWidth||document.documentElement.clientHeight)){
				g=document.documentElement.clientHeight
			}
			else{
				if(document.body&&(document.body.clientWidth||document.body.clientHeight)){
					g=document.body.clientHeight
				}
			}
		}
		return g
	}
		</script>
	</head>
	<body>
	<div id="bar" style="height:90px; border-bottom: 3px solid snow; background:#ccc url(../../images/stripe-frame.png) repeat-x;">
		<div class="span-14 prepend-top column">
			<a href="/<c:out value="${projectName}" />/bin/main/home">
				<img border="0" width="275" height="55" title="logo" class="logo" alt="logo" src="../../images/agendata-mini.png">
			</a>
			
		</div>
		<div class="span-2 column">
		<!--
			<img border="0" src="../../images/blue-mini.png" alt="linkeddata" title="argendata" class="logoimg" height="60" />
		-->
		</div>
		<div class="span-12" style="padding-top:6px; text-align:right" >
			<h3 style="    text-shadow: 1px 1px 1px #5B5B5B; color:snow	">Compartilo por:</h3> 
			<a href="mailto:?subject=Informacion encontrada en argendata&body=Mira lo que encontre en argendata: <c:out value="${mainURL}" />/external/linkWP?webPage=<c:out value="${url}" />">
				<img border="0"  style="padding-top: 4px" height="32px" title="email"  alt="email" src="../../images/email.png">
			</a>
			<span>&nbsp;&nbsp;</span>
			<a href="http://twitter.com/home?status=Mira lo que descubri en argendata: <c:out value="${mainURL}" />/external/linkWP?webPage=<c:out value="${url}" />... via @argendata">
				<img border="0"  style="padding-top: 4px" height="32px" title="twitter"  alt="twitter" src="../../images/twitter-2.png">
			</a>
			<span>&nbsp;&nbsp;</span>
			<a href="http://www.facebook.com/sharer.php?u=<c:out value="${mainURL}" />/external/linkWP?webPage=<c:out value="${url}"/>/&t=Lo%20que%20descubri%20en%20argendata">
				<img border="0" style="padding-top: 4px" height="32px" title="facebook"  alt="facebook" src="../../images/facebook.png">
			</a>
		</div>
		<div class="span-2 column prepend-top last" style="float:right; border-left:1px dashed #DDE4EB; padding-left:26px; padding-top:4px">
			<a href="<c:out value="${url}" />" >
				<img border="0" title="close" alt="close" src="../../images/close.png">
			</a>
		</div>
	</div>
	 <iframe id="frame" width="100%" height="0" frameborder="0" src="<c:out value="${url}" />" ></iframe>  			
	
	</body>
</html>
