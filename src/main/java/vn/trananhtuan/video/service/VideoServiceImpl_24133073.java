package vn.trananhtuan.video.service;

import java.util.List;
import java.util.UUID;

import vn.trananhtuan.video.dao.IVideoDAO_24133073;
import vn.trananhtuan.video.dao.VideoDAOImpl_24133073;
import vn.trananhtuan.video.model.Video_24133073;

public class VideoServiceImpl_24133073
        implements IVideoService_24133073 {

    private final IVideoDAO_24133073 videoDAO;

    public VideoServiceImpl_24133073() {
        this.videoDAO = new VideoDAOImpl_24133073();
    }

    public VideoServiceImpl_24133073(
            IVideoDAO_24133073 videoDAO) {

        this.videoDAO = videoDAO;
    }

    @Override
    public Video_24133073 findById(String videoId) {

        if (videoId == null || videoId.isBlank()) {
            return null;
        }

        return videoDAO.findById(videoId.trim());
    }

    @Override
    public Video_24133073 getVideoDetail(
            String videoId) {

        if (videoId == null || videoId.isBlank()) {
            return null;
        }

        String normalizedId = videoId.trim();

        Video_24133073 video =
                videoDAO.findById(normalizedId);

        if (video == null) {
            return null;
        }

        videoDAO.increaseViews(normalizedId);

        return videoDAO.findById(normalizedId);
    }

    @Override
    public List<Video_24133073> findAll(
            int page,
            int pageSize) {

        if (page < 1) {
            page = 1;
        }

        if (pageSize < 1) {
            pageSize = 3;
        }

        return videoDAO.findAll(page, pageSize);
    }

    @Override
    public List<Video_24133073> findByCategory(
            int categoryId,
            int page,
            int pageSize) {

        if (categoryId <= 0) {
            return List.of();
        }

        if (page < 1) {
            page = 1;
        }

        if (pageSize < 1) {
            pageSize = 3;
        }

        return videoDAO.findByCategory(
                categoryId,
                page,
                pageSize
        );
    }

    @Override
    public int count() {
        return videoDAO.count();
    }

    @Override
    public int countByCategory(int categoryId) {

        if (categoryId <= 0) {
            return 0;
        }

        return videoDAO.countByCategory(categoryId);
    }

    @Override
    public int countLikes(String videoId) {

        if (videoId == null || videoId.isBlank()) {
            return 0;
        }

        return videoDAO.countLikes(videoId.trim());
    }

    @Override
    public int countShares(String videoId) {

        if (videoId == null || videoId.isBlank()) {
            return 0;
        }

        return videoDAO.countShares(videoId.trim());
    }

    @Override
    public boolean insert(Video_24133073 video) {

        if (!isValidVideo(video)) {
            return false;
        }

        if (video.getVideoId() == null
                || video.getVideoId().isBlank()) {

            video.setVideoId(generateVideoId());
        }

        if (videoDAO.findById(
                video.getVideoId().trim()) != null) {

            return false;
        }

        normalizeVideo(video);

        if (video.getViews() < 0) {
            video.setViews(0);
        }

        return videoDAO.insert(video);
    }

    @Override
    public boolean update(Video_24133073 video) {

        if (video == null
                || video.getVideoId() == null
                || video.getVideoId().isBlank()
                || !isValidVideo(video)) {

            return false;
        }

        if (videoDAO.findById(
                video.getVideoId().trim()) == null) {

            return false;
        }

        normalizeVideo(video);

        if (video.getViews() < 0) {
            video.setViews(0);
        }

        return videoDAO.update(video);
    }

    @Override
    public boolean delete(String videoId) {

        if (videoId == null || videoId.isBlank()) {
            return false;
        }

        return videoDAO.delete(videoId.trim());
    }

    @Override
    public boolean increaseViews(String videoId) {

        if (videoId == null || videoId.isBlank()) {
            return false;
        }

        return videoDAO.increaseViews(videoId.trim());
    }

    private boolean isValidVideo(
            Video_24133073 video) {

        return video != null
                && video.getTitle() != null
                && !video.getTitle().isBlank()
                && video.getCategoryId() > 0;
    }

    private void normalizeVideo(
            Video_24133073 video) {

        if (video.getVideoId() != null) {
            video.setVideoId(
                    video.getVideoId().trim()
            );
        }

        video.setTitle(
                video.getTitle().trim()
        );

        if (video.getPoster() != null) {
            String poster = video.getPoster().trim();

            video.setPoster(
                    poster.isEmpty() ? null : poster
            );
        }

        if (video.getDescription() != null) {
            String description =
                    video.getDescription().trim();

            video.setDescription(
                    description.isEmpty()
                            ? null
                            : description
            );
        }
    }

    private String generateVideoId() {

        String randomValue = UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 12)
                .toUpperCase();

        return "VID" + randomValue;
    }
}