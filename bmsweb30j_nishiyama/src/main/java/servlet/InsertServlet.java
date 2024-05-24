/*
 * プログラミング名：InsertServlet
 * プログラムの説明：登録処理用サーブレット
 * 作成者：西山拓人
 * 作成日：2024年5月15日
 */

package servlet;

import java.io.IOException;

import bean.Book;
import dao.BookDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/insert")
public class InsertServlet extends HttpServlet {

	public void doGet(HttpServletRequest request ,HttpServletResponse response)
			throws ServletException ,IOException {

		String error = "";
		String cmd = "list";

		try {
			// BookDAOオブジェクトを生成
			BookDAO objDAO = new BookDAO();

			// Bookオブジェクトを生成
			Book book = new Book();

			// 入力情報が不正な場合のエラー文
			if (request.getParameter("isbn").isEmpty()) {
				error = "ISBNが未入力の為、書籍登録処理は行えませんでした。";
			} else if (request.getParameter("title").isEmpty()) {
				error = "タイトルが未入力の為、書籍登録処理は行えませんでした。";
			} else if (request.getParameter("price").isEmpty()) {
				error = "価格が未入力の為、書籍登録処理は行えませんでした。";
			} else {
				// isbnを用いてデータベースの情報をbookに格納する
				book = objDAO.selectByIsbn(request.getParameter("isbn"));

				// 入力した値が既に存在する場合のエラー文
				if(book.getIsbn() != null){
					error = "入力ISBNは既に登録済みの為、書籍登録処理は行えませんでした。";
				} else {
					// isbnをセット
					book.setIsbn(request.getParameter("isbn"));
					// タイトルをセット
					book.setTitle(request.getParameter("title"));
					// 価格をセット
					book.setPrice(Integer.parseInt(request.getParameter("price")));
					// insetメソッドを呼び出す
					objDAO.insert(book);
				}
			}
		} catch (NumberFormatException e) {
			error = "価格の値が不正の為、書籍登録処理は行えませんでした。";
		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、書籍登録処理は行えませんでした。";
			cmd = "logout";
		} catch (Exception e) {
			error = "予期せぬエラーが発生しました。";
		} finally {
			if (error.equals("")) {
				//フォワード先の指定
				request.getRequestDispatcher("/list").forward(request, response);
			} else {
				request.setAttribute("error",error);
				request.setAttribute("cmd",cmd);
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			}
		}
	}
}
