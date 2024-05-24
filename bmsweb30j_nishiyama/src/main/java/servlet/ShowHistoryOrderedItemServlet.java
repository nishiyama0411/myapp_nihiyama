/*
 * プログラミング名：ShowHistoryOrderedItemServlet
 * プログラムの説明：購入履歴表示処理用サーブレット
 * 作成者：西山拓人
 * 作成日：2024年5月20日
 */

package servlet;

import java.io.IOException;
import java.util.ArrayList;

import bean.OrderedItem;
import bean.User;
import dao.OrderedItemDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/showHistoryOrderedItem")
public class ShowHistoryOrderedItemServlet extends HttpServlet {

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
				error = "セッション切れの為、購入履歴の確認は出来ません。";
				cmd = "logout";
			} else {
				// インスタンス化
				OrderedItemDAO objDao = new OrderedItemDAO();

				// 注文情報の取得
				ArrayList<OrderedItem> list = objDao.selectByUser(user.getUserid());

				// スコープ処理
				request.setAttribute("ordered_list",list);
			}
		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、購入履歴の確認は出来ません。";
			cmd = "logout";
		} catch (Exception e) {
			error = "予期せぬエラーが発生しました。<br>"+e;;
		} finally {
			if (error.equals("")) {
				//フォワードの実行
				request.getRequestDispatcher("/view/showHistoryOrderedItem.jsp").forward(request, response);
			} else {
				request.setAttribute("error",error);
				request.setAttribute("cmd",cmd);
				//フォワード先の指定
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			}
		}
	}
}
