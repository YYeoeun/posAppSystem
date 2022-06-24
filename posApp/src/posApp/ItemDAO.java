package posApp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ItemDAO {

	private ItemDAO() { }
	
	private static ItemDAO instance = new ItemDAO();
	
	public static ItemDAO getInstance() {
		return instance;
	}
	
	//1. 전체 레코드 검색 기능(getAllItem)())	
	public Vector<Item> getAllItem() throws SQLException {
		
		Vector<Item> list = new Vector<Item>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "select * from item";
		ResultSet rs = null;
		
		try {
			conn = DBConnect.connect();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			//rs 순회하면서 Item 객체 생성, Vector에 생성된 item 추가
			while(rs.next()) {
				Item item = new Item();
				item.setId(rs.getInt("id"));
				item.setItem_name(rs.getString("item_name"));
				item.setItem_stock(rs.getInt("item_stock"));
				item.setItem_price(rs.getInt("item_price"));
				list.add(item);
			}
			
		}catch(Exception e) {
			System.out.println("DB 연결 또는  SQL 에러!");
		}finally {
			rs.close();
			pstmt.close();
			conn.close();
		}
		
		return list;
	}
	
	//2. 상품명 목록 처리
	public Vector<String> getItem() {
		
		Vector<Item> dblist = new Vector<Item>();
		Vector<String> itemlist = new Vector<String>();
		
		try {
			dblist = getAllItem();
			
			for(Item item: dblist) {
				itemlist.add(item.getItem_name());
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return itemlist;
		
	}
	
	//3. 제품 가격 가져오기(item_price)
	public String getPrice(String name) throws SQLException {
		
		Vector<Item> list = new Vector<Item>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "select item_price from item where item_name=?";
		ResultSet rs = null;
		String price = null;
		
		try {
			conn = DBConnect.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			
			//rs 순회하면서 Item 객체 생성, Vector에 생성된 item 추가
			while(rs.next()) {
				price = Integer.toString(rs.getInt("item_price"));
			}
			
		}catch(Exception e) {
			System.out.println("DB 연결 또는  SQL 에러!");
		}finally {
			rs.close();
			pstmt.close();
			conn.close();
		}
		
		return price;
	}
	
	//4. 제품 재고량 가져오기(item_stock)
	public String getStock(String name) throws SQLException {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "select item_stock from item where item_name=?";
		ResultSet rs = null;
		String stock = null;
		
		try {
			conn = DBConnect.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			
			//rs 순회하면서 Item 객체 생성, Vector에 생성된 item 추가
			while(rs.next()) {
				stock = Integer.toString(rs.getInt("item_stock"));
			}
			
		}catch(Exception e) {
			System.out.println("DB 연결 또는  SQL 에러!");
		}finally {
			rs.close();
			pstmt.close();
			conn.close();
		}
		
		return stock;
	}
		
	//5. 제품 재고량 업데이트하기(item_stock==> total, stock, name)
	public void updateStock(String total, String stock, String name) throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "update item set item_stock=?-? where item_name=?";
		
		try {
			conn = DBConnect.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, total);
			pstmt.setString(2, stock);
			pstmt.setString(3, name);
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			System.out.println("DB 연결 또는  SQL 에러!");
		}finally {
			pstmt.close();
			conn.close();
		}
	}
	
	//6. 재고관리화면 - 제품 신규 등록
	public boolean insertItem(Item item) throws SQLException {
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "insert into item(item_name, item_stock, item_price) values(?, ?, ?)";
		
		try {
			conn = DBConnect.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, item.getItem_name());
			pstmt.setInt(2, item.getItem_stock());
			pstmt.setInt(3, item.getItem_price());
			
			int r = pstmt.executeUpdate();
			
			if(r>0) {
				result = true;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			pstmt.close();
			conn.close();
		}
		
		return result;
	}
	
	//6. 재고관리화면 - 제품 정보 수정
	public boolean updateItem(Item item) throws SQLException {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "update item set item_name=?, item_stock=?, item_price=? where (id=?)";
		
		try {
			conn = DBConnect.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, item.getItem_name());
			pstmt.setInt(2, item.getItem_stock());
			pstmt.setInt(3, item.getItem_price());
			pstmt.setInt(4, item.getId());
			
			int r = pstmt.executeUpdate();
			
			if(r>0) {
				result = true;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			pstmt.close();
			conn.close();
		}
		
		return result;
	}
	
	//6. 재고관리화면 - 등록 제품 삭제
	public boolean deleteItem(int id) throws SQLException {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "delete from item where (id=?)";
		
		try {
			conn = DBConnect.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			
			int r = pstmt.executeUpdate();
			
			if(r>0) {
				result = true;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			pstmt.close();
			conn.close();
		}
		
		return result;
	}
}
