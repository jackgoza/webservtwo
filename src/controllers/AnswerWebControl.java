package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Answer;
import models.dbinterface;

@WebServlet("/answers")
public class AnswerWebControl extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ArrayList<Answer> answers = new ArrayList<Answer>();
	private String question;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String newAnswer = request.getParameter("newAnswer");
		Integer questionId = Integer.parseInt(request
				.getParameter("questionId"));

		dbinterface data = new dbinterface();
		data.addAnswer(newAnswer, questionId);

		response.sendRedirect("/answers?id=" + questionId);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		Integer questionId = Integer.parseInt(request.getParameter("id"));

		dbinterface data = new dbinterface();
		answers = data.getAnswers(questionId);
		question = data.getQuestion(questionId);

		request.setAttribute("answers", answers);
		request.setAttribute("question", question);

		RequestDispatcher rd = request
				.getRequestDispatcher("/Views/AnswersView.jsp");
		rd.forward(request, response);
	}
}
