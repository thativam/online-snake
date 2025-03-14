package src;

import java.util.Random;

public class Apple {
    private int applexPos;
    private int appleyPos;
    private static Random random = new Random();

    public Apple() {
        this.applexPos = 25 + (random.nextInt(81) * 6);
        this.appleyPos = 73 + (random.nextInt(72) * 6);
    }

    public int getappleXPos() {
        return applexPos;
    }

    public int getappleYPos() {
        return appleyPos;
    }

    public String printPosition() {
        return "(" + applexPos + ", " + appleyPos + ")";
    }
}
