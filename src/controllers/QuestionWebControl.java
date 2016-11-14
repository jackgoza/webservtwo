package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Question;
import models.dbinterface;

@WebServlet("/")
public class QuestionWebControl extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ArrayList<Question> questions = new ArrayList<Question>();

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		System.out.println(request.getParameter("newQuestion"));

		dbinterface data = new dbinterface();
		data.addQuestion(request.getParameter("newQuestion"));

		response.sendRedirect("/");
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		dbinterface data = new dbinterface();
		questions = data.getQuestions();

		request.setAttribute("questions", questions);

		RequestDispatcher rd = request
				.getRequestDispatcher("/Views/QuestionsView.jsp");
		rd.forward(request, response);

	}

}
