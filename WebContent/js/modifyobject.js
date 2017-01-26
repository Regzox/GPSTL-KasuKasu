function updateItemInformations(){
	var id=document.getElementById("objectId").value;
	{
		$.ajax({
			type : "GET",
			url : ObjectManagementServlet,
			data : {objectId : id, data : "data"},
			dataType : "JSON",
			success : processItemInformations,
			error : function(xhr,status,errorthrown){
				console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
			}
		});
	}
}

function processItemInformations(r){
	console.log("Processing informations ...");
	item = r;
	/** Title **/
	$('#itemtitle').val(r.item.items[0].title);

	/** Description **/
	$('#itemdescription').val(r.item.items[0].description);

	/** POUR CHAQUE ZONE ITEM **/
	if(r.itempoint!=undefined)
		for (var i = 0; i < r.itempoint.length; i++) {
			var zone = r.itempoint[i];
			$('#zone').prepend("<span id=\"z"+zone.id+"\" class=\"tag label label-info\"> <span>"
					+zone.nom+"</span> <a><i onclick=\"removeZone(this)\" class=\"remove glyphicon glyphicon-remove-sign glyphicon-red\"></i></a></span>");
		}
	/** POUR CHAQUE GROUPE ITEM**/
	if(r.itemgroup!=undefined)
		for (var i = 0; i < r.itemgroup.length; i++) {
			var group = r.itemgroup[i];
			$('#group').prepend("<span id=\"g"+group.id+"\" class=\"tag label label-info\"> <span>"
					+group.name+"</span> <a><i onclick=\"removeGroup(this)\" class=\"remove glyphicon glyphicon-remove-sign glyphicon-red\"></i></a></span>");
		}
	/** MODAL ZONE USER**/
	if(r.userpoint!=undefined)
		for (var i = 0; i < r.userpoint.length; i++) {
			var zone = r.userpoint[i];
			$('#zone-id').append("<OPTION value=\""+zone.id+"\">"+zone.nom+"</OPTION>");
		}
	/** MODAL GROUPE USER**/
	if(r.usergroup!=undefined)
		for (var i = 0; i < r.usergroup.length; i++) {
			var group = r.usergroup[i];
			$('#group-id').append("<OPTION value=\""+group.id+"\">"+group.nom+"</OPTION>");
		}

}

function modifyItem(){
	var id=document.getElementById("objectId").value;
	var title=$('#itemtitle').val();
	var description=$('#itemdescription').val();
	{
		$.ajax({
			type : "POST",
			url : ObjectManagementServlet,
			data : {objectId : id, target : "item", action: "update" ,title : title, description : description},
			dataType : "JSON",
			success : actionSuccess,
			error : function(xhr,status,errorthrown){
				console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
			}
		});
	}
}



function showZones(){
	$('#modal-zones').modal('show');
}

function showGroups(){
	$('#modal-groups').modal('show');
}
function modifyObject(){

}

function addZone(form){
	var objectId = document.getElementById("objectId").value;
	var sel = document.getElementById("zone-id");
	var text= sel.options[sel.selectedIndex].text;
	var id= sel.options[sel.selectedIndex].value;
	var test=document.getElementById("z"+id);
	console.log(text+" "+id+" "+test)
	if(test==undefined || test==null){
		addZonePost(objectId,id);
		$('#zone').prepend("<span id=\"z"+id+"\" class=\"tag label label-info\"> <span>"
				+text+"</span> <a><i onclick=\"removeZone(this)\" class=\"remove glyphicon glyphicon-remove-sign glyphicon-red\"></i></a></span>");
	}
	$('#modal-hide').modal('show');
}

function addGroup(form){
	var objectId = document.getElementById("objectId").value;
	var sel = document.getElementById("group-id");
	var text= sel.options[sel.selectedIndex].text;
	var id= sel.options[sel.selectedIndex].value;
	var test=document.getElementById("g"+id);
	console.log(text+" "+id+" "+test)
	if(test==undefined || test==null){
		addGroupPost(objectId,id);
		$('#group').prepend("<span id=\"g"+id+"\" class=\"tag label label-info\"> <span>"
				+text+"</span> <a><i onclick=\"removeGroup(this)\" class=\"remove glyphicon glyphicon-remove-sign glyphicon-red\"></i></a></span>");
	}
	$('#modal-groups').modal('hide');
}

function addZonePost(obj,zone){
	{
		$.ajax({
			type : "POST",
			url : ObjectManagementServlet,
			data : {objectId : obj, target : "expoints", targetid : zone, action : "update"},
			dataType : "JSON",
			success : actionSuccess,
			error : function(xhr,status,errorthrown){
				console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
			}
		});
	}
}

function addGroupPost(obj,group){
	{
		$.ajax({
			type : "POST",
			url : ObjectManagementServlet,
			data : {objectId : obj, target : "groups", targetid : group, action : "update"},
			dataType : "JSON",
			success : actionSuccess,
			error : function(xhr,status,errorthrown){
				console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
			}
		});
	}
}

function removeZone(element){
	var objectId = document.getElementById("objectId").value;
	var elem=element.parentNode.parentNode;
	var gid = elem.id;
	var id=gid.substring(1,gid.length);
	removeZonePost(objectId,id);
	elem.parentNode.removeChild(elem);
}

function removeGroup(element){
	var objectId = document.getElementById("objectId").value;
	var elem=element.parentNode.parentNode;
	var gid = elem.id;
	var id=gid.substring(1,gid.length);
	removeGroupPost(objectId,id);
	elem.parentNode.removeChild(elem);
}

function removeZonePost(obj,zone){
	{
		$.ajax({
			type : "POST",
			url : ObjectManagementServlet,
			data : {objectId : obj,target : "expoints", targetid : zone, action : "delete"},
			dataType : "JSON",
			success : actionSuccess,
			error : function(xhr,status,errorthrown){
				console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
			}
		});
	}
}

function removeGroupPost(obj,group){
	{
		$.ajax({
			type : "POST",
			url : ObjectManagementServlet,
			data : {objectId : obj, target : "groups", targetid : group, action : "delete"},
			dataType : "JSON",
			success : actionSuccess,
			error : function(xhr,status,errorthrown){
				console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
			}
		});
	}
}

function actionSuccess(){

}




function setItemBusyStatus(newstatus){
	var id=document.getElementById("objectId").value;
	$
	.post(SetItemBusyStatusServlet + "?status=" + newstatus+"&id="+id)
	.done(function (data) {
		openJModal(2000,
				$.parseJSON(data).message
				,getItemBusyStatus
		);

	});
}

function getItemBusyStatus(){
	var id=document.getElementById("objectId").value;
	$
	.get(GetItemStatusServlet + "?id="+id)
	.done( function (data) {
		var vaccation = $.parseJSON(data).vacationstatus;

		if(vaccation){
			if($.parseJSON(data).status=="borrowed"){
				printHTML('#vacation_control',
						"<button onclick=\"setItemBusyStatus('"+(!vaccation)+"')\" id=\"vacation_control_button\" class=\"button lazyuserbutton\">En pr&ecirc;t</button>");
				$("#vacation_control_button").css({
					"background-color": "orange",
					"color" : "white", 
					"border" : "2px solid #008CBA"
				});
			}
			else{
				printHTML('#vacation_control',
						"<button onclick=\"setItemBusyStatus('"+(!vaccation)+"')\" id=\"vacation_control_button\" class=\"button lazyuserbutton\">Sortir du mode vaccances</button>");
				$("#vacation_control_button").css({
					"background-color": "#f44336",
					"color" : "white", 
					"border" : "2px solid #008CBA"
				});
				$("#vacation_control_button").hover(
						function(){$(this).css({"background-color": "white","color" : "#f44336"});}
						,
						function(){$(this).css({"background-color" : "#f44336", "color" : "white"});}
				); 
			}
		}
		else{
			printHTML('#vacation_control',
					"<button onclick=\"setItemBusyStatus('"+(!vaccation)+"')\" id=\"vacation_control_button\"  class=\"button activuserbutton\">Mettre en vaccances</button>");
			$("#vacation_control_button").css({
				"background-color": "white",
				"color" : "#f44336", 
				"border" : "2px solid #008CBA"
			});
			$("#vacation_control_button").hover(
					function(){$(this).css({"background-color": "#f44336","color" : "white"});}
					,
					function(){$(this).css({"background-color" : "white", "color" : "#f44336"});}
			); 
		}
	} );
}