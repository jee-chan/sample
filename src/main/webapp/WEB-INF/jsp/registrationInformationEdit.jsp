<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>ユーザ情報編集</title>
</head>
<body>
  <!-- navbar -->
  <nav class="navbar navbar-inverse">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/main/">Rakus Items</a>
  </nav>

  <!-- register form -->
  <div id="login" class="container">
    <div class="panel panel-default">
      <div class="panel-heading">Register Account</div>
      <div class="panel-body">
        <form:form modelAttribute="loginUserForm" action="${pageContext.request.contextPath}/register/user" method="post">
          <div class="form-group">
            <label for="username">user name</label>
            <input type="text" class="form-control" id="username" name="username">
          </div>
          <div class="form-group">
            <label for="password">password</label>
            <input type="password" class="form-control" id="password" name="password">
          </div>
          <div class="form-group">
          	<label for="authority">authority</label><br>
          	<select id="authority" name="authority">
          		<option value="0">user</option>
          		<option value="1">admin</option>
          	</select>
          </div>
          <button type="submit" class="btn btn-default">Submit</button>
        </form:form>
      </div>
    </div>
    <div>
      <a type="button" class="btn btn-default" href="${pageContext.request.contextPath}/userLogin/"><i class="fa fa-reply"></i>&nbsp;Login page</a>
    </div>
</body>
</html>