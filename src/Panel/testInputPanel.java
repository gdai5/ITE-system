package Panel;

/*
 * 2013-09-09
 * �ۑ�Ǘ��\�u�W�A�P�O�v
 * �C���ӏ��Ō���
 * 
 */

import janCode.JanCodeSearch;
import janCode.NewReadWriteCsv;

import java.awt.Container;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public List<String> NGwordlist = new ArrayList<String>();
	public Map<String,String> changewordlist = new HashMap<String,String>();

	public testInputPanel() {
	}

	public void run() {
		setFrame();
		setLeftPanel();
		setRightPanel();
		setNGwordList();
		setChangewordList();
		setVisible(true);
	}

	private final void setFrame() {
		setBounds(100, 100, 1100, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridLayout(1, 2));
		contentPane = getContentPane();
	}

	private final void setLeftPanel() {
		lp.setLeftPanel(this);
		contentPane.add(lp);
	}

	private final void setRightPanel() {
		rp.setRightPanel(this);
		contentPane.add(rp);
	}

	protected final void ItemRegister() {
		// �C���ӏ��P�P
		if (!rp.chkRightField() || !lp.chkLeftField()) {
			return;
		}

		item_info = js.getiteminfo(rp.jancode, NGwordlist, changewordlist, rp.ngword, rp.barcode); // [TITLE]:title
												// [MAKERNUMBER]:maker number
		if (nrwc.searchCsv(rp.jancode)) {

			ItemConfilmDialog icd = new ItemConfilmDialog(rp.subtitle + " " + item_info[1] + " "
					+ item_info[0]);
			title = icd.txtTitle.getText();

			InsertCsvfile();

			rp.writeRegisterInfo(title);
		} else {
			// jancode���d�����Ă���ꍇ�̏���
			// �d�����Ă���̂Ń|�b�v�A�b�v�쐬
			JLabel msg = new JLabel("�o�^�ς݂ł��F" + rp.jancode); // �|�b�v�A�b�v�쐬
			JOptionPane.showMessageDialog(this, msg); // �|�b�v�A�b�v�\��
		}
	}

	// CSV�t�@�C���ɏ�������
	/**
	 * 2013-09-14
	 * author Ishikawa
	 * �ďo�i�񐔂��w��ł���悤�ɏC��
	 */
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
				lp.cut_negotiation, lp.relist, "������", "������", "������", "����", "������",
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
			JOptionPane.showMessageDialog(null, "PublicKey.txt��������܂���ł����B\n"
					+ "PublicKey.txt��ITE-ver1.5.jar������ꏊ�Ɠ����ʒu�ɒu���Ă��������B");
			return false;
		}
		// ���C�Z���X�L�[�̎擾
		String titleEditMsg = "�l�b�g���[�N�̐ڑ��`���i�L��or�����j�ɍ��킹��"
				+ "���C�Z���X�L�[�̃t�@�C�������t���l�[���œ��͂��Ă��������i�Ō��.txt��t���Y��Ȃ��ŉ������j";
		String license_filename = JOptionPane.showInputDialog(null,
				titleEditMsg, "");
		byte[] license_key = rsa_pub.loadLicenseKey(license_filename);
		if (license_key == null) {
			JOptionPane.showMessageDialog(null,
					"���������C�Z���X�L�[���擾�o���܂���ł����B�ȉ��̌������l�����܂��B\n"
							+ "�P�F�����i�L���j�ڑ��ɑ΂��ėL���i�����j�̃��C�Z���X�L�[���g���Ă���\n"
							+ "�Q�F�ʂ̃p�\�R���̃��C�Z���X�L�[���g���Ă���\n"
							+ "�R�F���͂����t�@�C�������Ԉ���Ă���(.txt�������Ă���Ȃ�)\n"
							+ "�S�F���C�Z���X�L�[�����Ă�\n" + "�ȏ�̂��Ƃ����m�F���������B");
			return false;
		}
		// �Í����ilicense_key�j�����J���ipubilc_key�j�ŕ�����
		byte[] dec = rsa_pub.decrypt(license_key, public_key);
		if (dec == null) {
			JOptionPane.showMessageDialog(null, "�����Ɏ��s���܂����B\n"
					+ "PublicKey.txt���j�����Ă���\��������܂��B\n"
					+ "CD����PublicKey.txt���ēx�R�s�[���Ă�������");
			return false;
		}
		// Mac�A�h���X�̎擾
		ArrayList<String> macAddressList = new ArrayList<String>();
		macAddressList = rsa_pub.getMacAddress();
		if (macAddressList == null) {
			JOptionPane
					.showMessageDialog(null, "�V�X�e���G���[\n"
							+ "�p�\�R����ID���擾���邱�Ƃ��ł��܂���ł����B\n"
							+ "�������̋L�ڂ���Ă���A����ɘA�����Ă��������B");
			return false;
		}
		if (macAddressList.size() == 0) {
			JOptionPane.showMessageDialog(null, "�C���^�[�l�b�g�ɐڑ��o���Ă��܂���\n"
					+ "�{�V�X�e���̓C���^�[�l�b�g�ɐڑ����ė��p���܂��B\n"
					+ "�C���^�[�l�b�g�ɐ������ڑ��ł��Ă��邩�ǂ������m�F���������B");
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
		JOptionPane.showMessageDialog(null, "���C�Z���X�L�[���Ԉ���Ă��܂�\n"
				+ "���̃p�\�R���̃��C�Z���X�L�[����͂��Ă��܂��������C�Z���X�L�[�����Ă���\��������܂��B\n"
				+ "���m�F���������B");
		return false;
	}
	
	/**
	 * 2013-09-14
	 */
	private void setNGwordList() {
		String word;
		try {
			File file = new File("NGwordList.txt");		
			FileInputStream in = new FileInputStream(file);
			InputStreamReader sr = new InputStreamReader(in, "UTF-8");
			BufferedReader br = new BufferedReader(sr);
			if (checkBeforeReadfile(file)){
		        while((word = br.readLine()) != null){
		        	System.out.println(word);
		        	NGwordlist.add(word);
		        }				
			} else {
				JOptionPane.showMessageDialog(null, "NGwordList.txt�̓ǂݍ��݂����s���܂����B\n" +
						"���̂���NG���[�h�폜�@�\�͓����܂���B");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 2013-09-14
	 */
	private void setChangewordList() {
		String word;
		String[] tempword;
		try {
			File file = new File("ChangewordList.txt");
			FileInputStream in = new FileInputStream(file);
			InputStreamReader sr = new InputStreamReader(in, "UTF-8");
			BufferedReader br = new BufferedReader(sr);
			if (checkBeforeReadfile(file)){
		        while((word = br.readLine()) != null){
		        	System.out.println(word);
		        	tempword = word.split(",");
		        	changewordlist.put(tempword[0],tempword[1]);
		        }
			} else {
				JOptionPane.showMessageDialog(null, "ChangewordList.txt�̓ǂݍ��݂����s���܂����B\n" +
						"���̂��ߏ��i�R�[�h�ϊ��@�\�͓����܂���B");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	
	private boolean checkBeforeReadfile(File file){
		if (file.exists()){
			if (file.isFile() && file.canRead()){
				return true;
	      }
		}
		
		return false;
	}
	
}
