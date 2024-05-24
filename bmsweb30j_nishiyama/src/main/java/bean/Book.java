/*
 * プログラミング名：Book
 * プログラムの説明：書籍データを取り扱うクラス
 * 作成者：西山拓人
 * 作成日：2024年5月15日
 */

package bean;

public class Book {

	private String isbn; // ISBN

	private String title; // タイトル

	private int price; // 価格

	public Book() {
		// 初期化
		this.isbn = null;
		this.title = null;
		this.price = 0;
	}

	public Book(String isbn, String title, int price) {
		// 初期化
		this.isbn = isbn;
		this.title = title;
		this.price = price;

	}

	/* getterメソッドの定型文 */
	public String getIsbn() {
		// フィールド変数isbnを返却
		return this.isbn;
	}

	public String getTitle() {
		// フィールド変数titleを返却 
		return this.title;
	}

	public int getPrice() {
		// フィールド変数priceを返却
		return this.price;
	}

	/* setterメソッドの定型文 */
	public void setIsbn(String isbn) {
		// 引数isbnをフィールド変数isbnに代入
		this.isbn = isbn;
	}

	public void setTitle(String title) {
		// 引数titleをフィールド変数titleに代入
		this.title = title;
	}

	public void setPrice(int price) {
		// 引数priceをフィールド変数priceに代入
		this.price = price;
	}



}
