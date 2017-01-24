<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title id='titre'>Mon Objet</title>

<link type="text/css" rel="stylesheet"
	href="/KasuKasu/bootstrap-3.3.7-dist/css/bootstrap.min.css">
<link type="text/css" rel="stylesheet"
	href="/KasuKasu/bootstrap-3.3.7-dist/css/bootstrap-theme.min.css">
<link rel="stylesheet" type="text/css" href="/KasuKasu/css/sidebar.css" />
<link rel="stylesheet" type="text/css" href="/KasuKasu/css/style.css" />
<link rel="stylesheet" type="text/css" href="/KasuKasu/css/profile.css" />
<link rel="stylesheet" type="text/css" href="/KasuKasu/css/icon.css" />
<link type="text/css" rel="stylesheet" href="/KasuKasu/css/jmodal.css" />

<script type="text/javascript" src="/KasuKasu/js/jquery-3.1.1.js"></script>
<script type="text/javascript" src="/KasuKasu/js/modifyobject.js"></script>
<script type="text/javascript"
	src="/KasuKasu/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/KasuKasu/js/traduction.js"></script>
<script type="text/javascript" src="/KasuKasu/js/cookies.js"></script>
<script type="text/javascript" src="/KasuKasu/js/utils.js"></script>

<script type="text/javascript">
	$(document).ready(function() {

		getItemBusyStatus();

		trans('itemmanagement.jsp', 'titre');
		trans('itemmanagement.jsp', 'title');
		trans('itemmanagement.jsp', 'desc');
		trans('itemmanagement.jsp', 'ajout');
		trans('itemmanagement.jsp', 'groupes');
		trans('itemmanagement.jsp', 'ajout1');
		trans('itemmanagement.jsp', 'modify');
		trans('itemmanagement.jsp', 'zones');
		trans('itemmanagement.jsp', 'add_zone');
		trans('itemmanagement.jsp', 'mes_groupes');
		trans('itemmanagement.jsp', 'add_group');
	});
</script>
</head>
<body>

	<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	<%
		String itemid = (String) request.getParameter("objectId");
	%>

	<%@ include file="/fragments/interface/navbar.jspf"%>
	<%@ include file="/fragments/interface/sidebar.jspf"%>

	<div id="page">
		<div id="profile">
			<table>
				<tr class="row">
					<td id="vacation_control"></td>
				</tr>
				<tr class="row">
					<td class="title" id='title'>Titre</td>
					<td class="information" id="title">
						<div class="title">
							<textarea rows="1" cols="50" id="itemtitle" name="description"
								style="resize: none; width: 100%; color: black;">loading</textarea>
						</div>
				</tr>
				<tr class="row">
					<td class="title" id='desc'>Description</td>
					<td class="information" id="description">
						<div class="title">
							<textarea rows="5" cols="50" id="itemdescription"
								name="description"
								style="resize: none; width: 100%; color: black;">loading</textarea>
						</div>
					</td>
				</tr>
				<tr class="row">
					<td class="title">Zones</td>
					<td class="information" id="zone"><span
						class="tag label label-warning"> <span id='ajout'>Ajouter</span>
							<a><i onclick="showZones()"
								class="remove glyphicon glyphicon-plus-sign glyphicon-blue"></i></a>
					</span></td>
				</tr>
				<tr class="row">
					<td class="title" id='groupes'>Groupes</td>
					<td class="information" id="group"><span
						class="tag label label-warning"> <span id='ajout1'>Ajouter</span>
							<a><i onclick="showGroups(this)"
								class="remove glyphicon glyphicon-plus-sign glyphicon-blue"></i></a>
					</span></td>
				</tr>

			</table>
			<div>
				<button type="button" onclick="modifyItem()"
					style="width: 100%; position: relative"
					class="btn btn-primary btn-lg" name="modify" id="modify">Appliquer
					les modifications du Titre et de la description</button>
			</div>
		</div>
	</div>

	<!-- MODAL - ZONES  -->
	<div id="modal-zones" class="modal fade" style="z-index: 1500">
		<div class="modal-dialog modal-lg">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close">&times;</button>
					<h4 class="modal-title" id='zones'>Mes Zones</h4>
				</div>
				<div class="modal-body">
					<div class="well-searchbox">
						<div class="row">
							<form id="zone-form" class="form-horizontal"
								action="javascript:(function(){return;})()" method="get"
								OnSubmit="javascript:addZone(this)">
								<input type="hidden" id="objectId" value=<%=itemid%>>
								<div>
									<SELECT class="form-control" id="zone-id" name="nom" size="1">

									</SELECT>
									<div class="col-sm-offset-4 col-sm-5">
										<button type="submit" class="btn btn-primary" name="add-zone"
											id="add_zone">Ajouter</button>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- MODAL - GROUPS  -->
	<div id="modal-groups" class="modal fade" style="z-index: 1500">
		<div class="modal-dialog modal-lg">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close">&times;</button>
					<h4 class="modal-title" id='mes_groupes'>Mes Groupes</h4>
				</div>
				<div class="modal-body">
					<div class="well-searchbox">
						<div class="row">
							<form id="zone-form" class="form-horizontal"
								action="javascript:(function(){return;})()" method="get"
								OnSubmit="javascript:addGroup(this)">
								<SELECT class="form-control" id="group-id" name="nom" size="1">


								</SELECT>
								<div class="col-sm-offset-4 col-sm-5">
									<button type="submit" class="btn btn-primary" name="add-group"
										id="add_group">Ajouter</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>

		<%@ include file="/fragments/interface/footer.jspf"%>

	</div>
</body>
<script language="javascript">
	window.onload = updateItemInformations();
</script>
</html>
