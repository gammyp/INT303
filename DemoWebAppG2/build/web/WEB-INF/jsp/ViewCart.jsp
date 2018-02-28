<%-- 
    Document   : ViewCart
    Created on : Feb 28, 2018, 2:42:29 PM
    Author     : Game
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    </head>
    <body>
        <h3>Your Cart::</h3>
        <hr>
        <div class = "container">
            <div class="row">
                <div class ="col-lg-1">
                    <table>
                        <thead>
                        <th>No</th>
                        <th>Photos</th>
                        <th>Detail</th>
                        <th>UnitPrice</th>
                        <th>Quantity</th>
                        <th>Total</th>
                        </thead>
                        <tbody>
                            <c:forEach items="${sessionScope.cart.orders}" var="od" varStatus="vs">
                                    <c:set var="p" value="${od.product}"/>
                                    <c:set var="cutPos" value="${fn:indexOf(p.productline.productline, ' ')}"/>
                                    <c:set var="path" value="${cutPos > 0 ? fn:substring(p.productline.productline, 0, cutPos) : p.productline.productline}"/>
                                    <c:set var="imgFile" value="model-images/${fn:toLowerCase(path)}/${p.productcode}.jpg"/>
                                    <tr>
                                        <td><img src="${imgFile}" width="100"></td>
                                        <td>${p.productname}</td>
                                        <td>${p.msrp}</td>
                                        <td><input type="number" value="${od.quantityordered}" name="${p.productcode}"></td>
                                        <td>${od.quantityordered*p.msrp}</td>
                                    </tr>
                            </c:forEach>
                    </table>
                </div>
                <div class="col-lg-10 col-sm-10">
                    <div class ="container">
                        </div>
                        <div class="row">
                            <div class="col-lg-10"></div>
                            <div class="col-lg-2"></div>
                        </div>
                    </div>
                </div>
                <div class = "col-lg-1 col-sm-1">

                </div>
            </div>
        </div>
    </body>
</html>
