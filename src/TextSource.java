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

    // 공통 라벨 생성 메서드
    private JLabel createLabel(ImageIcon icon, String name) {
        String word = getWord(); // 랜덤 단어 가져오기
        JLabel label = new JLabel(word, icon, JLabel.CENTER); // 단어와 아이콘 결합
        label.setHorizontalTextPosition(JLabel.CENTER); // 텍스트를 중앙에 배치
        label.setVerticalTextPosition(JLabel.BOTTOM); // 텍스트를 아이콘 아래에 배치
        label.setFont(new Font("Malgun Gothic", Font.BOLD, 14)); // 텍스트 스타일 설정
        label.setForeground(Color.BLACK); // 텍스트 색상 설정
        label.setSize(80, 60); // 라벨의 크기 설정
        if (name != null) {
            label.setName(name); // 라벨 이름 설정
        }
        return label; // 라벨 반환
    }

    // 물고기 라벨 생성
    public JLabel createFishLabel() {
        return createLabel(fishIcon, null);
    }

    // 큰 물고기 아이템 라벨 생성
    public JLabel createScoreItemLabel() {
        return createLabel(bigFishIcon, "bigFish");
    }

    // 하트 아이템 라벨 생성
    public JLabel createHeartItemLabel() {
        return createLabel(heartIcon, "heart");
    }

    // 타임스탑 아이템 라벨 생성
    public JLabel createTimeStopItemLabel() {
        return createLabel(timeStopIcon, "timeStop");
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
