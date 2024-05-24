/*
 * プログラミング名：BookDAO
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

import bean.Book;

public class BookDAO {
	
	// データベースに書籍データを削除するインスタンスメソッドdelete
		public ArrayList<Book> search(String isbn, String title, String price){

			Connection con = null;
			Statement smt = null;
			
			// 配列宣言
			ArrayList<Book> bookList = new ArrayList<Book>();

			try{
				// SQL文
				String sql = "SELECT isbn,title,price FROM bookinfo " + "WHERE isbn LIKE '%" + isbn + "%' AND title LIKE '%" + title + "%' AND price LIKE '%" + price + "%'";
				
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
					Book book = new Book();
					book.setIsbn(rs.getString("Isbn"));
					book.setTitle(rs.getString("title"));
					book.setPrice(rs.getInt("price"));
					bookList.add(book);
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
			return bookList;
		}
	
	// データベースに書籍データを削除するインスタンスメソッドdelete
		public void update(Book book){

			Connection con = null;
			Statement smt = null;
			
			// SQL文
			String sql = "UPDATE bookinfo SET title='" + book.getTitle() + "',price=" + book.getPrice() + " WHERE isbn='"
	                + book.getIsbn() + "'";


			try{
				//getConnectionメソッドの呼び出し
				con = BookDAO.getConnection();

				//createStatementメソッドの呼び出し
				smt = con.createStatement();

				//executeQueryメソッドを呼び出しSQL文を発行し結果セットを取得
				smt.executeUpdate(sql);

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
		}

	// データベースに書籍データを削除するインスタンスメソッドdelete
	public void delete(String isbn){

		Connection con = null;
		Statement smt = null;

		try{
			// SQL文
			String sql = "DELETE FROM bookinfo WHERE isbn = '" + isbn + "'";

			//getConnectionメソッドの呼び出し
			con = BookDAO.getConnection();

			//createStatementメソッドの呼び出し
			smt = con.createStatement();

			//executeQueryメソッドを呼び出しSQL文を発行し結果セットを取得
			smt.executeQuery(sql);

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
	}

	// データベースに書籍データをisbnで検索するインスタンスメソッドselectByIsbn
	public Book selectByIsbn(String isbn) {
		Connection con = null;
		Statement smt = null;

		Book book = new Book();

		try {
			// SQL文
			String sql = "SELECT isbn,title,price FROM bookinfo WHERE isbn = '" + isbn + "'";

			// DBに接続
			con = BookDAO.getConnection();
			smt = con.createStatement();

			// SQL文発行
			ResultSet rs = smt.executeQuery(sql);

			// 結果をBookクラスに渡す
			while (rs.next()) {
				book.setIsbn(rs.getString("Isbn"));
				book.setTitle(rs.getString("title"));
				book.setPrice(rs.getInt("price"));
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
		return book;
	}

	// データベースに書籍データを登録するインスタンスメソッドinsert
	public void insert (Book book) {

		// 変数宣言
		Connection con = null;   // DBコネクション
		Statement smt = null;    // SQLステートメント

		try {
			// SQL文
			String sql = "INSERT INTO bookinfo VALUES('"+book.getIsbn()+"','"+book.getTitle()+"',"+book.getPrice()+")";

			// DBに接続
			con = BookDAO.getConnection();
			smt = con.createStatement();

			// SQL文発行
			smt.executeUpdate(sql);

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
	}

	// データベースから書籍データを検索し、ArrayListオブジェクトに格納するインスタンスメソッドselectAll
	public ArrayList<Book> selectAll() {

		// 変数宣言
		Connection con = null;   // DBコネクション
		Statement smt = null;    // SQLステートメント

		// 配列宣言
		ArrayList<Book> bookList = new ArrayList<Book>();

		try {
			// SQL文
			String sql = "SELECT isbn,title,price FROM bookinfo ORDER BY isbn";

			// DBに接続
			con = BookDAO.getConnection();
			smt = con.createStatement();

			// SQL文発行
			ResultSet rs = smt.executeQuery(sql);

			// 検索結果をArrayListに格納
			while (rs.next()) {
				Book book = new Book();
				book.setIsbn(rs.getString("Isbn"));
				book.setTitle(rs.getString("title"));
				book.setPrice(rs.getInt("price"));
				bookList.add(book);
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
		return bookList;
	}

	// データベース接続情報を利用してデータベースに接続するクラスメソッドgetConnection
	public static Connection getConnection() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mariadb://localhost/mybookdb","root","root123");
			return con;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
}
