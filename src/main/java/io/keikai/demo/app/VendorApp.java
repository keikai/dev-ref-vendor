package io.keikai.demo.app;

import static io.keikai.demo.Configuration.getUriForBrowser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Set;

import io.keikai.client.api.AbortedException;
import io.keikai.client.api.DuplicateNameException;
import io.keikai.client.api.Keikai;
import io.keikai.client.api.Range;
import io.keikai.client.api.Spreadsheet;
import io.keikai.client.api.Workbook;
import io.keikai.client.api.ui.UIActivity;
import io.keikai.demo.persistence.PersistenceUtil;
import io.keikai.demo.persistence.VendorMap;

/**
 * enable collaboration edit with the same application ID.
 */
public class VendorApp{
	private Spreadsheet spreadsheetTable;
	private VendorMap vendor;

	static private String tableXlsx = "app-0702.xlsx";

	
	/**
	 * pass the anchor DOM element ID for rendering a Keikai spreadsheet
	 *
	 * @param elementId
	 * @return
	 */
	public String getSpreadsheetTableJavaScriptURI(String elementId) {
		return getUriForBrowser(spreadsheetTable, elementId);
	}

	public VendorApp(String keikaiServerAddress, VendorMap vendor) {
		// all clients that connect with the same application id share the same view.
		// we just use file name as app ID
		this.vendor = vendor;
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
			Workbook workbook = spreadsheetTable.imports(vendor.getInternalName(), new File(defaultFileFolder, tableXlsx));
			spreadsheetTable.setActiveWorkbook(workbook.getName());
			workbook.setActiveWorksheet(0);
			workbook.getWorksheet().getButton("SubmitButton").addAction(buttonShapeMouseEvent -> {
				workbookDataToVendor();
			});
			vendorDataToWorkbook();
		} catch (FileNotFoundException | DuplicateNameException | AbortedException e) {
			e.printStackTrace();
		}
	}
	
	private void vendorDataToWorkbook() {
		for (Map.Entry<String, Object> entry : vendor.getVendorData().entrySet()) {
			Range rangeByName = spreadsheetTable.getRangeByName(entry.getKey());
			if(rangeByName != null) {
				rangeByName.setValue(entry.getValue());
			}			
		}
	}
	
	private void workbookDataToVendor() {
		System.out.println("updating "+ vendor.getInternalName() + " to Persistence");
		for (String name: ManagerApp.DISPLAY_COLUMN_NAMES.keySet()) {
			Object value = spreadsheetTable.getRangeByName(name).getValue();
			System.out.println(name + " : " + value);
			if (value!=null && !"".equals(value)) {
				vendor.getVendorData().put(name, value);
			}else {
				vendor.getVendorData().remove(name);
			}
		}
		PersistenceUtil.addVendor(vendor);
		System.out.println("updated "+ vendor.getInternalName() + " to Persistence");
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
