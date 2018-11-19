<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"

	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Gomia Auto Service Center</title>

<link rel="stylesheet"
	href="<spring:url value="/resources/css/global.css"/>" />
<link rel="stylesheet"
	href="<spring:url value="/resources/css/datepicker.css"/>" />
<link rel="stylesheet"
	href="<spring:url value="/resources/css/bootstrap-multiselect.css"/>" />

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

<!-- Latest Jquery -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"
	type="text/javascript"></script>
<!-- Latest compiled and minified JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script
	src="<spring:url value="/resources/js/bootstrap-datepicker.js"/>"></script>
<script
	src="<spring:url value="/resources/js/bootstrap-multiselect.js"/>"></script>
<script src="<spring:url value="/resources/js/appointments.js"/>"></script>
<script>
	var root = "${pageContext.request.contextPath}";
</script>
</head>
<body>
	<jsp:include page="header.jsp"/>
	<div class="container">
		<div class="row">
			<h1>Appointment</h1>
		</div>
		<ul class="list-group">
			<li class="list-group-item"><label>Customer:</label><span>${appointment.user.firstName } ${appointment.user.lastName}</span></li>
			<li class="list-group-item"><label>Appointment Date:</label><span>${appointment.appointmentDt}</span></li>	
			<li class="list-group-item"><label>Make:</label><span>${appointment.automobile.make}</span></li>
			<li class="list-group-item"><label>Model:</label><span>${appointment.automobile.model }</span></li>
			<li class="list-group-item"><label>Year:</label><span>${appointment.automobile.year }</span></li>
			<li class="list-group-item">
				<label>Services:</label>
				<ul>
					<c:forEach items="${appointment.services}" var="service">
						<li>${service}</li>
					</c:forEach>
				</ul>
			</li>
			<li class="list-group-item"><label>Status:</label><span>${appointment.status}</span>
			<li class="list-group-item">
				<a class="btn btn-default"
				   href="<spring:url value="/appointments/"/>" role="button">Back</a>
				<%-- Protected by the user role --%>
				<%--<sec:authorize access="hasRole('ROLE_USER')">--%>
				<%-- isUser is the ModelAttribute defined in AppointmentController. It is set based on the Authorities of the
				user. This way is helpful because with this approach we do not need to mention the role check logic
				in multiple place and thus it is very easy to maintain --%>
				<sec:authorize access="${isUser}">
					<a class="btn btn-default"
				   		href="<spring:url value="/appointments/cancel"/>" role="button">Cancel</a>
				</sec:authorize>

				<%--Anything we can use in the access attribute in the security-context.xml file, we can use it here--%>
				<sec:authorize access="hasAuthority('ROLE_ADMIN')">
					<a class="btn btn-default"
					   href="<spring:url value="/appointments/confirm"/>" role="button">Confirm</a>
				</sec:authorize>

				<%-- Now when SS is determining to display that button on or not, it will check if the user has access
				 	 to 'schedule*' or not by referring to configuration in security-context.xml, find the 'access'
				 	 attribute and execute the SPEL or condition mentioned in it.
				 --%>
				<sec:authorize url="/schedule/*">
					<a class="btn btn-default"
					   href="<spring:url value="/appointments/complete"/>" role="button">Mark Complete</a>
				</sec:authorize>
			</li>
		</ul>
	</div>

</body>
</html>