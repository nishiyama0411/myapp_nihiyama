<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="bean.User"%>
<%
//セッションスコープでuser情報を受け取る
User user = (User) session.getAttribute("user");
//セッション切れか確認
if (user == null) {
//セッション切れならerror.jspへフォワード
request.setAttribute("error", "セッション切れの為、パスワード変更画面が表示できませんでした。");
request.setAttribute("cmd", "logout");
request.getRequestDispatcher("/view/error.jsp").forward(request, response);
return;
}

%>

<html>
<head>
<title>パスワード変更</title>
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
				<div id="nav">
					<ul>
						<li><a href="<%=request.getContextPath()%>/view/menu.jsp">[メニュー]</a></li>
					</ul>
				</div>
				<!-- ページタイトル -->
				<div id="page_title">
					<h2>パスワード変更</h2>
				</div>
			</div>
		</div>

		<!-- コンテンツ部分 -->
		<div id="main" class="container">
			<table>
				<form action="<%=request.getContextPath()%>/changePassword"
					method="get">
					<table class="changepass-table">
						<tr>
							<th>ユーザー</th>
							<td><%=user.getUserid()%></td>
						</tr>
						<tr>
							<th>旧パスワード</th>
							<td><input type="password" name="oldPass"></td>
						</tr>
						<tr>
							<th>新パスワード</th>
							<td><input type="password" name="newPass1"></td>
						</tr>
						<tr>
							<th>新パスワード(確認用)</th>
							<td><input type="password" name="newPass2"></td>
						</tr>
					</table>

					<table>
						<tr>
							<td><input type="submit" value="変更"></td>
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

