/**
 * 게임 전역 상수 관리 클래스
 * 하드코딩된 값들을 한 곳에서 관리하여 유지보수성 향상
 */
public class GameConstants {

    // 게임 화면 크기
    public static final int GAME_WIDTH = 1520;
    public static final int GAME_HEIGHT = 1000;

    // 게임 속도 (난이도별)
    public static final int SPEED_EASY = 300;
    public static final int SPEED_NORMAL = 200;
    public static final int SPEED_HARD = 100;

    // 난이도 레벨
    public static final int DIFFICULTY_EASY = 1;
    public static final int DIFFICULTY_NORMAL = 2;
    public static final int DIFFICULTY_HARD = 3;

    // 생명 관련
    public static final int MAX_PATIENCE = 5;
    public static final int INITIAL_PATIENCE = 5;

    // 점수 관련
    public static final int SCORE_MULTIPLIER_EASY = 1;
    public static final int SCORE_MULTIPLIER_NORMAL = 2;
    public static final int SCORE_MULTIPLIER_HARD = 3;
    public static final int SCORE_BIG_FISH = 5;

    // 물고기 낙하 관련
    public static final int FALLING_SPEED = 10; // 픽셀 단위 낙하 속도
    public static final int BOTTOM_THRESHOLD = 250; // 바닥 판정 기준
    public static final int ITEM_GENERATION_INTERVAL = 800; // 아이템 생성 간격 (ms)

    // 특수 아이템 효과
    public static final int TIME_STOP_DURATION = 2000; // 타임스탑 지속 시간 (ms)

    // 아이콘 크기
    public static final int ICON_SIZE = 40;
    public static final int LABEL_WIDTH = 80;
    public static final int LABEL_HEIGHT = 60;

    // 아이템 생성 확률 (Easy)
    public static final double EASY_NORMAL_PROB = 0.89;
    public static final double EASY_BIG_FISH_PROB = 0.94;
    public static final double EASY_HEART_PROB = 0.99;

    // 아이템 생성 확률 (Normal)
    public static final double NORMAL_NORMAL_PROB = 0.77;
    public static final double NORMAL_BIG_FISH_PROB = 0.87;
    public static final double NORMAL_HEART_PROB = 0.97;

    // 아이템 생성 확률 (Hard)
    public static final double HARD_NORMAL_PROB = 0.65;
    public static final double HARD_BIG_FISH_PROB = 0.85;
    public static final double HARD_HEART_PROB = 0.95;

    // 순위 관련
    public static final int MAX_RANK_DISPLAY = 10;

    // 파일 경로
    public static final String WORDS_FILE = "words.txt";
    public static final String RANK_FILE = "rank.txt";

    // 리소스 파일 경로
    public static final String BGM_LOBBY = "lobbyBgm.wav";
    public static final String BGM_EASY = "easyBgm.wav";
    public static final String BGM_NORMAL = "normalBgm.wav";
    public static final String BGM_HARD = "hardBgm.wav";
    public static final String BGM_COIN = "coinBgm.wav";
}
