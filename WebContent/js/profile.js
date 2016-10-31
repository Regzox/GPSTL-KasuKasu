function fillMyProfile() {
	$
	.get("/KasuKasu/profile")
	.done( function (data) {
		var json = $.parseJSON(data);
		
		$('#id').html(json.success.user0.id);
		$('#firstname').html(json.success.user0.firstname);
		$('#name').html(json.success.user0.name);
		$('#email').html(json.success.user0.email);
		$('#phone').html(json.success.user0.phone);
		
	} );
	
	$
	.get("/KasuKasu/imagesservlet")
	.done( function (data) {
		var json = $.parseJSON(data);
		
		$('#image').attr('src', json.success);
		
	} );
	
}