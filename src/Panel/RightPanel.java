/*
 * 2013-09-09
 * �ۑ�Ǘ��\�u�U�A�V�A�P�P�v
 * �C���ӏ��Ō���
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

	// �t�@�C����
	protected String filename = "";
	// �J�n���z
	protected String start_price = "";
	// �������z
	protected String end_price = "";
	// ���i���
	protected String item_status = "�V�i";
	protected String item_status_note = "";
	// ����
	protected String descriptive = "";
	// ���[�U���^�C�g���̑O�ɔC�ӂŕt����^�C�g��
	protected String subtitle = "";
	// JAN�R�[�h
	protected String jancode = "";
	// �J�e�S���[
	protected String category = "";

	private int item_count = 1;

	// NG���[�h�C���̗L��
	protected boolean ngword = false;
	// ���i�R�[�h�̏C���̗L��
	protected boolean barcode = false;

	final void setRightPanel(final testInputPanel tip) {
		this.frame = tip;

		int _width = 500;
		int _padding = 40;
		int _textBoxHeight = 25;
		int _textAreaHeight = 80;
		int _pricePanelHeight = 80;
		int _entryItemPanelHeight = 650;

		// �ʏ��i�o�^------------------------------------------------------start
		// JPanel entryItemPanel = new JPanel();
		setBorder(BorderFactory.createTitledBorder("�ʏ��i�o�^"));
		setLayout(new FlowLayout());
		setPreferredSize(new Dimension(_width, _entryItemPanelHeight));

		/* �o�^csv�t�@�C���� */
		JPanel csvfilenamepanel = new JPanel();
		csvfilenamepanel.setBorder(BorderFactory
				.createTitledBorder("�o�^�t�@�C����(���p�p����,�A���_�[�o�[(_)�̂�)")); // �^�C�g���{�[�_�[�̐ݒ�
		GridLayout csvfilenamepanellayout = new GridLayout(); // �^�C�g���{�[�_�[�����ɓ���邽�߂ɐݒ�
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

		// ���i�ݒ�------------------------------------------------------start
		JPanel price_panel = new JPanel();
		price_panel.setBorder(BorderFactory.createTitledBorder("���i�ݒ�"));
		price_panel.setLayout(new BorderLayout());
		price_panel.setPreferredSize(new Dimension(_width - _padding + 10,
				_pricePanelHeight));
		// �J�n���i
		JPanel start_price_panel = new JPanel();
		start_price_panel.setBorder(BorderFactory
				.createTitledBorder("�J�n���i(���p�̐���)�i�K�{�j"));

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

		// �������i
		JPanel end_price_panel = new JPanel();
		end_price_panel.setBorder(BorderFactory
				.createTitledBorder("�������i(���p�̐���)�i�C�Ӂj"));

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

		// �J�n���i�`�������i��\��
		JLabel price_label = new JLabel("�`");
		price_panel.add("Center", price_label);
		add(price_panel);
		// ���i�ݒ�------------------------------------------------------end

		// ���i�̏��
		JPanel neworoldpanel = new JPanel();
		neworoldpanel.setBorder(BorderFactory.createTitledBorder("���i���")); // �^�C�g���{�[�_�[�̐ݒ�
		GridLayout neworoldpanellaout = new GridLayout(); // �^�C�g���{�[�_�[�����ɓ���邽�߂ɐݒ�
		neworoldpanel.setLayout(neworoldpanellaout);

		item_status_button[0] = new JRadioButton("�V�i", true);
		item_status_button[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chkItemStatus(e);
			}
		});

		item_status_button[1] = new JRadioButton("����");
		item_status_button[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chkItemStatus(e);
			}
		});

		item_status_button[2] = new JRadioButton("���̑�");
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

		// ���i��Ԃ̔��l
		JPanel item_status_field_panel = new JPanel();
		item_status_field_panel.setBorder(BorderFactory
				.createTitledBorder("���i��Ԃ̔��l(15���ȓ�)"));
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

		/* ���i���� */
		JPanel descriptiveareapanel = new JPanel();
		descriptiveareapanel.setBorder(BorderFactory
				.createTitledBorder("���i����(�K�{)")); // �^�C�g���{�[�_�[�̐ݒ�
		GridLayout descriptiveareapanellayout = new GridLayout(); // �^�C�g���{�[�_�[�����ɓ���邽�߂ɐݒ�
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
				descriptive = replaceLineSeparator(descriptive); // ���s�R�[�h�̍폜
				descriptive = replacecomma(descriptive); // �u,�v���u�C�v�ɕϊ�
			}

			@Override
			public void focusGained(FocusEvent arg0) {
			}
		});

		descriptiveareapanel.add(desscrollpanel);
		add(descriptiveareapanel);

		/* �T�u�^�C�g�� */
		JPanel subtitlepanel = new JPanel();
		subtitlepanel.setBorder(BorderFactory
				.createTitledBorder("�T�u�^�C�g���i���i�^�C�g���̐擪�ɒǉ�����܂��j")); // �^�C�g���{�[�_�[�̐ݒ�
		GridLayout subtitlelayout = new GridLayout(); // �^�C�g���{�[�_�[�����ɓ���邽�߂ɐݒ�
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
		 * �C���ӏ��u�U�A�V�v ���i�R�[�h�����NG���[�h�̏C���@�\��ON,OFF�@�\����������
		 */
		JPanel barcode_ngword_panel = new JPanel();
		barcode_ngword_panel.setLayout(new GridLayout(1, 2));
		barcode_ngword_panel.setBorder(BorderFactory
				.createTitledBorder("���i�R�[�h��NG���[�h�̏C���@�\")); // �^�C�g���{�[�_�[�̐ݒ�

		/**
		 * 2013-09-14 author Ishikawa ���i�R�[�h�C���̗L����I�ׂ�`�F�b�N�{�b�N�X�̒ǉ�
		 */
		JPanel barcode_panel = new JPanel();
		barcode_chbox = new JCheckBox("���i�R�[�h�C���@�\");
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
		 * 2013-09-14 author Ishikawa NG���[�h�̍폜���邩�I�ׂ�`�F�b�N�{�b�N�X�̒ǉ�
		 */
		JPanel ngword_panel = new JPanel();
		ngword_chbox = new JCheckBox("NG���[�h�̍폜�@�\");
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
		// ���i�R�[�h��NG���[�h�̋@�\��ǉ�
		add(barcode_ngword_panel);

		JPanel category_jancode_panel = new JPanel();
		category_jancode_panel.setLayout(new GridLayout(1, 2));

		/* �J�e�S���[ */
		JPanel categorypanel = new JPanel();
		categorypanel.setBorder(BorderFactory
				.createTitledBorder("�J�e�S���[�ԍ�(���p����)")); // �^�C�g���{�[�_�[�̐ݒ�
		GridLayout categorypanellayout = new GridLayout(); // �^�C�g���{�[�_�[�����ɓ���邽�߂ɐݒ�
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

		/* JAN�R�[�h */
		JPanel jancodepanel = new JPanel();
		jancodepanel
				.setBorder(BorderFactory.createTitledBorder("JAN�R�[�h(���p����)")); // �^�C�g���{�[�_�[�̐ݒ�
		GridLayout jancodepanellayout = new GridLayout(); // �^�C�g���{�[�_�[�����ɓ���邽�߂ɐݒ�
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

		/* �o�^��� */
		JPanel registrationareapanel = new JPanel();
		registrationareapanel.setBorder(BorderFactory
				.createTitledBorder("�o�^���")); // �^�C�g���{�[�_�[�̐ݒ�
		GridLayout registrationareapanellayout = new GridLayout(); // �^�C�g���{�[�_�[�����ɓ���邽�߂ɐݒ�
		registrationareapanel.setLayout(registrationareapanellayout);
		registrationarea = new JTextArea();
		registrationarea.setLineWrap(true);
		JScrollPane regscrollpanel = new JScrollPane(registrationarea);

		regscrollpanel
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		regscrollpanel.setPreferredSize(new Dimension(_width - _padding, 130));

		registrationareapanel.add(regscrollpanel);
		add(registrationareapanel);
		// �ʏ��i�o�^------------------------------------------------------end

	}

	/*
	 * �������牺��Event�̏���
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
					err_msg = new JLabel("�t�@�C�����̍Ō�Ɂu.csv�v��t���Ă�������");
					JOptionPane.showMessageDialog(frame, err_msg);
					filename = "";
					e.getComponent().requestFocusInWindow();
				}
			} else if (disableCharMatcher.find()) {
				err_msg = new JLabel(
						"�t�@�C�����ɂ� \\, /, :, *, ?, \", <, >, | �͎g�p�o���܂���");
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
					err_msg = new JLabel("�J�n���i�͔��p�̐����œ��͂��Ă�������");
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
					err_msg = new JLabel("�������i�͔��p�̐����œ��͂��Ă�������");
					JOptionPane.showMessageDialog(frame, err_msg);
					end_price = "";
					e.getComponent().requestFocusInWindow();
				}
			} else {
				end_price = ((JTextField) e.getComponent()).getText();
				int sp = Integer.parseInt(start_price);
				int ep = Integer.parseInt(end_price);
				if (sp > ep) {
					err_msg = new JLabel("�J�n���i�����������Ă�������");
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
				err_msg = new JLabel("�J�e�S���[�\���琳�����ǂݎ���Ă�������");
				JOptionPane.showMessageDialog(frame, err_msg);
				category = "";
				category_field.requestFocusInWindow();
			}
		}
	}

	protected void chkJancode(ActionEvent e) {
		if (!e.getActionCommand().matches("[0-9]{13}")) {
			if (frame != null) {
				err_msg = new JLabel("���i�̃o�[�R�[�h�𐳂����ǂݎ���Ă�������");
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
		str = str.replaceAll("(\r\n|\r|\n)", ""); // windows��"\r\n"or"\n"�CMac��"\r"�CLinux��"\r\n"or"\n"
		return str;
	}

	// �u,�v���u�C�v�ɕϊ�
	private String replacecomma(String str) {
		str = str.replaceAll(",", "�C");
		return str;
	}

	protected void writeRegisterInfo(String title) {
		registrationarea.append("--------------" + item_count
				+ "�ڂ̏��i--------------");
		registrationarea.append("\n");
		registrationarea.append("�J�e�S���[�F" + category + "\n");
		registrationarea.append("�^�C�g���F" + title + "\n");
		registrationarea.append("�J�n���z�[�������z�@�F" + start_price + "�[" + end_price
				+ "\n");
		registrationarea.append("���i��ԁ@�F" + item_status + "\n");
		item_count++;
	}

	// �������ŏI�m�F���Ă���ꏊ
	// �C���ӏ��P�P
	/**
	 * 2013-09-14 author Ishikawa �J�e�S���[�ԍ���jan�R�[�h�����͂��ꂽ���auctown��ŃG���[���o��I����
	 * ���Ă��Ȃ����ǂ������`�F�b�N���� �g�p�ꏊ:testInputPanel
	 * 
	 * @return
	 */
	protected final boolean chkRightField() {
		if (stringIsNullOrEmpty(filename)) {
			JOptionPane.showMessageDialog(this, "�o�^�t�@�C��������͂��Ă��������B");
			csvfilenametextfield.requestFocusInWindow();
			return false;
		}
		if (chkCommaTextField(filename)) {
			JOptionPane.showMessageDialog(this, "�o�^�t�@�C�����Ɂu,�v�͓���Ȃ��ł�������");
			csvfilenametextfield.requestFocusInWindow();
			return false;
		}
		if (stringIsNullOrEmpty(start_price)) {
			JOptionPane.showMessageDialog(this, "�J�n���i����͂��Ă��������B");
			start_price_field.requestFocusInWindow();
			return false;
		}
		if (stringIsNullOrEmpty(descriptive)) {
			JOptionPane.showMessageDialog(this, "���i��������͂��Ă��������B");
			descriptivearea.requestFocusInWindow();
			return false;
		}
		if (stringIsNullOrEmpty(category)) {
			JOptionPane.showMessageDialog(this, "�J�e�S���[�ԍ�����͂��Ă��������B");
			category_field.requestFocusInWindow();
			return false;
			// ���i��Ԃ����̑�����Ԑ���������������Ă��Ȃ��ꍇ�̓G���[
		}
		if (item_status == "���̑�" && item_status_note == "") {
			JOptionPane.showMessageDialog(this,
					"���i��Ԃ��u���̑��v�̏ꍇ, �u���i��Ԃ̔��l�v�̓��͕͂K�{�ł��B");
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
	 * �S�Ă�textField��Ɂu,�v�����݂���ꍇ�͌x�����o��
	 */
	private final boolean chkCommaTextField(String text) {
		if (text.indexOf(",") >= 0) {
			return true;
		}
		return false;
	}
}
