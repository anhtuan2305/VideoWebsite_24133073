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
        - Video Website
    </title>

    <link
        href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
        rel="stylesheet">

    <link
        href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css"
        rel="stylesheet">

    <style>
        body {
            background-color: #f5f7fa;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }

        .navbar-brand {
            font-weight: 700;
            letter-spacing: 0.5px;
        }

        .main-content {
            flex: 1;
            padding-top: 30px;
            padding-bottom: 40px;
        }

        .video-card {
            height: 100%;
            border: none;
            border-radius: 12px;
            overflow: hidden;
            transition: transform 0.2s ease,
                        box-shadow 0.2s ease;
        }

        .video-card:hover {
            transform: translateY(-4px);
            box-shadow: 0 10px 25px
                        rgba(0, 0, 0, 0.12);
        }

        .video-poster {
            width: 100%;
            height: 190px;
            object-fit: cover;
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
            navbar-dark bg-dark shadow-sm">

    <div class="container">

        <a class="navbar-brand"
           href="${pageContext.request.contextPath}/home">

            <i class="bi bi-play-btn-fill"></i>
            Video Website
        </a>

        <button class="navbar-toggler"
                type="button"
                data-bs-toggle="collapse"
                data-bs-target="#userNavbar">

            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse"
             id="userNavbar">

            <ul class="navbar-nav me-auto">

                <li class="nav-item">
                    <a class="nav-link"
                       href="${pageContext.request.contextPath}/home">

                        <i class="bi bi-house-door"></i>
                        Trang chủ
                    </a>
                </li>

                <c:if test="${not empty sessionScope.currentUser
                            and sessionScope.currentUser.admin}">

                    <li class="nav-item">
                        <a class="nav-link"
                           href="${pageContext.request.contextPath}/admin/users">

                            <i class="bi bi-speedometer2"></i>
                            Quản trị
                        </a>
                    </li>

                </c:if>

            </ul>

            <ul class="navbar-nav">

                <c:choose>

                    <c:when test="${empty sessionScope.currentUser}">

                        <li class="nav-item">
                            <a class="nav-link"
                               href="${pageContext.request.contextPath}/login">

                                <i class="bi bi-box-arrow-in-right"></i>
                                Đăng nhập
                            </a>
                        </li>

                        <li class="nav-item">
                            <a class="nav-link"
                               href="${pageContext.request.contextPath}/register">

                                <i class="bi bi-person-plus"></i>
                                Đăng ký
                            </a>
                        </li>

                    </c:when>

                    <c:otherwise>

                        <!-- Nút Giỏ hàng -->
                        <li class="nav-item">
                            <a class="nav-link"
                               href="${pageContext.request.contextPath}/cart">
                                <i class="bi bi-cart3"></i>
                                Giỏ hàng
                                <c:if test="${not empty sessionScope.cart}">
                                    <span class="badge bg-danger rounded-pill ms-1">
                                        ${sessionScope.cart.size()}
                                    </span>
                                </c:if>
                            </a>
                        </li>

                        <!-- Nút Lịch sử đơn hàng -->
                        <li class="nav-item me-2">
                            <a class="nav-link"
                               href="${pageContext.request.contextPath}/orders">
                                <i class="bi bi-bag-check"></i>
                                Lịch sử mua
                            </a>
                        </li>

                        <li class="nav-item border-start border-secondary ps-3">
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

                    </c:otherwise>

                </c:choose>

            </ul>

        </div>
    </div>
</nav>

<main class="main-content">
    <div class="container">
        <sitemesh:write property="body"/>
    </div>
</main>

<footer>
    <div class="container text-center">

        <div class="fw-semibold">
            Video Website
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