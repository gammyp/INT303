<%-- 
    Document   : ProductList
    Created on : Feb 14, 2018, 1:40:04 PM
    Author     : Game
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    </head>
    <body>
        <div class ="container">
            <div class = "row">
                <div class ="col-sm-1 col-lg-2"></div>
                <div class ="col-sm-10 col-lg-8">
                    <h1>Product List::</h1>
                    <form action="ProductList" method="post">
                        <table>
                            <tr>
                                <td>
                                    Search from Product name :
                                </td>
                                <td>
                                    <input type ="text" required="productName">
                                </td>
                                <td>
                                    <input type ="submit">
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
            </div>
            <hr>
            <div class ="container">
                <div class = "row">
                    <div class ="col-sm-1 col-lg-2"></div>
                    <div class ="col-sm-10 col-lg-8"></div>
                    <table class="table-striped table-bordered">
                        <thead>
                        <th>No</th>
                        <th>Product Name</th>
                        <th>Product Scale</th>
                        <th>Product Line</th>
                        <th>Price</th>
                        </thead>
                        <tbody>
                            <c:forEach items="${products}" var="p" varStatus="vs">
                                <tr style="padding-right: 20px">
                                    <td>${vs.count}</td>
                                    <td><a href="ProductManager?productCode=${p.productcode}">${p.productname}</td>
                                    <td style="text-align: center">${p.productscale}</td>
                                    <td>${p.productline.productline}</td>
                                    <td style="text-align: right">${p.msrp}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
    </body>
</html>
