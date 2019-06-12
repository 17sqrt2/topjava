package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoMockImpl;
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
	private final DateTimeFormatter FORMATTER_USER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
	private final DateTimeFormatter FORMATTER_DATA = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
	private static String INSERT_OR_EDIT = "/mealEditor.jsp";
	private static String LIST_MEAL = "/meals.jsp";
	private MealDao mealDao;

	public MealServlet() {
		super();
		log.debug(getClass().getSimpleName() + " created");
		mealDao = new MealDaoMockImpl();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("now in " + new Object(){}.getClass().getEnclosingMethod().getName());
		request.setCharacterEncoding("UTF-8");

		String forward;
		String action = request.getParameter("action");
		if (action == null || action.isEmpty())
			action = "listMeal";
		Meal meal = new Meal();
		int mealId;

		switch (action.toLowerCase()) {
			case "delete" :
				mealId = Integer.parseInt(request.getParameter("mealId"));
				mealDao.delete(mealId);
				response.sendRedirect("meals");
				return;
			case "edit" :
				mealId = Integer.parseInt(request.getParameter("mealId"));
				meal = mealDao.get(mealId);
			case "insert" :
				forward = INSERT_OR_EDIT;
				request.setAttribute("meal", meal);
				request.setAttribute("formatter", FORMATTER_DATA);
				break;
			default :
				forward = LIST_MEAL;
				request.setAttribute("meals", MealsUtil.getAllWithExcess(mealDao.getAll(), MealsUtil.getCaloriesLimit()));
				request.setAttribute("formatter", FORMATTER_USER);
				break;
		}

		request.getRequestDispatcher(forward).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("now in " + new Object(){}.getClass().getEnclosingMethod().getName());
		request.setCharacterEncoding("UTF-8");

		String idStr = request.getParameter("id");
		String dateTimeStr = request.getParameter("dateTime");
		String description = request.getParameter("description");
		int calories = Integer.parseInt(request.getParameter("calories"));
		int id = 0;
		Meal meal = new Meal(id, LocalDateTime.parse(dateTimeStr, FORMATTER_DATA), description, calories);

		if (idStr == null || idStr.isEmpty() || idStr.equalsIgnoreCase("0")) {
			mealDao.add(meal);
		} else {
			id = Integer.parseInt(idStr);
			meal.setId(id);
			mealDao.update(id, meal);
		}

		request.setAttribute("meals", MealsUtil.getAllWithExcess(mealDao.getAll(), MealsUtil.getCaloriesLimit()));
		request.setAttribute("formatter", FORMATTER_USER);
		request.getRequestDispatcher(LIST_MEAL).forward(request, response);
	}
}
