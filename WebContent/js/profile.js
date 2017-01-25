bool=0;
if(readCookie("lang") == "en")
	bool=1;
else
	if(readCookie("lang") == "fr")
		bool=0;
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



function setUserVacationStatus(newstatus){
	$
	.post(SetUserVacationStatusServlet+ "?vacation=" + newstatus)
	.done(function (data) {
		getUserVacationStatus ();
	});
}

function getUserVacationStatus (){
	var sort_vac = "";
	var vac = "";
	if(bool==0)
		{
			sort_vac = "Sortir du mode vacances"; 
			vac = "Se mettre en mode vacances";
		}
	if(bool==1)
		{
			sort_vac = "Exit the vacation mode"; 
			vac = "Enter the vacation mode";
		}
	$
	.get(GetUserVacationStatusServlet)
	.done( function (data) {
		var vaccation = $.parseJSON(data).vacationstatus;
		if(vaccation){
			printHTML('#vacation_control',
					"<button onclick=\"setUserVacationStatus('"+(!vaccation)+"')\" id=\"vacation_control_button\" class=\"button lazyuserbutton\">"+sort_vac+"</button>");
			$("#vacation_control_button").css({
				"background-color": "#f44336",
				"color" : "white", 
				"border" : "2px solid #008CBA"
			});
			$("#vacation_control_button").hover(
					function(){$(this).css({"background-color": "white","color" : "#f44336"});}
					,
					function(){$(this).css({"background-color" : "#f44336", "color" : "white"});}
			); 
		}
		else{
			printHTML('#vacation_control',
					"<button onclick=\"setUserVacationStatus('"+(!vaccation)+"')\" id=\"vacation_control_button\"  class=\"button activuserbutton\">"+vac+"</button>");
			$("#vacation_control_button").css({
				"background-color": "white",
				"color" : "#f44336", 
				"border" : "2px solid #008CBA"
			});
			$("#vacation_control_button").hover(
					function(){$(this).css({"background-color": "#f44336","color" : "white"});}
					,
					function(){$(this).css({"background-color" : "white", "color" : "#f44336"});}
			); 
		}
	} );
}
