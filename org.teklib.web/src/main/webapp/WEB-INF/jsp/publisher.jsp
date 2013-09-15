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
<title><fmt:message key="book.title" /></title>
</head>

<body>
  <div id="container">
    <header>
		<h1><fmt:message key="teklib.title" /></h1>
    </header>
	<navigation>
	    <div id="main" role="main">|
            <a href="main.htm">Home</a>|
            <a href="uncategorized.htm">Uncategorized</a>|
            <a href="shelf.htm">Shelf</a>|
            <a href="lost.htm">Lost</a>|
        </div>
    </navigation>

	<h2><fmt:message key="publisher.title" /></h2>
    <div id="shelf">
		<c:forEach items="${publisherList}" var="publisher">
			<a href="${pageContext.request.contextPath}/app/editPublisher?publisherName=${publisher}">${publisher}</a><br/>
		</c:forEach>
    </div>
  </div>
</body>
</html>