/*
 * プログラミング名：User
 * プログラムの説明：ユーザーの情報を扱うクラス
 * 作成者：西山拓人
 * 作成日：2024年5月15日
 */

package bean;

public class User {
	private String userid; // ユーザー名

	private String password; // パスワード

	private String email; // メールアドレス

	private String authority; // 権限(1:一般ユーザー、2:管理者)

	public User() {
		// 初期化
		this.userid = null;
		this.password = null;
		this.email = null;
		this.authority = null;
	}

	public User(String userid, String password, String email) {
		// 初期化
		this.userid = userid;
		this.password = password;
		this.email = email;

	}

	/* getterメソッドの定型文 */
	public String getUserid() {
		// フィールド変数useridを返却
		return this.userid;
	}

	public String getPassword() {
		// フィールド変数passwordを返却 
		return this.password;
	}

	public String getEmail() {
		// フィールド変数emailを返却
		return this.email;
	}

	public String getAuthority() {
		// フィールド変数emailを返却
		return this.authority;
	}

	/* setterメソッドの定型文 */
	public void setUserid(String userid) {
		// 引数useridをフィールド変数useridに代入
		this.userid = userid;
	}

	public void setPassword(String password) {
		// 引数passwordをフィールド変数passwordに代入
		this.password = password;
	}

	public void setEmail(String email) {
		// 引数emailをフィールド変数emailに代入
		this.email = email;
	}

	public void setAuthority(String authority) {
		// 引数emailをフィールド変数emailに代入
		this.authority = authority;
	}
}
