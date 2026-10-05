package vn.trananhtuan.video.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import vn.trananhtuan.video.model.Category_24133073;
import vn.trananhtuan.video.model.Video_24133073;
import vn.trananhtuan.video.service.CategoryServiceImpl_24133073;
import vn.trananhtuan.video.service.ICategoryService_24133073;
import vn.trananhtuan.video.service.IVideoService_24133073;
import vn.trananhtuan.video.service.VideoServiceImpl_24133073;

@WebServlet(
        urlPatterns = {
                "/admin/videos",
                "/admin/videos/*"
        }
)
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 5 * 1024 * 1024,
        maxRequestSize = 10 * 1024 * 1024
)
public class AdminVideoController_24133073
        extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final int PAGE_SIZE = 6;

    private static final String IMAGE_FOLDER = "/images";

    private IVideoService_24133073 videoService;

    private ICategoryService_24133073 categoryService;

    @Override
    public void init() throws ServletException {

        videoService =
                new VideoServiceImpl_24133073();

        categoryService =
                new CategoryServiceImpl_24133073();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String path = request.getPathInfo();

        if (path == null || "/".equals(path)) {

            showVideoList(request, response);
            return;
        }

        switch (path) {

            case "/create":
                showCreateForm(request, response);
                break;

            case "/edit":
                showEditForm(request, response);
                break;

            default:
                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND
                );
                break;
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String path = request.getPathInfo();

        if (path == null) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST
            );

            return;
        }

        switch (path) {

            case "/create":
                createVideo(request, response);
                break;

            case "/edit":
                updateVideo(request, response);
                break;

            case "/delete":
                deleteVideo(request, response);
                break;

            default:
                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND
                );
                break;
        }
    }

    private void showVideoList(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        int page = parsePositiveInteger(
                request.getParameter("page"),
                1
        );

        int totalVideos = videoService.count();

        int totalPages = (int) Math.ceil(
                (double) totalVideos / PAGE_SIZE
        );

        if (totalPages < 1) {
            totalPages = 1;
        }

        if (page > totalPages) {
            page = totalPages;
        }

        List<Video_24133073> videos =
                videoService.findAll(
                        page,
                        PAGE_SIZE
                );

        request.setAttribute("videos", videos);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalVideos", totalVideos);

        request.getRequestDispatcher(
                "/WEB-INF/views/admin/videos/list.jsp"
        ).forward(request, response);
    }

    private void showCreateForm(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        loadCategories(request);

        request.setAttribute(
                "video",
                new Video_24133073()
        );

        request.setAttribute(
                "formAction",
                "create"
        );

        request.getRequestDispatcher(
                "/WEB-INF/views/admin/videos/form.jsp"
        ).forward(request, response);
    }

    private void showEditForm(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String videoId =
                normalize(request.getParameter("id"));

        Video_24133073 video =
                videoService.findById(videoId);

        if (video == null) {

            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Không tìm thấy video."
            );

            return;
        }

        loadCategories(request);

        request.setAttribute("video", video);
        request.setAttribute(
                "formAction",
                "edit"
        );

        request.getRequestDispatcher(
                "/WEB-INF/views/admin/videos/form.jsp"
        ).forward(request, response);
    }

    private void createVideo(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Video_24133073 video =
                readVideoFromRequest(request);

        if (!isValidVideo(video)) {

            forwardFormWithError(
                    request,
                    response,
                    video,
                    "Vui lòng nhập tiêu đề và chọn thể loại.",
                    "create"
            );

            return;
        }

        if (video.getVideoId() != null
                && !video.getVideoId().isBlank()
                && videoService.findById(
                        video.getVideoId()) != null) {

            forwardFormWithError(
                    request,
                    response,
                    video,
                    "Mã video đã tồn tại.",
                    "create"
            );

            return;
        }

        try {

            String posterFileName =
                    savePosterFile(request);

            if (posterFileName != null) {
                video.setPoster(posterFileName);
            }

        } catch (IllegalArgumentException exception) {

            forwardFormWithError(
                    request,
                    response,
                    video,
                    exception.getMessage(),
                    "create"
            );

            return;
        }

        boolean created =
                videoService.insert(video);

        if (!created) {

            forwardFormWithError(
                    request,
                    response,
                    video,
                    "Không thể thêm video.",
                    "create"
            );

            return;
        }

        response.sendRedirect(
                request.getContextPath()
                        + "/admin/videos?success=created"
        );
    }

    private void updateVideo(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Video_24133073 video =
                readVideoFromRequest(request);

        Video_24133073 oldVideo =
                videoService.findById(
                        video.getVideoId()
                );

        if (oldVideo == null) {

            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Không tìm thấy video."
            );

            return;
        }

        if (!isValidVideo(video)) {

            video.setPoster(oldVideo.getPoster());

            forwardFormWithError(
                    request,
                    response,
                    video,
                    "Vui lòng nhập tiêu đề và chọn thể loại.",
                    "edit"
            );

            return;
        }

        try {

            String newPoster =
                    savePosterFile(request);

            if (newPoster != null) {

                video.setPoster(newPoster);

            } else {

                // Không chọn ảnh mới thì giữ lại ảnh cũ
                video.setPoster(
                        oldVideo.getPoster()
                );
            }

        } catch (IllegalArgumentException exception) {

            video.setPoster(oldVideo.getPoster());

            forwardFormWithError(
                    request,
                    response,
                    video,
                    exception.getMessage(),
                    "edit"
            );

            return;
        }

        boolean updated =
                videoService.update(video);

        if (!updated) {

            forwardFormWithError(
                    request,
                    response,
                    video,
                    "Không thể cập nhật video.",
                    "edit"
            );

            return;
        }

        response.sendRedirect(
                request.getContextPath()
                        + "/admin/videos?success=updated"
        );
    }

    private void deleteVideo(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String videoId =
                normalize(request.getParameter("id"));

        boolean deleted =
                videoService.delete(videoId);

        String result =
                deleted
                        ? "success=deleted"
                        : "error=delete-failed";

        response.sendRedirect(
                request.getContextPath()
                        + "/admin/videos?"
                        + result
        );
    }

    private Video_24133073 readVideoFromRequest(
            HttpServletRequest request) {

        Video_24133073 video =
                new Video_24133073();

        video.setVideoId(
                normalize(
                        request.getParameter("videoId")
                )
        );

        video.setTitle(
                normalize(
                        request.getParameter("title")
                )
        );

        video.setViews(
                parsePositiveInteger(
                        request.getParameter("views"),
                        0
                )
        );

        video.setDescription(
                normalize(
                        request.getParameter("description")
                )
        );

        video.setActive(
                request.getParameter("active") != null
        );

        video.setCategoryId(
                parsePositiveInteger(
                        request.getParameter("categoryId"),
                        0
                )
        );

        return video;
    }

    private String savePosterFile(
            HttpServletRequest request)
            throws IOException, ServletException {

        Part posterPart =
                request.getPart("posterFile");

        if (posterPart == null
                || posterPart.getSize() == 0) {

            return null;
        }

        String originalFileName =
                Paths.get(
                        posterPart.getSubmittedFileName()
                ).getFileName().toString();

        String extension =
                getFileExtension(originalFileName);

        if (!isAllowedImageExtension(extension)) {

            throw new IllegalArgumentException(
                    "Chỉ được chọn ảnh JPG, JPEG, PNG, GIF hoặc WEBP."
            );
        }

        String contentType =
                posterPart.getContentType();

        if (contentType == null
                || !contentType.startsWith("image/")) {

            throw new IllegalArgumentException(
                    "File được chọn không phải là hình ảnh."
            );
        }

        String generatedFileName =
                "poster_"
                        + UUID.randomUUID()
                        .toString()
                        .replace("-", "")
                        + extension;

        String uploadDirectory =
                getServletContext()
                        .getRealPath(IMAGE_FOLDER);

        if (uploadDirectory == null) {

            throw new IOException(
                    "Không xác định được thư mục lưu ảnh."
            );
        }

        Path uploadPath =
                Paths.get(uploadDirectory);

        Files.createDirectories(uploadPath);

        Path destination =
                uploadPath.resolve(generatedFileName);

        try (InputStream inputStream =
                     posterPart.getInputStream()) {

            Files.copy(
                    inputStream,
                    destination,
                    StandardCopyOption.REPLACE_EXISTING
            );
        }

        return generatedFileName;
    }

    private String getFileExtension(
            String fileName) {

        if (fileName == null) {
            return "";
        }

        int dotIndex =
                fileName.lastIndexOf('.');

        if (dotIndex < 0) {
            return "";
        }

        return fileName
                .substring(dotIndex)
                .toLowerCase();
    }

    private boolean isAllowedImageExtension(
            String extension) {

        return ".jpg".equals(extension)
                || ".jpeg".equals(extension)
                || ".png".equals(extension)
                || ".gif".equals(extension)
                || ".webp".equals(extension);
    }

    private boolean isValidVideo(
            Video_24133073 video) {

        return video != null
                && video.getTitle() != null
                && !video.getTitle().isBlank()
                && video.getCategoryId() > 0;
    }

    private void forwardFormWithError(
            HttpServletRequest request,
            HttpServletResponse response,
            Video_24133073 video,
            String error,
            String formAction)
            throws ServletException, IOException {

        loadCategories(request);

        request.setAttribute("video", video);
        request.setAttribute("error", error);
        request.setAttribute(
                "formAction",
                formAction
        );

        request.getRequestDispatcher(
                "/WEB-INF/views/admin/videos/form.jsp"
        ).forward(request, response);
    }

    private void loadCategories(
            HttpServletRequest request) {

        List<Category_24133073> categories =
                categoryService.findAll();

        request.setAttribute(
                "categories",
                categories
        );
    }

    private int parsePositiveInteger(
            String value,
            int defaultValue) {

        try {

            int number =
                    Integer.parseInt(value);

            return number >= 0
                    ? number
                    : defaultValue;

        } catch (NumberFormatException exception) {

            return defaultValue;
        }
    }

    private String normalize(String value) {

        if (value == null) {
            return null;
        }

        return value.trim();
    }
}