<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@include file="../includes/header.jsp"%>

<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">1:1 문의 답변</h1>
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- /.row -->

<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">&nbsp;</div>
			<!-- /.panel-heading -->
			<div class="panel-body">








				<form role="form" action="/bangbang/paq/replyRegister" method="post">


					<div class="form-group">
						<input type='hidden' class="form-control" name='pqidx'
							value='<c:out value="${paq.pqidx}"/>' readonly='readonly'>

					</div>
					
					

					<div class="form-group">
						<label>제목</label> <input class="form-control" name='title'
							value='<c:out value="${paq.title }"/>' readonly='readonly'>
					</div>
					
					

					<div class="form-group">
						<label>내용</label> <input class="form-control" name='content'
							value='<c:out value="${paq.content }"/>' readonly='readonly'>
					</div>


					<th>답변</th>
					<td><textarea id="content" name="reply">${paq.reply}</textarea> <script
							type="text/javascript">
						// 글쓰기 editor 및 사진 업로드 기능
						CKEDITOR.replace('content', {
							filebrowserUploadUrl : '/bangbang/paq/imageUpload'
						});
					</script></td> <br>




					<button type="submit" class="btn btn-default">등록</button>
					<button type="reset" class="btn btn-default">초기화</button>

				</form>

			</div>
			<!-- /.panel-body -->
		</div>
		<!-- /.panel -->
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- /.row -->

<%@include file="../includes/footer.jsp"%>



