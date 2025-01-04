package tw.com.eeit.pawposter.model.entity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.Base64;
import java.util.Date;
import java.util.logging.Logger;

import tw.com.eeit.pawposter.util.ConnectionFactory;
import tw.com.eeit.pawposter.util.DateTool;

public class Pet {

	private final Logger log = Logger.getLogger(this.getClass().getName());

	private Integer petId;
	private String petType;
	private String petName;
	private Date petBirthDate = DateTool.getDefaultDate();
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

		try {
			String mimeType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(petPhoto));
			this.petPhotoBase64 = "data:%s;base64,".formatted(mimeType) + Base64.getEncoder().encodeToString(petPhoto);

		} catch (IOException e) {
			log.warning("讀取 pet 圖片 MimeType 出錯。message:" + e.getMessage());
		}
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
