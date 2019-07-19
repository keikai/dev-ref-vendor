package io.keikai.demo.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.keikai.demo.Configuration;
import io.keikai.demo.app.ManagerApp;

@WebServlet("/portalmanager/*")
public class ManagerAppServlet extends BaseServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(request, resp);
		ManagerApp myCollaborationApp = new ManagerApp(keikaiServerAddress);
		// pass the anchor DOM element id for rendering keikai
		String keikaiJs = myCollaborationApp.getSpreadsheetTableJavaScriptURI("spreadsheet");
		// store as an attribute to be accessed by EL on a JSP
		request.setAttribute(Configuration.KEIKAI_JS, keikaiJs);
		request.getRequestDispatcher("/app/app.jsp").forward(request, resp);
		myCollaborationApp.init(defaultFileFolder);

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
