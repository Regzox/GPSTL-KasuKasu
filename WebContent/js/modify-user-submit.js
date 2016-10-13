var notify = function (notification) {

	window.console.log(notification);

	var container = document.createElement("div");

	if (notification.success != undefined) {
		$(container).html(notification.success);
		$(container).addClass("success");
	} else if (notification.warning != undefined) {
		$(container).html(notification.warning);
		$(container).addClass("warning");
	} else if (notification.error != undefined) {
		$(container).html(notification.error);
		$(container).addClass("error");
	}

	setTimeout(function () {
		$(container).remove();
	}, 3000);

	$("#notifier").append(container);
}

var validation = function (form) {            	
	var json = {
			oldEmail : form.old_email_input.value,
			oldPassword : form.old_password_input.value,
			newEmail : form.new_email_input.value,
			newPassword : form.new_password_input.value,
			newName : form.new_name_input.value,
			newFirstname : form.new_firstname_input.value,
			newPhone : form.new_phone_input.value
	};

	if (	json.newEmail == "" &&
			json.newPassword == "" &&
			json.newName == "" &&
			json.newFirstname == "" &&
			json.newPhone == "") {
		notify({ warning : "No changes to apply" });
		return;
	}

	$
	.post("/KasuKasu/modify", json)
	.done(function (data) {
		notify($.parseJSON(data));
	})
}            