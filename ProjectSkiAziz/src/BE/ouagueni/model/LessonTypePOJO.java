package BE.ouagueni.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import dao.LessonTypeDAO;

public class LessonTypePOJO implements Serializable {
    private String level;
    private double price;
    private int id;
    
	public LessonTypePOJO(String level, double price) {
		super();
		this.level = level;
		this.price = price;
	}

	public int getId() {
	    return id;
	}

	public void setId(int id) {
	    this.id = id;
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
	public static List<LessonTypePOJO> getAllLessonTypes() {
        LessonTypeDAO lessonTypeDAO = new LessonTypeDAO();
        return lessonTypeDAO.getAllLessonTypes(); 
    }
}
