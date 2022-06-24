package posApp;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class Main_POS extends JFrame{

	POS_pos pos = null;
	POS_StockManagement stockManagement = null;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Main_POS mainPOS = new Main_POS();
		mainPOS.setTitle("POS System");
		
		mainPOS.pos = new POS_pos();
		mainPOS.stockManagement = new POS_StockManagement();
		
		JTabbedPane jtab = new JTabbedPane();
		
		jtab.add("POS", mainPOS.pos);
		jtab.add("재고관리", mainPOS.stockManagement);
		
		mainPOS.add(jtab);
		mainPOS.setSize(550,400);
		mainPOS.setVisible(true);		
	}

}
