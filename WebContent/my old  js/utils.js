/**
 * UTILS
 */




function isNumber(s){
	return ! isNaN (s-0);
}

function printHTML(dom,htm){ 
	$(dom).html(htm);
}

function printSupHTML(dom,htm){
	$(dom).append(htm);
}

function printAfterHTML(dom,htm){
	$(dom).after(htm);
}
function printBeforeHTML(dom,htm){
	$(dom).before(htm);
}
function removeHTML(dom,htm){
	$(dom).remove(htm);
}

function cutDate(dateString){
	//alert("cutDate");
	var tab=dateString.split(" ");
	var jour=tab[0];	var mois=tab[1];	var date=tab[2];	var heure=tab[3];	var cest=tab[4];	var annee=tab[5];
	//alert ("jour: "+jour+",mois: "+mois+",date:"+date+",heure :"+heure+",cest:"+cest+",annee:"+annee);
	var tab2=heure.split(":");
	var H=tab2[0];
	var m=tab2[1];
	var s=tab2[2];
	//alert ("H: "+H+",m: "+m+",s:"+s);
	subDate=jour+" "+date+" "+mois+" "+annee+" "+H+":"+m;
	//alert(subDate);
	//return subDate;*/
	return subDate;
}

function wait_ci(){
	var cube="<span id=\"waitingci\"></span>";
	return cube;
}
function wait_post(){
	var cube="<span id=\"waitingpost\"></span>";
	return cube;
}



