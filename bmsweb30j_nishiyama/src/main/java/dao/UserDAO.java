/*
 * プログラミング名：UserDAO
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

import bean.User;

public class UserDAO {

	private static String RDB_DRIVE = "org.mariadb.jdbc.Driver";
	private static String URL = "jdbc:mariadb://localhost/mybookdb";
	private static String USER="root";
	private static String PASSWD="root123";
	
	// DBのuserinfoテーブルから指定ユーザーのパスワード情報のみを更新するメソッド
	public int updateForPassword (String userid, String password) {
		int count = 0;
		
		Connection con = null;
		Statement smt = null;

		try {
			// SQL文
			String sql = "UPDATE userinfo SET password = '"+password+"' WHERE user = '"+userid+"'";

			//getConnectionメソッドの呼び出し
			con = UserDAO.getConnection();

			//createStatementメソッドの呼び出し
			smt = con.createStatement();

			//executeUpdateメソッドを呼び出しSQL文を発行し結果セットを取得
			count = smt.executeUpdate(sql);
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

		return count;
	}
	
	// DBのuserinfoテーブルから指定ユーザー名にあいまいに合致する情報を取得するメソッド
	public ArrayList<User> search(String userid){

		Connection con = null;
		Statement smt = null;

		// 配列宣言
		ArrayList<User> userList = new ArrayList<User>();

		try{
			// SQL文
			String sql = "SELECT * FROM userinfo WHERE user LIKE '%" + userid + "%'";

			//getConnectionメソッドの呼び出し
			con = BookDAO.getConnection();

			//createStatementメソッドの呼び出し
			smt = con.createStatement();

			//executeQueryメソッドを呼び出しSQL文を発行し結果セットを取得
			smt.executeQuery(sql);

			// SQL文発行
			ResultSet rs = smt.executeQuery(sql);

			// 検索結果をArrayListに格納
			while (rs.next()) {
				User user = new User();
				user.setUserid(rs.getString("user"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setAuthority(rs.getString("authority"));
				userList.add(user);
			}

		}catch(Exception e){
			throw new IllegalStateException(e);
		}finally{
			if( smt != null ){
				try{smt.close();}catch(SQLException ignore){}
			}
			if( con != null ){
				try{con.close();}catch(SQLException ignore){}
			}
		}
		return userList;
	}
	
	// 引数のユーザーデータを元にDBのuserinfoテーブルから該当書籍データの更新処理を行うメソッド
	public int update(User user){

		Connection con = null;
		Statement smt = null;
		int count = 0;

		// SQL文
		String sql = "UPDATE userinfo SET password='" + user.getPassword() + "',email='" + user.getEmail()
		+ "',authority='" + user.getAuthority() + "' WHERE user='" + user.getUserid() + "'";


		try{
			//getConnectionメソッドの呼び出し
			con = BookDAO.getConnection();

			//createStatementメソッドの呼び出し
			smt = con.createStatement();

			//executeUpdateメソッドを呼び出しSQL文を発行し結果セットを取得
			count = smt.executeUpdate(sql);

		}catch(Exception e){
			throw new IllegalStateException(e);
		}finally{
			if( smt != null ){
				try{smt.close();}catch(SQLException ignore){}
			}
			if( con != null ){
				try{con.close();}catch(SQLException ignore){}
			}
		}
		return count;
	}
	
	// 引数のユーザーデータ（user）を元にDBのuserinfoテーブルから該当ユーザーデータの削除処理を行うメソッド
	public int delete(String userid){

		Connection con = null;
		Statement smt = null;
		int count = 0;

		try{
			// SQL文
			String sql = "DELETE FROM userinfo WHERE user = '" + userid + "'";

			//getConnectionメソッドの呼び出し
			con = BookDAO.getConnection();

			//createStatementメソッドの呼び出し
			smt = con.createStatement();

			//executeQueryメソッドを呼び出しSQL文を発行し結果セットを取得
			count = smt.executeUpdate(sql);

		}catch(Exception e){
			throw new IllegalStateException(e);
		}finally{
			if( smt != null ){
				try{smt.close();}catch(SQLException ignore){}
			}
			if( con != null ){
				try{con.close();}catch(SQLException ignore){}
			}
		}
		return count;
	}
	
	// 引数のユーザーデータを元にDBのuserinfoテーブルに新規登録処理を行うメソッド
	public int insert(User user) {
		
		int count = 0; // 変数宣言
		Connection con = null;   // DBコネクション
		Statement smt = null;    // SQLステートメント

		try {
			// SQL文
			String sql = "INSERT INTO userinfo VALUES('" + user.getUserid() + "','" + user.getPassword() + "','"
					+ user.getEmail() + "','" + user.getAuthority() + "')";
			// DBに接続
			con = BookDAO.getConnection();
			smt = con.createStatement();

			// SQL文発行
			count = smt.executeUpdate(sql);

		}catch (Exception e) {
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
		
		return count;
	}
	
	// userinfoテーブルから全ユーザー情報を取得するメソッド
	public ArrayList<User> selectAll() {
		Connection con = null;
		Statement smt = null;

		// 配列宣言
		ArrayList<User> userList = new ArrayList<User>();

		try {
			// SQL文
			String sql = "SELECT * FROM userinfo";

			// DBに接続
			con = BookDAO.getConnection();
			smt = con.createStatement();

			// SQL文発行
			ResultSet rs = smt.executeQuery(sql);

			// 検索結果をArrayListに格納
			while (rs.next()) {
				User user = new User();
				user.setUserid(rs.getString("user"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setAuthority(rs.getString("authority"));
				userList.add(user);
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
		return userList;
	}

	//DBのuserinfoテーブルから指定ユーザーとパスワードの条件に合致する情報を取得するメソッド
	public User selectByUser (String _userid, String _password) {

		Connection con = null;
		Statement smt = null;
		User user = new User();

		try {
			// SQL文
			String sql = "SELECT * FROM userinfo WHERE user ='"+_userid+"' AND password='"+_password+"'";

			//getConnectionメソッドの呼び出し
			con = UserDAO.getConnection();

			//createStatementメソッドの呼び出し
			smt = con.createStatement();

			//executeQueryメソッドを呼び出しSQL文を発行し結果セットを取得
			ResultSet rs = smt.executeQuery(sql);

			// 検索結果
			while (rs.next()) {
				user.setUserid(rs.getString("user"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setAuthority(rs.getString("authority"));
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

		return user;
	}

	//DBのuserinfoテーブルから指定ユーザーの条件に合致する情報を取得するメソッド
	public User selectByUser (String _userid) {

		Connection con = null;
		Statement smt = null;
		User user = new User();

		try {
			// SQL文
			String sql = "SELECT * FROM userinfo WHERE user ='" + _userid + "'";

			//getConnectionメソッドの呼び出し
			con = UserDAO.getConnection();

			//createStatementメソッドの呼び出し
			smt = con.createStatement();

			//executeQueryメソッドを呼び出しSQL文を発行し結果セットを取得
			ResultSet rs = smt.executeQuery(sql);

			// 検索結果をArrayListに格納
			while (rs.next()) {
				user.setUserid(rs.getString("user"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setAuthority(rs.getString("authority"));
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

		return user;
	}

	// データベース接続情報を利用してデータベースに接続するクラスメソッドgetConnection
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
