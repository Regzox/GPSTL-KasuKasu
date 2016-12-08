function updateItemInformations(){
	var id=document.getElementById("objectId").innerText;
	{
		$.ajax({
			type : "GET",
			url : "/KasuKasu/objectmanagement",
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
	if(r.itemexpoints!=undefined)
	for (var i = 0; i < r.itemexpoints.length; i++) {
		var zone = r.itemexpoints[i];
		$('#zone').prepend("<span id=\"z"+zone+"\" class=\"tag label label-info\"> <span>"
				+zone+"</span> <a><i onclick=\"removeZone(this)\" class=\"remove glyphicon glyphicon-remove-sign glyphicon-red\"></i></a></span>");
	}
	/** POUR CHAQUE GROUPE ITEM**/
	if(r.itemsgroups!=undefined)
	for (var i = 0; i < r.itemsgroups.length; i++) {
		var group = r.itemsgroups[i];
		$('#group').prepend("<span id=\"g"+group.id+"\" class=\"tag label label-info\"> <span>"
				+group.name+"</span> <a><i onclick=\"removeGroup(this)\" class=\"remove glyphicon glyphicon-remove-sign glyphicon-red\"></i></a></span>");
	}
	/** MODAL ZONE USER**/
	if(r.userexpoints.expts!=undefined)
	for (var i = 0; i < r.userexpoints.expts.length; i++) {
		var zone = r.userexpoints.expts[i];
		$('#zone-id').append("<OPTION value=\""+zone.id+"\">"+zone.id+"</OPTION>");
	}
	/** MODAL GROUPE USER**/
	if(r.groups.groups!=undefined)
	for (var i = 0; i < r.groups.groups.length; i++) {
		var group = r.groups.groups[i];
		$('#group-id').append("<OPTION value=\""+group.id+"\">"+group.name+"</OPTION>");
	}

}

function modifyItem(){
	var id=document.getElementById("objectId").innerText;
	var title=$('#itemtitle').val();
	var description=$('#itemdescription').val();
	{
		$.ajax({
			type : "POST",
			url : "/KasuKasu/objectmanagement",
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
	var objectId = document.getElementById("objectId").innerText;
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
	var objectId = document.getElementById("objectId").innerText;
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
			url : "/KasuKasu/objectmanagement",
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
			url : "/KasuKasu/objectmanagement",
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
	var objectId = document.getElementById("objectId").innerText;
	var elem=element.parentNode.parentNode;
	var gid = elem.id;
	var id=gid.substring(1,gid.length);
	removeZonePost(objectId,id);
	elem.parentNode.removeChild(elem);
}

function removeGroup(element){
	var objectId = document.getElementById("objectId").innerText;
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
			url : "/KasuKasu/objectmanagement",
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
			url : "/KasuKasu/objectmanagement",
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