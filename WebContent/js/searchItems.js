//$("#iquery").focus()
function Item(id,owner,group,date,longitude,latitude,title,description){
	alert("new Item("+id+","+title+","+group+","+longitude+","+latitude+","+date+","
			+description+")");
	this.id=id;
	this.owner=owner;
	this.group=group;
	this.date=date;
	this.longitude=longitude;
	this.latitude=latitude;
	this.title=title;
	this.description=description;
	
} 


Item.revival=function(key,value){
	//alert("revival begin");
	if(key=="items"){ 
		var r;
		if((value.error==undefined) || (value.error==0)){
			r=value;  //whole tab
			alert("revival  -> items = "+JSON.stringify(r));
		}else{
			r =new Object();
			r.error=value.error;
			alert("revive -> error = "+r.error);
		}
		return (r);
	}else if(isNumber(key)&& value.type=="item"){ //tab index
		var i = new Item(value.id,value.owner,value.group,value.date,
				value.longitude,value.latitude,value.title,value.description);
		return (i);
	}else if(key =="date"){
		var d=new Date(value);
		alert("revival -> date = "+d);
		return d;
	}else{
		alert("revival -> value = "+value);
		return (value);
	}
};


Item.traiteReponseJSON=function(json){	
	alert("Item.traiteReponseJSON raw json -> "+JSON.stringify(json));	
	var jsob=JSON.parse(JSON.stringify(json),Item.revival);
	alert("Item.traiteReponseJSON cooked jsob -> "+JSON.stringify(jsob));

	if(jsob.error==undefined){
		//alert(jsob.title)
		//alert("after parsing we got that posts in html : "+obj.getHTML());
		//printHTML("#div_post_zone",obj.getHTML());  //printSup pour etre correct surtt kan on va charger la suite 
	}else
		alert("server error ! : " +jsob.error);
};










function searchMRItems(query){
	$.ajax({
		type : "GET",
		url : "searchitems",
		data : "query=" +query,
		dataType : "JSON",
		success : Item.traiteReponseJSON,
		error : function(xhr,status,errorthrown){
			console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
		}
	});
}
function searchItems(form){
	resetNOTIFIER();
	if (checkQuery(form.iquery.value))		
		searchMRItems(form.iquery.value);
}
function checkQuery(query){
	if(query.length==0)
		return false; //do nothing
	return true;
}
/**Only for first debugs*/ 
function printJSONItems(rep){printHTML("#found-items", JSON.stringify(rep));}