<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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

		<div class="d-flex justify-content-center m-3">
			<div class="accordion w-50">
				<div class="accordion-item">
					<h2 class="accordion-header">
						<button class="accordion-button collapsed" type="button"
							data-bs-toggle="collapse" data-bs-target="#MultipleCriteriaQuery"
							aria-expanded="false">多條件搜尋</button>
					</h2>
					<div class="accordion-collapse collapse" id="MultipleCriteriaQuery">

						<form action="${root}/QueryPet.do" method="GET">
							<div class="accordion-body">
								<div class="mb-3 row align-items-center justify-content-center">
									<div class="col-2">
										<label>寵物年齡：</label>
									</div>
									<div class="col">
										<input type="number" name="minAge" class="form-control "
											min="0" max="100" />
									</div>
									<div class="col-1 text-center">～</div>
									<div class="col">
										<input type="number" name="maxAge" class="form-control"
											min="0" max="100" />
									</div>
								</div>
								<div class="mb-3 row align-items-center">
									<div class="col-2">
										<label>寵物種類：</label>
									</div>
									<div class="col">
										<input class="form-control" list="petTypeList" name="type" />
										<datalist id="petTypeList">
											<option value="dog" />
											<option value="cat" />
											<option value="bird" />
											<option value="fish" />
											<option value="rabbit" />
											<option value="hamster" />
											<option value="turtle" />
										</datalist>
									</div>
								</div>

								<div class="row">
									<div class="col-10"></div>
									<div class="col-2">
										<button class="btn btn-primary">送出</button>
									</div>
								</div>

							</div>

						</form>
					</div>
				</div>
			</div>
		</div>

		<c:if test="${pList.isEmpty()}">
			<h1 class="text-center fs-1">查無寵物</h1>
		</c:if>

		<c:if test="${!pList.isEmpty()}">
			<div class="album py-5 bg-light">
				<div class="container">
					<div
						class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3 text-center"
						id="petHome">




						<c:forEach items="${pList}" var="p">
							<div class="col">
								<div class="card shadow-sm">
									<img src="${root}/GetPetPhoto.do?pID=${p.pID}" class="w-100" />
									<p class="card-text fs-3">${p.pName}</p>
									<div class="d-flex justify-content-between align-items-center">
										<button type="button"
											class="btn btn-sm btn-outline-secondary m-2">
											<i class="fa-regular fa-thumbs-up"></i>
										</button>

										<div class="m-3">
											<a href="${root}/profile?mID=${p.member.mID}">${p.member.mName }</a>
										</div>
									</div>
								</div>
							</div>
						</c:forEach>

					</div>
				</div>
			</div>
		</c:if>
	</main>

	<jsp:include page="${view}/frontend/component/footer.jsp" />

</body>
</html>