<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.ArrayList,bean.User"%>
<%
ArrayList<User> userList = (ArrayList<User>) request.getAttribute("user_list");
%>

<html>
<head>
<title>ユーザー一覧</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
	<!-- ブラウザ全体 -->
	<div id="wrap">

		<!--ヘッダー部分  -->
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
					</ul>
				</div>

				<!-- ページタイトル -->
				<div id="page_title">
					<h2>ユーザー一覧</h2>
				</div>
			</div>
		</div>

		<!-- 書籍一覧のコンテンツ部分 -->
		<div id="main" class="container">

			<!-- 検索フォーム -->
			<div class="box-left">
			<form class="inline-block"
				action="<%=request.getContextPath()%>/listUser">
				ユーザー <input type="text" name="searchUserid"> <input type="submit"
					value="検索">
			</form>
			<form class="inline-block"
				action="<%=request.getContextPath()%>/listUser">
				<input type="submit" value="全件表示">
			</form>
			</div>

			<!-- 書籍情報リスト -->
			<table class="list-table">
				<thead>
					<tr>
						<th>ユーザー</th>
						<th>Eメール</th>
						<th>権限</th>
						<th>変更 / 削除</th>
					</tr>
				</thead>
				<tbody>
					<%
					if (userList != null) {
						for (User user : userList) {
					%>
					<tr>
						<td><a
							href="<%=request.getContextPath()%>/detailUser?user=<%=user.getUserid()%>&cmd=detailUser"><%=user.getUserid()%></a></td>
						<td><%=user.getEmail()%></td>
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
						<td><a
							href="<%=request.getContextPath()%>/detailUser?user=<%=user.getUserid()%>&cmd=updateUser">変更</a>
							<a
							href="<%=request.getContextPath()%>/deleteUser?user=<%=user.getUserid()%>">削除</a>
						</td>
					</tr>
					<%
					}
					}
					%>
				</tbody>
			</table>
		</div>

		<!-- フッター部分 -->
		<%@ include file="/common/footer.jsp"%>

	</div>
</body>
</html>