/*
* 前のページに戻るときに用いる機能です
*/

//送信に必要な要素の宣言
// var pageNum = {page:1};
var page = 1;
var name = "";
var bigCategory = "";
var middleCategory = "";
var smallCategory = "";
var brand = "";
var searchBoolean =false;
$(function(){
	
	//#nextPageはボタンに設置したidを呼び出している
	$('.previous').on('click', function(){
		//ページ後退処理
		console.log('現在のページ番号は'+page);
		page = page-1;
		if(page < 1){
			page = 1;
		}
		console.log('前に遷移したときのページ番号'+page);
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
							prevSuccess(data);
						},
			error		: function(XMLHttpRequest, textStatus, errorThrown){
							prevError(XMLHttpRequest, textStatus, errorThrown);
						}
		});
	});
});
//Ajax通信成功処理
//dataにはコントローラから返された配列が格納されている
function prevSuccess(data){
	console.log('前のページに遷移しました');
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
 function prevError(XMLHttpRequest, textStatus, errorThrown){
	console.log('err');
		alert("error" + XMLHttpRequest);
		alert("status" + textStatus);
		alert("errorThrown" + errorThrown);
}
