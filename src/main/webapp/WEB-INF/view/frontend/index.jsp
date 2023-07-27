<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${webName}</title>

<!-- web icon -->
<link rel="icon" href="/PetForum/assets/favicon.ico">

<!-- bootstrap 5.1.3 CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">

<!-- bootstrap 5.1.3 JS -->
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
	crossorigin="anonymous"></script>

<!-- font awesome 6.4.0 -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"
	integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw=="
	crossorigin="anonymous" referrerpolicy="no-referrer" />

<style type="text/css">
.backgroundImg {
	background-image: url('/PetForum/assets/pet.png');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-position: center;
	background-size: cover;
}
</style>

</head>

<body>

	<header class="p-3 mb-3 border-bottom">
		<div class="container">
			<div
				class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
				<ul
					class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
					<li><a href="/PetForum/" class="nav-link px-2 link-secondary">寵物論壇</a></li>
					<li><a href="/PetForum/pets" class="nav-link px-2 link-dark">萌寵們</a></li>
					<li><a href="/PetForum/profile"
						class="nav-link px-2 link-dark">個人資訊</a></li>
					<li><a href="/PetForum/add_pet"
						class="nav-link px-2 link-dark">新增寵物</a></li>
					<li><a href="/PetForum/backend/index"
						class="nav-link px-2 link-dark">後臺管理</a></li>
				</ul>
				<div class="dropdown text-end">
					<a href="/PetForum/login">
						<button class="btn btn-primary">會員登入</button>
					</a>
				</div>
			</div>
		</div>
	</header>

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

	<footer class="footer mt-5 py-3 bg-black ">
		<div class="container text-center">
			<span class=" text-light">Copyright © 2023 <a
				href="https://github.com/s890257s">Allen</a>. All rights reserved.
			</span>
		</div>
	</footer>
</body>

</html>

