<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>뉴스 상세 정보</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"></head>
<body>
<div class="page-main">
	<h2>${newsVO.title}</h2>
	<p>
		뉴스번호 : ${newsVO.num}<br>
		작성자 : ${newsVO.writer}<br>
		이메일 : ${newsVO.email}<br>
		등록일 : ${newsVO.reg_date}
	</p>
	<hr size="1" width="100%" noshade="noshade">
	<div class="align-center">
		<img src="${pageContext.request.contextPath}/upload/${newsVO.filename}"
		                    style="max-width: 500px">
	</div>
	<p>
		${newsVO.article}
	</p>
	<hr size="1" width="100%" noshade="noshade">
	<div class="align-right">
		<input type="button" value="수정"
		       onclick="location.href='modifyForm.do?num=${newsVO.num}'">
		<input type="button" value="삭제"
		       onclick="location.href='deleteForm.do?num=${newsVO.num}'">
		<input type="button" value="목록"
		       onclick="location.href='list.do'">
	</div>
</div>
</body>
</html>