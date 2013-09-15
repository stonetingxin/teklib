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
    <header>
		<h1><fmt:message key="teklib.title" /></h1>
    </header>
    <div id="shelf">
	<h2><fmt:message key="shelfEdit.title"/></h2>
	<form:form name="shelfEdit" modelAttribute="shelfEditBean" method="post">
	<table border="0" width="600px">
	  <tr>
		<th class="editTitle"><fmt:message key="edit.name"/></th>
		<td class="editValue">
			<form:input path="name" />
		    <form:errors path="name" cssClass="errors"/>
		</td>
	  </tr>
	<tr><th></th><td></td></tr>
  	  <tr>
		<th class="editTitle"></th>
		<td class="editValue">
		
			<form:hidden path="id" />

			<button id="cancel" type="submit"  name="formAction" value="cancel">Cancel</button>
			<button id="submit" type="submit"  name="formAction" value="submit">Submit</button>
			<c:if test="${shelfEditBean.bookCount eq 0}">
				<button id="delete" type="submit" name="formAction" value="delete">Delete</button>
			</c:if>
		</td>
	  </tr>
	</table>
	</form:form>
    </div>
  </div>
  <script type="text/javascript">
  </script>
</body>
</html>
