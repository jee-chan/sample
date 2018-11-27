/*
* ページの表示を行うAjax
*/


//送信に必要な要素の宣言
var page = 1;
var name = '';
var bigCategory = '';
var middleCategory = '';
var smallCategory = '';
var brand = '';
var searchBoolean =false;
$(function(){
	console.log('表示を開始しています');
	console.log('入力された名前　＝＞'+name+'です');
	console.log('入力された親カテゴリ　＝＞'+bigCategory+'です');
	console.log('入力された子カテゴリ　＝＞'+middleCategory+'です');
	console.log('入力された孫カテゴリ　＝＞'+smallCategory+'です');
	console.log('入力されたブランド　＝＞'+brand+'です');
	console.log('今のフラグ　＝＞'+searchBoolean+'です');

		if(searchBoolean==true){
			console.log("検索フラグがtrueになったので表示を実行します")
			return
		}

		//outputDataを空に初期化
		$("#item-tableBody").text("");
		$.ajax({
			type		: "GET",
			url			: "getItemList",
			data		: {	page : page,
							name : name,
							bigCategory : bigCategory,
							middleCategory : middleCategory,
							smallCategory : smallCategory,
							brand : brand
							},
			datatype	: "json",
			success		: function(data){
							viewSuccess(data);
						},
			error		: function(XMLHttpRequest, textStatus, errorThrown){
							viewError(XMLHttpRequest, textStatus, errorThrown);
						}
		});
});
//Ajax通信成功処理
//dataにはコントローラから返された配列が格納されている
function viewSuccess(data){

	//配列の最大数まで回してテーブルの要素を生成する
	for (var cnt = 0; cnt < data.length; cnt++){

		var price = data[cnt].price;
		if(data[cnt].saleFlug == true){
			price = '<span class="saleMessage">'+data[cnt].salePrice+"</span>";
		}

		$("#item-tableBody").append('<tr>'
				+'<td class="item-name"><a href="/Mercari/itemdetail/?id='+data[cnt].id+'">'+data[cnt].name+'</a></td>'
				+'<td class="item-price"><span>$: '+price+'</span></td>'
				+'<td class="item-category">'
				+'<a href="">'+data[cnt].bigCategory+'</a>'
				+'<c:if test='+data[cnt].cut+'> /</c:if>'
				+'<a href="">'+data[cnt].middleCategory+'</a>'		
				+'<c:if test='+data[cnt].cut+'> /</c:if>'		
				+'<a href="">'+data[cnt].smallCategory+'</a>'		
				+'</td>'		
				+'<td class="item-brand"><a href="">'+data[cnt].brand+'</a></td>'		
				+'<td class="item-condition">'+data[cnt].condition+'</td>'		
				+'</tr>'	
		)
						
	}
	
}

//Ajax通信失敗処理
 function viewError(XMLHttpRequest, textStatus, errorThrown){
	console.log('err');
		alert("error" + XMLHttpRequest);
		alert("status" + textStatus);
		alert("errorThrown" + errorThrown);
}
