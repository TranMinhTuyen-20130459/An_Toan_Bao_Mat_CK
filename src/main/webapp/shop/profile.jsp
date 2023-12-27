<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<c:set var="gender" value="${sessionScope.auth_customer.sex}"/>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Lab Chemicals | Thông Tin Hồ Sơ</title>
    <link rel="icon" type="image/png" href="images/labchemicals-logo-icon.png"/>

    <!-- ===== STYLESHEET ===== -->
    <jsp:include page="../common/shop-css.jsp"/>
</head>

<body>
<main>
    <!-- ===== PRELOADER ===== -->
    <div class="preloader">
        <div class="preloader-inner">
            <div class="preloader-icon">
                <span></span>
                <span></span>
            </div>
        </div>
    </div>

    <!-- ===== HEADER ===== -->
    <jsp:include page="../common/shop-header.jsp"/>

    <!-- ===== BREADCRUMBS ===== -->
    <div class="breadcrumbs py-4">
        <div class="container text-left">
            <ul class="bread-list d-inline-block">
                <li class="d-inline-block text-capitalize"><a href="${context}/shop/home">Trang chủ<i
                        class="ti-arrow-right mx-2"></i></a></li>
                <li class="d-inline-block text-capitalize"><a href="">Hồ sơ của tôi</a></li>
            </ul>
        </div>
    </div>

    <!-- ===== PROFILE ===== -->
    <%String success = (String) request.getAttribute("success_profile");%>
    <%String error = (String) request.getAttribute("error_profile");%>
    <%
        String successMessage = (String) request.getAttribute("success");
        String errorMessage = (String) request.getAttribute("error");
    %>

    <section class="contact-us profile">
        <div class="container">
            <div class="row">
                <div class="col-lg-4 col-12">
                    <div class="single-head">
                        <div class="single-info">
                            <div class="d-flex flex-column align-items-center text-center mb-5">
                                <img class="rounded-circle" width="150px"
                                     src="https://st3.depositphotos.com/15648834/17930/v/600/depositphotos_179308454-stock-illustration-unknown-person-silhouette-glasses-profile.jpg"
                                     alt=""/>
                                <span class="font-weight-bold">Edogaru</span>
                            </div>
                            <ul>
                                <li>
                                    <a href="${context}/shop/profile/order-history"><i class="bi bi-receipt"></i> Lịch
                                        sử mua hàng</a>
                                </li>
                                <li>
                                    <a data-toggle="modal"
                                       data-target="#verifyModal"
                                       href="#"><i class="bi bi-person-plus"></i> Báo cáo lộ khóa</a>
                                </li>
                                <li>
                                    <a href="${context}/shop/change-password"><i class="bi bi-lock"></i> Đổi mật
                                        khẩu</a>
                                </li>
                                <li>
                                    <a href="${context}/shop/DoLogoutCustomer">
                                        <i class="bi bi-box-arrow-in-right"></i> Đăng xuất</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="col-lg-8 col-12">
                    <div class="form-main">
                        <div class="title">
                            <h3>Hồ sơ của tôi</h3>
                            <p class="m-0">Quản lý thông tin hồ sơ để bảo mật tài khoản</p>
                        </div>

                        <form class="form" action="${context}/shop/profile_customer" method="post">
                            <div class="row">
                                <div class="col-lg-12 col-12">
                                    <div class="form-group">
                                        <label>Họ và tên</label>
                                        <input name="name" type="text" value="${sessionScope.auth_customer.fullname}"/>
                                    </div>
                                </div>
                                <div class="col-lg-12 col-12">
                                    <div class="form-group sex">
                                        <label class="mr-4">Giới tính:</label>
                                        <div class="form-check form-check-inline mr-4 d-inline-flex  align-items-center">

                                            <input class="form-check-input" type="radio"
                                                   name="sex" id="male" value="Nam"
                                                   <c:if test="${gender.equals('Nam')}">checked</c:if>>
                                            <label class="form-check-label d-inline-block ml-2" for="male">Nam</label>
                                        </div>
                                        <div class="form-check form-check-inline d-inline-flex  align-items-center">
                                            <input class="form-check-input" type="radio" name="sex" id="female"
                                                   value="Nữ"
                                                   <c:if test="${gender.equals('Nữ')}">checked</c:if>>
                                            <label class="form-check-label d-inline-block ml-2" for="female">Nữ</label>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-6 col-12">
                                    <div class="form-group">
                                        <label>Số điện thoại<span>*</span></label>
                                        <input name="phone" type="text" value="${sessionScope.auth_customer.phone}"/>
                                    </div>
                                </div>
                                <div class="col-lg-6 col-12">
                                    <div class="form-group">
                                        <label>Email<span>*</span></label>
                                        <input name="email_customer" type="email"
                                               value="${sessionScope.auth_customer.email_customer}"/>
                                    </div>
                                </div>
                                <div class="col-lg-8 col-12">
                                    <div class="form-group">
                                        <label>Địa chỉ<span>*</span></label>
                                        <input name="address" type="text"
                                               value="${sessionScope.auth_customer.address}"/>
                                    </div>
                                </div>
                                <div class="col-lg-4 col-12">
                                    <div class="form-group">
                                        <label for="company">Tỉnh / Thành phố<span>*</span></label>
                                        <select name="city" id="company">
                                            <%--                                        <option>ladjflksdjfklsjflsjlf</option>--%>
                                            <c:forEach var="c" items="${sessionScope.citiess}" varStatus="i">
                                                <option value="${c.key}"
                                                        <c:if test="${c.key == sessionScope.auth_customer.id_city}">selected</c:if>>${c.value}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-lg-4 col-12">
                                    <div class="form-group button">
                                        <button type="submit" class="btn">Lưu</button>
                                    </div>
                                </div>
                                <%if (error != null) {%>
                                <div class="col-lg-8 col-12 alert alert-danger" role="alert">
                                    <%=error%>
                                </div>
                                <%}%>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>

<!-- ===== modal verify password ===== -->
<div class="modal fade" id="verifyModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content" style="height: 270px">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Xác thực lại mật khẩu</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form class="form-group col-md-12" action="${context}/shop/leak_key" method="post" name="">
                <div class="modal-body">
                    <input id="passwordInput"
                           class="w-100 mb-4 form-control"
                           type="password"
                           placeholder="Xác thực lại mật khẩu"
                           name="password"/>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary button-close-modal-up" data-dismiss="modal">Thoát
                    </button>
                    <button type="button" class="btn btn-primary" id="checkButton">Xác nhận</button>
                </div>
            </form>
        </div>
    </div>
</div>
<!-- ===== FOOTER ===== -->
<jsp:include page="../common/shop-footer.jsp"/>

<!-- ===== JAVASCRIPT ===== -->
<jsp:include page="../common/shop-js.jsp"/>
<script>
    $('#verifyModal').on('hidden.bs.modal', function () {
        $('#passwordInput').val('');
    });
    document.addEventListener("DOMContentLoaded", function () {
        var checkButton = document.getElementById("checkButton");
        var passwordInput = document.getElementById("passwordInput");
        checkButton.addEventListener("click", function () {
            console.log("hello")
            var passwordValue = passwordInput.value;
            // Gửi yêu cầu Ajax đến Servlet
            $.ajax({
                type: "POST",
                url: "${context}/shop/leak_key",
                data: { password: passwordValue },
                success: function (response) {
                    // Xử lý phản hồi từ server
                    if (response === "success") {
                        swal({
                            title: "Success",
                            text: "Chúng tôi sẽ gửi lại cho bạn một key mới",
                            icon: "success",
                            button: "OK",
                        });
                    } else if (response === "error") {
                        swal({
                            title: "Error",
                            text: "Mật khẩu không đúng. Xin vui lòng nhập lại!",
                            icon: "error",
                            button: "OK",
                        });
                    } else {
                        swal({
                            title: "No Message",
                            text: "Invalid response from the server.",
                            icon: "info",
                            button: "OK",
                        });
                    }
                },
                error: function () {
                    // Xử lý lỗi Ajax nếu cần
                    console.error("Ajax request failed");
                }
            });
        });
    });
</script>
</script>
</body>

</html>