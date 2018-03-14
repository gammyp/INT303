<%-- 
    Document   : ProductList
    Created on : Feb 14, 2018, 1:40:04 PM
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
                                    <input type ="text" required name="productName" value="${param.product}">
                                </td>
                                <td>
                                    <input type ="submit">
                                </td>
                                <td style="padding-left: 100; width:40%">
                                    <a href="ViewCart">
                                        <img src="cart.png" width="20">
                                    </a>
                                    (${cart.size})
                                </td>
                        </table>
                    </form>
                </div>
            </div>
            <hr>
            <div class="row">
            <div class="col-sm-1 col-lg-1"></div>
            <div class="col-sm-10 col-lg-10">
                <div class="container">
                    <div class="row">
                        <c:forEach items="${products}" var="p" varStatus="vs" >
                            <c:set var="cutPos" value="${fn:indexOf(p.productline.productline, ' ')}"/>
                            <c:set var="path" value="${cutPos > 0 ? fn:substring(p.productline.productline, 0, cutPos) : p.productline.productline}"/>
                            <c:set var="imgFile" value="model-images/${fn:toLowerCase(path)}/${p.productcode}.jpg"/>
                            <div class="col-md-3">
                                <div class="thumbnail">
                                    <a href="ProductManager?productCode=${p.productcode}">
                                        <img class="img-thumbnail"  src="${imgFile}" title="${p.productcode}">
                                        <div class="caption">
                                            <p>${p.productname}  | Scale:  ${p.productscale}  | Price: ${p.msrp} $US </p>
                                        </div>
                                    </a>
                                    <div>
                                        <a href="AddItemToCart?item=${p.productcode}">
                                        <img src ="cart.png" width="25">
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
            <div class="col-sm-1 col-lg-1"></div>
        </div>
<!--            <div class ="container">
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
            </div>-->
        </div>
    </body>
</html>
