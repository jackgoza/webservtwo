<%@ page import="models.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Questions</title>
    </head>

    <body>
        <form method='post' action='/answers'>
            <h1>Answers</h1>
            <h2><%= request.getAttribute("question")%></h2>
            <ol>
        <%
            Integer questionId = Integer.parseInt(request.getParameter("id"));
            ArrayList<Answer> answers = (ArrayList<Answer>) request.getAttribute("answers");
            for(Answer answer: answers)
                out.println("<li>" + answer.answer + "</li>");
            %>

            </ol>
            <input type='text' name='newAnswer'><button type='submit'>Add Answer</button>
            <input type="hidden" name="questionId" value="<%=questionId %>">
        </form>
    </body>
</html>