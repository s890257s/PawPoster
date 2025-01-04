package tw.com.eeit.pawposter.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import tw.com.eeit.pawposter.model.dao.MemberPetLikeDao;
import tw.com.eeit.pawposter.model.dao.PetDao;
import tw.com.eeit.pawposter.model.dto.PetDto;
import tw.com.eeit.pawposter.model.entity.Pet;
import tw.com.eeit.pawposter.util.ConnectionFactory;

public class PetService {

	/* === Read === */

	public Integer countPet() {
		try (Connection conn = ConnectionFactory.getConnection()) {

			PetDao petDao = new PetDao(conn);
			return petDao.countPet();

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<PetDto> getAllPetWithMemberAndLikeStatus(Integer memberId) {
		try (Connection conn = ConnectionFactory.getConnection()) {

			PetDao petDao = new PetDao(conn);
			MemberPetLikeDao memberPetLikeDao = new MemberPetLikeDao(conn);

			// 取得全部寵物
			List<Pet> pets = petDao.findAllPetWithMember();

			// 取得使用者按讚的寵物 id
			Set<Integer> likedPetIds = memberPetLikeDao.findLikedPetIds(memberId);

			// 轉換成 dto (data transfer object)
			List<PetDto> petDtos = pets.stream() // 將 List 流化
					.map(PetDto::new) // 將所有元素轉型成 PetDto，使用建構子 PetDto(Pet pet)
					.peek(petDto -> petDto.setIsLiked(likedPetIds.contains(petDto.getPetId()))) // 設定每個元素的 like 狀態，只有包含在
																								// likedPetIds 中的為 true
					.toList();

			return petDtos;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/* === Create === */

	public void insertPet(Pet pet) {
		try (Connection conn = ConnectionFactory.getConnection()) {

			PetDao petDao = new PetDao(conn);
			petDao.insertPet(pet);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertPets(List<Pet> pets) {
		try (Connection conn = ConnectionFactory.getConnection()) {

			PetDao petDao = new PetDao(conn);
			petDao.insertPets(pets);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
