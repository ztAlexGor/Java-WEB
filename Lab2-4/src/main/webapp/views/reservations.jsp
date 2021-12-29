<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="booking" uri="hotel-booking" %>
<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="<c:url value="/assets/css/table.css"/>" type="text/css"/>
    <link rel="stylesheet" href="<c:url value="/assets/css/reservations.css"/>" type="text/css"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
    <script src="<c:url value="/assets/js/form.js"/>"></script>


    <title>Reservations</title>
</head>
<body>
<jsp:include page="/views/header.jsp"/>

<div class="container">
    <c:set var="user" scope="session" value="${sessionScope.user}"/>
    <c:set var="default_sort_column" scope="page"/>
    <c:if test="${user.admin}">
        <c:set var="default_sort_column" value="4"/>
    </c:if>
    <c:if test="${not user.admin}">
        <c:set var="default_sort_column" value="3"/>
    </c:if>
    <div>
	  <table id="example" class="table table-hover" style="text-align: center; align-content: center">
	    <thead>
	    	<c:if test="${user.admin}">
            	<th>Client</th>
       	 	</c:if>
	        <th>Check-in</th>
	        <th>check-out</th>
	        <th>Status</th>
	        <th>Details</th>
	    </thead>
	    <tbody>
	    <c:forEach var="reservation" items="${requestScope.reservations}">
	      <tr>
			<c:if test="${user.admin}">
            	<td>${reservation.user.email}</td>
       	 	</c:if>
	        <td><fmt:formatDate value="${reservation.arrivalDate}" type="date"/></td>
	        <td><fmt:formatDate value="${reservation.departureDate}" type="date"/></td>
	        <c:if test="${reservation.reservationStatus eq 'WAITING'}">
            	<td>Waiting</td>
       	 	</c:if>
	        <c:if test="${reservation.reservationStatus eq 'APPROVED'}">
            	<td>Approved</td>
       	 	</c:if>
       	 	<c:if test="${reservation.reservationStatus eq 'PAID'}">
            	<td>Paid</td>
       	 	</c:if>
       	 	<c:if test="${reservation.reservationStatus eq 'CHECKED_IN'}">
            	<td>Checked in</td>
       	 	</c:if>
       	 	<c:if test="${reservation.reservationStatus eq 'CHECKED_OUT'}">
            	<td>Checked out</td>
       	 	</c:if>
       	 	<c:if test="${reservation.reservationStatus eq 'CANCELLED'}">
            	<td>Cancelled</td>
       	 	</c:if>
	        <td><a href='${pageContext.request.contextPath}/controller?command=show_reservations_page&id=${reservation.id}&d-8003858-s=${param["d-8003858-s"]}&d-8003858-p=${param["d-8003858-p"]}&d-8003858-o=${param["d-8003858-o"]}'>
                ...
            </a></td>
	      </tr>
	    </c:forEach>
	    </tbody>
	  </table>
	</div>
    
    
    
</div>


    <c:set var="reservation_details" scope="request" value="${requestScope.reservation_details}"/>
    <c:set var="user" scope="session" value="${sessionScope.user}"/>
    <c:set var="rooms" scope="session" value="${sessionScope.rooms}"/>

    <c:if test="${not empty reservation_details}">
<div class="container" id="details">
        Details
        <hr>
        <table>
            <tr>
                <td>First name:</td>
                <td>${reservation_details.user.firstName}</td>
            </tr>
            <tr>
                <td>Second name:</td>
                <td>${reservation_details.user.secondName}</td>
            </tr>
            <tr>
                <td>Email:</td>
                <td>${reservation_details.user.email}</td>
            </tr>
            <tr>
                <td>Room:</td>
                <c:if test="${not empty reservation_details.room}">
                    <td>${reservation_details.room.id}</td>
                </c:if>
                <c:if test="${user.admin and reservation_details.reservationStatus eq 'WAITING'}">
                    <td>
                        <c:if test="${not empty rooms}">
                        <select name="room_id" form="approve" required>
                            <c:forEach items="${rooms}" var="room">
                                <option value="${room.id}">
                                    Room №${room.id}
                                </option>
                            </c:forEach>
                        </c:if>
                            <c:if test="${empty rooms}">
                                No suitable rooms
                            </c:if>
                        </select>
                    </td>
                </c:if>
            </tr>
            <tr>
                <td>Class:</td>
                <td>${reservation_details.roomClass.name}</td>
            </tr>
            <tr>
                <td>Number of persons:</td>
                <td>${reservation_details.personsAmount}</td>
            </tr>
            <tr>
                <td>Check-in:</td>
                <td>
                    <fmt:formatDate value="${reservation_details.arrivalDate}" type="date"/>
                </td>
            </tr>
            <tr>
                <td>Check-out:</td>
                <td>
                    <fmt:formatDate value="${reservation_details.departureDate}" type="date"/>
                </td>
            </tr>
            <tr>
                <td>Status:</td>
                <c:if test="${reservation_details.reservationStatus eq 'WAITING'}">
	            	<td>Waiting</td>
	       	 	</c:if>
		        <c:if test="${reservation_details.reservationStatus eq 'APPROVED'}">
	            	<td>Approved</td>
	       	 	</c:if>
	       	 	<c:if test="${reservation_details.reservationStatus eq 'PAID'}">
	            	<td>Paid</td>
	       	 	</c:if>
	       	 	<c:if test="${reservation_details.reservationStatus eq 'CHECKED_IN'}">
	            	<td>Checked in</td>
	       	 	</c:if>
	       	 	<c:if test="${reservation_details.reservationStatus eq 'CHECKED_OUT'}">
	            	<td>Checked out</td>
	       	 	</c:if>
	       	 	<c:if test="${reservation_details.reservationStatus eq 'CANCELLED'}">
	            	<td>Cancelled</td>
	       	 	</c:if>

            </tr>
            <tr>
                <td>Total price:</td>
                <c:if test="${not empty reservation_details.totalPrice}">
                    <td>${reservation_details.totalPrice} UAH</td>
                </c:if>
            </tr>
            <tr>

                <c:if test="${reservation_details.reservationStatus ne 'CANCELLED'
                and reservation_details.reservationStatus ne 'CHECKED_OUT'}">
                    <td>
                        <form action="${pageContext.request.contextPath}/controller" method="post">
                            <input type="hidden" name="command" value="cancel_reservation">
                            <input type="hidden" name="id" value="${reservation_details.id}">
                            <input type="submit" value="Cancel">
                        </form>
                    </td>
                </c:if>

                <c:if test="${reservation_details.reservationStatus eq 'WAITING' and user.admin}">
                    <td>
                        <c:if test="${not empty rooms}">
                            <form id="approve" action="${pageContext.request.contextPath}/controller" method="post">
                                <input type="hidden" name="command" value="approve">
                                <input type="hidden" name="id" value="${reservation_details.id}">
                                <input type="submit" value="Approve">
                            </form>
                        </c:if>
                        <c:if test="${empty rooms}">
                            <input type="button" value="Approve" disabled>
                        </c:if>
                    </td>
                </c:if>
                <c:if test="${reservation_details.reservationStatus eq 'APPROVED' and not user.admin}">
                    <td>
                    <form action="${pageContext.request.contextPath}/controller" method="post">
                    	<input type="hidden" name="command" value="pay">
        				<input type="hidden" name="id" value="${reservation_details.id}">
                    	<input type="submit" value="Pay">
                        </form>
                    </td>
                </c:if>
                <c:if test="${reservation_details.reservationStatus eq 'PAID' and user.admin}">
                    <td>
                        <form action="${pageContext.request.contextPath}/controller" method="post">
                            <input type="hidden" name="command" value="check_in">
                            <input type="hidden" name="id" value="${reservation_details.id}">
                            <input type="submit" value="Check in">
                        </form>
                    </td>
                </c:if>
                <c:if test="${reservation_details.reservationStatus eq 'CHECKED_IN' and user.admin}">
                    <td>
                        <form action="${pageContext.request.contextPath}/controller" method="post">
                            <input type="hidden" name="command" value="check_out">
                            <input type="hidden" name="id" value="${reservation_details.id}">
                            <input type="submit" value="Check out">
                        </form>
                    </td>
                </c:if>
            </tr>
        </table>
    </c:if>
</div>
</body>
</html>