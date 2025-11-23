import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 게임 메인 플레이 영역 패널
 * 물고기 낙하, 사용자 입력 처리, 일시정지 기능 등을 관리
 */
class GameGroundPanel extends JPanel {
    private boolean isPaused = false; // 게임 일시정지 상태
    private CopyOnWriteArrayList<JLabel> fallingLabels = new CopyOnWriteArrayList<>(); // 떨어지는 라벨들을 저장 (스레드 안전)
    private CopyOnWriteArrayList<FallingThread> fallingThreads = new CopyOnWriteArrayList<>(); // FallingThread들을 저장 (스레드 안전)
    private ScorePanel scorePanel; // 점수 패널
    private TextSource textSource; // 단어 관리 객체
    private GenerateThread generateThread; // 라벨 생성 객체
    private JTextField inputField; // 입력 받을 텍스트 필드
    private CatProfile catProfile; // 고양이 프로필 패널
    private ImageIcon gameBackground = new ImageIcon("gameBackground.jpg"); // 게임 배경
    private Image backgroundImage = gameBackground.getImage(); // 배경 이미지 아이콘을 이미지로 변환

    public GameGroundPanel(ScorePanel scorePanel, TextSource textSource, CatProfile catProfile) {
        this.scorePanel = scorePanel; // 점수 패널 설정
        this.textSource = textSource; // 텍스트 소스 설정
        this.catProfile = catProfile; // 고양이 상태 패널 설정

        setLayout(null); // 레이아웃 null로 설정

        inputField = new JTextField(20); // 입력 필드 초기화
        inputField.setBounds(300, 800, 300, 30); // 입력 필드 위치와 크기 설정
        inputField.setBackground(Color.white); // 배경색 설정
        add(inputField); // 패널에 추가

        inputField.setFocusable(true); // 입력 필드에 포커스 설정
        inputField.requestFocus(); // 입력 필드에 자동으로 포커스 이동
        inputField.addActionListener(new ActionListener() { // 엔터 입력 시 이벤트 처리
            @Override
            public void actionPerformed(ActionEvent e) {
                checkInput(inputField.getText()); // 입력된 텍스트 확인
                inputField.setText(""); // 입력 필드 초기화
            }
        });

        // ESC 키로 일시정지 기능 추가
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    togglePause(); // ESC 키 누르면 일시정지/재개 토글
                }
            }
        });
    }

    public void addFallingLabel(JLabel label) {
        fallingLabels.add(label); // 벡터에 라벨 추가
        add(label); // 패널에 라벨 추가
        repaint(); // 화면 다시 그리기
    }

    public void removeFallingLabel(JLabel label) {
        fallingLabels.remove(label); // 벡터에서 라벨 제거
        remove(label); // 패널에서 라벨 제거
        repaint(); // 화면 다시 그리기
    }

    public boolean containsLabel(JLabel label) {
        return fallingLabels.contains(label);
    }

    public void startGame() { //게임 시작
        generateThread = new GenerateThread(this, scorePanel, textSource, catProfile); // GenerateThread 생성
        generateThread.start(); // 스레드 시작
    }

    public void stopGame() { //게임 종료
        if (generateThread != null) { // 스레드가 존재하면
            generateThread.stopRunning(); // 스레드 정지
        }

        // 모든 FallingThread 정리
        for (FallingThread thread : fallingThreads) {
            thread.interrupt(); // 스레드 인터럽트
        }
        fallingThreads.clear(); // 스레드 벡터 비우기

        // 모든 라벨 제거
        for (JLabel label : fallingLabels) {
            remove(label);
        }
        fallingLabels.clear(); // 라벨 벡터 비우기

        isPaused = false; // 일시정지 상태 초기화
        repaint(); // 화면 다시 그리기
    }

    public boolean isGameOver() {
        return scorePanel.getPatience() <= 0; //생명이 0 이하인지 확인하여 반환
    }

    public void freezeAllThreads(int duration) { //일시정지
        for (FallingThread thread : fallingThreads) { // 모든 FallingThread 정지
            thread.freeze();
        }
        if (generateThread != null) { // GenerateThread 정지
            generateThread.freeze();
        }

        Timer resumeTimer = new Timer(duration, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (FallingThread thread : fallingThreads) { // FallingThread 재개
                    thread.resumeThread();
                }
                if (generateThread != null) { // GenerateThread 재개
                    generateThread.resumeThread();
                }
            }
        });

        resumeTimer.setRepeats(false); // 타이머 한 번 실행
        resumeTimer.start(); // 타이머 시작
    }

    private synchronized void checkInput(String input) { //단어 맞는지 여부
        boolean matched = false; // 일치 여부
        for (JLabel label : fallingLabels) { // 모든 라벨을 돌며
            if (label.getText().equals(input)) { // 라벨의 텍스트와 입력값 비교
                if (label.getName() != null) { // 라벨의 이름이 있을 경우
                    switch (label.getName()) {
                        case "bigFish": // bigFish 이면
                            scorePanel.increase(GameConstants.SCORE_BIG_FISH); // 큰 물고기 점수 추가
                            catProfile.setFullCat(); // 배부른 고양이로 설정
                            BGMThread coinBgm = new BGMThread(GameConstants.BGM_COIN); // 코인 효과음 실행
                            coinBgm.start(); // 스레드 시작
                            break;
                        case "heart": // heart 이면
                            scorePanel.increasePatience(); // 생명 추가
                            catProfile.setFullCat(); // 배부른 고양이로 설정
                            break;
                        case "timeStop": // timeStop 이면
                            freezeAllThreads(GameConstants.TIME_STOP_DURATION); // 타임스탑 지속시간만큼 정지
                            catProfile.setFullCat(); // 배부른 고양이로 설정
                            break;
                        default: // 일반 단어인 경우
                            scorePanel.increase(); // 난이도에 따라 점수 증가
                            catProfile.setFullCat(); // 배부른 고양이로 설정
                            break;
                    }
                }
                removeFallingLabel(label); // 라벨 제거
                matched = true; // 일치 여부를 true로 설정
                break;
            }
        }

        if (!matched) { // 일치하는 라벨이 없으면
            catProfile.setHungryCat(); // 배고픈 고양이로 설정
        }
    }

    public void addFallingThread(FallingThread thread) {
        fallingThreads.add(thread); // FallingThread를 벡터에 추가
    }

    // 게임 일시정지/재개 토글
    public void togglePause() {
        isPaused = !isPaused;
        if (isPaused) {
            // 일시정지: 모든 스레드 정지
            for (FallingThread thread : fallingThreads) {
                thread.freeze();
            }
            if (generateThread != null) {
                generateThread.freeze();
            }
        } else {
            // 재개: 모든 스레드 재개
            for (FallingThread thread : fallingThreads) {
                thread.resumeThread();
            }
            if (generateThread != null) {
                generateThread.resumeThread();
            }
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // 기본 그리기
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null); // 배경 이미지 그리기

        // 일시정지 상태일 때 "PAUSED" 표시
        if (isPaused) {
            g.setColor(new Color(0, 0, 0, 150)); // 반투명 검은색 오버레이
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.WHITE);
            g.setFont(new Font("Malgun Gothic", Font.BOLD, 80));
            String pauseText = "일시정지 (ESC로 재개)";
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(pauseText);
            g.drawString(pauseText, (getWidth() - textWidth) / 2, getHeight() / 2);
        }
    }
}
