<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="bean.User"%>

<html>
<head>
<title>MENU</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
	<!-- ブラウザ全体 -->
	<div id="wrap">

		<!-- ヘッダー部分 -->
		<%@ include file="/common/header.jsp"%>
		
			<%@ include file="/common/userInfo.jsp"%>
		
		<!-- メニュー部分 -->
		<div id="menu">
			<div class="container">
				<!-- ページタイトル -->
				<div id="page_title">
					<h2>MENU</h2>
				</div>
			</div>
		</div>
		<%
		if (user.getAuthority().equals("1")) {
		%>
		<!-- コンテンツ(本文) -->
		<div id="m_main" class="container">
			<ul class="link">
				<li><a href="<%=request.getContextPath()%>/list">【書籍一覧】</a></li>
				<li><a href="<%=request.getContextPath()%>/showCart">【カート状況確認】</a></li>
				<li><a href="<%=request.getContextPath()%>/showHistoryOrderedItem">【購入履歴】</a></li>
				<li><a href="<%=request.getContextPath()%>/view/changePassword.jsp">【パスワード変更】</a></li>
				<li><a href="<%=request.getContextPath()%>/logout">【ログアウト】</a></li>
			</ul>
		</div>
		<%
		} else if (user.getAuthority().equals("2")) {
		%>
		<!-- コンテンツ(本文) -->
		<div id="m_main" class="container">
			<ul class="link">
				<li><a href="<%=request.getContextPath()%>/list">【書籍一覧】</a></li>
				<li><a href="<%=request.getContextPath()%>/view/insert.jsp">【書籍登録】</a></li>
				<li><a href="<%=request.getContextPath()%>/insertIniData">【初期データ登録（データがない場合のみ）】</a></li>
				<li><a href="<%=request.getContextPath()%>/showOrderedItem">【購入状況確認】</a></li>
				<li><a href="<%=request.getContextPath()%>/showSalesByMonth">【売上げ状況】</a></li>
				<li><a href="<%=request.getContextPath()%>/view/insertUser.jsp">【ユーザー登録】</a></li>
				<li><a href="<%=request.getContextPath()%>/listUser">【ユーザー一覧】</a></li>
				<li><a href="<%=request.getContextPath()%>/view/changePassword.jsp">【パスワード変更】</a></li>
				<li><a href="<%=request.getContextPath()%>/logout">【ログアウト】</a></li>
			</ul>
		</div>
		<%
		}
		%>

		<!-- フッター部分 -->
		<%@ include file="/common/footer.jsp"%>

	</div>
</body>

</html>