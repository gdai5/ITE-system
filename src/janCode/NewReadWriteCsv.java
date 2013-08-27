package janCode;

/* 
 * ReadWriteCsvクラス
 * Janコード受け取ったら，ハッシュコードを作成する．
 * CSVファイルに書き込む処理をしている．
 *　*/

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;

public class NewReadWriteCsv {
	HashMap<String, Boolean> map = new HashMap<String, Boolean>();

	public Boolean searchCsv(String jancode) {
		if (map.containsKey(jancode)) {
			return false;
		} else {
			map.put(jancode, true);
			return true;
		}
	}

	public void writeCsv(String filename, String category, String title,
			String explanation, String upsetprice, String buyoutprice,
			String number, String period, String terminationtime,
			String image1, String image1com, String image2, String image2com,
			String image3, String image3com, String prefectures,
			String municipality, String postagecharge, String payment,
			String settlement, String banktransfer, String bankname,
			String registmailcash, String cod, String paymethod1,
			String goodsstate, String goodsstatenote, String returngoods,
			String returngoodsnote, String biddervalulim, String autoextension,
			String earlytermination, String pricenegotiation,
			String autoreexhibition, String boldtext, String backgroundcolor,
			String giftsicon, String boon, String service, String deliabroad,
			String Affiliate) {
		/* csvファイル作成 */
		File csv = new File(filename); // CSVデータファイル
		/* 追記モード */
		BufferedWriter bw;
		/* データ行の追加 */
		if (csv.exists()) {
			try {
				bw = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(csv, true), "Shift_JIS"));
				bw.write(category + "," + title + "," + explanation + ","
						+ upsetprice + "," + buyoutprice + "," + number + ","
						+ period + "," + terminationtime + "," + image1 + ","
						+ image1com + "," + image2 + "," + image2com + ","
						+ image3 + "," + image3com + "," + prefectures + ","
						+ municipality + "," + postagecharge + "," + payment
						+ "," + settlement + "," + banktransfer + ","
						+ bankname + "," + registmailcash + "," + cod + ","
						+ paymethod1 + "," + goodsstate + "," + goodsstatenote
						+ "," + returngoods + "," + returngoodsnote + ","
						+ biddervalulim + "," + autoextension + ","
						+ earlytermination + "," + pricenegotiation + ","
						+ autoreexhibition + "," + boldtext + ","
						+ backgroundcolor + "," + giftsicon + "," + boon + ","
						+ service + "," + deliabroad + "," + Affiliate);
				bw.newLine();
				bw.close();
			} catch (FileNotFoundException e) {
				/* Fileオブジェクト生成時の例外捕捉 */
				e.printStackTrace();
			} catch (IOException e) {
				/* BufferedWriterオブジェクトのクローズ時の例外捕捉 */
				e.printStackTrace();
			}
		} else {
			try {
				bw = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(csv, true), "Shift_JIS"));
				bw.write("カテゴリ" + "," + "タイトル" + "," + "説明" + "," + "開始価格"
						+ "," + "即決価格" + "," + "個数" + "," + "開催期間" + ","
						+ "終了時間" + "," + "画像1" + "," + "画像1コメント" + "," + "画像2"
						+ "," + "画像2コメント" + "," + "画像3" + "," + "画像3コメント" + ","
						+ "商品発送元の都道府県" + "," + "商品発送元の市区町村" + "," + "送料負担"
						+ "," + "代金支払い" + "," + "Yahoo!かんたん決済" + "," + "銀行振込"
						+ "," + "銀行名1" + "," + "現金書留" + "," + "商品代引" + ","
						+ "その他決済方法1" + "," + "商品の状態" + "," + "商品の状態備考" + ","
						+ "返品の可否" + "," + "返品の可否備考" + "," + "入札者評価制限" + ","
						+ "自動延長" + "," + "早期終了" + "," + "	値下げ交渉" + ","
						+ "自動再出品" + "," + "太字テキスト" + "," + "背景色" + ","
						+ "贈答品アイコン" + "," + "はこBOON" + "," + "受け取り後決済サービス"
						+ "," + "海外発送" + "," + "アフィリエイト");
				bw.newLine();
				bw.write(category + "," + title + "," + explanation + ","
						+ upsetprice + "," + buyoutprice + "," + number + ","
						+ period + "," + terminationtime + "," + image1 + ","
						+ image1com + "," + image2 + "," + image2com + ","
						+ image3 + "," + image3com + "," + prefectures + ","
						+ municipality + "," + postagecharge + "," + payment
						+ "," + settlement + "," + banktransfer + ","
						+ bankname + "," + registmailcash + "," + cod + ","
						+ paymethod1 + "," + goodsstate + "," + goodsstatenote
						+ "," + returngoods + "," + returngoodsnote + ","
						+ biddervalulim + "," + autoextension + ","
						+ earlytermination + "," + pricenegotiation + ","
						+ autoreexhibition + "," + boldtext + ","
						+ backgroundcolor + "," + giftsicon + "," + boon + ","
						+ service + "," + deliabroad + "," + Affiliate);
				bw.newLine();
				bw.close();
			} catch (FileNotFoundException e) {
				/* Fileオブジェクト生成時の例外捕捉 */
				e.printStackTrace();
			} catch (IOException e) {
				/* BufferedWriterオブジェクトのクローズ時の例外捕捉 */
				e.printStackTrace();
			}
		}
	}
}
