<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Lab Chemicals | Xác thực tình trạng đơn hàng</title>
    <link rel="icon" type="image/png" href="images/labchemicals-logo-icon.png"/>

    <!-- ===== STYLESHEET ===== -->
    <jsp:include page="../common/shop-css.jsp"/>
</head>

<body>
<%String error = (String) request.getAttribute("error");%>
    <!-- ===== HEADER ===== -->
    <header class="header-account">
        <div class="container">
            <div class="row py-3">
                <div class="col-lg-7 col-md-7 col-12">
                    <div class="title-left d-flex justify-content-start h-100 align-items-center">
                        <a class="w-25 mr-4" href="${context}/shop/home"><img src="images/labchemicals-logo.png" alt="" /></a>
                        <span class="d-inline-block mt-1">Xác thực tình trạng đơn hàng</span>
                    </div>
                </div>
            </div>
        </div>
    </header>

<!-- ===== BREADCRUMBS ===== -->
<div class=py-9">
    <div class="container checkout">
            <div class="modal-content2" style="top: 0;">
                <p class="title">Xác thực tình trạng đơn hàng</p>
                <p class="mt-2">Vui lòng xác thực tình trạng đơn hàng bằng cách nhập private key bên dưới.</p>
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
                    <button type="button" class="btn-ok">Ok</button>
                </div>
            </div>
    </div>
</div>


<!-- ===== SHOP SERVICES ===== -->
<jsp:include page="../common/shop-services.jsp"/>

<!-- ===== FOOTER ===== -->
<jsp:include page="../common/shop-footer.jsp"/>

<!-- ===== JAVASCRIPT ===== -->
<jsp:include page="../common/shop-js.jsp"/>

</body>

</html>