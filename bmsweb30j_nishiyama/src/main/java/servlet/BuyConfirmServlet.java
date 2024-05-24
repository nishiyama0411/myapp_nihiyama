/*
 * プログラミング名：BuyConfirmServlet
 * プログラムの説明：注文登録・メール送信用サーブレット
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
import dao.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import util.SendMail;

@WebServlet("/buyConfirm")
public class BuyConfirmServlet extends HttpServlet {

	@SuppressWarnings("unchecked")
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
				error = "セッション切れの為、購入は出来ません。";
				cmd = "logout";
			} else {
				// 注文情報の取得
				ArrayList<Order> orderList = (ArrayList<Order>) session.getAttribute("order_list");

				/* インスタンス化 */
				BookDAO objBookDao = new BookDAO();
				OrderDAO objOrderDao = new OrderDAO();
				Book book = null;
				ArrayList<Book> list = new ArrayList<Book>();

				// 注文情報を元に、格納する
				if (orderList != null && orderList.size() != 0) {
					for (int i = 0; i < orderList.size(); i++) {
						Order order = orderList.get(i);

						book = objBookDao.selectByIsbn(order.getIsbn());
						if (book.getIsbn() == null) {
							error = "購入対象の書籍が存在しない為、購入できませんでした。";
							return;
						}
						objOrderDao.insert(order);
						list.add(book);
					}

					// メール送信
					SendMail.sendMail(user,list);

					// スコープ処理
					request.setAttribute("book_list",list);
					request.setAttribute("order_list",orderList);
					session.setAttribute("order_list",null);
				} else {
					error = "カートの中に何も無かったので購入は出来ません。";
					cmd = "menu";
				}
			}
		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、購入は出来ません。";
			cmd = "logout";
		} catch (Exception e) {
			error = "予期せぬエラーが発生しました。<br>"+e;;
		} finally {
			if (error.equals("")) {
				//フォワードの実行
				request.getRequestDispatcher("/view/buyConfirm.jsp").forward(request, response);
			} else {
				request.setAttribute("error",error);
				request.setAttribute("cmd",cmd);
				//フォワード先の指定
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			}
		}
	}
}
