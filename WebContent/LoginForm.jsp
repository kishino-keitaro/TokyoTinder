<%@ page contentType="text/html; charset=Windows-31J"
	pageEncoding="Windows-31J"%>
<%@ page import="models.ProfileEntity"%>
<%@ page import="models.TinderDao"%>
<!DOCTYPE HTML>
<HTML>
<HEAD>
<TITLE>JSP �T���v��</TITLE>
<META charset="Windows-31J">
<link rel="stylesheet" type="text/css" href="css/login.css">
<script>
	function validate() {
		var loginId = document.form.loginId.value;
		var pass = document.form.pass.value;

		if (loginId == null || loginId == "") {
			alert("ID����͂��Ă��������B");
			return false;
		} else if (pass == null || pass == "") {
			alert("�p�X���[�h����͂��Ă��������B");
			return false;
		}
	}
</script>
</HEAD>
<header> </header>
<BODY>
	<img src="./img/logo.png">

	<FORM name="form" ACTION="Login" METHOD="post"
		onsubmit="return validate()">


		<ul>

			<!--���[�U�[�l�[��-->
			<li class="username"><label for="username">���[�UID*</label> <input
				class="username" type="text" name="loginId" id="loginId"
				maxlength="16" placeholder="ID" size="60">
				<p>�����p�p����4�`16�����ȓ�</p></li>

			<!--�p�X���[�h-->
			<li class="password"><label for="password">�p�X���[�h*</label> <input
				class="password" type="password" name="pass" id="pass"
				maxlength="16" placeholder=Password size="60">
				<p>�����p�p����4�`16�����ȓ�</p></li>

			<!--���O�C���{�^��-->
			<li class="signup"><a href="./signup.jsp" target="">�V�K�o�^</a> <input
				id="button" type="submit" name="button" value="���O�C��">
				<p>
					<span style="color: red"><%=(request.getAttribute("message") == null) ? "" : request.getAttribute("message")%></span>
				</p></li>
		</ul>

	</FORM>


</BODY>
</HTML>
