<%@ page import="org.displaytag.tags.TableTagParameters,org.displaytag.util.ParamEncoder"%>
<jsp:scriptlet>
String urlParams = "?";
ParamEncoder encoder  = new ParamEncoder("row");
if(request.getParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_PAGE)) != null) {
    urlParams = urlParams + encoder.encodeParameterName(TableTagParameters.PARAMETER_PAGE) + "=" 
    + request.getParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_PAGE));
} if(request.getParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_SORT)) != null) {
    urlParams = urlParams + "&" + encoder.encodeParameterName(TableTagParameters.PARAMETER_SORT) + "=" 
    + request.getParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_SORT));
} if(request.getParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_ORDER)) != null) {
    urlParams = urlParams + "&" + encoder.encodeParameterName(TableTagParameters.PARAMETER_ORDER) + "=" 
    + request.getParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_ORDER));
}
</jsp:scriptlet>