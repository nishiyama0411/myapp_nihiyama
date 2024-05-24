/*
 * プログラミング名：InsertIntoCartServlet
 * プログラムの説明：カート追加処理用サーブレット
 * 作成者：佐田快斗
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

@WebServlet("/insertIntoCart")
public class InsertIntoCartServlet extends HttpServlet {

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String error = "";
		String cmd = "list";

		try {
			// セッションスコープの保存領域を確保
			HttpSession session = request.getSession();

			// ユーザー情報の取得
			User user = (User) session.getAttribute("user");

			//DTOオブジェクト宣言
			Book book = new Book();

			//DAOオブジェクト宣言
			BookDAO bookDao = new BookDAO();

			//セッション切れか確認
			if (user == null) {
				//セッション切れならerror.jspへフォワード
				error = "セッション切れの為、カートに追加出来ません。";
				cmd = "logout";
			} else {
				// 選択されたisbnの情報を取得
				String isbn = request.getParameter("isbn");

				int quan = 0;

				if (request.getParameter("quantity").equals("")) {
					error = "注文数を入力してください！";
				} else {
					// 入力された購入数を取得
					quan = Integer.parseInt(request.getParameter("quantity"));

					if (quan <= 0) {
						error = "注文数が不正です！<br>1つ以上を入力してください！";
					} else {
						// isbnから関連するデータを取得
						book = bookDao.selectByIsbn(isbn);
						// 入力した値が存在しない場合のエラー文
						if(book.getIsbn() == null){
							error = "カートに追加する対象の書籍が存在しない為、カート追加処理は行えませんでした。";
						} else {
							// リクエストスコープ
							request.setAttribute("book", book);

							// インスタンス化
							Order order = new Order();

							// orderに各情報をセット
							order.setIsbn(isbn);
							order.setUserid(user.getUserid());
							order.setQuantity(quan);

							// listに注文情報を格納
							ArrayList<Order> list = (ArrayList<Order>) session.getAttribute("order_list");

							// 情報がない場合初期化
							if (list == null) {
								list = new ArrayList<Order>();
							}

							// listにorder情報を格納
							list.add(order);

							request.setAttribute("quantity",quan);
							session.setAttribute("order_list", list);
						}
					}
				}
			}

		} catch (NumberFormatException e) {
			error = "数値以外が入力されました!<br>適切な値を入力してください!";
		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、カートに追加は出来ません。";
			cmd = "logout";
		} catch (Exception e) {
			error = "予期せぬエラーが発生しました。<br>" + e;
			;
		} finally {
			if (error.equals("")) {
				// フォワードの実行
				request.getRequestDispatcher("/view/insertIntoCart.jsp").forward(request, response);
			} else {
				// リクエストスコープ
				request.setAttribute("error", error);
				request.setAttribute("cmd", cmd);
				// フォワード先の指定
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			}
		}
	}
}