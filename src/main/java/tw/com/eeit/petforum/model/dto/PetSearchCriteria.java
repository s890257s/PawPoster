package tw.com.eeit.petforum.model.dto;

public class PetSearchCriteria {

	private String minAge;
	private String maxAge;
	private String type;

	public PetSearchCriteria() {
		super();
	}

	public PetSearchCriteria(String minAge, String maxAge, String type) {
		super();
		this.minAge = minAge;
		this.maxAge = maxAge;
		this.type = type;
	}

	public String getMinAge() {
		return minAge;
	}

	public void setMinAge(String minAge) {
		this.minAge = minAge;
	}

	public String getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(String maxAge) {
		this.maxAge = maxAge;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
