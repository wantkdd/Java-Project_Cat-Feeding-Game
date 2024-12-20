import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ScorePanel extends JPanel { // 점수 관리 클래스
    private int score = 0; // 점수 저장
    private JLabel scoreLabel = new JLabel("점수 : 0"); // 점수를 표시하는 라벨
    private int patience = 5; // 기본 생명 개수 설정
    private int speed = 0; // 게임 속도 설정
    private int difficulty = 0; // 난이도 설정

    private ArrayList<JLabel> hearts = new ArrayList<>(); // 하트 라벨을 저장할 리스트
    private ImageIcon heart = new ImageIcon(new ImageIcon("heart.png").getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH)); // 하트 이미지 설정

    public ScorePanel() {
        this.setBackground(new Color(255, 193, 204)); // 배경색 설정
        this.setLayout(null); // 레이아웃 null로 설정

        // 점수 라벨 설정
        scoreLabel.setBounds(50, 40, 500, 100);
        scoreLabel.setFont(new Font("MalgunGothic", Font.BOLD, 60)); // 폰트 크기 설정
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER); // 텍스트 가운데 정렬
        add(scoreLabel); // 점수 라벨 추가

        // 초기 생명(하트) 라벨 설정
        for (int i = 0; i < patience; i++) {
            JLabel heartImg = new JLabel(heart); // 하트 이미지 라벨 생성
            heartImg.setBounds(50 + (i * 100), 250, 90, 90); // 하트 위치 설정
            hearts.add(heartImg); // 리스트에 하트 추가
            add(heartImg); // 패널에 하트 추가
        }
    }


    public void increase() { //점수 증가
        switch (difficulty) { //난이도에 따라 점수 폭 다름
            case 1:
                score++;
                break;
            case 2:
                score += 2;
                break;
            case 3:
                score += 3;
                break;
        }
        scoreLabel.setText("점수: " + score); // 점수 라벨 갱신
    }

    public void increase(int amount) { //bigFish 위한 증가 메소드
        score += amount; // 점수 증가
        scoreLabel.setText("점수: " + score); // 점수 라벨 갱신
    }

    public int getScore() { // 현재 점수 반환
        return score;
    }

    // 이지 난이도 설정
    public void easy() {
        patience = 5; // 생명 5개로 설정
        speed = 300; // 게임 속도 설정
        difficulty = 1; // 난이도 설정
        resetHearts(); // 하트 초기화
    }

    // 노멀 난이도 설정
    public void normal() {
        patience = 5; // 생명 5개로 설정
        speed = 200; // 게임 속도 설정
        difficulty = 2; // 난이도 설정
        resetHearts(); // 하트 초기화
    }

    // 하드 난이도 설정
    public void hard() {
        patience = 5; // 생명 5개로 설정
        speed = 100; // 게임 속도 설정
        difficulty = 3; // 난이도 설정
        resetHearts(); // 하트 초기화
    }

    // 남은 생명 수 반환
    public int getPatience() {
        return patience;
    }

    // 게임 속도 반환
    public int getSpeed() {
        return speed;
    }

    // 현재 난이도 반환
    public int getDifficulty() {
        return difficulty;
    }

    // 생명을 감소시키는 메서드
    public void decreasePatience() {
        if (patience > 0) { // 생명이 남아있다면
            patience--;
            removeHeart(); // 하트 제거
        } else if (patience <= 0) { // 생명이 모두 소진되면
            GamePanel gamePanel = findGamePanel(); // GamePanel을 찾아서
            if (gamePanel != null) {
                gamePanel.checkGameOver(); // 게임 오버 상태 확인
            }
        }
    }

    // 생명을 증가시키는 메서드
    public void increasePatience() {
        if (patience < 5) { // 최대 생명이 5개 이하일 경우만
            patience++;
            addHeart(); // 하트 추가
        }
    }

    // 하트를 초기화하는 메서드
    private void resetHearts() {
        for (JLabel heart : hearts) { // 기존 하트 제거
            remove(heart);
        }
        hearts.clear(); // 리스트 초기화

        for (int i = 0; i < patience; i++) { // 현재 생명 수만큼
            JLabel heartLabel = new JLabel(heart); //하트 라벨 생성
            heartLabel.setBounds(50 + (i * 100), 250, 90, 90); //위치와 크기 설정
            hearts.add(heartLabel); //벡터에 추가
            add(heartLabel); //패널에 추가
        }

        revalidate(); // 컴포넌트 갱신
        repaint(); // 화면 다시 그리기
    }

    // 하트를 제거하는 메서드
    private void removeHeart() {
        if (!hearts.isEmpty()) { // 리스트에 하트가 있을 경우
            JLabel heartToRemove = hearts.get(hearts.size() - 1); // 마지막 하트 선택
            hearts.remove(heartToRemove); // 리스트에서 제거
            remove(heartToRemove); // 패널에서 제거
            revalidate(); //컴포넌트 갱신
            repaint(); //다시 그리기
        }
    }

    // 하트를 추가하는 메서드
    private void addHeart() {
        JLabel newHeart = new JLabel(heart); // 새 하트 라벨 생성
        newHeart.setBounds(50 + (hearts.size() * 100), 250, 90, 90); // 위치와 크기 설정
        hearts.add(newHeart); // 리스트에 추가
        add(newHeart); // 패널에 추가
        revalidate();
        repaint();
    }

    // 부모 컴포넌트를 탐색하여 GamePanel을 반환하는 메서드
    private GamePanel findGamePanel() {
        Container parent = getParent(); // 현재 패널의 부모 컨테이너 탐색
        while (parent != null) {
            if (parent instanceof GamePanel) { // 부모 중 GamePanel을 찾으면 반환
                return (GamePanel) parent;
            }
            parent = parent.getParent(); // 상위 부모로 이동
        }
        return null; // GamePanel을 찾지 못하면 null 반환
    }
}
