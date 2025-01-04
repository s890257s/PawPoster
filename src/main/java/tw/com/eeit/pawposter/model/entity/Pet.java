package tw.com.eeit.pawposter.model.entity;

import java.util.Base64;
import java.util.Date;

import tw.com.eeit.pawposter.util.CommonTool;

public class Pet {
	private Integer petId;
	private String petType;
	private String petName;
	private Date petBirthDate = CommonTool.getDefaultDate();
	private byte[] petPhoto = new byte[0];
	private Member member = new Member();
	private String petPhotoBase64;

	public Pet() {
	}

	public Pet(Integer petId) {
		this.petId = petId;
	}

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

	public Date getPetBirthDate() {
		return petBirthDate;
	}

	public void setPetBirthDate(Date petBirthDate) {
		this.petBirthDate = petBirthDate;
	}

	public byte[] getPetPhoto() {
		return petPhoto;
	}

	public void setPetPhoto(byte[] petPhoto) {

		String mimeType = CommonTool.getMimeType(petPhoto);
		this.petPhotoBase64 = "data:%s;base64,".formatted(mimeType) + Base64.getEncoder().encodeToString(petPhoto);

		this.petPhoto = petPhoto;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getPetPhotoBase64() {
		return petPhotoBase64;
	}

	public void setPetPhotoBase64(String petPhotoBase64) {
		this.petPhotoBase64 = petPhotoBase64;
	}

}
