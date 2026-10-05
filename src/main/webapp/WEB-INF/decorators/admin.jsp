<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>
        <sitemesh:write property="title"/>
        - Admin
    </title>

    <link
        href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
        rel="stylesheet">

    <link
        href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css"
        rel="stylesheet">

    <style>
        body {
            background-color: #f4f6f9;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }

        .admin-navbar {
            background: linear-gradient(
                90deg,
                #212529,
                #343a40
            );
        }

        .navbar-brand {
            font-weight: 700;
        }

        .admin-content {
            flex: 1;
            padding-top: 30px;
            padding-bottom: 40px;
        }

        .admin-card {
            border: none;
            border-radius: 12px;
            box-shadow: 0 4px 16px
                        rgba(0, 0, 0, 0.08);
        }

        .table thead th {
            white-space: nowrap;
        }

        .thumbnail-admin {
            width: 80px;
            height: 55px;
            object-fit: cover;
            border-radius: 6px;
            background-color: #e9ecef;
        }

        footer {
            background-color: #212529;
            color: #ffffff;
            padding: 18px 0;
        }
    </style>

    <sitemesh:write property="head"/>
</head>

<body>

<nav class="navbar navbar-expand-lg
            navbar-dark admin-navbar shadow">

    <div class="container-fluid px-4">

        <a class="navbar-brand"
           href="${pageContext.request.contextPath}/admin/users">

            <i class="bi bi-speedometer2"></i>
            Admin Dashboard
        </a>

        <button class="navbar-toggler"
                type="button"
                data-bs-toggle="collapse"
                data-bs-target="#adminNavbar">

            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse"
             id="adminNavbar">

            <ul class="navbar-nav me-auto">

                <li class="nav-item">

                    <a class="nav-link"
                       href="${pageContext.request.contextPath}/home">

                        <i class="bi bi-house-door"></i>
                        Trang chủ
                    </a>

                </li>

                <li class="nav-item">

                    <a class="nav-link"
                       href="${pageContext.request.contextPath}/admin/users">

                        <i class="bi bi-people"></i>
                        Người dùng
                    </a>

                </li>

                <li class="nav-item">

                    <a class="nav-link"
                       href="${pageContext.request.contextPath}/admin/categories">

                        <i class="bi bi-tags"></i>
                        Thể loại
                    </a>

                </li>

                <li class="nav-item">

                    <a class="nav-link"
                       href="${pageContext.request.contextPath}/admin/videos">

                        <i class="bi bi-camera-video"></i>
                        Video
                    </a>

                </li>

            </ul>

            <ul class="navbar-nav">

                <li class="nav-item">

                    <span class="nav-link text-warning">

                        <i class="bi bi-person-circle"></i>

                        <c:out value="${sessionScope.currentUser.fullname}"/>
                    </span>

                </li>

                <li class="nav-item">

                    <a class="nav-link"
                       href="${pageContext.request.contextPath}/logout">

                        <i class="bi bi-box-arrow-right"></i>
                        Đăng xuất
                    </a>

                </li>

            </ul>

        </div>
    </div>
</nav>

<main class="admin-content">
    <div class="container-fluid px-4">
        <sitemesh:write property="body"/>
    </div>
</main>

<footer>
    <div class="container text-center">

        <div class="fw-semibold">
            Hệ thống quản trị Video Website
        </div>

        <div>
            TranAnhTuan - 24133073 - Bài thi 04
        </div>

    </div>
</footer>

<script
    src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js">
</script>

</body>
</html>