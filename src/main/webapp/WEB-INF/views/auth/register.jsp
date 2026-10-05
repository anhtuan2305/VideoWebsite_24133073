<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">
    <title>Đăng ký tài khoản</title>

    <style>
        .register-wrapper {
            display: flex;
            justify-content: center;
            padding: 20px 0;
        }

        .register-card {
            width: 100%;
            max-width: 720px;
            border: none;
            border-radius: 16px;
            box-shadow: 0 8px 30px
                        rgba(0, 0, 0, 0.12);
        }

        .register-icon {
            width: 70px;
            height: 70px;
            margin: 0 auto 16px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            background-color: #198754;
            color: white;
            font-size: 32px;
        }
    </style>
</head>

<body>

<div class="register-wrapper">

    <div class="card register-card">

        <div class="card-body p-4 p-md-5">

            <div class="register-icon">
                <i class="bi bi-person-plus"></i>
            </div>

            <h2 class="text-center mb-2">
                Đăng ký tài khoản
            </h2>

            <p class="text-center text-muted mb-4">
                Mã OTP sẽ được gửi đến email của bạn
            </p>

            <c:if test="${not empty error}">

                <div class="alert alert-danger">
                    <i class="bi bi-exclamation-triangle"></i>
                    <c:out value="${error}"/>
                </div>

            </c:if>

            <form method="post"
                  action="${pageContext.request.contextPath}/register">

                <div class="row">

                    <div class="col-md-6 mb-3">

                        <label for="username"
                               class="form-label">

                            Tên đăng nhập
                            <span class="text-danger">*</span>
                        </label>

                        <input type="text"
                               class="form-control"
                               id="username"
                               name="username"
                               value="<c:out value='${username}'/>"
                               maxlength="50"
                               autocomplete="username"
                               required>

                    </div>

                    <div class="col-md-6 mb-3">

                        <label for="fullname"
                               class="form-label">

                            Họ và tên
                            <span class="text-danger">*</span>
                        </label>

                        <input type="text"
                               class="form-control"
                               id="fullname"
                               name="fullname"
                               value="<c:out value='${fullname}'/>"
                               maxlength="50"
                               required>

                    </div>

                    <div class="col-md-6 mb-3">

                        <label for="email"
                               class="form-label">

                            Email
                            <span class="text-danger">*</span>
                        </label>

                        <input type="email"
                               class="form-control"
                               id="email"
                               name="email"
                               value="<c:out value='${email}'/>"
                               maxlength="150"
                               autocomplete="email"
                               required>

                    </div>

                    <div class="col-md-6 mb-3">

                        <label for="phone"
                               class="form-label">

                            Số điện thoại
                        </label>

                        <input type="text"
                               class="form-control"
                               id="phone"
                               name="phone"
                               value="<c:out value='${phone}'/>"
                               maxlength="15"
                               autocomplete="tel">

                    </div>

                    <div class="col-md-6 mb-3">

                        <label for="password"
                               class="form-label">

                            Mật khẩu
                            <span class="text-danger">*</span>
                        </label>

                        <input type="password"
                               class="form-control"
                               id="password"
                               name="password"
                               minlength="6"
                               maxlength="100"
                               autocomplete="new-password"
                               required>

                        <div class="form-text">
                            Mật khẩu có ít nhất 6 ký tự.
                        </div>

                    </div>

                    <div class="col-md-6 mb-4">

                        <label for="confirmPassword"
                               class="form-label">

                            Xác nhận mật khẩu
                            <span class="text-danger">*</span>
                        </label>

                        <input type="password"
                               class="form-control"
                               id="confirmPassword"
                               name="confirmPassword"
                               minlength="6"
                               maxlength="100"
                               autocomplete="new-password"
                               required>

                    </div>

                </div>

                <button type="submit"
                        class="btn btn-success w-100 py-2">

                    <i class="bi bi-envelope-check"></i>
                    Gửi mã OTP
                </button>

            </form>

            <div class="text-center mt-4">

                <span class="text-muted">
                    Đã có tài khoản?
                </span>

                <a href="${pageContext.request.contextPath}/login"
                   class="text-decoration-none fw-semibold">

                    Đăng nhập
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