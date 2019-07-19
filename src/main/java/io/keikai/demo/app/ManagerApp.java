package io.keikai.demo.app;

import static io.keikai.demo.Configuration.getUriForBrowser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import io.keikai.client.api.AbortedException;
import io.keikai.client.api.DuplicateNameException;
import io.keikai.client.api.Keikai;
import io.keikai.client.api.Range;
import io.keikai.client.api.Range.DeleteShiftDirection;
import io.keikai.client.api.Range.InsertFormatOrigin;
import io.keikai.client.api.Range.InsertShiftDirection;
import io.keikai.client.api.Spreadsheet;
import io.keikai.client.api.Workbook;
import io.keikai.client.api.Worksheet;
import io.keikai.client.api.ctrl.Checkbox;
import io.keikai.client.api.ui.UIActivity;
import io.keikai.demo.persistence.PersistenceUtil;
import io.keikai.demo.persistence.VendorMap;

/**
 * enable collaboration edit with the same application ID.
 */
public class ManagerApp {
	private Spreadsheet spreadsheetTable;
	static private String tableXlsx = "managerapp.xlsx";

	public final static Map<String, String> DISPLAY_COLUMN_NAMES;
	static {
		DISPLAY_COLUMN_NAMES = new HashMap<String, String>();
		DISPLAY_COLUMN_NAMES.put("businessCategory1", "Business Category 1");
		DISPLAY_COLUMN_NAMES.put("businessCategory2", "Business Category2");
		DISPLAY_COLUMN_NAMES.put("businessProductsAndServices", "Business Products And Services");
		DISPLAY_COLUMN_NAMES.put("companyAdress", "Company Adress");
		DISPLAY_COLUMN_NAMES.put("companyName", "Company Name");
		DISPLAY_COLUMN_NAMES.put("contact1Designation", "Contact 1 Designation");
		DISPLAY_COLUMN_NAMES.put("contact1Name", "Contact 1 Name");
		DISPLAY_COLUMN_NAMES.put("contact1phone", "Contact 1 Phone");
		DISPLAY_COLUMN_NAMES.put("contact2Designation", "Contact 2 Designation");
		DISPLAY_COLUMN_NAMES.put("contact2Name", "Contact 2 Name");
		DISPLAY_COLUMN_NAMES.put("contact2phone", "Contact 2 phone");
		DISPLAY_COLUMN_NAMES.put("contact3Designation", "Contact 3 Designation");
		DISPLAY_COLUMN_NAMES.put("contact3Name", "Contact 3 Name");
		DISPLAY_COLUMN_NAMES.put("contact3phone", "Contact 3 phone");
		DISPLAY_COLUMN_NAMES.put("country", "country");
		DISPLAY_COLUMN_NAMES.put("email", "email");
		DISPLAY_COLUMN_NAMES.put("financialDetailsYear1production", "Financial Details Year 1 production");
		DISPLAY_COLUMN_NAMES.put("financialDetailsYear1turnover", "Financial Details Year 1 turnover");
		DISPLAY_COLUMN_NAMES.put("financialDetailsYear1year", "Financial Details Year 1 year");
		DISPLAY_COLUMN_NAMES.put("financialDetailsYear2production", "Financial Details Year 2 production");
		DISPLAY_COLUMN_NAMES.put("financialDetailsYear2turnover", "Financial Details Year 2 turnover");
		DISPLAY_COLUMN_NAMES.put("financialDetailsYear2year", "Financial Details Year 2 year");
		DISPLAY_COLUMN_NAMES.put("financialDetailsYear3production", "Financial Details Year 3 production");
		DISPLAY_COLUMN_NAMES.put("financialDetailsYear3turnover", "Financial Details Year 3 turnover");
		DISPLAY_COLUMN_NAMES.put("financialDetailsYear3year", "Financial Details Year 3 year");
		DISPLAY_COLUMN_NAMES.put("legalStructure", "Legal Structure");
		DISPLAY_COLUMN_NAMES.put("phone", "phone");
		DISPLAY_COLUMN_NAMES.put("signatureDate", "Signature Date");
		DISPLAY_COLUMN_NAMES.put("signatureDesignation", "Signature Designation");
		DISPLAY_COLUMN_NAMES.put("signaturePrintName", "Signature PrintName");
		DISPLAY_COLUMN_NAMES.put("signatureSign", "Signature Sign");
		DISPLAY_COLUMN_NAMES.put("website", "website");
	}

	/**
	 * pass the anchor DOM element ID for rendering a Keikai spreadsheet
	 *
	 * @param elementId
	 * @return
	 */
	public String getSpreadsheetTableJavaScriptURI(String elementId) {
		return getUriForBrowser(spreadsheetTable, elementId);
	}

	public ManagerApp(String keikaiServerAddress) {
		// all clients that connect with the same application id share the same view.
		// we just use file name as app ID
		spreadsheetTable = Keikai.newClient(keikaiServerAddress);
		spreadsheetTable.setUIActivityCallback(new UIActivity() {
			public void onConnect() {
				setupUi();
			}

			public void onDisconnect() {
				spreadsheetTable.close();
			}
		});
	}

	public void init(File defaultFileFolder) {
		try {
			Workbook workbook = spreadsheetTable.imports("managerapp", new File(defaultFileFolder, tableXlsx));
			spreadsheetTable.setActiveWorkbook(workbook.getName());
			workbook.setActiveWorksheet(0);
		} catch (FileNotFoundException | DuplicateNameException | AbortedException e) {
			e.printStackTrace(); 
		}
		vendorDataToWorkbook();
	}

	private void vendorDataToWorkbook() {
		VendorMap[] allVendors = PersistenceUtil.getAllVendors();
		Worksheet worksheet = spreadsheetTable.getWorksheet();
		Range firstrow = worksheet.getRange("1:1");
		Range displayTable = worksheet.getRange("2:2");
		String[] rangeNames = DISPLAY_COLUMN_NAMES.keySet().toArray(new String[] {});
		for (int i = 0; i < rangeNames.length; i++) {
			firstrow.getCell(firstrow.getRow(), i).setValue(DISPLAY_COLUMN_NAMES.get(rangeNames[i]));
		}
		int currentRow = 0;
		for (VendorMap vendor : allVendors) {
			displayTable.getRows(currentRow).insert(InsertShiftDirection.ShiftDown,
					InsertFormatOrigin.LeftOrAbove);
			for (int i = 0; i < rangeNames.length; i++) {
				displayTable.getCell(currentRow+1, i).setValue(vendor.getVendorData().get(rangeNames[i]));
			}
			currentRow++;
		}
		displayTable.getRows(currentRow).delete(DeleteShiftDirection.ShiftUp);
	}

	private void setupUi() {
		spreadsheetTable.getUIService().showToolbar(true);
		spreadsheetTable.getUIService().showSheetTabs(true);
		spreadsheetTable.getUIService().showContextMenu(true);
		spreadsheetTable.getUIService().showFormulaBar(true);
		spreadsheetTable.getUIService().showSheetControls(true);
		spreadsheetTable.getUIService().setProtectedSheetWarningEnabled(true);
	}

}
