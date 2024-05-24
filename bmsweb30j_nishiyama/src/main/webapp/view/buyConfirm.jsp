<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.ArrayList,util.MyFormat"%>
<%@page import="bean.Book"%>
<%@page import="bean.User"%>
<%MyFormat format = new MyFormat();%>
<html>
	<head>
		<title>購入状況</title>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
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
							<li><a href ="<%=request.getContextPath()%>/list">[書籍一覧]</a></li>
						</ul>
					</div>

					<!-- ページタイトル -->
					<div id="page_title">
						<h2>購入品確認</h2>
					</div>
				</div>
			</div>
			
			
			<!-- コンテンツ部分 -->
			<div id="main" class="container">
				<h3>下記の商品を購入いたしました。<br>
							ご購入ありがとうございました。</h3>
				<table>
				<tr>
					<th>ISBN</th>
					<th>TITLE</th>
					<th>価格</th>
				</tr>
				<%
				ArrayList<Book> list = (ArrayList<Book>) request.getAttribute("book_list");
				if (list != null) {
					for (int i = 0; i < list.size(); i++) {
						Book book = (Book) list.get(i);
				%>
				<tr>
					<td><a href="<%=request.getContextPath()%>/detail?isbn=<%=book.getIsbn()%>&cmd=detail"><%=book.getIsbn()%></a></td>
					<td><%=book.getTitle()%></td>
					<td><%=format.moneyFormat(book.getPrice())%></td>
				</tr>
				<%
					}
				} else {
				}
				%>
			</table>
			</div>

			<!-- フッター部分 -->
			<%@ include file="/common/footer.jsp" %>

		</div>
	</body>
</html>