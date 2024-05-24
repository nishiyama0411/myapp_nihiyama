/*
 * プログラミング名：DetailServlet
 * プログラムの説明：書籍データの詳細を表示するサーブレット
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

@WebServlet("/detail")
public class DetailServlet extends HttpServlet {
	public void doGet(HttpServletRequest request ,HttpServletResponse response) throws ServletException ,IOException{

		String error = "";
		// 送信されたISBN情報を受け取る
		String isbn = request.getParameter("isbn");
		// 送信されたcmd情報を受け取る
		String cmd = request.getParameter("cmd");

		try {
			// BookDAOオブジェクトを生成
			BookDAO objDAO = new BookDAO();

			// Bookオブジェクトを生成し、selectByIsbnメソッドの戻り値を代入
			Book book = objDAO.selectByIsbn(isbn);

			// 指定されたIsbnがデータにない場合、エラーを起こす処理
			if(book.getIsbn() == null){
				if (cmd.equals("detail")) {
					error = "表示対象の書籍が存在しない為、詳細情報は表示できませんでした。";
				} else if (cmd.equals("update")) {
					error = "更新対象の書籍が存在しない為、変更画面は表示できませんでした。";
				}
				cmd = "list";
			} else {
				// setIsbnメソッドを呼び出す
				book.setIsbn(isbn);
				// リクエストスコープに登録する
				request.setAttribute("book",book);
			}
		} catch (IllegalStateException e) {
			if (cmd.equals("detail")) {
				error = "DB接続エラーの為、書籍詳細は表示できませんでした。";
			} else if (cmd.equals("update")) {
				error = "DB接続エラーの為、変更画面は表示出来ませんでした。";
			}
			cmd = "logout";
		} finally {
			if (error.equals("")) {
				if(cmd.equals("detail")) {
					//フォワードの実行
					request.getRequestDispatcher("/view/detail.jsp").forward(request,response);
				} else if(cmd.equals("update")) {
					//フォワードの実行
					request.getRequestDispatcher("/view/update.jsp").forward(request,response);
				}
			} else {
				// リクエストスコープに登録する
				request.setAttribute("error",error);
				request.setAttribute("cmd",cmd);
				//フォワード先の指定
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			}
		}
	}
}