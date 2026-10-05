package vn.trananhtuan.video.dao;

import java.util.List;

import vn.trananhtuan.video.model.Video_24133073;

public interface IVideoDAO_24133073 {

    Video_24133073 findById(String videoId);

    List<Video_24133073> findAll(
            int page,
            int pageSize
    );

    List<Video_24133073> findByCategory(
            int categoryId,
            int page,
            int pageSize
    );

    int count();

    int countByCategory(int categoryId);

    int countLikes(String videoId);

    int countShares(String videoId);

    boolean insert(Video_24133073 video);

    boolean update(Video_24133073 video);

    boolean delete(String videoId);

    boolean increaseViews(String videoId);
}