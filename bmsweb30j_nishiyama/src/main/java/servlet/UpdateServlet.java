/*
 * プログラミング名：UpdateServlet
 * プログラムの説明：更新処理用サーブレット
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

@WebServlet("/update")
public class UpdateServlet extends HttpServlet {

	public void doGet(HttpServletRequest request ,HttpServletResponse response)
			throws ServletException ,IOException {

		String error = "";
		String cmd = "list";

		try{
			//DTOオブジェクト宣言
			Book book = new Book();

			//DAOオブジェクト宣言
			BookDAO bookDao = new BookDAO();

			// 入力された値が不正な場合のエラー文
			if (request.getParameter("title").equals("")) {
				error = "タイトルが未入力の為、書籍更新処理は行えませんでした。";
			} else if (request.getParameter("price").equals("")) {
				error = "価格が未入力の為、書籍更新処理は行えませんでした。";
			} else {
				// isbnを用いてデータベースの情報をbookに格納する
				book = bookDao.selectByIsbn(request.getParameter("isbn"));

				// 入力した値が存在しない場合のエラー文
				if(book.getIsbn() == null){
					error = "更新対象の書籍が存在しない為、書籍更新処理は行えませんでした。";
				} else {
					// 取得したデータをセット
					book.setIsbn(request.getParameter("isbn"));
					book.setTitle(request.getParameter("title"));
					book.setPrice(Integer.parseInt(request.getParameter("price")));

					//更新メソッドを呼び出し
					bookDao.update(book);
				}
			}
		} catch (NumberFormatException e) {
			error = "価格の値が不正の為、書籍更新処理は行えませんでした。";
		} catch (IllegalStateException e) {
			error ="DB接続エラーの為、書籍更新処理は行えませんでした。";
			cmd = "logout";
		} catch (Exception e){
			error ="予期せぬエラーが発生しました。<br>"+e;
		} finally {
			if (error.equals("")) {
				//フォワードの実行
				request.getRequestDispatcher("/list").forward(request,response);
			} else {
				// リクエストスコープ
				request.setAttribute("error", error);
				request.setAttribute("cmd", cmd);
				// フォワードの実行
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			}
		}
	}
}
