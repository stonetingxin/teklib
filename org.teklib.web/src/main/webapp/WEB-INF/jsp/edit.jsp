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
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/style.css" media="screen" />

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>

<link href="${pageContext.request.contextPath}/resources/jquery.ui.ufd-0.6/css/ufd-base.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/resources/jquery.ui.ufd-0.6/css/plain/plain.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/jquery.ui.ufd-0.6/jquery.ui.ufd.min.js"></script>

<title><fmt:message key="uncategorized.title" /></title>
</head>
<body>
    <header>
		<h1><fmt:message key="teklib.title" /></h1>
    </header>
    <div id="edit-form-div">
    	<form:form id="edit-form" name="bookEdit" modelAttribute="bookEditBean" method="post">
           	<form:hidden path="id" id="book-id"/>

            <label for="book-shelf"><fmt:message key="edit.shelf"/></label>
        	<form:select id="shelf" path="shelf" items="${bookEditBean.shelfList}"/>

            <label for="book-publisher"><fmt:message key="edit.publisher"/></label>
	        <form:select id="publisher" path="publisher" items="${bookEditBean.publisherList}"/>

            <label for="book-name"><fmt:message key="edit.name"/></label>
            <form:input id="book-name" size="50" path="name" required="true"/>
            <form:errors path="name" cssClass="errors"/>

            <label for="book-author"><fmt:message key="edit.author"/></label>
            <form:input id="book-author" size="50" path="author" required="true"/>
            <form:errors path="author" cssClass="errors"/>

            <label for="book-isbn"><fmt:message key="edit.isbn"/></label>
            <form:input path="isbn" required="true" id="book-isbn" length="50"/>
        	<form:errors path="isbn" cssClass="errors"/>

            <label><fmt:message key="edit.file"/></label>
            <c:forEach items="${bookEditBean.files}" var="file">
                <c:url value="/app/bookFile" var="url">
                    <c:param name="file" value="${file}"/>
                </c:url>
                <a href="${url}">${file}</a><br/>
            </c:forEach>

            <label><fmt:message key="edit.cover"/></label>
            <spring:bind  path="bookEditBean.coverUrl">
                <input id="coverUrl" type="text" size="50" maxlength="255" name="coverUrl" value="${status.value}"/><br/>
                <c:choose>
                    <c:when test="${bookEditBean.coverUrl ne null and bookEditBean.coverUrl ne ''}">
                        <img id="cover" border="0" src="${status.value}" width="135px" height="180px"/>
                    </c:when>
                    <c:when test="${bookEditBean.coverId gt 0}">
                        <img id="cover" border="0" src="${pageContext.request.contextPath}/app/image/${bookEditBean.coverId}" width="135px" height="180px"/>
                    </c:when>
                    <c:otherwise>
                        <img id="cover" border="0" src="" style="display:none" width="135px" height="180px"/>
                    </c:otherwise>
                </c:choose>
            </spring:bind>

            <spring:bind path="bookEditBean.description">
              <textarea id="book-description" rows="7" cols="50" name="description"><c:out value="${status.value}"/></textarea>
            </spring:bind>

            <label for="releaseDate">Release Date:</label>
            <spring:bind path="bookEditBean.releaseDate">
              <input id="book-release-date" type="text" size="20" maxlength="20" name="releaseDate" value="${status.value}"/>
            </spring:bind>

            <br/>
            <button id="ok-button" name="formAction" value="submit" type="image"><image src="../resources/ok.png"/></button>
            <button name="formAction" value="cancel" type="image"><image src="../resources/cancel.png"/></button>
            <c:if test="${bookEditBean.id ne 0}">
                <button id="delete-button" type="image" name="formAction" value="delete">
                    <image src="../resources/delete.png"/>
                </button>
            </c:if>
            <button height="10px" width="10px" id="amazon-lookup"><image src="../resources/amazon.png"/></button>
        </form:form>
    </div>

  <script type="text/javascript">
    $(document).ready(function(){
        $("#amazon-lookup").click(function() {
          var isbn = $("#book-isbn").val();
           $.getJSON('${pageContext.request.contextPath}/ajax/lookup.json?isbn='+isbn, function(data) {

              var newoption = '<option value="'+data.publisher+'">'+data.publisher+'</option>';
			  $('#publisher').prepend(newoption);
              $('#publisher').ufd("changeOptions");

              $('#book-name').val(data.name);
              $('#book-author').val(data.author);
              $('#book-description').val(data.description);
              $('#book-release-date').val(data.releaseDate);
              $('#coverUrl').val(data.imageUrl);
           });
           return false;
        });
        $("#shelf").ufd({submitFreeText:true});
        $("#publisher").ufd({submitFreeText:true});
    });
  </script>
  
</body>
</html>
