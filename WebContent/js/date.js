function dateCheck(year, month, day) {
  var y = Number(document.getElementsByName(year)[0].value);//�N
  //name������year��value�̒l���擾���Đ��l�ɕϊ�����y�ɑ��
  var m = Number(document.getElementsByName(month)[0].value);//��
  //name����day�̒l�����ׂĎ擾
  var day = Number(document.getElementsByName()[0].value);//��
  //day��value�𐔒l�ɕϊ����đ��

  if (y && m) {
  	//y��m�ɒl�������Ă���ꍇ�Ɏ��s
    var ds = new Date(y, m, 0);
    //�I�����ꂽ���ɂ����擾���đ��
    var dsn = Number(ds.getDate());
    //getDate���\�b�h�ł��̌��̍Ō�̓��t���擾

    var html=0 ;
    //html�̏��������̂��߂̕ϐ����`

    for(var i = 1; i <= dsn; i++) {
    	//���̌��̓������̑I���\���𐶐�
      if (i === d) {
        html += '<option value="' + i + '" selected>' + i + '</option>';
        //�I�����ꂽ�l��ێ����邽��selected�����ĕϐ�html�ɒǉ�
      }
      else {
        html += '<option value="' + i + '">' + i + '</option>';
        //i���̃I�v�V�������쐬���ĕϐ�html�ɒǉ�
      }
    }
    document.getElementsByName(document.getElementsByName(document.getElementsByName(day)[0])[0])[0].innerHTML = html;
    //name����day�̒��g��ϐ�html�̒��g�ɕϊ�
  }
}