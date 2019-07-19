package io.keikai.demo.web;

import io.keikai.demo.Configuration;
import io.keikai.demo.app.*;
import io.keikai.demo.persistence.PersistenceUtil;
import io.keikai.demo.persistence.VendorMap;
import io.keikai.demo.persistence.VendorProfile;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/client/*")
public class ClientCollabServlet extends BaseServlet {

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(request, resp);
        
        String url = request.getParameter("url");
        if(!(url == null) && !("".equals(url))) {
        	VendorMap vendor = PersistenceUtil.getVendorByName(url);
        	if(vendor != null) {
				VendorApp myCollaborationApp = new VendorApp(keikaiServerAddress, vendor);
				// pass the anchor DOM element id for rendering keikai
				String keikaiJs = myCollaborationApp.getSpreadsheetTableJavaScriptURI("spreadsheet");
				// store as an attribute to be accessed by EL on a JSP
				request.setAttribute(Configuration.KEIKAI_JS, keikaiJs);
				request.getRequestDispatcher("/app/app.jsp").forward(request, resp);
				myCollaborationApp.init(defaultFileFolder);
        	}
		}else {
			resp.sendError(501, "You are not authorized to access this service. Please check your url.");
		}
        
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	doGet(req, resp);
    }
    
    @Override
    protected void initHeader(HttpServletRequest request) {
        request.setAttribute(ATTR_TITLE, "Vendor Editing Page");
        request.setAttribute(ATTR_DESCRIPTION, "Generate a page from template for each vendor.");
    }
}
