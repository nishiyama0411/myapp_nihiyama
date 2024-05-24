/*
 * プログラミング名：OrderDAO
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

import bean.Order;

public class OrderDAO {
	private static String RDB_DRIVE = "org.mariadb.jdbc.Driver";
	private static String URL = "jdbc:mariadb://localhost/mybookdb";
	private static String USER="root";
	private static String PASSWD="root123";

	public void insert(Order order) {

		Connection con = null;
		Statement smt = null;

		try {
			// SQL文
			String sql = "INSERT INTO orderinfo VALUES(NULL,'"+ order.getUserid() + "','"+ order.getIsbn() +"',"
					+ order.getQuantity() +",CURDATE())";

			//getConnectionメソッドの呼び出し
			con = OrderDAO.getConnection();
			//createStatementメソッドの呼び出し
			smt = con.createStatement();

			//executeQueryメソッドを呼び出しSQL文を発行し結果セットを取得
			ResultSet rs = smt.executeQuery(sql);

			// 検索結果
			while (rs.next()) {
				order.setOrderNo(rs.getInt("orderno"));
				order.setUserid(rs.getString("user"));
				order.setIsbn(rs.getString("isbn"));
				order.setQuantity(rs.getInt("quantity"));
				order.setDate(rs.getString("date"));
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
