<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>${webName}-新增寵物</title>
  </head>

  <jsp:include page="${view}/common_dependencies.jsp" />

  <body>
    <jsp:include page="${view}/frontend/component/header.jsp" />
    <main class="m-5">
      <div class="container">
        <div class="row">
          <div class="col text-center">
            <form>
              <div class="mb-3">
                <label
                  >寵物名字：<input
                    type="text"
                    name="petName"
                    class="form-control"
                /></label>
              </div>
              <div class="mb-3">
                <label
                  >寵物年齡：<input
                    type="number"
                    name="petAge"
                    class="form-control w-100"
                    min="0"
                /></label>
              </div>
              <div class="mb-3">
                <label
                  >寵物種類：
                  <input
                    class="form-control"
                    list="petTypeList"
                    name="petType" />
                  <datalist id="petTypeList">
                    <option value="dog"></option>
                    <option value="cat"></option>
                    <option value="bird"></option>
                    <option value="fish"></option>
                    <option value="rabbit"></option>
                    <option value="hamster"></option>
                    <option value="turtle"></option></datalist
                ></label>
              </div>
              <div class="mb-1">
                <label
                  >寵物照片：<input
                    type="file"
                    name="petPhoto"
                    class="form-control"
                    id="petPhotoInput"
                /></label>
              </div>
              <div class="mb-3">
                <img
                  src="${root}/assets/no_image.png"
                  id="previewPhotoIMG"
                  class="w-25"
                />
              </div>
              <button class="btn btn-secondary" id="cancelBTN">取消</button>
              <button class="btn btn-primary">新增</button>
            </form>
          </div>
        </div>
      </div>
    </main>
    <jsp:include page="${view}/frontend/component/footer.jsp" />
  </body>

  <script type="text/javascript">
    // 選取元素
    const previewPhotoIMG = document.getElementById("previewPhotoIMG");
    const petPhotoInput = document.getElementById("petPhotoInput");
    const cancelBTN = document.getElementById("cancelBTN");

    // 預覽圖片
    petPhotoInput.addEventListener("change", function () {
      const file = this.files[0];
      if (file == null) {
        previewPhotoIMG.src = "${root}/assets/no_image.png";
        return;
      }
      const tempURL = URL.createObjectURL(file);
      previewPhotoIMG.src = tempURL;
    });

    // 點擊圖片觸發上傳檔案
    previewPhotoIMG.addEventListener("click", function () {
      petPhotoInput.click();
    });

    // 點擊取消按鈕返回上一頁
    cancelBTN.addEventListener("click", function (e) {
      e.preventDefault();
      window.history.back();
    });
  </script>
</html>
