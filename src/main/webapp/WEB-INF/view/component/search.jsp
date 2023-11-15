<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
								<input type="number" name="minAge" class="form-control " min="0"
									max="100" />
							</div>
							<div class="col-1 text-center">～</div>
							<div class="col">
								<input type="number" name="maxAge" class="form-control" min="0"
									max="100" />
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