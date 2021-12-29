<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="<c:url value="/assets/css/login.css"/>" type="text/css"/>

    <title>Log in</title>
</head>
<body>
    <jsp:include page="/views/header.jsp"/>

    <c:set var="user" scope="session" value="${sessionScope.user}"/>
    <c:if test="${not empty user}">
        <jsp:forward page="/home"/>
    </c:if>

    <div class="login">
        <form action="${pageContext.request.contextPath}/controller" method="post">
            <input type="hidden" name="command" value="login">
            <label >
                Email
                <input type="text" name="email" required>
            </label>
            <label>
                Password
                <input type="password" name="password" required>
            </label>
                <input class="button" type="submit" value="Log in">
        </form>
        <c:set var="is_login_failed" scope="request" value="${requestScope.is_login_failed}"/>
        <c:if test="${is_login_failed}">
            <div id="fail_message">
                You have entered an invalid email or password
            </div>
            <c:set var="is_login_failed" scope="session" value=""/>
        </c:if>

    </div>
</body>
</html>
