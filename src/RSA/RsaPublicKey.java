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
 * 公開鍵暗号の一種RSA暗号をJavaで実装 http://lab.moyo.biz/recipes/java/security/publickey.xsp
 * -キーペア生成 -個人鍵での復号 -公開鍵での暗号化 -鍵のファイル入出力機能
 * 
 * @author Guernsey
 */
public final class RsaPublicKey {

	private static final String CRYPT_ALGORITHM = "RSA";

	/**
	 * バイナリを公開鍵で復号
	 * 
	 * @param source
	 *            復号したいバイト列
	 * @param privateKey
	 * @return 復号したバイト列．失敗時は null
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
	 * 公開鍵を外部ファイルから読み込む
	 * 
	 * @param filename
	 *            ファイル名
	 * @param size
	 *            バイト配列の大きさ
	 * @return 公開鍵．失敗時は null
	 */
	public final PublicKey loadPublicKey(String filename) {
		if (!chkFileExist(filename)) {
			// System.out.println("PublicKey.txtファイルが見つかりませんでした");
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
	 * ライセンスキーを外部ファイルからShift_JIS形式で読み込む
	 * 
	 * @param b
	 *            秘密鍵のバイト配列
	 * @param filename
	 *            ファイル名
	 */
	public byte[] loadLicenseKey(String filename) {
		if (!chkFileExist(filename)) {
			System.out.println("ライセンスキーが存在しません");
			return null;
		}
		return loadBinary(filename);
	}

	/**
	 * バイナリを外部ファイルから読み込む
	 * 
	 * @param filename
	 *            ファイル
	 * @param size
	 *            バイト配列の大きさ
	 * @return 読み込んだバイト配列．失敗時は null
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
	 * 公開鍵を外部ファイルに保存する
	 * 
	 * @param b
	 *            公開鍵のバイト配列
	 * @param filename
	 *            ファイル名
	 */
	protected final void savePublicKey(byte[] b, String filename) {
		saveBinary(b, filename);
	}

	/**
	 * バイト型データを外部ファイルにShift_JIS形式で保存する
	 * 
	 * @param b
	 *            バイト配列
	 * @param filename
	 *            ファイル名
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
					bw.newLine(); // 最後の書き込み時に空白を入れたくないため
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
	 * Macアドレスの取得
	 * 
	 * @return Macアドレスのリスト, 失敗時は null
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