<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Edit</title>
</head>
<body>
<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
<jsp:useBean id="formatter" scope="request" type="java.time.format.DateTimeFormatter"/>

<form method="POST" action="meals" name="frmAddUser">
	<table>
		<tr>
			<td></td>
			<td>
				<input type="hidden" name="id" value="${meal.id}">
			</td>
		</tr>
		<tr>
			<td>
				Date :
			</td>
			<td>
				<input class="data" name="dateTime" type="datetime-local" value="${meal.dateTime.format(formatter)}">
			</td>
		</tr>
		<tr>
			<td>
				Description :
			</td>
			<td>
				<input class="data" name="description" type="text" value="${meal.description}">
			</td>
		</tr>
		<tr>
			<td>
				Calories :
			</td>
			<td>
				<input class="data" name="calories" type="number" value="${meal.calories}">
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<input class="button" type="submit" value="Submit">
				<input class="button" type="button" value="Cancel" onclick="window.location='meals';"/>
			</td>
		</tr>
	</table>
</form>
</body>
</html>
