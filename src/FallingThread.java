import javax.swing.*;

/**
 * 물고기 라벨의 낙하 애니메이션을 담당하는 스레드
 * 설정된 속도로 라벨을 아래로 이동시키고, 바닥 도달 시 처리
 */
public class FallingThread extends Thread {
    private JLabel fallingLabel; // 떨어질 라벨
    private GameGroundPanel ground; // 게임 그라운드 패널
    private ScorePanel scorePanel; // 점수 패널
    private CatProfile catProfile; // 고양이 프로필
    private volatile boolean isPaused = false; // 쓰레드 정지 상태 플래그


    public FallingThread(JLabel fallingLabel, GameGroundPanel ground, ScorePanel scorePanel, CatProfile catProfile) {
        this.fallingLabel = fallingLabel; //떨어질 라벨 초기화
        this.ground = ground; //게임 그라운드 초기화
        this.scorePanel = scorePanel; //점수 패널 초기화
        this.catProfile = catProfile; //고양이 프로필 초기화
    }

    @Override
    public void run() { // 쓰레드 실행
        try {
            while (true) { // 무한 루프
                synchronized (this) {
                    while (isPaused) { // 정지 상태라면
                        wait(); // 쓰레드 대기
                    }
                }
                // 라벨을 설정된 속도로 아래로 이동
                fallingLabel.setLocation(fallingLabel.getX(), fallingLabel.getY() + GameConstants.FALLING_SPEED);

                // 라벨이 바닥에 가까워졌다면
                if (fallingLabel.getY() > ground.getHeight() - GameConstants.BOTTOM_THRESHOLD) {
                    if (ground.containsLabel(fallingLabel)) { // 라벨이 그라운드 패널에 존재하는 경우
                        String labelType = fallingLabel.getName(); // 라벨 타입 가져오기
                        switch (labelType) { // 라벨 타입이
                            case "timeStop": // 타임스탑 아이템이라면
                                // 타임스탑 아이템 로직 (추후 구현)
                                break;
                            case "bigFish": // 점수 아이템이라면
                                break;
                            case "heart": // 하트 아이템이라면
                                break;
                            case "normal": // 일반 단어
                            default: // 그 외의 경우
                                catProfile.setHungryCat(); // 배고픈 고양이로 설정
                                scorePanel.decreasePatience(); // 생명감소
                                break;
                        }
                        ground.removeFallingLabel(fallingLabel);// 라벨을 그라운드에서 제거
                        sleep(30); // 30ms 대기
                    }
                }

                if (fallingLabel.getY() > ground.getHeight()) {
                    fallingLabel.setLocation(fallingLabel.getX(), -fallingLabel.getHeight()); // 라벨이 화면 아래로 벗어나면 다시 위로 이동
                }
                sleep(scorePanel.getSpeed()); // 난이도에 따라 속도 설정
            }
        } catch (InterruptedException e) { // 인터럽트 예외 발생 시
            Thread.currentThread().interrupt(); // 현재 쓰레드의 인터럽트 상태를 설정
        }
    }

    // 쓰레드 일시 정지
    public synchronized void freeze() {
        isPaused = true; // 정지 상태로 설정
    }

    // 쓰레드 재개
    public synchronized void resumeThread() {
        isPaused = false; // 정지 상태 해제
        notify(); // 대기 중인 쓰레드를 깨움
    }
}
