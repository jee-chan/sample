//通知の許可を求める
Push.Permission.request();


//Ajax処理。クリック時にファイルの生成を行う
$(function(){
    $('#getItemList').on('click',function(){
        
        var win;
        console.log('ロード開始');
        
        //通知機能で使用するウィンドウオブジェクトの生成
        
        
        //ボタンを無効化する
        $("#getItemList").attr('disabled', 'disabled');
        
        //Ajax処理実行前にロード画面を入れる
        //TODO: loadingScreen.jsを参照のこと
        displayLoading('ファイルの生成中\n少々お待ちください...');
        
        $.ajax({
            type : 'get',
            url : 'csvCreate',
            datatype : 'json',
            //ロード画面を挟むと画面内の操作ができなくなるためタイムアウトを設定して制御しています
            //現環境ではファイルの生成に十数秒かかっているので暫定でタイムアウトは30秒としています（タイムアウトになるとエラーの処理に移ります）
            timeout : 30000
        })
        .then(
            //成功時処理
            function(data){
                //dataはファイルのパス（一部）をもらってきている
                var path = data[0];
                $('#dl_main').append(
                    '<a href="http://localhost:8080/Mercari/itemListdownload/csvDownload?path='+path+'" id="dl_button" class="part_line_dl"><i class="fa fa-caret-right"></i> ファイルダウンロード</a><br>'
                    );
                    
                    //完了通知
                    Push.create('success!',{
                        body: 'CSVファイルの作成が完了しました！',
                        timeout : 5000, //5秒で消します
                        //通知のクリック時処理
                        onClick : function(){
                            if(win.closed){
                                win = window.open('http://www.google.co.jp/','sample');
                            }
                            else{
                                win.focus();
                            }
                            this.close();
                        }
                    });
                    
                },
                //失敗時処理
                function(){
                    console.log('sorry, failed...');
                    alert('処理に失敗しました。管理者に問い合わせるかもう一度お試しください');
                })
                //成功時も失敗時も行う処理
                .always(
                    function(data){
                        //ロード画面削除処理.
                        console.log('ロード画面削除');
                        //TODO: loadingScreen.jsを参照のこと
                        removeLoading();
                        
                        //閉じるボタンの生成
                        $('#closeButton').append(
                            '<a href="javascript:void(0)" class="part_line_cl" onClick=window.close();><i class="fa fa-caret-right"></i>ウィンドウを閉じる</a><br>'
                            );
                        });
                        
                    });
                });

