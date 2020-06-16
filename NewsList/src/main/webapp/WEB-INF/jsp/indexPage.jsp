<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>

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
	<script type="text/javascript" src="javaScript.js"></script>
	
</html>
