<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${webName}</title>

<jsp:include page="${view}/common_dependencies.jsp" />

<style type="text/css">
.backgroundImg {
	background-image: url('${root}/assets/pet.png');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-position: center;
	background-size: cover;
}
</style>

</head>

<body>

	<jsp:include page="${view}/frontend/component/header.jsp" />

	<main>
		<div
			class="position-relative overflow-hidden p-3 p-md-5 m-md-3 text-center backgroundImg">
			<div class="col-md-5 p-lg-5 mx-auto my-5">
				<h1 class="display-4 fw-normal text-light">寵物論壇</h1>
				<p class="lead fw-normal text-light">我家的貓咪會寫JAVA!!</p>
				<a class="btn btn-warning" href="/PetForum/pets">快來康康!</a>
			</div>
		</div>
	</main>

	<jsp:include page="${view}/frontend/component/footer.jsp" />

</body>

</html>

