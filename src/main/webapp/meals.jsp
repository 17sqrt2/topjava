<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<title>Meals</title>
	<style>
		tr.noExcess {
			background-color: #28a745;
		}

		tr.excess {
			background-color: #dc3545;
		}
	</style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<br>
<table border=1>
	<thead>
	<tr>
		<th>Date</th>
		<th>Description</th>
		<th>Calories</th>
		<th colspan="2">Action</th>
	</tr>
	</thead>
	<jsp:useBean id="meals" scope="request" type="java.util.List"/>
	<jsp:useBean id="formatter" scope="request" type="java.time.format.DateTimeFormatter"/>

	<c:forEach var="mealTo" items="${meals}">
		<tr class="${mealTo.excess ? "excess" : "noExcess"}">
			<td>${mealTo.dateTime.format(formatter)}</td>
			<td>${mealTo.description}</td>
			<td>${mealTo.calories}</td>
			<td>
				<a href="meals?action=delete&mealId=${mealTo.id}">delete</a>
			</td>
			<td>
				<a href="meals?action=edit&mealId=${mealTo.id}">edit</a>
			</td>
		</tr>
	</c:forEach>
</table>
<p><a href="meals?action=insert">Add Meal</a></p>
</body>
</html>
