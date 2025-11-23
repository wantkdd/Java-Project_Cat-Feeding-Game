import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 게임에서 사용되는 단어와 이미지 리소스를 관리하는 클래스
 * 이미지 캐싱을 통해 메모리 사용을 최적화하고,
 * 파일 로딩 시 에러 처리를 강화
 */
public class TextSource {
    private static final Logger LOGGER = Logger.getLogger(TextSource.class.getName());

    private ArrayList<String> words = new ArrayList<>(); // 단어를 저장할 리스트
    ArrayList<String> names = new ArrayList<>(); // 이름을 저장할 리스트

    // 이미지 아이콘 캐싱 (한 번만 로드하여 재사용)
    private final ImageIcon fishIcon;
    private final ImageIcon bigFishIcon;
    private final ImageIcon heartIcon;
    private final ImageIcon timeStopIcon;

    /**
     * TextSource 생성자
     * 단어 파일과 순위 파일을 로드하고, 이미지 리소스를 캐싱
     */
    public TextSource() {
        // 이미지 리소스 로드 및 크기 조정 (캐싱)
        fishIcon = loadAndScaleIcon("fish.png", GameConstants.ICON_SIZE, GameConstants.ICON_SIZE);
        bigFishIcon = loadAndScaleIcon("bigFish.png", GameConstants.ICON_SIZE, GameConstants.ICON_SIZE);
        heartIcon = loadAndScaleIcon("heart.png", GameConstants.ICON_SIZE, GameConstants.ICON_SIZE);
        timeStopIcon = loadAndScaleIcon("timeStop.png", GameConstants.ICON_SIZE, GameConstants.ICON_SIZE);

        // words.txt 파일에서 단어를 읽어와 리스트에 저장
        loadWordsFromFile();

        // rank.txt 파일에서 이름을 읽어와 리스트에 저장
        loadRankFromFile();
    }

    /**
     * 이미지 파일을 로드하고 지정된 크기로 스케일링
     * @param path 이미지 파일 경로
     * @param width 목표 너비
     * @param height 목표 높이
     * @return 스케일링된 ImageIcon
     */
    private ImageIcon loadAndScaleIcon(String path, int width, int height) {
        try {
            File imageFile = new File(path);
            if (!imageFile.exists()) {
                LOGGER.warning("이미지 파일을 찾을 수 없습니다: " + path);
                // 기본 빈 아이콘 반환
                return new ImageIcon();
            }
            ImageIcon icon = new ImageIcon(path);
            Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "이미지 로드 실패: " + path, e);
            return new ImageIcon();
        }
    }

    /**
     * words.txt 파일에서 단어 목록을 로드
     */
    private void loadWordsFromFile() {
        File wordsFile = new File(GameConstants.WORDS_FILE);
        if (!wordsFile.exists()) {
            LOGGER.warning("단어 파일이 존재하지 않습니다. 기본 단어로 초기화합니다.");
            // 기본 단어 추가
            words.add("고양이");
            words.add("물고기");
            words.add("게임");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(GameConstants.WORDS_FILE))) {
            String word;
            while ((word = br.readLine()) != null) {
                if (!word.trim().isEmpty()) { // 빈 줄 무시
                    this.words.add(word.trim());
                }
            }
            LOGGER.info("단어 " + words.size() + "개 로드 완료");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "단어 파일 로드 실패", e);
            // 기본 단어 추가
            words.add("고양이");
            words.add("물고기");
        }
    }

    /**
     * rank.txt 파일에서 순위 데이터를 로드
     */
    private void loadRankFromFile() {
        File rankFile = new File(GameConstants.RANK_FILE);
        if (!rankFile.exists()) {
            LOGGER.info("순위 파일이 존재하지 않습니다. 새로 생성됩니다.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(GameConstants.RANK_FILE))) {
            String name;
            while ((name = br.readLine()) != null) {
                if (!name.trim().isEmpty()) {
                    names.add(name.trim());
                }
            }
            LOGGER.info("순위 데이터 " + names.size() + "개 로드 완료");
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "순위 파일 로드 실패", e);
        }
    }

    /**
     * 공통 라벨 생성 메서드 (캐싱된 이미지 사용)
     * @param icon 사용할 ImageIcon
     * @param name 라벨의 이름 (아이템 타입)
     * @return 생성된 JLabel
     */
    private JLabel createLabel(ImageIcon icon, String name) {
        String word = getWord(); // 랜덤 단어 가져오기
        JLabel label = new JLabel(word, icon, JLabel.CENTER);
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.BOTTOM);
        label.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        label.setForeground(Color.BLACK);
        label.setSize(GameConstants.LABEL_WIDTH, GameConstants.LABEL_HEIGHT);
        if (name != null) {
            label.setName(name);
        }
        return label;
    }

    /**
     * 일반 물고기 라벨 생성
     * @return 물고기 라벨
     */
    public JLabel createFishLabel() {
        return createLabel(fishIcon, null);
    }

    /**
     * 큰 물고기 아이템 라벨 생성 (보너스 점수)
     * @return 큰 물고기 라벨
     */
    public JLabel createScoreItemLabel() {
        return createLabel(bigFishIcon, "bigFish");
    }

    /**
     * 하트 아이템 라벨 생성 (생명 회복)
     * @return 하트 라벨
     */
    public JLabel createHeartItemLabel() {
        return createLabel(heartIcon, "heart");
    }

    /**
     * 타임스탑 아이템 라벨 생성 (게임 일시정지)
     * @return 타임스탑 라벨
     */
    public JLabel createTimeStopItemLabel() {
        return createLabel(timeStopIcon, "timeStop");
    }

    /**
     * 랜덤 단어를 가져옴
     * @return 랜덤하게 선택된 단어
     */
    public String getWord() {
        if (words.isEmpty()) {
            return "단어없음"; // 단어가 없을 경우 기본값
        }
        int index = (int)(Math.random() * words.size());
        return words.get(index);
    }

    /**
     * 새로운 단어를 파일에 추가
     * @param word 추가할 단어
     */
    public void addWord(String word) {
        if (word == null || word.trim().isEmpty()) {
            LOGGER.warning("빈 단어는 추가할 수 없습니다.");
            return;
        }

        word = word.trim();
        if (words.contains(word)) {
            LOGGER.info("중복 단어: " + word);
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(GameConstants.WORDS_FILE, true))) {
            bw.write(word);
            bw.newLine();
            words.add(word);
            LOGGER.info("단어 추가 완료: " + word);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "단어 추가 실패: " + word, e);
        }
    }
}
