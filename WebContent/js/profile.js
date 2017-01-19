function fillMyProfile() {
	
	$
	.get(UserProfileServlet)
	.done( function (data) {
		var json = $.parseJSON(data);
		
		//$('#id').html(json.success.user0.id);
		$('#firstname').html(json.success.user0.firstname);
		$('#name').html(json.success.user0.name);
		$('#email').html(json.success.user0.email);
		$('#phone').html(json.success.user0.phone);
	} );
	
	$
	.get(ImagesServlet)
	.done( function (data) {
		var json = $.parseJSON(data);
		if (json.success!=undefined) $('#image').attr('src', json.success);
		else $('#image').attr('src', "/KasuKasu/data/profile-icon.png");
		
	} );
}

function fillProfileById(user_id) {
	$
	.get(UserProfileServlet + "?id=" + user_id)
	.done( function (data) {
		var json = $.parseJSON(data);
		
		//$('#id').html(json.success.user0.id);
		$('#firstname').html(json.success.user0.firstname);
		$('#name').html(json.success.user0.name);
		$('#email').html(json.success.user0.email);
		$('#phone').html(json.success.user0.phone);
		
	} );
	
	$
	.get(ImagesServlet + "?id=" + user_id)
	.done( function (data) {
		var json = $.parseJSON(data);
		
		$('#image').attr('src', json.success);
		
	} );
}