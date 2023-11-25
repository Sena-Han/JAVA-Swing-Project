package inside;

import java.util.List;
import javax.swing.ImageIcon;
import inside.Platform;

public class VavaFall {
	private static Platform platform;

    public VavaFall(Platform platform) {
    	VavaFall.platform = platform;
    }

    public static void fall(Vava vava, boolean escKeyOn, boolean downKeyOn, List<Platform> platformList) {
        

        new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    int foot = vava.getY() + vava.getHeight();// 캐릭터 발 위치 재스캔

                    if (
                        !escKeyOn &&
                        foot <  platform.getPlatformHeight() &&
                        !vava.isJump() &&
                        !vava.isFall()
                    ) {
                        vava.setFall(true);// 떨어지는 중으로 전환

                        if (vava.getCountJump() == 2) {
                            // 이미지 아이콘에 대한 리소스 경로를 정확히 설정, 나중에
                            vava.setImage(new ImageIcon("fallImage.png").getImage());
                        }

                       long t1 = System.currentTimeMillis();
                        long t2 = 0;
                        int set = 1;

                        while (foot < platform.getPlatformHeight()) { // 발이 발판에 닿기 전까지 반복
                            t2 = System.currentTimeMillis() - t1; // 지금 시간에서 t1을 뺀다

                            int fallY = set + (int) ((t2) / 40);// 낙하량을 늘린다.

                            foot = vava.getY() + vava.getHeight();// 캐릭터 발 위치 재스캔

                            if (foot + fallY >= platform.getPlatformHeight()) { // 발바닥+낙하량 위치가 발판보다 낮다면 낙하량을 조정한다.
                                fallY = platform.getPlatformHeight() - foot;
                            }

                            vava.setY(vava.getY() + fallY);// Y좌표에 낙하량을 더한다

                            if (vava.isJump()) {// 떨어지다가 점프를 하면 낙하중지
                                break;
                            }

                            if (escKeyOn) {
                                long tempT1 = System.currentTimeMillis();
                                long tempT2 = 0;
                                while (escKeyOn) {
                                    tempT2 = System.currentTimeMillis() - tempT1;
                                    try {
                                        Thread.sleep(10);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                t1 = t1 + tempT2;
                            }

                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        vava.setFall(false);

                        if (!vava.isJump()) {// 발이 땅에 닿고 점프 중이 아닐 때 더블점프 카운트를 0으로 변경
                            vava.setCountJump(0);
                        }
                    }

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}

