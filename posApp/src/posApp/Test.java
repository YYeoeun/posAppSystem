package posApp;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;

public class Test {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub

		ItemDAO dao = ItemDAO.getInstance();
		
		Vector<Item> list = new Vector<Item>();
		
		/*
		
		try {
			list = dao.getAllItem();
			
			Iterator it = list.iterator();
			
			System.out.println("*** 전체 상품 목록 출력 ***");
			while(it.hasNext()) {
				Item item = (Item)it.next();
				System.out.println(item.getId()+", "+item.getItem_name()+", "+item.getItem_stock()+", "+item.getItem_price());
			}
			
			System.out.println("*** 상품명 출력 ***");
			Vector<String> itemlist = new Vector<String>();
			
			itemlist = dao.getItem();
			
			//순회하면서 Vector의 각 요소 출력
			it = itemlist.iterator();
			while(it.hasNext()) {
				String item = (String) it.next();
				System.out.println(item);
			}
			
			System.out.println("*** 상품명 가격 출력 ***");
			Scanner sc = new Scanner(System.in);
			System.out.println("상품명 입력 : ");
			String input = sc.next();
			String price = dao.getPrice(input);
			System.out.println("상품 가격 : "+price);
			
			System.out.println("*** 상품명 재고량 출력 ***");
			String stock = dao.getStock(input);
			System.out.println("상품 재고 : "+stock);
			
			System.out.println("*** 상품명 재고량 업데이트 ***");
			String total = dao.getStock(input);
			System.out.println("판매 수량 : ");
			String count = sc.next();
			dao.updateStock(total, count, input);
			
			list = dao.getAllItem();			
			it = list.iterator();
			
			System.out.println("*** 전체 상품 목록 출력 ***");
			while(it.hasNext()) {
				Item item = (Item)it.next();
				System.out.println(item.getId()+", "+item.getItem_name()+", "+item.getItem_stock()+", "+item.getItem_price());
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/

		Scanner sc = new Scanner(System.in);
		
		Item itemNew = new Item();
		System.out.print("제품명 : ");
		String name = sc.next();
		System.out.print("입고량 : ");
		int stock = sc.nextInt();
		System.out.print("단가 : ");
		int price = sc.nextInt();
		itemNew.setItem_name(name);
		itemNew.setItem_stock(stock);
		itemNew.setItem_price(price);
		boolean result = dao.insertItem(itemNew);
		
		list = dao.getAllItem();			
		Iterator it = list.iterator();
		
		System.out.println("*** 전체 상품 목록 출력 ***");
		while(it.hasNext()) {
			Item item = (Item)it.next();
			System.out.println(item.getId()+", "+item.getItem_name()+", "+item.getItem_stock()+", "+item.getItem_price());
		}
		
	}

}
