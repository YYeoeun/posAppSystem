package posApp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class POS_pos extends JPanel implements ActionListener {

	ItemDAO dao = ItemDAO.getInstance(); // ItemDAO 객체 생성(dao) 및 로드

	JButton btnDB;// "제품 불러오기" 버튼 객체 선언(btnDB)
	JLabel lblItem;// "상품" 라벨 객체 선언(lblItem)
	JComboBox cmbBox;// "상품리스트" 콤보박스 객체 선언(cmbBox)
	JLabel lblStock;// "수량" 라벨 객체 선언(lblStock)
	JTextField txtStock;// "수량입력박스" 텍스트필드 객체 선언(txtStock)
	JLabel lblTotal;// "총가격" 라벨 객체 선언(lblTotal)
	JTextField txtTotal;// "총가격 출력박스" 텍스트필드 객체 선언(txtTotal)
	JButton btnAdd;// "추가" 버튼 객체 선언(btnAdd)
	JButton btnPay;// "결재" 버튼 객체 선언(btnPay)
	JButton btnCancel;// "취소" 버튼 객체 선언(btnCancel)
	JTable jTableItem;// "테이블출력" JTable 객체 선언(jTableItem)

	DefaultTableModel tableModel = new DefaultTableModel();// JTable에 출력할 Model 객체 선언(tableModel)
	DefaultComboBoxModel comboModel = new DefaultComboBoxModel();// JComboBox에 출력할 Model 객체 선언(comboModel)

	// 총가격 저장할 정수형 멤버변수 선언(total)

	public POS_pos() {

		tableModel.addColumn("id");
		tableModel.addColumn("상품명");
		tableModel.addColumn("구매개수");
		tableModel.addColumn("물품가격");
		jTableItem = new JTable(tableModel); // 테이블 형태로 데이터 출력 뷰(데이터 필요)
		JScrollPane jtable = new JScrollPane(jTableItem);

		// 자동 배치 레이아웃 비활성화
		setLayout(null);

		// 각 컴포넌트 객체 생성 및 화면 배치/크기 조정
		btnDB = new JButton("제품 불러오기");
		btnDB.setBounds(20, 20, 140, 40);

		lblItem = new JLabel("상품");
		lblItem.setBounds(20, 90, 100, 30);

		cmbBox = new JComboBox();
		cmbBox.setBounds(70, 90, 200, 30);

		lblStock = new JLabel("수량");
		lblStock.setBounds(20, 140, 100, 30);

		txtStock = new JTextField();
		txtStock.setBounds(70, 140, 200, 30);

		lblTotal = new JLabel("총가격");
		lblTotal.setBounds(20, 250, 100, 40);

		txtTotal = new JTextField();
		txtTotal.setBounds(70, 250, 200, 40);

		btnAdd = new JButton("추가");
		btnAdd.setBounds(170, 190, 100, 40);

		btnPay = new JButton("결재");
		btnPay.setBounds(300, 250, 100, 40);

		btnCancel = new JButton("취소");
		btnCancel.setBounds(410, 250, 100, 40);

		jTableItem = new JTable(tableModel);
		jTableItem.setBounds(300, 20, 210, 210);

		// JPanel에 추가
		add(btnDB);
		add(lblItem);
		add(cmbBox);
		add(lblStock);
		add(txtStock);
		add(lblTotal);
		add(txtTotal);
		add(btnAdd);
		add(btnPay);
		add(btnCancel);
		add(jTableItem);

		// 이벤트 처리를 위한 리스너 등록
		btnDB.addActionListener(this);
		btnAdd.addActionListener(this);
		btnPay.addActionListener(this);
		btnCancel.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// 이벤트 객체로부터 텍스트 가져오기
		String buttonText = e.getActionCommand();
		// 제품명, 재고량, 가격 저장할 지역변수 선언 및 초기화
		String name = "";
		String stock = "";
		String price = "";

		// [제품 불러오기] 버튼 클릭 시
		if (buttonText == "제품 불러오기") {
			// comboBox의 모든 데이터 요소 삭제(removeAllItems());
			cmbBox.removeAllItems();

			// DB로부터 상품명 전체 검색 및 Vector에 저장
			Vector<String> itemlist = dao.getItem();
			// Vector에 저장한 상품명을 comboBox에 추가
			for (int i = 0; i < itemlist.size(); i++) {
				cmbBox.addItem(itemlist.get(i));
			}

		} // [추가] 버튼 클릭 시
		else if (buttonText == "추가") {
			// comboBox에서 선택한 상품명과 텍스트필드에 입력한 수량 저장
			name = cmbBox.getSelectedItem().toString();
			stock = txtStock.getText();
			int index = tableModel.getRowCount() + 1;
			int total = 0;
			// DB로부터 사용자가 선택한 상품명의 단가 불러오기
			try {
				price = "" + (Integer.parseInt(stock) * Integer.parseInt(dao.getPrice(name)));

				Vector<String> in = new Vector<String>();
				in.add("" + index);
				in.add(name);
				in.add(stock);
				in.add(price);

				tableModel.addRow(in);
				for (int i = 0; i < tableModel.getRowCount(); i++) {
					total += Integer.parseInt((String) tableModel.getValueAt(i, 3));
					txtTotal.setText("" + total);
				}

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// 사용자가 선택한 상품의 구매가격(단가*수량)과 누적 총액 연산하기
			// 상품명, 구매수량, 구매가격, 누적총액을 Vector에 저장
			// Vector 객체를 tableModel에 추가

		} // [결재] 버튼 클릭 시
		else if (buttonText == "결재") {
			// "결재하시겠습니까?"라는 다이얼로그 창 출력(JOptionPane.showConfirmDialog())
			try {
				int res;
				res = JOptionPane.showConfirmDialog(null, "결재하시겠습니까?");
				// "YES"를 누르면 "총금액은 ~입니다"를 출력한 후 사용자로부터 숫자 입력받기(JOptionPane.showInputDialog())
				// 사용자 입력금액이 총금액보다 크면 "지불금액,거스름돈"을 출력한 후 DB 업데이트(stockUpdate), 모든 컴포넌트 내의 데이터
				// 초기화(clean())
				// 그렇지 않으면 "금액이 적습니다" Dialog 창 출력
				if (res == 0) {
					JOptionPane.showConfirmDialog(null, "총금액은 " + txtTotal.getText() + "입니다");
					String money = JOptionPane.showInputDialog("");
					if (Integer.parseInt(money) >= Integer.parseInt(txtTotal.getText())) {
						JOptionPane.showConfirmDialog(null,
								"지불하신 금액은 " + money + "이고\n상품의 합계는 " + txtTotal.getText() + "이며,\n거스름돈은 "
										+ (Integer.parseInt(money) - Integer.parseInt(txtTotal.getText())) + "입니다");

						stockUpdate(tableModel);

						clean();
					} else {
						JOptionPane.showConfirmDialog(null, "금액이 적습니다");
					}
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} // [취소] 버튼 클릭 시
		else {
			// "주문을 취소하시겠습니까?" Dialog 창 출력
			// 모든 컴포넌트의 데이터 초기화
			int res;
			res = JOptionPane.showConfirmDialog(null, "주문을 취소하시겠습니까?");
			if (res == 0) {
				clean();
			}
		}
	}

	// JTable, 수량과 총가격의 JTextField 내 데이터 초기화
	public void clean() {
		int rows = tableModel.getRowCount();
		for (int i = rows - 1; i >= 0; i--) {
			tableModel.removeRow(i);
		}
		txtStock.setText("");
		txtTotal.setText("");
	}

	// JTable에 출력된 모든 데이터의 상품명, 재고량, 가격을 이용하여 DB 데이터 업데이트
	public void stockUpdate(DefaultTableModel model) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "update item set item_stock=?-? where item_name=?";

		try {
			conn = DBConnect.connect();
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < model.getRowCount(); i++) {
				pstmt.setString(1, dao.getStock(model.getValueAt(i, 1).toString()));
				pstmt.setString(2, model.getValueAt(i, 2).toString());
				pstmt.setString(3, model.getValueAt(i, 1).toString());
				pstmt.executeUpdate();
			}

		} catch (Exception e) {
			System.out.println("DB 연결 또는  SQL 에러!");
		} finally {
			pstmt.close();
			conn.close();
		}

	}
}
