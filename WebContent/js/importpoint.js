var map;
bool=0;
if(document.cookie.search("lang=en")!=-1)
	bool=1;
else
	if(document.cookie.search("lang=fr")!=-1)
		bool=0;
var id; 
var name; 
var radius;

var json1; 
var json2;
var reponse1 = false;
var reponse2 = false;
var table_feature1 = new Array();
var table_feature2 = new Array(); 

OpenLayers.Control.Click = OpenLayers.Class(OpenLayers.Control, {
	defaultHandlerOptions : {
		'single' : true,
		'double' : true,
		'pixelTolerance' : 0,
		'stopSingle' : false,
		'stopDouble' : true
	},

	initialize : function(options) {
		this.handlerOptions = OpenLayers.Util.extend({},
				this.defaultHandlerOptions);
		OpenLayers.Control.prototype.initialize.apply(this, arguments);
		this.handler = new OpenLayers.Handler.Click(this, {
			'click' : this.trigger2,
			'dblclick': this.trigger 

		}, this.handlerOptions);
	},


	trigger : function(e) {
		var toProjection = new OpenLayers.Projection("EPSG:4326");
		var lonlat = map.getLonLatFromViewPortPx(e.xy);

		abonnement(lonlat.lat.toFixed(5),lonlat.lon.toFixed(5));

	},

	trigger2 : function(e) {

	}
});

function init() 
{
	map = new OpenLayers.Map("mapdiv");
	map.addLayer(new OpenLayers.Layer.OSM());

	var lonLat = new OpenLayers.LonLat(2.3488000, 48.8534100).transform(
			new OpenLayers.Projection("EPSG:4326"), // transform from WGS 1984
			map.getProjectionObject() // to Spherical Mercator Projection
	);

	var zoom = 14;
	map.setCenter(lonLat, zoom);

	//Récupérer les points d'échange des amis de l'utilisateur auxquels il peut s'abonner
	userFriendsPoints();
	reponse1=true;

	//Récupérer les points d'échange de l'utilisateur
	userPoints();
	reponse2=true;

	//Afficher les lieux une fois que les deux callback sont à true
	if (reponse1==true && reponse2==true )
	{
		affiche();
	}


	var click = new OpenLayers.Control.Click();
	map.addControl(click);
	click.activate();


}

/************************************* S'abonner à un point d'échange ************************************/
function abonnement(lat,lon)
{
	$("#myModal").modal({                    
		"backdrop"  : "static",
		"keyboard"  : true,
		"show"      : true                     
	});


	$("#myModal").on('hidden.bs.modal', function () {
		$(this).removeData('modal');
		window.location.href = importpoint_jsp;
	});  


	$('#save').on(
			'click',
			function(evt)
			{
				
				nom_abo = $("#myModal").find("#nom_input").val();
				radius_abo = $("#myModal").find("#radius_input").val();
				var pattern = /^\d+$/;
				numeric = pattern.test(radius_abo);

//				if (nom_abo.lenght==0) $("#myModal").find("#nom_input").text("Champ obligatoire");
//				if (radius_abo.lenght==0) $("#myModal").find("#radius_input").text("Champ obligatoire");
//				if (!numeric) $("#myModal").find("#radius_input").text("Valeur numérique obligatoire");


				if (nom_abo.length!=0 && radius_abo.length!=0 && numeric)
				{
					addPoint(nom_abo,radius_abo,lat,lon); 
					$("#myModal").hide();
				}


			}
	);


}

function addPoint(nom_abo,radius_abo,lat,lon)
{

	$.ajax({
		type : "POST",
		url : AddExchangePointServlet,
		xhrFields: 
		{
			withCredentials: true
		},
		data : "name=" + nom_abo + "&radius=" + radius_abo + "&lat=" + lat + "&lon=" + lon, 
		dataType : "JSON",
		success : function (data)
		{

			if (data.message=="1")
			{
				$("#myModal").hide(); 

				$("#myModal2").modal({                    
					"backdrop"  : "static",
					"keyboard"  : true,
					"show"      : true                     
				});

				$("#myModal2").on('hidden.bs.modal', function () {
					window.location.href = importpoint_jsp;
				});

			}
		},		error : function(xhr,status,errorthrown){
			console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
		}
	});

}

/********************************** Récupérer les points des amis de l'utilisateur ************************************/


function userFriendsPoints(){
	$.ajax({
		async : false,
		type : "GET",
		url : PointsUserFriendList,
		data : "",
		dataType : "JSON",
		success : traiteReponse2,
		error : function(xhr,status,errorthrown){
			console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
		}
	});
}



function traiteReponse2(json) 
{

	json1=JSON.parse(JSON.stringify(json));

}

/********************************** Récupérer les points de l'utilisateur ************************************/
function userPoints(){
	$.ajax({
		async : false,
		type : "GET",
		url : PointsUserList,
		data : "",
		dataType : "JSON",
		success : traiteReponse,
		error : function(xhr,status,errorthrown){
			console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
		}
	});
}

function traiteReponse(json) 
{

	json2=JSON.parse(JSON.stringify(json));

}

/*************************************** Supprimer un point d'échange ************************************/

function Delete(id)
{
	$.ajax({
		type : "POST",
		url : DeleteExchangePointServlet,
		data : "id="+id,
		dataType : "JSON",
		success : function (data)
		{

			if (data.message=="1")
			{
				$("#myModal3").modal({                    
					"backdrop"  : "static",
					"keyboard"  : true,
					"show"      : true                     
				});

				$("#myModal3").on('hidden.bs.modal', function () {
					window.location.href = importpoint_jsp;
				});
			}
		},		error : function(xhr,status,errorthrown){
			console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
		}
	});}


/*************************************** Modifier un point d'échange ************************************/


function Modifier(id_modif,old_name,old_radius)
{

	var modal = $('#myModal4');
	modal.find("#nom_input").val(old_name);
	modal.find("#radius_input").val(old_radius);

	$("#myModal4").modal({                    
		"backdrop"  : "static",
		"keyboard"  : true,
		"show"      : true                     
	});




	$('#enreg').on(
			'click',
			function(evt)
			{
				nom_modif = $("#myModal4").find("#nom_input").val();
				radius_modif = $("#myModal4").find("#radius_input").val();

				if (nom_modif.length==0) nom_modif = old_name;
				if (radius_modif.length==0) radius_modif = old_radius;
				
				var pattern = /^\d+$/;
				numeric = pattern.test(radius_modif);
				
				if (numeric) updatePoint(id_modif,radius_modif,nom_modif); 
				
				

			}
	);
}

function updatePoint(id_modif,radius_modif,name_modif)
{
	$.ajax({
		type : "POST",
		url : UpdateExchangePointServlet,
		data : "id=" + id_modif + "&radius=" + radius_modif + "&name=" + name_modif, 
		dataType : "JSON",
		success : function (data)
		{

			if (data.message=="1")
			{
				$("#myModal4").hide(); 

				$("#myModal3").modal({                    
					"backdrop"  : "static",
					"keyboard"  : true,
					"show"      : true                     
				});

				$("#myModal3").on('hidden.bs.modal', function () {
					window.location.href = importpoint_jsp;
				});
			}
		},		error : function(xhr,status,errorthrown){
			console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
		}
	});}


/********************************** Récupérer les informations sur un marker ************************************/
function getMarkerDetail(lat,lon)
{
	$.ajax({
		type : "GET",
		url : GetPointMarkerDetail,
		async: false,
		data : "lat=" + lat + "&lon=" + lon,
		dataType : "JSON",
		success : function (data)
		{
			id=data.id;
			name =data.name;
			radius=data.radius;
		},
		error : function(xhr,status,errorthrown){
			console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
		}
	});
}


/********************************** Afficher tous les points récupérés ************************************/

function affiche() 
{

	var vectorLayer = new OpenLayers.Layer.Vector("Overlay");

	/********     Afficher les points de l'utilisateur **********/
	for (var i=0; i< json1.expts.length; i++)
	{

		var lon = json1.expts[i].lon;
		var lat = json1.expts[i].lat;
		if(bool==0)
			var feature1 = new OpenLayers.Feature.Vector
			(

					new OpenLayers.Geometry.Point( lon, lat ),
					{description: "Abonnés: "+json1.expts[i].name} ,
					{externalGraphic: '/KasuKasu/data/marker.png', graphicHeight: 25, graphicWidth: 21, graphicXOffset:-12, graphicYOffset:-25  }
			); 
		if(bool==1)
			feature1 = new OpenLayers.Feature.Vector
			(

					new OpenLayers.Geometry.Point( lon, lat ),
					{description: "Subscribers : "+json1.expts[i].name} ,
					{externalGraphic: '/KasuKasu/data/marker.png', graphicHeight: 25, graphicWidth: 21, graphicXOffset:-12, graphicYOffset:-25  }
			); 
		vectorLayer.addFeatures(feature1);
		table_feature1.push(feature1);

	}

	/********     Afficher les points des amis de l'utilisateur    **********/


	for (var i=0; i< json2.expts.length; i++)
	{

		var lon = json2.expts[i].lon;
		var lat = json2.expts[i].lat;
		id = json2.expts[i].id;
		if(bool==0)
			var feature2 = new OpenLayers.Feature.Vector
			(

					new OpenLayers.Geometry.Point( lon, lat ),
					{description: "Lieu : "+json2.expts[i].name+", "+json2.expts[i].radius+"m"+"<br>"+"Abonnés :" + json2.expts[i].users},
					{externalGraphic: '/KasuKasu/data/marker-red.png', graphicHeight: 25, graphicWidth: 21, graphicXOffset:-12, graphicYOffset:-25  }
			); 
		if(bool==1)
			feature2 = new OpenLayers.Feature.Vector
			(

					new OpenLayers.Geometry.Point( lon, lat ),
					{description: "Place : "+json2.expts[i].name+", "+json2.expts[i].radius+"m"+"<br>"+"Subscribers :" + json2.expts[i].users} ,
					{externalGraphic: '/KasuKasu/data/marker-red.png', graphicHeight: 25, graphicWidth: 21, graphicXOffset:-12, graphicYOffset:-25  }
			); 

		vectorLayer.addFeatures(feature2);
		table_feature2.push(feature2);


	}

	map.addLayer(vectorLayer);

	var controls = {
			selector: new OpenLayers.Control.SelectFeature(vectorLayer, { onSelect: createPopup, onUnselect: destroyPopup })
	};

	function createPopup(feature) 
	{
		/********     Premier modèle de pop up     **********/

		if (containsObject(feature, table_feature1))
		{
			var lonLat = feature.geometry.getBounds().getCenterLonLat();

			feature.popup = new OpenLayers.Popup.FramedCloud("pop",
					feature.geometry.getBounds().getCenterLonLat(),
					null, 	          
					'<div class="markerContent">'+feature1.attributes.description+'</div>'+'<br></br>'+'<button onclick="abonnement(\'' + lonLat.lat + '\',\'' + lonLat.lon + '\')">S\'abonner</button>',
					null,
					true,
					function() { controls['selector'].unselectAll(); }
			);

			map.addPopup(feature.popup);   

		}

		/********     Deuxième modèle de pop up     **********/

		if (containsObject(feature, table_feature2))
		{
			var lonLat = feature.geometry.getBounds().getCenterLonLat();

			id="";
			name="";
			radius="";

			getMarkerDetail(lonLat.lat,lonLat.lon);


			feature.popup = new OpenLayers.Popup.FramedCloud
			(
					"popup",
					feature.geometry.getBounds().getCenterLonLat(),
					null, 	          
					'<div class="markerContent">'+feature.attributes.description+'</div>'+'<br></br>'+'<button onclick="Modifier(\'' + id + '\',\'' + name + '\' ,\'' + radius+ '\')"> Modifier</button>'+'<button onclick="Delete(\''+id+'\');">Supprimer</button>',
					null,
					true,
					function() { controls['selector'].unselectAll(); }
			);

			map.addPopup(feature.popup);
		}	         
	}

	function destroyPopup(feature) {

		feature.popup.destroy();
		feature.popup = null;

	}

	map.addControl(controls['selector']);
	controls['selector'].activate();


}

function containsObject(obj, list) {
	var i;
	for (i = 0; i < list.length; i++) {
		if (list[i] === obj) {
			return true;
		}
	}

	return false;
}
