<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="bean.User"%>
<%
User user = (User) session.getAttribute("user");

String userid = ""; //ユーザーを管理する変数
String password = ""; //パスワードを管理する変数

String message = (String) request.getAttribute("message");
if (message == null){
    message = "";
}

Cookie[] userCookie = request.getCookies(); //クッキーの取得

//クッキーがあるか判定
if (userCookie != null) {
	for (int i = 0; i < userCookie.length; i++) {
		//クッキーからユーザー情報の取得
		if (userCookie[i].getName().equals("userid")) {
			userid = userCookie[i].getValue();
		}
		//クッキーからパスワード情報の取得
		if (userCookie[i].getName().equals("password")) {
			password = userCookie[i].getValue();
		}
	}
}
%>

<html>
<head>
<title>ログイン</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
	<!-- ブラウザ全体 -->
	<div id="wrap">
		<!-- ヘッダー部分 -->
		<%@ include file="/common/header.jsp"%>
		<div id="menu">
		<div class="container"><h2>&nbsp;</h2></div>
		</div>
		<!-- コンテンツ部分 -->
		<div id="main" class="container">
			<table>
				<form action="<%=request.getContextPath()%>/login" method="post">
					<table>
						<tr>
							<th>ユーザー</th>
							<td><input type="text" name="userid" value=<%=userid%>></td>
						</tr>
						<tr>
							<th>パスワード</th>
							<td><input type="password" name="password"
								value=<%=password%>></td>
						</tr>
					</table>
					<table>
					<tr>
					<p class="red"><%=message%></p>
					</tr>
						<tr>
							<td><input type="submit" value="ログイン"></td>
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
>
