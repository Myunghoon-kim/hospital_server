package hospitalServer.webdata.servlet;

import hospitalServer.webdata.dao.MemberDAO;
import hospitalServer.webdata.vo.Member;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class MemberServlet
 */
@WebServlet({ "/login", "/logout", "/member/*" })
public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MemberServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		process(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		process(request, response);
	}

	private void process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/text");
		String uri = request.getRequestURI();
		int lastIndex = uri.lastIndexOf("/");
		String action = uri.substring(lastIndex + 1);

		if (action.equals("login")) {
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");

			System.out.println("id : " + id + "pw" + pw);
			MemberDAO mDAO = new MemberDAO();
			Member member = mDAO.doLogin(new Member(id, pw));
			Gson gson = new Gson();
			JsonObject jsonObj = new JsonObject();
			String json_result = null;
			if (member == null) {
				jsonObj.addProperty("success", 0);
				JsonObject nestObj = new JsonObject();
				nestObj.addProperty("message", "로그인이 실패하였습니다.");
				jsonObj.add("result", nestObj);
				json_result = gson.toJson(jsonObj);

			} else {
				jsonObj.addProperty("success", 1);
				JsonObject nestObj = new JsonObject();
				nestObj.addProperty("message", "로그인인 정상적으로 되었습니다.");
				nestObj.add("member", gson.toJsonTree(member));
				jsonObj.add("result", nestObj);
				json_result = gson.toJson(jsonObj);
			}

			PrintWriter writer = response.getWriter();
			writer.print(json_result);
			writer.flush();
			writer.close();

		} else if (action.equals("logout")) {

		} else if (action.equals("password")) {

		} else if (action.equals("profile")) {

		} else if (action.equals("register")) {
			String facebookToken = request.getParameter("access_token");
			Gson gson = new Gson();
			JsonObject jsonObj = new JsonObject();
			String json_result = null;
			if (facebookToken == null) {
				String id = request.getParameter("id");
				String pw = request.getParameter("pw");
				String name = request.getParameter("name");
				MemberDAO mDAO = new MemberDAO();
				
				System.out.println(id+"  "+pw+"   "+name);
				Member member = new Member(id, pw, name);
				int flag = mDAO.registerMember(member);
				if (flag == 0) {
					jsonObj.addProperty("success", 0);
					JsonObject nestObj = new JsonObject();
					nestObj.addProperty("message", "회원가입에 실패하였습니다.");
					jsonObj.add("result", nestObj);
					json_result = gson.toJson(jsonObj);
				} else {
					jsonObj.addProperty("success", 1);
					JsonObject nestObj = new JsonObject();
					nestObj.addProperty("message", "회원가입에 성공하였습니다.");
					nestObj.add("member", gson.toJsonTree(member));
					jsonObj.add("result", nestObj);
					json_result = gson.toJson(jsonObj);
				}
				
				PrintWriter writer = response.getWriter();
				writer.print(json_result);
				writer.flush();
				writer.close();

			} else {
				MemberDAO mDAO = new MemberDAO();
				int flag = mDAO.registerMember(facebookToken);
				if (flag == 0) {
					jsonObj.addProperty("success", 0);
					JsonObject nestObj = new JsonObject();
					nestObj.addProperty("message", "회원가입에 실패하였습니다.");
					jsonObj.add("result", nestObj);
					json_result = gson.toJson(jsonObj);
				} else {
					jsonObj.addProperty("success", 1);
					JsonObject nestObj = new JsonObject();
					nestObj.addProperty("message", "회원가입에 성하였습니다.");
					jsonObj.add("result", nestObj);
					json_result = gson.toJson(jsonObj);
				}
				PrintWriter writer = response.getWriter();
				writer.print(json_result);
				writer.flush();
				writer.close();
			}

		}

	}

}
