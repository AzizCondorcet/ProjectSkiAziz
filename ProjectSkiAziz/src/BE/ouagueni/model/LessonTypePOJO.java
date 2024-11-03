package BE.ouagueni.model;

import java.io.Serializable;
import java.util.Objects;

public class LessonTypePOJO implements Serializable {
    private String level;
    private double price;
    
	public LessonTypePOJO(String level, double price) {
		super();
		this.level = level;
		this.price = price;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "LessonTypePOJO [level=" + level + ", price=" + price + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(level, price);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LessonTypePOJO other = (LessonTypePOJO) obj;
		return Objects.equals(level, other.level)
				&& Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price);
	}
}
