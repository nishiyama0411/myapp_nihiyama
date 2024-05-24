/*
 * プログラミング名：DetailUserServlet
 * プログラムの説明： ユーザー詳細表示用サーブレット
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

@WebServlet("/detailUser")
public class DetailUserServlet extends HttpServlet {

	public void doGet(HttpServletRequest request ,HttpServletResponse response)
			throws ServletException ,IOException {

		String error = "";
		
		// 送信されたユーザーID情報を受け取る
		String userid = request.getParameter("user");
		
		// 送信されたcmd情報を受け取る
		String cmd = request.getParameter("cmd");

		try {
			// インスタンス化
			UserDAO objDao = new UserDAO();

			// ユーザーIDを元に情報を検索
			User objUser = objDao.selectByUser(userid);

			// リクエストスコープ
			request.setAttribute("user", objUser);

		} catch (IllegalStateException e) {
			if (cmd.equals("detailUser")) {
				error = "DB接続エラーの為、ユーザーの詳細情報は表示出来ません。";
			} else if (cmd.equals("updateUser")) {
				error = "DB接続エラーの為、ユーザーの変更画面は表示出来ません。";
			}
			cmd = "logout";
		} catch (Exception e) {
			error = "予期せぬエラーが発生しました。<br>"+e;;
		} finally {
			if (error.equals("")) {
				if(cmd.equals("detailUser")) {
					//フォワードの実行
					request.getRequestDispatcher("/view/detailUser.jsp").forward(request,response);
				} else if(cmd.equals("updateUser")) {
					//フォワードの実行
					request.getRequestDispatcher("/view/updateUser.jsp").forward(request,response);
				}
			} else {
				request.setAttribute("error",error);
				request.setAttribute("cmd",cmd);
				//フォワード先の指定
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			}
		}
	}
}
