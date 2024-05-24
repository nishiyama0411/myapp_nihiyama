/*
 * プログラミング名：SaleDAO
 * プログラムの説明：データベースへアクセスする処理をメソッド化し、1つのクラスにまとめるプログラム
 * 作成者：西山拓人
 * 作成日：2024年5月17日
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bean.Sale;


public class SaleDAO {
	private static String RDB_DRIVE = "org.mariadb.jdbc.Driver";
	private static String URL = "jdbc:mariadb://localhost/mybookdb";
	private static String USER="root";
	private static String PASSWD="root123";
	
	// DBのordernfoテーブルから指定ユーザーの条件に合致する購入履歴情報を取得するメソッド
	public ArrayList<Sale> selectBySales(String year, String month) {
		
		Connection con = null;
		Statement smt = null;
		ArrayList<Sale> saleList = new ArrayList<Sale>();

		try {
			// SQL文
			String sql = "SELECT b.isbn, title, price, sum(quantity) as quantity FROM orderinfo o inner join bookinfo b ON o.isbn=b.isbn "
					+" WHERE date LIKE '" + year + "-" + month + "%' GROUP BY b.isbn ORDER BY b.isbn;";

			//getConnectionメソッドの呼び出し
			con = SaleDAO.getConnection();
			//createStatementメソッドの呼び出し
			smt = con.createStatement();

			//executeQueryメソッドを呼び出しSQL文を発行し結果セットを取得
			ResultSet rs = smt.executeQuery(sql);

			// 検索結果をArrayListに格納
			while (rs.next()) {
				Sale sale = new Sale();
				sale.setIsbn(rs.getString("isbn"));
				sale.setTitle(rs.getString("title"));
				sale.setPrice(rs.getInt("price"));
				sale.setQuantity(rs.getInt("quantity"));
				saleList.add(sale);
			}

		} catch (Exception e) {
			throw new IllegalStateException(e);
		} finally {
			if ( smt != null ) {
				try { smt.close(); } catch (SQLException ignore) { }
			}
			if ( con != null ) {
				try { con.close(); } catch (SQLException ignore) { }
			}
		}
		return saleList;
	}
	
	//データベース接続情報を利用してデータベースに接続するクラスメソッドgetConnection
	private static Connection getConnection() {
		try {
			Class.forName(RDB_DRIVE);
			Connection con = DriverManager.getConnection(URL,USER,PASSWD);
			return con;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
}
