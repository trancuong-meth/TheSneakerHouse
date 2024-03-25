clientApp.controller('billDetailController',
    function ($scope, $http, $routeParams) {
        var id = $routeParams.id
        var today = new Date();
        var file = "";

        // scroll to 0 0
        window.scrollTo(0, 0)

        // bill
        $scope.bill = {}
        $scope.billDetails = []
        $scope.history = []
        $scope.listProducts = []

        // panigation
        $scope.currentPage = 1;
        $scope.itemsPerPage = 5;
        $scope.totalItems = 0;

        // history
        $scope.billState1 = {}
        $scope.billState2 = {}
        $scope.billState3 = {}
        $scope.billState4 = {}
        $scope.billState6 = {}

        // note
        $scope.noteState1 = ""
        $scope.noteState2 = ""
        $scope.noteState3 = ""
        $scope.noteState4 = ""

        // product refund
        $scope.billDetailRefund = {}
        $scope.quantityRefund = 0;

        // REGEX
        var phone_regex = /(03|05|07|08|09|01[2|6|8|9])+([0-9]{8})\b/;
        var email_regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        // choose gender
        $scope.selectGender = function (gender) {
            $scope.customer.gioiTinh = gender
        }

        $scope.loadBill = () => {
            $http.get('http://localhost:8080/bill/get-bill/' + id).then(
                function (res) {
                    console.log(res.data)
                    $scope.bill = res.data;
                }, function (error) {
                    console.log('Không tìm thấy hóa đơn này.Vui lòng nhập lại id!')
                }
            )

            $http.get('http://localhost:8080/bill-detail/get-by-bill?id=' + id + "&page=" + 0 + "&size=" + 100).then(
                function (response) {
                    $scope.billDetails = response.data
                    $scope.totalItemBillDetails = response.data.totalElements
                    $scope.bill.tongTien = 0
                    for (var i = 0; i < $scope.billDetails.content.length; i++) {
                        if ($scope.billDetails.content[i].idSanPhamChiTiet.idDotGiamGia == null) {
                            $scope.bill.tongTien += Number($scope.billDetails.content[i].idSanPhamChiTiet.donGia) * Number($scope.billDetails.content[i].soLuong)
                        } else {
                            $scope.bill.tongTien += Number((100 - $scope.billDetails.content[i].idSanPhamChiTiet.idDotGiamGia.phanTramGiam) * $scope.billDetails.content[i].idSanPhamChiTiet.donGia / 100) * Number($scope.billDetails.content[i].soLuong)
                        }
                    }
                    $scope.bill.tongTienSauGiam = Number($scope.bill.tongTien)

                    if ($scope.bill.idVoucher != null) {
                        $scope.bill.tongTienSauGiam = (100 - $scope.bill.idVoucher.phanTramGiam) * $scope.bill.tongTien / 100
                    }

                    $scope.bill.tongTienSauGiam += Number($scope.bill.phiVanChuyen == null || $scope.bill.phiVanChuyen == "" ? 0 : $scope.bill.phiVanChuyen)
                }
            ).catch(function (error) {
                console.log(error)
            })

            $http.get("http://localhost:8080/history/get-all-by-id/" + id).then(function (response) {
                $scope.history = response.data
                console.log($scope.history)
                for (var i = 0; i < $scope.history.length; i++) {
                    if ($scope.history[i].trangThai == 1) {
                        $scope.billState1 = $scope.history[i]
                    } else if ($scope.history[i].trangThai == 2) {
                        $scope.billState2 = $scope.history[i]
                    } else if ($scope.history[i].trangThai == 3) {
                        $scope.billState3 = $scope.history[i]
                    } else if ($scope.history[i].trangThai == 4) {
                        $scope.billState4 = $scope.history[i]
                    } else if ($scope.history[i].trangThai == 6) {
                        $scope.billState6 = $scope.history[i]
                    }
                }
                console.log($scope.history)
            })
                .catch(function (error) {

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
                                <img src="${response.data[i].duongDan}" class="d-block w-100" alt="...">
                            </div>`
                    } else {
                        textCenter += `
                            <div class="carousel-item">
                                <img src="${response.data[i].duongDan}" class="d-block w-100" alt="...">
                            </div>`
                    }

                }

                html.innerHTML = textFist + textCenter + textLast

            }).catch(function (error) {
                console.log(error)
            })
        }

        $scope.loadBill()

        $scope.updateVoucher = function () {

            if ($scope.voucher.ten === "" ||
                $scope.voucher.giaTriToiDa === '' ||
                $scope.voucher.giaTriToiThieu === '' ||
                $scope.voucher.phanTramGiam === '' ||
                $scope.voucher.soLanDung === '' ||
                $scope.voucher.ngayBatDau === '' ||
                $scope.voucher.ngayKetThuc === ''
            ) {
                toastr.error('Bạn phải nhập đủ các trường có trên form')
                return;
            }

            if (isNaN($scope.voucher.giaTriToiDa)) {
                toastr.error('Giá trị tối đa phải là số')
                return;
            }

            if (isNaN($scope.voucher.giaTriToiThieu)) {
                toastr.error('Giá trị đơn tối thiểu phải là số')
                return;
            }

            if (isNaN($scope.voucher.phanTramGiam)) {
                toastr.error('Giá trị phần trên phải là số')
                return;
            }

            if (isNaN($scope.voucher.soLanDung)) {
                toastr.error('Số lần phải là số')
            }

            if ($scope.voucher.giaTriToiDa < 0) {
                toastr.error('Giá trị tối đa phải là số duơng')
                return;
            }

            if ($scope.voucher.giaTriToiThieu < 0) {
                toastr.error('Giá trị đơn tối thiểu phải là số duơng')
                return;
            }

            if ($scope.voucher.phanTramGiam < 0) {
                toastr.error('Giá trị phần trăm phải là số duơng')
                return;
            }

            if ($scope.voucher.phanTramGiam >= 100) {
                toastr.error('Giá trị phần trăm phải nhỏ hơn 100%')
                return;
            }

            if ($scope.voucher.soLanDung < 0) {
                toastr.error('Số lượng phải là số duơng')
                return;
            }

            if ($scope.voucher.ngayBatDau > $scope.voucher.ngayKetThuc) {
                toastr.error('Ngày bắt đầu phải nhỏ hơn ngày kết thúc')
                return;
            }

            if ($scope.voucher.ngayBatDau < today) {
                toastr.error('Ngày bắt đầu phải lớn hơn ngày hôm nay')
                return;
            }

            axios.put('http://localhost:8080/voucher/edit-voucher', $scope.voucher).then(
                (response) => {
                    toastr.success('Bạn đã thay đổi voucher thành công')
                    setTimeout(() => {
                        location.href = "/html/router.html#!/phieu-giam-gia"
                    }, 400)
                }
            ).catch((error) => {
                console.log(error)
                toastr.error('Đã có lỗi xảy ra.Vui lòng liên hệ quản trị viên')
            });

        }

        // FAST DELIVERY
        const token = "2b4b5f3e-ac78-11ee-a6e6-e60958111f48";
        const serviceID = 53320;
        const shopDistrictId = 1482;
        const shopWardCode = 11007;

        const selectCityCustomer = document.querySelector("#city");
        const selectDistrict = document.querySelector("#district");
        const selectWardCodeCustomer = document.querySelector("#ward");

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
                    defaultOption.disabled = true;
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
                    $scope.bill.tongTienSauGiam = $scope.bill.tongTien + res.data.data.total
                    $scope.bill.phiVanChuyen = res.data.data.total
                })
                .catch((error) => console.error("Error:", error));
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
                })
                .catch((error) => console.error("Error:", error));
        }

        var readURL = function (input) {
            if (input.files && input.files[0]) {
                var reader = new FileReader();

                reader.onload = function (e) {
                    $('.profile_pic').attr('src', e.target.result);
                }

                reader.readAsDataURL(input.files[0]);
            }
        }

        $scope.changeImage = function (element) {

            if (element.files && element.files[0]) {
                const formData = new FormData();
                formData.append('file', element.files[0]);
                file = formData
            }

            readURL(element)
            var icon = document.getElementsByClassName('icon-upload-button')[0]
            icon.classList.add('icon-upload-button-close')
        }

        $scope.updateCustomer = function () {
            $scope.customer.gioiTinh = Number($scope.customer.gioiTinh)

            if (file === "") {

                if ($scope.customer.ten === "" ||
                    $scope.customer.ngaySinh === ""
                    || $scope.customer.cccd === ""
                    || $scope.customer.gioiTinh === ""
                    || $scope.customer.email === ""
                    || $scope.customer.soDienThoai === ""
                    || $scope.customer.maTinh === ""
                    || $scope.customer.maPhuong === ""
                    || $scope.customer.maXa === ""
                    || $scope.customer.diaChi === ""
                    || $scope.customer.avatar === "") {
                    toastr.error('Bạn phải nhập đầy các trường có trên form ')
                    return;
                }

                if ($scope.customer.ngaySinh > today) {
                    toastr.error('Ngày sinh phải nhỏ hơn ngày hôm nay')
                    return;
                }

                if ($scope.customer.cccd.length != 12) {
                    toastr.error('Nhập đủ 12 số căn cước công dân')
                    return;
                }

                if (!email_regex.test($scope.customer.email)) {
                    toastr.error('Bạn phải nhập đúng định dạng email')
                    return;
                }

                if (!phone_regex.test($scope.customer.soDienThoai)) {
                    toastr.error('Bạn phải nhập đúng định dạng số điện thoại')
                    return;
                }

                axios.put("http://localhost:8080/customer/update", $scope.customer)
                    .then((res) => {
                        toastr.success('Bạn đã thay đổi thông tin thành công!!!');
                    })
                    .catch((error) => console.error("Error:", error));

                setTimeout(() => {
                    location.href = "/html/router.html#!/khach-hang"
                }, 400)

            } else {

                axios.post("http://localhost:8080/cloudinary/upload",
                    file,
                    {
                        headers: {
                            'Content-Type': 'multipart/form-data',
                        }
                    })
                    .then((res) => {
                        $scope.customer.avatar = res.data.secure_url

                        if ($scope.customer.ten === "" ||
                            $scope.customer.ngaySinh === ""
                            || $scope.customer.cccd === ""
                            || $scope.customer.gioiTinh === ""
                            || $scope.customer.email === ""
                            || $scope.customer.soDienThoai === ""
                            || $scope.customer.maTinh === ""
                            || $scope.customer.maPhuong === ""
                            || $scope.customer.maXa === ""
                            || $scope.customer.diaChi === ""
                            || $scope.customer.avatar === "") {
                            toastr.error('Bạn phải nhập đầy các trường có trên form ')
                            return;
                        }

                        if ($scope.customer.ngaySinh > today) {
                            toastr.error('Ngày sinh phải nhỏ hơn ngày hôm nay')
                            return;
                        }

                        if ($scope.customer.cccd.length != 12) {
                            toastr.error('Nhập đủ 12 số căn cước công dân')
                            return;
                        }

                        if (!email_regex.test($scope.customer.email)) {
                            toastr.error('Bạn phải nhập đúng định dạng email')
                            return;
                        }

                        if (!phone_regex.test($scope.customer.soDienThoai)) {
                            toastr.error('Bạn phải nhập đúng định dạng số điện thoại')
                            return;
                        }

                        axios.put("http://localhost:8080/customer/update", $scope.customer)
                            .then((res) => {
                                toastr.success('Bạn đã thay đổi thông tin thành công!!!');
                            })
                            .catch((error) => console.error("Error:", error));

                        setTimeout(() => {
                            location.href = "/html/router.html#!/khach-hang"
                        }, 400)

                    })
                    .catch((error) => toastr.error('Bạn phải chọn ảnh đại diện'));

            }
        }

        $scope.formatToVND = function (amount) {
            const formatter = new Intl.NumberFormat("vi-VN", {
                style: "currency",
                currency: "VND",
                minimumFractionDigits: 0, // Set to 0 to display whole numbers
            });
            return formatter.format(amount);
        }

        $scope.print = () => {
            var e = document.getElementById("invoice")
            console.log(e)
            w = window.open();
            w.document.write(e.innerHTML);
            w.print();
            w.close();
        }

        $scope.confirmBill = () => {
            if ($scope.bill === null) {
                toastr.error('Đã có lỗi xảy ra vui lòng kiểm tra lại')
            } else {
                var brandUpdateModal = document.querySelector("#confirmModal")
                var modal = bootstrap.Modal.getOrCreateInstance(brandUpdateModal)

                modal.hide()
                $scope.bill.trangThai = 2;
                $scope.updateStateOfBill($scope.bill.trangThai, $scope.noteState1)
                toastr.success("Xác nhận hóa đơn thành công")
                setTimeout(() => {
                    $scope.loadBill()
                }, 100)
            }

        }

        $scope.shippBill = function () {
            if ($scope.bill === null) {
                toastr.error('Đã có lỗi xảy ra vui lòng kiểm tra lại')
            } else {
                var brandUpdateModal = document.querySelector("#shipModal")
                var modal = bootstrap.Modal.getOrCreateInstance(brandUpdateModal)

                modal.hide()
                $scope.bill.trangThai = 3;
                $scope.updateStateOfBill($scope.bill.trangThai, $scope.noteState2)
                toastr.success("Xác nhận hóa đơn thành công")
                setTimeout(() => {
                    $scope.loadBill()
                }, 100)
            }

        }

        $scope.successBill = function () {
            if ($scope.bill === null) {
                toastr.error('Đã có lỗi xảy ra vui lòng kiểm tra lại')
            } else {
                var brandUpdateModal = document.querySelector("#successModal")
                var modal = bootstrap.Modal.getOrCreateInstance(brandUpdateModal)

                modal.hide()
                $scope.bill.trangThai = 4;
                $scope.updateStateOfBill($scope.bill.trangThai, $scope.noteState3)
                toastr.success("Xác nhận hóa đơn thành công")
                setTimeout(() => {
                    $scope.loadBill()
                }, 100)
            }
        }

        $scope.updateStateOfBill = (trangThai, ghiChu) => {
            axios.put('http://localhost:8080/bill/update-bill', $scope.bill).then(function (response) {
                axios.post('http://localhost:8080/history/add', {
                    'trangThai': trangThai,
                    'ghiChu': ghiChu,
                    'hoaDon': response.data
                }).then(function (response) {

                }).catch(function (error) {
                    console.log(error);
                })

            })
                .catch(function (response) {
                    $scope.loadBill()
                    console.log(response.data)
                })
        }

        $scope.editCustomer = () => {
            setTimeout(() => {
                $scope.getAllprovideByCode($scope.bill.maPhuong, $scope.bill.maXa, $scope.bill.maTinh)
                selectCityCustomer = document.getElementById("city");
                selectDistrict = document.getElementById("district");
                selectWardCodeCustomer = document.getElementById("ward");
            })

        }

        $scope.updateCustomer = function () {
            var customerEditModal = document.querySelector("#customerEdit")
            var modal = bootstrap.Modal.getOrCreateInstance(customerEditModal)

            axios.put('http://localhost:8080/bill/update-bill', $scope.bill).then(function (response) {
                toastr.success("Thay đổi thông tin khách hàng thành công.");
                modal.hide()
            })
                .catch(function (response) {
                    $scope.loadBills()
                })

        }

        $scope.minusQuantity = function (billDetail) {
            var quantityHtml = document.getElementById("bill-detail-quantity-" + billDetail.id)
            quantityHtml.value = Number(quantityHtml.value) - 1
            if (quantityHtml.value == 0) {
                $scope.removeBillDetailByBillAndProductDetail(billDetail.id)
            } else {
                $scope.addProductToBillApi(billDetail.idSanPhamChiTiet, $scope.bill, quantityHtml.value)
            }

        }

        $scope.plusQuantity = function (billDetail) {
            var quantityHtml = document.getElementById("bill-detail-quantity-" + billDetail.id)
            quantityHtml.value = Number(quantityHtml.value) + 1
            $scope.addProductToBillApi(billDetail.idSanPhamChiTiet, $scope.bill, quantityHtml.value)
        }

        $scope.removeBillDetailByBillAndProductDetail = function (billDetailId) {
            axios.delete('http://localhost:8080/bill-detail/remove-by-id/' + billDetailId).then(function (response) {
                $scope.loadProductDetailByBillId($scope.bill)
            }).catch(function (response) {
                $scope.loadProductDetailByBillId($scope.bill)
            })
        }

        $scope.loadBillDetail = () => {
            setTimeout(() => {
                axios.put('http://localhost:8080/bill/update-bill', $scope.bill).then(function (response) {
                }).catch(function (response) {
                })
            }, 200)
        }

        $scope.addProductToBillApi = function (productDetail, bill, quantity) {

            $http.post('http://localhost:8080/bill-detail/add-product-to-bill', {
                'hoaDon': bill,
                'sanPhamChiTiet': productDetail,
                'soLuong': quantity
            }).then(function (response) {
                // check gia tri toi thieu voucher 
                if ($scope.bill.idVoucher !== null) {
                    if (quantity * productDetail.donGia < $scope.bill.idVoucher.giaTriToiThieu) {
                        $scope.removeVoucher();
                    }
                }

                setTimeout(() => {
                    $scope.loadBill()
                }, 100)

                $scope.loadBillDetail()

            }).catch(function (error) {
                console.log(error);
                toastr.error(error);
            })

        }

        $scope.removeVoucher = function () {
            axios.put('http://localhost:8080/bill/add-voucher-to-bill', {
                'voucher': null,
                'hoaDon': $scope.bill
            }).then(function (response) {
                toastr.success("Xóa voucher thành công.");
            }).catch(function (response) {
                console.log(response.data)
            })
        }

        $scope.loadProductList = function () {
            $http.get('http://localhost:8080/product-detail/get-all?page=' + ($scope.currentPage - 1) + '&size=' + $scope.itemsPerPage,)
                .then(function (response) {
                    $scope.listProducts = response.data
                    $scope.totalItems = response.data.totalElements
                }).catch(function (error) {
                    console.log(error)
                });
        }

        $scope.pageChanged = function () {
            $http.get('http://localhost:8080/product-detail/get-all?page=' + ($scope.currentPage - 1) + '&size=' + $scope.itemsPerPage,)
                .then(function (response) {
                    $scope.productDetails = response.data
                });
        };

        $scope.addProductToBill = function (productDetail) {
            var brandModal = document.querySelector("#productListModal")
            var addModal = bootstrap.Modal.getOrCreateInstance(brandModal)
            $scope.addProductToBillApi(productDetail, $scope.bill, -1)
            $scope.loadBillDetail()
            addModal.hide()
        }

        $scope.cancelBill = () => {
            Swal.fire({
                title: "Xác nhận hủy hóa đơn này?",
                icon: "warning",
                showCancelButton: true,
                confirmButtonColor: "#3085d6",
                cancelButtonColor: "#d33",
                confirmButtonText: "Xác nhận",
                cancelButtonText: "Hủy"
            }).then((result) => {
                if (result.isConfirmed) {

                    $scope.bill.trangThai = 5
                    axios.put('http://localhost:8080/bill/update-bill', $scope.bill).then(function (response) {

                        $scope.billDetails.content.forEach((x) => {
                            x.idSanPhamChiTiet.soLuongTon += x.soLuong
                            axios.put('http://localhost:8080/product-detail/update-product-detail', x.idSanPhamChiTiet)
                                .then((response) => {

                                }).catch((error) => {
                                    console.log(error)
                                })
                        })

                        if ($scope.bill.idVoucher != null) {
                            var voucher = $scope.bill.idVoucher
                            voucher.soLanDung += 1
                            axios.put('http://localhost:8080/voucher/edit-voucher', voucher)
                                .then((response) => {
                                }).catch((error) => {
                                    console.log(error)
                                })
                        }

                        toastr.success("Hủy hóa đơn thành công.")

                        setTimeout(() => {
                            $scope.loadBill()
                        }, 100)
                    })
                        .catch(function (response) {
                            $scope.loadBills()
                        })
                }
            });
        }

        $scope.refundBill = () => {

            if ($scope.noteState4 == "") {
                toastr.error('Vui lòng nhập lý do')
                return;
            }

            if ($scope.bill === null) {
                toastr.error('Đã có lỗi xảy ra vui lòng kiểm tra lại')
            } else {
                var brandUpdateModal = document.querySelector("#refundModal")
                var modal = bootstrap.Modal.getOrCreateInstance(brandUpdateModal)

                modal.hide()
                $scope.bill.trangThai = 6;
                $scope.updateStateOfBill($scope.bill.trangThai, $scope.noteState4)
                toastr.success("Trả hàng thành công.")
                setTimeout(() => {
                    $scope.loadBill()
                }, 100)
            }
        }

        $scope.refundSingleBill = () => {
            if ($scope.noteState4 == "") {
                toastr.error('Vui lòng nhập lý do')
                return;
            }

            if (isNaN($scope.quantityRefund)) {
                toastr.error("Số lượng phải là số")
                return;
            }

            if (Number($scope.quantityRefund) < 0) {
                toastr.error("Số lượng phải lớn hơn 0")
                quantityHtml.value = 0;
                return;
            }

            if ($scope.bill === null) {
                toastr.error('Đã có lỗi xảy ra vui lòng kiểm tra lại')
            } else {
                var brandUpdateModal = document.querySelector("#billDetailSingleRefund")
                var modal = bootstrap.Modal.getOrCreateInstance(brandUpdateModal)

                modal.hide()
                $scope.bill.trangThai = 6;
                $scope.updateStateOfBill($scope.bill.trangThai, $scope.noteState4)
                toastr.success("Trả hàng thành công.")

                // $scope.addProductToBillApi($scope.billDetailRefund.idSanPhamChiTiet, $scope.bill, $scope.billDetailRefund.soLuong - $scope.quantityRefund)

                setTimeout(() => {
                    $scope.loadBill()
                }, 100)
            }
        }



    });