/*
 * 2013-09-09
 * �C������
 * �ۑ�\�ԍ��u�Q�A�R�A�S�A�U�A�V�v
 * �u�C���ӏ��v�Ō��������ꏊ���C��
 */

package janCode;

/* 
 * JanCodeSearch�N���X
 * JanCodeProcess����CDocument�N���X���擾����
 * ���K�\����p���āC�^�C�g���Ə��i�R�[�h��؂�o���N���X
 *�@*/

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

	private List<String> searchedwordlist = new ArrayList<String>(); // ���X�g�^�z��ŁC�p�^�[���Ƀ}�b�`�������������X�g�ɓ����
	private String subtitle = new String(); // ��r����^�C�g�����ꎞ�I�ɕۑ�����
	private String submakernumber = new String(); // �i�ԂŐF�X�p�^�[��������̂őΉ������邽��

	private static String sName = "PHPSESSID";
	private static String sValue = null;

	public String[] getinformation(Document doc) {
		// ///�ȉ��C�����p�^�[���i���K�\���j//////////////////////////////////////////
		// ���K�\����ǉ�����ꍇ�́C�ȉ��ɂȂ���ĉ������D
		// "�A���]��:"
		arzon1[0] = "\\ .+?\\�F";
		arzon1[1] = "\\ .+?\\.";
		arzon1[2] = "\\ .+?\\-";

		// "Arzon�F"
		arzon2[0] = "\\ .+?\\�F";
		arzon2[1] = "\\ .+?\\.";
		arzon2[2] = "\\ .+?\\-";

		// "JBOOK"
		jbook1[0] = "\\�F.+?\\�F";
		jbook1[1] = "\\�F.+?\\.";
		jbook1[2] = "\\�F.+?\\-";

		// "DPG"...�P�W�փA�j���A�P�W�փQ�[��
		dpg[0] = "\\ .+?\\ ";

		// "CD|"...�P�W�փA�j���A�P�W�փQ�[�����̏ꍇ���ƁCJAN�R�[�h���������Ă��邩����S
		cdl[0] = "\\|.+?\\|";

		// "itemcode"...���i�R�[�h
		itemcode[0] = "\\ .+?\\ ";
		itemcode[1] = "\\ .+?\\.";
		itemcode[2] = "\\ .+?\\;";
		itemcode[3] = "�F.+?\\ ";
		// ///�ȏ�C�����p�^�[���i���K�\���j//////////////////////////////////////////

		Pattern pattern; // �����Ɏg�p����C���K�\����pattern���\�b�h
		Matcher matcher; // �����Ɏg�p����C���K�\����matcher���\�b�h

		/*
		 * get title algorithm 1.HTML��body�^�O�v�f��Jsoup�Ŏ擾 2.�������[�h�܂ł̕��������擾
		 * 3.�擾�p�^�[���̍쐬�@"\\ .+?\\ "�� 4.�p�^�[���}�b�`���O 5.�����ϐ����X�g�ɓ���� 6.[0]�ԖڂɃ^�C�g���������Ă���
		 * 7.�����O��̃X�y�[�X�̔r��
		 */

		String body = doc.getElementsByTag("body").text();
		// body�^�O�������\��
		// get TITLE
		if (body.indexOf("�A���]��:") != -1) {
			searchedwordlist.clear(); // �ϐ��̏�����
			int index = body.indexOf("�A���]��:"); // �������[�h�܂ł̕��������擾
			for (int i = 0; i < arzon1.length; i++) {
				pattern = Pattern.compile(arzon1[i]); // �擾�p�^�[���̍쐬
				matcher = pattern.matcher(body.substring(index)); // �p�^�[���}�b�`���O
				while (matcher.find()) {
					searchedwordlist.add(matcher.group());
				}
				if (i == 0) {
					returntitle[TITLE] = searchedwordlist.get(0);
					returntitle[TITLE] = returntitle[TITLE].substring(1,
							returntitle[TITLE].length() - 1); // ������̑O��̃X�y�[�X�����
					searchedwordlist.clear();// ��ɂ��邱�ƂŁA����searchedwordlist.get(0)�Ńq�b�g�ł���悤�ɂ���
				} else {
					subtitle = searchedwordlist.get(0);
					subtitle = subtitle.substring(1, subtitle.length() - 1); // ������̑O��̃X�y�[�X�����
					searchedwordlist.clear();
					if (returntitle[TITLE].length() > subtitle.length()) {
						returntitle[TITLE] = subtitle;
					}
				}
			}
		} else if (body.indexOf("Arzon�F") != -1) {
			// "Arzon�F"�̏ꍇ
			searchedwordlist.clear();
			int index = body.indexOf("Arzon�F"); // �������[�h�܂ł̕��������擾
			for (int i = 0; i < arzon2.length; i++) {
				pattern = Pattern.compile(arzon2[i]); // �擾�p�^�[���̍쐬
				matcher = pattern.matcher(body.substring(index)); // �p�^�[���}�b�`���O
				while (matcher.find()) {
					searchedwordlist.add(matcher.group());
				}
				try {
					if (i == 0) {
						returntitle[TITLE] = searchedwordlist.get(0);
						returntitle[TITLE] = returntitle[TITLE].substring(1,
								returntitle[TITLE].length() - 1); // ������̑O��̃X�y�[�X�����
						searchedwordlist.clear();// ��ɂ��邱�ƂŁA����searchedwordlist.get(0)�Ńq�b�g�ł���悤�ɂ���
					} else {
						subtitle = searchedwordlist.get(0);
						subtitle = subtitle.substring(1, subtitle.length() - 1); // ������̑O��̃X�y�[�X�����
						searchedwordlist.clear();
						if (returntitle[TITLE].length() > subtitle.length()) {
							returntitle[TITLE] = subtitle;
						}
					}
				} catch (IndexOutOfBoundsException e) {
				}
			}
		} else if (body.indexOf("JBOOK") != -1) {
			// "JBOOK"�̏ꍇ
			searchedwordlist.clear();
			int index = body.indexOf("JBOOK"); // �������[�h�܂ł̕��������擾
			// �p�^�[���}�b�`�������̂ł����Ƃ��Z�����̂�I��
			for (int i = 0; i < jbook1.length; i++) {
				pattern = Pattern.compile(jbook1[i]); // �擾�p�^�[���̍쐬
				matcher = pattern.matcher(body.substring(index)); // �p�^�[���}�b�`���O
				while (matcher.find()) {
					searchedwordlist.add(matcher.group());
				}
				try {
					if (i == 0) {
						returntitle[TITLE] = searchedwordlist.get(0);
						returntitle[TITLE] = returntitle[TITLE].substring(1,
								returntitle[TITLE].length() - 1); // ������̑O��̃X�y�[�X�����
						searchedwordlist.clear();// ��ɂ��邱�ƂŁA����searchedwordlist.get(0)�Ńq�b�g�ł���悤�ɂ���
					} else {
						subtitle = searchedwordlist.get(0);
						subtitle = subtitle.substring(1, subtitle.length() - 1); // ������̑O��̃X�y�[�X�����
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
			int index = body.indexOf("DPG"); // �������[�h�܂ł̕��������擾
			// �p�^�[���}�b�`�������̂ł����Ƃ��Z�����̂�I��
			for (int i = 0; i < dpg.length; i++) {
				pattern = Pattern.compile(dpg[i]); // �擾�p�^�[���̍쐬
				matcher = pattern.matcher(body.substring(index)); // �p�^�[���}�b�`���O
				while (matcher.find()) {
					searchedwordlist.add(matcher.group());
				}
				try {
					if (i == 0) {
						returntitle[TITLE] = searchedwordlist.get(0);
						returntitle[TITLE] = returntitle[TITLE].substring(1,
								returntitle[TITLE].length() - 1); // ������̑O��̃X�y�[�X�����
						searchedwordlist.clear();// ��ɂ��邱�ƂŁA����searchedwordlist.get(0)�Ńq�b�g�ł���悤�ɂ���
					} else {
						subtitle = searchedwordlist.get(0);
						subtitle = subtitle.substring(1, subtitle.length() - 1); // ������̑O��̃X�y�[�X�����
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
			int index = body.indexOf("CD|"); // �������[�h�܂ł̕��������擾
			// �p�^�[���}�b�`�������̂ł����Ƃ��Z�����̂�I��
			for (int i = 0; i < dpg.length; i++) {
				pattern = Pattern.compile(cdl[i]); // �擾�p�^�[���̍쐬
				matcher = pattern.matcher(body.substring(index)); // �p�^�[���}�b�`���O
				while (matcher.find()) {
					searchedwordlist.add(matcher.group());
				}
				try {
					if (i == 0) {
						returntitle[TITLE] = searchedwordlist.get(0);
						returntitle[TITLE] = returntitle[TITLE].substring(1,
								returntitle[TITLE].length() - 1); // ������̑O��̃X�y�[�X�����
						searchedwordlist.clear();// ��ɂ��邱�ƂŁA����searchedwordlist.get(0)�Ńq�b�g�ł���悤�ɂ���
					} else {
						subtitle = searchedwordlist.get(0);
						subtitle = subtitle.substring(1, subtitle.length() - 1); // ������̑O��̃X�y�[�X�����
						searchedwordlist.clear();
						if (returntitle[TITLE].length() > subtitle.length()) {
							returntitle[TITLE] = subtitle;
						}
					}
				} catch (IndexOutOfBoundsException e) {
				}
			}
		} else {
			// �^�C�g����������Ȃ��ꍇ
			returntitle[TITLE] = "";
		}

		// get MAKER NUMBER
		if (body.indexOf("���[�J�[�i�ԁF") != -1) {
			String regex = "\\ .+?\\ "; // �擾�p�^�[���͑O��̃X�y�[�X
			searchedwordlist.clear();
			int index = body.indexOf("���[�J�[�i�ԁF"); // �������[�h�܂ł̕��������擾
			pattern = Pattern.compile(regex); // �擾�p�^�[���̍쐬
			matcher = pattern.matcher(body.substring(index)); // �p�^�[���}�b�`���O
			while (matcher.find()) {
				searchedwordlist.add(matcher.group());
			}
			try {
				returntitle[MAKERNUMBER] = searchedwordlist.get(0);
				returntitle[MAKERNUMBER] = returntitle[MAKERNUMBER].substring(
						1, returntitle[MAKERNUMBER].length() - 1); // ������̑O��̃X�y�[�X�����
			} catch (IndexOutOfBoundsException e) {
			}

			// ������̑O��̃X�y�[�X�����
		} else if (body.indexOf("���i�ԍ�") != -1) {
			String regex = "\\ .+?\\."; // �擾�p�^�[���͑O��̃X�y�[�X
			searchedwordlist.clear();
			int index = body.indexOf("���i�ԍ�"); // �������[�h�܂ł̕��������擾
			pattern = Pattern.compile(regex); // �擾�p�^�[���̍쐬
			matcher = pattern.matcher(body.substring(index)); // �p�^�[���}�b�`���O
			while (matcher.find()) {
				searchedwordlist.add(matcher.group());
			}
			try {
				returntitle[MAKERNUMBER] = searchedwordlist.get(0);
				returntitle[MAKERNUMBER] = returntitle[MAKERNUMBER].substring(
						1, returntitle[MAKERNUMBER].length() - 1); // ������̑O��̃X�y�[�X�����
			} catch (IndexOutOfBoundsException e) {
			}
		} else if (body.indexOf("�i��") != -1) {
			int index = body.indexOf("�i��"); // �������[�h�܂ł̕��������擾
			for (int i = 0; i < itemcode.length; i++) {
				searchedwordlist.clear();
				pattern = Pattern.compile(itemcode[i]); // �擾�p�^�[���̍쐬
				matcher = pattern.matcher(body.substring(index)); // �p�^�[���}�b�`���O
				while (matcher.find()) {
					searchedwordlist.add(matcher.group());
				}
				try {
					if (i == 0) {
						returntitle[MAKERNUMBER] = searchedwordlist.get(0);
						returntitle[MAKERNUMBER] = returntitle[MAKERNUMBER]
								.substring(1,
										returntitle[MAKERNUMBER].length() - 1); // ������̑O��̃X�y�[�X�����
					} else {
						submakernumber = searchedwordlist.get(0);
						submakernumber = submakernumber.substring(1,
								submakernumber.length() - 1); // ������̑O��̃X�y�[�X�����
						pattern = Pattern.compile("-{1}"); // �擾�p�^�[���̍쐬
						matcher = pattern.matcher(returntitle[MAKERNUMBER]); // �p�^�[���}�b�`���O
						if (matcher.find()) {
							matcher = pattern.matcher(submakernumber); // �p�^�[���}�b�`���O
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
		} else if (body.indexOf("�K�i�ԍ�") != -1) {
			String regex = "\\�F.+?\\."; // �擾�p�^�[���͑O��̃X�y�[�X
			searchedwordlist.clear();
			int index = body.indexOf("�K�i�ԍ�"); // �������[�h�܂ł̕��������擾
			pattern = Pattern.compile(regex); // �擾�p�^�[���̍쐬
			matcher = pattern.matcher(body.substring(index)); // �p�^�[���}�b�`���O
			while (matcher.find()) {
				searchedwordlist.add(matcher.group());
			}
			try {
				returntitle[MAKERNUMBER] = searchedwordlist.get(0);
				returntitle[MAKERNUMBER] = returntitle[MAKERNUMBER].substring(
						1, returntitle[MAKERNUMBER].length() - 1); // ������̑O��̃X�y�[�X�����
			} catch (IndexOutOfBoundsException e) {
			}
		} else {
			// ���[�J�[�i�Ԃ�������Ȃ��ꍇ
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

			// �Z�b�V�����̊m��
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
				if (listName.text().indexOf("�i�ԁF") >= 0) {
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

			// �Z�b�V�����̊m��
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
				if (listName.text().indexOf("�i�ԁF") >= 0) {
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
			 * yahoo�����ɂ���ƁCAPI���g�p���Ȃ��ėǂ����߁D
			 */
			String yahoourl = "http://search.yahoo.co.jp/search?p=";

			returntitle[TITLE] = "";
			returntitle[MAKERNUMBER] = "";
			yahoourl = yahoourl.concat(jancode); // yahoourl + jancode
			try {
				Document searchresultdoc = Jsoup.connect(yahoourl).get();
				// yahoo�������ʂ̃\�[�X�R�[�h�擾
				returntitle = jcsearch.getinformation(searchresultdoc); // "�^�C�g��"+"���[�J�[�i��"�̎擾
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		/*
		 * �C���ӏ�
		 * returntitle[MAKERNUMBER]�ɓ����Ă��镶������C��
		 * �]���ȗ]���Ɓu�p�Ձv�̕������폜
		 * 
		 * returntitle[TITLE]���C������
		 * �S�p�p�����𔼊p�p�����ɒu��������
		 * �܂��u����������S�Ă̗]���𔼊p�X�y�[�X�ɒu��������
		 */
		
		/*
		 * �y�ۑ�ԍ�2�z
		 * 2013-09-14 Tree
		 * �S�p�p�����𔼊p�ɕϊ�
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
