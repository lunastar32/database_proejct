package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CakeOrder extends JFrame{

	public CakeOrder() {
		setTitle("🍰 케이크 주문 시스템");
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 창을 화면 가운데 정렬
	
        // 제목 라벨
        JLabel titleLabel = new JLabel("케이크 주문 시스템", JLabel.CENTER);
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
	
        // 버튼 패널
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
	
        String[] buttonLabels = {
                "케이크 주문",
                "전체 주문 목록 조회",
                "현재 주문 상세 내역",
                "주문 수정",
                "고객 정보 수정",
                "종료"
        };
        
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("맑은 고딕", Font.PLAIN, 14));

            // 버튼 클릭 이벤트 연결 (기본은 메시지박스)
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (label.equals("종료")) {
                        System.exit(0);
                    } else {
                        JOptionPane.showMessageDialog(
                            CakeOrder.this,
                            label + " 기능이 호출되었습니다.",
                            "알림",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                }
            });

            buttonPanel.add(button);
        }
            
        // 레이아웃 설정
        setLayout(new BorderLayout());
        add(titleLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
            new CakeOrder().setVisible(true);
        });
	}
}
