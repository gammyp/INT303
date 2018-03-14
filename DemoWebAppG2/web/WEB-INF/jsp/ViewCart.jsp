<%-- 
    Document   : ViewCart
    Created on : Feb 28, 2018, 2:42:29 PM
    Author     : INT303
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css" integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ" crossorigin="anonymous">
        <title>JSP Page</title>
    </head>
    <body>
        <br>
        <h3>Your  Cart ::</h3> <hr>
        <br>
        <div class="container">
            <div class="row">
                <div class="col-lg-1 col-sm-1"></div>
                <table class="table-striped">
                    <thead>
                    <th>No</th>
                    <th>Photo</th>
                    <th>Detail</th>
                    <th>Unit Price</th>
                    <th>Quantity</th>
                    <th>Total</th>
                    <th>Delete</th>
                    </thead>
                    <tbody>
                    <form action="UpdateCart" method="post">
                        <c:forEach items="${sessionScope.cart.orders}" var="od" varStatus="vs">
                            <c:set var="p" value="${od.product}"/>
                            <c:set var="cutPos" value="${fn:indexOf(p.productline.productline, ' ')}"/>
                            <c:set var="path" value="${cutPos > 0 ? fn:substring(p.productline.productline, 0, cutPos) : p.productline.productline}"/>
                            <c:set var="imgFile" value="model-images/${fn:toLowerCase(path)}/${p.productcode}.jpg"/>
                            <tr>
                                <td>${vs.count}</td>
                                <td><img src="${imgFile}" width="100"/></td>
                                <td>${p.productname}</td>
                                <td>${p.msrp}</td>
                                <td style="width: 15px"><input type="number" max="20" min="0" value="${od.quantityordered}" 
                                                               name="${p.productcode}" style="width: 50px"/></td>
                                <td style="padding-left: 50px">${od.quantityordered * p.msrp}</td>
                                <td><input type="checkbox" name="deleteItems" value="${p.productcode}"></td>
                            </tr>
                        </c:forEach>
                            <tr>
                                <td colspan="5" style="text-align: right">Total Price :: </td>
                                <td style="padding-left: 20px; padding-right: 30px; text-align: right"><fmt:formatNumber pattern="#,###.##" value="${cart.totalPrice}"></fmt:formatNumber></td>
                            </tr>
                            <tr><td colspan="6" style="text-align: right"><input type="submit" value="update"/></td></tr>
                    </form>
                    </tbody>
                </table>
            </div>
            <div class="col-lg-1 col-sm-1"></div>
        </div>
    </body>
</html>
