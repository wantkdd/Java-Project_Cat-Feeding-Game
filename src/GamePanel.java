import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private GameGroundPanel ground; // 게임 영역 패널 (
    private ScorePanel scorePanel; // 점수 패널
    private CatProfile catProfile; // 고양이 프로필
    private TextSource textSource; // 단어 제어 객체
    private GameFrame gameFrame; // 게임 프레임


    public GamePanel(GameFrame gameFrame, ScorePanel scorePanel, CatProfile catProfile, TextSource textSource) {
        this.scorePanel = scorePanel; // 점수 패널 초기화
        this.catProfile = catProfile; // 고양이 프로필 초기화
        this.textSource = textSource; // 단어 소스 초기화
        this.gameFrame = gameFrame; // 게임 프레임 초기화

        this.setLayout(new BorderLayout()); // 레이아웃 BorderLayout으로 설정
        this.setSize(1520, 1080); // 패널의 크기 설정

        ground = new GameGroundPanel(scorePanel, textSource, catProfile); // 게임 영역 패널 생성
        splitPanel(); // 패널을 화면에 분리하여 추가
    }


    private void splitPanel() {
        JSplitPane hPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT); // 좌우 분리 패널 생성
        hPane.setDividerLocation(920); // 좌우 분리 기준 위치 설정
        hPane.setDividerSize(1); // 구분선 두께 설정
        add(hPane); // 메인 패널에 좌우 분리 패널 추가

        JSplitPane vPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT); // 상하 분리 패널 생성
        vPane.setDividerLocation(480); // 상하 분리 기준 위치 설정
        vPane.setDividerSize(1); // 구분선 두께 설정
        vPane.setTopComponent(scorePanel); // 상단 패널에 점수 패널 추가
        vPane.setBottomComponent(catProfile); // 하단 패널에 고양이 상태 패널 추가
        catProfile.setPeacefulCat(); // 초기 고양이 평화 고양이로 설정

        hPane.setRightComponent(vPane); // 오른쪽에 상하 분리 패널 추가
        hPane.setLeftComponent(ground); // 왼쪽에 게임 영역 패널 추가
    }


    public void startGame() { //게임 시작
        ground.startGame(); // GameGroundPanel의 게임 시작
    }


    public void stopGame() { //게임 정지
        ground.stopGame(); // GameGroundPanel의 게임 정지
    }


    public void checkGameOver() { //게임 정지 여부 확인
        if (ground.isGameOver()) { // GameGroundPanel에서 생명이 소진되었는지 확인
            stopGame(); // 게임 정지
            gameFrame.showPanel("FinishPanel"); // 게임 종료 화면으로 전환
        }
    }
}
