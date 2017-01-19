var checkConnection = function () {
	$.get(CheckConnectionServlet)
	.done(function (data) {
		var json = $.parseJSON(data);
		if (json.error != undefined)
			window.location.href = portal_jsp;
		if (json.success != undefined)
			return json;
	});
}