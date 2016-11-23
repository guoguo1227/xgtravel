$(document).ready(function(){
    $.get("/login.json",function(data){
        if(data == "false"){
            toastr.info("请先登陆!");
            window.location.href= "../../login.vm";
        }else{
            console.log("")
        }
    })

});