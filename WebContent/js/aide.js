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
			if(bool==0) emprunt.innerHTML='- Vous empruntez '+data.emprunt+' objet(s),';
			else if(bool==1) emprunt.innerHTML='- You borrow '+data.emprunt+' object(s),';

			pret=document.getElementById('pret');
			if(bool==0) pret.innerHTML='- Vous prêtez '+data.loaned+' objets parmi vos '+data.pret+' objet(s).';
			else if(bool==1) pret.innerHTML='- You lend '+data.loaned+' objects among your '+data.pret+' object(s).';
			
			pend=document.getElementById('pend');
			if(bool==0) pend.innerHTML='Vous avez '+data.pending+' demande(s) de prêts en attente'+'<button onclick="locations()">Prêts en attente</button>';
			else if(bool==1) pend.innerHTML='You have '+data.pending+' pending loan request(s)'+'<button onclick="locations()">Pending requests</button>';
			
			//populatePre('aide.txt');

		},		error : function(xhr,status,errorthrown){
			console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
		}
		
		
	});

}

function locations()
{
window.location.href='/KasuKasu/restricted/useritems.jsp';
}

function populatePre(url) {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        document.getElementById('contents').textContent = this.responseText;
    };
    xhr.open('GET', url);
    xhr.send();
}
