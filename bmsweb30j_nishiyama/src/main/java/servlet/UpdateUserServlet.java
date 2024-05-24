/*
 * プログラミング名：UpdateUserServlet
 * プログラムの説明：ユーザー更新処理用サーブレット
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

@WebServlet("/updateUser")
public class UpdateUserServlet extends HttpServlet {

	public void doGet(HttpServletRequest request ,HttpServletResponse response)
			throws ServletException ,IOException {

		String error = "";
		String cmd = "listUser";

		try {
			/* ユーザー情報の取得 */
			String userid = request.getParameter("user");
			String pass1 = request.getParameter("pass1");
			String pass2 = request.getParameter("pass2");
			String email = request.getParameter("email");
			String auth = request.getParameter("auth");

			// インスタンス化
			UserDAO objUserDao = new UserDAO();
			User objUser = objUserDao.selectByUser(userid);

			if (pass1.equals("")) {
				error = "パスワード入力値不正の為、変更できません。";
			} else if (pass2.equals("")) {
				error = "パスワード(確認用)入力値不正の為、変更できません。";
			} else if (email.equals("")) {
				error = "Ｅメール入力値不正の為、変更できません。";
			} else if (auth == null) {
				error = "権限が未選択の為、変更できません。";
			} else if (!pass1.equals(pass2)) {
				error = "入力パスワードがパスワード(確認用)と一致しない為、変更できません。";
			} else {
				// ユーザー情報を設定する
				objUser.setUserid(userid);
				objUser.setPassword(pass1);
				objUser.setEmail(email);
				objUser.setAuthority(auth);
				// 更新する
				int count = objUserDao.update(objUser);
				if (count == 0) {
					error = "クエリ発行に失敗しました。";
					cmd = "logout";
				}
			}
		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、ユーザー変更は行えません。";
			cmd = "logout";
		} catch (Exception e) {
			error = "予期せぬエラーが発生しました。<br>"+e;
		} finally {
			if (error.equals("")) {
				//フォワードの実行
				request.getRequestDispatcher("/listUser").forward(request, response);
			} else {
				request.setAttribute("error",error);
				request.setAttribute("cmd",cmd);
				//フォワード先の指定
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			}
		}
	}
}
