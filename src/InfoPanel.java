import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InfoPanel extends JPanel {
    private GameFrame gameFrame = null; //게임 프레임 생성
    private ImageIcon infoIcon = new ImageIcon("infoBackGround.png"); // 인포 배경
    private Image info = infoIcon.getImage(); //인포 배경 이미지 변환
    private ImageIcon toMainButton = new ImageIcon("toMainButton.png"); //메인버튼 이미지
    private JButton toMain = new JButton(toMainButton); //메인으로 버튼 생성

    public InfoPanel(GameFrame gameFrame){
        this.gameFrame = gameFrame; //게임 프레임 초기화
        setSize(1520,1080); //패널 크기 설정
        setLayout(null); //레이아웃 null로 설정

        toMain.setBounds(1300,850,200,100); //버튼 위치 및 크기 설정
        toMain.setBorderPainted(false); //버튼 테두리 제거
        toMain.setContentAreaFilled(false); //배경 제거
        toMain.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { //버튼 클릭시
                gameFrame.showPanel("StartPanel"); //시작화면으로 전환
            }
        });
        add(toMain); //버튼을 패널에 추가

        setVisible(true); //패널 화면에 출력
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); //기본 그리기
        g.drawImage(info, 0, 0, getWidth(), getHeight(), null); //배경 이미지 그리기
    }
}
