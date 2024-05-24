/*
 * プログラミング名：Order
 * プログラムの説明：注文する情報を扱うクラス
 * 作成者：佐田快斗
 * 作成日：2024年5月14日
 */

package bean;

public class Order {
	private int orderno; // 注文No

	private String userid; // ユーザーID

	private String isbn; // ISBN

	private int quantity; // 数量

	private String date; // 購入日付

	public Order() {
		// 初期化
		this.orderno = 0;
		this.userid = null;
		this.isbn = null;
		this.quantity = 0;
		this.date = null;
	}

	/* getterメソッドの定型文 */
	public int getOrderNo() {
		// フィールド変数ordernoを返却
		return this.orderno;
	}

	public String getDate() {
		// フィールド変数dateを返却
		return this.date;
	}

	public String getUserid() {
		// フィールド変数useridを返却 
		return this.userid;
	}

	public String getIsbn() {
		// フィールド変数isbnを返却
		return this.isbn;
	}

	public int getQuantity() {
		// フィールド変数quantityを返却
		return this.quantity;
	}

	/* setterメソッドの定型文 */
	public void setOrderNo(int orderno) {
		// 引数isbnをフィールド変数isbnに代入
		this.orderno =orderno;
	}

	public void setUserid(String userid) {
		// 引数useridをフィールド変数useridに代入
		this.userid = userid;
	}

	public void setIsbn(String isbn) {
		// 引数isbnをフィールド変数isbnに代入
		this.isbn = isbn;
	}

	public void setQuantity(int quantity) {
		// 引数isbnをフィールド変数quantityに代入
		this.quantity = quantity;
	}

	public void setDate(String date) {
		// 引数isbnをフィールド変数dateに代入
		this.date = date;
	}
}
