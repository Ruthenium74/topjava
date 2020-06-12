<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meals</title>
    <style>
        .exceed {
            background-color: red
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<table>
    <tr>
        <th>id</th>
        <th>Описание</th>
        <th>Калорийность</th>
        <th>Время</th>
    </tr>
    <c:forEach items="${mealList}" var="meal">
        <tr
                <c:if test="${meal.excess == 'true'}">class="exceed" </c:if> >
            <td>
                    ${meal.id}
            </td>
            <td>
                    ${meal.description}
            </td>
            <td>
                    ${meal.calories}
            </td>
            <td>
                <fmt:parseDate value="${ meal.dateTime }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime"
                               type="both"/>
                <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }"/>
            </td>
            <td>
                <a href="meals?action=update&mealId=${meal.id}">Исправить</a>
            </td>
            <td>
                <a href="meals?action=delete&mealId=${meal.id}">Удалить</a>
            </td>
        </tr>
    </c:forEach>
</table>
<a href="meal?action=create">Добавить</a>
</body>
</html>
