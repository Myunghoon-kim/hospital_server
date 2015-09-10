package hospitalServer.webdata.dao;

import hospitalServer.webdata.vo.Hospital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class HospitalDAO {
	private final static String GET_HOSPITAL_INFORM = "SELECT * FROM hospital WHERE hospital_id = ?";
	private final static String GET_HOSPITAL_LIST = "select *, ROUND(1000 * (6371 * acos(cos(radians(?)) * cos(radians(latitude)) * cos(radians(longitude) - radians(?)) + sin(radians(?)) * sin(radians(Latitude ))))) AS distance, group_concat(specialist.specialist) AS specialist_list from hospital right outer join specialist on hospital.hospital_id = specialist.hospital_id where specialist.hospital_id IN(select hospital_id from specialist where specialist.specialist = ?) group by hospital.hospital_id HAVING distance < 5000 ORDER BY distance,hospital_rate;";
	private final static String GET_HOSPITAL_LOCATION_LIST = "SELECT *, (6371 * acos(cos(radians(? )) * cos(radians(latitude)) * cos(radians(longitude) - radians(? )) + sin(radians(? )) * sin(radians(Latitude )))) AS distance FROM hospital  HAVING distance < 5  ORDER BY distance";
	
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
	public ArrayList<Hospital> getSymptomList(double longitude, double latitude, String symptom_type){
		/* 
		 * 내과 0
		 * 외과 1
		 * 치과 2
		 * 이비인후과 3
		 * 피부과 4
		 * 성형외과 5
		 * 종합병원 6
		 * 안과 7
		 * 한의원 8
		 * 약국 9
		 * 
		 * 
		 * 눈 : 7,6
		 * 코 : 3,0,6 o
		 * 귀 : 3,0,6 o
		 * 열,감기 : 0,3,6 o
		 * 머리아픔 : 0,6 o 
		 * 이 : 2,6 o
		 * 무릎 : 1,6 o
		 * 피부 : 4,6 o
		 * 
		 * */
		String front = "SELECT * , ROUND(1000 * (6371 * acos(cos(radians(?)) * cos(radians(latitude)) * cos(radians(longitude) - radians(?)) + sin(radians(?)) * sin(radians(Latitude ))))) AS distance FROM hospital LEFT OUTER JOIN specialist ON hospital.hospital_id = specialist.hospital_id  ";
		//WHERE specialist.specialist = ? 
		String middle = null;	
		String rear = "HAVING distance < 5000  ORDER BY hospital_rate asc,distance desc";
		
		if(symptom_type.equals("NOSE") || symptom_type.equals("EARS") || symptom_type.equals("COLD")){
			middle = " WHERE specialist.specialist = 0 OR specialist.specialist = 3 OR specialist.specialist = 6 ";
		}else if(symptom_type.equals("HEAD")){
			middle = " WHERE specialist.specialist = 0 OR specialist.specialist = 6 ";
		}else if(symptom_type.equals("KNEE")){
			middle = " WHERE specialist.specialist = 1 OR specialist.specialist = 6 ";
		}else if(symptom_type.equals("TEETH")){
			middle = " WHERE specialist.specialist = 2 OR specialist.specialist = 6 ";
		}else if(symptom_type.equals("SKIN")){
			middle = " WHERE specialist.specialist = 4 OR specialist.specialist = 6 ";
		}else if(symptom_type.equals("EYES")){
			middle = " WHERE specialist.specialist = 7 OR specialist.specialist = 6 ";
		}
		String end = front + middle + rear;
		
		
		
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		ArrayList<Hospital> hospitalList = new ArrayList<Hospital>();

		try {
			con = JDBCUtil.getConnection();
			stmt = con.prepareStatement(end);
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
								.getDouble("longitude"),rst.getDouble("distance")));
			}

		} catch (SQLException e) {
			System.out.println("login error : " + e);
		} finally {
			JDBCUtil.close(rst, stmt, con);
		}

		return hospitalList;
		
	}
	public ArrayList<Hospital> getHospitalList(double longitude,double latitude,int specialist) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		ArrayList<Hospital> hospitalList = new ArrayList<Hospital>();

		try {
			con = JDBCUtil.getConnection();
			stmt = con.prepareStatement(GET_HOSPITAL_LIST);
			stmt.setDouble(1, latitude);
			stmt.setDouble(2, longitude);
			stmt.setDouble(3, latitude);
			stmt.setInt(4, specialist);

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
								.getDouble("longitude"),rst.getDouble("distance"),rst.getString("specialist_list")));
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
								.getDouble("distance"),rst.getString("specialist_list")));
			}

		} catch (SQLException e) {
			System.out.println("login error : " + e);
		} finally {
			JDBCUtil.close(rst, stmt, con);
		}

		return hospitalList;
	}

	public ArrayList<Hospital> getSymptomHospitalList(double longitude,
			double latitude, int symptom_type) {
		// TODO Auto-generated method stub
		
		
		
		
		
		return null;
	}
}
