<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý thể loại</title>
</head>

<body>

<div class="d-flex justify-content-between align-items-center mb-4">
    <div>
        <h1 class="h3 mb-1">
            <i class="bi bi-tags"></i>
            Quản lý thể loại
        </h1>

        <div class="text-muted">
            Tổng số: <strong>${totalCategories}</strong> thể loại
        </div>
    </div>

    <a href="${pageContext.request.contextPath}/admin/categories/create"
       class="btn btn-primary">
        <i class="bi bi-plus-circle"></i>
        Thêm thể loại
    </a>
</div>

<c:if test="${param.success == 'created'}">
    <div class="alert alert-success alert-dismissible fade show">
        Thêm thể loại thành công.
        <button type="button"
                class="btn-close"
                data-bs-dismiss="alert"></button>
    </div>
</c:if>

<c:if test="${param.success == 'updated'}">
    <div class="alert alert-success alert-dismissible fade show">
        Cập nhật thể loại thành công.
        <button type="button"
                class="btn-close"
                data-bs-dismiss="alert"></button>
    </div>
</c:if>

<c:if test="${param.success == 'deleted'}">
    <div class="alert alert-success alert-dismissible fade show">
        Xóa thể loại thành công.
        <button type="button"
                class="btn-close"
                data-bs-dismiss="alert"></button>
    </div>
</c:if>

<c:if test="${param.error == 'category-has-videos'}">
    <div class="alert alert-danger alert-dismissible fade show">
        Không thể xóa thể loại vì đang có video thuộc thể loại này.
        <button type="button"
                class="btn-close"
                data-bs-dismiss="alert"></button>
    </div>
</c:if>

<div class="card admin-card">
    <div class="card-body">

        <div class="table-responsive">

            <table class="table table-hover align-middle">

                <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Ảnh</th>
                        <th>Tên thể loại</th>
                        <th>Mã thể loại</th>
                        <th>Trạng thái</th>
                        <th class="text-center">Thao tác</th>
                    </tr>
                </thead>

                <tbody>

                <c:choose>

                    <c:when test="${empty categories}">
                        <tr>
                            <td colspan="6"
                                class="text-center text-muted py-4">
                                Không có thể loại nào.
                            </td>
                        </tr>
                    </c:when>

                    <c:otherwise>

                        <c:forEach var="category"
                                   items="${categories}">

                            <c:url var="editUrl"
                                   value="/admin/categories/edit">
                                <c:param name="id"
                                         value="${category.categoryId}"/>
                            </c:url>

                            <tr>
                                <td>${category.categoryId}</td>

                                <td>
                                    <c:choose>

                                        <c:when test="${empty category.images}">
                                            <div class="thumbnail-admin
                                                        d-flex
                                                        align-items-center
                                                        justify-content-center
                                                        bg-secondary
                                                        text-white">
                                                <i class="bi bi-image"></i>
                                            </div>
                                        </c:when>

                                        <c:otherwise>
                                            <img
                                                src="${pageContext.request.contextPath}/images/${category.images}"
                                                alt="Category"
                                                class="thumbnail-admin">
                                        </c:otherwise>

                                    </c:choose>
                                </td>

                                <td class="fw-semibold">
                                    <c:out value="${category.categoryName}"/>
                                </td>

                                <td>
                                    <code>
                                        <c:out value="${category.categoryCode}"/>
                                    </code>
                                </td>

                                <td>
                                    <c:choose>
                                        <c:when test="${category.status}">
                                            <span class="badge bg-success">
                                                Hoạt động
                                            </span>
                                        </c:when>

                                        <c:otherwise>
                                            <span class="badge bg-secondary">
                                                Đã ẩn
                                            </span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>

                                <td class="text-center">

                                    <a href="${editUrl}"
                                       class="btn btn-sm btn-warning">
                                        <i class="bi bi-pencil-square"></i>
                                    </a>

                                    <form method="post"
                                          action="${pageContext.request.contextPath}/admin/categories/delete"
                                          class="d-inline"
                                          onsubmit="return confirm('Bạn có chắc muốn xóa thể loại này?');">

                                        <input type="hidden"
                                               name="id"
                                               value="${category.categoryId}">

                                        <button type="submit"
                                                class="btn btn-sm btn-danger">
                                            <i class="bi bi-trash"></i>
                                        </button>

                                    </form>

                                </td>
                            </tr>

                        </c:forEach>

                    </c:otherwise>

                </c:choose>

                </tbody>
            </table>

        </div>
    </div>
</div>

<c:if test="${totalPages > 1}">

    <nav class="mt-4">
        <ul class="pagination justify-content-center">

            <li class="page-item ${currentPage <= 1 ? 'disabled' : ''}">
                <a class="page-link"
                   href="${pageContext.request.contextPath}/admin/categories?page=${currentPage - 1}">
                    Trước
                </a>
            </li>

            <c:forEach var="pageNumber"
                       begin="1"
                       end="${totalPages}">

                <li class="page-item ${pageNumber == currentPage ? 'active' : ''}">
                    <a class="page-link"
                       href="${pageContext.request.contextPath}/admin/categories?page=${pageNumber}">
                        ${pageNumber}
                    </a>
                </li>

            </c:forEach>

            <li class="page-item ${currentPage >= totalPages ? 'disabled' : ''}">
                <a class="page-link"
                   href="${pageContext.request.contextPath}/admin/categories?page=${currentPage + 1}">
                    Sau
                </a>
            </li>

        </ul>
    </nav>

</c:if>

</body>
</html>