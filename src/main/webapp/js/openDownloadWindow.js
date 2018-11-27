//別ウィンドウでページを表示する
//ページ内のリンクをクリックしたときに動作する
document.getElementById('csvDownload').onclick = function openWindow(){
    //ウィンドウ表示の座標を定義
    var Width = 400;
    var Height = 300;
    var top =  window.innerHeight / 2;
    var left = window.innerWidth / 2;
    var x = top - (Width / 2);
    var y = left - (Height / 2);
    
    //表示処理
    window.open('http://localhost:8080/Mercari/itemListdownload/',
                null,
                'top='+x+
                ',left='+y+
                ', width='+Width+
                ', height='+Height
                )
}