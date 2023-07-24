<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<html lang="zh-tw">
  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>${webName}_後台管理</title>

    <style type="text/css">
      img {
        width: 250px;
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
            class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom"
          >
            <h1 class="h2">新增會員</h1>
            <button id="demoBTN" class="btn btn-warning">DEMO用一鍵輸入</button>
          </div>

          <form action="#" method="POST" enctype="multipart/form-data">
            <div class="mb-3">
              <label for="email" class="form-label">email</label>
              <input
                type="email"
                class="form-control"
                id="email"
                name="email"
              />
            </div>

            <div class="mb-3">
              <label for="password" class="form-label">密碼</label>
              <input
                type="text"
                class="form-control"
                id="password"
                name="password"
              />
            </div>

            <div class="mb-3">
              <label for="mName" class="form-label">姓名</label>
              <input type="text" class="form-control" id="mName" name="mName" />
            </div>
            <div class="mb-3">
              <label for="mAge" class="form-label">年齡</label>
              <input
                type="number"
                class="form-control"
                id="mAge"
                name="mAge"
                max="100"
                min="1"
              />
            </div>

            <div class="mb-3">
              <label for="address" class="form-label"> 地址 </label
              ><select class="form-select" name="address" id="address">
                <option>台北市</option>
                <option>新北市</option>
                <option>桃園市</option>
                <option>台中市</option>
                <option>台南市</option>
                <option>高雄市</option>
              </select>
            </div>

            <div class="mb-3">
              <label
                >權限
                <select class="form-select" name="level">
                  <option value="會員">會員</option>
                  <option value="管理員">管理員</option>
                </select>
              </label>
            </div>

            <div class="mb-3">
              <label for="mPhotoInput" class="form-label">照片</label>
              <input class="form-control" type="file" id="mPhotoInput" />
            </div>

            <div class="mb-3">
              <img
                src="${root}/assets/no_image.png"
                class="rounded mx-auto d-block"
                id="previewPhotoIMG"
              />
            </div>
            <button type="submit" class="btn btn-primary">新增</button>
          </form>
        </main>
      </div>
    </div>
  </body>

  <script>
    const demoBTN = document.getElementById("demoBTN");
    const mPhotoInput = document.getElementById("mPhotoInput");
    const previewPhotoIMG = document.getElementById("previewPhotoIMG");

    demoBTN.addEventListener("click", function () {
      document.getElementById("email").value = "Kate@gamil.com";
      document.getElementById("password").value = "1234";
      document.getElementById("mName").value = "Kate";
      document.getElementById("mAge").value = "20";
    });

    // 預覽圖片
    mPhotoInput.addEventListener("change", function () {
      const file = this.files[0];

      if (!file) {
        previewPhotoIMG.src = "${root}/assets/no_image.png";
        return;
      }

      const tempURL = URL.createObjectURL(file);
      previewPhotoIMG.src = tempURL;
    });

    // 點擊圖片觸發上傳檔案
    previewPhotoIMG.addEventListener("click", function () {
      mPhotoInput.click();
    });
  </script>
</html>
