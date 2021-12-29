<%@page isErrorPage="true" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="<c:url value="/assets/css/error.css"/>" type="text/css"/>

    <title>Error</title>
</head>
<body>
    <jsp:include page="/views/header.jsp"/>
    <div class="form">
        Something went wrong
        <form action="${pageContext.request.contextPath}/controller" method="get">
            <input type="hidden" name="command" value="show_home_page">
            <input type="submit" value="Home">
        </form>
    </div>
</body>
</html>
