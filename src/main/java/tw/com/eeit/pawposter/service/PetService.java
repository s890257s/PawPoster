package tw.com.eeit.pawposter.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import tw.com.eeit.pawposter.model.dao.PetDao;
import tw.com.eeit.pawposter.model.po.Pet;
import tw.com.eeit.pawposter.util.ConnectionFactory;

public class PetService {

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

	public Integer countPet() {
		try (Connection conn = ConnectionFactory.getConnection()) {

			PetDao petDao = new PetDao(conn);
			return petDao.countPet();

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
