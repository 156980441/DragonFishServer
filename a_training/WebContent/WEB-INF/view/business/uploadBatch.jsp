<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<!-- use jstl java standard tag library -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${sign eq '1'}">${logImageName};${fileUrl }</c:if>
