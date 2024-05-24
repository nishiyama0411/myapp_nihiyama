/*
 * プログラミング名：ShowSalesByMonthServlet
 * プログラムの説明：月別売上げ表示用サーブレット
 * 作成者：西山拓人
 * 作成日：2024年5月20日
 */

package servlet;

import java.io.IOException;
import java.util.ArrayList;

import bean.Sale;
import dao.SaleDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/showSalesByMonth")
public class ShowSalesByMonthServlet extends HttpServlet {

	public void doGet(HttpServletRequest request ,HttpServletResponse response)
			throws ServletException ,IOException {

		String error = "";
		String cmd = "";

		try {
			// 入力された年月を取得
			String year = request.getParameter("year");
			String month = request.getParameter("month");
			
			// インスタンス化
			SaleDAO saleDao = new SaleDAO();
			ArrayList<Sale> saleList = null;
			String dispDate = "";
			
			if (year != null && year != "") {
				int yearNum =  Integer.parseInt(year);
				if (yearNum < 100) {
					year = "20" + yearNum;
				} else {
					year = "" + yearNum;
				}
				dispDate = year + "年";
				// ArrayListに年月を元に検索した結果を代入
				saleList = saleDao.selectBySales(year,month);
			}
			if(month != null && month != "") {
				int monthNum =  Integer.parseInt(month);
				if (monthNum < 10) {
					month = "0" + monthNum;
				}
				dispDate += month + "月";
				// ArrayListに年月を元に検索した結果を代入
				saleList = saleDao.selectBySales(year,month);
			}

			// リクエストスコープ
			request.setAttribute("sale_list", saleList);
			request.setAttribute("dispDate", dispDate);
		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、売り上げ情報の確認は出来ません。";
			cmd = "logout";
		} catch (Exception e) {
			error = "予期せぬエラーが発生しました。<br>"+e;;
		} finally {
			if (error.equals("")) {
				//フォワードの実行
				request.getRequestDispatcher("/view/showSalesByMonth.jsp").forward(request, response);
			} else {
				request.setAttribute("error",error);
				request.setAttribute("cmd",cmd);
				//フォワード先の指定
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			}
		}
	}
}
