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
<BODY>
<img src="./img/logo.png">
	<DIV CLASS="page">
		<FORM name="form" ACTION="controllers/Login" METHOD="post"
			onsubmit="return validate()">

			<div class="input_item">
				<TABLE>
					<TR>
						<TD>ID</TD>
						<TD><INPUT TYPE="text" NAME="loginId" id="loginId"
							maxlength="16"></TD>
						<TD>�����p�p����4�`16�����ȓ�</TD>
					</TR>
					<TR>
						<TD>Password</TD>
						<TD><INPUT TYPE="password" NAME="pass" id="pass"
							maxlength="16"></TD>
						<td>�����p�p����4�`16�����ȓ�</td>
					</TR>
					<tr>
						<td><span style="color: red"><%=(request.getAttribute("message") == null) ? "" : request
					.getAttribute("message")%></span></td>
					</tr>
				</TABLE>

			</DIV>

			<INPUT TYPE="submit" VALUE="���O�C��">

		</FORM>
		<FORM ACTION="signup.jsp" METHOD="post">
			<INPUT TYPE="submit" VALUE="�V�K�o�^">
		</FORM>
	</DIV>

</body>
</BODY>
</HTML>
