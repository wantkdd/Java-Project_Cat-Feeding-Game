import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StartPanel extends JPanel {

    private ImageIcon startBackgroundImage = new ImageIcon("introBackground.jpg"); // 배경 이미지
    private Image backgroundImage = startBackgroundImage.getImage(); // 배경 이미지 객체
    private TextSource textSource = new TextSource(); // 단어 제어 객체 생성
    private ScorePanel scorePanel = new ScorePanel(); // 점수 패널 객체 생성
    private CatProfile catProfile = new CatProfile(); // 고양이 프로필 객체 생성
    private GameFrame gameFrame = null; // 게임 프레임
    private GamePanel gamePanel = new GamePanel(gameFrame, scorePanel, catProfile, textSource); // 게임 패널 객체 생성

    private ImageIcon startButton = new ImageIcon("startButton.png"); // 시작 버튼 이미지
    private JButton start = new JButton(startButton); // 시작 버튼 생성
    private ImageIcon addWordButton = new ImageIcon("addWordButton.png"); // 단어 추가 버튼 이미지
    private JButton addWord = new JButton(addWordButton); // 단어 추가 버튼 생성
    private ImageIcon mealIcon = new ImageIcon("meal.png"); // meal 이미지
    private JLabel meal = new JLabel(mealIcon); // meal 이미지 라벨에 추가
    private JTextField addWordTf = new JTextField(20); // 단어 입력 필드 생성
    private ImageIcon hardButton = new ImageIcon("hardButton.png"); // 하드 버튼 이미지
    private JButton hard = new JButton(hardButton); // 하드 난이도 버튼 생성
    private ImageIcon normalButton = new ImageIcon("normalButton.png"); // 노멀 버튼 이미지
    private JButton normal = new JButton(normalButton); // 노멀 난이도 버튼 생성
    private ImageIcon easyButton = new ImageIcon("easyButton.png"); // 이지 버튼 이미지
    private JButton easy = new JButton(easyButton); // 이지 난이도 버튼 생성
    private ImageIcon titleButton = new ImageIcon("title.png"); // 타이틀 이미지
    private JLabel title = new JLabel(titleButton); // 타이틀 라벨 생성
    private ImageIcon infoButton = new ImageIcon("infoButton.png"); // 도움말 버튼 이미지
    private JButton info = new JButton(infoButton); // 도움말 버튼 생성

    public StartPanel(GameFrame gameFrame, GamePanel gamePanel, ScorePanel scorePanel) {

        this.gameFrame = gameFrame; // 게임 프레임 초기화
        this.scorePanel = scorePanel; // 점수 패널 초기화

        setLayout(null); // 레이아웃을 null로 설정

        title.setSize(1100, 300); // 타이틀 크기 설정
        title.setLocation(200, 50); // 타이틀 위치 설정
        add(title); // 타이틀 추가

        start.setSize(330, 140); // 버튼 크기 설정
        start.setLocation(600, 500); // 버튼 위치 설정
        addHoverEffect(start); // 마우스 호버 효과 추가
        start.setBorderPainted(false); // 테두리 제거
        start.setContentAreaFilled(false); // 배경 제거
        start.addMouseListener(new MouseAdapter() { //마우스 리스너 추가
            @Override
            public void mouseClicked(MouseEvent e) { //클릭 시
                gamePanel.startGame(); // 게임 시작
                gameFrame.showPanel("GamePanel"); // GamePanel로 화면 전환
            }
        });
        add(start); // start 추가

        meal.setSize(100, 100); // 이미지 크기 설정
        meal.setLocation(1390, 482); // 이미지 위치 설정
        add(meal); // meal 추가

        addWord.setSize(200, 100); // 버튼 크기 설정
        addWord.setLocation(1340, 501); // 버튼 위치 설정
        addHoverEffect(addWord); // 마우스 호버 효과 추가
        addWord.setBorderPainted(false); // 테두리 제거
        addWord.setContentAreaFilled(false); // 배경 제거
        addWord.addMouseListener(new MouseAdapter() { //마우스 리스너 추가
            @Override
            public void mouseClicked(MouseEvent e) { //클릭 시
                JTextField tf = addWordTf; // 입력 필드 참조
                String inputWord = tf.getText(); // 입력된 단어 가져오기
                if (!inputWord.isEmpty()) // 입력이 비어있지 않은 경우
                    textSource.addWord(inputWord); // 단어 추가
                animateMealLabel(); // meal 이미지 애니메이션
                tf.setText(""); // 입력 필드 초기화
            }
        });
        add(addWord); // addWord 추가

        addWordTf.setSize(200, 30); // 입력 필드 크기 설정
        addWordTf.setLocation(1200, 550); // 입력 필드 위치 설정
        add(addWordTf); // 입력 필드 추가



        hard.setSize(220, 120); // 버튼 크기 설정
        hard.setLocation(1000, 370); // 버튼 위치 설정
        addHoverEffect(hard); // 버튼에 마우스 호버 효과 추가
        hard.setBorderPainted(false); // 버튼 테두리 제거
        hard.setContentAreaFilled(false); // 버튼 배경  제거
        hard.addMouseListener(new MouseAdapter() { // 마우스리스너 추가
            @Override
            public void mouseClicked(MouseEvent e) { // 클릭 시
                hard.setSize(hard.getWidth() + 10, hard.getHeight() + 5); //크기 확대
                scorePanel.hard(); // 하드 적용
            }
        });
        add(hard); // 하드 버튼 추가


        normal.setSize(220, 120); // 버튼 크기 설정
        normal.setLocation(650, 370); // 버튼 위치 설정
        addHoverEffect(normal); // 버튼에 마우스 호버 효과 추가
        normal.setBorderPainted(false); // 버튼 테두리 제거
        normal.setContentAreaFilled(false); // 버튼 배경 제거
        normal.addMouseListener(new MouseAdapter() { // 마우스 리스너 추가
            @Override
            public void mouseClicked(MouseEvent e) { // 클릭 시
                normal.setSize(normal.getWidth() + 10, normal.getHeight() + 5); // 크기 확대
                scorePanel.normal(); // 노멀 적용
            }
        });
        add(normal); // 노멀 버튼 추가


        easy.setSize(220, 120); // 버튼 크기 설정
        easy.setLocation(300, 370); // 버튼 위치 설정
        easy.setBorderPainted(false); // 버튼 테두리 제거
        easy.setContentAreaFilled(false); // 버튼 배경 제거
        addHoverEffect(easy); // 버튼에 마우스 호버 효과 추가
        easy.addMouseListener(new MouseAdapter() { // 마우스 리스너 추가
            @Override
            public void mouseClicked(MouseEvent e) { // 클릭 시
                easy.setSize(easy.getWidth() + 10, easy.getHeight() + 5); // 크기 확대
                scorePanel.easy(); // 이지 적용
            }
        });
        add(easy); // 이지 버튼 추가


        info.setSize(220, 120); // 버튼 크기 설정
        info.setLocation(1300, 850); // 버튼 위치 설정
        info.setBorderPainted(false); // 버튼 테두리 제거
        info.setContentAreaFilled(false); // 버튼 배경 제거
        addHoverEffect(info); // 버튼에 마우스 호버 효과 추가
        info.addMouseListener(new MouseAdapter() { // 마우스 리스너 추가
            @Override
            public void mouseClicked(MouseEvent e) { // 클릭 시
                gameFrame.showPanel("InfoPanel"); // InfoPanel로 전환
            }
        });
        add(info); //도움말 버튼 추가

        setVisible(true); // 패널을 화면에 출력

    } // 생성자 종료


    private void addHoverEffect(JButton button) { //호버 효과
        button.addMouseListener(new MouseAdapter() { // 마우스리스너 추가
            @Override
            public void mouseEntered(MouseEvent e) { // 마우스 올렸을 때
                button.setSize(new Dimension(button.getWidth() + 15, button.getHeight() + 15)); // 버튼 크기 확대
            }

            @Override
            public void mouseExited(MouseEvent e) { // 마우스 내렸을 때
                button.setSize(new Dimension(button.getWidth() - 15, button.getHeight() - 15)); // 버튼 크기 복원
            }
        });
    }


    private void animateMealLabel() { // meal 애니메이션 설정
        JTextField tf = addWordTf; // 단어 입력 필드 가져오기
        String inputWord = tf.getText(); // 입력된 단어 가져오기
        if (inputWord.isEmpty()) return; // 입력된 단어가 없으면 리턴

        Runnable animationTask = new Runnable() { //Runnable 생성
            @Override
            public void run() {
                int originalY = meal.getY(); // meal의 원래 y 위치 저장
                try {
                    meal.setLocation(meal.getX(), originalY - 30); // meal 이미지를 위로 30픽셀 이동
                    Thread.sleep(300); // 0.3초 대기
                    meal.setLocation(meal.getX(), originalY); // meal 이미지를 원래 위치로 복원
                } catch (InterruptedException e) { //예외 발생 시
                    Thread.currentThread().interrupt(); // 현재 쓰레드 중단
                }
            }
        };

        Thread animationThread = new Thread(animationTask); // 쓰레드 생성
        animationThread.start(); // 쓰레드 실행
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // 기본 그리기
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null); // 배경 이미지 그리기
    }

}
