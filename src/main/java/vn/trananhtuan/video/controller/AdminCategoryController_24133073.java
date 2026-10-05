package vn.trananhtuan.video.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vn.trananhtuan.video.model.Category_24133073;
import vn.trananhtuan.video.service.CategoryServiceImpl_24133073;
import vn.trananhtuan.video.service.ICategoryService_24133073;

@WebServlet(
        urlPatterns = {
                "/admin/categories",
                "/admin/categories/*"
        }
)
public class AdminCategoryController_24133073
        extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final int PAGE_SIZE = 6;

    private ICategoryService_24133073 categoryService;

    @Override
    public void init() throws ServletException {

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
            showCategoryList(request, response);
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
                createCategory(request, response);
                break;

            case "/edit":
                updateCategory(request, response);
                break;

            case "/delete":
                deleteCategory(request, response);
                break;

            default:
                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND
                );
                break;
        }
    }

    private void showCategoryList(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        int page = parsePositiveInteger(
                request.getParameter("page"),
                1
        );

        int totalCategories =
                categoryService.count();

        int totalPages = (int) Math.ceil(
                (double) totalCategories / PAGE_SIZE
        );

        if (totalPages < 1) {
            totalPages = 1;
        }

        if (page > totalPages) {
            page = totalPages;
        }

        List<Category_24133073> categories =
                categoryService.findAll(
                        page,
                        PAGE_SIZE
                );

        request.setAttribute(
                "categories",
                categories
        );

        request.setAttribute(
                "currentPage",
                page
        );

        request.setAttribute(
                "totalPages",
                totalPages
        );

        request.setAttribute(
                "totalCategories",
                totalCategories
        );

        request.getRequestDispatcher(
                "/WEB-INF/views/admin/categories/list.jsp"
        ).forward(request, response);
    }

    private void showCreateForm(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute(
                "formAction",
                "create"
        );

        request.getRequestDispatcher(
                "/WEB-INF/views/admin/categories/form.jsp"
        ).forward(request, response);
    }

    private void showEditForm(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        int categoryId =
                parsePositiveInteger(
                        request.getParameter("id"),
                        0
                );

        Category_24133073 category =
                categoryService.findById(categoryId);

        if (category == null) {

            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Không tìm thấy thể loại."
            );

            return;
        }

        request.setAttribute(
                "category",
                category
        );

        request.setAttribute(
                "formAction",
                "edit"
        );

        request.getRequestDispatcher(
                "/WEB-INF/views/admin/categories/form.jsp"
        ).forward(request, response);
    }

    private void createCategory(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Category_24133073 category =
                readCategoryFromRequest(request);

        if (!isValidCategory(category)) {

            forwardFormWithError(
                    request,
                    response,
                    category,
                    "Vui lòng nhập tên và mã thể loại.",
                    "create"
            );

            return;
        }

        boolean created =
                categoryService.insert(category);

        if (!created) {

            forwardFormWithError(
                    request,
                    response,
                    category,
                    "Không thể thêm thể loại.",
                    "create"
            );

            return;
        }

        response.sendRedirect(
                request.getContextPath()
                        + "/admin/categories"
                        + "?success=created"
        );
    }

    private void updateCategory(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Category_24133073 category =
                readCategoryFromRequest(request);

        Category_24133073 oldCategory =
                categoryService.findById(
                        category.getCategoryId()
                );

        if (oldCategory == null) {

            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Không tìm thấy thể loại."
            );

            return;
        }

        if (!isValidCategory(category)) {

            forwardFormWithError(
                    request,
                    response,
                    category,
                    "Vui lòng nhập tên và mã thể loại.",
                    "edit"
            );

            return;
        }

        if (category.getImages() == null
                || category.getImages().isBlank()) {

            category.setImages(
                    oldCategory.getImages()
            );
        }

        boolean updated =
                categoryService.update(category);

        if (!updated) {

            forwardFormWithError(
                    request,
                    response,
                    category,
                    "Không thể cập nhật thể loại.",
                    "edit"
            );

            return;
        }

        response.sendRedirect(
                request.getContextPath()
                        + "/admin/categories"
                        + "?success=updated"
        );
    }

    private void deleteCategory(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        int categoryId =
                parsePositiveInteger(
                        request.getParameter("id"),
                        0
                );

        boolean deleted =
                categoryService.delete(categoryId);

        String result =
                deleted
                        ? "success=deleted"
                        : "error=category-has-videos";

        response.sendRedirect(
                request.getContextPath()
                        + "/admin/categories?"
                        + result
        );
    }

    private Category_24133073
            readCategoryFromRequest(
                    HttpServletRequest request) {

        Category_24133073 category =
                new Category_24133073();

        category.setCategoryId(
                parsePositiveInteger(
                        request.getParameter(
                                "categoryId"
                        ),
                        0
                )
        );

        category.setCategoryName(
                normalize(
                        request.getParameter(
                                "categoryName"
                        )
                )
        );

        category.setCategoryCode(
                normalize(
                        request.getParameter(
                                "categoryCode"
                        )
                )
        );

        category.setImages(
                normalize(
                        request.getParameter("images")
                )
        );

        category.setStatus(
                request.getParameter("status") != null
        );

        return category;
    }

    private boolean isValidCategory(
            Category_24133073 category) {

        return category != null
                && category.getCategoryName() != null
                && !category.getCategoryName().isBlank()
                && category.getCategoryCode() != null
                && !category.getCategoryCode().isBlank();
    }

    private void forwardFormWithError(
            HttpServletRequest request,
            HttpServletResponse response,
            Category_24133073 category,
            String error,
            String formAction)
            throws ServletException, IOException {

        request.setAttribute(
                "category",
                category
        );

        request.setAttribute(
                "error",
                error
        );

        request.setAttribute(
                "formAction",
                formAction
        );

        request.getRequestDispatcher(
                "/WEB-INF/views/admin/categories/form.jsp"
        ).forward(request, response);
    }

    private int parsePositiveInteger(
            String value,
            int defaultValue) {

        try {
            int number = Integer.parseInt(value);

            return number >= 0
                    ? number
                    : defaultValue;

        } catch (NumberFormatException e) {
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