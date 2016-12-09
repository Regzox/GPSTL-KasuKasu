mirror =(function(key,value){
	//alert("mirror begin a view construction");
	if(key=="items"){ 
		var tab;
		tab=value;  //whole tab
		//alert("mirror  -> items = "+JSON.stringify(r));
		return (tab);
	}
	
	else if(isNumber(key) && value.type=="item"){
		var i = new Item(value.id,value.owner,value.group,value.date,
				value.longitude,value.latitude,value.title,value.description,value.permission);
		return (i);
	}
	
	else if(isNumber(key) && value.type=="group"){
		var g = new Group(value.id,value.name,value.owner,value.type,value.date);
		return (g);
	}
	
	else if(isNumber(key) && value.type=="member"){
		var m = new Member(value.id,value.name,value.type);
		return (m);
	}
	
	else if(isNumber(key) && value.type=="loan"){ 
		var l = new Loan(value.loan_id,value.item,value.title,value.debut,value.fin);
		return (l);
	}
	
	
	
	
	
	
	
	else{
		//alert("revival -> value = "+value);
		return (value);
	}
});