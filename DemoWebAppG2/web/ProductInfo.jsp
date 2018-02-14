<%-- 
    Document   : ProductInfo
    Created on : Feb 7, 2018, 4:59:24 PM
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
        <h1>Product Information</h1><hr>
        <form action="ProductManager" method="post">Enter Product code to search:
            <input type="text" name="productCode" placeholder="Product code format X99_9999">
            <input type ="submit">
        </form>
        <p>${message}</p>
        <table>
            <tr>
                <td>Product Code</td>
                <td><input type="text" value="${product.productcode}" disabled></td>
            </tr>
            <tr>
                <td>Product Name</td>
                <td><input type="text" value="${product.productname}" disabled></td>
            </tr>
            <tr>
                <td>Product Description</td>
                <td><textarea rows="5">${product.productdescription}</textarea></td>
            </tr>
            <tr>
                <td>Price</td>
                <td><input type="text" value="${product.msrp}" disabled></td>
            </tr>
        </table>
    </body>
</html>
