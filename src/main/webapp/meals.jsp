<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Meal list</title>
</head>
<body>
<jsp:include page="./WEB-INF/fragments/header.jsp"/>
<section>
    <h3><a href="">Add meal</a></h3>

    <table border="1" cellpadding="10" cellspacing="10">
        <tr>
            <th class="mainTableDate">Date</th>
            <th class="mainTableDescription">Description</th>
            <th class="mainTableCalories">Calories</th>
            <th class="mainTableButtons"></th>
            <th class="mainTableButtons"></th>
        </tr>
        <c:forEach items="${mealsTo}" var="mealTo">
            <jsp:useBean id="mealTo" type="ru.javawebinar.topjava.model.MealTo"/>
            <tr style="color:${mealTo.excess ? 'red' : 'green'}">
                <td>
                    <fmt:parseDate value="${mealTo.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime"
                                   type="both"/>
                    <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${parsedDateTime}"/>
                </td>
                <td>${mealTo.description}</td>
                <td>${mealTo.calories}</td>
                <td><a href="">Update</a></td>
                <td><a href="">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
<jsp:include page="./WEB-INF/fragments/footer.jsp"/>
</body>
</html>