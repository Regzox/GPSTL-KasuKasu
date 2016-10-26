var ROOT = "/KasuKasu";
var RESTRICTED = "/restricted";

var kasukasu = {
	public : {
		portal: ROOT + "/portal.jsp",
		log_in: ROOT + "/connexion.jsp",
		sign_up: ROOT + "/createuser.jsp",
		retrieve_password: ROOT + "/retrievePassword.jsp",
		error_page: ROOT + "/errorpage.jsp"
	},
	private : {
		dashboard: ROOT + RESTRICTED + "/dashboard.jsp",
		find_user: ROOT + RESTRICTED + "/finduser.jsp",
		modify_user: ROOT + RESTRICTED + "/modifyUser.jsp",
		profile: ROOT + RESTRICTED + "/profile.jsp"
	}
};

var servlet = {
	check_connection: "",
	confirm_account: "",
	connect_user: "",
	create_user: "",
	find_user: "",
	modify_user: "",
	retrieve_password: "",
	user_profile: ""
};