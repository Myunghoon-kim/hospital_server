package hospitalServer.webdata.servlet;

import hospitalServer.webdata.dao.HospitalDAO;
import hospitalServer.webdata.vo.Hospital;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
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
			if (request.getParameter("hospital_id") == null) {
				Enumeration<String> params = request.getParameterNames();
				while (params.hasMoreElements()) {
					System.out.println(params.nextElement());
				}

				System.out.println(request.getParameter("longitude"));
				double longitude = Double.parseDouble(request
						.getParameter("longitude"));

				System.out.println(longitude);
				double latitude = Double.parseDouble(request
						.getParameter("latitude"));
				int hospital_type = Integer.parseInt(request
						.getParameter("hospital_type"));
				HospitalDAO hDAO = new HospitalDAO();
				ArrayList<Hospital> hospitalList = hDAO.getHospitalList(
						longitude, latitude, hospital_type);

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
			} else {
				int hospital_id = Integer.parseInt(request.getParameter("hospital_id"));

				System.out.println(hospital_id);
				HospitalDAO hDAO = new HospitalDAO();
				Hospital hospital = hDAO.getHospitalInform(hospital_id);
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

					nestObj.add("hospital_information",
							gson.toJsonTree(hospital));
					jsonObj.add("result", nestObj);

					json_result = gson.toJson(jsonObj);
				}
				PrintWriter writer = response.getWriter();
				writer.print(json_result);
				writer.flush();
				writer.close();

			}

		} else if (action.equals("symptom_type")) {
			double longitude = Double.parseDouble(request
					.getParameter("longitude"));
			double latitude = Double.parseDouble(request
					.getParameter("latitude"));
			String symptom_type = request.getParameter("symptom_type");
			HospitalDAO hDAO = new HospitalDAO();
			ArrayList<Hospital> hospitalList = hDAO.getSymptomList(longitude,
					latitude, symptom_type);

			/*
			 * 내과 0 외과 1 치과 2 이비인후과 3 피부과 4 성형외과 5 종합병원 6 안과 7 한의원 8 약국 9
			 * 
			 * 
			 * 눈 : 7,6 코 : 3,0,6 귀 : 3,0,6 열,감기 : 0,3,6 머리아픔 : 0,6 이 : 2,6 무릎 :
			 * 1,6 피부 : 4,6
			 */

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
			ArrayList<Hospital> hospitalList = hDAO.getHospitalLocationList(
					longitude, latitude);
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

		}
	}
}
