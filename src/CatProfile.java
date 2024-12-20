import javax.swing.*;
import java.awt.*;

public class CatProfile extends JPanel {

    private ImageIcon peacefulCat = new ImageIcon("peacefulCat.jpg"); // 기본 고양이 이미지
    private ImageIcon hungryCat = new ImageIcon("hungryCat.jpg"); // 배고픈 고양이 이미지
    private ImageIcon fullCat = new ImageIcon("fullCat.jpg"); // 배부른 고양이 이미지
    public Image profile = null; // 현재 고양이 이미지 저장
    private JLabel text = new JLabel(); // 상태 메시지를

    public CatProfile(){
        setBackground(Color.white); // 배경색을 흰색으로 설정
        profile = peacefulCat.getImage(); // 초기 고양이 이미지를 평온 상태로 설정
        text.setText("밥 달라냥"); // 초기 상태 메시지 설정
    }


    public void setFullCat() {
        text.setText("맛있다냥"); // 상태 메시지 변경
        profile = fullCat.getImage(); // 배부른 고양이 이미지 설정
        repaint(); // 다시 그리기
        revalidate(); // 레이아웃을 다시 그리기
    }


    public void setHungryCat() {
        text.setText("밥 안 주냥"); // 상태 메시지 변경
        profile = hungryCat.getImage(); // 배고픈 고양이 이미지 설정
        repaint(); // 다시 그리기
        revalidate(); // 레이아웃을 다시 그리기
    }


    public void setPeacefulCat() {
        text.setText("밥 달라냥"); // 상태 메시지 변경
        profile = peacefulCat.getImage(); // 평온한 고양이 이미지 설정
        repaint(); // 다시 그리기
        revalidate(); // 레이아웃을 다시 그리기
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // 기본 그리기
        g.drawImage(profile, 0, 0, getWidth(), getHeight(), this); // 현재 상태의 고양이 이미지를 패널에 그리기
    }
}
