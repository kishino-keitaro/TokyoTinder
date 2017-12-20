

    // 入力内容チェックのための関数
    function input_check(){
    var result = true;

    // エラー用装飾のためのクラスリセット
    $('#loginId').removeClass("inp_error");
    $('#pass').removeClass("inp_error");

    // 入力エラー文をリセット
    $("#loginId_error").empty();
    $("#pass_error").empty();


    // 入力内容セット
    var id   = $("#loginId").val();
    var furigana  = $("#pass").val();


    // 入力内容チェック

    // お名前
    if(id == ""){
        $("#loginId_error").html("<i class='fa fa-exclamation-circle'></i> IDは必須です。");
        $("#loginId").addClass("inp_error");
        result = false;
    }else if(id.length > 16){
        $("#loginId_error").html("<i class='fa fa-exclamation-circle'></i> IDは16文字以内で入力してください。");
        $("#loginId").addClass("inp_error");
        result = false;
    }
    // フリガナ
    if(pass == ""){
        $("#pass_error").html("<i class='fa fa-exclamation-circle'></i> パスワードは必須です。");
        $("#pass").addClass("inp_error");
        result = false;

    }else if(furigana.length > 25){
        $("#pass_error").html("<i class='fa fa-exclamation-circle'></i> パスワードは16文字以内入力してください。");
        $("#pass").addClass("inp_error");
        result = false;
    }



    return result;
}
