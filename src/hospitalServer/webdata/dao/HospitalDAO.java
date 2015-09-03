package hospitalServer.webdata.dao;

import hospitalServer.webdata.vo.Hospital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class HospitalDAO {
	private final static String GET_HOSPITAL_INFORM = "SELECT * FROM hospital WHERE hospital_id = ?";
	private final static String GET_HOSPITAL_LIST = "SELECT * FROM hospital LEFT OUTER JOIN specialist ON hospital.hospital_id = specialist.hospital_id  WHERE specialist.specialist = ?";
	private final static String GET_HOSPITAL_LOCATION_LIST = "SELECT *, (6371 * acos(cos(radians(? )) * cos(radians(latitude)) * cos(radians(longitude) - radians(? )) + sin(radians(? )) * sin(radians(Latitude )))) AS distance FROM hospital  HAVING distance < 5  ORDER BY distance;";

	// 뒤 앞 뒤
	public Hospital getHospitalInform(int hospital_id) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		Hospital hospital = null;

		try {
			con = JDBCUtil.getConnection();
			stmt = con.prepareStatement(GET_HOSPITAL_INFORM);
			stmt.setInt(1, hospital_id);

			rst = stmt.executeQuery();
			while (rst.next()) {
				hospital = new Hospital(rst.getInt("hospital_id"),
						rst.getString("hospital_name"),
						rst.getDouble("hospital_rate"),
						rst.getString("hospital_picture"),
						rst.getString("open_hour"),
						rst.getString("close_hour"),
						rst.getString("introduction"),
						rst.getString("address"), rst.getString("tel"),
						rst.getDouble("latitude"), rst.getDouble("longitude"));
			}

		} catch (SQLException e) {
			System.out.println("login error : " + e);
		} finally {
			JDBCUtil.close(rst, stmt, con);
		}

		return hospital;

	}

	public ArrayList<Hospital> getHospitalList(int specialist) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		ArrayList<Hospital> hospitalList = new ArrayList<Hospital>();

		try {
			con = JDBCUtil.getConnection();
			stmt = con.prepareStatement(GET_HOSPITAL_LIST);
			stmt.setInt(1, specialist);

			rst = stmt.executeQuery();
			while (rst.next()) {
				hospitalList.add(new Hospital(rst.getInt("hospital_id"), rst
						.getString("hospital_name"), rst
						.getDouble("hospital_rate"), rst
						.getString("hospital_picture"), rst
						.getString("open_hour"), rst.getString("close_hour"),
						rst.getString("introduction"),
						rst.getString("address"), rst.getString("tel"), rst
								.getDouble("latitude"), rst
								.getDouble("longitude")));
			}

		} catch (SQLException e) {
			System.out.println("login error : " + e);
		} finally {
			JDBCUtil.close(rst, stmt, con);
		}

		return hospitalList;
	}

	public ArrayList<Hospital> getHospitalLocationList(double longitude,
			double latitude) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		ArrayList<Hospital> hospitalList = new ArrayList<Hospital>();

		try {
			con = JDBCUtil.getConnection();
			stmt = con.prepareStatement(GET_HOSPITAL_LOCATION_LIST);
			stmt.setDouble(1, latitude);
			stmt.setDouble(2, longitude);
			stmt.setDouble(3, latitude);
			rst = stmt.executeQuery();
			while (rst.next()) {
				hospitalList.add(new Hospital(rst.getInt("hospital_id"), rst
						.getString("hospital_name"), rst
						.getDouble("hospital_rate"), rst
						.getString("hospital_picture"), rst
						.getString("open_hour"), rst.getString("close_hour"),
						rst.getString("introduction"),
						rst.getString("address"), rst.getString("tel"), rst
								.getDouble("latitude"), rst
								.getDouble("longitude"), rst
								.getDouble("distance")));
			}

		} catch (SQLException e) {
			System.out.println("login error : " + e);
		} finally {
			JDBCUtil.close(rst, stmt, con);
		}

		return hospitalList;
	}
}
