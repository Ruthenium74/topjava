<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 10.06.2020
  Time: 19:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meal</title>
</head>
<body>
    <form action="meals" method="post">
        <input name="id" hidden value="${meal.id}">
        <label for="desc">Описание</label>
        <input id="desc" type="text" name="description" placeholder="Завтрак" value="${meal.description}"
               required>
        <label for="dTime">Дата и время</label>
        <input id="dTime" type="datetime-local" name="dateTime" value="${meal.dateTime}" required>
        <label for="cal">Калории</label>
        <input id="cal" type="number" name="calories" value="${meal.calories}" required>
        <input type="submit" value="Сохранить">
    </form>
</body>
</html>
