package hospitalServer.webdata.servlet;

import hospitalServer.webdata.dao.HospitalDAO;
import hospitalServer.webdata.vo.Hospital;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class HospitalServlet
 */
@WebServlet({ "/hospital", "/hospital/*" })
public class HospitalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HospitalServlet() {
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
		
		
		if (action.equals("hospital")) {
			int hospital_type = Integer.parseInt(request
					.getParameter("hospital_type"));
			HospitalDAO hDAO = new HospitalDAO();
			ArrayList<Hospital> hospitalList = hDAO
					.getHospitalList(hospital_type);
			
			Gson gson = new Gson();
			JsonObject jsonObj = new JsonObject();
			String json_result = null;
			if (hospitalList.isEmpty()) {
				jsonObj.addProperty("success", 0);
				JsonObject nestObj = new JsonObject();
				nestObj.addProperty("message", "병원 리스트가 없습니다.");
				jsonObj.add("result", nestObj);

				json_result = gson.toJson(jsonObj);
			} else {

				jsonObj.addProperty("success", 1);
				JsonObject nestObj = new JsonObject();

				if (hospital_type == 9) {
					nestObj.addProperty("message", "약국 리스트가 조회되었습니다.");
				} else {
					nestObj.addProperty("message", "병원 리스트가 조회되었습니다.");
				}

				nestObj.add("hospital_list", gson.toJsonTree(hospitalList));
				jsonObj.add("result", nestObj);

				json_result = gson.toJson(jsonObj);
			}

			System.out.println(json_result);
			PrintWriter writer = response.getWriter();
			writer.print(json_result);
			writer.flush();
			writer.close();

		} else if (action.equals("map")) {
			double longitude = Double.parseDouble(request
					.getParameter("longitude"));
			double latitude = Double.parseDouble(request
					.getParameter("latitude"));
			HospitalDAO hDAO = new HospitalDAO();
			ArrayList<Hospital> hospitalList = hDAO.getHospitalLocationList(longitude, latitude);
			Gson gson = new Gson();
			JsonObject jsonObj = new JsonObject();
			String json_result = null;
			if (hospitalList.isEmpty()) {
				jsonObj.addProperty("success", 0);
				JsonObject nestObj = new JsonObject();
				nestObj.addProperty("message", "병원 리스트가 없습니다.");
				jsonObj.add("result", nestObj);

				json_result = gson.toJson(jsonObj);
			} else {

				jsonObj.addProperty("success", 1);
				JsonObject nestObj = new JsonObject();

				nestObj.addProperty("message", "병원 리스트가 조회되었습니다.");

				nestObj.add("hospital_list", gson.toJsonTree(hospitalList));
				jsonObj.add("result", nestObj);

				json_result = gson.toJson(jsonObj);
			}

			System.out.println(json_result);
			PrintWriter writer = response.getWriter();
			writer.print(json_result);
			writer.flush();
			writer.close();
			
		} else {
			int hospital_number = Integer.parseInt(action);
			HospitalDAO hDAO = new HospitalDAO();
			Hospital hospital = hDAO.getHospitalInform(hospital_number);
			Gson gson = new Gson();
			JsonObject jsonObj = new JsonObject();
			String json_result = null;
			if (hospital == null) {
				jsonObj.addProperty("success", 0);
				JsonObject nestObj = new JsonObject();
				nestObj.addProperty("message", "병원 리스트가 없습니다.");
				jsonObj.add("result", nestObj);

				json_result = gson.toJson(jsonObj);
			} else {

				jsonObj.addProperty("success", 1);
				JsonObject nestObj = new JsonObject();

				nestObj.addProperty("message", "정상적으로 조회되었습니다.");

				nestObj.add("hospital_information", gson.toJsonTree(hospital));
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
