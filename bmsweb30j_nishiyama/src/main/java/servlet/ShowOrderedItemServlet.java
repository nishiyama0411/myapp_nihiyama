/*
 * プログラミング名：ShowOrderedItemServlet
 * プログラムの説明：購入状況表示処理用サーブレット
 * 作成者：西山拓人
 * 作成日：2024年5月15日
 */

package servlet;

import java.io.IOException;
import java.util.ArrayList;

import bean.OrderedItem;
import dao.OrderedItemDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/showOrderedItem")
public class ShowOrderedItemServlet extends HttpServlet {

	public void doGet(HttpServletRequest request ,HttpServletResponse response)
			throws ServletException ,IOException {

		String error = "";
		String cmd = "list";

		try {
			/* インスタンス化 */
			OrderedItemDAO objDao = new OrderedItemDAO();
			
			ArrayList<OrderedItem> list = objDao.selectAll();

			// リクエストスコープ
			request.setAttribute("ordered_list",list);

		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、購入状況確認は出来ません。";
			cmd = "logout";
		} catch (Exception e) {
			error = "予期せぬエラーが発生しました。<br>"+e;;
		} finally {
			if (error.equals("")) {
				//フォワードの実行
				request.getRequestDispatcher("/view/showOrderedItem.jsp").forward(request, response);
			} else {
				// リクエストスコープ
				request.setAttribute("error",error);
				request.setAttribute("cmd",cmd);
				//フォワード先の指定
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			}
		}

	}
}
