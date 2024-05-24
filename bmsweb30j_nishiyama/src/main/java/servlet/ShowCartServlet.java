/*
 * プログラミング名：ShowCartServlet
 * プログラムの説明：カート確認処理用サーブレット
 * 作成者：西山拓人
 * 作成日：2024年5月15日
 */

package servlet;

import java.io.IOException;
import java.util.ArrayList;

import bean.Book;
import bean.Order;
import bean.User;
import dao.BookDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/showCart")
public class ShowCartServlet extends HttpServlet {

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
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
				error = "セッション切れの為、カート状況は確認出来ません。";
				cmd = "logout";
			} else {

				// delnoパラメータ情報の取得
				String delno = request.getParameter("delno");

				// 注文情報の取得
				ArrayList<Order> orderList = (ArrayList<Order>) session.getAttribute("order_list");

				// delnoがnullでない場合、その行にあるデータを削除
				if (delno != null) {
					if (orderList.size() != 0) {
						orderList.remove(Integer.parseInt(delno));
					}
				}

				/* インスタンス化 */
				BookDAO BookDaoObj = new BookDAO();
				Book book = null;
				ArrayList<Book> list = new ArrayList<Book>();

				// 注文情報がある場合
				if (orderList != null) {
					// 注文情報を格納
					for (int i = 0; i < orderList.size(); i++) {
						Order order = orderList.get(i);

						book = BookDaoObj.selectByIsbn(order.getIsbn());
						list.add(book);
					}
				}

				// リクエストスコープ
				request.setAttribute("book_list",list);
			}
		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、カート状況は確認出来ません。";
			cmd = "logout";
		} catch (Exception e) {
			error = "予期せぬエラーが発生しました。<br>"+e;;
		} finally {
			if (error.equals("")) {
				// フォワードの実行
				request.getRequestDispatcher("/view/showCart.jsp").forward(request, response);
			} else {
				// リクエストスコープ
				request.setAttribute("error",error);
				request.setAttribute("cmd",cmd);
				// フォワード先の指定
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			}
		}
	}
}
