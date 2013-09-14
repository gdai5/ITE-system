/*
 * 2013-09-09
 * 修正項目
 * 課題表番号「２、３、４、６、７」
 * 「修正箇所」で検索した場所を修正
 */

package janCode;

/* 
 * JanCodeSearchクラス
 * JanCodeProcessから，Documentクラスを取得して
 * 正規表現を用いて，タイトルと商品コードを切り出すクラス
 *　*/

import java.io.*;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class JanCodeSearch {
	public static final int TITLE = 0;
	public static final int MAKERNUMBER = 1;

	private String[] returntitle = new String[2];
	private String[] arzon1 = new String[3];
	private String[] arzon2 = new String[3];
	private String[] jbook1 = new String[3];
	private String[] dpg = new String[1];
	private String[] cdl = new String[1];
	private String[] itemcode = new String[4];

	private List<String> searchedwordlist = new ArrayList<String>(); // リスト型配列で，パターンにマッチした検索をリストに入れる
	private String subtitle = new String(); // 比較するタイトルを一時的に保存する
	private String submakernumber = new String(); // 品番で色々パターンがあるので対応させるため

	private static String sName = "PHPSESSID";
	private static String sValue = null;

	public String[] getinformation(Document doc) {
		// ///以下，検索パターン（正規表現）//////////////////////////////////////////
		// 正規表現を追加する場合は，以下にならって下さい．
		// "アルゾン:"
		arzon1[0] = "\\ .+?\\：";
		arzon1[1] = "\\ .+?\\.";
		arzon1[2] = "\\ .+?\\-";

		// "Arzon："
		arzon2[0] = "\\ .+?\\：";
		arzon2[1] = "\\ .+?\\.";
		arzon2[2] = "\\ .+?\\-";

		// "JBOOK"
		jbook1[0] = "\\：.+?\\：";
		jbook1[1] = "\\：.+?\\.";
		jbook1[2] = "\\：.+?\\-";

		// "DPG"...１８禁アニメ、１８禁ゲーム
		dpg[0] = "\\ .+?\\ ";

		// "CD|"...１８禁アニメ、１８禁ゲームこの場合だと，JANコードがくっついているから安心
		cdl[0] = "\\|.+?\\|";

		// "itemcode"...商品コード
		itemcode[0] = "\\ .+?\\ ";
		itemcode[1] = "\\ .+?\\.";
		itemcode[2] = "\\ .+?\\;";
		itemcode[3] = "：.+?\\ ";
		// ///以上，検索パターン（正規表現）//////////////////////////////////////////

		Pattern pattern; // 検索に使用する，正規表現のpatternメソッド
		Matcher matcher; // 検索に使用する，正規表現のmatcherメソッド

		/*
		 * get title algorithm 1.HTMLのbodyタグ要素をJsoupで取得 2.検索ワードまでの文字数を取得
		 * 3.取得パターンの作成　"\\ .+?\\ "等 4.パターンマッチング 5.文字変数リストに入れる 6.[0]番目にタイトルが入っている
		 * 7.文字前後のスペースの排除
		 */

		String body = doc.getElementsByTag("body").text();
		// bodyタグ内部情報表示
		// get TITLE
		if (body.indexOf("アルゾン:") != -1) {
			searchedwordlist.clear(); // 変数の初期化
			int index = body.indexOf("アルゾン:"); // 検索ワードまでの文字数を取得
			for (int i = 0; i < arzon1.length; i++) {
				pattern = Pattern.compile(arzon1[i]); // 取得パターンの作成
				matcher = pattern.matcher(body.substring(index)); // パターンマッチング
				while (matcher.find()) {
					searchedwordlist.add(matcher.group());
				}
				if (i == 0) {
					returntitle[TITLE] = searchedwordlist.get(0);
					returntitle[TITLE] = returntitle[TITLE].substring(1,
							returntitle[TITLE].length() - 1); // 文字列の前後のスペースを取る
					searchedwordlist.clear();// 空にすることで、次もsearchedwordlist.get(0)でヒットできるようにする
				} else {
					subtitle = searchedwordlist.get(0);
					subtitle = subtitle.substring(1, subtitle.length() - 1); // 文字列の前後のスペースを取る
					searchedwordlist.clear();
					if (returntitle[TITLE].length() > subtitle.length()) {
						returntitle[TITLE] = subtitle;
					}
				}
			}
		} else if (body.indexOf("Arzon：") != -1) {
			// "Arzon："の場合
			searchedwordlist.clear();
			int index = body.indexOf("Arzon："); // 検索ワードまでの文字数を取得
			for (int i = 0; i < arzon2.length; i++) {
				pattern = Pattern.compile(arzon2[i]); // 取得パターンの作成
				matcher = pattern.matcher(body.substring(index)); // パターンマッチング
				while (matcher.find()) {
					searchedwordlist.add(matcher.group());
				}
				try {
					if (i == 0) {
						returntitle[TITLE] = searchedwordlist.get(0);
						returntitle[TITLE] = returntitle[TITLE].substring(1,
								returntitle[TITLE].length() - 1); // 文字列の前後のスペースを取る
						searchedwordlist.clear();// 空にすることで、次もsearchedwordlist.get(0)でヒットできるようにする
					} else {
						subtitle = searchedwordlist.get(0);
						subtitle = subtitle.substring(1, subtitle.length() - 1); // 文字列の前後のスペースを取る
						searchedwordlist.clear();
						if (returntitle[TITLE].length() > subtitle.length()) {
							returntitle[TITLE] = subtitle;
						}
					}
				} catch (IndexOutOfBoundsException e) {
				}
			}
		} else if (body.indexOf("JBOOK") != -1) {
			// "JBOOK"の場合
			searchedwordlist.clear();
			int index = body.indexOf("JBOOK"); // 検索ワードまでの文字数を取得
			// パターンマッチしたものでもっとも短いものを選ぶ
			for (int i = 0; i < jbook1.length; i++) {
				pattern = Pattern.compile(jbook1[i]); // 取得パターンの作成
				matcher = pattern.matcher(body.substring(index)); // パターンマッチング
				while (matcher.find()) {
					searchedwordlist.add(matcher.group());
				}
				try {
					if (i == 0) {
						returntitle[TITLE] = searchedwordlist.get(0);
						returntitle[TITLE] = returntitle[TITLE].substring(1,
								returntitle[TITLE].length() - 1); // 文字列の前後のスペースを取る
						searchedwordlist.clear();// 空にすることで、次もsearchedwordlist.get(0)でヒットできるようにする
					} else {
						subtitle = searchedwordlist.get(0);
						subtitle = subtitle.substring(1, subtitle.length() - 1); // 文字列の前後のスペースを取る
						searchedwordlist.clear();
						if (returntitle[TITLE].length() > subtitle.length()) {
							returntitle[TITLE] = subtitle;
						}
					}
				} catch (IndexOutOfBoundsException e) {
				}
			}
		} else if (body.indexOf("DPG") != -1) {
			searchedwordlist.clear();
			int index = body.indexOf("DPG"); // 検索ワードまでの文字数を取得
			// パターンマッチしたものでもっとも短いものを選ぶ
			for (int i = 0; i < dpg.length; i++) {
				pattern = Pattern.compile(dpg[i]); // 取得パターンの作成
				matcher = pattern.matcher(body.substring(index)); // パターンマッチング
				while (matcher.find()) {
					searchedwordlist.add(matcher.group());
				}
				try {
					if (i == 0) {
						returntitle[TITLE] = searchedwordlist.get(0);
						returntitle[TITLE] = returntitle[TITLE].substring(1,
								returntitle[TITLE].length() - 1); // 文字列の前後のスペースを取る
						searchedwordlist.clear();// 空にすることで、次もsearchedwordlist.get(0)でヒットできるようにする
					} else {
						subtitle = searchedwordlist.get(0);
						subtitle = subtitle.substring(1, subtitle.length() - 1); // 文字列の前後のスペースを取る
						searchedwordlist.clear();
						if (returntitle[TITLE].length() > subtitle.length()) {
							returntitle[TITLE] = subtitle;
						}
					}
				} catch (IndexOutOfBoundsException e) {
				}
			}
		} else if (body.indexOf("CD|") != -1) {
			searchedwordlist.clear();
			int index = body.indexOf("CD|"); // 検索ワードまでの文字数を取得
			// パターンマッチしたものでもっとも短いものを選ぶ
			for (int i = 0; i < dpg.length; i++) {
				pattern = Pattern.compile(cdl[i]); // 取得パターンの作成
				matcher = pattern.matcher(body.substring(index)); // パターンマッチング
				while (matcher.find()) {
					searchedwordlist.add(matcher.group());
				}
				try {
					if (i == 0) {
						returntitle[TITLE] = searchedwordlist.get(0);
						returntitle[TITLE] = returntitle[TITLE].substring(1,
								returntitle[TITLE].length() - 1); // 文字列の前後のスペースを取る
						searchedwordlist.clear();// 空にすることで、次もsearchedwordlist.get(0)でヒットできるようにする
					} else {
						subtitle = searchedwordlist.get(0);
						subtitle = subtitle.substring(1, subtitle.length() - 1); // 文字列の前後のスペースを取る
						searchedwordlist.clear();
						if (returntitle[TITLE].length() > subtitle.length()) {
							returntitle[TITLE] = subtitle;
						}
					}
				} catch (IndexOutOfBoundsException e) {
				}
			}
		} else {
			// タイトルが見つからない場合
			returntitle[TITLE] = "";
		}

		// get MAKER NUMBER
		if (body.indexOf("メーカー品番：") != -1) {
			String regex = "\\ .+?\\ "; // 取得パターンは前後のスペース
			searchedwordlist.clear();
			int index = body.indexOf("メーカー品番："); // 検索ワードまでの文字数を取得
			pattern = Pattern.compile(regex); // 取得パターンの作成
			matcher = pattern.matcher(body.substring(index)); // パターンマッチング
			while (matcher.find()) {
				searchedwordlist.add(matcher.group());
			}
			try {
				returntitle[MAKERNUMBER] = searchedwordlist.get(0);
				returntitle[MAKERNUMBER] = returntitle[MAKERNUMBER].substring(
						1, returntitle[MAKERNUMBER].length() - 1); // 文字列の前後のスペースを取る
			} catch (IndexOutOfBoundsException e) {
			}

			// 文字列の前後のスペースを取る
		} else if (body.indexOf("製品番号") != -1) {
			String regex = "\\ .+?\\."; // 取得パターンは前後のスペース
			searchedwordlist.clear();
			int index = body.indexOf("製品番号"); // 検索ワードまでの文字数を取得
			pattern = Pattern.compile(regex); // 取得パターンの作成
			matcher = pattern.matcher(body.substring(index)); // パターンマッチング
			while (matcher.find()) {
				searchedwordlist.add(matcher.group());
			}
			try {
				returntitle[MAKERNUMBER] = searchedwordlist.get(0);
				returntitle[MAKERNUMBER] = returntitle[MAKERNUMBER].substring(
						1, returntitle[MAKERNUMBER].length() - 1); // 文字列の前後のスペースを取る
			} catch (IndexOutOfBoundsException e) {
			}
		} else if (body.indexOf("品番") != -1) {
			int index = body.indexOf("品番"); // 検索ワードまでの文字数を取得
			for (int i = 0; i < itemcode.length; i++) {
				searchedwordlist.clear();
				pattern = Pattern.compile(itemcode[i]); // 取得パターンの作成
				matcher = pattern.matcher(body.substring(index)); // パターンマッチング
				while (matcher.find()) {
					searchedwordlist.add(matcher.group());
				}
				try {
					if (i == 0) {
						returntitle[MAKERNUMBER] = searchedwordlist.get(0);
						returntitle[MAKERNUMBER] = returntitle[MAKERNUMBER]
								.substring(1,
										returntitle[MAKERNUMBER].length() - 1); // 文字列の前後のスペースを取る
					} else {
						submakernumber = searchedwordlist.get(0);
						submakernumber = submakernumber.substring(1,
								submakernumber.length() - 1); // 文字列の前後のスペースを取る
						pattern = Pattern.compile("-{1}"); // 取得パターンの作成
						matcher = pattern.matcher(returntitle[MAKERNUMBER]); // パターンマッチング
						if (matcher.find()) {
							matcher = pattern.matcher(submakernumber); // パターンマッチング
							if (matcher.find()) {
								if (returntitle[MAKERNUMBER].length() > submakernumber
										.length()) {
									returntitle[MAKERNUMBER] = submakernumber;
								}
							}
						} else if (returntitle[MAKERNUMBER].length() > submakernumber
								.length()) {
							returntitle[MAKERNUMBER] = submakernumber;
						}
					}
				} catch (IndexOutOfBoundsException e) {
				}
			}
		} else if (body.indexOf("規格番号") != -1) {
			String regex = "\\：.+?\\."; // 取得パターンは前後のスペース
			searchedwordlist.clear();
			int index = body.indexOf("規格番号"); // 検索ワードまでの文字数を取得
			pattern = Pattern.compile(regex); // 取得パターンの作成
			matcher = pattern.matcher(body.substring(index)); // パターンマッチング
			while (matcher.find()) {
				searchedwordlist.add(matcher.group());
			}
			try {
				returntitle[MAKERNUMBER] = searchedwordlist.get(0);
				returntitle[MAKERNUMBER] = returntitle[MAKERNUMBER].substring(
						1, returntitle[MAKERNUMBER].length() - 1); // 文字列の前後のスペースを取る
			} catch (IndexOutOfBoundsException e) {
			}
		} else {
			// メーカー品番が見つからない場合
			returntitle[MAKERNUMBER] = "";
		}
		return returntitle;
	}

	public String[] getInfoFromArzon(String jancode) {
		String[] returnValue = new String[2];
		returnValue[TITLE] = "";
		returnValue[MAKERNUMBER] = "";

		try {
			String arzonURl = "http://www.arzon.jp";
			String arzonSearchURl = "http://www.arzon.jp/itemlist.html?t=&m=all&s=&q=";

			// セッションの確立
			if (sValue == null) {
				String arzonConfilmUrl = "http://www.arzon.jp/index.php?action=adult_customer_agecheck&agecheck=1&redirect=http%3A%2F%2Fwww.arzon.jp%2F";
				sValue = Jsoup.connect(arzonConfilmUrl).execute().cookie(sName);
			}

			Document searchDoc = Jsoup.connect(arzonSearchURl + jancode)
					.cookie(sName, sValue).get();
			String itemUrl = searchDoc.getElementById("item")
					.getElementsByClass("pictlist").get(0)
					.getElementsByClass("hentry").get(0).getElementsByTag("dt")
					.get(0).getElementsByTag("a").get(0).attr("href");

			Document itemDoc = Jsoup.connect(arzonURl + itemUrl)
					.cookie(sName, sValue).get();

			returnValue[TITLE] = itemDoc.getElementById("detail_new")
					.getElementsByClass("detail_title").get(0).text().trim();

			for (Element listName : itemDoc.getElementById("detail_new")
					.getElementsByClass("item_detail").get(0)
					.getElementsByClass("list1")) {
				if (listName.text().indexOf("品番：") >= 0) {
					returnValue[MAKERNUMBER] = listName.nextElementSibling()
							.text().trim();
					break;
				}
			}
		} catch (Exception e) {
		}

		return returnValue;
	}

	public String[] getInfoFromArzonForAnime(String jancode) {
		String[] returnValue = new String[2];
		returnValue[TITLE] = "";
		returnValue[MAKERNUMBER] = "";

		try {
			String arzonURl = "http://www.arzon.jp";
			String arzonSearchURl = "http://www.arzon.jp/animelist.html?q=";

			// セッションの確立
			if (sValue == null) {
				String arzonConfilmUrl = "http://www.arzon.jp/index.php?action=adult_customer_agecheck&agecheck=1&redirect=http%3A%2F%2Fwww.arzon.jp%2F";
				sValue = Jsoup.connect(arzonConfilmUrl).execute().cookie(sName);
			}

			Document searchDoc = Jsoup.connect(arzonSearchURl + jancode)
					.cookie(sName, sValue).get();
			String itemUrl = searchDoc.getElementById("item")
					.getElementsByClass("pictlist").get(0)
					.getElementsByClass("hentry").get(0).getElementsByTag("dt")
					.get(0).getElementsByTag("a").get(0).attr("href");

			Document itemDoc = Jsoup.connect(arzonURl + itemUrl)
					.cookie(sName, sValue).get();

			returnValue[TITLE] = itemDoc.getElementById("detail_new")
					.getElementsByClass("detail_title").get(0).text().trim();

			for (Element listName : itemDoc.getElementById("detail_new")
					.getElementsByClass("item_detail").get(0)
					.getElementsByClass("list1")) {
				if (listName.text().indexOf("品番：") >= 0) {
					returnValue[MAKERNUMBER] = listName.nextElementSibling()
							.text().trim();
					break;
				}
			}
		} catch (Exception e) {
		}

		return returnValue;
	}

	//
	public String[] getiteminfo(String jancode) {
		JanCodeSearch jcsearch = new JanCodeSearch();

		// Arzon Search
		returntitle = jcsearch.getInfoFromArzon(jancode);

		// Arzon Anime Search
		if (returntitle[TITLE].equals("")
				|| returntitle[MAKERNUMBER].equals("")) {
			returntitle = jcsearch.getInfoFromArzonForAnime(jancode);
		}

		if (returntitle[TITLE].equals("")
				|| returntitle[MAKERNUMBER].equals("")) {
			/*
			 * yahoo検索にすると，APIを使用しなくて良いため．
			 */
			String yahoourl = "http://search.yahoo.co.jp/search?p=";

			returntitle[TITLE] = "";
			returntitle[MAKERNUMBER] = "";
			yahoourl = yahoourl.concat(jancode); // yahoourl + jancode
			try {
				Document searchresultdoc = Jsoup.connect(yahoourl).get();
				// yahoo検索結果のソースコード取得
				returntitle = jcsearch.getinformation(searchresultdoc); // "タイトル"+"メーカー品番"の取得
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		/*
		 * 修正箇所
		 * returntitle[MAKERNUMBER]に入っている文字列を修正
		 * 余分な余白と「廃盤」の文字を削除
		 * 
		 * returntitle[TITLE]を修正する
		 * 全角英数字を半角英数字に置き換える
		 * また置き換えた後全ての余白を半角スペースに置き換える
		 */
		
		/*
		 * 【課題番号2】
		 * 2013-09-14 Tree
		 * 全角英数字を半角に変換
		 */
		for (int i = 0; i < (returntitle == null ? 0 : returntitle.length); i++) {
			if (!"".equals(returntitle[i]) && returntitle[i] != null) {
				returntitle[i] = zenkakuToHankaku(returntitle[i]);
			}
		}

		return returntitle;
	}
	
	private String zenkakuToHankaku(String value) {
		StringBuilder sb = new StringBuilder(value);
		for (int i = 0; i < sb.length(); i++) {
			int c = (int) sb.charAt(i);
			if ((c >= 0xFF10 && c <= 0xFF19) || (c >= 0xFF21 && c <= 0xFF3A)
					|| (c >= 0xFF41 && c <= 0xFF5A)) {
				sb.setCharAt(i, (char) (c - 0xFEE0));
			}
		}
		value = sb.toString();
		return value;
	}
}
