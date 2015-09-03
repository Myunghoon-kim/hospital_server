package hospitalServer.webdata.vo;

import java.util.Date;

public class Review {
	
	private int review_id;
	private String review_content;
	private double review_rate;
	private Date review_date;
	private String id;
	private int hospital_id;
	public Review(String review_content, double review_rate, String id, int hospital_id){
		this.review_content = review_content;
		this.review_rate = review_rate;
		this.id = id;
		this.hospital_id = hospital_id;
	}
	public Review(int review_id, String review_content, double review_rate,
			Date review_date, String id, int hospital_id) {
		super();
		this.review_id = review_id;
		this.review_content = review_content;
		this.review_rate = review_rate;
		this.review_date = review_date;
		this.id = id;
		this.hospital_id = hospital_id;
	}
	public int getReview_id() {
		return review_id;
	}
	public void setReview_id(int review_id) {
		this.review_id = review_id;
	}
	public String getReview_content() {
		return review_content;
	}
	public void setReview_content(String review_content) {
		this.review_content = review_content;
	}
	public double getReview_rate() {
		return review_rate;
	}
	public void setReview_rate(double review_rate) {
		this.review_rate = review_rate;
	}
	public Date getReview_date() {
		return review_date;
	}
	public void setReview_date(Date review_date) {
		this.review_date = review_date;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getHospital_id() {
		return hospital_id;
	}
	public void setHospital_id(int hospital_id) {
		this.hospital_id = hospital_id;
	}

	
	
}
