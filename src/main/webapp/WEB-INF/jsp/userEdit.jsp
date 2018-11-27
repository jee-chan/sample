<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
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
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
    integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<title>User Edit</title>
</head>
<body>


  <!-- details -->
  <div class="container">
    <a type="button" class="btn btn-default" href="${pageContext.request.contextPath}/userdetail/?id=${user.id}"><i class="fa fa-reply"></i> back</a>
    <h2>UserInformation Edit</h2>
    <div id="details">
      <form:form id="edit_form" action="${pageContext.request.contextPath}/userEdit/edit" modelAttribute="userForm">
        <table class="table table-hover">
          <tbody>
            <tr>
              <th>ID</th>
              <td>
                <c:out value="${user.id}"/>
              </td>
            </tr>
            <tr>
              <th>name</th>
              <td><c:out value="${user.userName}"/></td>
              <td>
                <form:input path="userName" value="${user.userName}" size="47"/>
                <form:errors path="userName" cssStyle="color:red" element="div"/>
              </td>
            </tr>
            <tr>
              <th>e-mail</th>
              <td><c:out value="${user.mailAddress}"/></td>
              <td>
                <form:input path="mailAddress" value="${user.mailAddress}" size="47"/>
                <form:errors path="mailAddress" cssStyle="color:red" element="div"/>
              </td>
            </tr>
            <tr>
              <th>authority</th>
              <td>
                <c:if test="${user.authority == 0}">
                  <c:out value="User"/>
                </c:if>
                <c:if test="${user.authority == 1}">
                  <c:out value="Admin"/>
                </c:if>
              </td>
              <td>
                <select name="authority">
                  <option value="">---please select---</option>
                  <option value="0">user</option>
                  <option value="1">admin</option>
                </select>
                <form:errors path="authority" cssStyle="color:red" element="div"/>
              </td>
              </tr>
              <tr>
                <th>description</th>
                <td><c:out value="${user.description}"/></td>
                <td>
                  <textarea name="description" rows="5" cols="48"><c:out value="${user.description}"/></textarea>
                  <form:errors path="description" cssStyle="color:red" element="div"/>
                </td>
              </tr>
            </tbody>
          </table>
          <div id="button_center">
            <input class="btn btn-default" type="submit" value="submit">
          </div>

          <input type="hidden" name="id" value="${user.id}">
          <input type="hidden" name="password" value="FakePassword000">
        </form:form>
      <!-- <a type="button" class="btn btn-default" href=""><i class="fa fa-pencil-square-o"></i>&nbsp;edit</a> -->
    </div>
  </div>

</body>
</html>