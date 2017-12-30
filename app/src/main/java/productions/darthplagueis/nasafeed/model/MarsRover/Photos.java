package productions.darthplagueis.nasafeed.model.MarsRover;

/**
 * Created by oleg on 12/24/17.
 */

public class Photos {
    private int id;
    private int sol;
    private RoverCamera camera;
    private String img_src;
    private String earth_date;
    private Rover rover;

    public int getId() {
        return id;
    }

    public int getSol() {
        return sol;
    }

    public RoverCamera getCamera() {
        return camera;
    }

    public String getImg_src() {
        return img_src;
    }

    public String getEarth_date() {
        return earth_date;
    }

    public Rover getRover() {
        return rover;
    }
}
