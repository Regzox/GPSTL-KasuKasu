<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript" src="/KasuKasu/js/jquery-3.1.1.js"></script>
<script type="text/javascript" src="/KasuKasu/js/translation.js"></script>
<!-- <script type="text/javascript" src="js/enregistrement.js"></script> -->

<title id="titre">Télécharger une image</title>

</head>
<body onload="trans();">
	
	<%@ include file="/fragments/interface/navbar.jspf"%>
	<%@ include file="/fragments/interface/sidebar.jspf"%>

	<div id="page">
		<form method="post" action="/KasuKasu/imagesservlet"  enctype="multipart/form-data">
        
           <div id="upload">Sélectionner une image à télécharger</div> 
            <input type="file" name="url" id="url"/>
            <br/>
                     
           	<input id="submit" type="submit" value="Télécharger" /> 		
        </form>
        
        <%@ include file="/fragments/interface/footer.jspf"%>
	</div>

</body>
</html>