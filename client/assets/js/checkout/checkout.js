clientApp.controller('checkoutController',
    function ($scope, $http,  $window) {
        // id
        $scope.cartDetails = [];

        // scroll to 0 0
        window.scrollTo(0, 0)

        // total
        $scope.subTotal = 0;
        $scope.total = 0;
        $scope.shipFee = 0;
        $scope.paymentMethod = 0;

        // customer
        $scope.customer = JSON.parse(localStorage.getItem("user"));

        // user
        $scope.bill = {};

        // voucher
        $scope.codeVoucher = ""
        $scope.voucher = null;

        // REGEX
        var phone_regex = /(03|05|07|08|09|01[2|6|8|9])+([0-9]{8})\b/;
        var email_regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        // FAST DELIVERY
        const token = "2b4b5f3e-ac78-11ee-a6e6-e60958111f48";
        const serviceID = 53320;
        const shopDistrictId = 1482;
        const shopWardCode = 11007;

        var selectCityCustomer = document.querySelector("#city");
        var selectDistrict = document.querySelector("#district");
        var selectWardCodeCustomer = document.querySelector("#ward");

        $scope.loadDataProductDetail = () => {
            var id = $scope.customer == null ? -1 : $scope.customer.id
            $http.get('http://localhost:8080/cart/get-cart-detail-by-id/' + id).then(function (response) {
                $scope.cartDetails = response.data
                $scope.subTotal = 0
                for (var i = 0; i < $scope.cartDetails.length; i++) {
                    $scope.subTotal += $scope.cartDetails[i].idSanPhamChiTiet.idDotGiamGia == null ? $scope.cartDetails[i].soLuong * $scope.cartDetails[i].idSanPhamChiTiet.donGia : $scope.cartDetails[i].soLuong * (100 - $scope.cartDetails[i].idSanPhamChiTiet.idDotGiamGia.phanTramGiam) / 100 * $scope.cartDetails[i].idSanPhamChiTiet.donGia
                }

                $scope.total = $scope.subTotal;

                if ($scope.bill.phiVanChuyen !== null && $scope.bill.phiVanChuyen !== undefined) {
                    $scope.total += Number($scope.bill.phiVanChuyen)
                }

                if ($scope.bill.idVoucher != null) {
                    $scope.total -= Number($scope.bill.idVoucher.phanTramGiam * $scope.subTotal / 100)
                }
            }).catch(function (error) {
                console.log(error)
            })
        }

        $scope.getAllprovideByCode = function (district_code, ward_code, province_code) {
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

        $scope.getAllprovide = function () {
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
                    defaultOption.textContent = "--Chọn Tỉnh/Thành phố--"; // Set the text content
                    // Set the 'disabled' and 'selected' attributes to make it the default option
                    defaultOption.disabled = false;
                    defaultOption.selected = true;
                    selectCityCustomer.appendChild(defaultOption);
                    const options = data.data;
                    for (let i = 0; i < options.length; i++) {
                        const option = document.createElement("option");
                        // option.value = options[i].ProvinceID; // Set the value of the option (you can change this to any value you want)
                        option.text = options[i].ProvinceName; // Set the text of the option
                        option.setAttribute("providecode", options[i].ProvinceID);
                        selectCityCustomer.appendChild(option); // Add the option to the select element
                    }
                })
                .catch((error) => console.error("Error:", error));
        }

        $scope.getNewBill = () => {
            $http.get('http://localhost:8080/bill/get-new-bill').then(function (response) {
                $scope.bill = response.data
                if($scope.customer != null){
                    $scope.bill.idKhachHang = $scope.customer
                    $scope.bill.tenNguoiNhan = $scope.customer.ten
                    $scope.bill.email = $scope.customer.email
                    $scope.bill.sdtNguoiNhan = $scope.customer.soDienThoai
                    $scope.bill.diaChi = $scope.customer.diaChi
                    $scope.bill.xa = $scope.customer.xa
                    $scope.bill.phuong = $scope.customer.phuong
                    $scope.bill.tinh = $scope.customer.tinh
                    $scope.bill.maXa = $scope.customer.maXa
                    $scope.bill.maPhuong = $scope.customer.maPhuong
                    $scope.bill.maTinh = $scope.customer.maTinh

                    if($scope.customer.maXa == null && $scope.customer.maPhuong == null && $scope.customer.maTinh == null){
                        $scope.getAllprovide()
                    }else{
                        $scope.getAllprovideByCode($scope.customer.maPhuong, $scope.customer.maXa, $scope.customer.maTinh)
                    }
                }else {
                    $scope.getAllprovide()
                }
            }).catch(function (error) {
                console.log(error)
            })
        }

        $scope.loadDataProductDetail();
        $scope.getNewBill()

        $scope.formatToVND = function (amount) {
            const formatter = new Intl.NumberFormat("vi-VN", {
                style: "currency",
                currency: "VND",
                minimumFractionDigits: 0, // Set to 0 to display whole numbers
            });
            return formatter.format(amount);
        }

        $scope.getAllImagesByIDProductDetail = function (id) {
            var html = document.getElementById("image-" + id);
            axios.get('http://localhost:8080/image/get-all/' + id).then(function (response) {
                html.src = response.data[0].duongDan
            }).catch(function (error) {
                console.log(error)
            })
        }

        $scope.changePaymentMethod = () => {
            if ($scope.paymentMethod == 0) {
                $scope.paymentMethod = 1
            } else if ($scope.paymentMethod == 1) {
                $scope.paymentMethod = 0
            }
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


                })
                .catch((error) => console.error("Error:", error));

            if ($scope.bill.phiVanChuyen !== null && $scope.bill.phiVanChuyen !== undefined) {
                console.log($scope.bill.phiVanChuyen)
                $scope.bill.phiVanChuyen = 0;
                $scope.shipFee = 0
                $scope.loadDataProductDetail()
            }
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
    
        $scope.getAllDistrictByCode = function (ward_code, district_code, provinceCode) {
    
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
                    $scope.totalPrice += $scope.bill.phiVanChuyen;
                    $scope.shipFee = res.data.data.total
                    $scope.loadDataProductDetail();
                })
                .catch((error) => console.error("Error:", error));
        }

        $scope.removeCartDetail = (e) => {
            axios.delete('http://localhost:8080/cart-detail/remove-cart-detail/' + e.id).then(function (response) {
            }).catch(function (error) {
                console.log(error)
            })
        }

        $scope.checkout = () => {

            if ($scope.bill.tenNguoiNhan === null
                || $scope.bill.email === null
                || $scope.bill.sdtNguoiNhan === null
                || $scope.bill.maTinh === null
                || $scope.bill.maPhuong === null
                || $scope.bill.maXa === null
                || $scope.bill.diaChi === null) {
                toastr.error('Bạn phải nhập đầy các trường có trên form ')
                return;
            }

            if (!email_regex.test($scope.bill.email)) {
                toastr.error('Bạn phải nhập đúng định dạng email')
                return;
            }

            if (!phone_regex.test($scope.bill.sdtNguoiNhan)) {
                toastr.error('Bạn phải nhập đúng định dạng số điện thoại')
                return;
            }

            Swal.fire({
                title: "Xác nhận tạo đơn hàng này?",
                icon: "warning",
                showCancelButton: true,
                confirmButtonColor: "#3085d6",
                cancelButtonColor: "#d33",
                confirmButtonText: "Xác nhận",
                cancelButtonText: "Hủy"
            }).then((result) => {
                if (result.isConfirmed) {
                    //create bill
                    $scope.bill.phuongThucThanhToan = $scope.paymentMethod
                    $scope.bill.tongTien = $scope.subTotal
                    $scope.bill.tongTienSauGiam = $scope.total
                    $scope.bill.trangThai = 1
                    $scope.bill.loaiHoaDon = 1

                    if ($scope.voucher != null) {
                        $scope.bill.idVoucher = $scope.voucher
                    }

                    axios.put('http://localhost:8080/bill/update-bill', $scope.bill).then(function (response) {

                        axios.post('http://localhost:8080/history/add', {
                            'trangThai': 1,
                            'ghiChu': $scope.bill.ghiChu,
                            'hoaDon': response.data
                        }).then(function (response) {

                        }).catch(function (error) {
                            console.log(error);
                        })

                        $scope.cartDetails.forEach((x) => {

                            $scope.removeCartDetail(x)
                            $http.post('http://localhost:8080/bill-detail/add-product-to-bill', {
                                'hoaDon': response.data,
                                'sanPhamChiTiet': x.idSanPhamChiTiet,
                                'soLuong': x.soLuong
                            }).then(function (response) {
                            }).catch(function (error) {
                                toastr.error(error.data.message)
                            })

                            x.idSanPhamChiTiet.soLuongTon -= x.soLuong
                            axios.put('http://localhost:8080/product-detail/update-product-detail', x.idSanPhamChiTiet)
                                .then((response) => {

                                }).catch((error) => {
                                    console.log(error)
                                })
                        })

                        if ($scope.voucher != null) {
                            $scope.voucher.soLanDung -= 1
                            axios.put('http://localhost:8080/voucher/edit-voucher', $scope.voucher)
                                .then((response) => {
                                    toastr.success("Tạo đơn hàng thành công.");
                                    setTimeout(function () {
                                        $window.location.href= '#!chi-tiet-hoa-don/' + response.data.id;
                                        $window.location.reload();
                                        window.scrollTo(0, 0);
                                    }, 200)
                                }).catch((error) => {
                                    console.log(error)
                                })
                        } else {
                            toastr.success("Tạo đơn hàng thành công.");
                            setTimeout(function () {
                                $window.location.href= '#!chi-tiet-hoa-don/' + response.data.id;
                                $window.location.reload();
                            }, 200)
                        }

                    })
                    .catch(function (response) {
                        console.log(response.data)
                    })
                }
            });
        }

        $scope.checkVoucher = () => {
            var codeVoucher = document.getElementById("code_voucher").value
            $scope.codeVoucher = codeVoucher
            if ($scope.codeVoucher == "" || $scope.codeVoucher == null) {
                toastr.error("Vui lòng nhập mã phiếu giảm giá!!!")
                return;
            }

            $http.get("http://localhost:8080/voucher/get-voucher-absolute?id_voucher=" + $scope.codeVoucher).then(function (response) {
                // if(response.data.)
                if (response.data.trangThai !== 1) {
                    toastr.error("Mã phiếu giảm giá đã hết hạn.Vui lòng chọn mã phiếu khác!!!")
                    return;
                }

                if (response.data.giaTriToiThieu > $scope.subTotal) {
                    toastr.error("Giá trị hóa đơn không đủ điều kiện để sử dụng mã phiếu giảm giá này.")
                    return;
                }

                $scope.voucher = response.data
                $scope.bill.idVoucher = response.data;
                setTimeout(() => {
                    $scope.loadDataProductDetail()
                }, 100);
            }).catch(function (error) {
                toastr.error(error.data.message)
                return;
            })
        }

        $scope.removeVoucher = () => {
            $scope.voucher = null
            $scope.bill.idVoucher = null
            $scope.loadDataProductDetail()
            setTimeout(() => {
                document.getElementById("code_voucher").value = ""
                $scope.codeVoucher = ""
            }, 10);
        }

    });