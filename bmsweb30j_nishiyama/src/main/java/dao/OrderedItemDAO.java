/*
 * プログラミング名：OrderedItemDAO
 * プログラムの説明：データベースへアクセスする処理をメソッド化し、1つのクラスにまとめるプログラム
 * 作成者：西山拓人
 * 作成日：2024年5月15日
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bean.OrderedItem;


public class OrderedItemDAO {
	private static String RDB_DRIVE = "org.mariadb.jdbc.Driver";
	private static String URL = "jdbc:mariadb://localhost/mybookdb";
	private static String USER="root";
	private static String PASSWD="root123";

	// DBのordernfoテーブルから指定ユーザーの条件に合致する購入履歴情報を取得するメソッド定義
	public ArrayList<OrderedItem> selectByUser(String userid) {
		Connection con = null;
		Statement smt = null;

		ArrayList<OrderedItem> list = new ArrayList<OrderedItem>();

		try {
			// SQL文
			String sql =  "SELECT o.user , b.title , o.quantity, o.date FROM bookinfo b, orderinfo o"
					+ " WHERE b.isbn = o.isbn and o.user = '"+userid+"'";


			// DBに接続
			con = BookDAO.getConnection();
			smt = con.createStatement();

			// SQL文発行
			ResultSet rs = smt.executeQuery(sql);

			// 検索結果をArrayListに格納
			while (rs.next()) {
				OrderedItem orderedItem = new OrderedItem();
				orderedItem.setUserid(rs.getString("user"));
				orderedItem.setTitle(rs.getString("title"));
				orderedItem.setQuantity(rs.getInt("quantity"));
				orderedItem.setDate(rs.getString("date"));
				list.add(orderedItem);
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
		return list;
	}

	public ArrayList<OrderedItem> selectAll() {

		Connection con = null;
		Statement smt = null;
		ArrayList<OrderedItem> list = new ArrayList<OrderedItem>();

		try {
			// SQL文
			String sql = "SELECT o.user, b.title , o.quantity, o.date FROM bookinfo b, orderinfo o WHERE b.isbn = o.isbn";

			//getConnectionメソッドの呼び出し
			con = OrderedItemDAO.getConnection();
			//createStatementメソッドの呼び出し
			smt = con.createStatement();

			//executeQueryメソッドを呼び出しSQL文を発行し結果セットを取得
			ResultSet rs = smt.executeQuery(sql);

			// 検索結果をArrayListに格納
			while (rs.next()) {
				OrderedItem orderedItem = new OrderedItem();
				orderedItem.setUserid(rs.getString("user"));
				orderedItem.setTitle(rs.getString("title"));
				orderedItem.setQuantity(rs.getInt("quantity"));
				orderedItem.setDate(rs.getString("date"));
				list.add(orderedItem);
			}

		} catch (Exception e) {
			throw new IllegalStateException(e);
		} finally {
			// リソースの解放
			if ( smt != null ) {
				try { smt.close(); } catch (SQLException ignore) { }
			}
			if ( con != null ) {
				try { con.close(); } catch (SQLException ignore) { }
			}
		}
		return list;
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

	public static void main (String[] args) throws Exception{
		Connection con = getConnection();
		System.out.println("con=" + con);
		con.close();
	}
}
