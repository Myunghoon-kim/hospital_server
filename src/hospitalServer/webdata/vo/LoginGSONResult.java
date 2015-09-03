package hospitalServer.webdata.vo;

import java.io.Serializable;
import java.util.ArrayList;

public class LoginGSONResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5799837254984547206L;

	String success;
	ArrayList<LoginResult> pList;
	
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public ArrayList<LoginResult> getList() {
		return pList;
	}
	public void setList(ArrayList<LoginResult> pList) {
		this.pList = pList;
	}
	
	
	@Override
	public String toString() {
		return "LoginGSONResult [success=" + success + ", pList=" + pList + "]";
	}
	
	
}
