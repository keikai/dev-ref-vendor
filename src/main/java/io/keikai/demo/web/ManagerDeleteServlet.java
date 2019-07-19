package io.keikai.demo.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.keikai.demo.persistence.PersistenceUtil;

@WebServlet("/delete/*")
public class ManagerDeleteServlet extends BaseServlet {

	public static final String COLLAB_FORM = "CollabForm";

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(request, resp);
        String vendorName = request.getParameter("VendorName");
		if(vendorName != null && !"".equals(vendorName)) {
        	PersistenceUtil.deleteVendor(vendorName);
        }
        resp.sendRedirect(request.getContextPath() + "/manager/");
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
