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
							<th scope="col">權限</th>
							<th scope="col">email</th>
							<th scope="col">姓名</th>
							<th scope="col">年齡</th>
							<th scope="col">居住地</th>
							<th scope="col">照片</th>
							<th scope="col">是否啟用</th>
							<th scope="col">刪除</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td>管理員</td>
							<td>Lee@gmail.com</td>
							<td>Lee</td>
							<td>26</td>
							<td>台北市</td>
							<td><img src="${root}/assets/no_image.png"></td>
							<td>
								<button class="btn btn-primary">啟用</button>
							</td>
							<td>
								<button class="btn btn-danger">刪除</button>
							</td>
						</tr>
						<tr>
							<td>2</td>
							<td>會員</td>
							<td>Amy@gmail.com</td>
							<td>Amy</td>
							<td>32</td>
							<td>台北市</td>
							<td><img src="${root}/assets/no_image.png"></td>
							<td>
								<button class="btn btn-warning">禁用</button>
							</td>
							<td>
								<button class="btn btn-danger">刪除</button>
							</td>
						</tr>
					</tbody>
				</table>

			</main>
		</div>
	</div>
</body>
</html>
