package hospitalServer.webdata.vo;

import java.util.ArrayList;

public class Hospital {
	private int hospital_id;
	private String hospital_name;
	private double hospital_rate;
	private String hospital_picture;
	private String open_hour;
	private String close_hour;
	private String introduction;
	private String address;
	private String tel;
	private double latitude;
	private double longitude;
	private double distance;
	
	private ArrayList<Integer> specialist;

	
	
	public Hospital(int hospital_id, String hospital_name,
			double hospital_rate, String hospital_picture, String open_hour,
			String close_hour, String introduction, String address,String tel,
			double latitude, double longitude) {
		super();
		this.hospital_id = hospital_id;
		this.hospital_name = hospital_name;
		this.hospital_rate = hospital_rate;
		this.hospital_picture = hospital_picture;
		this.open_hour = open_hour;
		this.close_hour = close_hour;
		this.introduction = introduction;
		this.address = address;
		this.tel = tel;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public Hospital(int hospital_id, String hospital_name,
			double hospital_rate, String hospital_picture, String open_hour,
			String close_hour, String introduction, String address,String tel,
			double latitude, double longitude, double distance) {
		super();
		this.hospital_id = hospital_id;
		this.hospital_name = hospital_name;
		this.hospital_rate = hospital_rate;
		this.hospital_picture = hospital_picture;
		this.open_hour = open_hour;
		this.close_hour = close_hour;
		this.introduction = introduction;
		this.address = address;
		this.tel = tel;
		this.latitude = latitude;
		this.longitude = longitude;
		this.distance = distance;
	}
	
	public Hospital(int hospital_id, String hospital_name,
			double hospital_rate, String hospital_picture, String open_hour,
			String close_hour, String introduction, String address,String tel,
			double latitude, double longitude, ArrayList<Integer> specialist) {
		super();
		this.hospital_id = hospital_id;
		this.hospital_name = hospital_name;
		this.hospital_rate = hospital_rate;
		this.hospital_picture = hospital_picture;
		this.open_hour = open_hour;
		this.close_hour = close_hour;
		this.introduction = introduction;
		this.address = address;
		this.tel = tel;
		this.latitude = latitude;
		this.longitude = longitude;
		this.specialist = specialist;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public int getHospital_id() {
		return hospital_id;
	}

	public void setHospital_id(int hospital_id) {
		this.hospital_id = hospital_id;
	}

	public String getHospital_name() {
		return hospital_name;
	}

	public void setHospital_name(String hospital_name) {
		this.hospital_name = hospital_name;
	}

	public double getHospital_rate() {
		return hospital_rate;
	}

	public void setHospital_rate(double hospital_rate) {
		this.hospital_rate = hospital_rate;
	}

	public String getHospital_picture() {
		return hospital_picture;
	}

	public void setHospital_picture(String hospital_picture) {
		this.hospital_picture = hospital_picture;
	}

	public String getOpen_hour() {
		return open_hour;
	}

	public void setOpen_hour(String open_hour) {
		this.open_hour = open_hour;
	}

	public String getClose_hour() {
		return close_hour;
	}

	public void setClose_hour(String close_hour) {
		this.close_hour = close_hour;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public ArrayList<Integer> getSpecialist() {
		return specialist;
	}

	public void setSpecialist(ArrayList<Integer> specialist) {
		this.specialist = specialist;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

}