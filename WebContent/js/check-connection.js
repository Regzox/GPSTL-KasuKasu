var checkConnection = function () {
	$.get("/KasuKasu/check-connection")
	.done(function (data) {
		var json = $.parseJSON(data);
		console.log(json);
		if (json.error != undefined)
			window.location.href = "/KasuKasu/portal.jsp";
	});
}