import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * 게임의 점수, 생명(하트), 난이도를 관리하는 패널
 * 점수 증감, 생명 관리, 난이도별 게임 속도 설정 등을 담당
 */
public class ScorePanel extends JPanel {
    private int score = 0; // 현재 점수
    private JLabel scoreLabel = new JLabel("점수 : 0"); // 점수를 표시하는 라벨
    private int patience = GameConstants.INITIAL_PATIENCE; // 현재 생명
    private int speed = 0; // 현재 게임 속도 (ms)
    private int difficulty = 0; // 현재 난이도

    private ArrayList<JLabel> hearts = new ArrayList<>(); // 하트 UI 라벨 리스트
    private final ImageIcon heart; // 하트 이미지 아이콘 (캐싱)

    /**
     * ScorePanel 생성자
     * 초기 UI 설정 및 하트 표시
     */
    public ScorePanel() {
        // 하트 이미지 로드 및 캐싱
        heart = new ImageIcon(new ImageIcon("heart.png").getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH));

        this.setBackground(new Color(255, 193, 204)); // 배경색 설정
        this.setLayout(null); // 절대 레이아웃 사용

        // 점수 라벨 설정
        scoreLabel.setBounds(50, 40, 500, 100);
        scoreLabel.setFont(new Font("MalgunGothic", Font.BOLD, 60));
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(scoreLabel);

        // 초기 생명(하트) 라벨 설정
        for (int i = 0; i < patience; i++) {
            JLabel heartImg = new JLabel(heart);
            heartImg.setBounds(50 + (i * 100), 250, 90, 90);
            hearts.add(heartImg);
            add(heartImg);
        }
    }

    /**
     * 난이도에 따라 점수를 증가시킴
     * Easy: +1, Normal: +2, Hard: +3
     */
    public void increase() {
        switch (difficulty) {
            case GameConstants.DIFFICULTY_EASY:
                score += GameConstants.SCORE_MULTIPLIER_EASY;
                break;
            case GameConstants.DIFFICULTY_NORMAL:
                score += GameConstants.SCORE_MULTIPLIER_NORMAL;
                break;
            case GameConstants.DIFFICULTY_HARD:
                score += GameConstants.SCORE_MULTIPLIER_HARD;
                break;
        }
        updateScoreLabel();
    }

    /**
     * 지정된 점수만큼 증가시킴 (특수 아이템용)
     * @param amount 증가시킬 점수
     */
    public void increase(int amount) {
        score += amount;
        updateScoreLabel();
    }

    /**
     * 점수 라벨 UI 업데이트
     */
    private void updateScoreLabel() {
        scoreLabel.setText("점수: " + score);
    }

    /**
     * 현재 점수 반환
     * @return 현재 점수
     */
    public int getScore() {
        return score;
    }

    /**
     * Easy 난이도 설정
     * 생명 5개, 속도 300ms, 점수 배율 1배
     */
    public void easy() {
        patience = GameConstants.INITIAL_PATIENCE;
        speed = GameConstants.SPEED_EASY;
        difficulty = GameConstants.DIFFICULTY_EASY;
        resetGame();
    }

    /**
     * Normal 난이도 설정
     * 생명 5개, 속도 200ms, 점수 배율 2배
     */
    public void normal() {
        patience = GameConstants.INITIAL_PATIENCE;
        speed = GameConstants.SPEED_NORMAL;
        difficulty = GameConstants.DIFFICULTY_NORMAL;
        resetGame();
    }

    /**
     * Hard 난이도 설정
     * 생명 5개, 속도 100ms, 점수 배율 3배
     */
    public void hard() {
        patience = GameConstants.INITIAL_PATIENCE;
        speed = GameConstants.SPEED_HARD;
        difficulty = GameConstants.DIFFICULTY_HARD;
        resetGame();
    }

    /**
     * 게임 재시작 시 점수와 하트를 초기화
     */
    private void resetGame() {
        score = 0; // 점수 초기화
        updateScoreLabel(); // 점수 라벨 갱신
        resetHearts(); // 하트 UI 초기화
    }

    /**
     * 남은 생명 수 반환
     * @return 현재 생명
     */
    public int getPatience() {
        return patience;
    }

    /**
     * 현재 게임 속도 반환
     * @return 게임 속도 (ms)
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * 현재 난이도 반환
     * @return 난이도 (1: Easy, 2: Normal, 3: Hard)
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * 생명을 1 감소시킴
     * 생명이 0이 되면 게임 오버 체크
     */
    public void decreasePatience() {
        if (patience > 0) {
            patience--;
            removeHeart();
        } else if (patience <= 0) {
            GamePanel gamePanel = findGamePanel();
            if (gamePanel != null) {
                gamePanel.checkGameOver();
            }
        }
    }

    /**
     * 생명을 1 증가시킴 (최대 5개)
     */
    public void increasePatience() {
        if (patience < GameConstants.MAX_PATIENCE) {
            patience++;
            addHeart();
        }
    }

    /**
     * 하트 UI를 현재 생명 수에 맞게 초기화
     */
    private void resetHearts() {
        // 기존 하트 UI 제거
        for (JLabel heartLabel : hearts) {
            remove(heartLabel);
        }
        hearts.clear();

        // 현재 생명 수만큼 하트 UI 생성
        for (int i = 0; i < patience; i++) {
            JLabel heartLabel = new JLabel(heart);
            heartLabel.setBounds(50 + (i * 100), 250, 90, 90);
            hearts.add(heartLabel);
            add(heartLabel);
        }

        revalidate();
        repaint();
    }

    /**
     * 하트 UI를 하나 제거
     */
    private void removeHeart() {
        if (!hearts.isEmpty()) {
            JLabel heartToRemove = hearts.get(hearts.size() - 1);
            hearts.remove(heartToRemove);
            remove(heartToRemove);
            revalidate();
            repaint();
        }
    }

    /**
     * 하트 UI를 하나 추가
     */
    private void addHeart() {
        JLabel newHeart = new JLabel(heart);
        newHeart.setBounds(50 + (hearts.size() * 100), 250, 90, 90);
        hearts.add(newHeart);
        add(newHeart);
        revalidate();
        repaint();
    }

    /**
     * 부모 컴포넌트 계층에서 GamePanel을 찾아 반환
     * @return GamePanel 인스턴스 또는 null
     */
    private GamePanel findGamePanel() {
        Container parent = getParent();
        while (parent != null) {
            if (parent instanceof GamePanel) {
                return (GamePanel) parent;
            }
            parent = parent.getParent();
        }
        return null;
    }
}
