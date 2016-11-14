package models;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class dbinterface {

	private Connection con;
	private Statement stmt;

	private String url = "jdbc:mysql://kc-sce-appdb01.kc.umkc.edu/jgkp9?allowMultiQueries=true";
	private String userID = "jgkp9";
	private String password = "java";

	public dbinterface() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (java.lang.ClassNotFoundException e) {
			System.out.println(e);
			System.exit(0);
		}

		open();
		try {
			DatabaseMetaData dbm = con.getMetaData();
			// check if "employee" table is there
			ResultSet tables = dbm.getTables(null, null, "Questions", null);
			if (!tables.next()) {
				// Table doesn't exist
				createTables();
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		cleanup();

	}

	private void open() {
		try {
			con = DriverManager.getConnection(url, userID, password);
			stmt = con.createStatement();

		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	private void cleanup() {
		try {
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Clean-up function failed, try again.");
		}
	}

	public ArrayList<Answer> getAnswers(int qId) {
		ArrayList<Answer> answers = new ArrayList<Answer>();
		open();
		try {
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM Answers WHERE question_id = "
							+ qId);
			while (rs.next()) {
				int answer_id = rs.getInt("answer_id");
				int question_id = rs.getInt("question_id");
				String answer = rs.getString("answer");
				answers.add(new Answer(answer_id, question_id, answer));
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		cleanup();
		return answers;
	}

	public ArrayList<Question> getQuestions() {
		ArrayList<Question> questions = new ArrayList<Question>();
		open();
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM Questions");
			while (rs.next()) {
				int question_id = rs.getInt("question_id");
				String question = rs.getString("question");
				questions.add(new Question(question_id, question));
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		cleanup();
		return questions;
	}

	public void addAnswer(String answer, int qId) {
		open();
		try {
			stmt.executeUpdate("INSERT INTO Answers ('question_id', 'answer') VALUES('"
					+ qId + "', '" + answer + "')");
		} catch (SQLException e) {
			System.out.println(e);
		}
		cleanup();
	}

	public void addQuestion(String question) {
		open();
		try {
			stmt.executeUpdate("INSERT INTO Questions ('question') VALUES('"
					+ question + "')");
		} catch (SQLException e) {
			System.out.println(e);
		}
		cleanup();
	}

	public String getQuestion(int qId) {
		String question;
		open();
		try {
			ResultSet rs = stmt
					.executeQuery("SELECT question FROM Questions WHERE question_id = "
							+ qId);
			rs.next();
			question = rs.getString("question");
			cleanup();
			return question;
		} catch (SQLException e) {
			System.out.println(e);
		}
		cleanup();
		return "BadAccess";
	}

	private void createTables() {
		String makeQuestionsString = "create table Questions"
				+ "(question_id INT NOT NULL AUTO_INCREMENT,"
				+ "question VARCHAR(264), PRIMARY KEY (question_id))";
		String makeAnswersString = "create table Answers (answer_id INT NOT NULL"
				+ " AUTO_INCREMENT, question_id INT NOT NULL,"
				+ "answer VARCHAR(264), PRIMARY KEY (answer_id))";

		open();

		try {
			stmt.executeUpdate(makeQuestionsString);
			stmt.executeUpdate(makeAnswersString);
		} catch (SQLException e) {
			System.out.println("Tables not made.");
		}
		cleanup();

	}
}
