<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.ArrayList,util.MyFormat,bean.Book,servlet.InsertIntoCartServlet"%>

<%
MyFormat format = new MyFormat();
Book book = (Book) request.getAttribute("book");
int quan = (int) request.getAttribute("quantity");
ArrayList<Book> list = (ArrayList<Book>) request.getAttribute("book_list");
%>


<html>
	<head>
		<title>カート追加</title>
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
						<h2>カート追加</h2>
					</div>
				</div>
			</div>

			<!-- コンテンツ部分 -->
			<div id="main" class="container">
				
				<h3>下記の書籍をカートに追加しました。</h3><br><br>
				
				<table  class="insertToCart-table-table">
					<tr>
						<th>ISBN</th>
						<td><%=book.getIsbn()%></td>
					</tr>
					<tr>
						<th>TITLE</th>
						<td><%=book.getTitle()%></td>
					</tr>
					<tr>
						<th>価格</th>
						<td><%=format.moneyFormat(book.getPrice())%></td>
					</tr>
					<tr>
						<th>購入数</th>
						<td><%=quan%>冊</td>
					</tr>
				</table>

				<a
				href="<%=request.getContextPath()%>/showCart"> <input
					type="submit" value="カート確認"></input>
				</a>
			</div>
			<!-- フッター部分 -->
			<%@ include file="/common/footer.jsp" %>

		</div>
	</body>
</html>