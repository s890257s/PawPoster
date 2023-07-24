<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html lang="zh-tw">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>${webName}_後台管理</title>

<style type="text/css">
img {
	width: 100px
}
</style>

<jsp:include page="${view}/common_dependencies.jsp" />

</head>
<body>
	<jsp:include page="${view}/backend/component/topbar.jsp" />

	<div class="container-fluid">
		<div class="row">

			<jsp:include page="${view}/backend/component/sidebar.jsp" />


			<main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
				<div
					class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
					<h1 class="h2">會員清單</h1>
				</div>

				<table class="table align-middle text-center">
					<thead>
						<tr>
							<th scope="col">ID</th>
							<th scope="col">種類</th>
							<th scope="col">名字</th>
							<th scope="col">年齡</th>
							<th scope="col">照片</th>
							<th scope="col">主人姓名</th>
							<th scope="col">主人照片</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td>Dog</td>
							<td>Cookie</td>
							<td>3</td>
							<td><img src="${root}/assets/no_image.png"></td>
							<td>Lee</td>
							<td><img src="${root}/assets/no_image.png"></td>
						</tr>
						<tr>
							<td>2</td>
							<td>Dog</td>
							<td>Max</td>
							<td>2</td>
							<td><img src="${root}/assets/no_image.png"></td>
							<td>Lee</td>
							<td><img src="${root}/assets/no_image.png"></td>
						</tr>
					</tbody>
				</table>

			</main>
		</div>
	</div>
</body>
</html>
