<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<header class="p-3 mb-3 border-bottom">
	<div class="container">
		<div
			class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
			<ul
				class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
				<li><a href="${root}/" class="nav-link px-2 link-secondary">PawPoster</a></li>
				<li><a href="${root}/page/pets" class="nav-link px-2 link-dark">萌寵們</a></li>
				<li><a href="${root}/page/profile"
					class="nav-link px-2 link-dark">個人資訊</a></li>
				<li><a href="${root}/page/pets/create"
					class="nav-link px-2 link-dark">新增寵物</a></li>
			</ul>
			<div class="dropdown text-end">
				<c:if test="${loggedInMember == null }">
					<a href="${root}/page/login">
						<button class="btn btn-primary">會員登入</button>
					</a>
				</c:if>

				<c:if test="${loggedInMember != null }">
					<span> ${loggedInMember.memberName } </span>
					<img src="${loggedInMember.memberPhoto }" width="50px">
					<button class="btn btn-info">登出</button>
				</c:if>
			</div>
		</div>
	</div>
</header>
