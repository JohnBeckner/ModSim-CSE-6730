
public enum Priority {
    YELLOW ("Yellow", 0.76),
    RED ("Red", 0.12),
    GREEN ("Green", 0.11),
    BLACK ("Black", 0.1);

    private final String name;
    private final double probability;

    private Priority(String s, double prob) {
        name = s;
        probability = prob;
    }

    public String toString() {
        return name;
    }

    public double getProb() {
        return probability;
    }
}
