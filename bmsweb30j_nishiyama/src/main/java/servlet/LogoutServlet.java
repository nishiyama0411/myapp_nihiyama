/*
 * プログラミング名：LogoutServlet
 * プログラムの説明：ログアウト処理用サーブレット
 * 作成者：西山拓人
 * 作成日：2024年5月15日
 */

package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

	public void doGet(HttpServletRequest request ,HttpServletResponse response)
			throws ServletException ,IOException {

		// セッションを受け取る
		HttpSession session = request.getSession();
		// セッションをクリア
		session.invalidate();

		String logout = "logout";

		// リクエストスコープ
		request.setAttribute("message", "ログアウトしました");
		request.setAttribute("logout",logout);
		// loginに
		request.getRequestDispatcher("/view/login.jsp").forward(request, response);

	}
}
