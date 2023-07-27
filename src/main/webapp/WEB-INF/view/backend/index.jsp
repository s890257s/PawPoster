<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-tw">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>${webName}_後台管理</title>

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
					<h1 class="h2">歡迎使用 寵物論壇_後台管理 系統</h1>
				</div>

				<h3>請點選左側功能選單</h3>

			</main>
		</div>
	</div>
</body>
</html>
