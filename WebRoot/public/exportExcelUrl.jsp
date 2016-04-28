<%@ page pageEncoding="UTF-8"%> 
<% 
response.setHeader("Content-Type","application/force-download"); 
response.setHeader("Content-Type","application/vnd.ms-excel"); 
response.setHeader("Content-Disposition","attachment;filename=exportExcel.xls"); 
out.print(request.getParameter("exportContent")); 
%> 