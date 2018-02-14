<%-- 
    Document   : TestJSP
    Created on : Jan 17, 2018, 4:03:41 PM
    Author     : INT303
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Jsp Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <br>
        x = ${param.x}
        <br>
        y = ${param.y}
        <br>
        x + y = ${param.x + param.y}
    </body>
</html>
