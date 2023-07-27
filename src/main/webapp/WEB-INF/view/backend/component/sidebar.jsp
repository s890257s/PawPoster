<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
        <nav
          id="sidebarMenu"
          class="col-md-3 col-lg-2 d-md-block bg-light sidebar collapse"
        >
          <div class="position-sticky pt-3">
            <h6
              class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted"
            >
              <span>會員管理</span>
            </h6>
            <ul class="nav flex-column">
              <li class="nav-item">
                <a class="nav-link"  href="${root}/backend/show_members">
                  查詢所有會員
                </a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="${root}/backend/create_member">
                  新增會員
                </a>
              </li>
            </ul>

            <h6
              class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted"
            >
              <span>寵物管理</span>
            </h6>
            <ul class="nav flex-column mb-2">
              <li class="nav-item">
                <a class="nav-link" href="${root}/backend/show_pets">
                  查詢所有寵物
                </a>
              </li>
   
            </ul>
          </div>
        </nav>
