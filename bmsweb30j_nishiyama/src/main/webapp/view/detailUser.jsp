<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="bean.User"%>

<%
User user = (User) request.getAttribute("user");
%>

<html>
<head>
<title>ユーザー詳細情報</title>
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
						<li><a href="<%=request.getContextPath()%>/view/insertUser.jsp">[ユーザー登録]</a></li>
						<li><a href="<%=request.getContextPath()%>/listUser">[ユーザー一覧]</a></li>
					</ul>
				</div>

				<!-- ページタイトル -->
				<div id="page_title">
					<h2>ユーザー詳細情報</h2>
				</div>
			</div>
		</div>

		<!-- 書籍詳細情報コンテンツ部分 -->
		<div id="main" class="container">

			<!-- 書籍変更入力フォーム -->
			<div class="detail-button">
				<form action="<%=request.getContextPath()%>/detailUser"
					class="inline-block">
					<input type="hidden" name="user" value="<%=user.getUserid()%>">
					<input type="hidden" name="cmd" value="updateUser"></input>
					<input type="submit" value="変更">
				</form>
				&nbsp;&nbsp;&nbsp;
				<form action="<%=request.getContextPath()%>/deleteUser"
					class="inline-block">
					<input type="hidden" name="user" value="<%=user.getUserid()%>">
					<input type="submit" value="削除">
				</form>
			</div>
			
			<table class="detail-table">
				<tr>
					<th>ユーザー</th>
					<td class="white"><%=user.getUserid()%></td>
				</tr>
				<tr>
					<th>パスワード</th>
					<td class="white"><%=user.getPassword()%></td>
				</tr>
				<tr>
					<th>Eメール</th>
					<td class="white"><%=user.getEmail()%></td>
				</tr>
				<tr>
					<th>権限</th>
					<%if(user.getAuthority().equals("1")) {%>
					<td class="white">一般ユーザー</td>
					<%} else if(user.getAuthority().equals("2")) {%>	
					<td class="white">管理者</td>
					<%} %>
				</tr>
			</table>

		</div>

		<!-- フッター部分 -->
		<%@ include file="/common/footer.jsp"%>

	</div>
</body>
</html>