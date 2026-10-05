<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">

    <title>
        <c:out value="${video.title}"/>
    </title>

    <style>
        .detail-card {
            border: none;
            border-radius: 16px;
            overflow: hidden;
            box-shadow: 0 8px 30px
                        rgba(0, 0, 0, 0.12);
        }

        .detail-poster {
            width: 100%;
            max-height: 500px;
            object-fit: cover;
            background-color: #dee2e6;
        }

        .detail-placeholder {
            width: 100%;
            min-height: 420px;
            display: flex;
            align-items: center;
            justify-content: center;
            background-color: #dee2e6;
            color: #6c757d;
            font-size: 90px;
        }

        .statistic-box {
            background-color: #f8f9fa;
            border-radius: 10px;
            padding: 14px;
            text-align: center;
            height: 100%;
        }

        .statistic-value {
            font-size: 24px;
            font-weight: 700;
            color: #0d6efd;
        }

        .description-box {
            white-space: pre-line;
            line-height: 1.7;
        }
    </style>
</head>

<body>

<div class="mb-3">

    <a href="${pageContext.request.contextPath}/home"
       class="btn btn-outline-secondary">

        <i class="bi bi-arrow-left"></i>
        Quay lại trang chủ
    </a>

</div>

<div class="card detail-card">

    <div class="row g-0">

        <div class="col-lg-7">

            <c:choose>

                <c:when test="${empty video.poster}">

                    <div class="detail-placeholder">
                        <i class="bi bi-camera-video"></i>
                    </div>

                </c:when>

                <c:otherwise>

                    <img
                        src="${pageContext.request.contextPath}/images/${video.poster}"
                        class="detail-poster"
                        alt="<c:out value='${video.title}'/>"
                        onerror="this.style.display='none';
                                 this.nextElementSibling.style.display='flex';">

                    <div class="detail-placeholder"
                         style="display: none;">

                        <i class="bi bi-camera-video"></i>
                    </div>

                </c:otherwise>

            </c:choose>

        </div>

        <div class="col-lg-5">

            <div class="card-body p-4 p-xl-5">

                <span class="badge bg-primary mb-3">

                    <i class="bi bi-tag"></i>
                    <c:out value="${video.categoryName}"/>

                </span>

                <h1 class="h2 fw-bold mb-3">
                    <c:out value="${video.title}"/>
                </h1>

                <div class="text-muted mb-4">

                    Mã video:

                    <strong>
                        <c:out value="${video.videoId}"/>
                    </strong>

                </div>

                <div class="row g-3 mb-4">

                    <div class="col-4">

                        <div class="statistic-box">

                            <div class="statistic-value">
                                <i class="bi bi-eye"></i>
                                ${video.views}
                            </div>

                            <div class="text-muted small">
                                Lượt xem
                            </div>

                        </div>
                    </div>

                    <div class="col-4">

                        <div class="statistic-box">

                            <div class="statistic-value text-danger">
                                <i class="bi bi-heart-fill"></i>
                                ${likeCount}
                            </div>

                            <div class="text-muted small">
                                Lượt thích
                            </div>

                        </div>
                    </div>

                    <div class="col-4">

                        <div class="statistic-box">

                            <div class="statistic-value text-success">
                                <i class="bi bi-share-fill"></i>
                                ${shareCount}
                            </div>

                            <div class="text-muted small">
                                Lượt chia sẻ
                            </div>

                        </div>
                    </div>

                </div>

                <hr>

                <h5 class="fw-bold">
                    <i class="bi bi-card-text"></i>
                    Mô tả
                </h5>

                <div class="description-box text-muted">

                    <c:choose>

                        <c:when test="${empty video.description}">
                            Video chưa có mô tả.
                        </c:when>

                        <c:otherwise>
                            <c:out value="${video.description}"/>
                        </c:otherwise>

                    </c:choose>

                </div>

                <div class="mt-4 d-flex align-items-center justify-content-between">

                    <div>
                        <c:choose>

                            <c:when test="${video.active}">

                                <span class="badge bg-success">
                                    <i class="bi bi-check-circle"></i>
                                    Đang hoạt động
                                </span>

                            </c:when>

                            <c:otherwise>

                                <span class="badge bg-secondary">
                                    <i class="bi bi-pause-circle"></i>
                                    Không hoạt động
                                </span>

                            </c:otherwise>

                        </c:choose>
                    </div>

                </div>

                <!-- Nút thêm vào giỏ hàng -->
                <div class="mt-4">
                    <c:choose>
                        <c:when test="${video.active}">
                            <form method="post"
                                  action="${pageContext.request.contextPath}/cart/add">
                                <input type="hidden"
                                       name="videoId"
                                       value="<c:out value='${video.videoId}'/>">

                                <button type="submit"
                                        class="btn btn-primary btn-lg w-100 shadow-sm py-2">
                                    <i class="bi bi-cart-plus me-1"></i>
                                    Thêm vào giỏ hàng
                                </button>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <button type="button"
                                    class="btn btn-secondary btn-lg w-100 py-2"
                                    disabled>
                                <i class="bi bi-slash-circle me-1"></i>
                                Tạm ngưng bán
                            </button>
                        </c:otherwise>
                    </c:choose>
                </div>

            </div>
        </div>

    </div>
</div>

</body>
</html>