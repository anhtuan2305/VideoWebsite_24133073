package vn.trananhtuan.video.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vn.trananhtuan.video.model.Category_24133073;
import vn.trananhtuan.video.model.Video_24133073;
import vn.trananhtuan.video.service.CategoryServiceImpl_24133073;
import vn.trananhtuan.video.service.ICategoryService_24133073;
import vn.trananhtuan.video.service.IVideoService_24133073;
import vn.trananhtuan.video.service.VideoServiceImpl_24133073;

@WebServlet(urlPatterns = "/home")
public class HomeController_24133073
        extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final int PAGE_SIZE = 3;

    private ICategoryService_24133073 categoryService;
    private IVideoService_24133073 videoService;

    @Override
    public void init() throws ServletException {

        categoryService =
                new CategoryServiceImpl_24133073();

        videoService =
                new VideoServiceImpl_24133073();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        int page = parsePositiveInteger(
                request.getParameter("page"),
                1
        );

        int selectedCategoryId =
                parsePositiveInteger(
                        request.getParameter("categoryId"),
                        0
                );

        List<Category_24133073> allCategories =
                categoryService.findAll();

        List<Category_24133073> activeCategories =
                getActiveCategories(allCategories);

        List<Category_24133073> displayedCategories =
                filterCategories(
                        activeCategories,
                        selectedCategoryId
                );

        Map<Integer, List<Video_24133073>>
                videosByCategory =
                new LinkedHashMap<>();

        Map<Integer, Integer>
                totalPagesByCategory =
                new LinkedHashMap<>();

        Map<Integer, Integer>
                videoCountByCategory =
                new LinkedHashMap<>();

        for (Category_24133073 category
                : displayedCategories) {

            int categoryId =
                    category.getCategoryId();

            int requestedPage =
                    selectedCategoryId > 0
                            ? page
                            : 1;

            int totalVideos =
                    videoService.countByCategory(
                            categoryId
                    );

            int totalPages =
                    calculateTotalPages(totalVideos);

            if (totalPages > 0
                    && requestedPage > totalPages) {

                requestedPage = totalPages;
            }

            List<Video_24133073> videos =
                    videoService.findByCategory(
                            categoryId,
                            requestedPage,
                            PAGE_SIZE
                    );

            videosByCategory.put(
                    categoryId,
                    videos
            );

            totalPagesByCategory.put(
                    categoryId,
                    totalPages
            );

            videoCountByCategory.put(
                    categoryId,
                    totalVideos
            );

            if (selectedCategoryId == categoryId) {
                page = requestedPage;
            }
        }

        request.setAttribute(
                "categories",
                activeCategories
        );

        request.setAttribute(
                "displayedCategories",
                displayedCategories
        );

        request.setAttribute(
                "videosByCategory",
                videosByCategory
        );

        request.setAttribute(
                "totalPagesByCategory",
                totalPagesByCategory
        );

        request.setAttribute(
                "videoCountByCategory",
                videoCountByCategory
        );

        request.setAttribute(
                "selectedCategoryId",
                selectedCategoryId
        );

        request.setAttribute(
                "currentPage",
                page
        );

        request.setAttribute(
                "pageSize",
                PAGE_SIZE
        );

        request.getRequestDispatcher(
                "/WEB-INF/views/home.jsp"
        ).forward(request, response);
    }

    private List<Category_24133073>
            getActiveCategories(
                    List<Category_24133073> categories) {

        List<Category_24133073> result =
                new ArrayList<>();

        if (categories == null) {
            return result;
        }

        for (Category_24133073 category
                : categories) {

            if (category.isStatus()) {
                result.add(category);
            }
        }

        return result;
    }

    private List<Category_24133073>
            filterCategories(
                    List<Category_24133073> categories,
                    int selectedCategoryId) {

        if (selectedCategoryId <= 0) {
            return categories;
        }

        List<Category_24133073> result =
                new ArrayList<>();

        for (Category_24133073 category
                : categories) {

            if (category.getCategoryId()
                    == selectedCategoryId) {

                result.add(category);
                break;
            }
        }

        return result;
    }

    private int calculateTotalPages(
            int totalVideos) {

        if (totalVideos <= 0) {
            return 0;
        }

        return (int) Math.ceil(
                (double) totalVideos / PAGE_SIZE
        );
    }

    private int parsePositiveInteger(
            String value,
            int defaultValue) {

        if (value == null || value.isBlank()) {
            return defaultValue;
        }

        try {
            int number = Integer.parseInt(value);

            return number > 0
                    ? number
                    : defaultValue;

        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}