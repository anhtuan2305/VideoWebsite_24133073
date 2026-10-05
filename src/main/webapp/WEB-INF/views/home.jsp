<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="fn"
           uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">
    <title>Trang chủ</title>

    <style>
        .hero-section {
            background: linear-gradient(
                135deg,
                #0d6efd,
                #6f42c1
            );
            color: white;
            border-radius: 18px;
            padding: 45px 35px;
            margin-bottom: 30px;
        }

        .category-title {
            border-left: 5px solid #0d6efd;
            padding-left: 12px;
        }

        .empty-video {
            border: 2px dashed #ced4da;
            border-radius: 12px;
            padding: 30px;
            text-align: center;
            color: #6c757d;
        }

        .poster-placeholder {
            height: 190px;
            display: flex;
            align-items: center;
            justify-content: center;
            background-color: #dee2e6;
            color: #6c757d;
            font-size: 50px;
        }
    </style>
</head>

<body>

<section class="hero-section shadow">

    <div class="row align-items-center">

        <div class="col-md-8">

            <h1 class="fw-bold">
                <i class="bi bi-play-circle-fill"></i>
                Video Website
            </h1>

            <p class="lead mb-0">
                Khám phá các video được sắp xếp
                theo từng thể loại.
            </p>

        </div>

        <div class="col-md-4 text-md-end mt-3 mt-md-0">

            <c:choose>

                <c:when test="${empty sessionScope.currentUser}">

                    <a href="${pageContext.request.contextPath}/register"
                       class="btn btn-light btn-lg">

                        <i class="bi bi-person-plus"></i>
                        Đăng ký
                    </a>

                </c:when>

                <c:otherwise>

                    <span class="fs-5">
                        Xin chào,
                        <strong>
                            <c:out value="${sessionScope.currentUser.fullname}"/>
                        </strong>
                    </span>

                </c:otherwise>

            </c:choose>

        </div>

    </div>
</section>

<div class="card border-0 shadow-sm mb-4">

    <div class="card-body">

        <div class="d-flex flex-wrap gap-2">

            <a href="${pageContext.request.contextPath}/home"
               class="btn
                      ${selectedCategoryId == 0
                        ? 'btn-primary'
                        : 'btn-outline-primary'}">

                <i class="bi bi-grid"></i>
                Tất cả thể loại
            </a>

            <c:forEach var="category"
                       items="${categories}">

                <c:if test="${category.status}">

                    <a href="${pageContext.request.contextPath}/home?categoryId=${category.categoryId}&page=1"
                       class="btn
                              ${selectedCategoryId == category.categoryId
                                ? 'btn-primary'
                                : 'btn-outline-primary'}">

                        <c:out value="${category.categoryName}"/>
                    </a>

                </c:if>

            </c:forEach>

        </div>

    </div>
</div>

<c:if test="${empty displayedCategories}">

    <div class="alert alert-warning">
        Không tìm thấy thể loại được yêu cầu.
    </div>

</c:if>

<c:forEach var="category"
           items="${displayedCategories}">

    <c:if test="${category.status}">

        <c:set var="categoryVideos"
               value="${videosByCategory[category.categoryId]}"/>

        <c:set var="totalPages"
               value="${totalPagesByCategory[category.categoryId]}"/>

        <section class="mb-5">

            <div class="d-flex justify-content-between
                        align-items-center mb-3">

                <h2 class="category-title mb-0">

                    <c:out value="${category.categoryName}"/>

                    <span class="badge bg-secondary fs-6">
                        ${videoServiceCount}
                    </span>

                </h2>

                <c:if test="${selectedCategoryId == 0}">

                    <a href="${pageContext.request.contextPath}/home?categoryId=${category.categoryId}&page=1"
                       class="btn btn-sm btn-outline-primary">

                        Xem tất cả
                        <i class="bi bi-arrow-right"></i>
                    </a>

                </c:if>

            </div>

            <c:choose>

                <c:when test="${empty categoryVideos}">

                    <div class="empty-video">

                        <i class="bi bi-camera-video-off fs-1"></i>

                        <div class="mt-2">
                            Chưa có video trong thể loại này.
                        </div>

                    </div>

                </c:when>

                <c:otherwise>

                    <div class="row g-4">

                        <c:forEach var="video"
                                   items="${categoryVideos}">

                            <div class="col-md-6 col-lg-4">

                                <div class="card video-card shadow-sm">

                                    <c:choose>

                                        <c:when test="${empty video.poster}">

                                            <div class="poster-placeholder">
                                                <i class="bi bi-camera-video"></i>
                                            </div>

                                        </c:when>

                                        <c:otherwise>

                                            <img
                                                src="${pageContext.request.contextPath}/images/${video.poster}"
                                                class="video-poster"
                                                alt="<c:out value='${video.title}'/>"
                                                onerror="this.style.display='none';
                                                         this.nextElementSibling.style.display='flex';">

                                            <div class="poster-placeholder"
                                                 style="display: none;">

                                                <i class="bi bi-camera-video"></i>
                                            </div>

                                        </c:otherwise>

                                    </c:choose>

                                    <div class="card-body">

                                        <h5 class="card-title">

                                            <c:out value="${video.title}"/>

                                        </h5>

                                        <div class="text-muted small mb-3">

                                            <i class="bi bi-eye"></i>
                                            ${video.views} lượt xem

                                            <span class="mx-2">•</span>

                                            <i class="bi bi-tag"></i>
                                            <c:out value="${video.categoryName}"/>

                                        </div>

                                        <c:if test="${not empty video.description}">

                                            <p class="card-text text-muted">

                                                <c:choose>

                                                    <c:when test="${fn:length(video.description) > 100}">

                                                        <c:out value="${fn:substring(
                                                            video.description,
                                                            0,
                                                            100
                                                        )}"/>...

                                                    </c:when>

                                                    <c:otherwise>

                                                        <c:out value="${video.description}"/>

                                                    </c:otherwise>

                                                </c:choose>

                                            </p>

                                        </c:if>

                                    </div>

                                    <div class="card-footer
                                                bg-white border-0 pb-3">

                                        <a href="${pageContext.request.contextPath}/video/detail?id=${video.videoId}"
                                           class="btn btn-primary w-100">

                                            <i class="bi bi-play-fill"></i>
                                            Xem chi tiết
                                        </a>

                                    </div>

                                </div>
                            </div>

                        </c:forEach>

                    </div>

                </c:otherwise>

            </c:choose>

            <c:if test="${selectedCategoryId == category.categoryId
                        and totalPages > 1}">

                <nav class="mt-4">

                    <ul class="pagination justify-content-center">

                        <li class="page-item
                                   ${currentPage <= 1
                                     ? 'disabled'
                                     : ''}">

                            <a class="page-link"
                               href="${pageContext.request.contextPath}/home?categoryId=${category.categoryId}&page=${currentPage - 1}">

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
                                   href="${pageContext.request.contextPath}/home?categoryId=${category.categoryId}&page=${pageNumber}">

                                    ${pageNumber}
                                </a>

                            </li>

                        </c:forEach>

                        <li class="page-item
                                   ${currentPage >= totalPages
                                     ? 'disabled'
                                     : ''}">

                            <a class="page-link"
                               href="${pageContext.request.contextPath}/home?categoryId=${category.categoryId}&page=${currentPage + 1}">

                                Sau
                            </a>

                        </li>

                    </ul>

                </nav>

            </c:if>

        </section>

    </c:if>

</c:forEach>

</body>
</html>