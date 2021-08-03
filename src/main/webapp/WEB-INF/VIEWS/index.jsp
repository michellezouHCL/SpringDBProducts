<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="th" uri="http://www.thymeleaf.org" %>
<!DOCTYPE html>
<meta charset="utf-8"/>
<title>Product List</title>
</head>
<body>
<div align="center">
	<h3 th:inline="text">Welcome [[${#httpServletRequest.remoteUser}]]</h3>
	<form th:action="@{/logout}" method="post">
		<input type="submit" value="Logout" />
	</form>
    <h1>Product List</h1>
    <a href="/new" sec:authorize="hasRole('ROLE_ADMIN')">Create New Product</a>
    <br/><br/>
    <table border="1" cellpadding="10">
        <thead>
            <tr>
                <th>Product ID</th>
                <th>Name</th>
                <th>Price</th>
                <th>Instock#</th>
                <th sec:authorize="hasRole('ROLE_ADMIN')">Actions</th>
                <th sec:authorize="hasRole('ROLE_USER')">Quantity</th>		
                <th sec:authorize="hasRole('ROLE_USER')">Total</th>
                <th sec:authorize="hasRole('ROLE_USER')">Actions</th>
            </tr>
        </thead>
        <tbody>
            <tr th:each="product : ${listProducts}">
                <td th:text="${product.productId}">Product ID</td>
                <td th:text="${product.productName}">Name</td>
                <td th:text="${product.productPrice}">Price</td>
                <td th:text="${product.instockQty}">Instock#</td>
                <td sec:authorize="hasRole('ROLE_ADMIN')">
                    <a sec:authorize="hasRole('ROLE_ADMIN')" th:href="@{'/edit/' + ${product.productId}}">Edit</a>
                    &nbsp;&nbsp;&nbsp;
                    <a sec:authorize="hasRole('ROLE_ADMIN')" th:href="@{'/delete/' + ${product.productId}}">Delete</a>
                 </td>
                 <td sec:authorize="hasRole('ROLE_USER')" th:text="${product.productQty}">Quantity</td>
                 <td sec:authorize="hasRole('ROLE_USER')" th:text="${product.productTotal}">Total</td>
                 <td sec:authorize="hasRole('ROLE_USER')">
                 	<a sec:authorize="hasRole('ROLE_USER')" th:href="@{'/addOne/' + ${product.productId}}">&uarr;</a>
                    &nbsp;&nbsp;&nbsp;
                    <a sec:authorize="hasRole('ROLE_USER')" th:href="@{'/minusOne/' + ${product.productId}}">&darr;</a>
                    &nbsp;&nbsp;&nbsp;
                    <a sec:authorize="hasRole('ROLE_USER')" th:href="@{'/clear/' + ${product.productId}}">clear</a>
                 </td>
                 
            </tr>
        </tbody>
    </table>
</div>  
<div align="center" sec:authorize="hasRole('ROLE_USER')">
	<h4>Total Price:</h4>
	<h4> ######</h4>
</div> 
</body>
</html>