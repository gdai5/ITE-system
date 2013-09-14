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

		// テキストフィールドにタイトルの初期値を設定
		// レイアウトに追加する前に初期値を設定しないとバグる。
		txtTitle = new JTextField(defaultTitleText);

		// テキストが変更された際に、文字数をカウントするリスナの設定
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

		// OKボタンが押された際のイベントリスナ設定
		btnOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		// レイアウトは縦に3行のグリッド
		getContentPane().setLayout(new GridLayout(3, 1));
		getContentPane().add(lbTitleLength);
		getContentPane().add(txtTitle);
		getContentPane().add(btnOK);

		// 文字数を表示するラベルを更新
		updateLengthInfo(null);

		// その他設定
		setTitle("タイトルの編集");
		setBounds(50, 50, 500, 120);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setModal(true);
		setVisible(true);
	}

	/**
	 * 現在の文字数をカウントし、ラベルに表示する。
	 * 
	 * @param de
	 */
	private void updateLengthInfo(DocumentEvent de) {
		lbTitleLength.setText("タイトルは30文字以内にしてください。(全角1 半角0.5) 現在 : "
				+ titleCharCount(txtTitle.getText()));
	}

	@Override
	public void dispose() {
		if (titleCharCount(txtTitle.getText()) <= 30.0) {
			// 文字数に問題がなければ、モーダルを解除
			setModal(false);
			super.dispose();
		} else {
			// 文字数に問題があれば、警告ダイアログを表示
			JOptionPane.showMessageDialog(this,
					"タイトルは30文字以内にしてください。(全角1 半角0.5)");
		}
	}

	/**
	 * タイトル文字数のカウントメソッド
	 * 
	 * @param string
	 * @return 全角を1,半角を0.5とした場合の天井値
	 */
	private double titleCharCount(String string) {
		double count = 0;

		// 半角 0.5
		count += (double) (string.replaceAll("[^ -~｡-ﾟ]*", "").length()) / 2.0;

		// 全角 1
		count += (double) string.replaceAll("[ -~｡-ﾟ]*", "").length();

		// 【課題番号8】 2013-09-14 Tree
		// 半角カタカナは 1 としてカウントするため、 0.5 を足し込む。
		count += (double) (string.replaceAll("[^ｦ-ﾟ]*", "").length()) / 2.0;

		return count;
	}
}
