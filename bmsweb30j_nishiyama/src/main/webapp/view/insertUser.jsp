<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="bean.User"%>
<html>
<head>
<title>ユーザー登録</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
	<!-- ブラウザ全体 -->
	<div id="wrap">

		<!-- ヘッダー部分 -->
		<%@ include file="/common/header.jsp"%>

		<!-- メニュー部分 -->
		<div id="menu">
			<div class="container">
				<!-- ナビゲーション  -->
				<div id="nav">
					<ul>
						<li><a href="<%=request.getContextPath()%>/view/menu.jsp">[メニュー]</a></li>
						<li><a href="<%=request.getContextPath()%>/listUser">[ユーザー一覧]</a></li>
					</ul>
				</div>

				<!-- ページタイトル -->
				<div id="page_title">
					<h2>書籍登録</h2>
				</div>
			</div>
		</div>

		<!-- 書籍登録コンテンツ部分 -->
		<div id="main" class="container">
			<!--  入力フォーム -->
			<form action="<%=request.getContextPath()%>/insertUser">
				<table class="input-table" align="center">
					<tr>
						<th>ユーザー</th>
						<td><input type="text" name="user"></td>
					</tr>
					<tr>
						<th>パスワード</th>
						<td><input type="text" name="pass1"></td>
					</tr>
					<tr>
						<th>パスワード(確認用)</th>
						<td><input type="text" name="pass2"></td>
					</tr>
					<tr>
						<th>Eメール</th>
						<td><input type="text" name="email"></td>
					</tr>
					<tr>
						<th>権限</th>
						<td><select name="auth">
								<option></option>
								<option value="1">一般ユーザー</option>
								<option value="2">管理者</option>
						</select></td>
					</tr>
				</table>
				<input type="submit" value="登録">
			</form>
		</div>

		<!-- フッター部分 -->
		<%@ include file="/common/footer.jsp"%>
	</div>
</body>
</html>