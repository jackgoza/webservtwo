<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="java.util.ArrayList" %>
<%@ page import="models.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Questions</title>
</head>
<body>

<form method='post' action='/'>
<h1>Questions</h1>
<ol>
<%

ArrayList<Question> questions = (ArrayList<Question>) request.getAttribute("questions");


for(Question i: questions){
	out.println("<li><a href='/answers?id'" + i.question_id + "'>" + i.question + "</a></li>");
}

%>


</ol>
<input type='text' name='newQ'><button type='submit'>Add Question</button>
</form>

</body>
</html>