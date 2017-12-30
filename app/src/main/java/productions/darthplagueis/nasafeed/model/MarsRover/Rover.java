package productions.darthplagueis.nasafeed.model.MarsRover;

/**
 * Created by oleg on 12/25/17.
 */

public class Rover {
    private int id;
    private String name;
    private String landing_date;
    private String launch_date;
    private String status;
    private int max_sol;
    private String max_date;
    private int total_photos;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLanding_date() {
        return landing_date;
    }

    public String getLaunch_date() {
        return launch_date;
    }

    public String getStatus() {
        return status;
    }

    public int getMax_sol() {
        return max_sol;
    }

    public String getMax_date() {
        return max_date;
    }

    public int getTotal_photos() {
        return total_photos;
    }
}
