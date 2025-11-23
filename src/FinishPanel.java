import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class FinishPanel extends JPanel {
    private ScorePanel scorePanel = null; // 점수 패널 객체
    private TextSource textSource = null; // 텍스트 소스 객체
    private GameFrame gameFrame = null; // 게임 프레임 객체
    private ImageIcon finishBackground = new ImageIcon("introBackground.jpg"); // 배경 이미지 아이콘
    private Image backgroundImage = finishBackground.getImage(); // 아이콘 이미지 객체로 변환

    private JTextField nameField = new JTextField(20); // 이름 입력 필드 생성
    private ImageIcon nameIcon = new ImageIcon("nameInput.png"); // 이름 입력 아이콘
    private JLabel name = new JLabel(nameIcon); // 이름 입력 라벨 생성

    private ImageIcon restartButton = new ImageIcon("restartButton.png"); // 재시작 버튼 아이콘
    private JButton restart = new JButton(restartButton); // 재시작 버튼 생성

    public FinishPanel(GameFrame gameFrame, ScorePanel scorePanel) {
        this.gameFrame = gameFrame; // 게임 프레임 초기화
        this.scorePanel = scorePanel; // 점수 패널 초기화

        setSize(1520, 1080); // 패널 크기 설정
        setLayout(null); // 레이아웃 null로 설정

        name.setBounds(930, 200, 300, 60); // 위치와 크기 설정
        name.setOpaque(true); // 배경 불투명으로 설정
        add(name); // 패널에 추가

        nameField.setBounds(930, 260, 300, 50); // 위치와 크기 설정
        nameField.setFont(new Font("Malgun Gothic", Font.PLAIN, 30)); // 폰트 설정
        nameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField tf = (JTextField) e.getSource(); // 이벤트 소스 가져오기
                String name = tf.getText(); // 입력된 이름 가져오기
                rank(name, scorePanel.getScore()); // 랭킹 갱신
                printRank(); // 랭킹 출력
                tf.setText(""); // 입력 필드 초기화
            }
        });
        add(nameField); // 패널에 추가

        // 재시작 버튼 설정
        restart.setBounds(950, 460, 300, 100); // 위치와 크기 설정
        restart.setBorderPainted(false); // 버튼 테두리 제거
        restart.setContentAreaFilled(false); // 버튼 내용 채우기 제거
        restart.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gameFrame.showPanel("StartPanel"); // 시작 화면으로 이동
            }
        });
        add(restart); // 패널에 추가

        printRank(); // 랭킹 출력

        setVisible(true); // 패널 표시
    }


    public void rank(String name, int score) { //랭킹 최신화
        try {
            Vector<String> ranks = readRanks(); // 기존 랭킹 불러오기
            String inputRank = name + "의 급식 성공 횟수 : " + score; // 새 랭킹
            ranks.add(inputRank); // 벡터에 추가

            // 점수를 기준으로 내림차순 정렬
            Collections.sort(ranks, Comparator.comparingInt(s -> Integer.parseInt(s.split("의 급식 성공 횟수 : ")[1])));
            Collections.reverse(ranks);

            writeRanks(ranks); // 정렬된 랭킹 파일에 저장
        } catch (IOException e) {
            throw new RuntimeException(e); // 예외 발생 시 런타임 예외 처리
        }
    }

    private static Vector<String> readRanks() throws IOException { //랭킹 읽어오기
        Vector<String> ranks = new Vector<>(); // 랭킹 저장 벡터 생성
        try (BufferedReader br = new BufferedReader(new FileReader("rank.txt"))) { // 파일 읽기
            String line;
            while ((line = br.readLine()) != null) { // 파일 끝까지 읽기
                ranks.add(line); // 벡터에 한 줄씩 추가
            }
        }
        return ranks; // 랭킹 반환
    }

    private static void writeRanks(Vector<String> ranks) throws IOException { //랭킹 작성
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("rank.txt"))) { // 파일 쓰기
            for (String record : ranks) { // 벡터에 저장된 랭킹 정보 반복
                bw.write(record); // 파일에 기록
                bw.newLine(); // 줄바꿈 추가
            }
        }
    }

    private void printRank() { //랭킹 출력
        try {
            Component[] components = getComponents();
            for (Component component : components) {
                if (component instanceof JLabel && component != name) { // 이름 입력 라벨은 제외
                    remove(component); //출력 되어있던 랭킹 제거
                }
            }
            Vector<String> records = readRanks(); // 랭킹 정보 읽기

            JLabel[] rankLabels = new JLabel[records.size()]; // 출력할 라벨 배열 생성

            for (int i = 0; i < records.size() && i < 10; i++) { //상위 10명의
                String record = records.get(i); // 랭킹 정보 가져오기
                rankLabels[i] = new JLabel((i + 1) + "등 : " + record + "회"); // 라벨 생성
                rankLabels[i].setSize(1000, 50); // 크기 설정
                rankLabels[i].setLocation(50, 20 + i * 55); // 위치 설정
                rankLabels[i].setForeground(Color.black); // 글자 색 설정
                rankLabels[i].setFont(new Font("Malgun Gothic", Font.BOLD, 35)); // 폰트 설정
                add(rankLabels[i]); // 패널에 추가
            }

            revalidate(); // 화면 갱신
            this.repaint(); // 다시 그리기

        } catch (IOException e) {
            e.printStackTrace(); // 예외 출력
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // 기본 그리기
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null); // 배경 이미지 그리기
    }
}
