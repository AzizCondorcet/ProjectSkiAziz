package model;
import java.util.Objects;

public class SkierPOJO {
    private String niveau;
    private boolean assurance;
    
	public SkierPOJO(String niveau, boolean assurance) {
		super();
		this.niveau = niveau;
		this.assurance = assurance;
	}

	public String getNiveau() {
		return niveau;
	}

	public void setNiveau(String niveau) {
		this.niveau = niveau;
	}

	public boolean isAssurance() {
		return assurance;
	}

	public void setAssurance(boolean assurance) {
		this.assurance = assurance;
	}

	@Override
	public String toString() {
		return "Skier [niveau=" + niveau + ", assurance=" + assurance + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(assurance, niveau);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SkierPOJO other = (SkierPOJO) obj;
		return assurance == other.assurance && Objects.equals(niveau, other.niveau);
	}
    
}
