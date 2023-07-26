package tw.com.eeit.petforum.model.bean;

import java.util.List;

public class Member {

	private int mID;
	private String email;
	private String password;
	private boolean enabled;
	private String level;
	private String mName;
	private int mAge;
	private String address;
	private String mPhoto;
	private List<Pet> pets;

	public Member() {
	}

	public Member(int mID) {
		this.mID = mID;
	}

	public int getmID() {
		return mID;
	}

	public void setmID(int mID) {
		this.mID = mID;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public int getmAge() {
		return mAge;
	}

	public void setmAge(int mAge) {
		this.mAge = mAge;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getmPhoto() {
		return mPhoto;
	}

	public void setmPhoto(String mPhoto) {
		this.mPhoto = mPhoto;
	}

	public List<Pet> getPets() {
		return pets;
	}

	public void setPets(List<Pet> pets) {
		this.pets = pets;
	}

	@Override
	public String toString() {
		return "Member [mID=" + mID + ", email=" + email + ", password=" + password + ", enabled=" + enabled
				+ ", level=" + level + ", mName=" + mName + ", mAge=" + mAge + ", address=" + address + ", pets=" + pets
				+ "]";
	}

}
