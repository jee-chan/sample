<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="./header.jsp" %>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <!-- css -->
  <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" 
    integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous"/>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
    integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous"/>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
    integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous"/>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/mercari.css">
  <!-- script -->
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
 
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
    integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

<title>Item List</title>
</head>

  <div id="main" class="container-fluid">
    <!-- addItem link -->
    <c:if test="${adminaccount != null}" >
      <div id="addItemButton">
        <a class="btn btn-default" href="${pageContext.request.contextPath}/addItem/"><i class="fa fa-plus-square-o"></i> Add New Item</a>
        <a class="btn btn-default" href="${pageContext.request.contextPath}/register/"><i class="fa fa-plus-square-o"></i> Register Account</a>
      </div>
    </c:if>
    <div id="forms">
<!-- 	エラーメッセージの表示 -->
	<c:out value="${errorMessage}"/>
	
    <!-- 検索フォーム -->
<%--     action="${pageContext.request.contextPath}/itemSearch" --%>
<%--       <form  id="searchForm" class="form-inline" role="form" method="post"> --%>
      <form name="searchForm" id="searchForm" class="form-inline" role="form"  method="post">
        <div class="form-group">
<!--         商品名フォーム -->
          <input type="text" class="form-control" id="name" name="name" value="" placeholder="item name"/>
        </div>
        <div class="form-group"><i class="fa fa-plus"></i></div>
        <div class="form-group">
<!--         親カテゴリ -->
          <select class="form-control" id="bigCategory" name="bigCategory"　value="">
          <option value='' disabled selected style='display:none;'>parentCategory</option>
          <c:forEach items="${bigCategoryList}" var="list">
          	<option><c:out value="${list}"/></option>
          </c:forEach>
          </select>
<!--           子カテゴリ -->
          <select class="form-control" id="middleCategory" name="middleCategory" value="">
          <option value='' disabled selected style='display:none;'>ChildCategory</option>
          <c:forEach items="${middleCategoryList}" var="list">
          	<option><c:out value="${list}"/></option>
          </c:forEach>
          </select>
<!--           孫カテゴリ -->
          <select class="form-control" id="smallCategory" name="smallCategory"　value="">
          <option value='' disabled selected style='display:none;'>GrandChildCategory</option>
          <c:forEach items="${smallCategoryList}" var="list">
          	<option><c:out value="${list}"/></option>
          </c:forEach>
          </select>
        </div>
        <div class="form-group"><i class="fa fa-plus"></i></div>
        <div class="form-group">
<!--         ブランド -->
          <input type="text" id="brand" class="form-control" name="brand" value="" placeholder="brand"/>
        </div>
        <div class="form-group"></div>
        <a id='searchbutton' class="btn btn-default"><i class="fa fa-angle-double-right"></i> search</a>
      </form>
    </div>


    <!-- pagination -->
    <div class="pages" id="page">
      <nav class="page-nav">
        <ul class="pager">
<!--         前のページに戻る -->
          <li class="previous"><a class="PrevPage">&larr; prev</a></li>
<!--         次のページへすすむ -->
          <li class="next"><a class="NextPage">next &rarr;</a></li>
        </ul>
      </nav>
    </div>

    <!-- table -->
    <div class="table-responsive">
      <table id="item-table" class="table table-hover table-condensed">
        <thead>
          <tr>
            <th>name</th>
            <th>price</th>
            <th>category</th>
            <th>brand</th>
            <th>condition</th>
          </tr>
        </thead>
        <tbody id="item-tableBody">
<!--         ここにAjaxで生成されたテーブルが挿入されます -->
        </tbody>
      </table>
    </div>

    <!-- pagination -->
    <div class="pages">
      <nav class="page-nav">
        <ul class="pager">
<!--         前のページに戻る -->
          <li class="previous"><a class="PrevPage">&larr; prev</a></li>
<!--         次のページへすすむ -->
          <li class="next"><a class="NextPage">next &rarr;</a></li>
        </ul>
      </nav>
      </div>
      <!-- ページ番号を指定して表示するフォーム(未作成なのでフォームのみ) -->
      <!--
      <div id="select-page">
        <form class="form-inline">
          <div class="form-group">
            <div class="input-group col-xs-6">
              <label></label>
              <input type="text" class="form-control"/>
              -->
              <!-- 総ページ数 -->
              <!--
              <div class="input-group-addon">/ 20</div>
            </div>
            <div class="input-group col-xs-1">
              <button type="submit" class="btn btn-default">Go</button>
            </div>
          </div>
        </form>
      </div>
      -->
    </div>
  </div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/viewPageAjax.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/nextPagingAjax.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/previousPagingAjax.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/searchElement.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/openDownloadWindow.js"></script>
</body>
</html>