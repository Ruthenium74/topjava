<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
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
<h3><a href="meals">Назад</a></h3>
</body>
</html>
