<%-- 
    Document   : AdditionQuestion
    Created on : Mar 14, 2018, 2:31:31 PM
    Author     : Game
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Simply Addition Quiz::</h1>
        Score ${question.correct} / ${question.total-1} <br>
        <br>
        <form action="AdditionQuestion" method="post">
            What is ${question.x} + ${question.y} ? <input type="number" name="answer">
            <input type="submit">
        </form>
        <hr>
        <p style="color: ${message=="Correct!!!" ? "red" : "blue"}>${message}</p>
    </body>
</html>
