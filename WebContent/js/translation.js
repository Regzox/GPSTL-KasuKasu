//récupération de l'objet xhr
function getXMLHttpRequest() {
    var xhr = null;
    if (window.XMLHttpRequest || window.ActiveXObject) {
        if (window.ActiveXObject) {
            try {
                xhr = new ActiveXObject("Msxml2.XMLHTTP");
            } catch(e) {
                xhr = new ActiveXObject("Microsoft.XMLHTTP");
            }
        } else {
            xhr = new XMLHttpRequest(); 
        }
    } else {
        alert("Votre navigateur ne supporte pas l'objet XMLHTTPRequest...");
        return null;
    }
    return xhr;
}

//
//
//function translation(id,type, traductions)
//{
//	this.id=id;
//	this.type=type;
//	this.traductions=traducions;
//}

function trans()
{
	var xhr = getXMLHttpRequest();
	var dico;
	//get the file name
	var href = document.location.href;
	var fileName = href.substr(href.lastIndexOf('/') + 1);
	//alert(fileName);
	xhr.open("GET","traduction.json",false);
	xhr.send();
	//alert(xhr.readyState+" "+xhr.status);
	
//		xhr.onreadystatechange = function() {
//			console.log("aaaaaaaaaaaaaaaaaaaaa");
//        if (xhr.readyState == 4 && (xhr.status == 200 || xhr.status == 0)) {

        	dico = JSON.parse(xhr.responseText);
        	console.log(xhr.responseText);
            if(fileName == "portal.jsp")
        	{
	            document.getElementById('btn_fr').onclick = function() {document.cookie = "lang=fr";/*translation(dico,"fr",fileName);*/}
	            document.getElementById('btn_en').onclick = function() {document.cookie = "lang=en";/*translation(dico,"en",fileName);*/}           
        	}
            
	            	var cook = document.cookie;
	            	
	            	if(cook.search("lang=en")>0)
	            		{
	            			translation(dico,"en",fileName);
	            			//window.location(document.location);
	            			//alert("en "+cook.search("lang=en"));
	            		}
	            	else
	            		{
	            			if(cook.search("lang=fr")>0)
	            				{
	            					translation(dico,"fr",fileName);
	            					//window.location(document.location);
	            				}
	            			
	            			//alert("fr "+cook.search("lang=fr"));
	            		}
		            		
	            	
	            	
            	
            
//        }
        
		
	//}

}
function translation(dico,lang,fileName)
{//alert(fileName);
      //for every web page 
      for(i=0;i<dico.length;i++) 
  		{
	      	
	  		var id = dico[i].id;       		
	  		if(id == "portal.jsp" && id == fileName)
	  			{
	      		var traductions = dico[i].traductions;	
	//      		for(j=0;j<traductions.length;j++)
	//      			{
	//     				var langue = traductions[j].langue;
	     				var k=0;
	     				if(lang=="fr" && k< traductions.length/*&& langue == "fr-FR"*/)
	  					{
	  						printHTML("#bienvenue",traductions[k].expression);
	  						printHTML("#titre",traductions[k].expression);k=k+2;
	  						printHTML("#site",traductions[k].expression);k=k+2;
	  						printHTML("#btn_insc",traductions[k].expression);k=k+2;
	  						printHTML("#btn_connex",traductions[k].expression);
	  					} 
	     				k=1;
	      				if(lang=="en" && k< traductions.length/*&& langue == "en-GB"*/)
	  					{
	  						printHTML("#bienvenue",traductions[k].expression);
	  						printHTML("#titre",traductions[k].expression);k=k+2;
	  						printHTML("#site",traductions[k].expression);k=k+2;
	  						printHTML("#btn_insc",traductions[k].expression);k=k+2;
	  						printHTML("#btn_connex",traductions[k].expression);
	  					}	           				
	//      			}
	  			}
	  		if(id == "connexion.jsp"&& id == fileName)
  			{ 
	  			traductions = dico[i].traductions;	
     				k=0;
     				if(lang=="fr" && k< traductions.length)
  					{
  						printHTML("#titre",traductions[k].expression);
  						printHTML("#connex",traductions[k].expression);k=k+2;
  						printHTML("#email",traductions[k].expression);k=k+2;
  						printHTML("#mdp",traductions[k].expression);k=k+2;
  						printHTML(document.getElementById("mdpo"),traductions[k].expression);
  					} 
     				k=1;
      				if(lang=="en" && k< traductions.length/*&& langue == "en-GB"*/)
  					{
  						printHTML("#titre",traductions[k].expression);
  						printHTML("#connex",traductions[k].expression);k=k+2;
  						printHTML("#email",traductions[k].expression);k=k+2;
  						printHTML("#mdp",traductions[k].expression);k=k+2;
  						printHTML(document.getElementById("mdpo"),traductions[k].expression);
  					}	           				
      			}
	  		if(id == "createuser.jsp" && id == fileName)
  			{ 
	  			traductions = dico[i].traductions;	
     				k=0;
     				if(lang=="fr" && k< traductions.length)
  					{
  						printHTML("#titre",traductions[k].expression);
  						printHTML("#a",traductions[k].expression);k=k+2;
  						printHTML("#prenoml",traductions[k].expression);k=k+2;
  						printHTML("#noml",traductions[k].expression);k=k+2;
  						printHTML("#numl",traductions[k].expression);k=k+2;
  						printHTML("#emaill",traductions[k].expression);k=k+2;
  						printHTML("#mdpl",traductions[k].expression);k=k+2;
  						printHTML("#confl",traductions[k].expression);k=k+2;
  						printHTML("#captchal",traductions[k].expression);k=k+2;
  						printHTML("#submit",traductions[k].expression);
  					} 
     				k=1;
      				if(lang=="en" && k< traductions.length/*&& langue == "en-GB"*/)
  					{
  						printHTML("#titre",traductions[k].expression);
  						printHTML("#a",traductions[k].expression);k=k+2;
  						printHTML("#prenoml",traductions[k].expression);k=k+2;
  						printHTML("#noml",traductions[k].expression);k=k+2;
  						printHTML("#numl",traductions[k].expression);k=k+2;
  						printHTML("#emaill",traductions[k].expression);k=k+2;
  						printHTML("#mdpl",traductions[k].expression);k=k+2;
  						printHTML("#confl",traductions[k].expression);k=k+2;
  						printHTML("#captchal",traductions[k].expression);k=k+2;
  						printHTML("#submit",traductions[k].expression);
  					}	           				
      			}
	  		if(id == "retrievePassword.jsp" && id == fileName)
  			{ 
	  			traductions = dico[i].traductions;	
     				k=0;
     				if(lang=="fr" && k< traductions.length)
  					{
  						printHTML("#titre",traductions[k].expression);
  						printHTML("#ret",traductions[k].expression);k=k+2;
  						printHTML("#email",traductions[k].expression);k=k+2;
  						printHTML("#submit",traductions[k].expression);
  						
  					} 
     				k=1;
      				if(lang=="en" && k< traductions.length/*&& langue == "en-GB"*/)
  					{
      					printHTML("#titre",traductions[k].expression);
      					printHTML("#ret",traductions[k].expression);k=k+2;
  						printHTML("#email",traductions[k].expression);k=k+2;
  						printHTML("#submit",traductions[k].expression);
  					}	           				
      		}
	  		/*restricted ?!*/
	  		if(id == "modifyUser.jsp" && id == fileName)
  			{ //alert();
	  			traductions = dico[i].traductions;	
     				k=0;
     				if(lang=="fr" && k< traductions.length)
  					{
  						printHTML("#titre",traductions[k].expression);
  						printHTML("#a",traductions[k].expression);k=k+2;
  						printHTML("#modif",traductions[k].expression);k=k+2;
  						printHTML("#emaill",traductions[k].expression);k=k+2;
  						printHTML("#mdpl",traductions[k].expression);k=k+2;
  						printHTML("#noml",traductions[k].expression);k=k+2;
  						printHTML("#prenoml",traductions[k].expression);k=k+2;
  						printHTML("#numl",traductions[k].expression);k=k+2;
  						printHTML("#submit",traductions[k].expression);
  					} 
     				k=1;
      				if(lang=="en" && k< traductions.length/*&& langue == "en-GB"*/)
  					{
  						printHTML("#titre",traductions[k].expression);
  						printHTML("#a",traductions[k].expression);k=k+2;
  						printHTML("#modif",traductions[k].expression);k=k+2;
  						printHTML("#emaill",traductions[k].expression);k=k+2;
  						printHTML("#mdpl",traductions[k].expression);k=k+2;
  						printHTML("#noml",traductions[k].expression);k=k+2;
  						printHTML("#prenoml",traductions[k].expression);k=k+2;
  						printHTML("#numl",traductions[k].expression);k=k+2;
  						printHTML("#submit",traductions[k].expression);
  					}	           				
      			}
	  		if(id == "searchitems.jsp" && id == fileName)
  			{ 
	  			traductions = dico[i].traductions;	
     				k=0;
     				if(lang=="fr" && k< traductions.length)
  					{
  						printHTML("#titre",traductions[k].expression);k=k+2;
  						printHTML("#p",traductions[k].expression);k=k+2;
  						printHTML("#submit".innerHTML,traductions[k].expression);
  					} 
     				k=1;
      				if(lang=="en" && k< traductions.length/*&& langue == "en-GB"*/)
  					{
      					printHTML("#titre",traductions[k].expression);k=k+2;
  						printHTML("#p",traductions[k].expression);k=k+2;
  						printHTML("#submit".innerHTML,traductions[k].expression);
  					}	           				
      		}
	  		/*
	  		Problème => include + balise a
	  		*/
	  		if(id == "profile.jsp" && id == fileName)
  			{ 
	  			traductions = dico[i].traductions;	
     				k=0;
     				if(lang=="fr" && k< traductions.length)
  					{
  						printHTML("#titre",traductions[k].expression);k=k+2;
  						printHTML("#modif",traductions[k].expression);k=k+2;
  						printHTML("#photo",traductions[k].expression);
  						
  					} 
     				k=1;
      				if(lang=="en" && k< traductions.length/*&& langue == "en-GB"*/)
  					{
      					printHTML("#titre",traductions[k].expression);k=k+2;
  						printHTML("#modif",traductions[k].expression);k=k+2;
  						printHTML("#photo",traductions[k].expression);
  					}	           				
      		}
	  		if(id == "finduser.jsp" && id == fileName)
  			{ 
	  			traductions = dico[i].traductions;	
     				k=0;
     				if(lang=="fr" && k< traductions.length)
  					{
  						printHTML("#titre",traductions[k].expression);
  						printHTML("#rech",traductions[k].expression);k=k+2;
  						printHTML("#submit",traductions[k].expression);
  						
  					} 
     				k=1;
      				if(lang=="en" && k< traductions.length/*&& langue == "en-GB"*/)
  					{
      					printHTML("#titre",traductions[k].expression);
  						printHTML("#rech",traductions[k].expression);k=k+2;
  						printHTML("#submit",traductions[k].expression);
  					}	           				
      		}
	  		/** include !!*/ 
	  		if(id == "myfriends.jsp" && id == fileName)
  			{ 
	  			traductions = dico[i].traductions;	
     				k=0;
     				if(lang=="fr" && k< traductions.length)
  					{
  						printHTML("#titre",traductions[k].expression);
  						
  						
  					} 
     				k=1;
      				if(lang=="en" && k< traductions.length/*&& langue == "en-GB"*/)
  					{
      					printHTML("#titre",traductions[k].expression);
  						
  					}	           				
      		}
	  		/** include !!*/ 
	  		if(id == "pendingrequests.jsp" && id == fileName)
  			{ 
	  			traductions = dico[i].traductions;	
     				k=0;
     				if(lang=="fr" && k< traductions.length)
  					{
  						printHTML("#titre",traductions[k].expression);
  						
  						
  					} 
     				k=1;
      				if(lang=="en" && k< traductions.length/*&& langue == "en-GB"*/)
  					{
      					printHTML("#titre",traductions[k].expression);
  						
  					}	           				
      		}
	  		if(id == "groups.jsp" && id == fileName)
  			{ 
	  			traductions = dico[i].traductions;	
     				k=0;
     				if(lang=="fr" && k< traductions.length)
  					{
  						printHTML("#titre",traductions[k].expression);k=k+2;
  						printHTML("#create",traductions[k].expression);
  						printHTML("#submit",traductions[k].expression);
  					} 
     				k=1;
      				if(lang=="en" && k< traductions.length/*&& langue == "en-GB"*/)
  					{
      					printHTML("#titre",traductions[k].expression);k=k+2;
      					printHTML("#create",traductions[k].expression);
  						printHTML("#submit",traductions[k].expression);
  					}	           				
      		}
	  		if(id == "createobject.jsp" && id == fileName)
  			{ 
	  			traductions = dico[i].traductions;	
     				k=0;
     				if(lang=="fr" && k< traductions.length)
  					{
  						printHTML("#titre",traductions[k].expression);
  						printHTML("#ajout",traductions[k].expression);k=k+2;
  					} 
     				k=1;
      				if(lang=="en" && k< traductions.length/*&& langue == "en-GB"*/)
  					{
      					printHTML("#titre",traductions[k].expression);
      					printHTML("#ajout",traductions[k].expression);k=k+2;
  					}	           				
      		}
	  		if(id == "createobject.jsp" && id == fileName)
  			{ 
	  			traductions = dico[i].traductions;	
     				k=0;
     				if(lang=="fr" && k< traductions.length)
  					{
  						printHTML("#titre",traductions[k].expression);
  						printHTML("#ajout",traductions[k].expression);k=k+2;
  					} 
     				k=1;
      				if(lang=="en" && k< traductions.length/*&& langue == "en-GB"*/)
  					{
      					printHTML("#titre",traductions[k].expression);
      					printHTML("#ajout",traductions[k].expression);k=k+2;
  					}	           				
      		}
	  		if(id == "createobject.jsp" && id == fileName)
  			{ 
	  			traductions = dico[i].traductions;	
     				k=0;
     				if(lang=="fr" && k< traductions.length)
  					{
  						printHTML("#titre",traductions[k].expression);
  						printHTML("#ajout",traductions[k].expression);k=k+2;
  					} 
     				k=1;
      				if(lang=="en" && k< traductions.length/*&& langue == "en-GB"*/)
  					{
      					printHTML("#titre",traductions[k].expression);
      					printHTML("#ajout",traductions[k].expression);k=k+2;
  					}	           				
      		}
  			//}
  			
  		}
}
function printHTML(dom,htm)
{ 
	$(dom).html(htm);
}