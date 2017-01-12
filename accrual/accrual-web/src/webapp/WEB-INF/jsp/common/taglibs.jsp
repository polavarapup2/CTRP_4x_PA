<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%> 
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="accrual" %>
<%@page import="gov.nih.nci.accrual.util.AccrualUtil"%>
<c:set var="interTrial" scope="session" value="<%=AccrualUtil.INTERVENTIONAL%>"/>
<c:set var="nonInterTrial" scope="session" value="<%=AccrualUtil.NONINTERVENTIONAL%>"/>
<c:set var="both" scope="session" value="<%=AccrualUtil.BOTH%>"/>
<c:set var="patientLevel" scope="session" value="<%=AccrualUtil.SUBJECT_LEVEL%>"/>