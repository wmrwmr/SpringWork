<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@include file="../includes/header.jsp"%>

<div class="row">
	<div class="col-lg-9" style="padding-left: 200px;">
		<h1 class="page-header" >공지사항</h1>
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- /.row -->


<div class="row">
	<div class="col-lg-9" style="padding-left: 200px;">
		<div class="panel panel-default">
			<div class="panel-heading">&nbsp;</div>
			<!-- /.panel-heading -->
			<div class="panel-body">


				<%-- 				<div class="form-group">
					<label>공지사항 번호</label> <input class="form-control" name='nidx'
						value='<c:out value="${board.nidx}"/>' readonly='readonly'>

				</div> --%>
				
				
		

				<div class="form-group" style="display: inline;">

				<h2><c:out value="${board.title }"/></h2> 
					
					
				<div style="color: rgb(0, 0, 0, 0.5); display: inline; float:right; padding-right: 10px;">
				<i class="fa-solid fa-clock"></i> 
				<fmt:formatDate pattern="yyyy-MM-dd" value="${board.regdate}" /></div>
				</div>
				
				<br>
				
				<hr style="border: solid 2px rgb(224,224,224);">
				
				
				
				<div class="form-group">
				    <c:out value="${board.content }" escapeXml="false" />
					
				</div>

				<br>

				<button data-oper='modify' class="btn btn-default">수정/삭제</button>
				<button data-oper='list' class="btn btn-info">목록</button>

				<form id='operForm' action="/bangbang/board/modify" method="get">
					<input type='hidden' id='nidx' name='nidx'
						value='<c:out value="${board.nidx}"/>'> <input
						type='hidden' name='pageNum'
						value='<c:out value="${cri.pageNum}"/>'> <input
						type='hidden' name='amount' value='<c:out value="${cri.amount}"/>'>
					<input type='hidden' name='start'
						value='<c:out value="${cri.start}"/>'>

				</form>




			</div>
		</div>
	</div>
</div>


				


<%@include file="../includes/footer.jsp"%>

<script type="text/javascript">
	$(document).ready(function() {

		var operForm = $("#operForm");

		$("button[data-oper='modify']").on("click", function(e) {

			operForm.attr("action", "/bangbang/board/modify").submit();

		});

		$("button[data-oper='list']").on("click", function(e) {

			operForm.find("#nidx").remove();
			operForm.attr("action", "/bangbang/board/list")
			operForm.submit();

		});

	});
</script>