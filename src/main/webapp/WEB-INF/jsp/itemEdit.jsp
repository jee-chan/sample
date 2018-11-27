<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="./header.jsp" %>
<!DOCTYPE html>
<html lang="ja">
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

<head>
<meta charset="UTF-8">
<title>商品編集</title>
</head>
<body>


  <!-- details -->
  <div id="input-main" class="container">
    <a type="button" class="btn btn-default" href="${pageContext.request.contextPath}/itemdetail/?id=${item.id}"><i class="fa fa-reply"></i> back</a>
    <h2>Edit</h2>

    <!-- edit form -->
    <form:form id="itemEditForm" name="itemEditForm" modelAttribute="itemForm" action="${pageContext.request.contextPath}/editItem/edit" method="POST" enctype="multipart/form-data" class="form-horizontal">
        <!-- name -->
        <div class="form-group">
          <label for="inputName" class="col-sm-2 control-label">name</label>
          <div class="col-sm-8">
<%--             <input type="text" class="form-control" id="inputName" name="name" value="${item.name}"/> --%>
            <form:input path="name" value="${item.name}" class="form-control" id="inputName"/>
            <form:errors path="name" cssStyle="color:red"/>
          </div>
        </div>
        <!-- price -->
        <div class="form-group">
          <label for="price" class="col-sm-2 control-label">price</label>
          <div class="col-sm-8">
            <form:input class="form-control" id="price" path="price" value="${itemIntPrice}"/>
            <form:errors path="price" cssStyle="color:red"/>
          </div>
        </div>
        <!-- category -->
        <div class="form-group">
          <label for="category" class="col-sm-2 control-label">category</label>
          <div class="col-sm-8">
            <select class="form-control" name="bigCategory">
              <option selected><c:out value="${item.bigCategory}"/></option>
              <c:forEach var="bigCategory" items="${bigCategoryList}">
              	<option><c:out value="${bigCategory}"/></option>              
              </c:forEach>
            </select>
              <form:errors path="bigCategory" cssStyle="color:red"/>
          </div>
        </div>
        <div class="form-group">
          <label for="category" class="col-sm-2 control-label"></label>
          <div class="col-sm-8">
            <select class="form-control" name="middleCategory">
              <option selected><c:out value="${item.middleCategory}"/></option>
              <c:forEach var="middleCategory" items="${middleCategoryList}">
              	<option><c:out value="${middleCategory}"/></option>              
              </c:forEach>
            </select>
              <form:errors path="middleCategory" cssStyle="color:red"/>
          </div>
        </div>
        <div class="form-group">
          <label for="category" class="col-sm-2 control-label"></label>
          <div class="col-sm-8">
            <select class="form-control" name="smallCategory">
              <option selected><c:out value="${item.smallCategory}"/></option>
			  <c:forEach var="smallCategory" items="${smallCategoryList}">
              	<option><c:out value="${smallCategory}"/></option>              
              </c:forEach>
            </select>
            <form:errors path="smallCategory" cssStyle="color:red"/>
          </div>
        </div>
        <!-- brand -->
        <div class="form-group">
          <label for="brand" class="col-sm-2 control-label">brand</label>
          <div class="col-sm-8">
            <form:input id="brand" class="form-control" path="brand" value="${item.brand}"/>
            <form:errors path="brand" cssStyle="color:red"/>
          </div>
        </div>
        <!-- condition -->
        <div class="form-group">
          <label for="condition" class="col-sm-2 control-label">condition</label>
          <div class="col-sm-8">
            <label for="condition1" class="radio-inline">
              <input type="radio" name="condition" id="condition1" value="1"/> good
            </label>
            <label for="condition2" class="radio-inline">
              <input type="radio" name="condition" id="condition2" value="2"/> nomal
            </label>
            <label for="condition3" class="radio-inline">
              <input type="radio" name="condition" id="condition3" value="3"/> bad
            </label>
            <form:errors path="condition" cssStyle="color:red"/>
          </div>
        </div>
        <!-- description -->
        <div class="form-group">
          <label for="description" class="col-sm-2 control-label">description</label>
          <div class="col-sm-8">
            <!-- <form:textarea path="description" id="description" class="form-control" value="${descriptionValue}" rows="5"/> -->
            <textarea name="description" id="description" class="form-control" rows="5"><c:if test="${descriptionValue == null}"><c:out value="${item.description}"/></c:if><c:out value="${descriptionValue}"/></textarea>
          <form:errors path="description" cssStyle="color:red"/>
          </div>
        </div>
        <!-- picture -->
        <div class="form-group">
          <label for="picture" class="col-sm-2 control-label">picture</label>
          <div class="col-sm-8">
            <input name="picture" type="file" id="picture" class="form-control" accept=".jpg,.gif,.png,image/gif,image/jpeg,image/png"/>
            <p class="fileTypeMassage">ファイルサイズ上限は3MB、形式はjpg(jpeg),gif,pngのみ対応しています。(png推奨)</p>
          </div>
        </div>
        
        <!-- salePriceOtionButton -->
        <div class="form-group">
          <label for="saleOptionButton" class="col-sm-2 control-label">sale option</label>
          <div class="col-sm-8">
            <input type="radio" id="saleOptionButton" name="saleOptionButton" value="off" onclick="addSalePriceForm()" checked="checked">セール価格を設定しない
            <input type="radio" id="saleOptionButton" name="saleOptionButton" value="on" onclick="addSalePriceForm()" >セール価格を設定する
          </div>
          
        </div>
        <!-- salePrice -->
        <div id="saleStartForm" class="form-group" style="display: none">
          <label for="saleStartDate" class="col-sm-2 control-label">Startdatetime</label>
          <div class="col-sm-8">
            <!-- 開始の期日設定 -->
            <input type="date" name="saleStartDate" id="saleStartDate" class="form-control" min=""  required="required"/>
            <input type="time" name="saleStartTime" id="saleStartTime" min="" required="required">
          </div>
        </div>
        <div id="saleEndForm" class="form-group"  style="display: none">
          <label for="saleEndDate" class="col-sm-2 control-label">Enddatetime</label>
          <div class="col-sm-8">
            <!-- 終了の期日設定 -->
            <input type="date" name="saleEndDate" id="saleEndDate" class="form-control" min="" required="required"/>
            <input type="time" name="saleEndTime" id="saleEndTime" min="できるならばstartTimeのonclickイベントで拾ってきて設定する(Ajaxの追記内に仕込むか？)" required="required">
            <br>
            <p id="saleOptionMassage"  style="display: none">※開始時間は終了時間より前に設定して下さい</p>
            <br>
          </div>
        </div>

        <!-- 価格の設定 -->
        <div id="salePriceForm" class="form-group"  style="display: none">
          <label for="salePrice" class="col-sm-2 control-label">salePrice</label>
          <div class="col-sm-8">
            <!-- 価格の設定 -->
            <input type="text" name="salePrice" id="salePrice" class="form-control" required="required"/>
          </div>
        </div>
        <!-- submit button -->
        <div class="form-group">
          <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-default">Submit</button>
          </div>
        </div>
        <input type="hidden" name="id" value="${item.id}">
      </form:form>
    </div>

    <script src="${pageContext.request.contextPath}/js/itemEdit.js" type="text/javascript"></script>
</body>
</html>