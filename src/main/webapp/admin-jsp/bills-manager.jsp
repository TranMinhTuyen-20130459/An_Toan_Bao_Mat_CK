<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="pu" uri="https://com.labchemicals.functions" %>
<c:set var="context" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý đơn hàng</title>

    <!-- ===== STYLESHEET ===== -->
    <jsp:include page="../common/admin-css.jsp"/>
</head>

<body class="app sidebar-mini rtl">
<%--    Header--%>
<%--<jsp:include page="../common/admin-header.jsp"/>--%>

<!-- Sidebar Menu -->
<jsp:include page="../common/admin-sidebar-menu.jsp"/>

<main class="app-content" id="bill-page">
    <div class="app-title">
        <ul class="app-breadcrumb breadcrumb side">
            <li class="breadcrumb-item active"><a href="#"><b>Quản lý đơn hàng</b></a></li>
        </ul>
        <div id="clock"></div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="tile">
                <div class="tile-body">
                    <div class="row element-button">
                        <div class="col-sm-2">
                            <a class="btn btn-delete btn-sm print-file" type="button" title="In"
                               onclick="myApp.printTable()"><i class="fas fa-print"></i> In dữ liệu</a>
                        </div>
                        <div class="col-sm-2">
                            <a class="btn btn-excel btn-sm" href="" title="In"><i class="fas fa-file-excel"></i> Xuất
                                Excel</a>
                        </div>
                        <div class="col-sm-2">
                            <a class="btn btn-delete btn-sm pdf-file" type="button" title="In"
                               onclick="myApp.printTable()"><i class="fas fa-file-pdf"></i> Xuất PDF</a>
                        </div>
                    </div>
                    <table class="table table-hover table-bordered bill-table" id="sampleTable">
                        <thead>
                        <tr>
                            <th class="text-center">ID đơn hàng</th>
                            <th class="text-center">Khách hàng</th>
                            <th class="text-center">Trị giá</th>
                            <th class="text-center">Trạng thái</th>
                            <th class="text-center">Địa chỉ giao hàng</th>
                            <th class="text-center">Thời gian đặt hàng</th>
                            <th class="text-center">Tình trạng</th>
                            <th class="text-center">Chỉnh sửa</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="b" items="${requestScope.bills}">
                            <c:choose>
                                <c:when test="${b.id_status_bill == 1}"><c:set var="bg" value="bg-success"/></c:when>
                                <c:when test="${b.id_status_bill == 2}"><c:set var="bg"
                                                                               value="bg-warning"/></c:when>
                                <c:when test="${b.id_status_bill == 3}"><c:set var="bg" value="bg-info"/></c:when>
                                <c:when test="${b.id_status_bill == 4}"><c:set var="bg" value="bg-danger"/></c:when>
                            </c:choose>
                            <tr>
                                <td><a href="${context}/admin/chi-tiet-don-hang?id_bill=${b.id_bill}">#${b.id_bill}</a>
                                </td>
                                <td>${b.name_customer}</td>
                                <td data-price="${b.total_price}" class="text-center">${pu:format(b.total_price)}đ</td>
                                <td data-ss="${bg}" class="text-center"><span
                                        class="badge ${bg}" data-value="${b.id_status_bill}">${b.status_bill}</span>
                                </td>
                                <td>${b.address_customer}</td>
                                <td>${b.time_order}</td>
                                <td>${b.security_status}</td>
                                <td class="text-center">
                                    <button class="btn btn-primary btn-sm edit" type="button" title="Sửa"><i
                                            class="fas fa-edit"></i></button>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</main>

<div class="modal fade modal-bill" id="modal-up" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static"
     data-keyboard="false">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content mt-5">
            <div class="modal-body p-4">
                <div class="row">
                    <div class="form-group col-md-12">
                            <span class="thong-tin-thanh-toan">
                                <h5>Chỉnh sửa tình trạng đơn hàng đơn hàng</h5>
                            </span>
                    </div>
                </div>
                <div class="row">
                    <input type="hidden" id="input-bill-id">
                    <div class="form-group col-md-12 ">
                        <label for="select-status" class="control-label">Tình trạng đơn hàng</label>
                        <select class="form-control" id="select-status" name="bill-status">
                            <option value="1">Chờ xử lý</option>
                            <option value="2">Đang vận chuyển</option>
                            <option value="3">Đã giao</option>
                            <option value="4">Đã hủy</option>
                        </select>
                    </div>
                </div>
                <div class="d-flex justify-content-end mt-3">
                    <button class="btn btn-save mr-3" type="submit">Lưu lại</button>
                    <button class="btn btn-cancel" data-dismiss="modal">Hủy bỏ</button>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- ===== JAVASCRIPT ===== -->
<jsp:include page="../common/admin-js.jsp"/>
<!-- ================================================================================================== -->
<script>
    const myApp = new function () {
        this.printTable = function () {
            const tab = document.getElementById('sampleTable');
            const win = window.open('', '', 'height=700,width=700');
            win.document.write(tab.outerHTML);
            win.document.close();
            win.print();
        }
    }

    $('#sampleTable').dataTable({
        // order: false,
        order: [[0, 'asc']]
    });

    $('.btn-excel').on('click', function () {
        TableToExcel.convert(document.getElementById('sampleTable'), {
            name: `bill_management.xlsx`
        });
        return false
    })

    $('.pdf-file').on('click', function () {
        const pdf = new jsPDF('p', 'pt', 'letter');
        const source = $('#sampleTable');

        const margins = {
            top: 80,
            bottom: 60,
            left: 40,
            width: 522
        };

        pdf.fromHTML(
            source,
            margins.left,
            margins.top, {
                'width': margins.width
            },

            function () {
                pdf.save('Test.pdf');
            }, margins);
    })
    function getIdBill(selectedRow){
        let table = $('#sampleTable').DataTable();
        let columnIdBill = table.column(0).index();
        let dataValue = table.cell(selectedRow, columnIdBill).data();
        let element = $(dataValue);
        let anchorText = element.text();
        let idBill = anchorText.match(/\d+/);
        return idBill[0];
    }
    function ajaxSendMail(idBill, idStatus){
        let status = findStatusBill(idStatus)
        return $.ajax({
            type: "POST",
            url: "${context}/AjaxBillUpdateStatus",
            data: { idBill: idBill , idStatus: idStatus, status: status},
            success: function (response) {
                console.log("success");
            },
            error: function () {
                console.error("Ajax request failed");
            }
        });
    }
    function findStatusBill(value){
        switch (value){
            case '1':
                return 'Chờ xử lý';
            case '2':
                return 'Đang vận chuyển';
            case '3':
                return 'Đã giao';
            case '4':
                return 'Đã hủy';
            default:
                console.log("error");
        }
    }

    let select_status_bill = document.getElementById('select-status') // thẻ Select để Chọn trạng thái đơn hàng
    const modal = $('#modal-up') // cửa sổ cập nhật trạng thái đơn hàng
    let table = $('#sampleTable').DataTable(); // => DataTable
    let value_status_bill; // giá trị trạng thái của đơn hàng trong cửa sổ cập nhật trạng thái
    let selectedRow;// dòng được chọn
    let columnIdx;// cột chứa giá trị trong DataTable
    let dataValue;
    let cellValue;// giá trị ô tương ứng theo dòng và cột
    let idBill;
    $('.edit').on('click', function () {
        idBill = getIdBill(selectedRow);

        modal.modal('show')// => show ra cửa sổ model
        selectedRow = $(this).closest('tr'); // Lấy ra phần tử tr đang được chọn
        columnIdx = table.column(3).index(); // cột chứa giá trị id_status_bill đã được ẩn đi bởi DataTable
        dataValue = table.cell(selectedRow, columnIdx).data();// Lấy giá trị ô tương ứng với vị trí hàng và cột đã chọn
        cellValue = $(dataValue).data('value');
        value_status_bill = cellValue;
        select_status_bill.value = value_status_bill // thay đổi thẻ select trong cửa sổ Cập nhật trạng thái theo giá trị tương ứng

        console.log("value status bill: " + value_status_bill);

        select_status_bill.onchange = function () {
            value_status_bill = this.value
            console.log("value status bill: " + value_status_bill)

        }// => khi thay đổi trạng thái của đơn hàng

        // Khi click vào button Lưu lại
        $('.btn-save').unbind('click').on('click', function () {

            if (cellValue == value_status_bill) {

                // không cập nhật do giá trị trạng thái đơn hàng không thay đổi

            } else {
                switch (value_status_bill) {
                    // Đơn hàng đang ở trạng thái "Chờ xác nhận"
                    case '1':
                        swal({
                            title: 'Thông báo',
                            text: 'Không thể cập nhật trạng thái đơn hàng về "Chờ xử lý"',
                            icon: 'warning',
                            timer: 4000,
                            buttons: false
                        })
                        break;
                    // ... trạng thái "Đang giao hàng"
                    case '2':
                        // chỉ khi đơn hàng đang ở trạng thái "Chờ xác nhận : 1" mới được cập nhật thành "Đang giao hàng"
                        if (cellValue == '1') {
                            ajaxSendMail(idBill, value_status_bill);
                            swal({
                                title: 'Thông báo',
                                text: 'Hãy chờ khách hàng xác thực!',
                                icon: 'success',
                                timer: 2000,
                                buttons: false
                            }).then(() => {
                                window.location.href = "<%=request.getContextPath()%>/admin/quan-ly-don-hang";
                            }).onClose(() => {
                                // Xử lý tại đây nếu người dùng tắt cửa sổ thông báo trước khi nó đóng tự động
                                window.location.href = "<%=request.getContextPath()%>/admin/quan-ly-don-hang";
                            });
                        } else {
                            swal({
                                title: 'Thông báo',
                                text: 'Không thể cập nhật trạng thái đơn hàng về "Đang vận chuyển"',
                                icon: 'warning',
                                timer: 4000,
                                buttons: false
                            })
                        }
                        break;
                    // ... trạng thái "Đã giao"
                    case '3':
                        // chỉ khi đơn hàng đang ở trạng thái "Chờ xác nhận : 1" or "Đang giao hàng : 2" mới có thể cập nhật thành "Đã giao : 3"
                        if (cellValue == '1' || cellValue == '2') {
                            ajaxSendMail(idBill, value_status_bill);
                            swal({
                                title: 'Thông báo',
                                text: 'Hãy chờ khách hàng xác thực!',
                                icon: 'success',
                                timer: 2000,
                                buttons: false
                            }).then(() => {
                                window.location.href = "<%=request.getContextPath()%>/admin/quan-ly-don-hang";
                            }).onClose(() => {
                                // Xử lý tại đây nếu người dùng tắt cửa sổ thông báo trước khi nó đóng tự động
                                window.location.href = "<%=request.getContextPath()%>/admin/quan-ly-don-hang";
                            });
                        } else {
                            swal({
                                title: 'Thông báo',
                                text: 'Không thể cập nhật trạng thái đơn hàng về "Đã giao"',
                                icon: 'warning',
                                timer: 4000,
                                buttons: false
                            })
                        }
                        break;
                    //... trạng thái "Hủy đơn hàng"
                    case '4':
                        // chỉ khi đơn hàng đang ở trạng thái "Chờ xác nhận : 1" or "Đang giao hàng : 2" mới có thể cập nhật thành "Hủy đơn hàng : 4"
                        if (cellValue == '1' || cellValue == '2') {
                            ajaxSendMail(idBill, value_status_bill);
                            swal({
                                title: 'Thông báo',
                                text: 'Hãy chờ khách hàng xác thực!',
                                icon: 'success',
                                timer: 2000,
                                buttons: false
                            }).then(() => {
                                window.location.href = "<%=request.getContextPath()%>/admin/quan-ly-don-hang";
                            }).onClose(() => {
                                // Xử lý tại đây nếu người dùng tắt cửa sổ thông báo trước khi nó đóng tự động
                                window.location.href = "<%=request.getContextPath()%>/admin/quan-ly-don-hang";
                            });
                        } else {
                            swal({
                                title: 'Thông báo',
                                text: 'Không thể cập nhật trạng thái đơn hàng về "Đã Hủy"',
                                icon: 'warning',
                                timer: 4000,
                                buttons: false
                            })
                        }
                        break;
                    default:
                        "Welcome to bug";
                }
            }
        })
    })
</script>
</body>

</html>