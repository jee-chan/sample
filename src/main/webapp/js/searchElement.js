// submitボタンを押下した際のイベント
//送信に必要な要素の宣言
var page = 1;
var name = '';
var bigCategory = '';
var middleCategory = '';
var smallCategory = '';
var brand = '';
var searchBoolean =false;

function isSearch(){
	searchBoolean = true;
}
$(function(){
	
	
	//クリック時のイベント
	$('#forms').on('click','#searchbutton', function()
	/*document.getElementById('searchbutton').onclick = function() */{
		//outputDataを空に初期化
		$("#item-tableBody").text("");
		
		
		//フラグをtrueにして
		isSearch();
		searchBoolean = true;
		//ページ情報もリセット
		page = 1;
		

		console.log('検索時に取得したページ番号'+page);
		
		//フォームの値を取得
		//name = document.getElementById('searchform').getElementById('name').value;
		name = document.forms.searchForm.name.value;
		console.log('検索ワード ===> ' + name);
		
		// bigCategoryNum = document.getElementById('searchform').getElementById('bigCategory').selectedIndex;
		// bigCategory =document.getElementById('searchform').getElementById('name').options[searchWordNum].value;
		bigCategory = document.getElementById('bigCategory').value;
		console.log('大カテゴリ ===> ' + bigCategory);
		
		// middleCategoryNum = document.getElementById('searchform').getElementById('middleCategory').selectedIndex;
		// middleCategory = document.getElementById('searchform').getElementById('name').options[searchWordNum].value;
		middleCategory = document.getElementById('middleCategory').value
		console.log('中カテゴリ ===> ' + middleCategory);
		
		// smallCategoryNum = document.getElementById('searchform').getElementById('smallCategory').selectedIndex;
		// smallCategory = document.getElementById('searchform').getElementById('smallCategory').options[smallCategoryNum].value;
		smallCategory = document.getElementById('smallCategory').value
		console.log('小カテゴリ ===> ' + smallCategory);
		
		//brand =document.getElementById('searchform').getElementById('brand').value;
		brand = document.forms.searchForm.brand.value;
		console.log('ブランド ===> ' + brand);
		
		console.log('検索ワードが入力されました');
		console.log('入力されたnameは　＝＝＞'+name);
		console.log('入力されたbigCategoryは　＝＝＞'+bigCategory);
		console.log('入力されたmiddleCategoryは　＝＝＞'+middleCategory);
		console.log('入力されたsmallCategoryは　＝＝＞'+smallCategory);
		console.log('入力されたbrandは　＝＝＞'+brand);
		
		
		$.ajax({
			type		: "GET",
			url			: "getItemList",
			datatype	: "json",
	data		: {	page : page,
					name : name,
					bigCategory : bigCategory,
					middleCategory : middleCategory,
					smallCategory : smallCategory,
					brand : brand
					},
			success		: function(data){
						searchSuccess(data);
						},
			error		: function(XMLHttpRequest, textStatus, errorThrown){
							searchError(XMLHttpRequest, textStatus, errorThrown);
						}
		});				
	});
});

//Ajax通信成功処理
//dataにはコントローラから返された配列が格納されている
function searchSuccess(data){
	console.log('検索を実行しました');
	
	//配列の最大数まで回してテーブルの要素を生成する
	for (var cnt = 0; cnt < data.length; cnt++){
		$("#item-tableBody").append('<tr>'
				+'<td class="item-name"><a href="/Mercari/itemdetail/?id='+data[cnt].id+'">'+data[cnt].name+'</a></td>'
				//						+'<td>'+data[cnt].name+'</td>'
				+'<td class="item-price">'+data[cnt].price+'</td>'
				//						+'<td>'+data[cnt].price+'</td>'
				+'<td class="item-category">'
				//						+'<td>'
				+'<a href="">'+data[cnt].bigCategory+'</a>'
				+'<c:if test='+data[cnt].cut+'> /</c:if>'
				+'<a href="">'+data[cnt].middleCategory+'</a>'		
				+'<c:if test='+data[cnt].cut+'> /</c:if>'		
				+'<a href="">'+data[cnt].smallCategory+'</a>'		
				+'</td>'		
				+'<td class="item-brand"><a href="">'+data[cnt].brand+'</a></td>'		
				//						+'<td><a href="">'+data[cnt].brand+'</a></td>'		
				+'<td class="item-condition">'+data[cnt].condition+'</td>'		
				//						+'<td>'+data[cnt].condition+'</td>'		
				+'</tr>'
				)
	
	}
}

//Ajax通信失敗処理
 function searchError(XMLHttpRequest, textStatus, errorThrown){
	console.log('err');
		alert("error" + XMLHttpRequest);
		alert("status" + textStatus);
		alert("errorThrown" + errorThrown);
}
