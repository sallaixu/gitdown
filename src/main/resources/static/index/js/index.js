



    $("#loginBtn").click(function (){
        console.log("电解铝");
        $("#login").modal("show");
    })

    // $("#loginSubmit").click(function (){
    //     var Aname = $("#Aname").val();
    //     var Apwd = $("#Apwd").val();
    //     $.ajax({
    //         url:"/login",
    //         type: "post",
    //         data: {
    //             'Aname':Aname,
    //             'Apwd':Apwd
    //         },
    //         success(res) {
    //             console.log(res)
    //            if(res.code==200){
    //                console.log(res)
    //            }
    //         }
    //         }
    //     )
    // })
    //下载提交数据
    $("#submit").click(function (){
        console.log("提交任务")
        var url = $("#url").val();
        var verityCode = $("#verityCode").val();
        console.log("verity"+verityCode)
        if(url.indexOf("https://github.com")!=0){
            $("#res").text("链接不正确");
            $("#res").css("color","red")
            return;
        }
        showModal();

                $.ajax({
                    url:"/download",
                    async:true,
                    dataType : 'json',
                    type:"post",
                    data:{'url':url,
                          'verityCode':verityCode
                         },
                    success:function (res){
                        console.log(res)
                        if(res.code==200){

                            $("#res").text(res.msg);
                            $('#qrcode').modal('hide');
                            $('#downUrl').text("点我下载");

                            $('#downUrl').attr('href',res.data.fileName);

                        }else{

                            $('#qrcode').modal('hide');
                            $("#res").css("color","red");
                            $("#res").text(res.msg);

                        }

                    }
                })

    })


       function showModal(){
            $('#qrcode').modal('show');//展示
           //  $('#qrcode').on('show.bs.modal', function (event) {//模型框显示后，可以定义里面的值，这个不是动态的值，用处不大
           //     var modal = $(this);  //get modal itself
           //     modal.find('.modal-body #message').text('请稍等,请勿刷新页面');
           //
           // });
        };
function changeMsg() {
     $('#qrcode').on('show.bs.modal', function (event) {//模型框显示后，可以定义里面的值，这个不是动态的值，用处不大
        var modal = $(this);  //get modal itself
        modal.find('.modal-body #message').text('请稍等,请勿刷新页面');

    });
}



