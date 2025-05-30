package database;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import javax.swing.JOptionPane;
import java.time.LocalDate;

public class CakeOrderSystem {
	// 초기 인덱스 값 세팅
	static int orders_index = 11;
	static int customer_index = 11;
	static int orderitem_index = 17;

	public static void 초기화면() {
		System.out.println("============================");
		System.out.printf("\t케이크 주문 시스템\n");
		System.out.println("============================");
		// 케이크 메뉴 및 재고 현황표
		// 인기 케이크 순위
		System.out.println();
	}
	
	public static void 메뉴(Connection conn) {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("1. 케이크 주문하기");
		System.out.println("2. 전체 주문 목록 조회");
		System.out.println("3. 현재 주문 내역 조회");
		System.out.println();
		System.out.println("4. 주문 수정");
		System.out.println("5. 고객 정보 수정");
		System.out.println();
		System.out.println("0. 프로그램 종료");
		
		int choice = -1;

		while(true){
			try{
			System.out.print("[메뉴를 선택하세요] : ");
			choice = Integer.parseInt(scanner.nextLine());
			break;
			} catch(NumberFormatException e){
				System.out.println("[오류] 숫자를 입력해주세요.\n");
				continue;
			}
		}
		
		switch (choice) {
        case 1: // 케이크 주문하기
			addOrder(conn);
        	break;
        case 2: // 전체 주문 목록 조회
			System.out.println("이름을 입력해주세요: ");
        	String name = scanner.nextLine();
        	showCustomerOrder(conn, name);
        	break;
        case 3: // 현재 주문 내역 조회
        	System.out.println("주문 ID 입력: ");
        	String orderId = scanner.nextLine();
        	showOrderDetail(conn, orderId);
        	break;
        case 4:
        	updateOrder(conn);
        	break;
        case 5:
        	updateCustomer(conn);
        	break;
        case 0:
        	return;
        	default:
        		System.out.println("잘못된 선택입니다. 다시 선택해주세요.");
		}
	}
	
	private static void addOrder(Connection conn){
		// 케이크 메뉴 및 재고 현황 출력 

		Scanner sc = new Scanner(System.in);

		// 고객 정보 추가
		System.out.print("이름을 입력하세요: ");
		String name = sc.nextLine();
		String customerID = findCustomerID(conn, name); // customerID 불러오기

		// orders 테이블 정보 추가
		String ordersID = "O0"+Integer.toString(orders_index);
		orders_index++; // 변수 값 증가
		LocalDate date = LocalDate.now(); // 현재 날짜 불러오기
		addOrdersTable(conn, ordersID, customerID, date);

		boolean more = true; // 주문을 계속할 건지 확인하는 변수
		while (more) {
			System.out.print("주문할 케이크 ID를 입력하세요(ex):C001):");
			String cakeID = sc.nextLine();

			if (isValidCakeId(conn, cakeID)) { // 케이크 ID가 DB에 존재하면
				// 주문 계속 진행
				System.out.print("수량을 입력하세요: ");
				int quantity = Integer.parseInt(sc.nextLine());

				// orderitem 테이블에 data 추가
				String orderItemID = "I0"+Integer.toString(orderitem_index);
				addOrderitemTable(conn, orderItemID, ordersID, cakeID, quantity);

				// 재고 감소
//				updateInventoryTable(conn, quantity, cakeID);
//				String updateStockSQL = "UPDATE inventory SET quantity_available = quantity_available - ? WHERE cake_id = ?";
//          try (PreparedStatement ps = conn.prepareStatement(updateStockSQL)) {
//      	    ps.setInt(1, quantity);
//            ps.setString(2, cakeID);
//            ps.executeUpdate();
//					}

				System.out.print("더 주문하시겠습니까? (Y/N): ");
				String answer = sc.nextLine().toUpperCase();
				if (!answer.equals("Y")) {
					more = false;
				}
			}
			else { // 케이크 ID가 DB에 존재하지 않으면면
				System.out.println("존재하지 않는 케이크 ID입니다. 다시 입력해주세요.");
			}
		}
	}



	private static void updateCustomer(Connection conn) {
		// TODO Auto-generated method stub
		
	}
	
	//주문 정보 수정 또는 삭제 
	private static void updateOrder(Connection conn) {	
		Scanner sc = new Scanner(System.in);
		
		//주 번호 조회
		System.out.print("수정 또는 삭제할 주문 ID를 입력하세요 (ex: O001): ");
		String orderId = sc.nextLine();

		System.out.print("1.주문 삭제 ");
		System.out.println("2.주문 정보 수정 (주문한 케이크 수량 변경)");
		
		int choice = Integer.parseInt(sc.nextLine());
		
		// 주문 삭제 선택 
		if (choice == 1) { 
			try {
				// order item 테이블에서 해당 주문번호 삭제
				String deleteItemsSQL = "DELETE FROM orderitem WHERE orders_id = ?";
				try (PreparedStatement stmt = conn.prepareStatement(deleteItemsSQL)) {
					stmt.setString(1, orderId);
					stmt.executeUpdate();
				}
				// orders 테이블에서 해당 주문번호 삭제
				String deleteOrderSQL = "DELETE FROM orders WHERE orders_id = ?";
				try (PreparedStatement stmt = conn.prepareStatement(deleteOrderSQL)) {
					stmt.setString(1, orderId);
					stmt.executeUpdate();
				}
				System.out.println("주문이 삭제되었습니다.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
				
		// 주문 내역 수정 선택
		} else if (choice == 2) {
			System.out.print("수정하고 싶은 케이크 ID를 입력하세요 (ex: C001): ");
			String oldCakeId = sc.nextLine();
			
			System.out.println("새로운 케이크 ID를 입력하세요 (케이크 종류를 변경하지 않는다면 기존의 케이크 ID를 적어주세요): ");
			String newCakeId = sc.nextLine();
			
			if (!isValidCakeId(conn, newCakeId)) {
				System.out.println("케이크 ID가 존재하지 않습니다.");
				return;
			}
				
			System.out.print("새로운 수량을 입력하세요: ");
			int newQty = Integer.parseInt(sc.nextLine());

			String updateSQL = "UPDATE orderitem SET cake_id = ?, quantity = ? WHERE orders_id = ? AND cake_id = ?";
			try (PreparedStatement stmt = conn.prepareStatement(updateSQL)) {
				stmt.setString(1, newCakeId);
				stmt.setInt(2, newQty);
				stmt.setString(3, orderId);
				stmt.setString(4, oldCakeId);
				
				int rows = stmt.executeUpdate();
				if (rows > 0) {
					System.out.println("주문이 수정되었습니다.");
				} else {
					System.out.println("해당 주문 또는 케이크 ID를 찾을 수 없습니다.");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private static void showOrderDetail(Connection conn, String orderId) {
		// TODO Auto-generated method stub
		
	}

	private static void showCustomerOrder(Connection conn, String name) {
		// TODO Auto-generated method stub
		
	}

	// 입력한 cakeID가 DB에 존재하는지 확인하는 함수
	public static boolean isValidCakeId(Connection conn, String cakeId) {
		String query = "SELECT 1 FROM cake WHERE cake_id = ?";
		try (PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, cakeId);
			try (ResultSet rs = stmt.executeQuery()) {
				return rs.next(); // 존재하면 true
			}
		} catch (SQLException e){
			e.printStackTrace();
			return false;
		}
	}

	// 이름 입력을 통해 customerID를 찾고, 없을 경우 고객 정보 추가하는 함수
	public static String findCustomerID(Connection conn, String name) {
		Scanner sc = new Scanner(System.in);
		String query = "SELECT customer_id FROM customer WHERE name = ?";
		try (PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, name);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) { // 고객 정보가 존재할 경우
					return rs.getString("customer_id");
				} else { // 고객 정보가 존재하지 않을 경우
					System.out.println("고객 정보가 존재하지 않아 새로 추가합니다.");
					System.out.println("전화번호를 입력하세요(ex)010-1111-2222): ");
					String phone_num = sc.nextLine();
					String customerID = "U0"+Integer.toString(customer_index);
					customer_index++; // 변수 값 증가 -> 이후에 적용

					// DB customer 테이블에 data 추가가
					addCustomerTable(conn, customerID, name, phone_num);

					return customerID;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// customer 테이블에 데이터 추가 함수
	private static void addCustomerTable(Connection conn, String customerID, String name, String phone_num) {
		String sql = "INSERT INTO customer (customer_id, name, phone_number) VALUES (?, ?, ?)";
   		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
   			stmt.setString(1, customerID);
   			stmt.setString(2, name);
   			stmt.setString(3, phone_num);
   			stmt.executeUpdate();

   			System.out.println("고객 정보가 등록되었습니다.");
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
	}

	// orders 테이블에 데이터 추가 함수
	private static void addOrdersTable(Connection conn, String ordersID, String customerID, LocalDate date) {
		String sql = "INSERT INTO orderitem (order_item_id, orders_id, cake_id, quantity) VALUES (?, ?, ?, ?)";
   		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
   			stmt.setString(1, ordersID);
   			stmt.setString(2, customerID);
   			stmt.setDate(3, Date.valueOf(date));
			stmt.executeUpdate();
			
			System.out.println("주문이 완료되었습니다.");
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
	}

	// orderitem 테이블에 데이터 추가 함수
	private static void addOrderitemTable(Connection conn, String orderItemID, String ordersID, String cakeID, int quantity) {
		String sql = "INSERT INTO orderitem (order_item_id, orders_id, cake_id, quantity) VALUES (?, ?, ?, ?)";
   		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
   			stmt.setString(1, orderItemID);
   			stmt.setString(2, ordersID);
   			stmt.setString(3, cakeID);
   			stmt.executeUpdate();
			
   			System.out.println("주문이 완료되었습니다.");
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
	}

	public static void main(String[] args) {
		Connection conn = sql_connection.getConnection();
		초기화면();
		메뉴(conn); // DB 연결 이어서 가져감
		sql_connection.close();
	}

}
