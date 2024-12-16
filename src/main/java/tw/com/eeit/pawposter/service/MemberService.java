//package tw.com.eeit.pawposter.service;
//
//import java.sql.Connection;
//
//import tw.com.eeit.pawposter.model.bean.Likes;
//import tw.com.eeit.pawposter.model.dao.LikesDao;
//import tw.com.eeit.pawposter.util.ConnectionFactory;
//
//public class MemberService {
//
//	/**
//	 * 呼叫此方法並傳入Likes物件(須包含日期、會員ID、寵物ID)以切換「按讚」狀態。<br>
//	 * 1.若資料庫有按讚記錄則移除<br>
//	 * 2.若資料庫無按讚記錄則增加<br>
//	 * 
//	 * @param likes 單筆按讚記錄，須包含日期、會員ID、寵物ID
//	 * @return "like!" : 若是新增按讚記錄到資料庫 ； "unlike!" : 若是從資料庫刪除按讚記錄 ； "error!" : 未預期的錯誤
//	 */
//	public String switchLikeStatus(Likes likes) {
//		try (Connection conn = ConnectionFactory.getConnection()) {
//
//			LikesDao lDao = new LikesDao(conn);
//			Likes likesFromDB = lDao.findLikeByMemberIDAndPetID(likes);
//
//			if (likesFromDB == null) {
//				lDao.insertLike(likes);
//				return "like!";
//			}
//
//			if (likesFromDB != null) {
//				lDao.removeLike(likes);
//				return "unlike!";
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "error!";
//	}
//}
