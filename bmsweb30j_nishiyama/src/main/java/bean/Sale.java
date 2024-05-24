/*
 * プログラミング名：Sale
 * プログラムの説明：商品の情報を扱うクラス
 * 作成者：西山拓人
 * 作成日：2024年5月17日
 */

package bean;

public class Sale {
	private String isbn; // ISBN番号

	private String title; // 書籍のタイトル

	private int price; // 価格

	private int quantity; // 数量

	public Sale() {
		// 初期化
		this.isbn = null;
		this.title = null;
		this.price = 0;
		this.quantity = 0;
	}

	public Sale(Book _book, int quantity) {
		this.isbn = _book.getIsbn();
		this.title = _book.getTitle();
		this.price = _book.getPrice();
		this.quantity = quantity;
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

	public int getQuantity() {
		// フィールド変数priceを返却
		return this.quantity;
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

	public void setQuantity(int quantity) {
		// 引数priceをフィールド変数priceに代入
		this.quantity = quantity;
	}
}
