package com.gadgetbadget.funding.model;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class FundAPI
 */
@WebServlet("/FundAPI")
public class FundAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Fund fundObj = new Fund();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FundAPI() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JsonObject res = fundObj.readFunds(null);
		JsonObject table = getTable(res);
		System.out.println(table.toString());
		response.getWriter().append(table.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JsonObject res = null;

		try {
			String fund_id = request.getParameter("funderId").toString();
			String res_id = request.getParameter("researchId").toString();
			float funded_amount = Float.parseFloat(request.getParameter("fundedAmount").toString());
			String credit_c_no = request.getParameter("creditCardNo").toString();
			
			
			System.out.println(fund_id+" "+res_id+" "+funded_amount+" "+credit_c_no);

			JsonObject insertRes = fundObj.insertFund(fund_id, res_id, funded_amount, credit_c_no);
			System.out.println("res= " + insertRes.toString());

			if (! insertRes.get("STATUS").getAsString().equalsIgnoreCase("SUCCESSFUL")) {
				res = new JsonObject();
				res.addProperty("status", "error");
				res.addProperty("data", "error...");
				response.getWriter().append(res.toString());
				return;
			}

			JsonObject table = getTable(fundObj.readFunds(null));
			res = new JsonObject();
			res.addProperty("status", "success");
			res.addProperty("data", table.get("data").getAsString());

		} catch (Exception ex) {
			ex.printStackTrace();
			res = new JsonObject();
			res.addProperty("status", "error");
			res.addProperty("data", "error...");
		}
		response.getWriter().append(res.toString());
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JsonObject res = null;

		try {
			Map<String, String> map = getMap(request);
			String f_id = map.get("hiddenFundId").toString();
			String fund_id = map.get("funderId").toString();
			String res_id = map.get("researchId").toString();
			float funded_amount = Float.parseFloat(map.get("fundedAmount").toString());
			String credit_c_no = map.get("creditCardNo").toString();
			
			System.out.println(f_id+" "+fund_id+" "+res_id+" "+funded_amount+" "+credit_c_no);

			JsonObject updateRes = fundObj.updateFund(f_id, fund_id ,res_id,funded_amount, credit_c_no);
			System.out.println("res= " + updateRes.toString());

			if (! updateRes.get("STATUS").getAsString().equalsIgnoreCase("SUCCESSFUL")) {
				res = new JsonObject();
				res.addProperty("status", "error");
				res.addProperty("data", "error...");
				response.getWriter().append(res.toString());
				return;
			}

			JsonObject table = getTable(fundObj.readFunds(null));
			res = new JsonObject();
			res.addProperty("status", "success");
			res.addProperty("data", table.get("data").getAsString());

		} catch (Exception ex) {
			ex.printStackTrace();
			res = new JsonObject();
			res.addProperty("status", "error");
			res.addProperty("data", "error...");
		}
		response.getWriter().append(res.toString());
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JsonObject res = null;

		try {
			Map<String, String> map = getMap(request);
			String f_id = map.get("fundId").toString();
			System.out.println(f_id);

			JsonObject deleteRes = fundObj.deleteFund(null, f_id);
			System.out.println("res= " + deleteRes.toString());

			if (! deleteRes.get("STATUS").getAsString().equalsIgnoreCase("SUCCESSFUL")) {
				res = new JsonObject();
				res.addProperty("status", "error");
				res.addProperty("data", "error...");
				response.getWriter().append(res.toString());
				return;
			}

			JsonObject table = getTable(fundObj.readFunds(null));
			res = new JsonObject();
			res.addProperty("status", "success");
			res.addProperty("data", table.get("data").getAsString());

		} catch (Exception ex) {
			ex.printStackTrace();
			res = new JsonObject();
			res.addProperty("status", "error");
			res.addProperty("data", "error...");
		}
		response.getWriter().append(res.toString());
	}
	
	private JsonObject getTable(JsonObject fundList) {
		//generating products table
		JsonObject res;		
		String tableString = "<table class='table table-sm table-striped'>"
				+ "<thead><tr>"
				+ "<th>fund ID</th>"
				+ "<th>Funder ID</th>"
				+ "<th>Research ID</th>"
				+ "<th>Funded Amount</th>"
				+ "<th>Date Funded</th>"
				+ "<th>Service Charge Rate</th>"
				+ "<th>Credit Card Number</th>"
				+ "<th>Update</th>"
				+ "<th>Delete</th>"
				+ "</tr><thead><tbody>";

		if(! fundList.has("funds")) {
			res = new JsonObject();
			res.addProperty("status", "error");
			res.addProperty("data", "error...");
			return res;
		}

		for(JsonElement elem : fundList.get("funds").getAsJsonArray()) {
			JsonObject fund = elem.getAsJsonObject();
			tableString += "<tr><td>"+ fund.get("fund_id").getAsString() +"</td>"
					+ "<td>"+ fund.get("funder_id").getAsString() +"</td>"
					+ "<td>"+ fund.get("research_id").getAsString() +"</td>"
					+ "<td>"+ fund.get("funded_amount").getAsString() +"</td>"
					+ "<td>"+ fund.get("date_funded").getAsString() +"</td>"
					+ "<td>"+ fund.get("service_charge_rate").getAsString() +"</td>"
					+ "<td>"+ fund.get("creditcard_no").getAsString() +"</td>"
					+ "<td><input type='button' class='btn btn-primary btnUpdate' id='btnUpdate' data-productid='"+fund.get("fund_id").getAsString()+"' value='Update'></td>"
					+ "<td><input type='button' class='btn btn-primary btnDelete' id='btnDelete' data-productid='"+fund.get("fund_id").getAsString()+"' value='Delete'</td></tr>";
		}

		tableString += "</tbody></table>";

		res = new JsonObject();
		res.addProperty("status", "success");
		res.addProperty("data", tableString);

		return res;
	}
	
	private static Map<String,String> getMap(HttpServletRequest request)
	{
		Map<String, String> map = new HashMap<String, String>();
		try
		{
			Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
			String queryString = scanner.hasNext() ?
					scanner.useDelimiter("\\A").next() : "";
			scanner.close();
			String[] params = queryString.split("&");
			for (String param : params)
			{
				String[] p = param.split("=");

				//decoding the string before putting into the map to avoid undesired strings
				map.put(p[0], java.net.URLDecoder.decode(p[1], StandardCharsets.UTF_8.name()));
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return map;
	}

}
