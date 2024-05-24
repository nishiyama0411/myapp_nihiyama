/*
 * プログラミング名：ListUserServlet
 * プログラムの説明： ユーザー一覧表示用サーブレット
 * 作成者：西山拓人
 * 作成日：2024年5月20日
 */

package servlet;

import java.io.IOException;
import java.util.ArrayList;

import bean.User;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/listUser")
public class ListUserServlet extends HttpServlet {

	public void doGet(HttpServletRequest request ,HttpServletResponse response)
			throws ServletException ,IOException {

		String error = "";
		String cmd = "";

		try {
			// セッションスコープの保存領域を確保
			HttpSession session = request.getSession();

			// ユーザー情報の取得
			User user = (User) session.getAttribute("user");

			//セッション切れか確認
			if (user == null) {
				//セッション切れならerror.jspへフォワード
				error = "セッション切れの為、ユーザ一覧表示は行えません。";
				cmd = "logout";
				return;
			}

			// 変数だけ宣言
			ArrayList<User> userList;

			// インスタンス化
			UserDAO userDao = new UserDAO();

			// 検索データを取得
			String searchUserid = request.getParameter("searchUserid");

			// データがnullの時、全検索。そうでないとき、ユーザーIDを元に検索
			if(searchUserid == null){
				userList = userDao.selectAll();
			} else {
				userList = userDao.search(searchUserid);
			}

			// リクエストスコープ
			request.setAttribute("user_list", userList);
		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、ユーザ一覧は表示出来ません。";
			cmd = "logout";
		} catch (Exception e) {
			error = "予期せぬエラーが発生しました。<br>"+e;;
		} finally {
			if (error.equals("")) {
				//フォワードの実行
				request.getRequestDispatcher("/view/listUser.jsp").forward(request, response);
			} else {
				request.setAttribute("error",error);
				request.setAttribute("cmd",cmd);
				//フォワード先の指定
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			}
		}
	}
}
