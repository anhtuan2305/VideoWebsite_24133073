<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">
    <title>Quản lý người dùng</title>
</head>

<body>

<div class="d-flex justify-content-between
            align-items-center mb-4">

    <div>

        <h1 class="h3 mb-1">
            <i class="bi bi-people"></i>
            Quản lý người dùng
        </h1>

        <div class="text-muted">
            Tổng số:
            <strong>${totalUsers}</strong>
            người dùng
        </div>

    </div>

    <a href="${pageContext.request.contextPath}/admin/users/create"
       class="btn btn-primary">

        <i class="bi bi-person-plus"></i>
        Thêm người dùng
    </a>

</div>

<c:choose>

    <c:when test="${param.success == 'created'}">

        <div class="alert alert-success alert-dismissible fade show">

            Thêm người dùng thành công.

            <button type="button"
                    class="btn-close"
                    data-bs-dismiss="alert">
            </button>

        </div>

    </c:when>

    <c:when test="${param.success == 'updated'}">

        <div class="alert alert-success alert-dismissible fade show">

            Cập nhật người dùng thành công.

            <button type="button"
                    class="btn-close"
                    data-bs-dismiss="alert">
            </button>

        </div>

    </c:when>

    <c:when test="${param.success == 'deleted'}">

        <div class="alert alert-success alert-dismissible fade show">

            Xóa người dùng thành công.

            <button type="button"
                    class="btn-close"
                    data-bs-dismiss="alert">
            </button>

        </div>

    </c:when>

    <c:when test="${param.error == 'self-delete'}">

        <div class="alert alert-warning alert-dismissible fade show">

            Bạn không thể xóa tài khoản đang đăng nhập.

            <button type="button"
                    class="btn-close"
                    data-bs-dismiss="alert">
            </button>

        </div>

    </c:when>

    <c:when test="${param.error == 'delete-failed'}">

        <div class="alert alert-danger alert-dismissible fade show">

            Không thể xóa người dùng.
            Người dùng có thể đang có dữ liệu liên quan.

            <button type="button"
                    class="btn-close"
                    data-bs-dismiss="alert">
            </button>

        </div>

    </c:when>

</c:choose>

<div class="card admin-card">

    <div class="card-body">

        <div class="table-responsive">

            <table class="table table-hover align-middle">

                <thead class="table-dark">

                    <tr>
                        <th>Ảnh</th>
                        <th>Tên đăng nhập</th>
                        <th>Họ tên</th>
                        <th>Email</th>
                        <th>Điện thoại</th>
                        <th>Vai trò</th>
                        <th>Trạng thái</th>
                        <th class="text-center">Thao tác</th>
                    </tr>

                </thead>

                <tbody>

                    <c:choose>

                        <c:when test="${empty users}">

                            <tr>
                                <td colspan="8"
                                    class="text-center text-muted py-4">

                                    Không có người dùng nào.
                                </td>
                            </tr>

                        </c:when>

                        <c:otherwise>

                            <c:forEach var="user"
                                       items="${users}">

                                <c:url var="editUrl"
                                       value="/admin/users/edit">

                                    <c:param name="username"
                                             value="${user.username}"/>

                                </c:url>

                                <tr>

                                    <td>

                                        <c:choose>

                                            <c:when test="${empty user.images}">

                                                <div class="rounded-circle
                                                            bg-secondary
                                                            text-white
                                                            d-flex
                                                            align-items-center
                                                            justify-content-center"
                                                     style="width: 45px;
                                                            height: 45px;">

                                                    <i class="bi bi-person"></i>
                                                </div>

                                            </c:when>

                                            <c:otherwise>

                                                <img
                                                    src="${pageContext.request.contextPath}/images/${user.images}"
                                                    alt="Avatar"
                                                    class="rounded-circle"
                                                    style="width: 45px;
                                                           height: 45px;
                                                           object-fit: cover;">

                                            </c:otherwise>

                                        </c:choose>

                                    </td>

                                    <td class="fw-semibold">
                                        <c:out value="${user.username}"/>
                                    </td>

                                    <td>
                                        <c:out value="${user.fullname}"/>
                                    </td>

                                    <td>
                                        <c:out value="${user.email}"/>
                                    </td>

                                    <td>
                                        <c:out value="${user.phone}"/>
                                    </td>

                                    <td>

                                        <c:choose>

                                            <c:when test="${user.admin}">

                                                <span class="badge bg-danger">
                                                    Admin
                                                </span>

                                            </c:when>

                                            <c:otherwise>

                                                <span class="badge bg-primary">
                                                    User
                                                </span>

                                            </c:otherwise>

                                        </c:choose>

                                    </td>

                                    <td>

                                        <c:choose>

                                            <c:when test="${user.active}">

                                                <span class="badge bg-success">
                                                    Hoạt động
                                                </span>

                                            </c:when>

                                            <c:otherwise>

                                                <span class="badge bg-secondary">
                                                    Đã khóa
                                                </span>

                                            </c:otherwise>

                                        </c:choose>

                                    </td>

                                    <td class="text-center">

                                        <a href="${editUrl}"
                                           class="btn btn-sm btn-warning">

                                            <i class="bi bi-pencil-square"></i>
                                        </a>

                                        <c:if test="${sessionScope.currentUser.username
                                                    != user.username}">

                                            <form method="post"
                                                  action="${pageContext.request.contextPath}/admin/users/delete"
                                                  class="d-inline"
                                                  onsubmit="return confirm(
                                                      'Bạn có chắc muốn xóa người dùng này?'
                                                  );">

                                                <input type="hidden"
                                                       name="username"
                                                       value="<c:out value='${user.username}'/>">

                                                <button type="submit"
                                                        class="btn btn-sm btn-danger">

                                                    <i class="bi bi-trash"></i>
                                                </button>

                                            </form>

                                        </c:if>

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

            <li class="page-item
                       ${currentPage <= 1
                         ? 'disabled'
                         : ''}">

                <a class="page-link"
                   href="${pageContext.request.contextPath}/admin/users?page=${currentPage - 1}">

                    Trước
                </a>

            </li>

            <c:forEach var="pageNumber"
                       begin="1"
                       end="${totalPages}">

                <li class="page-item
                           ${pageNumber == currentPage
                             ? 'active'
                             : ''}">

                    <a class="page-link"
                       href="${pageContext.request.contextPath}/admin/users?page=${pageNumber}">

                        ${pageNumber}
                    </a>

                </li>

            </c:forEach>

            <li class="page-item
                       ${currentPage >= totalPages
                         ? 'disabled'
                         : ''}">

                <a class="page-link"
                   href="${pageContext.request.contextPath}/admin/users?page=${currentPage + 1}">

                    Sau
                </a>

            </li>

        </ul>

    </nav>

</c:if>

</body>
</html>