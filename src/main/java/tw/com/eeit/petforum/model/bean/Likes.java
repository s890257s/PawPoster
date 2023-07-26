package tw.com.eeit.petforum.model.bean;

import java.util.Date;

public class Likes {

	private Integer lID;
	private Date time;
	private Member member;
	private Pet pet;

	public Integer getlID() {
		return lID;
	}

	public void setlID(Integer lID) {
		this.lID = lID;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}

	@Override
	public String toString() {
		return "Likes [lID=" + lID + ", time=" + time + ", member=" + member.getmID() + ", pet=" + pet.getpID() + "]";
	}

}
