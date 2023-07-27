<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${webName}-登入</title>

<jsp:include page="${view}/common_dependencies.jsp" />
<body class="vh-100">

	<main class="form-signin m-5 text-center ">
		<form action="${root}/Login.do" method="post">
			<h1 class="h3 mb-3 fw-normal">會員登入</h1>
			<div class="form-floating">
				<input type="text" class="form-control" placeholder="#"
					id="emailInput" name="email" required> <label>信箱</label>
			</div>
			<div class="form-floating">
				<input type="text" class="form-control" id="passwordInput"
					placeholder="Password" name="password" required> <label>密碼</label>
			</div>
			<div class="checkbox my-3">
				<label> <input type="checkbox" name="remeberMe"> 記住我
				</label>
			</div>
			<div class="mb-2">
				<button class="w-25 btn btn-lg btn-primary">登入</button>
			</div>
			<div>

				<button class="w-25 btn btn-lg btn-outline-dark" id="cancelBTN">取消</button>

			</div>
			<div class="mt-5" style="color: red">${message}</div>
		</form>
	</main>

	<div class="text-center m-5">
		快速登入:
		<button class="btn btn-outline-info fastLogin">Lee(管理員)</button>
		<button class="btn btn-outline-info fastLogin">Amy</button>
		<button class="btn btn-outline-info fastLogin">Nick</button>
		<button class="btn btn-outline-info fastLogin">Elisa</button>
		<button class="btn btn-outline-info fastLogin">Bob</button>
	</div>

</body>

<script type="text/javascript">
	const fastLoginBTNs = document.querySelectorAll(".fastLogin");
	const emailInput = document.querySelector("#emailInput");
	const passwordInput = document.querySelector("#passwordInput");
	const cancelBTN = document.querySelector("#cancelBTN");

	//快速登入
	fastLoginBTNs.forEach(button=>{
		button.addEventListener("click",function(){
			if(this.innerHTML=="Lee(管理員)"){
				emailInput.value="Lee@gmail.com";
			}else{
				emailInput.value=this.innerHTML+"@gmail.com";
			}
			passwordInput.value=1234;
		})
	})

	cancelBTN.addEventListener("click",function(e){
		e.preventDefault();
		window.location.href="${root}/";
	}) 
	
	
    // 如果是通過 "返回上一頁" 的方式進入此頁面，且cookie中有登入的flag，則使用 location.replace() 將頁面導回首頁。
    if (performance.navigation.type == 2 && document.cookie) {
        location.replace("index");
    }
	
</script>

</html>