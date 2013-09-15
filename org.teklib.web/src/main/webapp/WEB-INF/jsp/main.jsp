<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/style.css" media="screen" />
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.10.2.min.js"></script>
<!-- jpaginate -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/jpaginate/jquery.paginate.js"></script>
<link href="${pageContext.request.contextPath}/resources/jpaginate/css/style.css" rel="stylesheet" type="text/css" />

<style>
#content-holder {
    /* dimensions */
    width:100%;
    padding:15px 0;

    /* centered */
    text-align:center;

    /* some "skinning" */
    background-color:#efefef;
    border:2px solid #fff;
    outline:1px solid #ddd;
    -moz-ouline-radius:4px;

    height: 330px;
    overflow: scroll;
    float: left;
    white-space: nowrap;
}
#slideshow-previous, #slideshow-next {
  width: 50px;
  height: 50px;
  position: absolute;
  background: transparent url("../images/arrow_left.gif") no-repeat 50% 50%;
  top: 150px;
}
#slideshow-next {
  display: block;
  background: transparent url("../images/arrow_right.gif") no-repeat 50% 50%;
  top: 150px;
  right: 0;
}
</style>

<script type="text/javascript">
    var PUBLISHER_OPTION = '<fmt:message key="bookList.publisher" />';
    var SHELF_OPTION = '<fmt:message key="bookList.shelf" />';

    function loadBooks(page) {

        var shelfText = $( "#shelf option:selected" ).text();
        var shelf = (shelfText == SHELF_OPTION ? '' : shelfText);
        var publisherText = $( "#publisher option:selected" ).text();
        var publisher = (publisherText == PUBLISHER_OPTION ? '' : publisherText);
        var search =  $( "#search" ).val();
        var index = 0;
        var itemsPerPage = 10;

        $('#content').children().remove();

        $.getJSON('${pageContext.request.contextPath}/app/books?shelf='+shelf+
            '&publisher='+publisher+'&search='+search+'&index='+((page-1)*itemsPerPage)+'&count='+itemsPerPage, function(data) {

            var bookList = data.books;
            for(i=0; i<bookList.length; i++) {
                var el = $('<img>', {
                    src: '${pageContext.request.contextPath}/app/image/'+(bookList[i].coverId),
                    id: bookList[i].id
                 }).addClass('slideshow-content');

                el.click(function() {
                    $.getJSON('${pageContext.request.contextPath}/app/book?id='+this.id, function(data) {
                        var bookInfo = $('#book-info');
                        bookInfo.empty();
                        bookInfo.append($('<h2/>').text(data.name));

                        for(i=0; i<data.files.length; i++) {
                            var image =  $('<img src="${pageContext.request.contextPath}/resources/'+data.files[i].type+'.png"/>');
                            bookInfo.append($('<a href="bookFile?file='+data.files[i].name+'"></a>').append(image));
                        }

                        var editImage =  $('<img src="${pageContext.request.contextPath}/resources/edit.png"/>');
                        bookInfo.append($('<a href="edit.htm?bookId='+data.id+'"></a>').append(editImage));

                        bookInfo.append($('<p></p>').text(data.author));
                        bookInfo.append($('<p></p>').text(data.releaseDate));
                        bookInfo.append(data.description);
                    });
                });

                $('#content').append(el);
            }
        });
        return false;
    }

    /* load the publishers */
    function loadSearchPublisher() {
        $.getJSON('publisher', function(data) {
            var publisher = $('#publisher');
            publisher.empty();
            publisher.append($('<option></option>').val('').text(PUBLISHER_OPTION));
            for (i=0; i<data.length; i++) {
                publisher.append(
                    $('<option></option>').val(data[i].text).text(data[i].text)
                );
            }
        });
    }

    function loadSearchShelf() {
        $.getJSON('shelf', function(data) {
            var shelf = $('#shelf');
                shelf.empty();
                shelf.append($('<option></option>').val('').text(SHELF_OPTION));
            for (i=0; i<data.length; i++) {
                    shelf.append(
                        $('<option></option>').val(data[i].text).text(data[i].text)
                    );
                }
        });
    }

    $(document).ready(function(){

        loadSearchPublisher();
        loadSearchShelf();
        loadBooks(1);

        $("#slideshow-next").hover(function(){
            $("#content-holder").animate({ scrollLeft: "+=5" }, "slow");
        });

        $("#slideshow-previous").hover(function(){
            $("#content-holder").animate({ scrollLeft: "-=5" }, "slow");
        });
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
            <a href="uncategorized.htm">uncategorized</a>
            <a href="lost.htm">lost</a>
            </form>
        </div>
    </navigation>

<!-- div id="pager"></div -->

<div id="content-holder">
<div id="slideshow-next"></div>
<div id="content"></div>
<div id="slideshow-previous"></div>
</div>

    <div id="book-info"></div>

    <div id="edit-div"> <!-- where does this close -->
     <div id="edit-navigation">
         <p>navigation</p>
     </div>

</div>
</body>