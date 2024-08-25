<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style>
.rptviewerstyle{position:fixed; top:0px; left:0px; bottom:0px; right:0px; width:100%; height:100%; border:none; margin:0; padding:0; overflow:hidden; z-index:999999;}
</style>
</head>
<body>
<% String url="http://192.168.210.94:9080/bgd/frameset?__report=dpdc/reports/rptOperatorCharge.rptdesign&__showtitle=false&loudong=&danyuan=&nodecode=All,0031,003101,00310102,0031010201,00310101,0031010102,0031010101,0031010103&netname=All,DPDC,MIC,LALBAG,UVS4,AZIMPUR,UVS2,UVS1,UVS3&nodecodec=All,0031,003101,00310102,0031010201,00310101,0031010102,0031010101,0031010103&startDate=2017-08-17%252000:00:00&endDate=2017-08-17%252023:59:59&oprName=&oprGuid=&isOprName=0&isJfMode=0&jfMode=0&isDel=1&tjOprName=&isMeterBaseType=0&meterBaseType=&meterBaseTypeName=&isZoneId=0&zoneId=&zoneIdName=&isPriceNo=0&priceNo=&priceNoName=&drvdb=0&subSys=050031"; %>
<iframe class="rptviewerstyle" src="<%=url%>">
    Your browser doesn't support iframes
</iframe>
</body>
</html>