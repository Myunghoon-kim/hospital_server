package hospitalServer.webdata.dao;

import hospitalServer.webdata.vo.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;





public class MemberDAO {

	private final static String LOGIN = "SELECT * FROM member WHERE id = ? AND pw = ? ";
	private final static String INSERT_MEMBER = "INSERT INTO member(id,pw,name) VALUES(?,?,?)";
	private final static String UPDATE_MEMBER_PICTURE = "UPDATE member SET profile_picture=? WHERE id=?";
	private final static String INSERT_MEMBER_FACEBOOK = "INSERT INTO facebook VALUES(?)";
	private final static String CHECK_MEMBER = "SELECT id FROM member WHERE id=?";
	private final static String UPDATE_PASSWORD = "UPDATE member SET pw = ? WHERE id = ?";
	private final static String GET_MEMBER ="SELECT * FROM member WHERE id = ?";
	
	
	public boolean doCheck(Member member){
		//실제 DB연동 여기서 한다.
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		
		
		try{
			con  = JDBCUtil.getConnection();
			stmt = con.prepareStatement(CHECK_MEMBER);
			stmt.setString(1, member.getId());
			
			rst = stmt.executeQuery();
			if(rst.next()){
				return true;
			} else {
				return false;
			}
			
		} catch (SQLException e){
			System.out.println("login error : " + e);
		} finally {
			JDBCUtil.close(rst, stmt, con);
		}
		return false;
	}
	public Member getMember(String id){
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		Member finded_member = null;
		
		try{
			con  = JDBCUtil.getConnection();
			stmt = con.prepareStatement(GET_MEMBER);
			stmt.setString(1, id);
			
			rst = stmt.executeQuery();
			if(rst.next()){
				finded_member = new Member(rst.getString("id"),	 rst.getString("pw"), rst.getString("profile_picture"), rst.getString("name"));
			}
			
		} catch (SQLException e){
			System.out.println("login error : " + e);
		} finally {
			JDBCUtil.close(rst, stmt, con);
		}
		return finded_member;
	
	}
	
	
	public int updatePicture(String profile_picture, String id){
		Connection con = null;
		PreparedStatement stmt = null;
		int result = 0;
		try{
			con  = JDBCUtil.getConnection();
			stmt = con.prepareStatement(UPDATE_MEMBER_PICTURE);
			stmt.setString(1, profile_picture);
			stmt.setString(2, id);
			
			 result= stmt.executeUpdate();
			
			
		} catch (SQLException e){
			System.out.println("login error : " + e);
		} finally {
			JDBCUtil.close(stmt, con);
		}
		return result;

	}
	public Member doLogin(Member member){
		//실제 DB연동 여기서 한다.
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		Member finded_member = null;
		
		try{
			con  = JDBCUtil.getConnection();
			stmt = con.prepareStatement(LOGIN);
			stmt.setString(1, member.getId());
			stmt.setString(2, member.getPw());
			
			rst = stmt.executeQuery();
			if(rst.next()){
				finded_member = new Member(rst.getString("id"),	 rst.getString("pw"), rst.getString("profile_picture"), rst.getString("name"));
			}
			
		} catch (SQLException e){
			System.out.println("login error : " + e);
		} finally {
			JDBCUtil.close(rst, stmt, con);
		}
		return finded_member;
	}
	
	public int updatePassword(String id, String new_pw){
		Connection con = null;
		PreparedStatement stmt = null;
		int result = 0;
		try{
			con  = JDBCUtil.getConnection();
			stmt = con.prepareStatement(UPDATE_PASSWORD);
			stmt.setString(1, new_pw);
			stmt.setString(2, id);
			
			 result= stmt.executeUpdate();
			
			
		} catch (SQLException e){
			System.out.println("login error : " + e);
		} finally {
			JDBCUtil.close(stmt, con);
		}
		return result;
		
	}
	
	public int registerMember(String facebookToken){
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			con = JDBCUtil.getConnection();
			pstmt = con.prepareStatement(INSERT_MEMBER_FACEBOOK);
			
			pstmt.setString(1, facebookToken);
			
			int result = pstmt.executeUpdate();
			
			if(result == 1){
				System.out.println("insert success");
			}
			
		} catch (SQLException e){
			System.out.println("board insert error : " + e);
		} finally { 
			JDBCUtil.close(pstmt, con);
		}
		
		
		return 1;
	}
	
	public int registerMember(Member member){
		if(checkId(member.getId())){
			return 0;
		}
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			con = JDBCUtil.getConnection();
			pstmt = con.prepareStatement(INSERT_MEMBER);
			
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getPw());
//			pstmt.setString(3, member.getProfile_picture());
			pstmt.setString(3, member.getName());
			int result = pstmt.executeUpdate();
			
			if(result == 1){
				System.out.println("insert success");
			}
			
		} catch (SQLException e){
			System.out.println("board insert error : " + e);
		} finally { 
			JDBCUtil.close(pstmt, con);
		}
		
		
		return 1;
	}
	
	public boolean checkId(String id){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			con = JDBCUtil.getConnection();
			pstmt = con.prepareStatement(CHECK_MEMBER);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			if(rs.next()){
				return true;
			}else{
				return false;
			}
			
		} catch (SQLException e){
			System.out.println("board insert error : " + e);
		} finally { 
			JDBCUtil.close(rs,pstmt, con);
		}
		
		return false;
	}
	
	
}
