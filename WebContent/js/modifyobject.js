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
	
}

function addGroupPost(obj,group){
	
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
	
}

function removeGroupPost(obj,group){
	
}