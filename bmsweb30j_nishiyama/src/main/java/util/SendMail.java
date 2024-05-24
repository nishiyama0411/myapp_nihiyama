/*
 * プログラミング名：SendMail
 * プログラムの説明：メール送信用クラス
 * 作成者：西山拓人
 * 作成日：2024年5月20日
 */

package util;

import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import bean.Book;
import bean.User;

public class SendMail {
	public static void sendMail (User user,ArrayList<Book> list) {

		try {
			Properties props = System.getProperties();

			// SMTPサーバのアドレスを指定（今回はxserverのSMTPサーバを利用）
			props.put("mail.smtp.host", "sv5215.xserver.jp");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.debug", "true");

			Session session = Session.getInstance(
					props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							//メールサーバにログインするメールアドレスとパスワードを設定
							return new PasswordAuthentication("test.sender@kanda-it-school-system.com", "kandaSender202208");
						}
					}
					);

			MimeMessage mimeMessage = new MimeMessage(session);

			// 送信元メールアドレスと送信者名を指定
			mimeMessage.setFrom(new InternetAddress("test.sender@kanda-it-school-system.com", "神田IT School", "iso-2022-jp"));

			// 送信先メールアドレスを指定（ご自分のメールアドレスに変更）
			mimeMessage.setRecipients(Message.RecipientType.TO, user.getEmail());

			// メールのタイトルを指定
			mimeMessage.setSubject("書籍を購入しました");

			// メールの内容を指定
			mimeMessage.setText(user.getUserid() + "様\n\n" 
					+ "本のご購入ありがとうざいます。\n"
					+ "以下内容でご注文を受け付けましたので、ご連絡致します。\n\n"
					+ SendMail.allPrint(list)
					+ "またのご利用よろしくお願いします。", "iso-2022-jp");



			// メールの形式を指定
			mimeMessage.setHeader("Content-Type", "text/plain; charset=iso-2022-jp");

			// 送信日付を指定
			mimeMessage.setSentDate(new Date());

			// 送信します
			Transport.send(mimeMessage);

			// 送信成功
			System.out.println("送信に成功しました。");

		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("送信に失敗しました。\n" + e);
		}
	}

	public static String allPrint(ArrayList<Book> list) {
		String str = "";
		int total = 0;
		for (int i = 0; i < list.size(); i++) {
			Book book = (Book) list.get(i);
			total += book.getPrice();
			str += book.getIsbn() + "  " + book.getTitle() + "  " + book.getPrice() + "円\n";

		}
		str += "\n合計　" + total + "円\n\n";

		return str;
	}
}


