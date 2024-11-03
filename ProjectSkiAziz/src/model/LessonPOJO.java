package model;

import java.io.Serializable;
import java.util.Objects;

public class LessonPOJO implements Serializable {
    private int minBookings;
    private int maxBookings;
    
	public LessonPOJO(int minBookings, int maxBookings) {
		super();
		this.minBookings = minBookings;
		this.maxBookings = maxBookings;
	}

	public int getMinBookings() {
		return minBookings;
	}

	public void setMinBookings(int minBookings) {
		this.minBookings = minBookings;
	}

	public int getMaxBookings() {
		return maxBookings;
	}

	public void setMaxBookings(int maxBookings) {
		this.maxBookings = maxBookings;
	}

	@Override
	public String toString() {
		return "LessonPOJO [minBookings=" + minBookings + ", maxBookings=" + maxBookings + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(maxBookings, minBookings);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LessonPOJO other = (LessonPOJO) obj;
		return maxBookings == other.maxBookings && minBookings == other.minBookings;
	}
    
    
}
