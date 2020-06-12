<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meals</title>
    <style>
        .excess {
            color: red
        }

        .not-excess {
            color: green
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<table>
    <tr>
        <th>Описание</th>
        <th>Калорийность</th>
        <th>Время</th>
    </tr>
    <c:forEach items="${mealList}" var="meal">
        <tr
                <c:if test="${meal.excess}">class="excess" </c:if>
                <c:if test="${!meal.excess}">class="not-excess" </c:if>>
            <td>
                    ${meal.description}
            </td>
            <td>
                    ${meal.calories}
            </td>
            <td>
                <fmt:parseDate value="${ meal.dateTime }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime"
                               type="both"/>
                <fmt:formatDate pattern="yyyy.MM.dd HH:mm" value="${ parsedDateTime }"/>
            </td>
            <td>
                <a href="meals/meal?action=update&mealId=${meal.id}">Исправить</a>
            </td>
            <td>
                <a href="meals/meal?action=delete&mealId=${meal.id}">Удалить</a>
            </td>
        </tr>
    </c:forEach>
</table>
<a href="meals/meal?action=create">Добавить</a>
</body>
</html>
