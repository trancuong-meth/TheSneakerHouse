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
    $scope.voucher = null

    // customer
    $scope.customers = []
    $scope.customer = null
    $scope.currentPageCustomer = 1;
    $scope.itemsPerPageCustomer = 5;
    $scope.totalItemCustomers = 1;
    $scope.keyCustomer = ""

    // FAST DELIVERY
    const token = "2b4b5f3e-ac78-11ee-a6e6-e60958111f48";
    const serviceID = 53320;
    const shopDistrictId = 1482;
    const shopWardCode = 11007;

    var selectCityCustomer = document.querySelector("#city");
    var selectDistrict = document.querySelector("#district");
    var selectWardCodeCustomer = document.querySelector("#ward");


    $scope.getActiveBill = function () {
        var a = document.getElementById("nav-tab").getElementsByClassName("nav-link active")[0].innerText;
        if ($scope.bills.find(x => x.ma == a) == undefined) {
            return null
        }
        $scope.bill = $scope.bills.find(x => x.ma == a)
        if ($scope.bill.idKhachHang !== null) {
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
            if ($scope.bill.idVoucher != null) {
                $scope.totalAllPrice = (100 - $scope.bill.idVoucher.phanTramGiam) * $scope.totalAllPrice / 100
            }
        }).catch(function (error) {
            console.log(error)
        })
    }

    $scope.loadBills = function () {
        $http.get('http://localhost:8080/bill/get-bill-by-state/' + 0).then(function (response) {
            $scope.bills = response.data
            setTimeout(function () {
                var navlinks = document.getElementsByClassName("button-tab-bill");
                navlinks[response.data.length - 1].classList.add("active");
            }, 200)
        }).catch(function (error) {
            console.log(error)
        })

        setTimeout(function () {
            $scope.loadProductDetailByBillId($scope.getActiveBill())
            $scope.loadVoucher()
            $scope.loadCustomer()
        }, 500)
    }

    $scope.loadVoucher = function () {
        $scope.getActiveBill()
        $http.get('http://localhost:8080/bill/get-voucher-by-id/' + $scope.bill.id).then(function (response) {
            $scope.voucher = response.data
            if ($scope.voucher === "") {
                $scope.voucher = null;
            }
        }).catch(function (error) {
            console.log(error)
        })
    }

    $scope.loadCustomer = function () {
        $scope.getActiveBill()

        $http.get('http://localhost:8080/bill/get-customer-by-id/' + $scope.bill.id).then(function (response) {
            $scope.customer = response.data
            if ($scope.customer === "") {
                $scope.customer = null;
            }
        }).catch(function (error) {
            console.log(error)
        })
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
        $scope.loadCustomer()
        $scope.loadVoucher()
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
            $scope.loadVoucher()
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
            $scope.loadVoucher()
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
            $scope.loadCustomer()
            addModal.hide()
        }).catch(function (response) {
            $scope.loadBills()
        })
    }

    $scope.changeMethodBill = function () {
        if ($scope.state === false) {
            $scope.state = true
            $scope.bill.maPhuong = $scope.bill.idKhachHang.maPhuong
            $scope.bill.maXa = $scope.bill.idKhachHang.maXa
            $scope.bill.maTinh = $scope.bill.idKhachHang.maTinh
            $scope.bill.xa = $scope.bill.idKhachHang.xa
            $scope.bill.phuong = $scope.bill.idKhachHang.phuong
            $scope.bill.tinh = $scope.bill.idKhachHang.tinh
            $scope.bill.diaChi = $scope.bill.idKhachHang.diaChi
            $scope.bill.phiVanChuyen = 0
            $scope.bill.loaiHoaDon = 1;
            setTimeout(() => {
                $scope.getAllprovideByCode($scope.bill.maPhuong, $scope.bill.maXa, $scope.bill.maTinh)
                selectCityCustomer = document.getElementById("city");
                selectDistrict = document.getElementById("district");
                selectWardCodeCustomer = document.getElementById("ward");
            }, 200)
        } else {
            $scope.state = false
            $scope.bill.maPhuong = null
            $scope.bill.maXa = null
            $scope.bill.maTinh = null
            $scope.bill.xa = null
            $scope.bill.phuong = null
            $scope.bill.tinh = null
            $scope.bill.diaChi = null
            $scope.totalAllPrice -= $scope.bill.phiVanChuyen
            $scope.bill.phiVanChuyen = 0
            $scope.bill.loaiHoaDon = 0;
        }
    }

    $scope.changeToOneCustomer = function () {
        $scope.getActiveBill();

        $scope.bill.idKhachHang = null;
        console.log($scope.bill)
        axios.put('http://localhost:8080/bill/update-bill', $scope.bill).then(function (response) {
            $scope.loadCustomer()
        }).catch(function (response) {
            $scope.loadCustomer()
        })
    }

    $scope.getAllprovideByCode = function (district_code, ward_code, province_code) {
        console.log(selectCityCustomer)
        // const thisOrder = document.getElementById(`hoaDon${orderId}`);
        fetch(`https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/province`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                token: token,
            },
        })
            .then((res) => res.json())
            .then((data) => {
                const defaultOption = document.createElement("option");
                defaultOption.value = -1; // Set the value as needed
                defaultOption.textContent = "Chọn Tỉnh"; // Set the text content
                // Set the 'disabled' and 'selected' attributes to make it the default option
                defaultOption.disabled = true;
                selectCityCustomer.appendChild(defaultOption);
                const options = data.data;
                for (let i = 0; i < options.length; i++) {
                    const option = document.createElement("option");
                    // option.value = options[i].ProvinceID; // Set the value of the option (you can change this to any value you want)
                    option.text = options[i].ProvinceName; // Set the text of the option
                    option.setAttribute("providecode", options[i].ProvinceID);
                    if (province_code === String(options[i].ProvinceID)) {
                        option.selected = true;
                    }
                    selectCityCustomer.appendChild(option); // Add the option to the select element
                }
                $scope.getAllDistrictByCode(ward_code, district_code, province_code)
            })
            .catch((error) => console.error("Error:", error));
    }

    $scope.getAllDistrictByCode = function (ward_code, district_code, provinceCode) {

        console.log(ward_code, district_code, provinceCode)
        axios
            .get(`https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/district`, {
                params: {
                    province_id: provinceCode,
                },
                headers: {
                    Accept: "application/json",
                    token: token,
                },

            })
            .then((res) => {
                const options = res.data.data;
                console.log(options)

                for (let i = 0; i < options.length; i++) {
                    const option = document.createElement("option");
                    option.value = options[i].DistrictID; // Set the value of the option (you can change this to any value you want)
                    option.text = options[i].DistrictName; // Set the text of the option
                    option.setAttribute("districtcode", options[i].DistrictID);
                    if (district_code === String(options[i].DistrictID)) {
                        option.selected = true;
                    }
                    selectDistrict.appendChild(option); // Add the option to the select element
                }
                $scope.getFullWardCodeByCode(ward_code, district_code)
            })
            .catch((error) => console.error("Error:", error));
    }

    $scope.getFullWardCodeByCode = function (ward_code, district_code) {

        axios.get(`https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/ward`, {
            headers: {
                Accept: "application/json",
                token: token,
            },
            params: {
                district_id: district_code,
            }
        })
            .then((res) => {
                //remove all child
                const options = res.data.data;
                for (let i = 0; i < options.length; i++) {
                    const option = document.createElement("option");
                    option.value = options[i].WardCode; // Set the value of the option (you can change this to any value you want)
                    option.text = options[i].WardName; // Set the text of the option
                    option.setAttribute("WardCode", options[i].WardCode);
                    if (ward_code === String(options[i].WardCode)) {
                        option.selected = true;
                    }
                    selectWardCodeCustomer.appendChild(option); // Add the option to the select element
                }
                $scope.getFeeShipping()
            })
            .catch((error) => console.error("Error:", error));

    }

    $scope.getAllDistrict = function () {
        const selectedOption = selectCityCustomer.options[selectCityCustomer.selectedIndex];
        const customAttribute = selectedOption.getAttribute("providecode");
        const provinceid = customAttribute;
        const selectDistrict = document.querySelector(` #district`);

        // remove child districts
        var old_options = selectDistrict.querySelectorAll("option");
        for (var i = 1; i < old_options.length; i++) {
            selectDistrict.removeChild(old_options[i]);
        }

        // remove child wards
        var old_options = selectWardCodeCustomer.querySelectorAll("option");
        for (var i = 1; i < old_options.length; i++) {
            selectWardCodeCustomer.removeChild(old_options[i]);
        }

        $scope.bill.maTinh = provinceid
        $scope.bill.tinh = selectedOption.textContent

        axios
            .get(`https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/district`, {
                params: {
                    province_id: provinceid,
                },
                headers: {
                    Accept: "application/json",
                    token: token,
                },

            })
            .then((res) => {
                const options = res.data.data;

                for (let i = 0; i < options.length; i++) {
                    const option = document.createElement("option");
                    option.value = options[i].DistrictID; // Set the value of the option (you can change this to any value you want)
                    option.text = options[i].DistrictName; // Set the text of the option
                    option.setAttribute("districtcode", options[i].DistrictID);
                    selectDistrict.appendChild(option); // Add the option to the select element
                }

                // remove shipp fee old
                $scope.totalAllPrice -= $scope.bill.phiVanChuyen;
                $scope.bill.phiVanChuyen = 0
                $scope.loadCustomer()
            })
            .catch((error) => console.error("Error:", error));
    }

    $scope.getFullWardCode = function () {
        const selectedOption = selectDistrict.options[selectDistrict.selectedIndex];
        const customAttribute = selectedOption.getAttribute("districtcode");
        const districtid = customAttribute;

        // remove child
        var old_options = selectWardCodeCustomer.querySelectorAll("option");
        for (var i = 1; i < old_options.length; i++) {
            selectWardCodeCustomer.removeChild(old_options[i]);
        }

        $scope.bill.maPhuong = districtid;
        $scope.bill.phuong = selectedOption.textContent

        axios.get(`https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/ward`, {
            headers: {
                Accept: "application/json",
                token: token,
            },
            params: {
                district_id: districtid,
            }
        })
            .then((res) => {
                //remove all child
                const options = res.data.data;
                for (let i = 0; i < options.length; i++) {
                    const option = document.createElement("option");
                    option.value = options[i].WardCode; // Set the value of the option (you can change this to any value you want)
                    option.text = options[i].WardName; // Set the text of the option
                    option.setAttribute("WardCode", options[i].WardCode);
                    selectWardCodeCustomer.appendChild(option); // Add the option to the select element
                }
            })
            .catch((error) => console.error("Error:", error));
    }

    // GET FEE SHIPPING
    $scope.getFeeShipping = () => {
        const district_selected = selectDistrict.options[selectDistrict.selectedIndex];
        const district_attribute = district_selected.getAttribute("districtcode");
        const id_district = district_attribute;

        const ward_selected = selectWardCodeCustomer.options[selectWardCodeCustomer.selectedIndex];
        const ward_attribute = ward_selected.getAttribute("WardCode");
        const code_ward = ward_attribute;

        $scope.bill.maXa = code_ward
        $scope.bill.xa = ward_selected.textContent

        axios.get(
            "https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee",
            {
                params: {
                    from_district_id: shopDistrictId,
                    from_ward_code: shopWardCode,
                    service_id: serviceID,
                    to_district_id: id_district,
                    to_ward_code: code_ward,
                    weight: 240,
                },
                headers: {
                    token: token,
                    Accept: "application/json",
                },
            }
        )
            .then((res) => {
                $scope.bill.phiVanChuyen = res.data.data.total
                $scope.totalAllPrice += $scope.bill.phiVanChuyen;
                $scope.loadCustomer()
            })
            .catch((error) => console.error("Error:", error));
    }

})