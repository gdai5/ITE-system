package janCode;

/* 
 * ReadWriteCsv�N���X
 * Jan�R�[�h�󂯎������C�n�b�V���R�[�h���쐬����D
 * CSV�t�@�C���ɏ������ޏ��������Ă���D
 *�@*/

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;

public class NewReadWriteCsv {
	HashMap<String, Boolean> map = new HashMap<String, Boolean>();

	public Boolean searchCsv(String jancode) {
		if (map.containsKey(jancode)) {
			return false;
		} else {
			map.put(jancode, true);
			return true;
		}
	}

	public void writeCsv(String filename, String category, String title,
			String explanation, String upsetprice, String buyoutprice,
			String number, String period, String terminationtime,
			String image1, String image1com, String image2, String image2com,
			String image3, String image3com, String prefectures,
			String municipality, String postagecharge, String payment,
			String settlement, String banktransfer, String bankname,
			String registmailcash, String cod, String paymethod1,
			String goodsstate, String goodsstatenote, String returngoods,
			String returngoodsnote, String biddervalulim, String autoextension,
			String earlytermination, String pricenegotiation,
			String autoreexhibition, String boldtext, String backgroundcolor,
			String giftsicon, String boon, String service, String deliabroad,
			String Affiliate) {
		/* csv�t�@�C���쐬 */
		File csv = new File(filename); // CSV�f�[�^�t�@�C��
		/* �ǋL���[�h */
		BufferedWriter bw;
		/* �f�[�^�s�̒ǉ� */
		if (csv.exists()) {
			try {
				bw = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(csv, true), "Shift_JIS"));
				bw.write(category + "," + title + "," + explanation + ","
						+ upsetprice + "," + buyoutprice + "," + number + ","
						+ period + "," + terminationtime + "," + image1 + ","
						+ image1com + "," + image2 + "," + image2com + ","
						+ image3 + "," + image3com + "," + prefectures + ","
						+ municipality + "," + postagecharge + "," + payment
						+ "," + settlement + "," + banktransfer + ","
						+ bankname + "," + registmailcash + "," + cod + ","
						+ paymethod1 + "," + goodsstate + "," + goodsstatenote
						+ "," + returngoods + "," + returngoodsnote + ","
						+ biddervalulim + "," + autoextension + ","
						+ earlytermination + "," + pricenegotiation + ","
						+ autoreexhibition + "," + boldtext + ","
						+ backgroundcolor + "," + giftsicon + "," + boon + ","
						+ service + "," + deliabroad + "," + Affiliate);
				bw.newLine();
				bw.close();
			} catch (FileNotFoundException e) {
				/* File�I�u�W�F�N�g�������̗�O�ߑ� */
				e.printStackTrace();
			} catch (IOException e) {
				/* BufferedWriter�I�u�W�F�N�g�̃N���[�Y���̗�O�ߑ� */
				e.printStackTrace();
			}
		} else {
			try {
				bw = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(csv, true), "Shift_JIS"));
				bw.write("�J�e�S��" + "," + "�^�C�g��" + "," + "����" + "," + "�J�n���i"
						+ "," + "�������i" + "," + "��" + "," + "�J�Ê���" + ","
						+ "�I������" + "," + "�摜1" + "," + "�摜1�R�����g" + "," + "�摜2"
						+ "," + "�摜2�R�����g" + "," + "�摜3" + "," + "�摜3�R�����g" + ","
						+ "���i�������̓s���{��" + "," + "���i�������̎s�撬��" + "," + "�������S"
						+ "," + "����x����" + "," + "Yahoo!���񂽂񌈍�" + "," + "��s�U��"
						+ "," + "��s��1" + "," + "��������" + "," + "���i���" + ","
						+ "���̑����ϕ��@1" + "," + "���i�̏��" + "," + "���i�̏�Ԕ��l" + ","
						+ "�ԕi�̉�" + "," + "�ԕi�̉۔��l" + "," + "���D�ҕ]������" + ","
						+ "��������" + "," + "�����I��" + "," + "	�l��������" + ","
						+ "�����ďo�i" + "," + "�����e�L�X�g" + "," + "�w�i�F" + ","
						+ "�����i�A�C�R��" + "," + "�͂�BOON" + "," + "�󂯎��㌈�σT�[�r�X"
						+ "," + "�C�O����" + "," + "�A�t�B���G�C�g");
				bw.newLine();
				bw.write(category + "," + title + "," + explanation + ","
						+ upsetprice + "," + buyoutprice + "," + number + ","
						+ period + "," + terminationtime + "," + image1 + ","
						+ image1com + "," + image2 + "," + image2com + ","
						+ image3 + "," + image3com + "," + prefectures + ","
						+ municipality + "," + postagecharge + "," + payment
						+ "," + settlement + "," + banktransfer + ","
						+ bankname + "," + registmailcash + "," + cod + ","
						+ paymethod1 + "," + goodsstate + "," + goodsstatenote
						+ "," + returngoods + "," + returngoodsnote + ","
						+ biddervalulim + "," + autoextension + ","
						+ earlytermination + "," + pricenegotiation + ","
						+ autoreexhibition + "," + boldtext + ","
						+ backgroundcolor + "," + giftsicon + "," + boon + ","
						+ service + "," + deliabroad + "," + Affiliate);
				bw.newLine();
				bw.close();
			} catch (FileNotFoundException e) {
				/* File�I�u�W�F�N�g�������̗�O�ߑ� */
				e.printStackTrace();
			} catch (IOException e) {
				/* BufferedWriter�I�u�W�F�N�g�̃N���[�Y���̗�O�ߑ� */
				e.printStackTrace();
			}
		}
	}
}
