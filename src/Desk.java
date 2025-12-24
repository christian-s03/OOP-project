public class Desk extends Resource {
    public enum DeskType {
        HOT,
        FIXED;
    }

    private DeskType type;

    public Desk(String name, Money customHourlyRate, DeskType type) {
        super(name, customHourlyRate);
        this.type = type;
    }

    public DeskType getType() {
        return type;
    }

    public void setType(DeskType type) {
        this.type = type;
    }

    @Override
    protected Money baseRatePerHour() {
        if (type == DeskType.HOT) {
            return Money.of(15.0);
        } else {
            return Money.of(25.0);
        }
    }
    @Override
    public String describe() {
        return "Desk: " + getName() + " (type: " + type + ")";
    }
}

