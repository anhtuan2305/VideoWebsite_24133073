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
          ? 'Thêm thể loại'
          : 'Cập nhật thể loại'}
    </title>
</head>

<body>

<div class="row justify-content-center">
    <div class="col-xl-7">

        <div class="d-flex justify-content-between align-items-center mb-4">

            <h1 class="h3 mb-0">
                <i class="bi bi-tag"></i>

                ${formAction == 'create'
                  ? 'Thêm thể loại'
                  : 'Cập nhật thể loại'}
            </h1>

            <a href="${pageContext.request.contextPath}/admin/categories"
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
                      action="${pageContext.request.contextPath}/admin/categories/${formAction}">

                    <c:if test="${formAction == 'edit'}">
                        <input type="hidden"
                               name="categoryId"
                               value="${category.categoryId}">
                    </c:if>

                    <div class="mb-3">

                        <label for="categoryName"
                               class="form-label">
                            Tên thể loại
                            <span class="text-danger">*</span>
                        </label>

                        <input type="text"
                               class="form-control"
                               id="categoryName"
                               name="categoryName"
                               value="<c:out value='${category.categoryName}'/>"
                               maxlength="100"
                               required>

                    </div>

                    <div class="mb-3">

                        <label for="categoryCode"
                               class="form-label">
                            Mã thể loại
                            <span class="text-danger">*</span>
                        </label>

                        <input type="text"
                               class="form-control"
                               id="categoryCode"
                               name="categoryCode"
                               value="<c:out value='${category.categoryCode}'/>"
                               maxlength="100"
                               placeholder="Ví dụ: AMNHAC"
                               required>

                    </div>

                    <div class="mb-3">

                        <label for="images"
                               class="form-label">
                            Tên file ảnh
                        </label>

                        <input type="text"
                               class="form-control"
                               id="images"
                               name="images"
                               value="<c:out value='${category.images}'/>"
                               maxlength="500"
                               placeholder="Ví dụ: music.jpg">

                    </div>

                    <div class="form-check form-switch mb-4">

                        <input type="checkbox"
                               class="form-check-input"
                               id="status"
                               name="status"
                               ${(formAction == 'create' or category.status)
                                  ? 'checked'
                                  : ''}>

                        <label class="form-check-label"
                               for="status">
                            Thể loại đang hoạt động
                        </label>

                    </div>

                    <button type="submit"
                            class="btn btn-primary">
                        <i class="bi bi-save"></i>

                        ${formAction == 'create'
                          ? 'Thêm thể loại'
                          : 'Lưu thay đổi'}
                    </button>

                    <a href="${pageContext.request.contextPath}/admin/categories"
                       class="btn btn-secondary">
                        Hủy
                    </a>

                </form>

            </div>
        </div>

    </div>
</div>

</body>
</html>