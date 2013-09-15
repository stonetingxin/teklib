<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<!--[if lt IE 7 ]> <html class="no-js ie6" lang="en"> <![endif]-->
<!--[if IE 7 ]>    <html class="no-js ie7" lang="en"> <![endif]-->
<!--[if IE 8 ]>    <html class="no-js ie8" lang="en"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--><html class="no-js" lang="en"><!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/style.css" media="screen" />
<title><fmt:message key="uncategorized.title" /></title>
</head>
<body>
  <div id="container">
    <header>
		<h1><fmt:message key="teklib.title" /></h1>
    </header>
	<navigation>
	    <div id="main" role="main">|
            <a href="main.htm">Home</a>|
            <a href="shelf.htm">Shelf</a>|
            <a href="publisher.htm">Publisher</a>|
            <a href="lost.htm">Lost</a>|
        </div>
    </navigation>
	<h2><fmt:message key="uncategorized.title" /></h2>
    <div id="main" role="main">
		<c:forEach items="${files}" var="book">
		 	<c:url value="edit.htm" var="url">
		 		<c:param name="filename" value="${book}"/>
			</c:url>
			<a href="${url}">${book}</a><br/>
		</c:forEach>
    </div>
  </div>
</body>
</html>
