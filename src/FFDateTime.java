import java.time.LocalDateTime;
import java.time.Duration;

public final class FFDateTime implements Comparable<FFDateTime> {

    public final int year;
    public final int month;
    public final int day;
    public final int hour;
    public final int minute;

    private static final LocalDateTime EPOCH = LocalDateTime.of(2000, 1, 1, 0, 0, 0);

    public FFDateTime(int y, int m, int d, int h, int min) {
        validate(y, m, d, h, min);
        this.year = y;
        this.month = m;
        this.day = d;
        this.hour = h;
        this.minute = min;
    }

    private static void validate(int y, int m, int d, int h, int min) {
        if (m < 1 || m > 12) throw new IllegalArgumentException("Month must be 1-12");
        if (d < 1 || d > 31) throw new IllegalArgumentException("Day must be 1-31");
        if (h < 0 || h > 23) throw new IllegalArgumentException("Hour must be 0-23");
        if (min < 0 || min > 59) throw new IllegalArgumentException("Minute must be 0-59");
        try {
            java.time.LocalDateTime.of(y, m, d, h, min);
        } catch (java.time.DateTimeException e) {
            throw new IllegalArgumentException("Invalid date/time");
        }
    }

    public static FFDateTime of(int y, int m, int d, int h, int min) {
        return new FFDateTime(y, m, d, h, min);
    }

    public static FFDateTime parse(String iso) {
        try {
            java.time.LocalDateTime ldt = java.time.LocalDateTime.parse(iso,
                    java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
            return new FFDateTime(ldt.getYear(), ldt.getMonthValue(), ldt.getDayOfMonth(), ldt.getHour(), ldt.getMinute());
        } catch (java.time.format.DateTimeParseException e) {
            throw new IllegalArgumentException("Wrong date format");
        }
    }

    public int toEpochMinutes() {
        LocalDateTime ldt = LocalDateTime.of(year, month, day, hour, minute);
        return (int) Duration.between(EPOCH, ldt).toMinutes();
    }

    public FFDateTime plusMinutes(int minutes) {
        LocalDateTime ldt = LocalDateTime.of(year, month, day, hour, minute);
        ldt = ldt.plusMinutes(minutes);
        return new FFDateTime(ldt.getYear(), ldt.getMonthValue(), ldt.getDayOfMonth(), ldt.getHour(), ldt.getMinute());
    }

    public int minutesUntil(FFDateTime other) {
        LocalDateTime thisLdt = LocalDateTime.of(year, month, day, hour, minute);
        LocalDateTime otherLdt = LocalDateTime.of(other.year, other.month, other.day, other.hour, other.minute);
        return (int) Duration.between(thisLdt, otherLdt).toMinutes();
    }

    @Override
    public int compareTo(FFDateTime o) {
        return Integer.compare(this.toEpochMinutes(), o.toEpochMinutes());
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d:%02d", year, month, day, hour, minute);
    }

    public boolean isBefore(FFDateTime other) {
        return this.compareTo(other) < 0;
    }

    public boolean isAfter(FFDateTime other) {
        return this.compareTo(other) > 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof FFDateTime)) return false;
        FFDateTime other = (FFDateTime) obj;
        return this.year == other.year &&
                this.month == other.month &&
                this.day == other.day &&
                this.hour == other.hour &&
                this.minute == other.minute;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(year, month, day, hour, minute);
    }
}
