<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/style.css" media="screen" />
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.jstree-pre1.0.js"></script>
<style>
</style>

<script type="text/javascript">
$(document).ready(function(){
});
</script>

<title><fmt:message key="teklib.title" /></title>
</head>
<body>
    <header><fmt:message key="teklib.title" /></header>
    <navigation>
	    <div id="navigation" role="navigation">
            <form class="editForm" method="post" onsubmit="return loadBooks(false);">
            <select id="publisher" name="publisher" size="1" /><option/></select>
            <select id="shelf" name="shelf" size="1" /><option/></select>
            <input id="search" type="search" size="20" maxlength="50" name="search" />
            <button id="search-reset" type="image"><image src="../resources/clear.png"/></button>
            <button id="search-submit" type="image"><image src="../resources/search.png"/></button>
            <button id="edit-open" type="image"><image src="../resources/edit.png"/></button>
            </form>
        </div>
</navigation>


<div id="demo4" class="demo" style="height:100px;">
	<!--ul>
		<li id="phtml_1">
			<a href="#">Root node 1</a>
			<ul>
				<li id="phtml_2">
					<a href="#">Child node 1</a>
				</li>
				<li id="phtml_3">
					<a href="#">Child node 2</a>
				</li>
			</ul>
		</li>
		<li id="phtml_4">
			<a href="#">Root node 2</a>
		</li>
	</ul-->
</div>



<div id="book-info"></div>
<div id="edit-div">

    <ul class="css-tabs">
        <li id="li1"><a id="a1" href="#">Tab 1</a></li>
        <li id="li2"><a id="a2" href="#">Tab 2</a></li>
        <li id="li3"><a id="a3" href="#">Tab 3</a></li>
    </ul>

    <div class="css-panes">
        <div id="div1">First tab content. Tab contents are called "panes"</div>
        <div id="div2">Second tab content</div>
        <div id="div3">Third tab content</div>
    </div>

    <div id="edit-form-div">
    <form id="edit-form">
        <input type="hidden" id="book-id"/>
	    <label for="book-shelf">Shelf:</label><input name="book-shelf" id="book-shelf"></input>
            <label for="book-publisher">Publisher:</label><input name="book-publisher" id="book-publisher"></input>
            <label for="book-name">Name:</label><input name="book-name" id="book-name" length="50" required></input>
            <label for="book-author">Author:</label><input id="book-author" length="50"></input>
            <label for="book-isbn">ISBN:</label><input id="book-isbn" length="50"></input>
            <label>File:</label><a id="file" href="#">file</a>
            <label><fmt:message key="edit.cover"/></label>
            <textarea id="book-description" rows="7" cols="50" name="description"></textarea>
            <label for="releaseDate"><input id="book-release-date" length="20" name="releaseDate"></input></label>
            <br/>
            <button id="ok-button" name="submit" type="image"><image src="../resources/ok.png"/></button>
            <button name="cancel" class="close" type="image"><image src="../resources/cancel.png"/></button>
            <button id="delete-button" name="delete" type="image"><image src="../resources/delete.png"/></button>
            <button height="10px" width="10px" id="amazon-lookup"><image src="../images/amazon.png"/></button>
        </form>
    </div>
</div>
</body>
