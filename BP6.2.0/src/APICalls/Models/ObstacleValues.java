package APICalls.Models;

public class ObstacleValues {

    private int OSX;
    private int OSY;

    public ObstacleValues(int OSX, int OSY) {
        this.OSX = OSX;
        this.OSY = OSY;
    }

    public int getOSX() {
        return OSX;
    }

    public void setOSX(int OSX) {
        this.OSX = OSX;
    }

    public int getOSY() {
        return OSY;
    }

    public void setOSY(int OSY) {
        this.OSY = OSY;
    }

    @Override
    public String toString() {
        return "ObstacleValues{" +
                "OSX=" + OSX +
                ", OSY=" + OSY +
                '}';
    }
}
