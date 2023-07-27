<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<header class="p-3 bg-dark text-white">
	<div class="container-fulid">
		<div
			class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
			<a href="#"
				class="d-flex align-items-center mb-2 mb-lg-0 text-white text-decoration-none">
				<img src="${root}/assets/favicon.ico" style="width: 40px">
			</a>

			<ul
				class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
				<li><a href="#" class="nav-link px-2 text-white fs-3">${webName}後台</a></li>
			</ul>


			<div class="text-end">

				<a href="${root}/index" style="text-decoration: none;">
					<button type="button" class="btn btn-warning">回前臺</button>
				</a>
				<button type="button" class="btn btn-danger">登出</button>
			</div>
		</div>
	</div>
</header>