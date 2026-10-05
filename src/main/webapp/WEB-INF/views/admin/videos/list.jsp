<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý video</title>
</head>

<body>

<div class="d-flex justify-content-between align-items-center mb-4">

    <div>
        <h1 class="h3 mb-1">
            <i class="bi bi-camera-video"></i>
            Quản lý video
        </h1>

        <div class="text-muted">
            Tổng số: <strong>${totalVideos}</strong> video
        </div>
    </div>

    <a href="${pageContext.request.contextPath}/admin/videos/create"
       class="btn btn-primary">
        <i class="bi bi-plus-circle"></i>
        Thêm video
    </a>

</div>

<c:if test="${param.success == 'created'}">
    <div class="alert alert-success alert-dismissible fade show">
        Thêm video thành công.
        <button type="button"
                class="btn-close"
                data-bs-dismiss="alert"></button>
    </div>
</c:if>

<c:if test="${param.success == 'updated'}">
    <div class="alert alert-success alert-dismissible fade show">
        Cập nhật video thành công.
        <button type="button"
                class="btn-close"
                data-bs-dismiss="alert"></button>
    </div>
</c:if>

<c:if test="${param.success == 'deleted'}">
    <div class="alert alert-success alert-dismissible fade show">
        Xóa video thành công.
        <button type="button"
                class="btn-close"
                data-bs-dismiss="alert"></button>
    </div>
</c:if>

<c:if test="${param.error == 'delete-failed'}">
    <div class="alert alert-danger alert-dismissible fade show">
        Không thể xóa video.
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
                        <th>Poster</th>
                        <th>Mã video</th>
                        <th>Tiêu đề</th>
                        <th>Thể loại</th>
                        <th>Lượt xem</th>
                        <th>Trạng thái</th>
                        <th class="text-center">Thao tác</th>
                    </tr>
                </thead>

                <tbody>

                <c:choose>

                    <c:when test="${empty videos}">
                        <tr>
                            <td colspan="7"
                                class="text-center text-muted py-4">
                                Không có video nào.
                            </td>
                        </tr>
                    </c:when>

                    <c:otherwise>

                        <c:forEach var="video"
                                   items="${videos}">

                            <c:url var="editUrl"
                                   value="/admin/videos/edit">
                                <c:param name="id"
                                         value="${video.videoId}"/>
                            </c:url>

                            <tr>

                                <td>
                                    <c:choose>

                                        <c:when test="${empty video.poster}">
                                            <div class="thumbnail-admin
                                                        d-flex
                                                        align-items-center
                                                        justify-content-center
                                                        bg-secondary
                                                        text-white">
                                                <i class="bi bi-camera-video"></i>
                                            </div>
                                        </c:when>

                                        <c:otherwise>
                                            <img
                                                src="${pageContext.request.contextPath}/images/${video.poster}"
                                                alt="Poster"
                                                class="thumbnail-admin">
                                        </c:otherwise>

                                    </c:choose>
                                </td>

                                <td>
                                    <code>
                                        <c:out value="${video.videoId}"/>
                                    </code>
                                </td>

                                <td class="fw-semibold">
                                    <c:out value="${video.title}"/>
                                </td>

                                <td>
                                    <c:out value="${video.categoryName}"/>
                                </td>

                                <td>
                                    <i class="bi bi-eye"></i>
                                    ${video.views}
                                </td>

                                <td>
                                    <c:choose>

                                        <c:when test="${video.active}">
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

                                    <a href="${pageContext.request.contextPath}/video/detail?id=${video.videoId}"
                                       class="btn btn-sm btn-info"
                                       target="_blank">
                                        <i class="bi bi-eye"></i>
                                    </a>

                                    <a href="${editUrl}"
                                       class="btn btn-sm btn-warning">
                                        <i class="bi bi-pencil-square"></i>
                                    </a>

                                    <form method="post"
                                          action="${pageContext.request.contextPath}/admin/videos/delete"
                                          class="d-inline"
                                          onsubmit="return confirm('Bạn có chắc muốn xóa video này?');">

                                        <input type="hidden"
                                               name="id"
                                               value="<c:out value='${video.videoId}'/>">

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
                   href="${pageContext.request.contextPath}/admin/videos?page=${currentPage - 1}">
                    Trước
                </a>
            </li>

            <c:forEach var="pageNumber"
                       begin="1"
                       end="${totalPages}">

                <li class="page-item ${pageNumber == currentPage ? 'active' : ''}">
                    <a class="page-link"
                       href="${pageContext.request.contextPath}/admin/videos?page=${pageNumber}">
                        ${pageNumber}
                    </a>
                </li>

            </c:forEach>

            <li class="page-item ${currentPage >= totalPages ? 'disabled' : ''}">
                <a class="page-link"
                   href="${pageContext.request.contextPath}/admin/videos?page=${currentPage + 1}">
                    Sau
                </a>
            </li>

        </ul>
    </nav>

</c:if>

</body>
</html>