import java.util.ArrayList;
import java.util.List;

public class Gear {
    private int x;
    private int y;
    private List<Integer> partNumbers = new ArrayList<>();

    public Gear(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Gear(int x, int y, int partNumber) {
        this.x = x;
        this.y = y;
        partNumbers.add(partNumber);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public List<Integer> getPartNumbers() {
        return partNumbers;
    }

    public void addPartNumber(int number) {
        partNumbers.add(number);
    }

    public Integer getRatio() {
        if (!isValid()) {
            return 0;
        }
        
        return partNumbers.get(0) * partNumbers.get(1);
    }
    
    private boolean isValid() {
        return partNumbers.size() == 2;
    }

    @Override
    public String toString() {
        return getX() + ", " + getY();
    }
}
