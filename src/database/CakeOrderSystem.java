package database;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CakeOrderSystem {

	public static void 초기화면() {
		System.out.println("============================");
		System.out.printf("\t케이크 주문 시스템\n");
		System.out.println("============================");
		// 케이크 메뉴 및 재고 현황표
		// 인기 케이크 순위
		System.out.println();
	}
	
	public static void 메뉴() {
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
					addOrder();
        	break;
        case 2: // 전체 주문 목록 조회
        	System.out.println("이름을 입력해주세요: ");
        	String name = scanner.nextLine();
        	showCustomerOrder(name);
        	break;
        case 3: // 현재 주문 내역 조회
        	System.out.println("주문 ID 입력: ");
        	String orderId = scanner.nextLine();
        	showOrderDetail(orderId);
        	break;
        case 4:
        	updateOrder();
        	break;
        case 5:
        	updateCustomer();
        	break;
        case 0:
        	return;
        	default:
        		System.out.println("잘못된 선택입니다. 다시 선택해주세요.");
		}
	}
	
	private static void addOrder(){
		Scanner sc = new Scanner(System.in);
    System.out.print("주문 ID: ");
    String orderId = sc.nextLine();
    System.out.print("고객 ID: ");
    String customerId = sc.nextLine();
    System.out.print("주문 날짜 (YYYY-MM-DD): ");
    String date = sc.nextLine();

    String query = "INSERT INTO orders (orders_id, customer_id, orders_date) VALUES (?, ?, ?)";
		//    try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
    //    pstmt.setString(1, orderId);
  	//    pstmt.setString(2, customerId);
    //    pstmt.setDate(3, Date.valueOf(date));
    //    pstmt.executeUpdate();
    //    System.out.println("주문이 추가되었습니다.");
        }	
	}

	private static void updateCustomer() {
		// TODO Auto-generated method stub
		
	}

	private static void updateOrder() {
		// TODO Auto-generated method stub
		
	}

	private static void showOrderDetail(String orderId) {
		// TODO Auto-generated method stub
		
	}

	private static void showCustomerOrder(String name) {
		// TODO Auto-generated method stub
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		초기화면();
		메뉴();
		
	}

}
