package vn.trananhtuan.video.service;

import java.util.List;

import vn.trananhtuan.video.dao.CategoryDAOImpl_24133073;
import vn.trananhtuan.video.dao.ICategoryDAO_24133073;
import vn.trananhtuan.video.model.Category_24133073;

public class CategoryServiceImpl_24133073
        implements ICategoryService_24133073 {

    private final ICategoryDAO_24133073 categoryDAO;

    public CategoryServiceImpl_24133073() {
        this.categoryDAO =
                new CategoryDAOImpl_24133073();
    }

    public CategoryServiceImpl_24133073(
            ICategoryDAO_24133073 categoryDAO) {

        this.categoryDAO = categoryDAO;
    }

    @Override
    public Category_24133073 findById(
            int categoryId) {

        if (categoryId <= 0) {
            return null;
        }

        return categoryDAO.findById(categoryId);
    }

    @Override
    public List<Category_24133073> findAll() {
        return categoryDAO.findAll();
    }

    @Override
    public List<Category_24133073> findAll(
            int page,
            int pageSize) {

        if (page < 1) {
            page = 1;
        }

        if (pageSize < 1) {
            pageSize = 6;
        }

        return categoryDAO.findAll(page, pageSize);
    }

    @Override
    public int count() {
        return categoryDAO.count();
    }

    @Override
    public boolean insert(
            Category_24133073 category) {

        if (!isValidCategory(category)) {
            return false;
        }

        normalizeCategory(category);

        return categoryDAO.insert(category);
    }

    @Override
    public boolean update(
            Category_24133073 category) {

        if (category == null
                || category.getCategoryId() <= 0
                || !isValidCategory(category)) {

            return false;
        }

        if (categoryDAO.findById(
                category.getCategoryId()) == null) {

            return false;
        }

        normalizeCategory(category);

        return categoryDAO.update(category);
    }

    @Override
    public boolean delete(int categoryId) {

        if (categoryId <= 0) {
            return false;
        }

        return categoryDAO.delete(categoryId);
    }

    private boolean isValidCategory(
            Category_24133073 category) {

        return category != null
                && category.getCategoryName() != null
                && !category.getCategoryName().isBlank()
                && category.getCategoryCode() != null
                && !category.getCategoryCode().isBlank();
    }

    private void normalizeCategory(
            Category_24133073 category) {

        category.setCategoryName(
                category.getCategoryName().trim()
        );

        category.setCategoryCode(
                category.getCategoryCode().trim()
        );

        if (category.getImages() != null) {
            String images = category.getImages().trim();

            category.setImages(
                    images.isEmpty() ? null : images
            );
        }
    }
}