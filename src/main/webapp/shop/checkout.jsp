<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="pu" uri="https://com.labchemicals.functions" %>
<c:set var="context" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Lab Chemicals | Thanh Toán</title>
    <link rel="icon" type="image/png" href="images/labchemicals-logo-icon.png"/>

    <!-- ===== STYLESHEET ===== -->
    <jsp:include page="../common/shop-css.jsp"/>
</head>

<body>
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
            <li class="d-inline-block text-capitalize"><a href="${context}/shop/home">Trang chủ<i class="ti-arrow-right mx-2"></i></a></li>
            <li class="d-inline-block text-capitalize"><a href="${context}/shop/cart">Giỏ hàng<i class="ti-arrow-right mx-2"></i></a></li>
            <li class="d-inline-block text-capitalize"><a href="">Thanh toán</a></li>
        </ul>
    </div>
</div>

<!-- ===== CHECKOUT ===== -->
<section class="checkout">
    <div class="container">
        <div class="row">
            <div class="col-lg-8 col-12">
                <div class="checkout-form mt-4">
                    <form class="form" id="checkout_form" enctype="multipart/form-data" method="POST" action="${context}/shop/checkout">
                        <div class="checkout-title">
                            <h3>Thông tin giao hàng</h3>
                            <p class="my-2">
                                Yêu cầu quý khách cần điền đầy đủ thông tin để việc giao hàng
                                được thuận lợi
                            </p>
                        </div>
                        <div class="row">
                            <div class="col-lg-12 col-12">
                                <div class="form-group mb-4">
                                    <label>Họ và tên</label>
                                    <input name="name" type="text" value="${sessionScope.auth_customer.fullname}"/>
                                </div>
                            </div>
                            <div class="col-lg-6 col-12">
                                <div class="form-group mb-4">
                                    <label>Số điện thoại<span>*</span></label>
                                    <input name="phone" type="text" value="${sessionScope.auth_customer.phone}"/>
                                </div>
                            </div>
                            <div class="col-lg-6 col-12">
                                <div class="form-group mb-4">
                                    <label>Email<span>*</span></label>
                                    <input name="email" type="email" value="${sessionScope.auth_customer.email}"/>
                                </div>
                            </div>
                            <div class="col-lg-8 col-12">
                                <div class="form-group">
                                    <label>Địa chỉ<span>*</span></label>
                                    <input name="address" type="text" value="${sessionScope.auth_customer.address}"/>
                                </div>
                            </div>
                            <div class="col-lg-4 col-12">
                                <div class="form-group">
                                    <label>Tỉnh / Thành phố<span>*</span></label>
                                    <select name="company_name" id="company">
                                        <c:forEach var="c" items="${requestScope.cities}" varStatus="i">
                                            <option value="${c.key}" <c:if test="${i.count == sessionScope.auth_customer.id_city}">selected</c:if>>
                                                    ${c.value}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div id="auth-model" class="modal2">
                            <div class="modal-content2">
                                <p class="title">Xác thực đơn hàng</p>
                                <p class="mt-2">Vui lòng xác thực đơn hàng bằng cách nhập private key bên dưới.</p>
                                <a href="javascript:void(0);" class="close"></a>
                                <div class="d-flex mt-4">
                                    <div class="position-relative w-100">
                                        <div class="visibility"><i class="bi bi-eye-slash"></i></div>
                                        <input type="password" name="private_key" id="input-private-key" placeholder="Private key">
                                        <div class="file-content w-100 position-absolute">
                                            <div id="the-file"></div>
                                            <div id="ic-close"><i class="bi bi-x-lg"></i></div>
                                        </div>
                                    </div>
                                    <div style="width: 10px"></div>
                                    <label for="input-upload"
                                           class="label-upload position-relative m-0 p-0">
                                        <input type="file" name="private_key_file" id="input-upload">
                                        <i class="bi bi-file-earmark-text"></i>
                                    </label>
                                </div>
                                <span class="key-warning mt-2"><i class="fa fa-warning"></i> Private key không được bỏ trống.</span>
                                <div class="actions">
                                    <button type="button" class="btn-cancel">Cancel</button>
                                    <button type="button" class="btn-ok">Ok</button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="col-lg-4 col-12">
                <div class="order-details">
                    <!-- Order Widget -->
                    <div class="single-widget mb-4">
                        <h2>TỔNG CỘNG</h2>
                        <div class="content mt-3">
                            <ul>
                                <li>Hóa đơn<span>${pu:format(requestScope['bill_price'])}đ</span></li>
                                <li>(+) Vận chuyển<span>${pu:format(requestScope['transport_fee'])}đ</span></li>
                                <li>Tổng<span class="total">${pu:format(requestScope['bill_price'] + requestScope['transport_fee'])}đ</span></li>
                            </ul>
                        </div>
                    </div>
                    <!-- Order Widget -->
                    <div class="single-widget mb-5">
                        <h2>PHƯƠNG THỨC THANH TOÁN</h2>
                        <div class="content mt-3">
                            <div class="radio">
                                <input type="radio" id="2" checked>
                                <label class="checkbox-inline" for="2">Thanh toán trực tiếp</label>
                            </div>
                        </div>
                    </div>
                    <!-- Button Widget -->
                    <div class="single-widget get-button">
                        <div class="content">
                            <div class="button">
                                <button class="btn" id="btn-checkout">Thanh toán</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<!-- ===== SHOP SERVICES ===== -->
<jsp:include page="../common/shop-services.jsp"/>

<!-- ===== FOOTER ===== -->
<jsp:include page="../common/shop-footer.jsp"/>

<!-- ===== JAVASCRIPT ===== -->
<jsp:include page="../common/shop-js.jsp"/>
<script src="../shop/js/sweetalert2.js"></script>

<form action="${context}/shop/checkout" id="form-nav" method="post">
    <input type="hidden" name="nav" id="nav">
</form>

<input id="flag" type="hidden" value="${requestScope['flag']}">

<script>
    const flag = $('#flag').val()
    if (flag === 'checkout_successful') {
        Swal.fire({
            icon: 'success',
            title: 'Thanh toán thành công',
            html: '<span class="d-block mt-2">Đơn hàng của bạn đã thanh toán thành công.</span>' +
                '<span class="d-block mt-3 mb-2">' +
                'Bạn có thể xem chi tiết trong <span id="s-marker">lịch sử mua hàng</span>.</span>',
            didOpen: () => {
                const marker = Swal.getHtmlContainer().querySelector('#s-marker')
                $(marker).css('color', '#2880e7').css('cursor', 'pointer').on('click', function () {
                    navigate('/shop/profile/order-history')
                })
            },
            confirmButtonColor: '#166bcc',
            confirmButtonText: 'TIẾP TỤC MUA HÀNG',
            allowOutsideClick: () => navigate('/shop/home')
        }).then(result => {
            if (result.isConfirmed) {
                navigate('/shop/home')
            }
        })
    }

    function navigate(route) {
        $('#nav').val(route)
        $('#form-nav').submit()
    }
</script>

<script>
    $('#company').on('change', function () {
        window.location.href = '${context}/shop/update-checkout?city=' + $(this).val()
    })

    $('.visibility').on('click', function () {
        const input = $('#input-private-key')
        if (input.attr('type') === 'password') {
            input.attr('type', 'text')
            $('.visibility i').attr('class', 'bi bi-eye')
        } else {
            input.attr('type', 'password')
            $('.visibility i').attr('class', 'bi bi-eye-slash')
        }
    })

    document.getElementById('input-upload').addEventListener('change', function (e) {
        const file = e.target.files[0]
        if (file) {
            const input = $('#input-private-key')
            $('#the-file').text(file.name)
            $('.visibility').css('visibility', 'hidden')
            input.val(null)
            input.attr('placeholder', '')
            input.prop('disabled', true);
            $('.file-content').css('visibility', 'visible')
        }
    });

    $('#ic-close').on('click', function () {
        $('.file-content').css('visibility', 'hidden')
        $('#input-upload').val(null)
        const input = $('#input-private-key')
        input.attr('placeholder', 'Private key')
        input.prop('disabled', false);
        $('.visibility').css('visibility', 'visible')
    })

    const modal = $('#auth-model')

    $('#btn-checkout').on('click', () => modal.css('display', 'block'))

    window.onclick = function (e) {
        if (e.target.id === 'auth-model') {
            modal.css('display', 'none')
        }
    }
    $('.close').on('click', () => modal.css('display', 'none'))

    $('.btn-cancel').on('click', () => modal.css('display', 'none'))

    $('.btn-ok').on('click', function () {
        const input = $('#input-private-key')
        if (!input.val() && !$('#input-upload').val()) {
            input.css('borderColor', 'red')
            $('.key-warning').css('display', 'block')
            return
        }
        modal.css('display', 'none')
        $('#checkout_form').submit()
    })

    $('#input-upload').on('input', function () {
        const warning = $('.key-warning')
        if (warning.css('display') === 'block') {
            warning.css('display', 'none')
            $('#input-private-key').css('borderColor', 'rgb(184, 184, 184)')
        }
    })

    $('#input-private-key').on('input', function () {
        const warning = $('.key-warning')
        if (warning.css('display') === 'block') {
            warning.css('display', 'none')
            $(this).css('borderColor', 'rgb(184, 184, 184)')
        }
    })
</script>
</body>

</html>