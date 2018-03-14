<%-- 
    Document   : ErrorHandler
    Created on : Mar 14, 2018, 3:46:24 PM
    Author     : Game
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page isErrorPage="true" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error</title>
    </head>
    <body>
        <h1>OOPS! something went worng!!</h1>
        <h4>
            Error code: <span style="color:red">${pageContext.errorData.statusCode}</span>
            <br>
            Error Message:
            <span style="color:red">${pageContext.errorData.statusCode==404?"Document not found" : pageContext.exception}</span>
        </h4>
    </body>
</html>
