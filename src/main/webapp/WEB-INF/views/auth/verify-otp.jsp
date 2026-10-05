<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">
    <title>Xác nhận OTP</title>

    <style>
        .otp-wrapper {
            min-height: 560px;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .otp-card {
            width: 100%;
            max-width: 500px;
            border: none;
            border-radius: 16px;
            box-shadow: 0 8px 30px
                        rgba(0, 0, 0, 0.12);
        }

        .otp-icon {
            width: 75px;
            height: 75px;
            margin: 0 auto 16px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            background-color: #6f42c1;
            color: white;
            font-size: 34px;
        }

        .otp-input {
            text-align: center;
            font-size: 28px;
            font-weight: 700;
            letter-spacing: 10px;
        }
    </style>
</head>

<body>

<div class="otp-wrapper">

    <div class="card otp-card">

        <div class="card-body p-4 p-md-5">

            <div class="otp-icon">
                <i class="bi bi-shield-check"></i>
            </div>

            <h2 class="text-center mb-2">
                Xác nhận OTP
            </h2>

            <p class="text-center text-muted mb-4">

                Mã OTP đã được gửi đến:

                <strong>
                    <c:out value="${email}"/>
                </strong>

            </p>

            <c:if test="${not empty error}">

                <div class="alert alert-danger">
                    <i class="bi bi-exclamation-triangle"></i>
                    <c:out value="${error}"/>
                </div>

            </c:if>

            <c:if test="${not empty message}">

                <div class="alert alert-success">
                    <i class="bi bi-check-circle"></i>
                    <c:out value="${message}"/>
                </div>

            </c:if>

            <form method="post"
                  action="${pageContext.request.contextPath}/verify-otp">

                <div class="mb-4">

                    <label for="otp"
                           class="form-label text-center d-block">

                        Nhập mã OTP gồm 6 chữ số
                    </label>

                    <input type="text"
                           class="form-control otp-input"
                           id="otp"
                           name="otp"
                           inputmode="numeric"
                           pattern="[0-9]{6}"
                           minlength="6"
                           maxlength="6"
                           autocomplete="one-time-code"
                           placeholder="000000"
                           required
                           autofocus>

                    <div class="form-text text-center mt-2">
                        Mã OTP có hiệu lực trong 5 phút.
                    </div>

                </div>

                <button type="submit"
                        class="btn btn-primary w-100 py-2">

                    <i class="bi bi-check-circle"></i>
                    Xác nhận đăng ký
                </button>

            </form>

            <form method="post"
                  action="${pageContext.request.contextPath}/verify-otp"
                  class="mt-3">

                <input type="hidden"
                       name="action"
                       value="resend">

                <button type="submit"
                        class="btn btn-outline-secondary w-100">

                    <i class="bi bi-arrow-clockwise"></i>
                    Gửi lại mã OTP
                </button>

            </form>

            <div class="text-center mt-4">

                <a href="${pageContext.request.contextPath}/register"
                   class="text-decoration-none">

                    <i class="bi bi-arrow-left"></i>
                    Quay lại đăng ký
                </a>

            </div>

        </div>
    </div>
</div>

</body>
</html>