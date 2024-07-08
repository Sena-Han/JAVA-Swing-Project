package inside;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class BGM {
    private Clip clip;

    public BGM(String musicFilePath, boolean isLoop) {
        try {
           // 오디오 파일 로드
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(musicFilePath));
         // Clip 객체 생성
            clip = AudioSystem.getClip();
            clip.open(ais);
         // 볼륨 제어 설정 (볼륨 감소)
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-30.0f); //볼륨 줄이기
         // 음악 재생 시작
            clip.start();
         // 루프가 true이면 음악을 계속 반복 재생
            if (isLoop)
                clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // BGM 중지 메서드
    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }
}