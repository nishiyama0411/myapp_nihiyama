<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="bean.User"%>
<%
// ユーザー情報を受け取る
User user = (User) request.getAttribute("user");
%>

<html>
<head>
<title>ユーザー変更</title>
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
						<li><a
							href="<%=request.getContextPath()%>/view/insertUser.jsp">[ユーザー登録]</a></li>
						<li><a href="<%=request.getContextPath()%>/listUser">[ユーザー一覧]</a></li>
					</ul>
				</div>
				<!-- ページタイトル -->
				<div id="page_title">
					<h2>ユーザー変更</h2>
				</div>
			</div>
		</div>

		<!-- コンテンツ部分 -->
		<div id="main" class="container">
			<table style="margin: auto; border: 0;">
				<form action="<%=request.getContextPath()%>/updateUser" method="get">
					<table>
						<tr>
							<th class="white"></th>
							<th class="white">&lt;&lt;変更前情報&gt;&gt;</th>
							<th class="white">&lt;&lt;変更後情報&gt;&gt;</th>
						</tr>
						<tr>
							<th>ユーザー</th>
							<td><%=user.getUserid()%></td>
							<td><%=user.getUserid()%></td>
							<input type="hidden" name="user" value="<%=user.getUserid()%>">
						</tr>
						<tr>
							<th>パスワード</th>
							<td><%=user.getPassword()%></td>
							<td><input type="password" name="pass1"></td>
						</tr>
						<tr>
							<th>パスワード(確認用)</th>
							<td></td>
							<td><input type="password" name="pass2"></td>
						</tr>
						<tr>
							<th>Eメール</th>
							<td><%=user.getEmail()%></td>
							<td><input type="text" name="email"></td>
						</tr>
						<tr>
							<th>権限</th>
							<%
							if (user.getAuthority().equals("1")) {
							%>

							<td>一般ユーザー</td>
							<%
							} else if (user.getAuthority().equals("2")) {
							%>
							<td>管理者</td>
							<%
							}
							%>
							<td><select name="auth">
									<option></option>
									<option value="1">一般ユーザー</option>
									<option value="2">管理者</option>
							</select></td>
							</th>
					</table>

					<table>
						<tr>
							<td><input type="submit" value="変更完了"></td>
						</tr>
					</table>
				</form>
			</table>
		</div>

		<!-- フッター部分 -->
		<%@ include file="/common/footer.jsp"%>

	</div>
</body>
</html>