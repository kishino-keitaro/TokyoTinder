<%@ page contentType="text/html; charset=Windows-31J"
	pageEncoding="Windows-31J"%>
<%@ page import="models.ProfileEntity"%>
<%@ page import="models.TinderDao"%>
<!DOCTYPE HTML>
<HTML>
<HEAD>
<TITLE>JSP サンプル</TITLE>
<META charset="Windows-31J">
<link rel="stylesheet" type="text/css" href="css/login.css">
<script>
	function validate() {
		var loginId = document.form.loginId.value;
		var pass = document.form.pass.value;

		if (loginId == null || loginId == "") {
			alert("IDを入力してください。");
			return false;
		} else if (pass == null || pass == "") {
			alert("パスワードを入力してください。");
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

			<!--ユーザーネーム-->
			<li class="username"><label for="username">ユーザID*</label> <input
				class="username" type="text" name="loginId" id="loginId"
				maxlength="16" placeholder="ID" size="60">
				<p>※半角英数字4～16文字以内</p></li>

			<!--パスワード-->
			<li class="password"><label for="password">パスワード*</label> <input
				class="password" type="password" name="pass" id="pass"
				maxlength="16" placeholder=Password size="60">
				<p>※半角英数字4～16文字以内</p></li>

			<!--ログインボタン-->
			<li class="signup"><a href="./signup.jsp" target="">新規登録</a> <input
				id="button" type="submit" name="button" value="ログイン">
				<p>
					<span style="color: red"><%=(request.getAttribute("message") == null) ? "" : request.getAttribute("message")%></span>
				</p></li>
		</ul>

	</FORM>


</BODY>
</HTML>
