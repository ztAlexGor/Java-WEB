<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="booking" uri="hotel-booking" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="<c:url value="/assets/css/book.css"/>" type="text/css"/>

    <title>Book a room</title>
</head>
<body>
    <jsp:include page="/views/header.jsp"/>

    <div class="reservation_request">
        <form action="${pageContext.request.contextPath}/controller" method="post">
            <input type="hidden" name="command" value="book">
            <label>
                Check-in date <br>
                <booking:dateInput name="arrival_date" minimumDate="today"/> <br>
            </label>
            <label>
                Check-out date <br>
                <booking:dateInput name="departure_date" minimumDate="tomorrow"/> <br>
            </label>
            <label>
                Room class <br>
                <select name="room_class">
                    <c:forEach var="room_class" items="${requestScope.room_classes}">
                        <option value="${room_class.name}">${room_class.name}</option>
                    </c:forEach>
                </select> <br>
            </label>
            <label>
                Number of persons <br>
                <select name="persons_amount">
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                    <option value="6">6</option>
                </select> <br>
            </label>
            <input type="submit" value="Book a room"/>
        </form>
    </div>
</body>
</html>
