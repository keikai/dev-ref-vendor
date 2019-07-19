package io.keikai.demo.web;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.keikai.demo.persistence.PersistenceUtil;
import io.keikai.demo.persistence.VendorMap;

@WebServlet("/manager/*")
public class ManagerCollabServlet extends BaseServlet {

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(request, resp);
        String vendorName = request.getParameter("VendorName");
		if(vendorName != null && !"".equals(vendorName)) {
        	PersistenceUtil.addVendor(new VendorMap(vendorName, new HashMap<String, Object>()));
        }
        request.getRequestDispatcher("/app/manager.jsp").forward(request, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	doGet(req, resp);
    }
    
    @Override
    protected void initHeader(HttpServletRequest request) {
        request.setAttribute(ATTR_TITLE, "Manager page");
        request.setAttribute(ATTR_DESCRIPTION, "control users");
    }
}
