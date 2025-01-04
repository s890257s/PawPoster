package tw.com.eeit.pawposter.model.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tw.com.eeit.pawposter.util.DateTool;

public class Member {

	private Integer memberId;
	private String email;
	private String password;
	private Boolean enabled;
	private String memberName;
	private Date memberBirthDate = DateTool.getDefaultDate();
	private String memberPhoto;
	private List<Pet> pets = new ArrayList<>();

	public Member() {
	}

	public Member(Integer memberId) {
		super();
		this.memberId = memberId;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
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

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Date getMemberBirthDate() {
		return memberBirthDate;
	}

	public void setMemberBirthDate(Date memberBirthDate) {
		this.memberBirthDate = memberBirthDate;
	}

	public String getMemberPhoto() {
		return memberPhoto;
	}

	public void setMemberPhoto(String memberPhoto) {
		this.memberPhoto = memberPhoto;
	}

	public List<Pet> getPets() {
		return pets;
	}

	public void setPets(List<Pet> pets) {
		this.pets = pets;
	}

	@Override
	public String toString() {
		return "Member [memberId=" + memberId + ", email=" + email + ", password=" + password + ", enabled=" + enabled
				+ ", memberName=" + memberName + ", memberBirthDate=" + memberBirthDate + ", memberPhoto=" + memberPhoto
				+ ", pets=" + pets + "]";
	}

	
}
