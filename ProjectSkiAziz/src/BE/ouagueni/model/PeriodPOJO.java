package BE.ouagueni.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import dao.PeriodDAO;
import dao.SkierDAO;
import singleton.EcoleConnection;

public class PeriodPOJO implements Serializable {
	private int id;
    private Date startDate;
    private Date endDate;
    private boolean isVacation;
    
	public PeriodPOJO(int id,Date startDate, Date endDate, boolean isVacation) {
		super();
		this.id=id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.isVacation = isVacation;
	}

	public int getid()
	{
		return id;
	}
	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public boolean isVacation() {
		return isVacation;
	}

	public void setVacation(boolean isVacation) {
		this.isVacation = isVacation;
	}

	@Override
	public String toString() {
		return "PeriodPOJO [startDate=" + startDate + ", endDate=" + endDate + ", isVacation=" + isVacation + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(endDate, isVacation, startDate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PeriodPOJO other = (PeriodPOJO) obj;
		return Objects.equals(endDate, other.endDate) && isVacation == other.isVacation
				&& Objects.equals(startDate, other.startDate);
	}
    public static List<PeriodPOJO> getAllPeriodNotInBooking() {
        PeriodDAO PeriodDAO = new PeriodDAO(EcoleConnection.getInstance().getConnect());
        return PeriodDAO.getAllPeriodNotInBooking(); 
    }
    public static List<PeriodPOJO> getAllPeriod() {
        PeriodDAO PeriodDAO = new PeriodDAO(EcoleConnection.getInstance().getConnect());
        return PeriodDAO.getAllPeriod(); 
    }
    
}

