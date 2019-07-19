<%@page import="java.util.*"%>
<%@page import="io.keikai.demo.persistence.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Keikai manager page</title>
    <link href="https://fonts.googleapis.com/css?family=Lato&display=swap" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <style>
    	.hiddenblock{
    		display: none;
    	}
    </style>
   	<%@ include file="../commonHead.jsp" %>
</head>
<body>
	<script>
	
		function copyToClipboard(value) {
			var $temp = $("<input>");
			$("body").append($temp);
			$temp.val(value).select();
			document.execCommand("copy");
			$temp.remove();
		}
	</script>
    <div class="managerpanel">
	    <form action="#" method="POST">
	  		 <table>
	  		 	  <colgroup>
				    <col width="110px">
				    <col width="110px">
				    <col width="110px">
				    <col width="110px">
				    <col width="110px">
				    <col width="110px">
				  </colgroup>
	  		 	<tr class="controlpaneltitle">
	  		 		<td colspan="3">Vendor's Management Panel</td>
	  		 		<td colspan="3">
	 	 		 		<input type="submit" value="Vendor Summary" onClick="window.open('<%=request.getContextPath()%>/portalmanager', '_blank');"></input>
	    			</td>
	   			</tr>
	   			<tr class="separator"><td colspan="6"><div></div></td></tr>
	    		<tr id="addVendorPanel">
		    			<td colspan="3">
							<input class="txinput" placeholder="Vendor Name" name="VendorName"/>
						</td>
						<td colspan="3">
							<input type="submit" value="Generate Link"></input>
						</td>
				</tr>
				<%
				  	VendorMap[] allVendors = PersistenceUtil.getAllVendors();
					for (int i = 0; i < allVendors.length; i++) {
						VendorMap vendor = allVendors[i];
						String linkVal = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/client/?url=" + vendor.getInternalName();
				%>
				<tr class="vendorline">
					<td class="vendorinfo" colspan="2">
						<%=i+1%>. <%=vendor.getInternalName()%>
					</td>
					<td class="vendorbuttons" colspan="2">
						<input type="submit" value="Copy Link" onClick="copyToClipboard('<%=linkVal%>')"></input>
					</td>
					<td colspan="2" class="vendorbuttons">
						<input type="submit" value="Go to Sheet" onClick="window.open('<%=linkVal%>', '_blank');"></input>
					</td>
				</tr>
				<%
				}
				%>
			</table>
		</form>
    </div>
</body>
</html>