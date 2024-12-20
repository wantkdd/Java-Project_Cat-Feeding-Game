import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    private CardLayout cardLayout; //카드 레이아웃위한 객체
    private JPanel cardPanel; //카드레이아웃에 추가할 카드패널 객체
    private BGMThread bgm; //bgm쓰레드 사용 위한 객체
    private ScorePanel scorePanel; //점수패널 사용 위한 객체

    public GameFrame(){
        setTitle("고양이 밥 주기!"); //제목 설정
        setSize(1520,1000); //크기 설정
        setResizable(false); //크기 재설정 불가
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //JFrame종료 시 프로그램 종료

        cardLayout = new CardLayout(); //카드 레이아웃 생성
        cardPanel = new JPanel(cardLayout); //카드 레이아웃을 패널에 추가

        TextSource textSource = new TextSource(); //단어관리 객체 생성
        scorePanel = new ScorePanel(); //점수패널 생성
        CatProfile catProfile = new CatProfile(); //프로필 패널 생성

        GamePanel gamePanel = new GamePanel(this, scorePanel, catProfile, textSource); //게임패널 생성
        StartPanel startPanel = new StartPanel(this, gamePanel, scorePanel); //시작패널 생성
        FinishPanel finishPanel = new FinishPanel(this, scorePanel); //종료패널 생성
        InfoPanel infoPanel = new InfoPanel(this); //도움말 패널 생성

        cardPanel.add(startPanel, "StartPanel");  //시작화면 추가
        cardPanel.add(gamePanel, "GamePanel"); //게임화면 추가
        cardPanel.add(finishPanel, "FinishPanel"); //종료화면 추가
        cardPanel.add(infoPanel, "InfoPanel"); //도움말 화면 추가

        add(cardPanel); //프레임에 카드패널 추가

        bgm = new BGMThread("lobbyBgm.wav"); //시작화면 BGM설정
        bgm.start(); //BGM 실행

        setVisible(true);  //프레임 화면에 출력
        cardLayout.show(cardPanel,"StartPanel"); //초기 표시 패널 시작패널로 설정
        
    }

    public void showPanel(String panelName) { //panelName을 매개변수로 받아 패널을 보여주는 함수
        if (bgm != null) { //bgm이 설정되어 있다면
            bgm.stopBGM(); //bgm 멈춤
        }

        switch (panelName) {  //패널 이름이
            case "StartPanel": //startPanel이라면
                bgm = new BGMThread("lobbyBgm.wav"); //lobbyBGM으로 설정
                break;
            case "GamePanel": //GamePanel이라면
                int difficulty = scorePanel.getDifficulty(); //난이도 변수 가져옴
                if (difficulty == 1) { //난이도가 1이라면
                    bgm = new BGMThread("easyBgm.wav"); //easyBGM 설정
                } else if (difficulty == 2) { //2라면
                    bgm = new BGMThread("normalBgm.wav"); //normalBGM설정
                } else if (difficulty == 3) { //3이라면
                    bgm = new BGMThread("hardBgm.wav"); //hardBGM설정
                }
                break;
            case "FinishPanel": //FinishPanel이라면
                bgm = new BGMThread("lobbyBgm.wav"); //lobbyBGM 설정
                break;
            case "InfoPanel":
                bgm = null; // InfoPanel에서는 배경음악 없음
                break;
        }

        if (bgm != null) { //bgm이 설정되었다면
            bgm.start(); //bgm 시작
        }

        cardLayout.show(cardPanel, panelName); //panelName패널로 전환
    }

    public static void main(String[] args) {
        new GameFrame(); //gameFrame 객체 생성
    }
}