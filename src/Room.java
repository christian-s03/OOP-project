import java.util.HashSet;
import java.util.Set;

public class Room extends Resource {
    private int seats;
    private Set<String> equipment;

    public Room(String name, Money customHourlyRate, int seats,
                Set<String> equipment) {
        super(name, customHourlyRate);
        this.seats = seats;
        this.equipment = equipment != null ? equipment : new HashSet<>();
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public Set<String> getEquipment() {
        return equipment;
    }

    public void setEquipment(Set<String> equipment) {
        this.equipment = equipment;
    }

    @Override
    protected Money baseRatePerHour() {
        return Money.of(25.50);
    }

    @Override
    public String describe() {
        return "Room: " + getName() + " (seats: " + seats + ", equipment: " + equipment + ")";
    }
}
