<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
<%@ include file="./header.jsp" %>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <!-- css -->
  <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" 
    integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous"/>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
    integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous"/>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
    integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous"/>
   <!-- script -->
   <!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script> -->
   <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
   <link rel="stylesheet" href="${pageContext.request.contextPath}/lightbox2-master/src/css/lightbox.css">
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
  integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
  <title>商品詳細</title>
</head>
<body>

  <!-- details -->
  <div class="container">
    <a type="button" class="btn btn-default" href="${pageContext.request.contextPath}/main/"><i class="fa fa-reply"></i> back</a>
    <h2>Details</h2>
    <div id="details">
      <table class="table table-hover">
        <tbody>
          <tr>
            <th>ID</th>
            <td><c:out value="${item.id}"/></td>
          </tr>
          <tr>
            <th>name</th>
            <td><c:out value="${item.name}"/></td>
          </tr>
          <tr>
            <c:if test="${item.saleFlug == true}">
              <th>
                <p class="saleMessage">sale now!!</p>
              </th>
              <td>
                <p class="salePrice">$: <c:out value="${item.salePrice}"/></p>
              </td>
            </c:if>
            <c:if test="${item.saleFlug == false}">
              <th>price</th>
              <td><p>$: <c:out value="${item.price}"/></p></td>
            </c:if>
          </tr>
          <tr>
            <th>category</th>
            <td>
              <c:out value="${item.bigCategory}"/>
           	 	<c:if test="${item.cut}"> /</c:if> 
                <c:out value="${item.middleCategory}"/>
                <c:if test="${item.cut}"> /</c:if>
                <c:out value="${item.smallCategory}"/>
              </td>
            </tr>
            <tr>
              <th>brand</th>
              <td><c:out value="${item.brand}"/></td>
            </tr>
            <tr>
              <th>condition</th>
            <td><c:out value="${item.condition}"/></td>
          </tr>
          <tr>
            <th>description</th>
            <td><c:out value="${item.description}"/></td>
          </tr>
        </tr>
        <tr>
            <th>picture</th>
            <td><a href="${pageContext.request.contextPath}/image/itemPictures/${item.picture}" data-lightbox="itemImage" data-title="${item.name}"><img id="itemImage" src="${pageContext.request.contextPath}/image/itemPictures/${item.picture}"/></a></td>
          </tr>
        </tbody>
      </table>
      <a type="button" class="btn btn-default" href="${pageContext.request.contextPath}/editItem/?id=${item.id}"><i class="fa fa-pencil-square-o"></i>&nbsp;edit</a>
    </div>
  </div>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mercari.css">
  <script src="${pageContext.request.contextPath}/lightbox2-master/src/js/lightbox.js" type="text/javascript"></script>
</body>
</html>