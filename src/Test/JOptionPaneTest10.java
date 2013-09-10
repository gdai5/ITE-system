package Test;
//テキストでイベントを取得してリアルタイムに文字数を反映する方法が
//おもっていたよりも難しい
import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.TextComponent;
import java.awt.TextField;
import java.awt.event.*;

public class JOptionPaneTest10 extends JFrame{
	private Container contentPane;
	private JPanel jp = new JPanel();
	JLabel label;
    private int i = 0;
    String s1;
    TextComponent tc;
    TextField jt1;

    public JOptionPaneTest10() {  
    }
  
  private void setFrame() {
	  setDefaultCloseOperation(EXIT_ON_CLOSE);
	  setLocationRelativeTo(null);
      setBounds(10, 10, 300, 200);
      setTitle("タイトル");
      contentPane = getContentPane();      
  }

  public void run(){
    TextField jt = new TextField("hello");
    jt1 = new TextField(String.valueOf(titleCharCount(jt.getText())));
    jt1.setPreferredSize(new Dimension(200, 100));
    
    jt.addTextListener(new TextListener() {
    	@Override
    	public void textValueChanged(TextEvent e) {
    		tc = (TextComponent) e.getSource();
    	    jt1.setText(String.valueOf(titleCharCount(tc.getText())));
        }
    });
    //s1=String.valueOf(1);
    //label = new JLabel(s1);
    
    jp.add(jt);
    jp.add(jt1);
    
    getContentPane().add(jp);
    setVisible(true);
  }

  private int titleCharCount(String string) {
		String hankaku = string.replaceAll("[^ -~｡-ﾟ]*", "");
		String zenkaku = string.replaceAll("[ -~｡-ﾟ]*", "");
		return (int) Math.ceil((double) zenkaku.length()
				+ (double) hankaku.length() / 2);
  }
  
  public static void main(String[] args){
	  JOptionPaneTest10 jopt = new JOptionPaneTest10();
	  jopt.setFrame();
	  jopt.run();
  }
  
}