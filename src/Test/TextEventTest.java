package Test;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class TextEventTest extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel label = new JLabel();
	private JTextField tf = new JTextField();
	
	public TextEventTest() {
		getContentPane().setLayout(new BorderLayout());

		getContentPane().add(label,"Center");
		getContentPane().add(tf,"South");

		tf.getDocument().addDocumentListener(new TextEventListener());
		
		
	}
	
	class TextEventListener implements DocumentListener
	{
		public void changedUpdate(DocumentEvent de)
		{
			if(de.getDocument() == tf.getDocument())
				label.setText(Double.toString(titleCharCount(tf.getText())));
		}
		public void insertUpdate(DocumentEvent de)
		{
			if(de.getDocument() == tf.getDocument())
				label.setText(Double.toString(titleCharCount(tf.getText())));;
		}
		public void removeUpdate(DocumentEvent de)
		{
			if(de.getDocument() == tf.getDocument())
				label.setText(Double.toString(titleCharCount(tf.getText())));
		}
	}
	
	private double titleCharCount(String string) {
		if (string.length() != 0) {
			String hankaku = string.replaceAll("[^ -~¡-ß]*", "");
			String zenkaku = string.replaceAll("[ -~¡-ß]*", "");
			//System.out.println((double) zenkaku.length() + (double) hankaku.length() / 2);
			return (double) zenkaku.length() + (double) hankaku.length() / 2;
		}
		return 0;
  }
	
	public static void main(String args[]) {
		TextEventTest tet = new TextEventTest();		
		tet.setTitle("DocumentListener Sample");
		tet.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tet.setBounds(50,50,300,300);
		tet.setVisible(true);
	}
	
}
