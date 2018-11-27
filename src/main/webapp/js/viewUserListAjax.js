/*
* ユーザ一覧ページの初期表示を行うAjax
*/

// 要素を宣言
var page = 1;
var name = '';
var mailAddress = '';

$(start =  function(){
    //ページ処理
    if(page < 1){
        page = 1;
    }
    //変数を初期値にセット
    page = 1;
    name = '';
    mailAddress = '';

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
        },
        //失敗処理
        function(){
            console.log("error");
            alert('何らかのエラーが発生しました');
        }
    )
})

$('#resetbutton').on('click', function(){
    start();
})