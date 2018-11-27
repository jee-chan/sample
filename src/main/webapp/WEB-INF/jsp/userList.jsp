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
<!-- このページ専用のcss -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/userList.css">
<!-- script -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>

<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
  integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<title>User List</title>
</head>
<body>

  <h2>User List</h2>
  <!-- 検索フォーム -->
  <div id="form" class="searchbox">
    <form id="searchform" action="">
      <p class="searchOption_text">Name:</p> <input type="text" id="searchName" name="name" value="">
      <p class="serchOption_text">Mail Address:</p> <input type="text"  id="searchEmailAddress" name="emailAddress" value="">
      <a id="searchbutton" class="btn btn-default"><i class="fa fa-angle-double-right"></i> search</a>
      <a id="resetbutton" class="btn btn-default"><i class="fa fa-angle-double-right"></i> reset</a>
    </form>
  </div>


  <div class="container">
  <!-- pagination -->
  <div class="pages" id="page">
    <nav class="page-nav">
      <ul class="pager">
        <!-- 前のページに戻る -->
        <li class="previous"><a class="PrevPage">&larr; prev</a></li>
        <!--         次のページへすすむ -->
        <li class="next"><a class="NextPage">next &rarr;</a></li>
      </ul>
    </nav>
  </div>
  
    <!-- table -->
    <div class="table-responsive">
      <table id="user-table" class="table table-hover table-condensed">
        <thead>
          <tr>
            <th>name</th>
            <th>email</th>
              <th>authority</th>
            </tr>
          </thead>
          <tbody id="user-tableBody">
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
    </div>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/viewUserListAjax.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/nextUserList.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/previousUserList.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/userSearchAjax.js"></script>
</body>
</html>