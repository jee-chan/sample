window.onload = function(){
// form要素を取得
var element = document.getElementById( "itemEditForm" ) ;

// form要素内のラジオボタングループ(name="hoge")を取得
var elements = element.condition;
// elements["${item.condition}"-1].checked = true;
elements[1].checked = true;

}


function addSalePriceForm(){
    if(document.itemEditForm.saleOptionButton.value=="on"){
        // document.itemEditForm["saleStartForm"].disabled = false;
        // document.itemEditForm["saleEndForm"].disabled = false;
        // document.itemEditForm["salePriceForm"].disabled = false;
        document.getElementById('saleStartForm').style.display="inline";
        document.getElementById('saleEndForm').style.display="inline";
        document.getElementById('salePriceForm').style.display="inline";

        document.itemEditForm["saleStartDate"].disabled = false;
        document.getElementById('saleStartDate').style.display="inline";
        document.itemEditForm["saleStartTime"].disabled = false;
        document.getElementById('saleStartTime').style.display="inline";
        document.itemEditForm["saleEndDate"].disabled = false;
        document.getElementById('saleEndDate').style.display="inline";
        document.itemEditForm["saleEndTime"].disabled = false;
        document.getElementById('saleEndTime').style.display="inline";
        document.getElementById('saleOptionMassage').style.display="inline";
        document.getElementById('salePrice').style.display="inline";
        document.itemEditForm["salePrice"].disabled = false;

        var toDay = new Date();
        toDay.setDate(toDay.getDate());
        var yyyy = toDay.getFullYear();
        var MM = ("0"+(toDay.getMonth()+1)).slice(-2);
        var dd = ("0"+toDay.getDay()).slice(-2);

        // document.itemEditForm.saleStartDate.value = yyyy + '-' + MM+'-' + dd;
        document.getElementById("saleStartDate").value=yyyy+'-'+MM+'-'+dd;
        // document.itemEditForm.saleStartDate.min = yyyy + '-' + MM + '-'+ dd;
        document.getElementById("saleStartDate").min=yyyy+'-'+MM+'-'+dd;
        // document.itemEditForm.saleStartTime.value = toDay.getHours+':'+toDay.getMinutes;
        document.getElementById("saleStartTime").value = toDay.getHours+':'+toDay.getMinutes;

        // itemEditForm.saleEnddate.value = 
        // itemEditForm.saleEnddate.min = 
        // itemEditForm.saleEndTime.value = 

        // itemEditForm.salePrice.value = 
        
    }else{
        // document.itemEditForm["saleStartForm"].disabled = true;
        // document.itemEditForm["saleEndForm"].disabled = true;
        // document.itemEditForm["salePriceForm"].disabled = true;
        document.getElementById('saleStartForm').style.display="none";
        document.getElementById('saleEndForm').style.display="none";
        document.getElementById('salePriceForm').style.display="none";

        document.itemEditForm["saleStartDate"].disabled = true;
        document.getElementById('saleStartDate').style.display="none";
        document.itemEditForm["saleStartTime"].disabled = true;
        document.getElementById('saleStartTime').style.display="none";
        document.itemEditForm["saleEndDate"].disabled = true;
        document.getElementById('saleEndDate').style.display="none";
        document.itemEditForm["saleEndTime"].disabled = true;
        document.getElementById('saleEndTime').style.display="none";
        document.getElementById('saleOptionMassage').style.display="none";
        document.getElementById('salePrice').style.display="none";
        document.itemEditForm["salePrice"].disabled = true;
    }
}

window.onload =addSalePriceForm();