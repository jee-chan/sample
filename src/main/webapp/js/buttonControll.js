$(function(){
    $('#dl_main').click(function(){
        $('#dl_main').text("");
        $('#dl_main').append('<a href="javascript:void(0)" id="dl_button" class="part_line_dl"><i class="fa fa-caret-right"></i> ダウンロード済</a><br>');
        console.log('ボタンが切り替わりました');
        $('#dl_button').attr('id','dl_after');
    })
});