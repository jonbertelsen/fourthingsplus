<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="error.jsp" isErrorPage="false" %>

<t:fourthingsplus_template>
    <jsp:attribute name="header">
         Fourthingsplus
    </jsp:attribute>

    <jsp:body>
        <form action="login" method="post">
            <div class="form-outline mb-4">
                <label class="form-label" for="username">Username: </label>
                <input type="text" class="form-control" id="username" name="username"/>
            </div>
            <div class="form-outline mb-4">
                <label class="form-label" for="password">Password: </label>
                <input type="password" class="form-control" id="password" name="password"/>
            </div>

            <button type="submit" class="btn btn-outline-secondary w-100">Get access to my mind</button>
        </form>
    </jsp:body>

</t:fourthingsplus_template>