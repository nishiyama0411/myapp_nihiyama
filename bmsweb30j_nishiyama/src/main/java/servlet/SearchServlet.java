/*
 * プログラミング名：SearchServlet
 * プログラムの説明：検索処理用サーブレット
 * 作成者：西山拓人
 * 作成日：2024年5月15日
 */

package servlet;

import java.io.IOException;
import java.util.ArrayList;

import bean.Book;
import dao.BookDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {

	public void doGet(HttpServletRequest request ,HttpServletResponse response)
			throws ServletException ,IOException {

		String error = "";
		String cmd = "";

		try {
			// ArrayListを初期化
			ArrayList<Book> bookList = new ArrayList<Book>();

			//パラメータの取得
			String isbn = request.getParameter("isbn");
			String title = request.getParameter("title");
			String price = request.getParameter("price");

			//DAOオブジェクト宣言
			BookDAO objDAO = new BookDAO();

			bookList = objDAO.search(isbn,title,price);

			// list.jspにフォワード
			request.setAttribute("book_list", bookList);

		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、一覧表示は行えませんでした。";
			cmd = "logout";
		} catch (Exception e){
			error ="予期せぬエラーが発生しました。<br>"+e;
		} finally {
			if (error.equals("")) {
				//フォワードの実行
				request.getRequestDispatcher("/view/list.jsp").forward(request,response);
			} else {
				// error.jspにフォワード
				request.setAttribute("error",error);
				request.setAttribute("cmd",cmd);
				//フォワード先の指定
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			}
		}
	}
}
