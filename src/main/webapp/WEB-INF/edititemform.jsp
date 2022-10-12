<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="../error.jsp" isErrorPage="false" %>

<t:fourthingsplus_template>
    <jsp:attribute name="header">
         Edit Item
    </jsp:attribute>

    <jsp:body>

        <h3>Please update item below</h3>

        <form method="post">
            <div class="form-outline mb-4">
                <label for="name" class="form-label">Name: </label>
                <input name="name"  class="form-control" id="name" type="text" value="${requestScope.item.name}"/>
            </div>
            <button class="btn btn-outline-secondary w-100" formaction="editupdate" value="${requestScope.item.id}"
                    name="item_id">Update my mind
            </button>
        </form>

    </jsp:body>

</t:fourthingsplus_template>