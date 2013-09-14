/*
 * 2013-09-09
 * 課題管理表「６、７、１１」
 * 修正箇所で検索
 */

package Panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

final class RightPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	public static final int TITLE = 0;
	public static final int MAKERNUMBER = 1;
	private JFrame frame = null;
	private JTextField jancode_field;
	private JTextField category_field;
	private JTextField subtitle_field;
	private JTextField start_price_field;
	private JTextField end_price_field;
	private JTextArea registrationarea;
	private JTextArea descriptivearea;
	private JTextField csvfilenametextfield;
	private JRadioButton[] item_status_button = new JRadioButton[3];
	private JTextField item_status_field;
	private JLabel err_msg;
	private JCheckBox ngword_chbox;
	private JCheckBox barcode_chbox;

	// ファイル名
	protected String filename = "";
	// 開始金額
	protected String start_price = "";
	// 即決金額
	protected String end_price = "";
	// 商品状態
	protected String item_status = "新品";
	protected String item_status_note = "";
	// 説明
	protected String descriptive = "";
	// ユーザがタイトルの前に任意で付けるタイトル
	protected String subtitle = "";
	// JANコード
	protected String jancode = "";
	// カテゴリー
	protected String category = "";

	private int item_count = 1;

	// NGワード修正の有無
	protected boolean ngword = false;
	// 商品コードの修正の有無
	protected boolean barcode = false;

	final void setRightPanel(final testInputPanel tip) {
		this.frame = tip;

		int _width = 500;
		int _padding = 40;
		int _textBoxHeight = 25;
		int _textAreaHeight = 80;
		int _pricePanelHeight = 80;
		int _entryItemPanelHeight = 650;

		// 個別商品登録------------------------------------------------------start
		// JPanel entryItemPanel = new JPanel();
		setBorder(BorderFactory.createTitledBorder("個別商品登録"));
		setLayout(new FlowLayout());
		setPreferredSize(new Dimension(_width, _entryItemPanelHeight));

		/* 登録csvファイル名 */
		JPanel csvfilenamepanel = new JPanel();
		csvfilenamepanel.setBorder(BorderFactory
				.createTitledBorder("登録ファイル名(半角英数字,アンダーバー(_)のみ)")); // タイトルボーダーの設定
		GridLayout csvfilenamepanellayout = new GridLayout(); // タイトルボーダー内部に入れるために設定
		csvfilenamepanel.setLayout(csvfilenamepanellayout);

		csvfilenametextfield = new JTextField();
		csvfilenametextfield.setPreferredSize(new Dimension(_width - _padding,
				_textBoxHeight));
		csvfilenametextfield.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				chkfileName(e);
			}

			@Override
			public void focusGained(FocusEvent arg0) {
			}
		});
		csvfilenamepanel.add(csvfilenametextfield);
		add(csvfilenamepanel);

		// 価格設定------------------------------------------------------start
		JPanel price_panel = new JPanel();
		price_panel.setBorder(BorderFactory.createTitledBorder("価格設定"));
		price_panel.setLayout(new BorderLayout());
		price_panel.setPreferredSize(new Dimension(_width - _padding + 10,
				_pricePanelHeight));
		// 開始価格
		JPanel start_price_panel = new JPanel();
		start_price_panel.setBorder(BorderFactory
				.createTitledBorder("開始価格(半角の数字)（必須）"));

		start_price_field = new JTextField();
		start_price_field.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				chkstartPrice(e);
			}

			@Override
			public void focusGained(FocusEvent arg0) {
			}
		});
		start_price_field.setPreferredSize(new Dimension(
				(_width - (_padding + 12) * 2) / 2, _textBoxHeight));

		start_price_panel.add(start_price_field);
		price_panel.add("West", start_price_panel);

		// 即決価格
		JPanel end_price_panel = new JPanel();
		end_price_panel.setBorder(BorderFactory
				.createTitledBorder("即決価格(半角の数字)（任意）"));

		end_price_field = new JTextField();
		end_price_field.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				chkEndPrice(e);
			}

			@Override
			public void focusGained(FocusEvent arg0) {
			}
		});
		end_price_field.setPreferredSize(new Dimension(
				(_width - (_padding + 12) * 2) / 2, _textBoxHeight));

		end_price_panel.add(end_price_field);
		price_panel.add("East", end_price_panel);

		// 開始価格～即決価格を表現
		JLabel price_label = new JLabel("～");
		price_panel.add("Center", price_label);
		add(price_panel);
		// 価格設定------------------------------------------------------end

		// 商品の状態
		JPanel neworoldpanel = new JPanel();
		neworoldpanel.setBorder(BorderFactory.createTitledBorder("商品状態")); // タイトルボーダーの設定
		GridLayout neworoldpanellaout = new GridLayout(); // タイトルボーダー内部に入れるために設定
		neworoldpanel.setLayout(neworoldpanellaout);

		item_status_button[0] = new JRadioButton("新品", true);
		item_status_button[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chkItemStatus(e);
			}
		});

		item_status_button[1] = new JRadioButton("中古");
		item_status_button[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chkItemStatus(e);
			}
		});

		item_status_button[2] = new JRadioButton("その他");
		item_status_button[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chkItemStatus(e);
			}
		});

		neworoldpanel.add(item_status_button[0]);
		neworoldpanel.add(item_status_button[1]);
		neworoldpanel.add(item_status_button[2]);

		ButtonGroup group = new ButtonGroup();
		group.add(item_status_button[0]);
		group.add(item_status_button[1]);
		group.add(item_status_button[2]);

		add(neworoldpanel);

		// 商品状態の備考
		JPanel item_status_field_panel = new JPanel();
		item_status_field_panel.setBorder(BorderFactory
				.createTitledBorder("商品状態の備考(15字以内)"));
		item_status_field = new JTextField();
		item_status_field.setPreferredSize(new Dimension(200, 30));
		item_status_field.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if (((JTextField) e.getComponent()).getText().length() == 0) {
					item_status_note = "";
				}else{
					item_status_note = ((JTextField) e.getComponent()).getText();
				}
			}

			@Override
			public void focusGained(FocusEvent arg0) {
			}
		});

		item_status_field_panel.add(item_status_field);
		add(item_status_field_panel);

		/* 商品説明 */
		JPanel descriptiveareapanel = new JPanel();
		descriptiveareapanel.setBorder(BorderFactory
				.createTitledBorder("商品説明(必須)")); // タイトルボーダーの設定
		GridLayout descriptiveareapanellayout = new GridLayout(); // タイトルボーダー内部に入れるために設定
		descriptiveareapanel.setLayout(descriptiveareapanellayout);

		descriptivearea = new JTextArea();
		descriptivearea.setLineWrap(true);
		JScrollPane desscrollpanel = new JScrollPane(descriptivearea);
		desscrollpanel
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		desscrollpanel.setPreferredSize(new Dimension(_width - _padding,
				_textAreaHeight));
		descriptivearea.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				descriptive = ((JTextArea) e.getComponent()).getText();
				descriptive = descriptivearea.getText();
				descriptive = replaceLineSeparator(descriptive); // 改行コードの削除
				descriptive = replacecomma(descriptive); // 「,」を「，」に変換
			}

			@Override
			public void focusGained(FocusEvent arg0) {
			}
		});

		descriptiveareapanel.add(desscrollpanel);
		add(descriptiveareapanel);

		/* サブタイトル */
		JPanel subtitlepanel = new JPanel();
		subtitlepanel.setBorder(BorderFactory
				.createTitledBorder("サブタイトル（商品タイトルの先頭に追加されます）")); // タイトルボーダーの設定
		GridLayout subtitlelayout = new GridLayout(); // タイトルボーダー内部に入れるために設定
		subtitlepanel.setLayout(subtitlelayout);

		subtitle_field = new JTextField();
		subtitle_field.setPreferredSize(new Dimension(_width - _padding,
				_textBoxHeight));
		subtitle_field.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				subtitle = ((JTextField) e.getComponent()).getText();
			}

			@Override
			public void focusGained(FocusEvent arg0) {
			}
		});

		subtitlepanel.add(subtitle_field);
		add(subtitlepanel);

		/*
		 * 修正箇所「６、７」 商品コードおよびNGワードの修正機能のON,OFF機能を実装する
		 */
		JPanel barcode_ngword_panel = new JPanel();
		barcode_ngword_panel.setLayout(new GridLayout(1, 2));
		barcode_ngword_panel.setBorder(BorderFactory
				.createTitledBorder("商品コードとNGワードの修正機能")); // タイトルボーダーの設定

		/**
		 * 2013-09-14 author Ishikawa 商品コード修正の有無を選べるチェックボックスの追加
		 */
		JPanel barcode_panel = new JPanel();
		barcode_chbox = new JCheckBox("商品コード修正機能");
		barcode_chbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (barcode_chbox.isSelected()) {
					barcode = true;
				} else {
					barcode = false;
				}
			}
		});
		barcode_panel.add(barcode_chbox);
		barcode_ngword_panel.add(barcode_panel);

		/**
		 * 2013-09-14 author Ishikawa NGワードの削除するか選べるチェックボックスの追加
		 */
		JPanel ngword_panel = new JPanel();
		ngword_chbox = new JCheckBox("NGワードの削除機能");
		ngword_chbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (ngword_chbox.isSelected()) {
					ngword = true;
				} else {
					ngword = false;
				}
			}
		});
		ngword_panel.add(ngword_chbox);
		barcode_ngword_panel.add(ngword_panel);
		// 商品コード＆NGワードの機能を追加
		add(barcode_ngword_panel);

		JPanel category_jancode_panel = new JPanel();
		category_jancode_panel.setLayout(new GridLayout(1, 2));

		/* カテゴリー */
		JPanel categorypanel = new JPanel();
		categorypanel.setBorder(BorderFactory
				.createTitledBorder("カテゴリー番号(半角数字)")); // タイトルボーダーの設定
		GridLayout categorypanellayout = new GridLayout(); // タイトルボーダー内部に入れるために設定
		categorypanel.setLayout(categorypanellayout);
		category_field = new JTextField();
		category_field.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chkCategory(e);
			}
		});
		category_field.setPreferredSize(new Dimension((_width / 2) - _padding,
				_textBoxHeight));
		categorypanel.add(category_field);

		/* JANコード */
		JPanel jancodepanel = new JPanel();
		jancodepanel
				.setBorder(BorderFactory.createTitledBorder("JANコード(半角数字)")); // タイトルボーダーの設定
		GridLayout jancodepanellayout = new GridLayout(); // タイトルボーダー内部に入れるために設定
		jancodepanel.setLayout(jancodepanellayout);
		jancode_field = new JTextField();
		jancode_field.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chkJancode(e);
				tip.ItemRegister();
				JancodeClear();
				CategoryClear();
			}
		});
		jancode_field.setPreferredSize(new Dimension((_width / 2) - _padding,
				_textBoxHeight));
		jancodepanel.add(jancode_field);

		category_jancode_panel.add(categorypanel);
		category_jancode_panel.add(jancodepanel);
		add(category_jancode_panel);

		/* 登録情報 */
		JPanel registrationareapanel = new JPanel();
		registrationareapanel.setBorder(BorderFactory
				.createTitledBorder("登録情報")); // タイトルボーダーの設定
		GridLayout registrationareapanellayout = new GridLayout(); // タイトルボーダー内部に入れるために設定
		registrationareapanel.setLayout(registrationareapanellayout);
		registrationarea = new JTextArea();
		registrationarea.setLineWrap(true);
		JScrollPane regscrollpanel = new JScrollPane(registrationarea);

		regscrollpanel
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		regscrollpanel.setPreferredSize(new Dimension(_width - _padding, 130));

		registrationareapanel.add(regscrollpanel);
		add(registrationareapanel);
		// 個別商品登録------------------------------------------------------end

	}

	/*
	 * ここから下はEventの処理
	 */
	protected void chkItemStatus(ActionEvent e) {
		item_status = e.getActionCommand();
	}

	protected void chkfileName(FocusEvent e) {
		Pattern disableCharPattern = Pattern.compile("[\\\\/:\\*\\?\"<>|]+");
		Matcher disableCharMatcher = disableCharPattern.matcher(((JTextField) e
				.getComponent()).getText());

		if (((JTextField) e.getComponent()).getText().length() != 0) {
			if (!((JTextField) e.getComponent()).getText().matches(".*\\.csv$")) {
				if (frame != null) {
					err_msg = new JLabel("ファイル名の最後に「.csv」を付けてください");
					JOptionPane.showMessageDialog(frame, err_msg);
					filename = "";
					e.getComponent().requestFocusInWindow();
				}
			} else if (disableCharMatcher.find()) {
				err_msg = new JLabel(
						"ファイル名には \\, /, :, *, ?, \", <, >, | は使用出来ません");
				JOptionPane.showMessageDialog(frame, err_msg);
				filename = "";
				e.getComponent().requestFocusInWindow();
			} else {
				filename = ((JTextField) e.getComponent()).getText();
			}
		} else {
			filename = "";
		}
	}

	protected void chkstartPrice(FocusEvent e) {
		if (((JTextField) e.getComponent()).getText().length() != 0) {
			if (!((JTextField) e.getComponent()).getText().matches("[0-9]+")) {
				if (frame != null) {
					err_msg = new JLabel("開始価格は半角の数字で入力してください");
					JOptionPane.showMessageDialog(frame, err_msg);
					start_price = "";
					e.getComponent().requestFocusInWindow();
				}
			} else {
				start_price = ((JTextField) e.getComponent()).getText();
				if (!end_price_field.isEnabled()) {
					end_price_field.setText(start_price);
					end_price = start_price;
				}
			}
		} else {
			start_price = "";
		}
	}

	protected void chkEndPrice(FocusEvent e) {
		if (((JTextField) e.getComponent()).getText().length() != 0) {
			if (!((JTextField) e.getComponent()).getText().matches("[0-9]+")) {
				if (frame != null) {
					err_msg = new JLabel("即決価格は半角の数字で入力してください");
					JOptionPane.showMessageDialog(frame, err_msg);
					end_price = "";
					e.getComponent().requestFocusInWindow();
				}
			} else {
				end_price = ((JTextField) e.getComponent()).getText();
				int sp = Integer.parseInt(start_price);
				int ep = Integer.parseInt(end_price);
				if (sp > ep) {
					err_msg = new JLabel("開始価格よりも高くしてください");
					JOptionPane.showMessageDialog(frame, err_msg);
					end_price = "";
					e.getComponent().requestFocusInWindow();
				}
			}
		} else {
			end_price = "";
		}
	}

	protected void chkCategory(ActionEvent e) {
		if (e.getActionCommand().matches("[0-9]{13}")) {
			category_field.setText(category_field.getText().substring(0,
					category_field.getText().length() - 3));
			jancode_field.requestFocusInWindow();
			category = category_field.getText();
		} else {
			if (frame != null) {
				err_msg = new JLabel("カテゴリー表から正しく読み取ってください");
				JOptionPane.showMessageDialog(frame, err_msg);
				category = "";
				category_field.requestFocusInWindow();
			}
		}
	}

	protected void chkJancode(ActionEvent e) {
		if (!e.getActionCommand().matches("[0-9]{13}")) {
			if (frame != null) {
				err_msg = new JLabel("商品のバーコードを正しく読み取ってください");
				JOptionPane.showMessageDialog(frame, err_msg);
				jancode_field.requestFocusInWindow();
				jancode = "";
			}
		} else {
			jancode = e.getActionCommand();
			category_field.requestFocusInWindow();
		}
	}

	private void JancodeClear() {
		jancode = "";
		jancode_field.setText("");
	}

	private void CategoryClear() {
		category = "";
		category_field.setText("");
	}

	private String replaceLineSeparator(String str) {
		str = str.replaceAll("(\r\n|\r|\n)", ""); // windowsは"\r\n"or"\n"，Macは"\r"，Linuxは"\r\n"or"\n"
		return str;
	}

	// 「,」を「，」に変換
	private String replacecomma(String str) {
		str = str.replaceAll(",", "，");
		return str;
	}

	protected void writeRegisterInfo(String title) {
		registrationarea.append("--------------" + item_count
				+ "目の商品--------------");
		registrationarea.append("\n");
		registrationarea.append("カテゴリー：" + category + "\n");
		registrationarea.append("タイトル：" + title + "\n");
		registrationarea.append("開始金額ー即決金額　：" + start_price + "ー" + end_price
				+ "\n");
		registrationarea.append("商品状態　：" + item_status + "\n");
		item_count++;
	}

	// ここが最終確認している場所
	// 修正箇所１１
	/**
	 * 2013-09-14 author Ishikawa カテゴリー番号とjanコードが入力された後にauctown上でエラーが出る選択を
	 * していないかどうかをチェックする 使用場所:testInputPanel
	 * 
	 * @return
	 */
	protected final boolean chkRightField() {
		if (stringIsNullOrEmpty(filename)) {
			JOptionPane.showMessageDialog(this, "登録ファイル名を入力してください。");
			csvfilenametextfield.requestFocusInWindow();
			return false;
		}
		if (chkCommaTextField(filename)) {
			JOptionPane.showMessageDialog(this, "登録ファイル名に「,」は入れないでください");
			csvfilenametextfield.requestFocusInWindow();
			return false;
		}
		if (stringIsNullOrEmpty(start_price)) {
			JOptionPane.showMessageDialog(this, "開始価格を入力してください。");
			start_price_field.requestFocusInWindow();
			return false;
		}
		if (stringIsNullOrEmpty(descriptive)) {
			JOptionPane.showMessageDialog(this, "商品説明を入力してください。");
			descriptivearea.requestFocusInWindow();
			return false;
		}
		if (stringIsNullOrEmpty(category)) {
			JOptionPane.showMessageDialog(this, "カテゴリー番号を入力してください。");
			category_field.requestFocusInWindow();
			return false;
			// 商品状態がその他＆状態説明が何も書かれていない場合はエラー
		}
		if (item_status == "その他" && item_status_note == "") {
			JOptionPane.showMessageDialog(this,
					"商品状態が「その他」の場合, 「商品状態の備考」の入力は必須です。");
			item_status_field.requestFocusInWindow();
			return false;
		} else {
			return true;
		}
	}

	protected final void endPriceEnabled(boolean enabled) {
		end_price_field.setEnabled(enabled);
		if (enabled) {
			end_price_field.setBackground(Color.white);
			end_price_field.setDisabledTextColor(Color.black);
		} else {
			end_price_field.setBackground(Color.lightGray);
			end_price_field.setDisabledTextColor(Color.white);
			end_price_field.setText(start_price);
			end_price = start_price;
		}
	}

	private boolean stringIsNullOrEmpty(String str) {
		return str == null || str.equals("");
	}
	
	/**
	 * 2013-09-14
	 * author Ishikawa
	 * 全てのtextField上に「,」が存在する場合は警告を出す
	 */
	private final boolean chkCommaTextField(String text) {
		if (text.indexOf(",") >= 0) {
			return true;
		}
		return false;
	}
}
