<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" isELIgnored="false" %>

<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="<c:url value="/assets/css/header.css"/>" type="text/css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <title></title>

</head>
<body>
<div class="header">
    <div class="header__inner">
        <div class="logo">
            <h1><span>HOTEL</span></h1>
        </div>
        <c:set var="user" scope="session" value="${sessionScope.user}"/>
        <ul class="navigation">
            <a href="${pageContext.request.contextPath}/controller?command=show_home_page"><li>Home</li></a>
            <c:if test="${empty user}">
                <a href="${pageContext.request.contextPath}/controller?command=show_login_page">
                    <li>Log in</li>
                </a>
            </c:if>
            <c:if test="${not empty user}">
                <c:if test="${user.admin}">
                    <a href="${pageContext.request.contextPath}/controller?command=show_rooms_page"><li>Room</li></a>
                </c:if>
                <c:if test="${not user.admin}">
                    <a href="${pageContext.request.contextPath}/controller?command=show_book_page"><li>Book</li></a>
                </c:if>
                <a href="${pageContext.request.contextPath}/controller?command=show_reservations_page">
                    <li>Reservations</li>
                </a>
                <a href="${pageContext.request.contextPath}/controller?command=sign_out">
                    <li>Sing out</li>
                </a>
            </c:if>
        </ul>
    </div>
</div>
</body>
</html>
