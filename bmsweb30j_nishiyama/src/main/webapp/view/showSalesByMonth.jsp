<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.ArrayList,bean.Sale"%>
<%
ArrayList<Sale> list = (ArrayList<Sale>) request.getAttribute("sale_list");
int total = 0;
String dispDate = (String) request.getAttribute("dispDate");
%>
<html>
<head>
<title>売上げ状況</title>
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
					<h2><%=dispDate %>売上げ状況</h2>
				</div>
			</div>
		</div>

		<!-- コンテンツ部分 -->
		<div id="main" class="container">
			<!-- 検索フォーム -->
			<div class="box-left">
			<form action="<%=request.getContextPath()%>/showSalesByMonth">
				<td>年 <input type="text" name="year"></td>
				<td>月 <input type="text" name="month"></td>
				<td><input type="submit" value="検索"></td>
			</form>
			</div>
			<table>
				<tr>
					<th>ISBN</th>
					<th>Title</th>
					<th>価格</th>
					<th>数量</th>
					<th>売上げ小計</th>
				</tr>
				<%
				// listがnullでない場合に一覧表示
				if (list != null && list.size() != 0) {
					for (int i = 0; i < list.size(); i++) {
						Sale sale = (Sale) list.get(i);
						total += sale.getPrice() * sale.getQuantity();
				%>
				<tr>
					<td><%=sale.getIsbn()%></td>
					<td><%=sale.getTitle()%></td>
					<td><%=sale.getPrice()%></td>
					<td><%=sale.getQuantity()%></td>
					<td><%=sale.getPrice() * sale.getQuantity()%></td>
				</tr>
				<%
				}
				%>
			</table>
			<hr class="double" noshade>
			
			<div class="right">
			<table>
				<tr>
					<br>
					<th>合計</th>
					<td>&nbsp;</td>
					<td><%=total%>円</td>
				</tr>
			</table>
			</div>
		</div>

		<%
		}
		%>

		<!-- フッター部分 -->
		<%@ include file="/common/footer.jsp"%>

	</div>
</body>
</html>