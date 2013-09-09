package Panel;

/*
 * 2013-09-09
 * 課題管理表「８、１０」
 * 修正箇所で検索
 * 
 */

import janCode.JanCodeSearch;
import janCode.NewReadWriteCsv;

import java.awt.Container;
import java.awt.GridLayout;
import java.security.PublicKey;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import RSA.RsaPublicKey;

public final class testInputPanel extends JFrame {
	private static final long serialVersionUID = 1;
	public static final int TITLE = 0;
	public static final int MAKERNUMBER = 1;
	// ITEパネル関連の変数宣言
	private JanCodeSearch js = new JanCodeSearch();
	private NewReadWriteCsv nrwc = new NewReadWriteCsv();
	protected LeftPanel lp = new LeftPanel();
	protected RightPanel rp = new RightPanel();
	private Container contentPane;
	private String[] item_info = new String[2];
	private String title;
	private StringBuilder sb = new StringBuilder();

	public testInputPanel() {
	}

	public void run() {
		setFrame();
		getLeftPanel();
		setRightPanel();
		setVisible(true);
	}

	private final void setFrame() {
		setBounds(100, 100, 1100, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridLayout(1, 2));
		contentPane = getContentPane();
	}

	private final void getLeftPanel() {
		lp.setLeftPanel(this);
		contentPane.add(lp);
	}

	private final void setRightPanel() {
		rp.setRightPanel(this);
		contentPane.add(rp);
	}

	protected final void ItemRegister() {
		if (!rp.chkRightField()) {
			return;
		}

		item_info = js.getiteminfo(rp.jancode); // [TITLE]:title
												// [MAKERNUMBER]:maker number
		if (nrwc.searchCsv(rp.jancode)) {
			// jancodeが重複していない場合の処理
			// CSVファイル書き込み
			String itemCdEditMsg = "商品コードの編集 ( 半角 )";

			item_info[1] = JOptionPane.showInputDialog(this, itemCdEditMsg,
					item_info[1]);
			addSubtitle();
			
			//修正箇所８、１１
			//TextEventでリアルタイムに反映
			String titleEditMsg = "タイトルの編集 ( タイトルは30文字以内にしてください。 全角1 半角0.5 )";
			do {
				title = JOptionPane.showInputDialog(this, titleEditMsg + "現在："
						+ titleCharCount(title) + "文字", title);
			} while (titleCharCount(title) > 30);

			InsertCsvfile();
			rp.writeRegisterInfo(title);
		} else {
			// jancodeが重複している場合の処理
			// 重複しているのでポップアップ作成
			JLabel msg = new JLabel("登録済みです：" + rp.jancode); // ポップアップ作成
			JOptionPane.showMessageDialog(this, msg); // ポップアップ表示
		}
	}

	/**
	 * タイトル文字数のカウントメソッド
	 * 
	 * @param string
	 * @return 全角を1,半角を0.5とした場合の天井値
	 */
	private int titleCharCount(String string) {
		String hankaku = string.replaceAll("[^ -~｡-ﾟ]*", "");
		String zenkaku = string.replaceAll("[ -~｡-ﾟ]*", "");
		return (int) Math.ceil((double) zenkaku.length()
				+ (double) hankaku.length() / 2);
	}

	// サブタイトルの追加
	private void addSubtitle() {
		sb.append(item_info[TITLE]);
		if (item_info[MAKERNUMBER] != "") {
			sb.insert(0, item_info[MAKERNUMBER] + " "); // title = subtitle
														// makernumber title
		}
		if (!(rp.subtitle.length() == 0)) {
			sb.insert(0, rp.subtitle + " "); // title = subtitle title
		}
		title = new String(sb);
		sb.setLength(0);
	}

	// CSVファイルに書き込む
	private void InsertCsvfile() {
		nrwc.writeCsv(rp.filename, rp.category, title, rp.descriptive,
				rp.start_price, rp.end_price, lp.quantity, lp.day, lp.time, "",
				"", "", "", "", "", lp.prefecture, lp.municipality,
				lp.shippingcharge, lp.payment, lp.yahoo_settlement,
				lp.bank_settlement, lp.bank_settlement_field,
				lp.mail_settlement, lp.cod_settlement,
				lp.other_settlement_field, rp.item_status, rp.item_status_note,
				lp.send_back, lp.send_back_filed, lp.tender_exchange,
				lp.early_exchange, lp.autoextension_exchange,
				lp.cut_negotiation, "0", "いいえ", "いいえ", "いいえ", "無効", "いいえ",
				"いいえ", "いいえ");
	}

	/**
	 * 正しくライセンスキーがあるかどうかのチェック
	 * 
	 * @var licence_key Macアドレスを秘密鍵で暗号化したものが入っている
	 * @var public_key 公開鍵（複合鍵）
	 * @return true, 失敗時false
	 */
	public static final boolean chkLicenseKey() {
		// RSA方式の公開鍵（複合鍵）を取得
		RsaPublicKey rsa_pub = new RsaPublicKey();
		PublicKey public_key;

		// 公開鍵のロード
		public_key = rsa_pub.loadPublicKey("PublicKey.txt");
		if (public_key == null) {
			JOptionPane.showMessageDialog(null, "PublicKey.txtが見つかりませんでした。\n" +
					                            "PublicKey.txtをITE-ver1.5.jarがある場所と同じ位置に置いてください。");
			return false;
		}
		// ライセンスキーの取得
		String titleEditMsg = "ネットワークの接続形式（有線or無線）に合わせた"
				+ "ライセンスキーのファイル名をフルネームで入力してください（最後に.txtを付け忘れないで下さい）";
		String license_filename = JOptionPane.showInputDialog(null,
				titleEditMsg, "");
		byte[] license_key = rsa_pub.loadLicenseKey(license_filename);
		if (license_key == null) {
			JOptionPane.showMessageDialog(null, "正しくライセンスキーが取得出来ませんでした。以下の原因が考えられます。\n" +
					                            "１：無線（有線）接続に対して有線（無線）のライセンスキーを使っている\n" +
					                            "２：別のパソコンのライセンスキーを使っている\n" +
					                            "３：入力したファイル名が間違っている(.txtが抜けているなど)\n" +
					                            "４：ライセンスキーが壊れてる\n" +
					                            "以上のことをご確認ください。");
			return false;
		}
		// 暗号文（license_key）を公開鍵（pubilc_key）で複合化
		byte[] dec = rsa_pub.decrypt(license_key, public_key);
		if (dec == null) {
			JOptionPane.showMessageDialog(null, "複合に失敗しました。\n" +
					                            "PublicKey.txtが破損している可能性があります。\n" +
					                            "CDからPublicKey.txtを再度コピーしてください");
			return false;
		}
		// Macアドレスの取得
		ArrayList<String> macAddressList = new ArrayList<String>();
		macAddressList = rsa_pub.getMacAddress();
		if (macAddressList == null) {
			JOptionPane.showMessageDialog(null, "システムエラー\n" +
					                            "パソコンのIDを取得することができませんでした。\n" +
					                            "説明書の記載されている連絡先に連絡してください。");
			return false;
		}
		if (macAddressList.size() == 0) {
			JOptionPane.showMessageDialog(null, "インターネットに接続出来ていません\n" +
					                            "本システムはインターネットに接続して利用します。\n" +
					                            "インターネットに正しく接続できているかどうかご確認ください。");
			return false;
		}
		// 複合化したバイト配列を元の文字列に変換
		String dec_result = new String(dec);
		for (int i = 0; i < macAddressList.size(); i++) {
			if (!macAddressList.get(i).equalsIgnoreCase(dec_result)) {
			} else {
				return true;
			}
		}
		JOptionPane.showMessageDialog(null, "ライセンスキーが間違っています\n" +
				                            "他のパソコンのライセンスキーを入力してしまったかライセンスキーが壊れている可能性があります。\n" +
				                            "ご確認ください。");
		return false;
	}

}
