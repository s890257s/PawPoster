<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${webName}</title>

<jsp:include page="${component}/common_imports.jsp" />

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
	<!-- header -->
	<header class="p-3 mb-3 border-bottom">
		<div class="container">
			<div
				class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
				<ul
					class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
					<li><a href="${root}/" class="nav-link px-2 link-secondary">PawPoster</a></li>
					<li><a href="${root}/page/pets"
						class="nav-link px-2 link-dark">萌寵們</a></li>
					<li><a href="${root}/page/profile"
						class="nav-link px-2 link-dark">個人資訊</a></li>
					<li><a href="${root}/page/pets/create"
						class="nav-link px-2 link-dark">新增寵物</a></li>
				</ul>
				<div class="dropdown text-end">
						<a href="${root}/page/login">
							<button class="btn btn-primary">會員登入</button>
						</a>

						<span> 使用者 </span>
						<img src="${root}/assets/no_image.png" width="50px">
						<button class="btn btn-info">登出</button>
				</div>
			</div>
		</div>
	</header>

	<!-- main -->
	<main>
		<div
			class="position-relative overflow-hidden p-3 p-md-5 m-md-3 text-center backgroundImg">
			<div class="col-md-5 p-lg-5 mx-auto my-5">
				<h1 class="display-4 fw-normal text-light">PawPoster</h1>
				<p class="lead fw-normal text-light ">我家的貓咪會寫JAVA!!</p>
			</div>
		</div>
	</main>

	<!-- footer -->
	<footer class="footer mt-5 py-3 bg-black ">
		<div class="container text-center">
			<span class=" text-light">Copyright © 2023 <a
				href="https://github.com/s890257s">Allen Hong</a>. All rights
				reserved.
			</span>
		</div>
	</footer>
</body>

</html>

