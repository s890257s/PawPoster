package tw.com.eeit.pawposter.model.bean;

import java.util.Date;

import tw.com.eeit.pawposter.util.DateTool;

public class MemberPetLike {

	private Integer likeId;
	private Date createDate = DateTool.getDefaultDate();
	private Member member = new Member();
	private Pet pet = new Pet();

	public MemberPetLike() {
	}

	public MemberPetLike(Integer likeId, Date createDate, Integer memberId, Integer petId) {
		this.likeId = likeId;
		this.createDate = createDate;
		this.member = new Member(memberId);
		this.pet = new Pet(petId);
	}

	public Integer getLikeId() {
		return likeId;
	}

	public void setLikeId(Integer likeId) {
		this.likeId = likeId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public void setMember(Integer memberId) {
		this.member = new Member(memberId);
	}

	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}

	public void setPet(Integer petId) {
		this.pet = new Pet(petId);
	}

}
