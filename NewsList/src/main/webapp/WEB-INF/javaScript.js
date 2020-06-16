// Hlavní funkce
window.onload = function() {
		
	// Vyhledání všech elementů
    var elements = document.querySelectorAll("#articleCreationDate");

    for (var i = 0; i < elements.length; i++) {
    	
    	// Změní formát datumu
        changeDateFormat(elements[i]);
    }
}

//Změní formát datumu
function changeDateFormat(element) {
	
    var date = new Date(element.innerHTML);
    var articleCreationDate = date.getDate() + "." + (date.getMonth() + 1) + ".";
    
    var dateNow = new Date();
    var today = dateNow.getDate() + "." + (dateNow.getMonth() + 1) + ".";
    var yesterday = (dateNow.getDate() - 1) + "." + (dateNow.getMonth() + 1) + ".";

    if (articleCreationDate.includes(today)) {
        articleCreationDate = "dnes ";
        
    } else if (articleCreationDate.includes(yesterday)) {
        articleCreationDate = "včera ";
    }

    // Dvoučíselný formát minut
    if (date.getMinutes() < 10) {
        date = articleCreationDate + " " + date.getHours() + ":0" + date.getMinutes();
    
    } else date = articleCreationDate + " " + date.getHours() + ":" + date.getMinutes();

    element.innerHTML = date;
}

//Zvýraznění aktuálního linku
var links = document.querySelectorAll("#sideNavigation a");

switch (location.pathname.split('/')[2]) {

    case "" :
    	links[0].className = "active";
    	break;
    	
    case "domaci" : 
    	links[1].className = "active";
    	break;
    	
    case "zahranicni" :
    	links[2].className = "active";
    	break;
    	
    case "ekonomika" :
    	links[3].className = "active";
    	break;
    	
    case "krimi" :
    	links[4].className = "active";
    	break;
    	
    case "pocasi" :
    	links[5].className = "active";
    	break;
}