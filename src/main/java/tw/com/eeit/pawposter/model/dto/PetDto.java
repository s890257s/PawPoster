package tw.com.eeit.pawposter.model.dto;

import tw.com.eeit.pawposter.model.po.Pet;

public class PetDto {

	public PetDto() {
	}

	public PetDto(Pet pet) {
		this.petId = pet.getPetId();
		this.petType = pet.getPetType();
		this.petName = pet.getPetName();
		this.petPhotoBase64 = pet.getPetPhotoBase64();
		this.memberId = pet.getMember().getMemberId();
		this.memberName = pet.getMember().getMemberName();
	}

	private Integer petId;
	private String petType;
	private String petName;
	private String petPhotoBase64;
	private Integer memberId;
	private String memberName;
	private Boolean isLiked = false;

	public Integer getPetId() {
		return petId;
	}

	public void setPetId(Integer petId) {
		this.petId = petId;
	}

	public String getPetType() {
		return petType;
	}

	public void setPetType(String petType) {
		this.petType = petType;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public String getPetPhotoBase64() {
		return petPhotoBase64;
	}

	public void setPetPhotoBase64(String petPhotoBase64) {
		this.petPhotoBase64 = petPhotoBase64;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Boolean getIsLiked() {
		return isLiked;
	}

	public void setIsLiked(Boolean isLiked) {
		this.isLiked = isLiked;
	}
}
