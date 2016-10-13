var clearNotifier = function () {
	setTimeout(() => {
		$("#notifier").html('');
	}, 10000);
}

var encrypt = function (algorithm, value) {
	switch (algorithm) {
	case "md5" :
		return md5(value);
	default :
		return value;
	}
}

var submit = function () {
	
	var oe = $("#old-email-input").val(),
		op = $("#old-password-input").val(),
		ne = $("#new-email-input").val(),
		np = $("#new-password-input").val()
		nn = $("#new-name-input").val(),
		nf = $("#new-firstname-input").val(),
		nph = $("#new-phone-input").val(),
		wrg = "",
		algenc = "none";
	
	if (oe == "")
		wrg += "No current email typed <br/>";
	
	if (op != "")
		op = encrypt(algenc, op);
		
	else
		wrg += "No current password typed <br/>";
	
	if (ne == "" && np == "" && nn == "" && nf == "" && nph == "")
		wrg +="No changes to apply <br/>";
	
	if (np != "")
		np = encrypt(algenc, np);
	
	if (wrg != "") {
		$("#notifier").html(wrg);
		clearNotifier();
		return;
	}

	var set = {
			oldEmail : oe,
			oldPassword : op,
			newEmail : ne,
			newPassword : np,
			newName : nn,
			newFirstname : nf,
			newPhone : nph
	};
	$.post("/KasuKasu/modify", set)
	.done(function (data) {
		$("#notifier").html(data);
		$("#notifier").html($("#notifier").html() + "<br/>You will be redirected to the dashboard");
		setTimeout(() => {
			window.location.href = "/KasuKasu/dashboard.jsp";
		}, 3000);
	})
}