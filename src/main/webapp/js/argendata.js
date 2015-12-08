$(document).ready(function() {

	

	$('#loginForm').hide();
	$('#dontremember').hide();

    	
	$("#loginButton").click(function(){
		$.ajax({
			url:'../auth/login',
			type:'POST',
			data:'username='+$('input#username').val()+'&password='+$('input#password').val(),
			success:function(data){
				
    			if(data=='-1'){
    				window.location.href='../static/error';
    			}
    			else if(data=='-2'){
    				$("#login-errors").html("Cuenta no activada.");
    			}
    			else if(data=='-3'){
    				$("#login-errors").html("Usuario o contrase&ntilde;a incorrectos.");
    			}	
    			else{
    				$('input#username').val('');
    				$('input#password').val('');
    				$('#loginForm').hide();
    				$('#dontremember').hide();
    				$('#no-logged-user').hide();
    				$('#login-errors').hide();
    				$('#logged-user').show();
    				$('#user-home-link').show();
    				$('#logout-button').val("logout ("+data+")");
    			}
    			
			},
			error:function(){
				$("#login-errors").append("Ha ocurrido un error, reint&eacute;nte.");
			}
		});
		return false;
	});	

	$("#dontrememberlink").click(function() {
		
		$('#iremember').hide();
		$('#dontremember').fadeIn("slow");
		
		return false;
	});

	$("#irememberlink").click(function() {
		
		$('#dontremember').hide();
		$('#iremember').fadeIn("slow");
		
		return false;
	});

	$("#loginAnchor").click(function() {
		
		$('#loginForm').slideToggle("slow");
		$('input#username').focus();
		
		return false;
	});
	
	$("#closeLoginAnchor").click(function() {
		
		$('#loginForm').slideToggle("slow");
		
		return false;
	});
	
	
});