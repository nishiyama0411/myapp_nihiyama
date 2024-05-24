/*
 * プログラミング名：listServlet
 * プログラムの説明：書籍データを一覧表示するサーブレット
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

@WebServlet("/list")
public class ListServlet extends HttpServlet {

	public void doGet(HttpServletRequest request ,HttpServletResponse response)
			throws ServletException ,IOException {

		String error = "";
		String url = "";
		String cmd ="";

		try {
			// ArrayListを初期化
			ArrayList<Book> bookList = new ArrayList<Book>();

			// BookDAOオブジェクトを生成
			BookDAO objDAO = new BookDAO();

			// book_ListにselectAllメソッドの戻り値を代入
			bookList = objDAO.selectAll();

			// list.jspにフォワード
			request.setAttribute("book_list", bookList);

		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、一覧表示は行えませんでした。";
			// urlにサーブレットパスを代入
			url = request.getServletPath();
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
				request.setAttribute("url",url);
				request.setAttribute("cmd",cmd);
				//フォワード先の指定
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			}
		}
	}
}
