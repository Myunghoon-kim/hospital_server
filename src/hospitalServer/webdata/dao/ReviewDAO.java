package hospitalServer.webdata.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import hospitalServer.webdata.vo.Review;

public class ReviewDAO {
	private static final String INSERT_REVIEW = "INSERT INTO review(review_content, review_rate ,id, hospital_id) values(?,?,?,?)";
	private static final String GET_REVIEW_LIST = "SELECT * FROM review WHERE hospital_id = ?";
	
	
	public int insertReview(Review review){
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = JDBCUtil.getConnection();
			stmt = con.prepareStatement(INSERT_REVIEW);
			stmt.setString(1, review.getReview_content());
			stmt.setDouble(2, review.getReview_rate());
			stmt.setString(3, review.getId());
			stmt.setInt(4, review.getHospital_id());
			System.out.println(stmt);
			int result = stmt.executeUpdate();
			if(result == 1){
				return 1;
			}else{
				return 0;
			}

		} catch (SQLException e) {
			System.out.println("login error : " + e);
		} finally {
			JDBCUtil.close( stmt, con);
		}
		return 0;
	}
	
	public ArrayList<Review> getReviewList(int hospital_id){
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		ArrayList<Review> reviewList = new ArrayList<Review>();

		try {
			con = JDBCUtil.getConnection();
			stmt = con.prepareStatement(GET_REVIEW_LIST);
			stmt.setInt(1, hospital_id);

			rst = stmt.executeQuery();
			while (rst.next()) {
				reviewList.add(new Review(rst.getInt("review_id"),rst.getString("review_content"),rst.getDouble("review_rate"),new Date(rst.getTimestamp("review_date").getTime()),rst.getString("id"),rst.getInt("hospital_id")));
				
			}

		} catch (SQLException e) {
			System.out.println("login error : " + e);
		} finally {
			JDBCUtil.close(rst, stmt, con);
		}

		return reviewList;
	}
	
}
