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

<title>User Login</title>
</head>
<body>
  <!-- navbar -->
  <nav class="navbar navbar-inverse">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/main/">Rakus Items</a>
  </nav>

  <!-- login form -->
  <div id="login" class="container">
    <div class="panel panel-default">
      <div class="panel-heading">Login</div>
      <div class="panel-body">
        <p class="text-danger"><c:out value="${registerMessage}"/></p>
        <form:form modelAttribute="loginUserForm" action="${pageContext.request.contextPath}/login/">
          <div class="form-group">
            <label for="mail">user name</label>
            <form:input class="form-control" id="name" path="username"/>
          </div>
          <div class="form-group">
            <label for="password">password</label>
            <form:password class="form-control" id="password" path="password"/>
          </div>
          <button type="submit" class="btn btn-default">Login</button>
        </form:form>
      </div>
    </div>
    <div>
      <a type="button" class="btn btn-default" href="${pageContext.request.contextPath}/AdminUserLogin/"><i class="fa fa-user-plus"></i>&nbsp;Admin Login</a>
    </div>
    <br>
  </div>

</body>
</html>