<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="bean.User"%>

<div id="userInfo">
	<%
//セッションからユーザー情報を取得
User user = (User) session.getAttribute("user");
//セッション切れか確認
if (user == null) {
	//セッション切れならerror.jspへフォワード
	request.setAttribute("error", "セッション切れの為、メニュー画面が表示できませんでした。");
	request.setAttribute("cmd", "logout");
	request.getRequestDispatcher("/view/error.jsp").forward(request, response);
	return;
}

String auth = "";
if (user.getAuthority().equals("1")) {
	auth = "一般ユーザー";
} else if (user.getAuthority().equals("2")){
	auth = "管理者";
}
%>
	<p class="txt">
		名前：<%=user.getUserid()%>
		<br> 権限：<%=auth %>
	</p>
</div>