<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.ArrayList,bean.Order,bean.Book,util.MyFormat"%>

<%
ArrayList<Book> bookList = (ArrayList<Book>) request.getAttribute("book_list");
ArrayList<Order> orderList = (ArrayList<Order>) session.getAttribute("order_list");
MyFormat format = new MyFormat();
int total = 0;
%>

<html>
<head>
<title>カート内容</title>
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
						<li><a href="<%=request.getContextPath()%>/list">[書籍一覧]</a></li>
					</ul>
				</div>

				<!-- ページタイトル -->
				<div id="page_title">
					<h2>カート内容</h2>
				</div>
			</div>
		</div>

		<!-- コンテンツ部分 -->
		<div id="main" class="container">
			<table class="list-table">
				<thead>
					<tr>
						<th>ISBN</th>
						<th>TITLE</th>
						<th>価格</th>
						<th>購入数</th>
						<th>削除</th>
					</tr>
				</thead>
				<tr>
					<%
					if (bookList != null) {
						for (int i = 0; i < bookList.size(); i++) {
							Book book = (Book) bookList.get(i);
							Order order = (Order) orderList.get(i);
							total += book.getPrice() * order.getQuantity();
					%>
				
				<tr>
					<td><a
						href="<%=request.getContextPath()%>/detail?isbn=<%=book.getIsbn()%>&cmd=detail"><%=book.getIsbn()%></a></td>
					<td><%=book.getTitle()%></td>
					<td><%=format.moneyFormat(book.getPrice())%></td>
					<td><%=order.getQuantity()%></td>
					<td><a
						href="<%=request.getContextPath()%>/showCart?delno=<%=i%>">削除</a>
					</td>

				</tr>
				<%
					}
				}
				%>
				</tr>

			</table>

			<hr class="double">

			<table>
				<tr>
					<br>
					<br>
					<th>合計</th>
					<td><%=format.moneyFormat(total)%></td>
				</tr>
			</table>
			<div class="right">
				<a href="<%=request.getContextPath()%>/buyConfirm"> <input
					type="submit" value="購入"></input></a>
			</div>
		</div>
		<!-- フッター部分 -->
		<%@ include file="/common/footer.jsp"%>

	</div>
</body>
</html>