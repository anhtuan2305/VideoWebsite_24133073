package vn.trananhtuan.video.config;

import javax.servlet.annotation.WebFilter;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

@WebFilter(urlPatterns = "/*")
public class MySiteMeshFilter_24133073
        extends ConfigurableSiteMeshFilter {

    private static final long serialVersionUID = 1L;

    @Override
    protected void applyCustomConfiguration(
            SiteMeshFilterBuilder builder) {

        builder
                // Giao diện dành riêng cho Admin
                .addDecoratorPath(
                        "/admin/*",
                        "/WEB-INF/decorators/admin.jsp"
                )

                // Giao diện chung dành cho người dùng
                .addDecoratorPath(
                        "/*",
                        "/WEB-INF/decorators/user.jsp"
                )

                // Không trang trí các tài nguyên tĩnh
                .addExcludedPath("/assets/*")
                .addExcludedPath("/css/*")
                .addExcludedPath("/js/*")
                .addExcludedPath("/images/*");
    }
}