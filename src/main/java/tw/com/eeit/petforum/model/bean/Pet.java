package tw.com.eeit.petforum.model.bean;

public class Pet {

	private Integer pID;
	private String type;
	private String pName;
	private Integer pAge;
	private byte[] pPhoto;
	private Member member;
	private String pPhotoBase64;

	public Pet() {
	}

	public Pet(String type, Integer pAge) {
		super();
		this.type = type;
		this.pAge = pAge;
	}

	public Pet(String pName, String type, Integer pAge, byte[] pPhoto) {
		this.pName = pName;
		this.type = type;
		this.pAge = pAge;
		this.pPhoto = pPhoto;
	}

	public Pet(String pName, String type, Integer pAge, byte[] pPhoto, Member member) {
		this.pName = pName;
		this.type = type;
		this.pAge = pAge;
		this.pPhoto = pPhoto;
		this.member = member;
	}

	public Pet(Integer pID) {
		super();
		this.pID = pID;
	}

	public Integer getpID() {
		return pID;
	}

	public void setpID(Integer pID) {
		this.pID = pID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public Integer getpAge() {
		return pAge;
	}

	public void setpAge(Integer pAge) {
		this.pAge = pAge;
	}

	public byte[] getpPhoto() {
		return pPhoto;
	}

	public void setpPhoto(byte[] pPhoto) {
		this.pPhoto = pPhoto;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getpPhotoBase64() {
		return pPhotoBase64;
	}

	public void setpPhotoBase64(String pPhotoBase64) {
		this.pPhotoBase64 = pPhotoBase64;
	}

	@Override
	public String toString() {
		return "Pet [pID=" + pID + ", type=" + type + ", pName=" + pName + ", pAge=" + pAge + ", member="
				+ member.getmName() + "]";
	}

}
