<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${webName}-所有寵物</title>

<jsp:include page="${view}/common_dependencies.jsp" />

</head>
<body>

	<jsp:include page="${view}/frontend/component/header.jsp" />

	<main>
		<section class="py-3 text-center container">
			<div class="row py-lg-5">
				<div class="col-lg-6 col-md-8 mx-auto">
					<p class="lead text-muted">
						(以下由ChatGPT產生)<br>加入我們的寵物社交平台，閱讀飼養心得、瀏覽可愛的寵物相片，與其他寵物愛好者分享您的寵物故事和照片。透過按讚、收藏和分享相簿等功能，建立更多寵物社交關係，探索寵物世界！
					</p>

				</div>
			</div>
		</section>

		<div class="album py-5 bg-light">
			<div class="container">
				<div
					class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3 text-center"
					id="petHome">
					<div class="col">
						<div class="card shadow-sm">
							<img src="${root}/assets/no_image.png" class="w-100" />
							<p class="card-text fs-3">寵物名</p>
							<div class="d-flex justify-content-between align-items-center">
								<button type="button"
									class="btn btn-sm btn-outline-secondary m-2">
									<i class="fa-regular fa-thumbs-up"></i>
								</button>
								<div class="m-3">
									<a href="#">主人名</a>
								</div>
							</div>
						</div>
					</div>

					<div class="col">
						<div class="card shadow-sm">
							<img src="${root}/assets/no_image.png" class="w-100" />
							<p class="card-text fs-3">寵物名</p>
							<div class="d-flex justify-content-between align-items-center">
								<button type="button"
									class="btn btn-sm btn-outline-secondary m-2">
									<i class="fa-regular fa-thumbs-up"></i>
								</button>
								<div class="m-3">
									<a href="#">主人名</a>
								</div>
							</div>
						</div>
					</div>

					<div class="col">
						<div class="card shadow-sm">
							<img src="${root}/assets/no_image.png" class="w-100" />
							<p class="card-text fs-3">寵物名</p>
							<div class="d-flex justify-content-between align-items-center">
								<button type="button"
									class="btn btn-sm btn-outline-secondary m-2">
									<i class="fa-solid fa-thumbs-up" style="color: #025cf7;"></i>
								</button>
								<div class="m-3">
									<a href="#">主人名</a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

	</main>

	<jsp:include page="${view}/frontend/component/footer.jsp" />

</body>
</html>