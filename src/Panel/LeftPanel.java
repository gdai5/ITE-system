package Panel;

/*
 * 2013-09-09
 * 課題管理表「１」
 * 修正箇所で検索
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import PanelContets.Days;
import PanelContets.Prefectures;
import PanelContets.Times;

final class LeftPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JFrame frame = null;
	private JRadioButton[] sale_form_button = new JRadioButton[3];
	private String[] prefectures = new Prefectures().getPrefectures();
	private String[] days = new Days().getDays();
	private String[] times = new Times().getTimes();
	private JComboBox<String> day_box;
	private JComboBox<String> time_box;
	private JComboBox<String> prefectures_box;
	private JTextField municipality_field;
	private JTextField bank_settlement_textfield1;
	private JTextField other_settlement_textfield1;
	private JTextField send_back_textfiled;
	private JRadioButton[] shippingcharge_button;
	private JRadioButton[] payment_button;
	private JRadioButton[] send_back_button;
	private JCheckBox yahoo_settlement_chbox;
	private JCheckBox bank_settlement_chbox;
	private JCheckBox mail_settlement_chbox;
	private JCheckBox cod_settlement_chbox;
	private JCheckBox tender_exchange_chbox;
	private JCheckBox autoextension_exchange_chbox;
	private JCheckBox early_exchange_chbox;
	private JLabel error_msg;

	// 販売形式
	protected String sale_form = "オークション形式";
	protected String cut_negotiation = "いいえ";
	// 出品個数
	protected String quantity = "1";
	// 開催期間
	protected String day = "2";
	protected String time = "0";
	// 都道府県
	protected String prefecture = "北海道";
	protected String municipality = "";
	// 送料負担者
	protected String shippingcharge = "落札者";
	// 代金支払い
	protected String payment = "先払い";
	// 決済方法
	protected String yahoo_settlement = "いいえ";
	protected String bank_settlement = "はい";
	protected String mail_settlement = "いいえ";
	protected String cod_settlement = "いいえ";
	protected String bank_settlement_field = "";
	protected String other_settlement_field = "";
	// 返品の可否
	protected String send_back = "返品不可";
	protected String send_back_filed = "";
	// 取引オプション
	protected String tender_exchange = "はい";
	protected String autoextension_exchange = "はい";
	protected String early_exchange = "はい";

	final void setLeftPanel(JFrame frame) {
		this.frame = frame;

		setLayout(new FlowLayout(FlowLayout.LEFT));
		setBorder(BorderFactory.createTitledBorder("Yahooオークション設定"));
		// 販売形式------------------------------------------------------start
		JPanel saleform_panel = new JPanel();
		saleform_panel.setBorder(BorderFactory.createTitledBorder("販売形式（必須）"));

		sale_form_button[0] = new JRadioButton("オークション形式", true);
		sale_form_button[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sale_form = e.getActionCommand();
				cut_negotiation = "いいえ";
				endPriceEnabled(true);
			}
		});

		sale_form_button[1] = new JRadioButton("定額で出品");
		sale_form_button[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sale_form = e.getActionCommand();
				cut_negotiation = "いいえ";
				endPriceEnabled(false);
			}
		});

		sale_form_button[2] = new JRadioButton("定額で出品（値下げ有り）");
		sale_form_button[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sale_form = e.getActionCommand();
				cut_negotiation = "はい";
				endPriceEnabled(false);
			}
		});

		ButtonGroup sale_form_group = new ButtonGroup();
		sale_form_group.add(sale_form_button[0]);
		sale_form_group.add(sale_form_button[1]);
		sale_form_group.add(sale_form_button[2]);

		saleform_panel.add(sale_form_button[0]);
		saleform_panel.add(sale_form_button[1]);
		saleform_panel.add(sale_form_button[2]);
		// left_panel.add(saleform_panel);

		add(saleform_panel);
		// 販売形式------------------------------------------------------end

		/*
		 * 6-5 Ishikawa：個数および開催期間の設置＆データの取得
		 */
		// 個数＆開催期間------------------------------------------------------start
		JPanel quantity_priod_panel = new JPanel();
		// 個数
		JPanel quantity_panel = new JPanel();
		quantity_panel.setBorder(BorderFactory.createTitledBorder("個数"));
		JLabel quantity_label = new JLabel("  出品個数：１個  ");
		quantity_panel.add(quantity_label);
		quantity_priod_panel.add(quantity_panel);

		// 開催期間
		JPanel priod_panel = new JPanel();
		priod_panel.setBorder(BorderFactory.createTitledBorder("開催期間（必須）"));
		priod_panel.setLayout(new GridLayout(2, 2, 25, 0));

		day_box = new JComboBox<String>(days);
		day_box.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (day_box.getSelectedIndex() == -1)
					day = "non Selected";
				else
					day = (String) day_box.getSelectedItem();
			}
		});

		time_box = new JComboBox<String>(times);
		time_box.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (time_box.getSelectedIndex() == -1)
					time = "non Selected";
				else {
					time = (String) time_box.getSelectedItem();
					if ((time.length() == 9) || (time.length() == 10))
						time = time.substring(0, 1);
					else
						time = time.substring(0, 2);
				}
			}
		});

		JLabel day_label = new JLabel("何日間");
		JLabel time_label = new JLabel("終了時間");
		priod_panel.add(day_label);
		priod_panel.add(time_label);
		priod_panel.add(day_box);
		priod_panel.add(time_box);
		quantity_priod_panel.add(priod_panel);
		add(quantity_priod_panel);
		// 個数＆開催期間------------------------------------------------------end

		// 発送元------------------------------------------------------start
		JPanel send_panel = new JPanel();
		send_panel.setLayout(new GridLayout(2, 2, 0, 0));
		send_panel.setBorder(BorderFactory.createTitledBorder("商品発送元の地域（必須）"));
		JLabel prefectures_label = new JLabel("都道府県（必須）");

		prefectures_box = new JComboBox<String>(prefectures);
		prefectures_box.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (prefectures_box.getSelectedIndex() == -1)
					prefecture = "non Selected";
				else
					prefecture = (String) prefectures_box.getSelectedItem();
			}
		});

		JLabel municipality_label = new JLabel("市区町村(10字以内)（任意）");

		municipality_field = new JTextField();
		municipality_field.setPreferredSize(new Dimension(250, 30));
		municipality_field.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				chkVeryShortStringLength(e);
				municipality = ((JTextField) e.getComponent()).getText();
			}

			@Override
			public void focusGained(FocusEvent arg0) {
			}
		});

		send_panel.add(prefectures_label);
		send_panel.add(municipality_label);
		send_panel.add(prefectures_box);
		send_panel.add(municipality_field);
		add(send_panel);
		// 発送元------------------------------------------------------end

		// 送料------------------------------------------------------start
		JPanel shippingcharge_and_payment_panel = new JPanel();
		shippingcharge_and_payment_panel.setLayout(new BoxLayout(
				shippingcharge_and_payment_panel, BoxLayout.X_AXIS));

		JPanel shippingcharge_pannel = new JPanel();
		shippingcharge_pannel.setBorder(BorderFactory
				.createTitledBorder("送料負担者（必須）"));
		shippingcharge_button = new JRadioButton[2];

		shippingcharge_button[0] = new JRadioButton("落札者", true);
		shippingcharge_button[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chkShippingCharger(e);
			}
		});

		shippingcharge_button[1] = new JRadioButton("出品者");
		shippingcharge_button[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chkShippingCharger(e);
			}
		});

		shippingcharge_button[0].setPreferredSize(new Dimension(100, 30));
		shippingcharge_button[1].setPreferredSize(new Dimension(100, 30));

		ButtonGroup shippingcharge_group = new ButtonGroup();
		shippingcharge_group.add(shippingcharge_button[0]);
		shippingcharge_group.add(shippingcharge_button[1]);

		shippingcharge_pannel.add(shippingcharge_button[0]);
		shippingcharge_pannel.add(shippingcharge_button[1]);
		add(shippingcharge_pannel);
		// 送料------------------------------------------------------end

		// 代金------------------------------------------------------start
		JPanel payment_pannel = new JPanel();
		payment_pannel.setBorder(BorderFactory
				.createTitledBorder("代金先払い、後払い（必須）"));
		payment_button = new JRadioButton[2];

		payment_button[0] = new JRadioButton("先払い", true);
		payment_button[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chkPayment(e);
			}
		});

		payment_button[1] = new JRadioButton("後払い");
		payment_button[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chkPayment(e);
			}
		});

		payment_button[0].setPreferredSize(new Dimension(100, 30));
		payment_button[1].setPreferredSize(new Dimension(100, 30));

		ButtonGroup payment_group = new ButtonGroup();
		payment_group.add(payment_button[0]);
		payment_group.add(payment_button[1]);

		payment_pannel.add(payment_button[0]);
		payment_pannel.add(payment_button[1]);
		shippingcharge_and_payment_panel.add(payment_pannel);

		add(shippingcharge_and_payment_panel);
		// 代金------------------------------------------------------end

		// 決済方法------------------------------------------------------start
		JPanel settlement_panel = new JPanel();
		settlement_panel
				.setBorder(BorderFactory.createTitledBorder("決済方法（必須）"));
		settlement_panel.setLayout(new GridLayout(2, 1, 0, 0));

		JPanel settlement_button_panel = new JPanel();

		yahoo_settlement_chbox = new JCheckBox("Yahooかんたん決済");
		yahoo_settlement_chbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chkSettlement(e);
				if (yahoo_settlement_chbox.isSelected())
					yahoo_settlement = "はい";
				else
					yahoo_settlement = "いいえ";
			}
		});

		// bug
		// 今のままだと銀行振込にチェックを入れていない状態で銀行名だけを入力できる
		// この場合auctownにアップロードするとエラーが起きてしまう
		bank_settlement_chbox = new JCheckBox("銀行振込", true);
		bank_settlement_chbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chkSettlement(e);
				if (bank_settlement_chbox.isSelected())
					bank_settlement = "はい";
				else
					bank_settlement = "いいえ";
			}
		});

		mail_settlement_chbox = new JCheckBox("現金書留");
		mail_settlement_chbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chkSettlement(e);
				if (mail_settlement_chbox.isSelected())
					mail_settlement = "はい";
				else
					mail_settlement = "いいえ";
			}
		});

		cod_settlement_chbox = new JCheckBox("商品代引");
		cod_settlement_chbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chkSettlement(e);
				if (cod_settlement_chbox.isSelected())
					cod_settlement = "はい";
				else
					cod_settlement = "いいえ";
			}
		});

		settlement_button_panel.add(yahoo_settlement_chbox);
		settlement_button_panel.add(bank_settlement_chbox);
		settlement_button_panel.add(mail_settlement_chbox);
		settlement_button_panel.add(cod_settlement_chbox);

		settlement_panel.add(settlement_button_panel);

		JPanel settlement_text_panel = new JPanel();
		settlement_text_panel.setLayout(new GridLayout(1, 2));

		JPanel left_settlement_text_panel = new JPanel();
		left_settlement_text_panel.setBorder(BorderFactory
				.createLineBorder(Color.black));
		left_settlement_text_panel.setLayout(new GridLayout(2, 1));
		JLabel bank_settlement_label = new JLabel("受け取り先銀行名の記入(15字以内)（任意）");

		bank_settlement_textfield1 = new JTextField();
		bank_settlement_textfield1.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				chkShortStringLength(e);
				bank_settlement_field = ((JTextField) e.getComponent())
						.getText();
			}

			@Override
			public void focusGained(FocusEvent arg0) {
			}
		});

		left_settlement_text_panel.add(bank_settlement_label);
		left_settlement_text_panel.add(bank_settlement_textfield1);
		settlement_text_panel.add(left_settlement_text_panel);

		JPanel right_settlement_text_panel = new JPanel();
		right_settlement_text_panel.setBorder(BorderFactory
				.createLineBorder(Color.black));
		right_settlement_text_panel.setLayout(new GridLayout(2, 1));
		JLabel other_settlement_label = new JLabel("他の決済方法(15字以内)（任意）");
		other_settlement_textfield1 = new JTextField();
		other_settlement_textfield1.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				chkShortStringLength(e);
				other_settlement_field = ((JTextField) e.getComponent())
						.getText();
			}

			@Override
			public void focusGained(FocusEvent arg0) {
			}
		});
		right_settlement_text_panel.add(other_settlement_label);
		right_settlement_text_panel.add(other_settlement_textfield1);
		settlement_text_panel.add(right_settlement_text_panel);

		settlement_panel.add(settlement_text_panel);
		add(settlement_panel);

		JPanel send_back_panel = new JPanel();
		send_back_panel
				.setBorder(BorderFactory.createTitledBorder("返品の可否（必須）"));
		send_back_panel.setLayout(new GridLayout(1, 2, 0, 0));

		JPanel send_back_button_panel = new JPanel();
		send_back_button = new JRadioButton[2];

		send_back_button[0] = new JRadioButton("返品不可", true);
		send_back_button[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chkSendBack(e);
			}
		});

		send_back_button[1] = new JRadioButton("返品可");
		send_back_button[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chkSendBack(e);
			}
		});

		ButtonGroup send_back_group = new ButtonGroup();
		send_back_group.add(send_back_button[0]);
		send_back_group.add(send_back_button[1]);
		send_back_button_panel.add(send_back_button[0]);
		send_back_button_panel.add(send_back_button[1]);
		send_back_panel.add(send_back_button_panel);

		JPanel send_back_textfiled_panel = new JPanel();
		send_back_textfiled_panel.setBorder(BorderFactory
				.createTitledBorder("備考欄(30字以内)（任意）"));
		send_back_textfiled = new JTextField();
		send_back_textfiled.setPreferredSize(new Dimension(230, 30));
		send_back_textfiled.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				chkLongStringLength(e);
				send_back_filed = ((JTextField) e.getComponent()).getText();
			}

			@Override
			public void focusGained(FocusEvent arg0) {
			}
		});
		send_back_textfiled_panel.add(send_back_textfiled);
		send_back_panel.add(send_back_textfiled_panel);

		add(send_back_panel);

		JPanel exchange_panel = new JPanel();
		exchange_panel.setBorder(BorderFactory
				.createTitledBorder("取引オプション（任意）"));

		tender_exchange_chbox = new JCheckBox("入札者評価制限あり", true);
		tender_exchange_chbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tender_exchange_chbox.isSelected())
					tender_exchange = "はい";
				else
					tender_exchange = "いいえ";
			}
		});

		autoextension_exchange_chbox = new JCheckBox("自動延長あり", true);
		autoextension_exchange_chbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (autoextension_exchange_chbox.isSelected())
					autoextension_exchange = "はい";
				else
					autoextension_exchange = "いいえ";
			}
		});

		early_exchange_chbox = new JCheckBox("早期終了あり", true);
		early_exchange_chbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (early_exchange_chbox.isSelected())
					early_exchange = "はい";
				else
					early_exchange = "いいえ";
			}
		});

		exchange_panel.add(tender_exchange_chbox);
		exchange_panel.add(autoextension_exchange_chbox);
		exchange_panel.add(early_exchange_chbox);
		add(exchange_panel);

		/*
		 * 修正箇所 ここに新しい項目として「再出品回数」をしていできるプルダウンボックスを設置
		 */
	}

	/**
	 * 2013-09-14 author Ishikawa 
	 * 処理： カテゴリー番号とjanコードが入力されて商品検索が行われる前に
	 * 画面の左半分が正しく入力されているか確認する
	 * 
	 * @return
	 */
	protected final boolean chkLeftField() {
		if (bank_settlement == "いいえ" && bank_settlement_field != "") {
			JOptionPane.showMessageDialog(this,
					"「銀行名」を入力したら, 必ず「銀行振込」にチェックを入れてください。\n"
							+ "銀行振込をチェックしない場合は「銀行名」を入力しないでください。");
			bank_settlement_chbox.requestFocusInWindow();
			return false;
		}
		return true;
	}

	/**
	 * 市区町村は１０文字以内のため、新しく追加
	 * 
	 * @param e
	 */
	protected void chkVeryShortStringLength(FocusEvent e) {
		if (((JTextField) e.getComponent()).getText().length() > 10) {
			if (frame != null) {
				error_msg = new JLabel("10文字以内に納めて下さい（現在の文字数："
						+ ((JTextField) e.getComponent()).getText().length()
						+ ")");
				JOptionPane.showMessageDialog(frame, error_msg);
				e.getComponent().requestFocusInWindow();
			}
		}
	}

	// リスナーの中で呼ぶため
	protected void chkShortStringLength(FocusEvent e) {
		if (((JTextField) e.getComponent()).getText().length() > 15) {
			if (frame != null) {
				error_msg = new JLabel("15文字以内に納めて下さい（現在の文字数："
						+ ((JTextField) e.getComponent()).getText().length()
						+ ")");
				JOptionPane.showMessageDialog(frame, error_msg);
				e.getComponent().requestFocusInWindow();
			}
		}
	}

	protected void chkLongStringLength(FocusEvent e) {
		if (((JTextField) e.getComponent()).getText().length() > 30) {
			if (frame != null) {
				error_msg = new JLabel("30文字以内に納めて下さい（現在の文字数："
						+ ((JTextField) e.getComponent()).getText().length()
						+ ")");
				JOptionPane.showMessageDialog(frame, error_msg);
				e.getComponent().requestFocusInWindow();
			}
		}
	}

	protected void chkShippingCharger(ActionEvent e) {
		shippingcharge = e.getActionCommand();
	}

	protected void chkPayment(ActionEvent e) {
		payment = e.getActionCommand();
	}

	protected void chkSettlement(ActionEvent e) {
		if (yahoo_settlement_chbox.isSelected()) {
		} else if (bank_settlement_chbox.isSelected()) {
		} else if (mail_settlement_chbox.isSelected()) {
		} else if (cod_settlement_chbox.isSelected()) {
		} else {
			if (frame != null) {
				error_msg = new JLabel("最低一つはチェックしてください");
				JOptionPane.showMessageDialog(frame, error_msg);
				yahoo_settlement_chbox.setSelected(true);
				yahoo_settlement = "はい";
			}
		}
	}

	protected void chkSendBack(ActionEvent e) {
		send_back = e.getActionCommand();
	}

	protected final void endPriceEnabled(boolean enabled) {
		if (this.frame instanceof testInputPanel) {
			((testInputPanel) this.frame).rp.endPriceEnabled(enabled);
		}
	}
}
