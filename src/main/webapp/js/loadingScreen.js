//ロード画面の表示処理
function displayLoading(msg){
    //表示メッセージの定義と表示
    //メッセージの引数がない場合空文字を挿入
    if(msg == undefined){
        msg = '';
    }
    
    var displayMessage = "<div class='loadingMsg'>" + msg + "</div>";
    
    if($('#loading').length == 0){
        $('body').append("<div id='loading'>" + displayMessage + "</div>");
    }
}

//ロード画面の削除処理
function removeLoading(){
    $('#loading').remove();
}
