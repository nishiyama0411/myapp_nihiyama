<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="bean.Book,util.MyFormat"%>

<%
	Book book = (Book) request.getAttribute("book");
	MyFormat format = new MyFormat();
%>

<html>
	<head>
		<title>書籍変更</title>
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
							<li><a href ="<%=request.getContextPath()%>/view/insert.jsp">[書籍登録]</a></li>
							<li><a href ="<%=request.getContextPath()%>/list">[書籍一覧]</a></li>
						</ul>
					</div>

					<!-- ページタイトル -->
					<div id="page_title">
						<h2>書籍変更</h2>
					</div>
				</div>
			</div>

			<!-- 書籍変更コンテンツ部分 -->
			<div id="main" class="container">
				<!-- 変更画面 -->
				<form action="<%=request.getContextPath()%>/update">
					<table class="update-table">

							<tr>
								<th class="white"></th>
								<th class="white">&lt;&lt;変更前情報&gt;&gt;</th>
								<th class="white">&lt;&lt;変更後情報&gt;&gt;</th>
							</tr>


							<tr>
								<th>ISBN</th>
								<td class="white"><%=book.getIsbn() %></td>
								<td class="white"><%=book.getIsbn() %></td>
							</tr>
							<tr>
								<th>TITLE</th>
								<td class="white"><%=book.getTitle() %></td>
								<td class="white"><input type="text" name="title"></td>
							</tr>
							<tr>
								<th>価格</th>
								<td class="white"><%=format.moneyFormat(book.getPrice())%></td>
								<td class="white"><input type="text" name="price"></td>
							</tr>
						</tbody>
					</table>
					<input type="hidden" name="isbn" value="<%=book.getIsbn() %>">

					<input type="submit" value="変更完了">
				</form>
			</div>

			<!-- フッター部分 -->
			<%@ include file="/common/footer.jsp" %>

		</div>

	</body>
</html>
