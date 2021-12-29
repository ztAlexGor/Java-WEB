<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="booking" uri="hotel-booking" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <link rel="stylesheet" href="<c:url value="/assets/css/book.css"/>" type="text/css"/>
    <link rel="stylesheet" href="<c:url value="/assets/css/table.css"/>" type="text/css"/>
    <title>Rooms</title>
</head>
<body>
<jsp:include page="/views/header.jsp"/>

<div class="container">
    <form action="${pageContext.request.contextPath}/controller" method="post">
	    <div>
		  <table id="room_class" class="table table-hover" style="text-align: center; align-content: center">
		    <thead>
		        <th>Class</th>
		        <th>Basic rate in UAH</th>
		        <th>Rate per person</th>
		    </thead>
		    <tbody>
		    <c:forEach var="room_class_line" items="${requestScope.room_classes}">
		      <tr>
		        <td>${room_class_line.name}</td>
		        <td>
		        	<input type="hidden" name="name" value="${room_class_line.name}">
		            <input type="text" required value="${room_class_line.basicRate}" name="basic_rate" pattern="\d+\.\d{2}">
		        </td>
		        <td>
		        	<input type="text" required value="${room_class_line.ratePerPerson}" name="rate_per_person" pattern="\d+\.\d{2}">
		        </td>
		      </tr>
		    </c:forEach>
		    </tbody>
		  </table>
		</div>
        <input type="hidden" name="command" value="save_prices">
        <input type="submit" value="Save" id="save">
    </form>
    <form action="${pageContext.request.contextPath}/controller" method="get">
        <input type="submit" value="Cancel" id="cancel">
        <input type="hidden" name="command" value="update_page">
    </form>
</div>
<div class="container">
	<div>
	  <table id="room" class="table table-hover" style="text-align: center; align-content: center">
	    <thead>
	        <th>Room number</th>
	        <th>Class</th>
	        <th>Beds amount</th>
	        <th>Active</th>
	    </thead>
	    <tbody>
	    <c:forEach var="room" items="${requestScope.rooms}">
	      <tr>
	        <td>${room.id}</td>
	        <td>${room.roomClass.name}</td>
	        <td>${room.bedsAmount}</td>
	        <td><form action="${pageContext.request.contextPath}/controller" method="post">
                <input type="hidden" name="command" value="change_room_status">
                <input type="hidden" name="id" value="${room.id}">
                <input type="checkbox" name="checkbox" value="checked"
                       <c:if test="${room.active}">checked="checked"</c:if>
                       onchange="this.form.submit()"
                />
            </form></td>
	      </tr>
	    </c:forEach>
	    </tbody>
	  </table>
	</div>
</div>	
</body>
</html>
