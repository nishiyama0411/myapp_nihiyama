/*
 * プログラミング名：OrderedItem
 * プログラムの説明：購入情報を扱うクラス
 * 作成者：西山拓人
 * 作成日：2024年5月15日
 */

package bean;

public class OrderedItem {
	private String userid; // ユーザーID

	private String title; //書籍タイトル
	
	private int quantity;

	private String date; //購入日付

	public OrderedItem() {
		// 初期化
		this.userid = null;
		this.title = null;
		this.quantity = 0;
		this.date = null;
	}

	/* getterメソッドの定型文 */
	public String getUserid() {
		// フィールド変数useridを返却
		return this.userid;
	}

	public String getTitle() {
		// フィールド変数titleを返却 
		return this.title;
	}
	
	public int getQuantity() {
		// フィールド変数quantityを返却 
		return this.quantity;
	}

	public String getDate() {
		// フィールド変数dateを返却
		return this.date;
	}

	/* setterメソッドの定型文 */
	public void setUserid(String userid) {
		// 引数useridをフィールド変数useridに代入
		this.userid = userid;
	}

	public void setTitle(String title) {
		// 引数titleをフィールド変数titleに代入
		this.title = title;
	}
	
	public void setQuantity(int quantity) {
		// 引数quantityをフィールド変数quantityに代入
		this.quantity = quantity;
	}

	public void setDate(String date) {
		// 引数dateをフィールド変数dateに代入
		this.date = date;
	}
}
