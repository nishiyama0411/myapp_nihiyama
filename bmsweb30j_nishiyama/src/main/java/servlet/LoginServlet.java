/*
 * プログラミング名：LoginServlet
 * プログラムの説明：ログイン処理用サーブレット
 * 作成者：西山拓人
 * 作成日：2024年5月15日
 */

package servlet;

import java.io.IOException;

import bean.User;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String error = "";
		String cmd = "";

		// 送信されたUser情報を受け取る
		String userid = request.getParameter("userid");

		// 送信されたPassword情報を受け取る
		String password = request.getParameter("password");

		/* インスタンス化 */
		UserDAO userDaoObj = new UserDAO();
		User user = null;

		try {
			// 受け取ったパラメータを引数として戻り値を格納する
			user = userDaoObj.selectByUser(userid, password);

			// Useridに情報がある場合
			if (user.getUserid() != null) {
				// セッションスコープの保存領域を確保
				HttpSession session = request.getSession();

				// セッションスコープに保存
				session.setAttribute("user", user);

				//ユーザー用クッキーの生成
				Cookie userCookie = new Cookie("userid", userid);
				userCookie.setMaxAge(60 * 60 * 24 * 5);
				response.addCookie(userCookie);

				//パスワード用クッキーの生成
				Cookie passwordCookie = new Cookie("password", password);
				passwordCookie.setMaxAge(60 * 60 * 24 * 5);
				response.addCookie(passwordCookie);
			} else {
				// login.jspにフォワード
				request.setAttribute("message", "入力データが間違っています。");
			}
		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、ログインは出来ません。";
			cmd = "logout";
		} catch (Exception e){
			error ="予期せぬエラーが発生しました。<br>"+e;
		} finally {
			if (error.equals("")) {
				if (user.getUserid() != null) {
					//フォワードの実行
					request.getRequestDispatcher("/view/menu.jsp").forward(request, response);
				} else {
					//フォワードの実行
					request.getRequestDispatcher("/view/login.jsp").forward(request, response);
				}
			} else {
				// リクエストスコープ
				request.setAttribute("error", error);
				request.setAttribute("cmd", cmd);

				//フォワード先の指定
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);

			}
		}
	}
}
