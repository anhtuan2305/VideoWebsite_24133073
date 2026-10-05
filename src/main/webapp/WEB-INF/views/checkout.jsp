<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thanh toán đơn hàng</title>
</head>
<body>

<div class="d-flex justify-content-between align-items-center mb-4">
    <h1 class="h3 mb-1"><i class="bi bi-credit-card"></i> Xác nhận thanh toán COD</h1>
    <a href="${pageContext.request.contextPath}/cart" class="btn btn-outline-secondary">
        <i class="bi bi-arrow-left"></i> Quay lại giỏ hàng
    </a>
</div>

<div class="row">
    <!-- Thông tin người nhận -->
    <div class="col-lg-7">
        <div class="card border-0 shadow-sm mb-4">
            <div class="card-body p-4">
                <h5 class="fw-bold mb-3"><i class="bi bi-geo-alt"></i> Địa chỉ giao hàng</h5>
                <form action="${pageContext.request.contextPath}/checkout" method="post" id="checkoutForm">
                    <div class="mb-3">
                        <label class="form-label">Họ và tên người nhận</label>
                        <input type="text" class="form-control" name="fullname" value="${sessionScope.currentUser.fullname}" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Số điện thoại</label>
                        <input type="text" class="form-control" name="phone" value="${sessionScope.currentUser.phone}" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Địa chỉ nhận hàng chi tiết</label>
                        <textarea class="form-control" name="address" rows="3" placeholder="Số nhà, tên đường, phường/xã, quận/huyện..." required></textarea>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Hình thức thanh toán</label>
                        <input type="text" class="form-control" value="Thanh toán khi nhận hàng (COD)" readonly>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- Tóm tắt đơn hàng -->
    <div class="col-lg-5">
        <div class="card border-0 shadow-sm">
            <div class="card-body p-4">
                <h5 class="fw-bold mb-3">Tóm tắt đơn hàng</h5>
                <ul class="list-group list-group-flush mb-3">
                    <c:set var="totalCheckout" value="0"/>
                    <c:forEach var="item" items="${sessionScope.cart}">
                        <c:set var="totalCheckout" value="${totalCheckout + item.totalPrice}"/>
                        <li class="list-group-item d-flex justify-content-between align-items-center px-0">
                            <div>
                                <h6 class="my-0"><c:out value="${item.title}"/></h6>
                                <small class="text-muted">SL: ${item.quantity}</small>
                            </div>
                            <span class="text-primary"><fmt:formatNumber value="${item.totalPrice}" pattern="#,###"/> đ</span>
                        </li>
                    </c:forEach>
                </ul>
                <div class="d-flex justify-content-between mb-4">
                    <span class="fs-5 fw-bold">Tổng thanh toán:</span>
                    <span class="fs-5 fw-bold text-danger"><fmt:formatNumber value="${totalCheckout}" pattern="#,###"/> đ</span>
                </div>
                <button type="submit" form="checkoutForm" class="btn btn-success w-100 py-2 fw-semibold">
                    <i class="bi bi-check-circle"></i> Đặt hàng ngay
                </button>
            </div>
        </div>
    </div>
</div>

</body>
</html>