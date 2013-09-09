package janCode;

import Panel.testInputPanel;

//import javax.swing.text.*;

class JanCodeProcess {
	public static void main(String args[]) {
		if (testInputPanel.chkLicenseKey()) {
			new testInputPanel().run();
		}
	}
}
