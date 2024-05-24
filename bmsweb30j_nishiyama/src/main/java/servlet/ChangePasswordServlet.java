/*
 * プログラミング名：ChangePasswordServlet
 * プログラムの説明：パスワード変更処理用サーブレット
 * 作成者：西山拓人
 * 作成日：2024年5月20日
 */

package servlet;

import java.io.IOException;

import bean.User;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/changePassword")
public class ChangePasswordServlet extends HttpServlet {

	public void doGet(HttpServletRequest request ,HttpServletResponse response)
			throws ServletException ,IOException {

		String error = "";
		String cmd = "menu";
		UserDAO objUserDao = new UserDAO();

		try {
			// セッションスコープの保存領域を確保
			HttpSession session = request.getSession();

			// ユーザー情報の取得
			User user = (User) session.getAttribute("user");

			//セッション切れか確認
			if (user == null) {
				//セッション切れならerror.jspへフォワード
				error = "セッション切れの為、メニュー画面が表示できませんでした。";
				cmd = "logout";
			} else {
				// 入力データを取得する
				String newPass1 = request.getParameter("newPass1");
				String newPass2 =request.getParameter("newPass2");
				String oldPass = request.getParameter("oldPass");

				// 入力された値に間違いがあった場合のエラー
				if (oldPass.equals("")) {
					error = "旧パスワードを入力して下さい！";
				} else if (newPass1.equals("")) {
					error = "新パスワードを入力して下さい！";
				} else if (newPass2.equals("")) {
					error = "新パスワード(確認用)を入力して下さい！";
				} else if (!oldPass.equals(user.getPassword())) {
					error = "旧パスワードが間違っています！";
				} else if (!newPass1.equals(newPass2)) {
					error = "新パスワードと確認パスワードが合っていません！";
				} else {
					// 値が正しいので、メソッドを呼び出して更新する
					int count = objUserDao.updateForPassword(user.getUserid(),newPass1);
					if (count == 0) {
						error = "クエリ発行に失敗しました。";
						cmd = "logout";
					} else {
						user.setPassword(newPass1);
						session.setAttribute("user",user);
					}
				}
			}
		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、パスワード変更は行えません。";
			cmd = "logout";
		} catch (Exception e) {
			error = "予期せぬエラーが発生しました。<br>"+e;;
		} finally {
			if (error.equals("")) {
				//フォワードの実行
				request.getRequestDispatcher("/view/menu.jsp").forward(request, response);
			} else {
				request.setAttribute("error",error);
				request.setAttribute("cmd",cmd);
				//フォワード先の指定
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			}
		}
	}
}
