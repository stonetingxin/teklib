<%@ page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
        <?xml version="1.0" encoding="UTF-8"?>
<feed xmlns="http://www.w3.org/2005/Atom"
      xmlns:dc="http://purl.org/dc/terms/"
      xmlns:opds="http://opds-spec.org/2010/catalog">
<id>urn:uuid:433a5d6a-0b8c-4933-af65-4ca4f02763eb</id>

<link rel="related"
      href="/opds-catalogs/vampire.farming.xml"
      type="application/atom+xml;profile=opds-catalog;kind=acquisition"/>
<link rel="self"
      href="/opds-catalogs/unpopular.xml"
      type="application/atom+xml;profile=opds-catalog;kind=acquisition"/>
<link rel="start"
      href="/opds-catalogs/root.xml"
      type="application/atom+xml;profile=opds-catalog;kind=navigation"/>
<link rel="up"
      href="/opds-catalogs/root.xml"
      type="application/atom+xml;profile=opds-catalog;kind=navigation"/>

<title>Spielhuus ebooks</title>
<updated>2010-01-10T10:01:11Z</updated>
<author>
    <name>Spec Writer</name>
    <uri>http://opds-spec.org</uri>
</author>


    <c:forEach items="${shelfList}" var="shelf">
        <c:url value="odps.xml" var="url">
            <c:param name="shelf" value="${shelf.value}"/>
        </c:url>
        <entry>
            <title><c:out value="${shelf.value}"/></title>
            <link rel="http://opds-spec.org/sort/popular"
                  href="${url}"
                  type="application/atom+xml;profile=opds-catalog;kind=acquisition"/>
            <!-- updated>2010-01-10T10:01:01Z</updated>
            <id>urn:uuid:d49e8018-a0e0-499e-9423-7c175fa0c56e</id>
            <content type="text">Popular publications from this catalog based on downloads.</content  -->
        </entry>
    </c:forEach>

    <c:forEach items="${items}" var="item">
        <entry>
            <title><c:out value="${item.name}"/></title>
            <id>urn:uuid:6409a00b-7bf2-405e-826c-3fdff0fd0734</id>
            <updated>2010-01-10T10:01:11Z</updated>
            <author>
                <name><c:out value="${item.author}"/></name>
            </author>
            <dc:issued><c:out value="${item.releaseDate}"/></dc:issued>
            <summary><c:out value="${item.description}"/></summary>

            <link rel="http://opds-spec.org/image/thumbnail"
                  href="image/${item.coverImage}"
                  type="image/jpeg"/>
            <link rel="http://opds-spec.org/image"
                  href="image/${item.coverImage}"
                  type="image/jpeg"/>

            <c:forEach items="${item.files}" var="file">
               <c:url value="bookFile" var="url">
                   <c:param name="file" value="${file.filename}"/>
               </c:url>

                <link rel="http://opds-spec.org/acquisition"
                      href="${url}"
                      type="application/${file.format}"/>
            </c:forEach>
        </entry>
    </c:forEach>
</feed>