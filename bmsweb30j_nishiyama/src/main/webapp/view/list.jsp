<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.ArrayList,bean.Book,bean.User,util.MyFormat"%>
<%
ArrayList<Book> bookList = (ArrayList<Book>) request.getAttribute("book_list");
MyFormat format = new MyFormat();
// ユーザー情報の取得
User user = (User) session.getAttribute("user");
//セッション切れか確認
if (user == null) {
	//セッション切れならerror.jspへフォワード
	request.setAttribute("error", "セッション切れの為、一覧画面が表示できませんでした。");
	request.setAttribute("cmd", "logout");
	request.getRequestDispatcher("/view/error.jsp").forward(request, response);
	return;
}
%>

<html>
<head>
<title>書籍一覧</title>
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
						<%
						if (user.getAuthority().equals("1")) {
						%>
						<li><a href="<%=request.getContextPath()%>/showCart">[カート状況]</a></li>
						<%
						} else if (user.getAuthority().equals("2")) {
						%>
						<li><a href="<%=request.getContextPath()%>/view/insert.jsp">[書籍登録]</a></li>
						<%
						}
						%>
					</ul>
				</div>

				<!-- ページタイトル -->
				<div id="page_title">
					<h2>書籍一覧</h2>
				</div>
			</div>
		</div>

		<!-- 書籍一覧のコンテンツ部分 -->
		<div id="main" class="container">

			<!-- 検索フォーム -->
			<form class="inline-block"
				action="<%=request.getContextPath()%>/search">
				ISBN <input type="text" name="isbn"> TITLE <input
					type="text" name="title"> 価格 <input type="text"
					name="price"> <input type="submit" value="検索">
			</form>
			<form class="inline-block"
				action="<%=request.getContextPath()%>/list">
				<input type="submit" value="全件表示">
			</form>

			<!-- 書籍情報リスト -->
			<table class="list-table">
				<thead>
					<tr>
						<th>ISBN</th>
						<th>TITLE</th>
						<th>価格</th>
						<%
						if (user.getAuthority().equals("1")) {
						%>
						<th>購入数</th>
						<%
						} else if (user.getAuthority().equals("2")) {
						%>
						<th>変更 / 削除</th>
						<%
						}
						%>

					</tr>
				</thead>
				<tbody>
					<%
					if (bookList != null) {
						for (Book book : bookList) {
					%>
					<tr>
						<td><a
							href="<%=request.getContextPath()%>/detail?isbn=<%=book.getIsbn()%>&cmd=detail"><%=book.getIsbn()%></a></td>
						<td><%=book.getTitle()%></td>
						<td><%=format.moneyFormat(book.getPrice())%></td>
						<td>
							<%
							if (user.getAuthority().equals("1")) {
							%>
							<form
								action="<%=request.getContextPath()%>/insertIntoCart">
								<input type="hidden" name="isbn" value=<%=book.getIsbn()%>>
								<input type="text" name="quantity"> 
								<input type="submit" value="カートに入れる">
							</form>
							<%
							} else if (user.getAuthority().equals("2")) {
							%>
							<a
							href="<%=request.getContextPath()%>/detail?isbn=<%=book.getIsbn()%>&cmd=update">変更</a>
							<a
							href="<%=request.getContextPath()%>/delete?isbn=<%=book.getIsbn()%>">削除</a>

							<%
							}
							%>
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