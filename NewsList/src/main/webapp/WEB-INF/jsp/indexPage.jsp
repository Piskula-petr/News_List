<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="cs" lang="cs">
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link rel="stylesheet" type="text/css" href="style.css"/>
		<link rel="shortcut icon" href="favicon.png" type="image/png"/>
		<title>News List</title>
	</head>
	
	<body>
	
		<!-- Záhlaví -->
		<c:import url="header.jsp"></c:import>
	
		<!-- Navigační menu -->
		<c:import url="sideNavigation.jsp"></c:import>
		
		<!-- Články -->
		<c:forEach var="article" items="${articles}">
	
			<a href="${article.link}">
		
				<div id="articleContainer">
			
					<img src="${article.image}"></img>
					<div id="articleName">${article.name}</div>
					
					<table>
						<tr>
							<td>
								<div>${article.source}</div>
							</td>
							
							<td>
								<div id="articleCreationDate">${article.creationDate}</div>
							</td>
							
							<td>
								<div>${article.topic}</div>
							</td>
						</tr>
					</table>
				</div>
			</a>
		</c:forEach>
	</body>
	
	<!-- Scripty -->
	<script>
	
		// Změní formát datumu
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
	
	    // Vyhledání všech elementů
	    window.onload = function() {
	    	
	        var elements = document.querySelectorAll("#articleCreationDate");
	
	        for (var i = 0; i < elements.length; i++) {
	            changeDateFormat(elements[i]);
	        }
	    }
	    
	    // Zvítaznění aktivního linku
	    var currentPage = location.pathname.split('/')[2];
	    var links = document.querySelectorAll("#sideNavigation a");
	    
	    switch (currentPage) {
	    
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
	    
	</script>
	
</html>
