package vn.trananhtuan.video.service;

import java.util.List;

import vn.trananhtuan.video.model.Category_24133073;

public interface ICategoryService_24133073 {

    Category_24133073 findById(int categoryId);

    List<Category_24133073> findAll();

    List<Category_24133073> findAll(
            int page,
            int pageSize
    );

    int count();

    boolean insert(Category_24133073 category);

    boolean update(Category_24133073 category);

    boolean delete(int categoryId);
}