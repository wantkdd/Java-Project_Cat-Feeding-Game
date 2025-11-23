import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class BGMThread extends Thread {
    private Clip clip; // 오디오 클립 객체
    private String filePath; // 오디오 파일 경로
    private volatile boolean stopRequested = false; // BGM 중지 요청 여부

    public BGMThread(String filePath) {
        this.filePath = filePath; // 파일 경로 저장
    }

    @Override
    public void run() {
        try {
            File audioFile = new File(filePath); // 파일 객체 생성
            // 오디오 입력 스트림 생성 (오디오 파일 읽기)
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip(); // 오디오 클립 생성
            clip.open(audioInputStream); // 클립에 오디오 입력 스트림 연결
            clip.loop(Clip.LOOP_CONTINUOUSLY); // 오디오 무한 반복 재생

            // 중지 요청이 들어올 때까지 대기
            while (!stopRequested && clip.isRunning()) {
                Thread.sleep(100); // 100ms 단위로 중지 요청 확인
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
            e.printStackTrace(); // 예외 발생 시 오류 출력
        } finally {
            stopBGM(); // 재생이 끝난 후 BGM 중지
        }
    }

    public void stopBGM() {
        stopRequested = true; // 중지 요청 플래그 설정
        if (clip != null && clip.isRunning()) { // 클립이 실행 중이라면
            clip.stop(); // 오디오 중지
            clip.close(); // 클립 종료
        }
    }
}
