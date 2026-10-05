package vn.trananhtuan.video.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vn.trananhtuan.video.model.Video_24133073;
import vn.trananhtuan.video.service.IVideoService_24133073;
import vn.trananhtuan.video.service.VideoServiceImpl_24133073;

@WebServlet(urlPatterns = "/video/detail")
public class VideoDetailController_24133073
        extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private IVideoService_24133073 videoService;

    @Override
    public void init() throws ServletException {

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

        String videoId =
                request.getParameter("id");

        if (videoId == null
                || videoId.isBlank()) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Thiếu mã video."
            );

            return;
        }

        Video_24133073 video =
                videoService.getVideoDetail(
                        videoId.trim()
                );

        if (video == null) {

            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Không tìm thấy video."
            );

            return;
        }

        int likeCount =
                videoService.countLikes(
                        video.getVideoId()
                );

        int shareCount =
                videoService.countShares(
                        video.getVideoId()
                );

        request.setAttribute(
                "video",
                video
        );

        request.setAttribute(
                "likeCount",
                likeCount
        );

        request.setAttribute(
                "shareCount",
                shareCount
        );

        request.getRequestDispatcher(
                "/WEB-INF/views/video/detail.jsp"
        ).forward(request, response);
    }
}