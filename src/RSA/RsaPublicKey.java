package RSA;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.NetworkInterface;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * ���J���Í��̈��RSA�Í���Java�Ŏ��� http://lab.moyo.biz/recipes/java/security/publickey.xsp
 * -�L�[�y�A���� -�l���ł̕��� -���J���ł̈Í��� -���̃t�@�C�����o�͋@�\
 * 
 * @author Guernsey
 */
public final class RsaPublicKey {

	private static final String CRYPT_ALGORITHM = "RSA";

	/**
	 * �o�C�i�������J���ŕ���
	 * 
	 * @param source
	 *            �����������o�C�g��
	 * @param privateKey
	 * @return ���������o�C�g��D���s���� null
	 */
	public final byte[] decrypt(byte[] source, PublicKey public_key) {
		try {
			Cipher cipher = Cipher.getInstance(getCRYPT_ALGORITHM());
			cipher.init(Cipher.DECRYPT_MODE, public_key);
			return cipher.doFinal(source);
		} catch (IllegalBlockSizeException ex) {
			System.err.println(ex.getMessage());
		} catch (BadPaddingException ex) {
			System.err.println(ex.getMessage());
		} catch (InvalidKeyException ex) {
			System.err.println(ex.getMessage());
		} catch (NoSuchAlgorithmException ex) {
			System.err.println(ex.getMessage());
		} catch (NoSuchPaddingException ex) {
			System.err.println(ex.getMessage());
		}
		return null;
	}

	/**
	 * ���J�����O���t�@�C������ǂݍ���
	 * 
	 * @param filename
	 *            �t�@�C����
	 * @param size
	 *            �o�C�g�z��̑傫��
	 * @return ���J���D���s���� null
	 */
	public final PublicKey loadPublicKey(String filename) {
		if (!chkFileExist(filename)) {
			// System.out.println("PublicKey.txt�t�@�C����������܂���ł���");
			return null;
		}
		try {
			KeyFactory key_factory = KeyFactory
					.getInstance(getCRYPT_ALGORITHM());
			byte[] b = loadBinary(filename);
			EncodedKeySpec key_spec = new X509EncodedKeySpec(b);
			return key_factory.generatePublic(key_spec);
		} catch (InvalidKeySpecException ex) {
			// System.err.println(ex.getMessage());
		} catch (NoSuchAlgorithmException ex) {
			// System.err.println(ex.getMessage());
		}
		return null;
	}

	/**
	 * ���C�Z���X�L�[���O���t�@�C������Shift_JIS�`���œǂݍ���
	 * 
	 * @param b
	 *            �閧���̃o�C�g�z��
	 * @param filename
	 *            �t�@�C����
	 */
	public byte[] loadLicenseKey(String filename) {
		if (!chkFileExist(filename)) {
			System.out.println("���C�Z���X�L�[�����݂��܂���");
			return null;
		}
		return loadBinary(filename);
	}

	/**
	 * �o�C�i�����O���t�@�C������ǂݍ���
	 * 
	 * @param filename
	 *            �t�@�C��
	 * @param size
	 *            �o�C�g�z��̑傫��
	 * @return �ǂݍ��񂾃o�C�g�z��D���s���� null
	 */
	private static byte[] loadBinary(String filename) {
		ArrayList<String> array = new ArrayList<String>();
		try (FileInputStream fis = new FileInputStream(filename);
				InputStreamReader in = new InputStreamReader(fis, "Shift_JIS");
				BufferedReader br = new BufferedReader(in);) {
			String line;
			while ((line = br.readLine()) != null) {
				array.add(line);
			}
			byte[] b = new byte[array.size()];
			for (int i = 0; i < array.size(); i++) {
				b[i] = Byte.parseByte(array.get(i));
			}
			return b;
		} catch (IOException ex) {
			// System.err.println(ex.getMessage());
		}
		return null;
	}

	/**
	 * ���J�����O���t�@�C���ɕۑ�����
	 * 
	 * @param b
	 *            ���J���̃o�C�g�z��
	 * @param filename
	 *            �t�@�C����
	 */
	protected final void savePublicKey(byte[] b, String filename) {
		saveBinary(b, filename);
	}

	/**
	 * �o�C�g�^�f�[�^���O���t�@�C����Shift_JIS�`���ŕۑ�����
	 * 
	 * @param b
	 *            �o�C�g�z��
	 * @param filename
	 *            �t�@�C����
	 */
	private static void saveBinary(byte[] b, String filename) {
		try (FileOutputStream fos = new FileOutputStream(filename);
				OutputStreamWriter out = new OutputStreamWriter(fos,
						"Shift_JIS");
				BufferedWriter bw = new BufferedWriter(out);) {
			String str;
			for (int i = 0; i < b.length; i++) {
				str = String.valueOf(b[i]);
				bw.write(str);
				if (i != b.length - 1)
					bw.newLine(); // �Ō�̏������ݎ��ɋ󔒂���ꂽ���Ȃ�����
			}
		} catch (IOException ex) {
			// System.err.println(ex.getMessage());
		}
	}

	/**
	 * @return the CRYPT_ALGORITHM
	 */
	public static String getCRYPT_ALGORITHM() {
		return CRYPT_ALGORITHM;
	}

	/**
	 * Mac�A�h���X�̎擾
	 * 
	 * @return Mac�A�h���X�̃��X�g, ���s���� null
	 */
	public final ArrayList<String> getMacAddress() {
		ArrayList<String> macAddressList = new ArrayList<String>();
		try {
			Enumeration<NetworkInterface> nics = NetworkInterface
					.getNetworkInterfaces();
			while (nics.hasMoreElements()) {
				StringBuilder macAddress = new StringBuilder();

				NetworkInterface nic = nics.nextElement();
				byte[] hardwareAddress = nic.getHardwareAddress();
				if (hardwareAddress != null) {
					for (byte b : hardwareAddress) {
						macAddress.append(String.format("%02X ", b));
					}
				}

				if (nic.isUp() && macAddress.toString().trim().length() == 17) {
					macAddressList.add(macAddress.toString().trim());
				}
			}
			return macAddressList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static final boolean chkFileExist(String filename) {
		File file = new File(filename);
		if (!file.exists()) {
			return false;
		}
		return true;
	}
}