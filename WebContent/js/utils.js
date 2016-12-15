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