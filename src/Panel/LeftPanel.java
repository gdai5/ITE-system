package Panel;

/*
 * 2013-09-09
 * �ۑ�Ǘ��\�u�P�v
 * �C���ӏ��Ō���
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

	// �̔��`��
	protected String sale_form = "�I�[�N�V�����`��";
	protected String cut_negotiation = "������";
	// �o�i��
	protected String quantity = "1";
	// �J�Ê���
	protected String day = "2";
	protected String time = "0";
	// �s���{��
	protected String prefecture = "�k�C��";
	protected String municipality = "";
	// �������S��
	protected String shippingcharge = "���D��";
	// ����x����
	protected String payment = "�敥��";
	// ���ϕ��@
	protected String yahoo_settlement = "������";
	protected String bank_settlement = "�͂�";
	protected String mail_settlement = "������";
	protected String cod_settlement = "������";
	protected String bank_settlement_field = "";
	protected String other_settlement_field = "";
	// �ԕi�̉�
	protected String send_back = "�ԕi�s��";
	protected String send_back_filed = "";
	// ����I�v�V����
	protected String tender_exchange = "�͂�";
	protected String autoextension_exchange = "�͂�";
	protected String early_exchange = "�͂�";

	final void setLeftPanel(JFrame frame) {
		this.frame = frame;

		setLayout(new FlowLayout(FlowLayout.LEFT));
		setBorder(BorderFactory.createTitledBorder("Yahoo�I�[�N�V�����ݒ�"));
		// �̔��`��------------------------------------------------------start
		JPanel saleform_panel = new JPanel();
		saleform_panel.setBorder(BorderFactory.createTitledBorder("�̔��`���i�K�{�j"));

		sale_form_button[0] = new JRadioButton("�I�[�N�V�����`��", true);
		sale_form_button[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sale_form = e.getActionCommand();
				cut_negotiation = "������";
				endPriceEnabled(true);
			}
		});

		sale_form_button[1] = new JRadioButton("��z�ŏo�i");
		sale_form_button[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sale_form = e.getActionCommand();
				cut_negotiation = "������";
				endPriceEnabled(false);
			}
		});

		sale_form_button[2] = new JRadioButton("��z�ŏo�i�i�l�����L��j");
		sale_form_button[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sale_form = e.getActionCommand();
				cut_negotiation = "�͂�";
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
		// �̔��`��------------------------------------------------------end

		/*
		 * 6-5 Ishikawa�F������ъJ�Ê��Ԃ̐ݒu���f�[�^�̎擾
		 */
		// �����J�Ê���------------------------------------------------------start
		JPanel quantity_priod_panel = new JPanel();
		// ��
		JPanel quantity_panel = new JPanel();
		quantity_panel.setBorder(BorderFactory.createTitledBorder("��"));
		JLabel quantity_label = new JLabel("  �o�i���F�P��  ");
		quantity_panel.add(quantity_label);
		quantity_priod_panel.add(quantity_panel);

		// �J�Ê���
		JPanel priod_panel = new JPanel();
		priod_panel.setBorder(BorderFactory.createTitledBorder("�J�Ê��ԁi�K�{�j"));
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

		JLabel day_label = new JLabel("������");
		JLabel time_label = new JLabel("�I������");
		priod_panel.add(day_label);
		priod_panel.add(time_label);
		priod_panel.add(day_box);
		priod_panel.add(time_box);
		quantity_priod_panel.add(priod_panel);
		add(quantity_priod_panel);
		// �����J�Ê���------------------------------------------------------end

		// ������------------------------------------------------------start
		JPanel send_panel = new JPanel();
		send_panel.setLayout(new GridLayout(2, 2, 0, 0));
		send_panel.setBorder(BorderFactory.createTitledBorder("���i�������̒n��i�K�{�j"));
		JLabel prefectures_label = new JLabel("�s���{���i�K�{�j");

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

		JLabel municipality_label = new JLabel("�s�撬��(10���ȓ�)�i�C�Ӂj");

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
		// ������------------------------------------------------------end

		// ����------------------------------------------------------start
		JPanel shippingcharge_and_payment_panel = new JPanel();
		shippingcharge_and_payment_panel.setLayout(new BoxLayout(
				shippingcharge_and_payment_panel, BoxLayout.X_AXIS));

		JPanel shippingcharge_pannel = new JPanel();
		shippingcharge_pannel.setBorder(BorderFactory
				.createTitledBorder("�������S�ҁi�K�{�j"));
		shippingcharge_button = new JRadioButton[2];

		shippingcharge_button[0] = new JRadioButton("���D��", true);
		shippingcharge_button[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chkShippingCharger(e);
			}
		});

		shippingcharge_button[1] = new JRadioButton("�o�i��");
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
		// ����------------------------------------------------------end

		// ���------------------------------------------------------start
		JPanel payment_pannel = new JPanel();
		payment_pannel.setBorder(BorderFactory
				.createTitledBorder("����敥���A�㕥���i�K�{�j"));
		payment_button = new JRadioButton[2];

		payment_button[0] = new JRadioButton("�敥��", true);
		payment_button[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chkPayment(e);
			}
		});

		payment_button[1] = new JRadioButton("�㕥��");
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
		// ���------------------------------------------------------end

		// ���ϕ��@------------------------------------------------------start
		JPanel settlement_panel = new JPanel();
		settlement_panel
				.setBorder(BorderFactory.createTitledBorder("���ϕ��@�i�K�{�j"));
		settlement_panel.setLayout(new GridLayout(2, 1, 0, 0));

		JPanel settlement_button_panel = new JPanel();

		yahoo_settlement_chbox = new JCheckBox("Yahoo���񂽂񌈍�");
		yahoo_settlement_chbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chkSettlement(e);
				if (yahoo_settlement_chbox.isSelected())
					yahoo_settlement = "�͂�";
				else
					yahoo_settlement = "������";
			}
		});

		// bug
		// ���̂܂܂��Ƌ�s�U���Ƀ`�F�b�N�����Ă��Ȃ���Ԃŋ�s����������͂ł���
		// ���̏ꍇauctown�ɃA�b�v���[�h����ƃG���[���N���Ă��܂�
		bank_settlement_chbox = new JCheckBox("��s�U��", true);
		bank_settlement_chbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chkSettlement(e);
				if (bank_settlement_chbox.isSelected())
					bank_settlement = "�͂�";
				else
					bank_settlement = "������";
			}
		});

		mail_settlement_chbox = new JCheckBox("��������");
		mail_settlement_chbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chkSettlement(e);
				if (mail_settlement_chbox.isSelected())
					mail_settlement = "�͂�";
				else
					mail_settlement = "������";
			}
		});

		cod_settlement_chbox = new JCheckBox("���i���");
		cod_settlement_chbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chkSettlement(e);
				if (cod_settlement_chbox.isSelected())
					cod_settlement = "�͂�";
				else
					cod_settlement = "������";
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
		JLabel bank_settlement_label = new JLabel("�󂯎����s���̋L��(15���ȓ�)�i�C�Ӂj");

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
		JLabel other_settlement_label = new JLabel("���̌��ϕ��@(15���ȓ�)�i�C�Ӂj");
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
				.setBorder(BorderFactory.createTitledBorder("�ԕi�̉ہi�K�{�j"));
		send_back_panel.setLayout(new GridLayout(1, 2, 0, 0));

		JPanel send_back_button_panel = new JPanel();
		send_back_button = new JRadioButton[2];

		send_back_button[0] = new JRadioButton("�ԕi�s��", true);
		send_back_button[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chkSendBack(e);
			}
		});

		send_back_button[1] = new JRadioButton("�ԕi��");
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
				.createTitledBorder("���l��(30���ȓ�)�i�C�Ӂj"));
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
				.createTitledBorder("����I�v�V�����i�C�Ӂj"));

		tender_exchange_chbox = new JCheckBox("���D�ҕ]����������", true);
		tender_exchange_chbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tender_exchange_chbox.isSelected())
					tender_exchange = "�͂�";
				else
					tender_exchange = "������";
			}
		});

		autoextension_exchange_chbox = new JCheckBox("������������", true);
		autoextension_exchange_chbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (autoextension_exchange_chbox.isSelected())
					autoextension_exchange = "�͂�";
				else
					autoextension_exchange = "������";
			}
		});

		early_exchange_chbox = new JCheckBox("�����I������", true);
		early_exchange_chbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (early_exchange_chbox.isSelected())
					early_exchange = "�͂�";
				else
					early_exchange = "������";
			}
		});

		exchange_panel.add(tender_exchange_chbox);
		exchange_panel.add(autoextension_exchange_chbox);
		exchange_panel.add(early_exchange_chbox);
		add(exchange_panel);

		/*
		 * �C���ӏ� �����ɐV�������ڂƂ��āu�ďo�i�񐔁v�����Ă��ł���v���_�E���{�b�N�X��ݒu
		 */
	}

	/**
	 * 2013-09-14 author Ishikawa 
	 * �����F �J�e�S���[�ԍ���jan�R�[�h�����͂���ď��i�������s����O��
	 * ��ʂ̍����������������͂���Ă��邩�m�F����
	 * 
	 * @return
	 */
	protected final boolean chkLeftField() {
		if (bank_settlement == "������" && bank_settlement_field != "") {
			JOptionPane.showMessageDialog(this,
					"�u��s���v����͂�����, �K���u��s�U���v�Ƀ`�F�b�N�����Ă��������B\n"
							+ "��s�U�����`�F�b�N���Ȃ��ꍇ�́u��s���v����͂��Ȃ��ł��������B");
			bank_settlement_chbox.requestFocusInWindow();
			return false;
		}
		return true;
	}

	/**
	 * �s�撬���͂P�O�����ȓ��̂��߁A�V�����ǉ�
	 * 
	 * @param e
	 */
	protected void chkVeryShortStringLength(FocusEvent e) {
		if (((JTextField) e.getComponent()).getText().length() > 10) {
			if (frame != null) {
				error_msg = new JLabel("10�����ȓ��ɔ[�߂ĉ������i���݂̕������F"
						+ ((JTextField) e.getComponent()).getText().length()
						+ ")");
				JOptionPane.showMessageDialog(frame, error_msg);
				e.getComponent().requestFocusInWindow();
			}
		}
	}

	// ���X�i�[�̒��ŌĂԂ���
	protected void chkShortStringLength(FocusEvent e) {
		if (((JTextField) e.getComponent()).getText().length() > 15) {
			if (frame != null) {
				error_msg = new JLabel("15�����ȓ��ɔ[�߂ĉ������i���݂̕������F"
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
				error_msg = new JLabel("30�����ȓ��ɔ[�߂ĉ������i���݂̕������F"
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
				error_msg = new JLabel("�Œ��̓`�F�b�N���Ă�������");
				JOptionPane.showMessageDialog(frame, error_msg);
				yahoo_settlement_chbox.setSelected(true);
				yahoo_settlement = "�͂�";
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
