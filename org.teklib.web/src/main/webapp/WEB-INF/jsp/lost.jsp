<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/style.css" media="screen" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/mootools-core-1.4.5-full-nocompat.js"></script>
<title><fmt:message key="book.lost.title" /></title>
</head>

<body>
	<!-- the books -->
    <header>
    	<h1><fmt:message key="book.lost.title"/></h1>
    </header>
	<navigation>
	    <div id="main" role="main">|
            <a href="main.htm">Home</a>|
            <a href="uncategorized.htm">Uncategorized</a>|
            <a href="shelf.htm">Shelf</a>|
            <a href="publisher.htm">Publisher</a>|
        </div>
    </navigation>
	<c:forEach items="${lost}" var="book">
		<a href="${pageContext.request.contextPath}/app/edit.htm?bookId=${book.id}">${book.name}</a><br/>
	</c:forEach>
</body>
</html>
