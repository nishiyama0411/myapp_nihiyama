<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.ArrayList,bean.OrderedItem"%>
<%
ArrayList<OrderedItem> list = (ArrayList<OrderedItem>) request.getAttribute("ordered_list");
%>
<html>
<head>
<title>購入履歴</title>
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
					</ul>
				</div>

				<!-- ページタイトル -->
				<div id="page_title">
					<h2>購入履歴</h2>
				</div>
			</div>
		</div>

		<!-- コンテンツ部分 -->
		<div id="main" class="container">
			<table>
				<tr>
					<th>Title</th>
					<th>数量</th>
					<th>注文日</th>
				</tr>
				<%
				// listがnullでない場合に一覧表示
				if (list != null) {
					for (int i = 0; i < list.size(); i++) {
						OrderedItem ordered = (OrderedItem) list.get(i);
				%>
				<tr>
					<td><%=ordered.getTitle()%></td>
					<td><%=ordered.getQuantity()%></td>
					<td><%=ordered.getDate().replace("-", "/")%></td>
				</tr>

				<%
				}
				}
				%>
			</table>
		</div>

		<!-- フッター部分 -->
		<%@ include file="/common/footer.jsp"%>

	</div>
</body>
</html>