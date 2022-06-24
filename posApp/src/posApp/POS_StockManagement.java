package posApp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class POS_StockManagement extends JPanel implements ActionListener{

	JLabel labelName;
	JButton buttonDB;
	JButton buttonRegister;
	JButton buttonUpdate;
	JButton buttonDelete;
	static JTable jtableStock;
	
	//멤버변수들의 초기화 - 생성자 메소드
	public POS_StockManagement() {
		
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("ID");
		model.addColumn("상품명");
		model.addColumn("재고량");
		model.addColumn("단가");
		jtableStock = new JTable(model); // 테이블 형태로 데이터 출력 뷰(데이터 필요)
		JScrollPane jtable = new JScrollPane(jtableStock);
		
		setLayout(null);
		
		labelName = new JLabel("재고현황");
		labelName.setSize(100, 40);
		labelName.setLocation(60, 20);
		
		buttonDB = new JButton("상품 새로고침");
		buttonDB.setBounds(10, 70, 150, 40);
		
		buttonRegister = new JButton("등록");
		buttonRegister.setBounds(10, 130, 150, 40);
		
		buttonUpdate = new JButton("수정");
		buttonUpdate.setBounds(10, 190, 150, 40);
		
		buttonDelete = new JButton("삭제");
		buttonDelete.setBounds(10, 250, 150, 40);
		
		jtableStock.setBounds(200, 20, 300, 280);
		
		add(labelName);
		add(buttonDB);
		add(buttonRegister);
		add(buttonUpdate);
		add(buttonDelete);
		add(jtableStock);
		
		buttonDB.addActionListener(this);
		buttonRegister.addActionListener(this);
		buttonUpdate.addActionListener(this);
		buttonDelete.addActionListener(this);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		DefaultTableModel model = (DefaultTableModel) jtableStock.getModel();
		
		String buttonText = e.getActionCommand();
		if(buttonText == "상품 새로고침") {
			//DB연결-select 전체검색-vector 담아서-model에 행으로 삽입 -->loadDB
			try {
				loadDB(model);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if(buttonText == "등록") {
			StockWindow window = new StockWindow(buttonText);
		} else if(buttonText == "수정") {
			int row = jtableStock.getSelectedRow();
			if(row == -1) {
				 JOptionPane.showMessageDialog(null, "셀을 선택하세요!", "경고!", JOptionPane.WARNING_MESSAGE);
			} else {
				String id = (String) jtableStock.getValueAt(row, 0);
				String name = (String) jtableStock.getValueAt(row, 1);
				String stock = (String) jtableStock.getValueAt(row, 2);
				String price = (String) jtableStock.getValueAt(row, 3);
				
				Item item = new Item();
				item.setId(Integer.parseInt(id));
				item.setItem_name(name);
				item.setItem_stock(Integer.parseInt(stock));
				item.setItem_price(Integer.parseInt(price));
				
				StockWindow window = new StockWindow(buttonText, item);
			}
			
		} else {
			int row = jtableStock.getSelectedRow();
			if(row == -1) {
				 JOptionPane.showMessageDialog(null, "셀을 선택하세요!", "경고!", JOptionPane.WARNING_MESSAGE);
			} else {
				String id = (String) jtableStock.getValueAt(row, 0);
				String name = (String) jtableStock.getValueAt(row, 1);
				String stock = (String) jtableStock.getValueAt(row, 2);
				String price = (String) jtableStock.getValueAt(row, 3);
				
				Item item = new Item();
				item.setId(Integer.parseInt(id));
				item.setItem_name(name);
				item.setItem_stock(Integer.parseInt(stock));
				item.setItem_price(Integer.parseInt(price));
				
				StockWindow window = new StockWindow(buttonText, item);
			}
		}
		
//		Object buttonObject = e.getSource();	
//		if(buttonObject == buttonDB) {
//			//DB연결-select 전체검색-vector 담아서-model에 행으로 삽입 -->loadDB
//			try {
//				loadDB(model);
//			} catch (SQLException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//		} else if(buttonObject == buttonRegister) {
//			
//			
//		} else if(buttonObject == buttonUpdate) {
//			
//		} else {
//			
//		}
		
		
	}
	
	public static void loadDB(DefaultTableModel model) throws SQLException {
		Vector<Item> itemlist = ItemDAO.getInstance().getAllItem();
		
		int rows = model.getRowCount();
		for (int i = rows-1; i >= 0; i--) {
			model.removeRow(i);
		}
		
		//itemlist가 참조하는 DB의 모든 레코드를 한 행씩 vector에 담아서 model에 추가
		for (Item item : itemlist) {
			String item_id = String.valueOf(item.getId()); //Integer.toString(), String.valueOf()
			String item_name = item.getItem_name();
			String item_stock = String.valueOf(item.getItem_stock());
			String item_price = String.valueOf(item.getItem_price());
			
			Vector<String> in = new Vector<String>();
			//in의 요소로 id, 상품명, 재고량, 단가를 추가
			in.add(item_id);
			in.add(item_name);
			in.add(item_stock);
			in.add(item_price);
			
			model.addRow(in);
		}
		
	}

}
