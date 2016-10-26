var checkConnection = function () {
	$.get("/KasuKasu/check-connection")
	.done(function (data) {
		var json = $.parseJSON(data);
		if (json.error != undefined)
			window.location.href = "/KasuKasu/portal.jsp";
		if (json.success != undefined)
			return json;
	});
}