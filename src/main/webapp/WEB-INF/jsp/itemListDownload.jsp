<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<!-- script -->
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/push.js/1.0.7/push.min.js"></script>
<!-- css -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

<title>ItemList Download</title>
</head>
<body>
<div class="centering_cr">
    <button type="button" id="getItemList" class="btn btn-success btn-lg" >CSVファイルの生成</button><br>
</div>

<div id="downloadButton" class="centering">
    <div id="dl_main">
        <!-- ダウンロードボタンがAjaxでこの中に生成されます -->
    </div>
</div>

<div id="closeButton" class="centering">
    <!-- 閉じるボタンがAjaxでこの中に生成されます -->
</div>

 
<!-- jqueryの読み込み -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<!-- PopperのJS読み込み -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<!-- bootstrapの読み込み -->
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
<!-- スクリプトの読み込み -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/loadingScreen.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/itemListCsvAjax.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/buttonControll.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/downloadButton.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/loadingDisplay.css">
</body>
</html>