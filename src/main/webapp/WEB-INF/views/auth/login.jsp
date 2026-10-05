<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">
    <title>Đăng nhập</title>

    <style>
        .login-wrapper {
            min-height: 560px;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .login-card {
            width: 100%;
            max-width: 450px;
            border: none;
            border-radius: 16px;
            box-shadow: 0 8px 30px
                        rgba(0, 0, 0, 0.12);
        }

        .login-icon {
            width: 70px;
            height: 70px;
            margin: 0 auto 16px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            background-color: #0d6efd;
            color: white;
            font-size: 32px;
        }
    </style>
</head>

<body>

<div class="login-wrapper">

    <div class="card login-card">

        <div class="card-body p-4 p-md-5">

            <div class="login-icon">
                <i class="bi bi-person-lock"></i>
            </div>

            <h2 class="text-center mb-2">
                Đăng nhập
            </h2>

            <p class="text-center text-muted mb-4">
                Đăng nhập vào Video Website
            </p>

            <c:if test="${param.registered == 'true'}">

                <div class="alert alert-success">
                    <i class="bi bi-check-circle"></i>
                    Đăng ký thành công. Hãy đăng nhập.
                </div>

            </c:if>

            <c:if test="${not empty error}">

                <div class="alert alert-danger">
                    <i class="bi bi-exclamation-triangle"></i>
                    <c:out value="${error}"/>
                </div>

            </c:if>

            <form method="post"
                  action="${pageContext.request.contextPath}/login">

                <div class="mb-3">

                    <label for="username"
                           class="form-label">

                        Tên đăng nhập
                    </label>

                    <div class="input-group">

                        <span class="input-group-text">
                            <i class="bi bi-person"></i>
                        </span>

                        <input type="text"
                               class="form-control"
                               id="username"
                               name="username"
                               value="<c:out value='${username}'/>"
                               maxlength="50"
                               autocomplete="username"
                               required
                               autofocus>

                    </div>
                </div>

                <div class="mb-4">

                    <label for="password"
                           class="form-label">

                        Mật khẩu
                    </label>

                    <div class="input-group">

                        <span class="input-group-text">
                            <i class="bi bi-key"></i>
                        </span>

                        <input type="password"
                               class="form-control"
                               id="password"
                               name="password"
                               autocomplete="current-password"
                               required>

                    </div>
                </div>

                <button type="submit"
                        class="btn btn-primary w-100 py-2">

                    <i class="bi bi-box-arrow-in-right"></i>
                    Đăng nhập
                </button>

            </form>

            <div class="text-center mt-4">

                <span class="text-muted">
                    Chưa có tài khoản?
                </span>

                <a href="${pageContext.request.contextPath}/register"
                   class="text-decoration-none fw-semibold">

                    Đăng ký ngay
                </a>

            </div>

            <div class="text-center mt-3">

                <a href="${pageContext.request.contextPath}/home"
                   class="text-decoration-none">

                    <i class="bi bi-arrow-left"></i>
                    Quay lại trang chủ
                </a>

            </div>

        </div>
    </div>
</div>

</body>
</html>