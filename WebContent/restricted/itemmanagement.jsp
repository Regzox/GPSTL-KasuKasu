<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Mon Objet</title>

<link type="text/css" rel="stylesheet"
	href="/KasuKasu/bootstrap-3.3.7-dist/css/bootstrap.min.css">
<link type="text/css" rel="stylesheet"
	href="/KasuKasu/bootstrap-3.3.7-dist/css/bootstrap-theme.min.css">
<link rel="stylesheet" type="text/css" href="/KasuKasu/css/sidebar.css" />
<link rel="stylesheet" type="text/css" href="/KasuKasu/css/style.css" />
<link rel="stylesheet" type="text/css" href="/KasuKasu/css/profile.css" />
<link rel="stylesheet" type="text/css" href="/KasuKasu/css/icon.css" />

<script type="text/javascript" src="/KasuKasu/js/jquery-3.1.1.js"></script>
<script type="text/javascript" src="/KasuKasu/js/modifyobject.js"></script>
<script type="text/javascript" src="/KasuKasu/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>

</head>
<body>
	<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ page
		import="java.util.List, entities.Item,entities.Group,entities.Zone"%>
	<%
		Item item = (Item) session.getAttribute("item");
	%>

	<%@ include file="/fragments/sidebar.jspf"%>

	<div id="page">
		<div id="profile">
			<table>
				<tr class="row">
					<td class="title">Id</td>
					<td class="information" id="objectId">${item.id}</td>
				</tr>
				<tr class="row">
					<td class="title">Titre</td>

					<td class="information" id="title">
						<div class="title">
							<textarea rows="1" cols="50" id="description" name="description"
								style="resize: none; width: 100%; color: black;">${item.title}</textarea>
						</div>
				</tr>
				<tr class="row">
					<td class="title">Description</td>
					<td class="information" id="description">
						<div class="title">
							<textarea rows="5" cols="50" id="description" name="description"
								style="resize: none; width: 100%; color: black;">${item.description}</textarea>
						</div>

					</td>
				</tr>
				<tr class="row">
					<td class="title">Zones</td>
					<td class="information" id="zone"><c:forEach var="z"
							items="${item.zone}" varStatus="status">
							<span id="z${z.id}" class="tag label label-info"> <span>
									${z.name}</span> <a><i onclick="removeZone(this)"
									class="remove glyphicon glyphicon-remove-sign glyphicon-red"></i></a>
							</span>
						</c:forEach> <span class="tag label label-warning"> <span>Ajouter</span>
							<a><i onclick="showZones()"
								class="remove glyphicon glyphicon-plus-sign glyphicon-blue"></i></a>
					</span></td>
				</tr>
				<tr class="row">
					<td class="title">Groupes</td>
					<td class="information" id="group"><c:forEach var="g"
							items="${item.group}" varStatus="status">

							<span id="g${g.id}" class="tag label label-info">
								<span> ${g.name}</span> <a><i onclick="removeGroup(this)"
									class="remove glyphicon glyphicon-remove-sign glyphicon-red"></i></a>
							</span>
						</c:forEach> <span class="tag label label-warning"> <span>Ajouter</span>
							<a><i onclick="showGroups(this)"
								class="remove glyphicon glyphicon-plus-sign glyphicon-blue"></i></a>
					</span></td>
				</tr>

			</table>
			<div>
				<button onclick="modifyObject()" type="submit"
					style="width: 100%; position: relative"
					class="btn btn-primary btn-lg" name="modify" value="modify">Appliquer
					les modifications</button>
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
					<h4 class="modal-title">Mes Zones!</h4>
				</div>
				<div class="modal-body">
					<div class="well-searchbox">
						<div class="row">
							<form id="zone-form" class="form-horizontal"
								action="javascript:(function(){return;})()" method="get"
								OnSubmit="javascript:addZone(this)">
								<div>
								<SELECT class="form-control" id="zone-id" name="nom" size="1">
									<c:forEach var="z" items="${item.zone}" varStatus="status">

										<OPTION value="${z.id}">${z.name}</OPTION>
									</c:forEach>
								</SELECT>
								<div class="col-sm-offset-4 col-sm-5">
									<button type="submit" class="btn btn-primary" name="add-zone"
										value="add-zone">Ajouter !</button>
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
					<h4 class="modal-title">Mes Groupes!</h4>
				</div>
				<div class="modal-body">
					<div class="well-searchbox">
						<div class="row">
							<form id="zone-form" class="form-horizontal"
								action="javascript:(function(){return;})()" method="get"
								OnSubmit="javascript:addGroup(this)">
								<SELECT class="form-control" id="group-id" name="nom" size="1">
									<c:forEach var="g" items="${item.group}" varStatus="status">

										<OPTION value="${g.id}">${g.name}</OPTION>
									</c:forEach>

								</SELECT>
								<div class="col-sm-offset-4 col-sm-5">
									<button type="submit" class="btn btn-primary" name="add-group"
										value="add-group">Ajouter !</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>

</html>