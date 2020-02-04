package types;

public enum Priority {
    RED ("Red"),
    YELLOW ("Yellow"),
    GREEN ("Green"),
    BLACK ("Black");

    private final String name;

    private Priority(String s) {
        name = s;
    }

    public String toString() {
        return name;
    }
}
