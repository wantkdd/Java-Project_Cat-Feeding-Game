import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    private CardLayout cardLayout; // 카드 레이아웃을 위한 객체
    private JPanel cardPanel; // 카드 레이아웃에 추가할 카드 패널 객체
    private BGMThread bgm; // BGM 스레드 사용을 위한 객체
    private ScorePanel scorePanel; // 점수 패널 사용을 위한 객체

    public GameFrame() {
        setTitle("고양이 밥 주기!"); // 제목 설정
        setSize(GameConstants.GAME_WIDTH, GameConstants.GAME_HEIGHT); // 크기 설정
        setResizable(false); // 크기 재설정 불가
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // JFrame 종료 시 프로그램 종료

        cardLayout = new CardLayout(); // 카드 레이아웃 생성
        cardPanel = new JPanel(cardLayout); // 카드 레이아웃을 패널에 추가

        TextSource textSource = new TextSource(); // 단어 관리 객체 생성
        scorePanel = new ScorePanel(); // 점수 패널 생성
        CatProfile catProfile = new CatProfile(); // 프로필 패널 생성

        GamePanel gamePanel = new GamePanel(this, scorePanel, catProfile, textSource); // 게임 패널 생성
        StartPanel startPanel = new StartPanel(this, gamePanel, scorePanel); // 시작 패널 생성
        FinishPanel finishPanel = new FinishPanel(this, scorePanel); // 종료 패널 생성
        InfoPanel infoPanel = new InfoPanel(this); // 도움말 패널 생성

        cardPanel.add(startPanel, "StartPanel"); // 시작 화면 추가
        cardPanel.add(gamePanel, "GamePanel"); // 게임 화면 추가
        cardPanel.add(finishPanel, "FinishPanel"); // 종료 화면 추가
        cardPanel.add(infoPanel, "InfoPanel"); // 도움말 화면 추가

        add(cardPanel); // 프레임에 카드 패널 추가

        bgm = new BGMThread(GameConstants.BGM_LOBBY); // 시작 화면 BGM 설정
        bgm.start(); // BGM 실행

        setVisible(true); // 프레임 화면에 출력
        cardLayout.show(cardPanel, "StartPanel"); // 초기 표시 패널을 시작 패널로 설정
    }

    // panelName을 매개변수로 받아 패널을 보여주는 함수
    public void showPanel(String panelName) {
        if (bgm != null) { // BGM이 설정되어 있다면
            bgm.stopBGM(); // BGM 멈춤
        }

        switch (panelName) { // 패널 이름에 따라
            case "StartPanel": // StartPanel이라면
                bgm = new BGMThread(GameConstants.BGM_LOBBY); // 로비 BGM으로 설정
                break;
            case "GamePanel": // GamePanel이라면
                int difficulty = scorePanel.getDifficulty(); // 난이도 변수 가져옴
                if (difficulty == GameConstants.DIFFICULTY_EASY) { // 난이도가 Easy라면
                    bgm = new BGMThread(GameConstants.BGM_EASY); // Easy BGM 설정
                } else if (difficulty == GameConstants.DIFFICULTY_NORMAL) { // Normal이라면
                    bgm = new BGMThread(GameConstants.BGM_NORMAL); // Normal BGM 설정
                } else if (difficulty == GameConstants.DIFFICULTY_HARD) { // Hard라면
                    bgm = new BGMThread(GameConstants.BGM_HARD); // Hard BGM 설정
                }
                break;
            case "FinishPanel": // FinishPanel이라면
                bgm = new BGMThread(GameConstants.BGM_LOBBY); // 로비 BGM 설정
                break;
            case "InfoPanel": // InfoPanel이라면
                bgm = null; // 배경음악 없음
                break;
        }

        if (bgm != null) { // BGM이 설정되었다면
            bgm.start(); // BGM 시작
        }

        cardLayout.show(cardPanel, panelName); // panelName 패널로 전환
    }

    public static void main(String[] args) {
        new GameFrame(); // GameFrame 객체 생성
    }
}