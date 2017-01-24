bool=0;
if(document.cookie.search("lang=en")!=-1)
	bool=1;
else
	if(document.cookie.search("lang=fr")!=-1)
		bool=0;



function init()
{

	$.ajax({
		type : "POST",
		url : "/KasuKasu/userinfospageaide",
		xhrFields: 
		{
			withCredentials: true
		},
		dataType : "JSON",
		success : function (data)
		{

			act = document.getElementById('act');
			if(bool==0) act.innerHTML=' Actuellement:';
			else if (bool==1) act.innerHTML=' Currently:';
			
			emprunt=document.getElementById('emprunt');
			if(bool==0) emprunt.innerHTML='- <b>Vous <button class=\"btn btn-primary btn-xs\" style=\"margin-left:1px;margin-right:3px;\" onclick="locations(\'/KasuKasu/restricted/userLoans.jsp\')">empruntez</button>'+data.emprunt+'<b> objet(s),</b>';
			else if(bool==1) emprunt.innerHTML='- <b>You <button class=\"btn btn-primary btn-xs\" style=\"margin-left:1px;margin-right:3px;\" onclick="locations(\'/KasuKasu/restricted/userLoans.jsp\')">borrow</button>'+data.emprunt+'<b> object(s),</b>';

			pret=document.getElementById('pret');
			if(bool==0) pret.innerHTML='- <b>Vous prêtez '+data.loaned+' objets parmi vos  </b>'+data.pret+'<b> objet(s).</b>';
			else if(bool==1) pret.innerHTML='- <b>You lend '+data.loaned+' objects among your  </b>'+data.pret+' object(s).</b>';
			
			pend=document.getElementById('pend');
			if(bool==0) pend.innerHTML='<b>Vous avez '+data.pending+' demande(s) de </b>'+'<button class=\"btn btn-primary btn-xs\" style=\"margin-left:1px;\" onclick="locations(\'/KasuKasu/restricted/applicants.jsp\')">Prêts en attente</button>';
			else if(bool==1) pend.innerHTML='<b>You have '+data.pending+' </b>'+'<button class=\"btn btn-primary btn-xs\" style=\"margin-left:1px;\" onclick="locations(\'/KasuKasu/restricted/applicants.jsp\')">Pending requests</button>';
			
			//populatePre('aide.txt'); 

		},		error : function(xhr,status,errorthrown){
			console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
		}
		
		
	});

}

function locations(href)
{
window.location.href=href;
}

function populatePre(url) {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        document.getElementById('contents').textContent = this.responseText;
    };
    xhr.open('GET', url);
    xhr.send();
}
