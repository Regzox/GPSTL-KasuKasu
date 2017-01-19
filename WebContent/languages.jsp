<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link type="text/css" rel="stylesheet"
	href="/KasuKasu/css/bootstrap.min.css">
<script	type="text/javascript" src="/KasuKasu/js/translation_.js"></script>
<script type="text/javascript" src="/KasuKasu/js/jquery-3.1.1.js"></script>
<style>

#elt    {
    width: 400px;
/*     height: 400px; */
/*     float: left; */
}

#add  {
    width: 400px;
    margin-auto: 0;
}
</style>
<title>Languages</title>
</head>

<body onload="editLang();">
<div id='main'>
	<div id='elt'></div>
	<div id='add'></div>
</div>
	<button id='ajout' onclick="addLanguage();">Ajouter une nouvelle langue</button>
	<button id='save' onclick="saveLang()">Enregistrer</button>
</body>
</html>