/*
 * プログラミング名：InsertIniDataServlet
 * プログラムの説明：初期データ登録をするサーブレット
 * 作成者：西山拓人
 * 作成日：2024年5月15日
 */
package servlet;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.Book;
import dao.BookDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.FileIn;
@WebServlet("/insertIniData")
public class InsertIniDataServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String error = "";
		String cmd = "";
		FileIn file = new FileIn();
		boolean success = true;
		Connection conn = null;

		try {
			BookDAO BookDaoObj = new BookDAO();
			conn = BookDAO.getConnection();
			conn.setAutoCommit(false); // トランザクションを開始
			ArrayList<Book> list = BookDaoObj.selectAll();

			// データベースにデータがあるかを確認
			if (list.size() != 0) {
				error = "DBにはデータが存在するので、初期データは登録できません。";
				cmd = "menu";
			} else {
				// csvファイルのパスを取得
				String path = getServletContext().getRealPath("file/initial_data.csv");
				Book book = null;

				// ファイルを開く
				if (file.open(path)) {
					String line;
					// FileInクラスから文字列を一行分取得し、nullまでループ
					while ((line = file.readLine()) != null) {
						// String配列にカンマ区切りで格納
						String[] tokens = line.split(",");
						// カンマが3つの時の処理
						if (tokens.length == 3) {
							try {
								book = new Book();
								book.setIsbn(tokens[0]);
								book.setTitle(tokens[1]);
								book.setPrice(Integer.parseInt(tokens[2]));
								list.add(book);
							} catch (NumberFormatException e) {
								success = false;
								error = "初期データファイルに不備がある為、登録は行えません。";
								cmd = "menu";
								break;
							}
						} else {
							success = false;
							error = "初期データファイルに不備がある為、登録は行えません。";
							cmd = "menu";
							break;
						}
					}

					// ファイルを閉じる
					file.close();

					// 初期データファイルに不備が無かった場合の処理
					if (success) {
						// listの情報を全て格納
						for (Book b : list) {
							BookDaoObj.insert(b);
						}
						conn.commit(); // トランザクションをコミット
						request.setAttribute("book_list", list);
					} else {
						conn.rollback(); // エラーが発生した場合、トランザクションをロールバック
					}
				} else {
					error = "初期データファイルが無い為、登録は行えません。";
					cmd = "menu";
				}
			}
		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、初期データ登録は行えません。";
			cmd = "logout";
		} catch (SQLException e) {
			if (conn != null) {
				try {
					conn.rollback(); // エラーが発生した場合、トランザクションをロールバック
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		} finally {
			if (conn != null) {
				try {
					conn.setAutoCommit(true); // トランザクションを終了
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (error.equals("")) {
				//フォワードの実行
				request.getRequestDispatcher("/view/insertIniData.jsp").forward(request, response);
			} else {
				// リクエストスコープ
				request.setAttribute("error", error);
				request.setAttribute("cmd", cmd);
				//フォワード先の指定
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			}
		}
	}
}