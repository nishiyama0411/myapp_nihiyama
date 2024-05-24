/*
 * プログラミング名：DeleteUserServlet
 * プログラムの説明：ユーザー削除機能用サーブレット
 * 
 * 作成日：2024年5月20日
 */

package servlet;

import java.io.IOException;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/deleteUser")
public class DeleteUserServlet extends HttpServlet {
	public void doGet(HttpServletRequest request ,HttpServletResponse response)
			throws ServletException ,IOException {

		String error = "";
		String cmd = "logout";
		
		String userid = (String) request.getParameter("user");

		try {
			// インスタンス化
			UserDAO objDao = new UserDAO();
			
			// 削除
			int count = objDao.delete(userid);
			
			if (count == 0) {
				error = "クエリ発行に失敗しました。";
			}
		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、ユーザー削除は行えません。";
		} catch (Exception e) {
			error = "予期せぬエラーが発生しました。<br>"+e;;
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
