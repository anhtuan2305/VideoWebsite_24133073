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
          ? 'Thêm video'
          : 'Cập nhật video'}
    </title>

    <style>

        .poster-preview {
            display: block;
            width: 100%;
            max-width: 360px;
            max-height: 220px;
            margin-top: 12px;
            border: 1px solid #dee2e6;
            border-radius: 10px;
            object-fit: cover;
        }

    </style>

</head>

<body>

<div class="row justify-content-center">

    <div class="col-xl-9">

        <div class="d-flex justify-content-between
                    align-items-center mb-4">

            <h1 class="h3 mb-0">

                <i class="bi bi-camera-video"></i>

                ${formAction == 'create'
                  ? 'Thêm video'
                  : 'Cập nhật video'}

            </h1>

            <a href="${pageContext.request.contextPath}/admin/videos"
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
                      enctype="multipart/form-data"
                      action="${pageContext.request.contextPath}/admin/videos/${formAction}">

                    <div class="row">

                        <div class="col-md-6 mb-3">

                            <label for="videoId"
                                   class="form-label">

                                Mã video

                            </label>

                            <input type="text"
                                   class="form-control"
                                   id="videoId"
                                   name="videoId"
                                   value="<c:out value='${video.videoId}'/>"
                                   maxlength="50"
                                   ${formAction == 'edit'
                                     ? 'readonly'
                                     : ''}
                                   placeholder="Để trống để tạo tự động">

                        </div>

                        <div class="col-md-6 mb-3">

                            <label for="categoryId"
                                   class="form-label">

                                Thể loại
                                <span class="text-danger">*</span>

                            </label>

                            <select class="form-select"
                                    id="categoryId"
                                    name="categoryId"
                                    required>

                                <option value="">
                                    -- Chọn thể loại --
                                </option>

                                <c:forEach var="category"
                                           items="${categories}">

                                    <option
                                        value="${category.categoryId}"

                                        ${category.categoryId
                                          == video.categoryId
                                          ? 'selected'
                                          : ''}>

                                        <c:out value="${category.categoryName}"/>

                                    </option>

                                </c:forEach>

                            </select>

                        </div>

                        <div class="col-12 mb-3">

                            <label for="title"
                                   class="form-label">

                                Tiêu đề video
                                <span class="text-danger">*</span>

                            </label>

                            <input type="text"
                                   class="form-control"
                                   id="title"
                                   name="title"
                                   value="<c:out value='${video.title}'/>"
                                   maxlength="200"
                                   required>

                        </div>

                        <div class="col-md-8 mb-3">

                            <label for="posterFile"
                                   class="form-label">

                                Chọn ảnh poster từ máy

                            </label>

                            <input type="file"
                                   class="form-control"
                                   id="posterFile"
                                   name="posterFile"
                                   accept=".jpg,.jpeg,.png,.gif,.webp,image/*"
                                   onchange="previewPoster(this)">

                            <div class="form-text">

                                Cho phép JPG, JPEG, PNG, GIF hoặc WEBP.
                                Kích thước tối đa 5 MB.

                                <c:if test="${formAction == 'edit'}">

                                    Nếu không chọn ảnh mới,
                                    hệ thống sẽ giữ ảnh hiện tại.

                                </c:if>

                            </div>

                            <c:choose>

                                <c:when test="${not empty video.poster}">

                                    <c:url var="currentPosterUrl"
                                           value="/images/${video.poster}"/>

                                    <img id="posterPreview"
                                         class="poster-preview"
                                         src="${currentPosterUrl}"
                                         alt="Poster hiện tại">

                                    <div class="form-text">

                                        Ảnh hiện tại:
                                        <c:out value="${video.poster}"/>

                                    </div>

                                </c:when>

                                <c:otherwise>

                                    <img id="posterPreview"
                                         class="poster-preview"
                                         src=""
                                         alt="Xem trước poster"
                                         style="display: none;">

                                </c:otherwise>

                            </c:choose>

                        </div>

                        <div class="col-md-4 mb-3">

                            <label for="views"
                                   class="form-label">

                                Lượt xem

                            </label>

                            <input type="number"
                                   class="form-control"
                                   id="views"
                                   name="views"
                                   value="${empty video.views
                                            ? 0
                                            : video.views}"
                                   min="0">

                        </div>

                        <div class="col-12 mb-3">

                            <label for="description"
                                   class="form-label">

                                Mô tả

                            </label>

                            <textarea class="form-control"
                                      id="description"
                                      name="description"
                                      rows="6"
                                      maxlength="500"><c:out value="${video.description}"/></textarea>

                        </div>

                        <div class="col-12 mb-4">

                            <div class="form-check form-switch">

                                <input type="checkbox"
                                       class="form-check-input"
                                       id="active"
                                       name="active"

                                       ${(formAction == 'create'
                                          or video.active)
                                          ? 'checked'
                                          : ''}>

                                <label class="form-check-label"
                                       for="active">

                                    Video đang hoạt động

                                </label>

                            </div>

                        </div>

                    </div>

                    <button type="submit"
                            class="btn btn-primary">

                        <i class="bi bi-save"></i>

                        ${formAction == 'create'
                          ? 'Thêm video'
                          : 'Lưu thay đổi'}

                    </button>

                    <a href="${pageContext.request.contextPath}/admin/videos"
                       class="btn btn-secondary">

                        Hủy

                    </a>

                </form>

            </div>

        </div>

    </div>

</div>

<script>

    function previewPoster(input) {

        const preview =
            document.getElementById("posterPreview");

        if (!input.files || !input.files[0]) {
            return;
        }

        const selectedFile = input.files[0];

        if (!selectedFile.type.startsWith("image/")) {

            alert("Vui lòng chọn một file hình ảnh.");

            input.value = "";

            return;
        }

        preview.src =
            URL.createObjectURL(selectedFile);

        preview.style.display = "block";
    }

</script>

</body>

</html>