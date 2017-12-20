function dateCheck(year, month, day) {
  var y = Number(document.getElementsByName(year)[0].value);//年
  //name属性がyearのvalueの値を取得して数値に変換してyに代入
  var m = Number(document.getElementsByName(month)[0].value);//月
  //name属性dayの値をすべて取得
  var day = Number(document.getElementsByName()[0].value);//日
  //dayのvalueを数値に変換して代入

  if (y && m) {
  	//yとmに値が入っている場合に実行
    var ds = new Date(y, m, 0);
    //選択された日にちを取得して代入
    var dsn = Number(ds.getDate());
    //getDateメソッドでその月の最後の日付を取得

    var html=0 ;
    //htmlの書き換えのための変数を定義

    for(var i = 1; i <= dsn; i++) {
    	//その月の日数分の選択可能日を生成
      if (i === d) {
        html += '<option value="' + i + '" selected>' + i + '</option>';
        //選択された値を保持するためselectedをつけて変数htmlに追加
      }
      else {
        html += '<option value="' + i + '">' + i + '</option>';
        //i日のオプションを作成して変数htmlに追加
      }
    }
    document.getElementsByName(document.getElementsByName(document.getElementsByName(day)[0])[0])[0].innerHTML = html;
    //name属性dayの中身を変数htmlの中身に変換
  }
}