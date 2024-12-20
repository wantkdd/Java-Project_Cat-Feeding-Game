import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Vector;

public class TextSource {
    private Vector<String> words = new Vector<String>(); // 단어를 저장할 벡터
    Vector<String> names = new Vector<String>(); // 이름을 저장할 벡터
    private ImageIcon fishIcon = new ImageIcon("fish.png"); // 물고기 이미지 아이콘 생성
    private ImageIcon bigFishIcon = new ImageIcon("bigFish.png"); // 큰 물고기 아이템 이미지 아이콘 생성
    private ImageIcon heartIcon = new ImageIcon("heart.png"); // 하트 아이템 이미지 아이콘 생성
    private ImageIcon timeStopIcon = new ImageIcon("timeStop.png"); // 타임스탑 아이템 이미지 아이콘 생성

    public TextSource(){
        // words.txt 파일에서 단어를 읽어와 벡터에 저장
        try (BufferedReader br = new BufferedReader(new FileReader("words.txt"))) {
            String word;
            while ((word = br.readLine()) != null) { // 파일의 각 줄을 읽어서
                this.words.add(word); // words 벡터에 추가
            }
        } catch (IOException e) {
            e.printStackTrace(); // 입출력 예외 발생 시 출력
        }

        // rank.txt 파일에서 이름을 읽어와 names 벡터에 저장
        try (BufferedReader br = new BufferedReader(new FileReader("rank.txt"))) {
            String name;
            while ((name = br.readLine()) != null) { // 파일의 각 줄을 읽어서
                names.add(name); // names 벡터에 추가
            }
        } catch (IOException e){
            e.printStackTrace(); // 입출력 예외 발생 시 출력
        }

        // 이미지 아이콘의 크기를 조절 (40x40 픽셀)
        fishIcon = new ImageIcon(fishIcon.getImage().getScaledInstance(40,40,Image.SCALE_SMOOTH));
        bigFishIcon = new ImageIcon(bigFishIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
        heartIcon = new ImageIcon(heartIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
        timeStopIcon = new ImageIcon(timeStopIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
    }

    // 물고기 라벨 생성
    public JLabel createFishLabel() {
        String word = getWord(); // 랜덤 단어 가져오기
        JLabel fishLabel = new JLabel(word, fishIcon, JLabel.CENTER); // 단어와 아이콘 결합
        fishLabel.setHorizontalTextPosition(JLabel.CENTER); // 텍스트를 중앙에 배치
        fishLabel.setVerticalTextPosition(JLabel.BOTTOM); // 텍스트를 아이콘 아래에 배치
        fishLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 14)); // 텍스트 스타일 설정
        fishLabel.setForeground(Color.BLACK); // 텍스트 색상 설정
        fishLabel.setSize(80, 60); // 라벨의 크기 설정
        return fishLabel; // 라벨 반환
    }

    // 큰 물고기 아이템 라벨 생성 (위와 같음)
    public JLabel createScoreItemLabel() {
        String word = getWord();
        JLabel scoreLabel = new JLabel(word, bigFishIcon, JLabel.CENTER);
        scoreLabel.setHorizontalTextPosition(JLabel.CENTER);
        scoreLabel.setVerticalTextPosition(JLabel.BOTTOM);
        scoreLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        scoreLabel.setForeground(Color.BLACK);
        scoreLabel.setSize(80, 60);
        scoreLabel.setName("bigFish");
        return scoreLabel;
    }

    // 하트 아이템 라벨 생성 (위와 같음)
    public JLabel createHeartItemLabel() {
        String word = getWord();
        JLabel heartLabel = new JLabel(word, heartIcon, JLabel.CENTER);
        heartLabel.setHorizontalTextPosition(JLabel.CENTER);
        heartLabel.setVerticalTextPosition(JLabel.BOTTOM);
        heartLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        heartLabel.setForeground(Color.BLACK);
        heartLabel.setSize(80, 60);
        heartLabel.setName("heart");
        return heartLabel;
    }

    // 타임스탑 아이템 라벨 생성 (위와 같음)
    public JLabel createTimeStopItemLabel() {
        String word = getWord();
        JLabel timeStopLabel = new JLabel(word, timeStopIcon, JLabel.CENTER);
        timeStopLabel.setHorizontalTextPosition(JLabel.CENTER);
        timeStopLabel.setVerticalTextPosition(JLabel.BOTTOM);
        timeStopLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        timeStopLabel.setForeground(Color.BLACK);
        timeStopLabel.setSize(80, 60);
        timeStopLabel.setName("timeStop");
        return timeStopLabel;
    }


    public String getWord(){ //랜덤 단어 가져옴
        int index = (int)(Math.random()*words.size()); // 벡터 크기 내에서 랜덤 인덱스 생성
        return words.get(index); // 해당 인덱스의 단어 반환
    }


    public void addWord(String word){ //단어 텍스트파일에 추가
        if (words.contains(word)){ // 단어 중복 검사
            System.out.println("중복 단어 : "+word); // 중복 단어 메시지 출력
            return; // 메서드 종료
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("words.txt",true))) {
            bw.write(word); // 단어를 파일에 쓰기
            bw.newLine(); // 줄 바꿈
            words.add(word); // 벡터에 단어 추가
        }
        catch (IOException e) {
            e.printStackTrace(); // 예외 발생 시 출력
        }
    }
}
