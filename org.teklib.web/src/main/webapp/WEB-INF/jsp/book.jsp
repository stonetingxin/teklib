<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<!--[if lt IE 7 ]> <html class="no-js ie6" lang="en"> <![endif]-->
<!--[if IE 7 ]>    <html class="no-js ie7" lang="en"> <![endif]-->
<!--[if IE 8 ]>    <html class="no-js ie8" lang="en"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--><html class="no-js" lang="en"><!--<![endif]-->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/style.css" media="screen" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/mootools-core-1.3.2-full-nocompat-yc.js"></script>
<title><fmt:message key="book.title" /></title>
</head>

<body>
  <div id="container">
    <div id="book">
			<image class="BookImage" align="left" src="${pageContext.request.contextPath}/app/image/${book.coverImage}.jpg" height="300" width="220"/>
    		<h2><fmt:message key="book.title" /><c:out value="${book.name}"/></h2>

			<fmt:message key="book.isbn"/><c:out value="${book.isbn}"/><br/>
			<fmt:message key="book.author"/><c:out value="${book.author}"/><br/>
			<fmt:message key="book.publisher"/><c:out value="${book.publisher}"/><br/>
			<fmt:message key="book.date"/><c:out value="${book.releaseDate}"/><br/>

			<c:forEach items="${book.files}" var="file">
			 	<c:url value="/app/bookFile" var="url">
			 		<c:param name="file" value="${file.filename}"/>
				</c:url>
				|<a href="${url}">${file.format}</a>
			</c:forEach>

			|<a href="${pageContext.request.contextPath}/app/edit.htm?bookId=${book.id}" target="_top"><fmt:message key="book.edit"/></a>|
		
			<p><c:out escapeXml="false" value="${book.description}"/></p>
    </div>
    <footer>
    </footer>
  </div>
</body>
</html>
