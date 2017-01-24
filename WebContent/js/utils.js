/**
 * ANAGBLA Joan */

function isNumber(s){return !isNaN(s-0);}

function printHTML(dom,htm){$(dom).html(htm);}

function printHTMLSup(dom,htm){$(dom).append(htm);}

function printHTMLAfter(dom,htm){$(dom).after(htm);}

function printHTMLBefore(dom,htm){$(dom).before(htm);}

function fillNOTIFIER(msg){printHTML("#notifier",msg);}

function resetNOTIFIER(){printHTML("#notifier","");}

function removeHTML(dom,htm){$(dom).remove(htm);}

function removeElt(dom){$(dom).remove(); }

function chill(){/*alert('I m lazy')*/}

function refresh(result){location.reload();}

function gotoURL(url){window.location.href=url};



function openWaiter(){
	var coreHTM ="<div class=\"loader\"></div>";
	var fullHTM = "<div id=\"jModal\" class=\"jmodal\">"+coreHTM+"</div>";

	if(document.getElementById('jModal') != undefined)
		printHTML("#jModal",coreHTM);
	else
		printHTMLSup("body",fullHTM);

	printHTMLSup("body",fullHTM);
	var modal = document.getElementById('jModal');
	modal.style.display = "block";

	/**later-> http://stackoverflow.com/questions/574944/how-to-load-up-css-files-using-javascript
	//https://www.google.fr/search?newwindow=1&q=dynamically+include+css&spell=1&sa=X&ved=0ahUKEwjG3MSf2djRAhWE7hoKHaETBM4QvwUIGSgA&biw=1449&bih=768*/
};


function openJModal(timeout=0,message="",callback=chill){
	var coreHTM ="<div class=\"jmodal-content\">" +
	"<p id=\"JMODALMESSAGE\"></p>" +
	"</div>";
	var fullHTM = "<div id=\"jModal\" class=\"jmodal\">"+coreHTM+"</div>";

	if(document.getElementById('jModal') != undefined)
		printHTML("#jModal",coreHTM);
	else
		printHTMLSup("body",fullHTM);

	var modal = document.getElementById('jModal');
	modal.style.display = "block";
	printHTML("#JMODALMESSAGE",message);
	setTimeout(function(){
		callback();
		modal.style.display = "none";
	}, timeout);

	/**later-> http://stackoverflow.com/questions/574944/how-to-load-up-css-files-using-javascript
	//https://www.google.fr/search?newwindow=1&q=dynamically+include+css&spell=1&sa=X&ved=0ahUKEwjG3MSf2djRAhWE7hoKHaETBM4QvwUIGSgA&biw=1449&bih=768*/
};
