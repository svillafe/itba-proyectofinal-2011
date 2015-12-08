$(document).ready(function() {

	

	$('#loginForm').hide();

	$("#loginAnchor").click(function() {
		
		$('#loginForm').slideToggle("slow");
		
		return false;
	});
	
	$("#closeLoginAnchor").click(function() {
		
		$('#loginForm').slideToggle("slow");
		
		return false;
	});
	
	
});