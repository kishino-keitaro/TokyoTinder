<%@ page language="java" contentType="text/html; charset=Windows-31J"
	pageEncoding="Windows-31J"%>
<%@ page import="models.ProfileEntity"%>
<%@ page import="models.TinderDao"%>
<%@page import="java.util.*, java.io.*"%>
<!DOCTYPE html>
<html>
<head>
<title>�V�K�o�^���</title>
<link rel="stylesheet" href="css/validationEngine.jquery.css"
	type="text/css" />
<link rel="stylesheet" type="text/css" href="css/login.css">
<script src="js/date.js" type="text/javascript"></script>
<script src="http://code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="js/jquery-1.8.2.min.js" type="text/javascript"></script>
<script src="js/languages/jquery.validationEngine-ja.js"
	type="text/javascript" charset="utf-8"></script>
<script src="js/jquery.validationEngine.js" type="text/javascript"
	charset="Windows-31J"></script>
<script type="text/javascript">
	jQuery(document).ready(function() {
		jQuery("#form").validationEngine();
	});
</script>
<script>
	$(function() {
		$('#myfile').change(function(e) {
			//�t�@�C���I�u�W�F�N�g���擾����
			var file = e.target.files[0];
			var reader = new FileReader();

			//�摜�łȂ��ꍇ�͏����I��
			if (file.type.indexOf("image") < 0) {
				alert("�摜�t�@�C�����w�肵�Ă��������B");
				return false;
			}

			//�A�b�v���[�h�����摜��ݒ肷��
			reader.onload = (function(file) {
				return function(e) {
					$("#img1").attr("src", e.target.result);
					$("#img1").attr("title", file.name);
				};
			})(file);
			reader.readAsDataURL(file);

		});
	});
</script>

</head>

<body onload="dateCheck('year', 'month', 'day');">

	<ul>

		<form id="form" method="post" action="controllers/Registration">







			<!-- �ȉ��Ajavascript -->
			<script type="text/javascript">
				$(function() {
					var file = null; // �I�������t�@�C��
					var blob = null; // �摜(BLOB�f�[�^)
					const THUMBNAIL_WIDTH = 300; // �摜���T�C�Y��̉��̒����̍ő�l
					const THUMBNAIL_HEIGHT = 300; // �摜���T�C�Y��̏c�̒����̍ő�l

					// �t�@�C�����I�����ꂽ��
					$('input[type=file]').change(
							function() {

								// �t�@�C�����擾
								file = $(this).prop('files')[0];
								// �I�����ꂽ�t�@�C�����摜���ǂ�������
								if (file.type != 'image/jpeg'
										&& file.type != 'image/png') {
									// �摜�łȂ��ꍇ�͏I��
									file = null;
									blob = null;
									return;
								}

								// �摜�����T�C�Y����
								var image = new Image();
								var reader = new FileReader();
								reader.onload = function(e) {
									image.onload = function() {
										var width, height;
										if (image.width > image.height) {
											// �����̉摜�͉��̃T�C�Y���w��l�ɂ��킹��
											var ratio = image.height
													/ image.width;
											width = THUMBNAIL_WIDTH;
											height = THUMBNAIL_WIDTH * ratio;
										} else {
											// �c���̉摜�͏c�̃T�C�Y���w��l�ɂ��킹��
											var ratio = image.width
													/ image.height;
											width = THUMBNAIL_HEIGHT * ratio;
											height = THUMBNAIL_HEIGHT;
										}
										// �T���l�`��pcanvas�̃T�C�Y����ŎZ�o�����l�ɕύX
										var canvas = $('#canvas').attr('width',
												width).attr('height', height);
										var ctx = canvas[0].getContext('2d');
										// canvas�Ɋ��ɕ`�悳��Ă���摜���N���A
										ctx.clearRect(0, 0, width, height);
										// canvas�ɃT���l�C����`��
										ctx.drawImage(image, 0, 0, image.width,
												image.height, 0, 0, width,
												height);

										// canvas����base64�摜�f�[�^���擾
										var base64 = canvas.get(0).toDataURL(
												'image/jpeg');
										// base64����Blob�f�[�^���쐬
										var barr, bin, i, len;
										bin = atob(base64.split('base64,')[1]);
										len = bin.length;
										barr = new Uint8Array(len);
										i = 0;
										while (i < len) {
											barr[i] = bin.charCodeAt(i);
											i++;
										}
										blob = new Blob([ barr ], {
											type : 'image/jpeg'
										});
										console.log(blob);
									}
									image.src = e.target.result;
								}
								reader.readAsDataURL(file);
							});

					// �A�b�v���[�h�J�n�{�^�����N���b�N���ꂽ��
					$('#upload').click(function() {

						// �t�@�C�����w�肳��Ă��Ȃ���Ή����N����Ȃ�
						if (!file || !blob) {
							return;
						}

						var name, fd = new FormData();
						fd.append('file', blob); // �t�@�C����Y�t����

						$.ajax({
							url : "http://exapmle.com", // ���M��
							type : 'POST',
							dataType : 'json',
							data : fd,
							processData : false,
							contentType : false
						}).done(function(data, textStatus, jqXHR) {
							// ���M����
						}).fail(function(jqXHR, textStatus, errorThrown) {
							// ���M���s
						});

					});

				});
			</script>

			<!--�t�@�C����I���{�^��-->
			<li>
				<p class=clearfix>
					<img id="img1" style="width: 160px; height: 200px;" /> <input
						type="file" id="myfile"><br>
				</p> <!--�摜���A�b�v���[�h�{�^��-->
				<p class="button3">
					<input class="button3" type="submit" name="button3"
						value="�摜���A�b�v���[�h">
			</li>
			<p>���摜�̍ő�T�C�Y�F700KB</p>
			<p>�Ή��t�H�[�}�b�g��GIF�AJPEG�APNG�ł��B</p>


			<!--���O-->

			<li class="name"><label for="name">���O*</label> <input
				class="validate[required,minSize[4],maxSize[16]]" type="text"
				name="name" placeholder="���[�U�[��" size="60">
				<p>�����p�p����4�`16�����ȓ�</p></li>

			<!--����--->
			<li class="gender"><label for="gender">����*</label> <!--�j��---> <input
				type="radio" name="����" value="m" /> �j�� <!--����---> <input
				class="female" type="radio" name="����" value="f" checked> ����</li>

			<!--ID-->
			<li class="id"><label for="id">ID*</label> <input
				class="validate[required,minSize[4],maxSize[16]]" type="text"
				name="id" placeholder="ID" size="60">
				<p>�����p�p����4�`16�����ȓ�</p></li>


			<!--�p�X���[�h-->
			<li class="password1"><label for="password1">Password*</label> <input
				class="validate[required,minSize[4],maxSize[16],custom[onlyLetterNumber]]text-input"
				type="password" name="pass" id="passwd" placeholder="Password"
				size="60">
				<p>�����p�p����4�`16�����ȓ�</p></li>

			<!--�p�X���[�h�m�F�p-->
			<li class="password2"><label for="password2">Password(�m�F�p)*</label>
				<input class="validate[required,equals[passwd]] text-input"
				type="password" id="reepass" placeholder="Password�m�F�p" size="60">
				<p>�����p�p����4�`16�����ȓ�</p></li>

			<!--�N��A���N����-->
			<li class="birthday"><label for="birthday">�N��*</label> <!--�N��-->
				<select name="year" onchange="dateCheck('year', 'month', 'day')">
					<!-- �I������javascript�쓮-->
					<option value="1900">1900</option>
					<option value="1901">1901</option>
					<option value="1902">1902</option>
					<option value="1903">1903</option>
					<option value="1904">1904</option>
					<option value="1905">1905</option>
					<option value="1906">1906</option>
					<option value="1907">1907</option>
					<option value="1908">1908</option>
					<option value="1909">1909</option>
					<option value="1910">1910</option>
					<option value="1911">1911</option>
					<option value="1912">1912</option>
					<option value="1913">1913</option>
					<option value="1914">1914</option>
					<option value="1915">1915</option>
					<option value="1916">1916</option>
					<option value="1917">1917</option>
					<option value="1918">1918</option>
					<option value="1919">1919</option>
					<option value="1920">1920</option>
					<option value="1921">1921</option>
					<option value="1922">1922</option>
					<option value="1923">1923</option>
					<option value="1924">1924</option>
					<option value="1925">1925</option>
					<option value="1926">1926</option>
					<option value="1927">1927</option>
					<option value="1928">1928</option>
					<option value="1929">1929</option>
					<option value="1930">1930</option>
					<option value="1931">1931</option>
					<option value="1932">1932</option>
					<option value="1933">1933</option>
					<option value="1934">1934</option>
					<option value="1935">1935</option>
					<option value="1936">1936</option>
					<option value="1937">1937</option>
					<option value="1938">1938</option>
					<option value="1939">1939</option>
					<option value="1940">1940</option>
					<option value="1941">1941</option>
					<option value="1942">1942</option>
					<option value="1943">1943</option>
					<option value="1944">1944</option>
					<option value="1945">1945</option>
					<option value="1946">1946</option>
					<option value="1947">1947</option>
					<option value="1948">1948</option>
					<option value="1949">1949</option>
					<option value="1950">1950</option>
					<option value="1951">1951</option>
					<option value="1952">1952</option>
					<option value="1953">1953</option>
					<option value="1954">1954</option>
					<option value="1955">1955</option>
					<option value="1956">1956</option>
					<option value="1957">1957</option>
					<option value="1958">1958</option>
					<option value="1959">1959</option>
					<option value="1960">1960</option>
					<option value="1961">1961</option>
					<option value="1962">1962</option>
					<option value="1963">1963</option>
					<option value="1964">1964</option>
					<option value="1965">1965</option>
					<option value="1966">1966</option>
					<option value="1967">1967</option>
					<option value="1968">1968</option>
					<option value="1969">1969</option>
					<option value="1970">1970</option>
					<option value="1971">1971</option>
					<option value="1972">1972</option>
					<option value="1973">1973</option>
					<option value="1974">1974</option>
					<option value="1975">1975</option>
					<option value="1976">1976</option>
					<option value="1977">1977</option>
					<option value="1978">1978</option>
					<option value="1979">1979</option>
					<option value="1980" selected="selected">1980</option>
					<option value="1981">1981</option>
					<option value="1982">1982</option>
					<option value="1983">1983</option>
					<option value="1984">1984</option>
					<option value="1985">1985</option>
					<option value="1986">1986</option>
					<option value="1987">1987</option>
					<option value="1988">1988</option>
					<option value="1989">1989</option>
					<option value="1990">1990</option>
					<option value="1981">1981</option>
					<option value="1982">1982</option>
					<option value="1983">1983</option>
					<option value="1984">1984</option>
					<option value="1985">1985</option>
					<option value="1986">1986</option>
					<option value="1987">1987</option>
					<option value="1988">1988</option>
					<option value="1989">1989</option>
					<option value="1990">1990</option>
					<option value="1991">1991</option>
					<option value="1992">1992</option>
					<option value="1993">1993</option>
					<option value="1994">1994</option>
					<option value="1995">1995</option>
					<option value="1996">1996</option>
					<option value="1997">1997</option>
					<option value="1998">1998</option>
					<option value="1999">1999</option>
					<option value="2000">2000</option>
					<option value="2001">2001</option>
					<option value="2002">2002</option>
					<option value="2003">2003</option>
					<option value="2004">2004</option>
					<option value="2005">2005</option>
					<option value="2006">2006</option>
					<option value="2007">2007</option>
					<option value="2008">2008</option>
					<option value="2009">2009</option>
					<option value="2010">2010</option>
					<option value="2011">2011</option>
					<option value="2012">2012</option>
					<option value="2013">2013</option>
					<option value="2014">2014</option>
					<option value="2015">2015</option>
					<option value="2016">2016</option>
					<option value="2017">2017</option>
			</select><label for="year"></label> <select name="month"
				onchange="dateCheck('year', 'month', 'day')">
					<!-- �I������javascript�쓮-->

					<option value="1" selected="selected">1</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
					<option value="5">5</option>
					<option value="6">6</option>
					<option value="7">7</option>
					<option value="8">8</option>
					<option value="9">9</option>
					<option value="10">10</option>
					<option value="11">11</option>
					<option value="12">12</option>
			</select><label for="month"></label> <select name="day">
					<!-- �쓮���ɒ��g��ύX-->
					<option value="1" selected="selected">1</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
					<option value="5">5</option>
					<option value="6">6</option>
					<option value="7">7</option>
					<option value="8">8</option>
					<option value="9">9</option>
					<option value="10">10</option>
					<option value="11">11</option>
					<option value="12">12</option>
					<option value="13">13</option>
					<option value="14">14</option>
					<option value="15">15</option>
					<option value="16">16</option>
					<option value="17">17</option>
					<option value="18">18</option>
					<option value="19">19</option>
					<option value="20">20</option>
					<option value="21">21</option>
					<option value="22">22</option>
					<option value="23">23</option>
					<option value="24">24</option>
					<option value="25">25</option>
					<option value="26">26</option>
					<option value="27">27</option>
					<option value="28">28</option>
					<option value="29">29</option>
					<option value="30">30</option>
					<option value="31">31</option>
			</select><label for="day"></label></li>

			<!--�v���t�B�[��-->
			<!--���ȃv���t�B�[��-->

			<li class="profile"><label for="profile">���ȃv���t�B�[��*</label> <textarea
					class="validate[maxSize[200]]" name="profile"
					placeholder="�v���t�B�[������͂��Ă��������B
���[�U�[�o�^��ɐݒ肷�邱�Ƃ��\�ł��B"  cols="62"
					rows="10" wrap="hard" maxlength="200"></textarea>
				<p>��200�����ȓ�</p></li>





			<!--���M�{�^��-->
			<li class="button2"><input class="button2" type="submit"
				name="button2" value="���M"></li> <a href="LoginForm.jsp"
				class="button2">���ǂ�</a>
	</ul>
	</form>
</body>
</html>