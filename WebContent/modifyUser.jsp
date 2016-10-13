<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>KasuKasu - Modify your account</title>

    <link type="text/css" rel="stylesheet" href="/KasuKasu/css/style.css" />
    <link type="text/css" rel="stylesheet" href="/KasuKasu/css/bootstrap.min.css">

    <script type="text/javascript" src="/KasuKasu/js/md5.js"></script>
    <script type="text/javascript" src="/KasuKasu/js/jquery-3.1.1.min.js"></script>
    <script type="text/javascript" src="/KasuKasu/js/tether.min.js"></script>
    <script	type="text/javascript" src="/KasuKasu/js/bootstrap.min.js"></script>

    <script type="text/javascript" src="/KasuKasu/js/modify-user-submit.js"></script>
    </head>

    <body>
        
        <div class="fit-width capital center show">
        	Modify your account
        </div>
                
        <div class="layer-center">
        
	        <div id="notifier" class="notifier"></div>
	        
	        <form class="layer-left" action="javascript:void(0)" method="post" onsubmit="validation(this)">
	            <fieldset>
	
	                <legend>Type your current</legend>
	
	                <div class="form-group row">
	                    <label for="old_email_input" class="col-xs-2 col-form-label">Email *</label>
	                    <div class="col-xs-10">
	                        <input 	class = "form-control" 
	                                type = "email" 
	                                value = ""
	                                id = "old_email_input"
	                                name = "old_email_input"
	                                required >
	                    </div>
	                </div>
	
	                <div class="form-group row">
	                    <label for="old_password_input" class="col-xs-2 col-form-label">Password *</label>
	                    <div class="col-xs-10">
	                        <input 	class = "form-control" 
	                                type = "password" 
	                                value = ""
	                                id = "old_password_input"
	                                name = "old_password_input"
	                                required >
	                    </div>
	                </div>
	
	                <hr />
	
	                <legend>Type your information modifications</legend>
	
	                <div class="form-group row">
	                    <label for="new_email_input" class="col-xs-2 col-form-label">Email</label>
	                    <div class="col-xs-10">
	                        <input 	class = "form-control"
	                                type = "email"
	                                value = ""
	                                id = "new_email_input"
	                                name = "new_email_input" >
	                    </div>
	                </div>
	
	                <div class="form-group row">
	                    <label for="new_password_input" class="col-xs-2 col-form-label" pattern=".{0}|.{8,64}" >Password</label>
	                    <div class="col-xs-10">
	                        <input 	class = "form-control"
	                            type = "password"
	                            value = ""
	                            id = "new_password_input"
	                            name = "new_password_input" >
	                    </div>
	                </div>
	
	                <div class="form-group row">
	                    <label for="new_name_input" class="col-xs-2 col-form-label" pattern=".{0}|.{1,64}">Name</label>
	                    <div class="col-xs-10">
	                        <input 	class = "form-control"
	                            type = "text"
	                            value = ""
	                            id = "new_name_input"
	                            name = "new_name_input" >
	                    </div>
	                </div>
	
	                <div class="form-group row">
	                    <label for="new_firstname_input" class="col-xs-2 col-form-label" pattern=".{0}|.{1,64}">First Name</label>
	                    <div class="col-xs-10">
	                        <input 	class = "form-control"
	                            type = "text"
	                            value = ""
	                            id = "new_firstname_input"
	                            name = "new_firstname_input" >
	                    </div>
	                </div>
	
	                <div class="form-group row">
	                    <label  for = "new_phone_input" 
	                            class = "col-xs-2 col-form-label"
	                            pattern = "^((\+\d{1,3}(-| )?\(?\d\)?(-| )?\d{1,5})|(\(?\d{2,6}\)?))(-| )?(\d{3,4})(-| )?(\d{4})(( x| ext)\d{1,5}){0,1}$">
	                        Phone</label>
	                    <div class="col-xs-10">
	                        <input 	class = "form-control"
	                            type = "text"
	                            value = ""
	                            id = "new_phone_input"
	                            name = "new_phone_input" >
	                    </div>
	                </div>
	
	                <input class="fit-width button" type="submit" value="Validate" />
	
	            </fieldset>
	        </form>
	    
	    </div>
    </body>

</html>