// 要素を宣言
var page = 1;
var name = '';
var mailAddress = '';

$(function(){

	$('#searchbutton').on('click', function(){

        name = document.forms.searchform.searchName.value;
        mailAddress = document.forms.searchform.searchEmailAddress.value;
        console.log('取得した名前'+name);
        console.log('取得したアドレス'+mailAddress);
        //ページ番号処理
        if(page < 1){
            page = 1;
		}

        //outputDataを空に初期化
        $('#user-tableBody').text('');
        $.ajax({
            type        : 'GET',
            url         : 'listShow',
            data        : { page : page,
                            name : name,
                            mailAddress : mailAddress
                            },
            datatype    : 'json',
        })
        .then(
            //成功処理
            function(data){
                //権限の変数を宣言
                var authority;
                for(var cnt = 0; cnt < data.length; cnt++){
                    switch(data[cnt].authority){
                        case 0:
                            authority = "User";
                            break;
                        case 1:
                            authority = "Admin";
                            break;
                        default:
                            authority = "権限不明";
                    }
                    $('#user-tableBody').append(
                        '<tr>'
                        +'<td><a href="/Mercari/userdetail/?id='+data[cnt].id+'">'+data[cnt].userName+'</a></td>'
                        +'<td>'+data[cnt].mailAddress+'</td>'
                        +'<td>'+authority+'</td>'
                        +'</tr>'
                    )
                }
                //検索結果が15件以下だった場合にページ数を一つ戻す
                if(data.length != 15){
            		page = page-1
	            }
            },
            //失敗処理
            function(){
                console.log("error");
                alert('何らかのエラーが発生しました');
            }
        )
    })
})