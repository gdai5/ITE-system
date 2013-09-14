package Panel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ItemConfilmDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private JLabel lbTitleLength = new JLabel();
	public JTextField txtTitle = new JTextField();
	private JButton btnOK = new JButton("OK");

	public ItemConfilmDialog(String defaultTitleText) {
		super();

		txtTitle = new JTextField(defaultTitleText);
		txtTitle.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent de) {
				updateLengthInfo(de);
			}

			public void insertUpdate(DocumentEvent de) {
				updateLengthInfo(de);
			}

			public void removeUpdate(DocumentEvent de) {
				updateLengthInfo(de);
			}
		});

		btnOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnOKClickEvent(e);
			}
		});

		getContentPane().setLayout(new GridLayout(3, 1));

		getContentPane().add(lbTitleLength);
		getContentPane().add(txtTitle);
		getContentPane().add(btnOK);

		updateLengthInfo(null);

		setTitle("�^�C�g���̕ҏW");
		setBounds(50, 50, 500, 120);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setModal(true);
		setVisible(true);
	}

	private void btnOKClickEvent(ActionEvent e) {
		dispose();
	}

	private void updateLengthInfo(DocumentEvent de) {
		lbTitleLength.setText("�^�C�g����30�����ȓ��ɂ��Ă��������B(�S�p1 ���p0.5) ���� : "
				+ titleCharCount(txtTitle.getText()));
	}

	@Override
	public void dispose() {
		if (titleCharCount(txtTitle.getText()) <= 30.0) {
			setModal(false);
			super.dispose();
		} else {
			JOptionPane.showMessageDialog(this,
					"�^�C�g����30�����ȓ��ɂ��Ă��������B(�S�p1 ���p0.5)");
		}
	}

	/**
	 * �^�C�g���������̃J�E���g���\�b�h
	 * 
	 * @param string
	 * @return �S�p��1,���p��0.5�Ƃ����ꍇ�̓V��l
	 */
	private double titleCharCount(String string) {
		double count = 0;

		// ���p 0.5
		count += (double) (string.replaceAll("[^ -~�-�]*", "").length()) / 2.0;

		// �S�p 1
		count += (double) string.replaceAll("[ -~�-�]*", "").length();

		// �y�ۑ�ԍ�8�z 2013-09-14 Tree
		// ���p�J�^�J�i�� 1 �Ƃ��ăJ�E���g���邽�߁A 0.5 �𑫂����ށB
		count += (double) (string.replaceAll("[^�-�]*", "").length()) / 2.0;

		return count;
	}
}
