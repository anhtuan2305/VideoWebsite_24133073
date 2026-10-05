<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Giỏ hàng của bạn</title>
</head>
<body>

<div class="d-flex justify-content-between align-items-center mb-4">
    <h1 class="h3 mb-1"><i class="bi bi-cart3"></i> Giỏ hàng của bạn</h1>
    <a href="${pageContext.request.contextPath}/home" class="btn btn-outline-secondary">
        <i class="bi bi-arrow-left"></i> Tiếp tục chọn
    </a>
</div>

<c:if test="${not empty param.success}">
    <div class="alert alert-success alert-dismissible fade show">
        <i class="bi bi-check-circle"></i> Đặt hàng COD thành công! Chúng tôi sẽ sớm giao hàng.
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
</c:if>

<div class="row">
    <div class="col-lg-8">
        <div class="card border-0 shadow-sm mb-4">
            <div class="card-body">
                <c:choose>
                    <c:when test="${empty sessionScope.cart}">
                        <div class="text-center text-muted py-5">
                            <i class="bi bi-cart-x fs-1"></i>
                            <p class="mt-3">Giỏ hàng đang trống.</p>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="table-responsive">
                            <table class="table align-middle">
                                <thead class="table-light">
                                    <tr>
                                        <th>Sản phẩm</th>
                                        <th>Đơn giá</th>
                                        <th>Số lượng</th>
                                        <th>Thành tiền</th>
                                        <th>Xóa</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:set var="totalCart" value="0"/>
                                    <c:forEach var="item" items="${sessionScope.cart}">
                                        <c:set var="totalCart" value="${totalCart + item.totalPrice}"/>
                                        <tr>
                                            <td>
                                                <div class="d-flex align-items-center">
                                                    <img src="${pageContext.request.contextPath}/images/${item.poster}" 
                                                         class="rounded" style="width: 60px; height: 40px; object-fit: cover; margin-right: 10px;">
                                                    <span class="fw-semibold"><c:out value="${item.title}"/></span>
                                                </div>
                                            </td>
                                            <td><fmt:formatNumber value="${item.price}" pattern="#,###"/> đ</td>
                                            <td>
                                                <form action="${pageContext.request.contextPath}/cart/update" method="post" class="d-flex">
                                                    <input type="hidden" name="videoId" value="${item.videoId}">
                                                    <input type="number" name="quantity" value="${item.quantity}" min="1" max="10" 
                                                           class="form-control form-control-sm text-center" style="width: 70px;" onchange="this.form.submit()">
                                                </form>
                                            </td>
                                            <td class="text-primary fw-bold">
                                                <fmt:formatNumber value="${item.totalPrice}" pattern="#,###"/> đ
                                            </td>
                                            <td>
                                                <form action="${pageContext.request.contextPath}/cart/remove" method="post">
                                                    <input type="hidden" name="videoId" value="${item.videoId}">
                                                    <button type="submit" class="btn btn-sm btn-outline-danger"><i class="bi bi-trash"></i></button>
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>

    <!-- Cột chuyển hướng sang trang Checkout độc lập -->
    <c:if test="${not empty sessionScope.cart}">
        <div class="col-lg-4">
            <div class="card border-0 shadow-sm">
                <div class="card-body p-4">
                    <h5 class="fw-bold mb-3">Tóm tắt đơn hàng</h5>
                    <div class="d-flex justify-content-between mb-3">
                        <span class="fs-5">Tổng cộng:</span>
                        <span class="fs-5 fw-bold text-danger"><fmt:formatNumber value="${totalCart}" pattern="#,###"/> đ</span>
                    </div>
                    <hr>
                    <a href="${pageContext.request.contextPath}/checkout" class="btn btn-success w-100 py-2 text-center text-decoration-none">
                        <i class="bi bi-credit-card"></i> Tiến hành thanh toán
                    </a>
                </div>
            </div>
        </div>
    </c:if>
</div>
</body>
</html>