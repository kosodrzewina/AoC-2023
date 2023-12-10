import java.util.ArrayList;
import java.util.List;

public class PartNumber {
    private int number;
    private List<Gear> gears = new ArrayList<>();

    public PartNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public List<Gear> getGears() {
        return gears;
    }

    public void addGear(Gear gear) {
        gears.add(gear);
    }
}
