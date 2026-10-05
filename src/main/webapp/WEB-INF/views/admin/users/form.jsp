<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">

    <title>
        ${formAction == 'create'
          ? 'Thêm người dùng'
          : 'Cập nhật người dùng'}
    </title>
</head>

<body>

<div class="row justify-content-center">

    <div class="col-xl-8">

        <div class="d-flex justify-content-between
                    align-items-center mb-4">

            <h1 class="h3 mb-0">

                <c:choose>

                    <c:when test="${formAction == 'create'}">

                        <i class="bi bi-person-plus"></i>
                        Thêm người dùng

                    </c:when>

                    <c:otherwise>

                        <i class="bi bi-pencil-square"></i>
                        Cập nhật người dùng

                    </c:otherwise>

                </c:choose>

            </h1>

            <a href="${pageContext.request.contextPath}/admin/users"
               class="btn btn-outline-secondary">

                <i class="bi bi-arrow-left"></i>
                Quay lại
            </a>

        </div>

        <div class="card admin-card">

            <div class="card-body p-4">

                <c:if test="${not empty error}">

                    <div class="alert alert-danger">

                        <i class="bi bi-exclamation-triangle"></i>
                        <c:out value="${error}"/>

                    </div>

                </c:if>

                <form method="post"
                      action="${pageContext.request.contextPath}/admin/users/${formAction}">

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
                                   value="<c:out value='${user.username}'/>"
                                   maxlength="50"
                                   ${formAction == 'edit'
                                     ? 'readonly'
                                     : ''}
                                   required>

                            <c:if test="${formAction == 'edit'}">

                                <div class="form-text">
                                    Không thể thay đổi tên đăng nhập.
                                </div>

                            </c:if>

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
                                   value="<c:out value='${user.fullname}'/>"
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
                                   value="<c:out value='${user.email}'/>"
                                   maxlength="150"
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
                                   value="<c:out value='${user.phone}'/>"
                                   maxlength="15">

                        </div>

                        <div class="col-md-6 mb-3">

                            <label for="password"
                                   class="form-label">

                                Mật khẩu

                                <c:if test="${formAction == 'create'}">
                                    <span class="text-danger">*</span>
                                </c:if>

                            </label>

                            <input type="password"
                                   class="form-control"
                                   id="password"
                                   name="password"
                                   minlength="6"
                                   maxlength="100">

                            <div class="form-text">

                                <c:choose>

                                    <c:when test="${formAction == 'create'}">
                                        Nhập ít nhất 6 ký tự.
                                    </c:when>

                                    <c:otherwise>
                                        Để trống nếu không muốn đổi mật khẩu.
                                    </c:otherwise>

                                </c:choose>

                            </div>

                        </div>

                        <div class="col-md-6 mb-3">

                            <label for="confirmPassword"
                                   class="form-label">

                                Xác nhận mật khẩu

                                <c:if test="${formAction == 'create'}">
                                    <span class="text-danger">*</span>
                                </c:if>

                            </label>

                            <input type="password"
                                   class="form-control"
                                   id="confirmPassword"
                                   name="confirmPassword"
                                   minlength="6"
                                   maxlength="100">

                        </div>

                        <div class="col-12 mb-3">

                            <label for="images"
                                   class="form-label">

                                Tên file ảnh
                            </label>

                            <input type="text"
                                   class="form-control"
                                   id="images"
                                   name="images"
                                   value="<c:out value='${user.images}'/>"
                                   maxlength="500"
                                   placeholder="Ví dụ: user01.jpg">

                            <div class="form-text">
                                Ảnh được lưu trong thư mục
                                <code>src/main/webapp/images</code>.
                            </div>

                        </div>

                        <div class="col-md-6 mb-3">

                            <div class="form-check form-switch">

                                <input type="checkbox"
                                       class="form-check-input"
                                       id="admin"
                                       name="admin"
                                       ${user.admin
                                         ? 'checked'
                                         : ''}>

                                <label class="form-check-label"
                                       for="admin">

                                    Quyền quản trị viên
                                </label>

                            </div>

                        </div>

                        <div class="col-md-6 mb-4">

                            <div class="form-check form-switch">

                                <input type="checkbox"
                                       class="form-check-input"
                                       id="active"
                                       name="active"
                                       ${(formAction == 'create'
                                          or user.active)
                                         ? 'checked'
                                         : ''}>

                                <label class="form-check-label"
                                       for="active">

                                    Tài khoản hoạt động
                                </label>

                            </div>

                        </div>

                    </div>

                    <div class="d-flex gap-2">

                        <button type="submit"
                                class="btn btn-primary">

                            <i class="bi bi-save"></i>

                            ${formAction == 'create'
                              ? 'Thêm người dùng'
                              : 'Lưu thay đổi'}

                        </button>

                        <a href="${pageContext.request.contextPath}/admin/users"
                           class="btn btn-secondary">

                            Hủy
                        </a>

                    </div>

                </form>

            </div>
        </div>

    </div>
</div>

</body>
</html>