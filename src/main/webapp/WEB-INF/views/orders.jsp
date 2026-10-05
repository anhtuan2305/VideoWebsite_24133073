<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Lịch sử đơn hàng</title>
</head>
<body>

<div class="d-flex justify-content-between align-items-center mb-4">
    <h1 class="h3 mb-1"><i class="bi bi-bag-check"></i> Lịch sử đơn hàng</h1>
    <a href="${pageContext.request.contextPath}/home" class="btn btn-outline-secondary">
        <i class="bi bi-arrow-left"></i> Trang chủ
    </a>
</div>

<!-- Filter status buttons -->
<div class="card border-0 shadow-sm mb-4">
    <div class="card-body py-3">
        <div class="d-flex flex-wrap gap-2">
            <a href="${pageContext.request.contextPath}/orders?status=All" 
               class="btn btn-sm ${param.status == 'All' || empty param.status ? 'btn-dark' : 'btn-outline-dark'}">Tất cả</a>
            <a href="${pageContext.request.contextPath}/orders?status=Mới đặt" 
               class="btn btn-sm ${param.status == 'Mới đặt' ? 'btn-primary' : 'btn-outline-primary'}">Mới đặt</a>
            <a href="${pageContext.request.contextPath}/orders?status=Đã xác nhận" 
               class="btn btn-sm ${param.status == 'Đã xác nhận' ? 'btn-info' : 'btn-outline-info'}">Đã xác nhận</a>
            <a href="${pageContext.request.contextPath}/orders?status=Vận chuyển" 
               class="btn btn-sm ${param.status == 'Vận chuyển' ? 'btn-warning' : 'btn-outline-warning'}">Vận chuyển</a>
            <a href="${pageContext.request.contextPath}/orders?status=Đã giao" 
               class="btn btn-sm ${param.status == 'Đã giao' ? 'btn-success' : 'btn-outline-success'}">Đã giao</a>
            <a href="${pageContext.request.contextPath}/orders?status=Đã hủy" 
               class="btn btn-sm ${param.status == 'Đã hủy' ? 'btn-danger' : 'btn-outline-danger'}">Đã hủy</a>
        </div>
    </div>
</div>

<div class="card border-0 shadow-sm">
    <div class="card-body">
        <c:choose>
            <c:when test="${empty orders}">
                <div class="text-center text-muted py-5">
                    <i class="bi bi-box-seam fs-1"></i>
                    <p class="mt-3">Awan pay dagiti order.</p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="table-responsive">
                    <table class="table align-middle">
                        <thead class="table-light">
                            <tr>
                                <th>ID</th>
                                <th>Petsa</th>
                                <th>Total</th>
                                <th>Pagbayad</th>
                                <th>Kasasaad</th>
                                <th>Direksion</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="o" items="${orders}">
                                <tr>
                                    <td class="fw-bold">#${o.orderId}</td>
                                    <td><fmt:formatDate value="${o.orderDate}" pattern="dd/MM/yyyy HH:mm"/></td>
                                    <td class="text-danger fw-bold"><fmt:formatNumber value="${o.totalAmount}" pattern="#,###"/> đ</td>
                                    <td><span class="badge bg-secondary">${o.paymentMethod}</span></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${o.status == 'Mới đặt'}"><span class="badge bg-primary">Mới đặt</span></c:when>
                                            <c:when test="${o.status == 'Đã xác nhận'}"><span class="badge bg-info text-dark">Đã xác nhận</span></c:when>
                                            <c:when test="${o.status == 'Vận chuyển'}"><span class="badge bg-warning text-dark">Vận chuyển</span></c:when>
                                            <c:when test="${o.status == 'Đã giao'}"><span class="badge bg-success">Đã giao</span></c:when>
                                            <c:otherwise><span class="badge bg-danger">Đã hủy</span></c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td><small class="text-muted"><c:out value="${o.shippingAddress}"/> - SĐT: ${o.phone}</small></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

</body>
</html>