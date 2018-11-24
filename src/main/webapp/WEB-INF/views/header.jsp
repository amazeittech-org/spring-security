<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="navbar navbar-inverse navbar-static-top">
    <div class="container">
        <div class="navbar-header">
            <a href="<spring:url value="/"/>" class="navbar-brand">Gomia Auto Service Center</a>
        </div>
        <ul class="nav navbar-nav">
            <li><a href="<spring:url value="/services/"/>">Services</a></li>
            <li><a href="<spring:url value="/appointments/"/>">Appointments</a></li>
            <li><a href="<spring:url value="/schedule/"/>">Schedule</a></li>
            <sec:authorize access="isAuthenticated()" var="authenticated"/>
            <c:url value="/logout" var="logoutUrl"/>
            <form id="logout" action="${logoutUrl}" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
            <c:choose>
                <c:when test="${authenticated}">
                    <li>
                        <p class="navbar-text">
                            Welcome <sec:authentication property="name"/>
                            ( <sec:authentication property="principal.firstName" />
                            <sec:authentication property="principal.lastName"/> )
                            <a href="javascript:document.getElementById('logout').submit()">Logout</a>
                        </p>
                    </li>
                </c:when>
                <c:otherwise>
                    <li><a href="<spring:url value="/login/" />">SignIn</a></li>
                    <li><a href="<spring:url value="/register/"/>">Register</a></li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</nav>