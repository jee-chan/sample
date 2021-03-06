<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/mercari.css">
  <!-- script -->
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
    integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

<title>ユーザー登録</title>
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
      	<p class="text-danger"><c:out value="${registerMessage}"/></p>
        <form:form modelAttribute="userForm" action="${pageContext.request.contextPath}/register/user" method="post">
          <div class="form-group">
            <label for="username">user name</label>
            <form:input class="form-control" id="userName" path="userName"/>
            <form:errors path="userName" cssStyle="color:red" />
          </div>
          <div class="form-group">
            <label for="mailAddress">MailAddress</label>
            <form:input class="form-control" id="mailAddress" path="mailAddress"/>
            <form:errors path="mailAddress" cssStyle="color:red" />
          </div>
          <div class="form-group">
            <label for="password">password</label>
            <form:password class="form-control" id="password" path="password"/>
            <form:errors path="password" cssStyle="color:red" />
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