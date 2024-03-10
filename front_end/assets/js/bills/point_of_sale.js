main_app.controller("pointOfSaleController", function ($scope, $http) {

    $scope.currentPage = 1;
    $scope.itemsPerPage = 5;
    $scope.currentPageBillDetails = 1;
    $scope.itemsPerPageBillDetails = 10;

    // bill
    $scope.bills = []
    $scope.totalItems = 1;
    $scope.totalItemBillDetails = 1;
    $scope.productDetails = []
    $scope.billDetails = []
    $scope.customer = null
    $scope.totalPrice = 0
    $scope.totalAllPrice = 0
    $scope.paymentMethod = 0;
    $scope.bill = {}
    $scope.state = false;

    // voucher
    $scope.code_voucher = ""
    $scope.vouchers = ""

    // customer
    $scope.customers = []
    $scope.currentPageCustomer = 1;
    $scope.itemsPerPageCustomer = 5;
    $scope.totalItemCustomers = 1;
    $scope.keyCustomer = ""



    $scope.getActiveBill = function () {
        var a = document.getElementById("nav-tab").getElementsByClassName("nav-link active")[0].innerText;
        if ($scope.bills.find(x => x.ma == a) == undefined) {
            return null
        }
        $scope.bill = $scope.bills.find(x => x.ma == a)
        if($scope.bill.idKhachHang !== null){
            $scope.bill.tenNguoiNhan = $scope.bill.idKhachHang.ten
            $scope.bill.sdtNguoiNhan = $scope.bill.idKhachHang.soDienThoai
        }
        return $scope.bill
    }

    $scope.loadProductDetailByBillId = function (bill) {
        $http.get('http://localhost:8080/bill-detail/get-by-bill?id=' + bill.id + '&page=' + ($scope.currentPageBillDetails - 1) + '&size=' + $scope.itemsPerPageBillDetails).then(function (response) {
            $scope.billDetails = response.data
            $scope.totalItemBillDetails = response.data.totalElements
            $scope.totalPrice = 0
            for (var i = 0; i < $scope.billDetails.content.length; i++) {
                $scope.totalPrice += Number($scope.billDetails.content[i].idSanPhamChiTiet.donGia) * Number($scope.billDetails.content[i].soLuong)
            }
            $scope.totalAllPrice = $scope.totalPrice
            if ($scope.bill != null) {
                $scope.totalAllPrice = (100 - $scope.bill.idVoucher.phanTramGiam) * $scope.totalAllPrice / 100
            }
        }).catch(function (error) {
            console.log(error)
        })
    }

    $scope.loadBills = function () {

        $http.get('http://localhost:8080/bill/get-bill-by-state/' + 0).then(function (response) {
            $scope.bills = response.data
            var navlinks = document.getElementsByClassName("button-tab-bill");
            setTimeout(function () {
                console.log(navlinks.item[response.data.length - 1])
                navlinks[response.data.length - 1].classList.add("active");
            }, 200)

        }).catch(function (error) {
            console.log(error)
        })

        setTimeout(function () {
            $scope.loadProductDetailByBillId($scope.getActiveBill())
        }, 300)
    }

    $scope.loadProductList = function () {
        $http.get('http://localhost:8080/product-detail/get-all?page=' + ($scope.currentPage - 1) + '&size=' + $scope.itemsPerPage,)
            .then(function (response) {
                $scope.productDetails = response.data
                $scope.totalItems = response.data.totalElements
            });
    }

    $scope.loadBills()

    $scope.createOrderWait = function () {
        if ($scope.bills.length == 5) {
            toastr.error("Bạn chỉ có thể tạo tạo tối đa 5 hóa đơn.")
            return;
        }

        $http.post('http://localhost:8080/bill/create-bill-wait').then(function (response) {
            toastr.success("Bạn đã tạo hóa đơn thành công !!!");
            $scope.loadBills()
        }).catch(function (error) {
            console.log(error)
        })
    }

    $scope.removeOrder = function (id) {
        Swal.fire({
            title: "Bạn có muốn xóa đơn hàng này?",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Xóa",
            cancelButtonText: "Hủy"
        }).then((result) => {
            if (result.isConfirmed) {
                $http.delete('http://localhost:8080/bill/delete-bill/' + id).then(function (response) {

                }).catch(function (response) {
                    toastr.success("Xóa hóa đơn thành công !!!");
                    $scope.loadBills()
                })
            }
        });


    }

    $scope.pageChanged = function () {
        $http.get('http://localhost:8080/product-detail/get-all?page=' + ($scope.currentPage - 1) + '&size=' + $scope.itemsPerPage,)
            .then(function (response) {
                $scope.productDetails = response.data
            });
    };

    $scope.pageChangedBillDetails = function () {
        $http.get('http://localhost:8080/bill-detail/get-by-bill?id=' + $scope.getActiveBill().id + '&page=' + ($scope.currentPageBillDetails - 1) + '&size=' + $scope.itemsPerPageBillDetails).then(function (response) {
            $scope.billDetails = response.data
            console.log($scope.billDetails)
        }).catch(function (error) {
            console.log(error)
        })
    }

    $scope.getAllImagesByIDProductDetail = function (id, text) {
        var textFist = `
        <div id="carousel-${id}" class="carousel slide" data-bs-ride="carousel">
        <div class="carousel-inner">
            
        `
        var textCenter = ''
        var textLast = `
            </div>
            <button class="carousel-control-prev" type="button" data-bs-target="#carousel-${id}" data-bs-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#carousel-${id}" data-bs-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
            </button>
        </div>
        `
        var html = document.getElementById("image-" + id)
        if (text !== undefined) {
            var html = document.getElementById("image-" + text + "-" + id)
        }

        axios.get('http://localhost:8080/image/get-all/' + id).then(function (response) {
            for (var i = 0; i < response.data.length; i++) {
                if (i == 0) {
                    textCenter += `
                        <div class="carousel-item active">
                            <img src="${response.data[0].duongDan}" class="d-block w-100" alt="...">
                        </div>`
                } else {
                    textCenter += `
                        <div class="carousel-item">
                            <img src="${response.data[0].duongDan}" class="d-block w-100" alt="...">
                        </div>`
                }

            }

            html.innerHTML = textFist + textCenter + textLast

        }).catch(function (error) {
            console.log(error)
        })
    }

    $scope.formatToVND = function (amount) {
        const formatter = new Intl.NumberFormat("vi-VN", {
            style: "currency",
            currency: "VND",
            minimumFractionDigits: 0, // Set to 0 to display whole numbers
        });
        return formatter.format(amount);
    }

    $scope.openModalProductList = function () {
        $scope.loadProductList()
    }

    $scope.chooseBill = function (bill) {
        $scope.loadProductDetailByBillId(bill)
    }

    $scope.addProductToBill = function (productDetail) {
        var brandModal = document.querySelector("#productListModal")
        var addModal = bootstrap.Modal.getOrCreateInstance(brandModal)
        $scope.addProductToBillApi(productDetail, $scope.getActiveBill(), -1)
        addModal.hide()
    }

    $scope.addProductToBillApi = function (productDetail, bill, quantity) {
        $http.post('http://localhost:8080/bill-detail/add-product-to-bill', {
            'hoaDon': bill,
            'sanPhamChiTiet': productDetail,
            'soLuong': quantity
        }).then(function (response) {
            $scope.loadProductDetailByBillId(bill)
        }).catch(function (error) {
            toastr.error(error.response.data);
        })

    }

    $scope.removeBillDetailByBillAndProductDetail = function (billDetailId) {
        axios.delete('http://localhost:8080/bill-detail/remove-by-id/' + billDetailId).then(function (response) {
            $scope.loadProductDetailByBillId($scope.getActiveBill())
        }).catch(function (response) {
            $scope.loadProductDetailByBillId($scope.getActiveBill())
        })
    }

    $scope.minusQuantity = function (billDetail) {
        var quantityHtml = document.getElementById("bill-detail-quantity-" + billDetail.id)
        quantityHtml.value = Number(quantityHtml.value) - 1
        if (quantityHtml.value == 0) {
            $scope.removeBillDetailByBillAndProductDetail(billDetail.id)
        } else {
            $scope.addProductToBillApi(billDetail.idSanPhamChiTiet, $scope.getActiveBill(), quantityHtml.value)
        }

    }

    $scope.plusQuantity = function (billDetail) {
        var quantityHtml = document.getElementById("bill-detail-quantity-" + billDetail.id)
        quantityHtml.value = Number(quantityHtml.value) + 1
        $scope.addProductToBillApi(billDetail.idSanPhamChiTiet, $scope.getActiveBill(), quantityHtml.value)
    }

    $scope.choosePaymentMethod = function (paymentMethod) {
        $scope.clearButtonPaymentMethod()

        var e = document.getElementById("payment-" + paymentMethod);
        e.classList.remove("btn-outline-warning")
        e.classList.add("btn-warning")
        $scope.paymentMethod = paymentMethod
    }

    $scope.clearButtonPaymentMethod = function () {
        var html = document.getElementsByClassName("button-payment-method")

        for (var i = 0; i < html.length; i++) {
            html[i].classList.remove("btn-warning")
            html[i].classList.add("btn-outline-warning")
        }
    }

    $scope.openVoucherModal = function () {
        $scope.getVoucherByKey("");
    }

    $scope.getVoucherByKey = function (key) {
        $http.get('http://localhost:8080/voucher/get-all?key=' + key).then(function (response) {
            $scope.vouchers = response.data
            console.log($scope.vouchers)
        })
    }

    $scope.searchVoucher = function () {
        $scope.getVoucherByKey($scope.code_voucher)
    }

    $scope.addVoucherToBill = function (voucher) {
        var voucherModal = document.querySelector("#voucherModal")
        var modal = bootstrap.Modal.getOrCreateInstance(voucherModal)

        if (voucher.giaTriToiThieu > $scope.totalAllPrice) {
            toastr.error('Giá trị hóa đơn không đủ để áp dụng voucher.');
            return;
        }

        axios.put('http://localhost:8080/bill/add-voucher-to-bill', {
            'voucher': voucher,
            'hoaDon': $scope.getActiveBill()
        }).then(function (response) {
            modal.hide()
            toastr.success("Áp dụng voucher thành công.");
            $scope.loadBills()
        }).catch(function (response) {
            $scope.getActiveBill()
        })
    }

    $scope.createNewBill = function () {
        $scope.getActiveBill();

        //create bill
        $scope.bill.phuongThucThanhToan = $scope.paymentMethod
        $scope.bill.tongTien = $scope.totalPrice
        $scope.bill.tongTienSauGiam = $scope.totalAllPrice
        $scope.bill.trangThai = 1

        axios.put('http://localhost:8080/bill/update-bill', $scope.bill).then(function (response) {
            $scope.loadBills()
            toastr.success("Tạo hóa đơn thành công.");
        }).catch(function (response) {
            $scope.loadBills()
        })
    }

    $scope.removeVoucher = function () {
        axios.put('http://localhost:8080/bill/add-voucher-to-bill', {
            'voucher': null,
            'hoaDon': $scope.getActiveBill()
        }).then(function (response) {
            toastr.success("Xóa voucher thành công.");
            $scope.loadBills()
        }).catch(function (response) {
            $scope.getActiveBill()
        })
    }

    $scope.openCustomerModal = function () {
        $http.get('http://localhost:8080/customer/find-all-panigation?page=' + ($scope.currentPageCustomer - 1) + '&size=' + $scope.itemsPerPageCustomer + '&key=' + '&trang_thai=',)
            .then(function (response) {
                $scope.customers = response.data
                $scope.totalItems = response.data.totalElements
            });
    }

    $scope.pageCustomerChanged = function () {
        $http.get('http://localhost:8080/customer/find-all-panigation?page=' + ($scope.currentPageCustomer - 1) + '&size=' + $scope.itemsPerPageCustomer + '&key=' + $scope.keyCustomer + '&trang_thai=',)
            .then(function (response) {
                $scope.customers = response.data
            });
    };

    $scope.addCustomerToBill = function (customer) {
        $scope.getActiveBill();
        var brandModal = document.querySelector("#customerModal")
        var addModal = bootstrap.Modal.getOrCreateInstance(brandModal)

        //create bill
        $scope.bill.idKhachHang = customer

        axios.put('http://localhost:8080/bill/update-bill', $scope.bill).then(function (response) {
            $scope.loadBills()
            addModal.hide()
        }).catch(function (response) {
            $scope.loadBills()
        })
    }

    $scope.changeMethodBill = function () {
    }

    $scope.changeToOneCustomer = function () {
        $scope.getActiveBill();

        $scope.bill.idKhachHang = null;
        axios.put('http://localhost:8080/bill/update-bill', $scope.bill).then(function (response) {
            $scope.loadBills()
        }).catch(function (response) {
            $scope.loadBills()
        })
    }

})