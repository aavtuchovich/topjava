<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Meal</title>
</head>
<body>
<form method="post" action="meals">
    <label for="dateTime">DateTime</label><input type="datetime-local" name="dateTime" id="dateTime"
                                                 value="${meal.dateTime}"><br/>
    <label for="description">Description</label><input type="text" name="description" id="description"
                                                       value="${meal.description}"><br/>
    <label for="calories">Calories</label><input type="text" name="calories" id="calories"
                                                 value="${meal.calories}"><br/>
    <input type="submit">
</form>
</body>
</html>
