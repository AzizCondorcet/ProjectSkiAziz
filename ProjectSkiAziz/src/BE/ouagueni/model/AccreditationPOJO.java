package BE.ouagueni.model;

import java.io.Serializable;
import java.util.Objects;

public class AccreditationPOJO implements Serializable {
    private String name;
    
	public AccreditationPOJO(String name) {
		super();
		this.name = name;
	}
    

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Override
	public String toString() {
		return "AccreditationPOJO [name=" + name + "]";
	}


	@Override
	public int hashCode() {
		return Objects.hash(name);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccreditationPOJO other = (AccreditationPOJO) obj;
		return Objects.equals(name, other.name);
	}
    
}

