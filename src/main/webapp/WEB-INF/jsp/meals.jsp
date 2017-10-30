<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/datatablesUtil.js" defer></script>
<script type="text/javascript" src="resources/js/mealDatatables.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>

<section>
    <h3><spring:message code="meal.title"/></h3>

    <form method="post" action="meals/filter">
        <dl>
            <dt><spring:message code="meal.startDate"/>:</dt>
            <dd><input type="date" name="startDate" value="${param.startDate}"></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.endDate"/>:</dt>
            <dd><input type="date" name="endDate" value="${param.endDate}"></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.startTime"/>:</dt>
            <dd><input type="time" name="startTime" value="${param.startTime}"></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.endTime"/>:</dt>
            <dd><input type="time" name="endTime" value="${param.endTime}"></dd>
        </dl>
        <button type="submit"><spring:message code="meal.filter"/></button>
    </form>
    <hr>
    <button type="button" class="btn" id="btnId"><spring:message code="meal.add"/></button>
    <hr>
    <div class="container">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
        <div class="modal fade" id="modalId" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title"><spring:message code="${meal.isNew() ? 'meal.add' : 'meal.edit'}"/></h4>
                    </div>
                    <form method="post" action="meals">
                        <div class="modal-body">

                            <input type="hidden" name="id" value="${meal.id}">
                            <dl>
                                <dt><spring:message code="meal.dateTime"/>:</dt>
                                <dd><input type="datetime-local" value="${meal.dateTime}" name="dateTime"></dd>
                            </dl>
                            <dl>
                                <dt><spring:message code="meal.description"/>:</dt>
                                <dd><input type="text" value="${meal.description}" size=40 name="description"></dd>
                            </dl>
                            <dl>
                                <dt><spring:message code="meal.calories"/>:</dt>
                                <dd><input type="number" value="${meal.calories}" name="calories"></dd>
                            </dl>

                        </div>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-success"><spring:message code="common.save"/></button>
                            <button type="button" onclick="window.history.back()" class="btn btn-default"
                                    data-dismiss="modal"><spring:message
                                    code="common.cancel"/></button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <table id="datatable" class="table table-striped display" border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th><spring:message code="meal.dateTime"/></th>
            <th><spring:message code="meal.description"/></th>
            <th><spring:message code="meal.calories"/></th>
            <th><spring:message code="common.actions"/></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="mealWithExceed">
            <jsp:useBean id="mealWithExceed" type="ru.javawebinar.topjava.to.MealWithExceed"/>
            <tr class="${mealWithExceed.exceed ? 'exceeded' : 'normal'}">
                <td>
                        <%--${mealWithExceed.dateTime.toLocalDate()} ${mealWithExceed.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(mealWithExceed.getDateTime())%>--%>
                        <%--${fn:replace(mealWithExceed.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(mealWithExceed.dateTime)}
                </td>
                <td>${mealWithExceed.description}</td>
                <td>${mealWithExceed.calories}</td>
                <td><a href="meals/update?id=${mealWithExceed.id}"><spring:message code="common.update"/></a>
                    <br/><a href="meals/delete?id=${mealWithExceed.id}"><spring:message code="common.delete"/></a></td>
            </tr>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>