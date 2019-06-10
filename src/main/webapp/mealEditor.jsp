<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Edit</title>
	<style>
		div.container {
			width: 500px;
			clear: both;
		}

		div.container input.data {
			float: right;
			clear: both;
		}

		div.container input.button {
			width: 48%;
			clear: both;
		}
	</style>
</head>
<body>
<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
<jsp:useBean id="formatter" scope="request" type="java.time.format.DateTimeFormatter"/>

<div class="container">
	<form method="POST" action="${pageContext.request.contextPath}/meals" name="frmAddUser">
		<input type="hidden" name="id" value="${meal.id}">
		Date : <label><input class="data" type="datetime-local" name="dateTime"
							 value="${meal.dateTime.format(formatter)}"></label><br/>
		Description : <label><input class="data" type="text" name="description" value="${meal.description}"></label><br/>
		Calories : <label><input class="data" type="number" name="calories" value="${meal.calories}"></label><br/>
		<input class="button" type="submit" value="Submit">
		<input class="button" type="button" value="Cancel" onclick="window.location='${pageContext.request.contextPath}/meals';"/>
	</form>
</div>
</body>
</html>
