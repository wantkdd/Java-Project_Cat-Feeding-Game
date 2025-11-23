import javax.swing.*;

public class GenerateThread extends Thread { // 라벨 생성 쓰레드
    private GameGroundPanel ground; // 게임 패널
    private ScorePanel scorePanel; // 점수 패널
    private TextSource textSource; // 단어 제어
    private CatProfile catProfile; // 고양이 프로필 패널
    private boolean runFlag = true; // 쓰레드 실행 플래그
    private volatile boolean isPaused = false; // 일시 정지 상태 플래그

    public GenerateThread(GameGroundPanel ground, ScorePanel scorePanel, TextSource textSource, CatProfile catProfile) {
        this.ground = ground; //게임 그라운드 초기화
        this.scorePanel = scorePanel; //점수 패널 초기화
        this.textSource = textSource; //단어 제어 객체 초기화
        this.catProfile = catProfile; //고양이 프로필 초기화
    }

    @Override
    public void run() { // 쓰레드 실행
        try {
            while (true) { // 무한 루프
                checkStop(); // 정지 상태 확인
                synchronized (this) {
                    while (isPaused) { // 일시 정지 상태일 때
                        wait(); // 대기
                    }
                }

                JLabel label = generateRandomLabel(); //라벨 생성
                if (label != null) {
                    label.setLocation((int) (Math.random() * (ground.getWidth() - label.getWidth())), 0); //위치 설정
                    ground.addFallingLabel(label); // 패널에 라벨 추가

                    FallingThread fallingThread = new FallingThread(label, ground, scorePanel, catProfile); //FallingThread 생성
                    ground.addFallingThread(fallingThread); //게임 그라운드에 쓰레드 추가
                    fallingThread.start(); //쓰레드 시작
                }

                Thread.sleep(GameConstants.ITEM_GENERATION_INTERVAL); // 설정된 간격마다 라벨 생성
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 인터럽트 상태 설정
        }
    }

    private JLabel generateRandomLabel() { //랜덤 라벨 생성
        double random = Math.random(); // 랜덤 확률 생성

        switch (scorePanel.getDifficulty()) { // 난이도에 따라 확률 결정
            case GameConstants.DIFFICULTY_EASY:
                if (random < GameConstants.EASY_NORMAL_PROB) {
                    return createLabelWithName(textSource.createFishLabel(), "normal");
                } else if (random < GameConstants.EASY_BIG_FISH_PROB) {
                    return createLabelWithName(textSource.createScoreItemLabel(), "bigFish");
                } else if (random < GameConstants.EASY_HEART_PROB) {
                    return createLabelWithName(textSource.createHeartItemLabel(), "heart");
                } else {
                    return createLabelWithName(textSource.createTimeStopItemLabel(), "timeStop");
                }

            case GameConstants.DIFFICULTY_NORMAL:
                if (random < GameConstants.NORMAL_NORMAL_PROB) {
                    return createLabelWithName(textSource.createFishLabel(), "normal");
                } else if (random < GameConstants.NORMAL_BIG_FISH_PROB) {
                    return createLabelWithName(textSource.createScoreItemLabel(), "bigFish");
                } else if (random < GameConstants.NORMAL_HEART_PROB) {
                    return createLabelWithName(textSource.createHeartItemLabel(), "heart");
                } else {
                    return createLabelWithName(textSource.createTimeStopItemLabel(), "timeStop");
                }

            case GameConstants.DIFFICULTY_HARD:
                if (random < GameConstants.HARD_NORMAL_PROB) {
                    return createLabelWithName(textSource.createFishLabel(), "normal");
                } else if (random < GameConstants.HARD_BIG_FISH_PROB) {
                    return createLabelWithName(textSource.createScoreItemLabel(), "bigFish");
                } else if (random < GameConstants.HARD_HEART_PROB) {
                    return createLabelWithName(textSource.createHeartItemLabel(), "heart");
                } else {
                    return createLabelWithName(textSource.createTimeStopItemLabel(), "timeStop");
                }
        }
        return null; // 라벨 생성 실패 시 null 반환
    }

    // 라벨에 이름을 설정하여 반환하는 헬퍼 메서드
    private JLabel createLabelWithName(JLabel label, String name) {
        label.setName(name);
        return label;
    }

    // 쓰레드 중지
    synchronized public void stopRunning() {
        runFlag = false; // 실행 플래그를 false로 설정
    }

    // 중지 상태를 확인
    synchronized public void checkStop() {
        if (!runFlag) { // 중지 상태라면
            try {
                this.wait(); // 대기 상태로 들어감
            } catch (InterruptedException e) {
                return; // 예외 발생 시 종료
            }
        }
    }

    // 쓰레드 일시 정지
    public synchronized void freeze() {
        isPaused = true; // 정지 상태 설정
    }

    // 쓰레드 재개
    public synchronized void resumeThread() {
        isPaused = false; // 정지 상태 해제
        notify(); // 대기 중인 쓰레드를 깨움
    }
}
