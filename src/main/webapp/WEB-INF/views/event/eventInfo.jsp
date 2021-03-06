<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE htm>
<html>
<head>
<meta charset="UTF-8">
<title>eventInfo</title>
<style type="text/css">
body {
	text-align: center;
}

table {
	display: inline-block;
	text-align: left;
	border-collapse: collapse;
}

td {
padding:15px 15px 15px 15px;
	border: 1px solid black;
}

th {
	border: 1px solid black;
}
</style>
<script type="text/javascript" src="<c:url value="/resources/jquery-2.1.4.min.js"/>"></script>
<script type="text/javascript">
$(function() {
	$('button[name=btn_delete]').on('click', function(evt){
		
          if(confirm("정말삭제하시겠습니까")){
        	  location.href="eventDelete.do?eno=${selectevt.eno}";
          }
            
    });
});

</script>
</head>
<body>
<h2>상세정보페이지</h2>
	<div>
			<table>
				<tr>
					<td>행사번호 :</td>
					<td>${selectevt.eno}</td>
				</tr>
				<tr>
					<td>행사주관단체 :</td>
					<td>${selectevt.eorg}</td>
				</tr>
				<tr>
					<td>행사일 :</td>
					<td>${selectevt.edate}</td>
				</tr>
				<tr>
					<td>행사장소 :</td>
					<td>${selectevt.eplace}</td>
				</tr>
				<tr>
				<tr>
					<td>연락처 :</td>
					<td>${selectevt.phone}</td>
				</tr>
				<tr>

					<td colspan="2">
					<a href="eventEdit.do?eno=${selectevt.eno}"><input type="submit" value="행사수정" /></a> 
					<button type="button" name="btn_delete" value="행사삭제">행사삭제</button>
					</td>
					
				</tr>
				<tr>
					<td colspan="2"><a href="eventList">행사전체리스트 보기</a></td>
				</tr>
			</table>

	</div>
</body>
</html>