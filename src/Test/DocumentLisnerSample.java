//リアルタイム変更のサンプルを見つけた

package Test;

import java.lang.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

class DocumentListenerSample extends JFrame
{
	JLabel label = new JLabel();
	JTextField field = new JTextField();

	DocumentListenerSample()
	{
		getContentPane().setLayout(new BorderLayout());

		getContentPane().add(label,"Center");
		getContentPane().add(field,"South");

		field.getDocument().addDocumentListener(new MyListener());
	}

	class MyListener implements DocumentListener
	{
		public void changedUpdate(DocumentEvent de)
		{
			if(de.getDocument() == field.getDocument())
				label.setText(field.getText());
		}
		public void insertUpdate(DocumentEvent de)
		{
			if(de.getDocument() == field.getDocument())
				label.setText(field.getText());;
		}
		public void removeUpdate(DocumentEvent de)
		{
			if(de.getDocument() == field.getDocument())
				label.setText(field.getText());
		}
	}

	public static void main(String[] anyArguments)
	{
		DocumentListenerSample f = new DocumentListenerSample();
		f.setTitle("DocumentListener Sample");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setBounds(50,50,300,300);
		f.setVisible(true);
	}
}