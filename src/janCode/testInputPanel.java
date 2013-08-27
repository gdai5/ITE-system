package janCode;

//5-30
//���C�A�E�g��啝�ɕύX����
//6-8
//�V�X�e���̊����@version1.0

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
	// ITE�p�l���֘A�̕ϐ��錾
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
			// jancode���d�����Ă��Ȃ��ꍇ�̏���
			// CSV�t�@�C����������
			String itemCdEditMsg = "���i�R�[�h�̕ҏW ( ���p )";

			item_info[1] = JOptionPane.showInputDialog(this, itemCdEditMsg,
					item_info[1]);
			addSubtitle();

			String titleEditMsg = "�^�C�g���̕ҏW ( �^�C�g����30�����ȓ��ɂ��Ă��������B �S�p1 ���p0.5 )";
			do {
				title = JOptionPane.showInputDialog(this, titleEditMsg + "���݁F"
						+ titleCharCount(title) + "����", title);
			} while (titleCharCount(title) > 30);

			InsertCsvfile();
			rp.writeRegisterInfo(title);
		} else {
			// jancode���d�����Ă���ꍇ�̏���
			// �d�����Ă���̂Ń|�b�v�A�b�v�쐬
			JLabel msg = new JLabel("�o�^�ς݂ł��F" + rp.jancode); // �|�b�v�A�b�v�쐬
			JOptionPane.showMessageDialog(this, msg); // �|�b�v�A�b�v�\��
		}
	}

	/**
	 * �^�C�g���������̃J�E���g���\�b�h
	 * 
	 * @param string
	 * @return �S�p��1,���p��0.5�Ƃ����ꍇ�̓V��l
	 */
	private int titleCharCount(String string) {
		String hankaku = string.replaceAll("[^ -~�-�]*", "");
		String zenkaku = string.replaceAll("[ -~�-�]*", "");
		return (int) Math.ceil((double) zenkaku.length()
				+ (double) hankaku.length() / 2);
	}

	// �T�u�^�C�g���̒ǉ�
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

	// CSV�t�@�C���ɏ�������
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
				lp.cut_negotiation, "0", "������", "������", "������", "����", "������",
				"������", "������");
	}

	/**
	 * ���������C�Z���X�L�[�����邩�ǂ����̃`�F�b�N
	 * 
	 * @var licence_key Mac�A�h���X��閧���ňÍ����������̂������Ă���
	 * @var public_key ���J���i�������j
	 * @return true, ���s��false
	 */
	public static final boolean chkLicenseKey() {
		// RSA�����̌��J���i�������j���擾
		RsaPublicKey rsa_pub = new RsaPublicKey();
		PublicKey public_key;

		// ���J���̃��[�h
		public_key = rsa_pub.loadPublicKey("PublicKey.txt");
		if (public_key == null) {
			JOptionPane.showMessageDialog(null, "PublicKey.txt�����݂��Ă��܂���");
			return false;
		}
		// ���C�Z���X�L�[�̎擾
		String titleEditMsg = "�l�b�g���[�N�̐ڑ��`���i�L��or�����j�ɍ��킹��"
				+ "���C�Z���X�L�[�̃t�@�C�������t���l�[���œ��͂��Ă��������i�Ō��.txt��t���Y��Ȃ��ŉ������j";
		String license_filename = JOptionPane.showInputDialog(null,
				titleEditMsg, "");
		byte[] license_key = rsa_pub.loadLicenseKey(license_filename);
		if (license_key == null) {
			JOptionPane.showMessageDialog(null, "���������C�Z���X�L�[���擾�o���܂���ł����B");
			return false;
		}
		// �Í����ilicense_key�j�����J���ipubilc_key�j�ŕ�����
		byte[] dec = rsa_pub.decrypt(license_key, public_key);
		if (dec == null) {
			JOptionPane.showMessageDialog(null, "�����Ɏ��s���܂����B");
			return false;
		}
		// Mac�A�h���X�̎擾
		ArrayList<String> macAddressList = new ArrayList<String>();
		macAddressList = rsa_pub.getMacAddress();
		if (macAddressList == null) {
			JOptionPane.showMessageDialog(null, "�V�X�e���G���[");
			return false;
		}
		if (macAddressList.size() == 0) {
			JOptionPane.showMessageDialog(null, "�C���^�[�l�b�g�ɐڑ��o���Ă��܂���");
			return false;
		}
		// �����������o�C�g�z������̕�����ɕϊ�
		String dec_result = new String(dec);
		for (int i = 0; i < macAddressList.size(); i++) {
			if (!macAddressList.get(i).equalsIgnoreCase(dec_result)) {
			} else {
				return true;
			}
		}
		JOptionPane.showMessageDialog(null, "���C�Z���X�L�[���Ԉ���Ă��܂�");
		return false;
	}

}
