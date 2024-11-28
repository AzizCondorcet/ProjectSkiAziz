package BE.ouagueni.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import dao.LessonTypeDAO;
import singleton.EcoleConnection;

public class LessonTypePOJO implements Serializable {
    private String level;
    private BigDecimal price;
    private int id;
    
	public LessonTypePOJO(String level, BigDecimal price) {
		super();
		this.level = level;
		this.price = price;
	}
	public LessonTypePOJO(int id,String level, BigDecimal price) {
		super();
		this.id=id;
		this.level = level;
		this.price = price;
	}
	public LessonTypePOJO() {
		super();
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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal bigDecimal) {
		this.price = bigDecimal;
	}

	@Override
	public String toString() {
		return "LessonTypePOJO [level=" + level + ", price=" + price + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(level, price);
	}

	/*@Override
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
	}*/
	public static List<LessonTypePOJO> getAllLessonTypes() {
        LessonTypeDAO lessonTypeDAO = new LessonTypeDAO();
        return lessonTypeDAO.getAllLessonTypes(); 
    }
	public static LessonTypePOJO getLessonTypeById(int LTid) {
        LessonTypeDAO lessonTypeDAO = new LessonTypeDAO(EcoleConnection.getInstance().getConnect());
        return lessonTypeDAO.getLessonTypeById(LTid); 
    }
}
