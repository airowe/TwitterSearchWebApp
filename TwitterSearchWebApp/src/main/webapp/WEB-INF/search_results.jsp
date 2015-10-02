<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link rel="stylesheet" type="text/css" href="/style/search_styles.css" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search Twitter</title>
</head>
<body>
<div>
<form action="display_search" name="display_search_results" class="panel">
<input type="hidden" name="searchQuery" id="searchQuery" value=""/>
<br>Search Term:

<select id="searchRequestsList" onchange="setSearchQuery()">
<c:forEach items="${searchRequests}" var="searchRequest">
	<option value="${searchRequest}" <c:if test="${searchRequest == searchQuery}">selected</c:if>>${searchRequest}</option>
</c:forEach>
</select>
  <input type="submit" value="Display Results">
</form>
<form action="/TwitterSearchWebApp/index.jsp">
	<input type="submit" value="Home">
</form>
</div>
<div>
<c:forEach items="${requestScope.searchResults}" var="searchResult">
        <div class="left">@${searchResult.userName} - ${searchResult.text}</div>
</c:forEach>
</div>
</body>

 <script LANGUAGE="JavaScript" TYPE="TEXT/JAVASCRIPT">
 setSearchQuery();
 
 function setSearchQuery()
 {
	 document.getElementById("searchQuery").value = document.forms['display_search_results'].searchRequestsList.value;
 }
 </script>
 </html>