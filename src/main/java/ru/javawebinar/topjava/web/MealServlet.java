package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
	private static final Logger log = getLogger(MealServlet.class);
	private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
	private static String INSERT_OR_EDIT = "/mealEditor.jsp";
	private static String LIST_MEAL = "/meals.jsp";
	private int caloriesPerDay = MealsUtil.getCaloriesLimit();
	private MealDao mealDao;

	public MealServlet() {
		super();
		mealDao = new MealDaoImpl();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String forward;
		String action = request.getParameter("action");
		if (action == null || action.isEmpty())
			action = "listMeal";
		Meal meal = new Meal();

		if (action.equalsIgnoreCase("delete")) {
			int mealId = Integer.parseInt(request.getParameter("mealId"));
			mealDao.delete(mealId);
			forward = LIST_MEAL;
		} else if (action.equalsIgnoreCase("edit")) {
			forward = INSERT_OR_EDIT;
			int mealId = Integer.parseInt(request.getParameter("mealId"));
			meal = mealDao.getOneById(mealId);
		} else if (action.equalsIgnoreCase("listMeal")) {
			forward = LIST_MEAL;
		} else {
			forward = INSERT_OR_EDIT;
		}

		request.setAttribute("meal", meal);
		request.setAttribute("meals", MealsUtil.getAllWithExcess(mealDao.getAll(), caloriesPerDay));
		request.setAttribute("formatter", FORMATTER);
		request.getRequestDispatcher(forward).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String idStr = request.getParameter("id");
		String dateTimeStr = request.getParameter("dateTime");
		String description = request.getParameter("description");
		int calories = Integer.parseInt(request.getParameter("calories"));
		int id = 0;
		Meal meal;

		if (idStr == null || idStr.isEmpty() || idStr.equalsIgnoreCase("0")) {
			meal = new Meal(id, LocalDateTime.parse(dateTimeStr, FORMATTER), description, calories);
			mealDao.add(meal);
		} else {
			id = Integer.parseInt(idStr);
			meal = new Meal(id, LocalDateTime.parse(dateTimeStr, FORMATTER), description, calories);
			mealDao.update(id, meal);
		}

		request.setAttribute("meal", meal);
		request.setAttribute("meals", MealsUtil.getAllWithExcess(mealDao.getAll(), caloriesPerDay));
		request.setAttribute("formatter", FORMATTER);
		request.getRequestDispatcher(LIST_MEAL).forward(request, response);
	}
}
