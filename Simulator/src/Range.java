
import java.util.Random;

public class Range {
    public int lower;
    public int upper;
    private Random random;

    public Range(int lower, int upper) {
        this.lower = lower;
        this.upper = upper;
        this.random = new Random();
    }

    public int generateRandomInRange() {
        return random.nextInt((this.upper - this.lower) + 1) + this.lower;
    }
}
