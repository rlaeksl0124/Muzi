<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
	<title>Product-Home</title>
<script>
    const page = 0;
    const limit = 10;
function getProducts(page, limit) {
fetch('/Muzi_project/product/list?page=' + encodeURIComponent(page) + '&limit=' + encodeURIComponent(limit))
    .then(response => response.json())
    .then(responseData => {
        console.log(responseData);
        const productList = document.getElementById('productList');

        // Clear the existing content
        productList.innerHTML = '';

        // Create the table and header row
        const table = document.createElement('table');
        table.className = 'product-table'; // Add a class for styling

        const headerRow = document.createElement('tr');
        const headers = ['상품 번호', '상품 이름', '상품 가격'];

        headers.forEach(headerText => {
            const th = document.createElement('th');
            th.textContent = headerText;
            headerRow.appendChild(th);
        });

        table.appendChild(headerRow);

        const products = responseData.dto || [];
        console.log(products);

        products.forEach(product => {
            const row = document.createElement('tr');

            // 상품 번호
            const productNumberCell = document.createElement('td');
            const numberLink = document.createElement('a');
            numberLink.href = '/Muzi_project/product/detail?productNumber=' + encodeURIComponent(product.productNumber);
            numberLink.textContent = product.productNumber;
            productNumberCell.appendChild(numberLink);

            // 상품 이름
            const nameLink = document.createElement('a');
            nameLink.href = '/Muzi_project/product/detail?productNumber=' + encodeURIComponent(product.productNumber);
            const productNameCell = document.createElement('td');
            nameLink.textContent = product.productName;
            productNameCell.appendChild(nameLink);

            // 상품 가격
            const priceLink= document.createElement('a');
            priceLink.href = '/Muzi_project/product/detail?productNumber=' + encodeURIComponent(product.productNumber);
            const productPriceCell = document.createElement('td');
            priceLink.textContent = product.productPrice;
            productPriceCell.appendChild(priceLink);


            row.appendChild(productNumberCell);
            row.appendChild(productNameCell);
            row.appendChild(productPriceCell);

            table.appendChild(row);
        });

        productList.appendChild(table);
    })
    .catch(error => console.error('Error fetching product list:', error));
}
getProducts(page, limit);
</script>
</head>
<body>

    <h1>Product List</h1>
    <ul id="productList"></ul>



</body>
</html>
