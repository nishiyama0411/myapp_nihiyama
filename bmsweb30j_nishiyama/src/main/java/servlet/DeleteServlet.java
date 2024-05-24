/*
 * プログラミング名：DeleteServlet
 * プログラムの説明：書籍データを削除するサーブレット
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

@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {
	public void doGet(HttpServletRequest request ,HttpServletResponse response)
			throws ServletException ,IOException {
		String error = "";
		String cmd = "list";
		try {
			//BookDAOクラスのオブジェクトを生成
			BookDAO objDAO = new BookDAO();

			Book book = new Book();

			// 送信されたISBN情報を受け取る
			String isbn = request.getParameter("isbn");

			book = objDAO.selectByIsbn(isbn); 

			// 指定されたIsbnがデータにない場合、エラーを起こす処理
			if(book.getIsbn() == null){
				error = "削除対象の書籍が存在しない為、書籍削除処理は行えませんでした。";
			} else {
				// deleteメソッドを呼び出す
				objDAO.delete(isbn);
			}
		} catch (IllegalStateException e) {
			error ="DB接続エラーの為、書籍削除処理はできませんでした。";
			cmd = "logout";
		} catch (Exception e) {
			error ="予期せぬエラーが発生しました。<br>"+e;
		} finally {
			if (error.equals("")) {
				//フォワード先の指定
				request.getRequestDispatcher("/list").forward(request, response);
			} else {
				request.setAttribute("error", error);
				request.setAttribute("cmd", cmd);
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			}
		}
	}

}