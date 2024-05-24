<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.ArrayList,util.MyFormat,bean.Book"%>
<%MyFormat format = new MyFormat();%>

<html>
	<head>
		<title>書籍登録</title>
		<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/style.css">
	</head>
	<body>
		<!-- ブラウザ全体 -->
		<div id="wrap">

			<!-- ヘッダー部分 -->
			<%@ include file="/common/header.jsp" %>

			<!-- メニュー部分 -->
			<div id="menu">
				<div class="container">
					<!-- ナビゲーション  -->
					<div id="nav">
						<ul>
							<li><a href ="<%=request.getContextPath()%>/view/menu.jsp" >[メニュー]</a></li>
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
				<table>
				<tr><h3>初期データとして以下を登録しました。</h3></tr>
				<tr>
					<th>isbn</th>
					<th>title</th>
					<th>価格</th>
				</tr>
				<%
				ArrayList<Book> list = (ArrayList<Book>) request.getAttribute("book_list");
				
					for (int i = 0; i < list.size(); i++) {
						Book book = (Book) list.get(i);
				%>
				<tr>
					<td><%=book.getIsbn()%></td>
					<td><%=book.getTitle()%></td>
					<td><%=format.moneyFormat(book.getPrice())%></td>
				</tr>
				<%
				}
				%>
			</table>
			</div>

			<!-- フッター部分 -->
			<%@ include file="/common/footer.jsp" %>

		</div>
	</body>
</html>