<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<%--   <script type="text/javascript" src="${pageContext.request.contextPath}/js/main.js"></script> --%>
<title>Insert title here</title>
<
</head>
<body>
<h1>Hello World!</h1>

<P> The time on the server is ${serverTime}. </P>

<div id="ajax_data">
	<input type="button" id="ajax_btn" value="Ajax通信テスト" /><br />
	outputData:<span id="output_data"></span>
</div>

<script type="text/javascript">

$(function(){
	//Ajaxテスト　ボタンをクリックすると実行される動作
	//#ajax_btnはボタンに設置したidを呼び出している
	$("#ajax_btn").on('click', function(){
		//outputDataを空に初期化
		$("#outputData").text("");
		$.ajax({
			type		: "GET",
			url			: "/Mercari//getItemlList",
			datatype	: "json",
			data		: //ここに変数を入力
			success		: function(data){
							success(data);
						},
			error		: function(XMLHttpRequest, textStatus, errorThrown){
							error(XMLHttpRequest, textStatus, errorThrown);
						}
		});
	});
});

//Ajax通信成功処理
function success(data){
	console.log('suc');
	alart("success:" + data);
	
	$("#outputData").text("");
	for (var cnt = 0; cnt < data.length; cnt++){
		$("#output_data").append("data[" + cnt + "]" + data[cnt] +";");
	}
}

//Ajax通信失敗処理
 function error(XMLHttpRequest, textStatus, errorThrown){
	console.log('err');
		alert("error" + XMLHttpRequest);
		alert("status" + textStatus);
		alert("errorThrown" + errorThrown);
}

</script>
</body>
</html>