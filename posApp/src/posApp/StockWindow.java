package posApp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class StockWindow extends JFrame implements ActionListener{

	JLabel labelId = new JLabel("ID");
	JLabel labelName = new JLabel("상품명");
	JLabel labelStock = new JLabel("재고량");
	JLabel labelPrice = new JLabel("단가");
	
	JTextField textFieldId = new JTextField(10);
	JTextField textFieldName = new JTextField(10);
	JTextField textFieldStock = new JTextField(10);
	JTextField textFieldPrice = new JTextField(10);
	
	JButton buttonAccept = new JButton("");
	Item item;
	String menu;
	
	public StockWindow(String menu) { //등록
		this.menu = menu;
		display();
		setSize(300,300);
		setVisible(true);
	}
	
	public StockWindow(String menu, Item item) { //수정, 삭제
		this.menu = menu;
		this.item = item;
		display();
		setSize(300,300);
		setVisible(true);
	}

	public void display() {
		
		Container ct = getContentPane();
		JPanel jp = new JPanel(new GridLayout(4, 2));
		buttonAccept.setText(menu);
		textFieldId.setEditable(false);
		
		if(item != null) {
			textFieldId.setText(String.valueOf(item.getId()));
			textFieldName.setText(item.getItem_name());
			textFieldStock.setText(String.valueOf(item.getItem_stock()));
			textFieldPrice.setText(String.valueOf(item.getItem_price()));
		}
		
		jp.add(labelId);
		jp.add(textFieldId);

		jp.add(labelName);
		jp.add(textFieldName);
		
		jp.add(labelStock);
		jp.add(textFieldStock);
		
		jp.add(labelPrice);
		jp.add(textFieldPrice);
		
		ct.add(jp, BorderLayout.CENTER);
		ct.add(buttonAccept, BorderLayout.SOUTH);
		
		buttonAccept.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String state = e.getActionCommand(); //buttonAccept.getText();
		String id, name, stock, price;
		boolean result = false;
		
		switch (state) {
			case "등록":
				name = textFieldName.getText();
				stock = textFieldStock.getText();
				price = textFieldPrice.getText();
				
				Item register = new Item();
				register.setItem_name(name);
				register.setItem_stock(Integer.parseInt(stock));
				register.setItem_price(Integer.parseInt(price));
				
				try {
					result = ItemDAO.getInstance().insertItem(register);
					//jtableStock.getModel() 업데이트
					DefaultTableModel model = (DefaultTableModel) POS_StockManagement.jtableStock.getModel();
					//loadDB(model)
					POS_StockManagement.loadDB(model);
					this.dispose();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
				
			case "수정":
				id = textFieldId.getText();
				name = textFieldName.getText();
				stock = textFieldStock.getText();
				price = textFieldPrice.getText();
				item.setId(Integer.parseInt(id));
				item.setItem_name(name);
				item.setItem_stock(Integer.parseInt(stock));
				item.setItem_price(Integer.parseInt(price));
				
				try {
					result = ItemDAO.getInstance().updateItem(item);
					DefaultTableModel model = (DefaultTableModel) POS_StockManagement.jtableStock.getModel();
					//loadDB(model)
					POS_StockManagement.loadDB(model);
					this.dispose();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
				
			case "삭제":
				id = textFieldId.getText();
				name = textFieldName.getText();
				stock = textFieldStock.getText();
				price = textFieldPrice.getText();
				
//				item.setId(Integer.parseInt(id));
//				item.setItem_name(name);
//				item.setItem_stock(Integer.parseInt(stock));
//				item.setItem_price(Integer.parseInt(price));
				
				try {
//					result = ItemDAO.getInstance().deleteItem(Integer.parseInt(id));
					
					int res;
					res = JOptionPane.showConfirmDialog(null, "선택한 상품 " + name + "를 정말 삭제하겠습니까?");
					
					if(res==0) {
						ItemDAO.getInstance().deleteItem(Integer.parseInt(id));
						DefaultTableModel model = (DefaultTableModel) POS_StockManagement.jtableStock.getModel();
						//loadDB(model)
						POS_StockManagement.loadDB(model);
						this.dispose();
					}else {
						JOptionPane.showConfirmDialog(null, "삭제를 취소했습니다.");
					}
					
					DefaultTableModel model = (DefaultTableModel) POS_StockManagement.jtableStock.getModel();
					//loadDB(model)
					POS_StockManagement.loadDB(model);
					this.dispose();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
		}
	}
}