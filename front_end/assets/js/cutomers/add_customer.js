
main_app.controller("addCustomerController", function ($scope, $http) {

    var today = new Date();
    var file = "";
    $scope.customer = {
        ten: '',
        ngaySinh: '',
        cccd: '',
        gioiTinh: 1,
        email: "",
        soDienThoai: "",
        maTinh: "",
        maPhuong: "",
        maXa: "",
        tinh: "",
        phuong: "",
        xa: "",
        diaChi: "",
        avatar: ""
    }

    // REGEX
    var phone_regex = /(03|05|07|08|09|01[2|6|8|9])+([0-9]{8})\b/;
    var email_regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    // choose gender
    $scope.selectGender = function (gender) {
        $scope.customer.gioiTinh = gender
    }

    $scope.addCustomer = function () {

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

                axios.post("http://localhost:8080/customer/add", $scope.customer)
                    .then((res) => {
                        toastr.success('Bạn đã tạo thành công khách hàng');
                    })
                    .catch((error) => console.error("Error:", error));

                setTimeout(() => {
                    location.href = "/html/router.html#!/khach-hang"
                }, 400)

            })
            .catch((error) => {
                toastr.error('Bạn phải chọn ảnh đại diện');
            });
    }

    // FAST DELIVERY
    const token = "2b4b5f3e-ac78-11ee-a6e6-e60958111f48";
    const serviceID = 53320;
    const shopDistrictId = 1482;
    const shopWardCode = 11007;

    const selectCity = document.querySelector("#city");
    const districtSelect = document.querySelector("#district");
    const selectWardCode = document.querySelector("#ward");

    const ERROR_BORDER = '1px solid #dd3333'
    const SUCCESS_BORDER = '1px solid green'
    var API_BASE_URL = "/test/api/cart";
    var API_BASE_COOKIE_URL = "/test/api/cart/cookie";
    var LIST_ADDRESS = [];
    var ADDRESS_CURRENT = {}

    // FORMAT VND
    $scope.formatToVND = function (amount) {
        const formatter = new Intl.NumberFormat("vi-VN", {
            style: "currency",
            currency: "VND",
            minimumFractionDigits: 0, // Set to 0 to display whole numbers
        });
        return formatter.format(amount);
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
                defaultOption.disabled = true;
                defaultOption.selected = true;
                selectCity.appendChild(defaultOption);
                const options = data.data;
                for (let i = 0; i < options.length; i++) {
                    const option = document.createElement("option");
                    // option.value = options[i].ProvinceID; // Set the value of the option (you can change this to any value you want)
                    option.text = options[i].ProvinceName; // Set the text of the option
                    option.setAttribute("providecode", options[i].ProvinceID);
                    selectCity.appendChild(option); // Add the option to the select element
                }
            })
            .catch((error) => console.error("Error:", error));
    }

    // function
    $scope.getAllprovide()

    $scope.getAllDistrict = function () {
        const selectedOption = selectCity.options[selectCity.selectedIndex];
        const customAttribute = selectedOption.getAttribute("providecode");
        const provinceid = customAttribute;
        const selectDistrict = document.querySelector(` #district`);

        // remove child districts
        var old_options = selectDistrict.querySelectorAll("option");
        for(var i = 1; i < old_options.length; i++) {
            selectDistrict.removeChild(old_options[i]);
        }

        // remove child wards
        var old_options = selectWardCode.querySelectorAll("option");
        for(var i = 1; i < old_options.length; i++) {
            selectWardCode.removeChild(old_options[i]);
        }

        $scope.customer.maTinh = provinceid
        $scope.customer.tinh = selectedOption.textContent

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
        const selectedOption = districtSelect.options[districtSelect.selectedIndex];
        const customAttribute = selectedOption.getAttribute("districtcode");
        const districtid = customAttribute;

        // remove child
        var old_options = selectWardCode.querySelectorAll("option");
        for(var i = 1; i < old_options.length; i++) {
            selectWardCode.removeChild(old_options[i]);
        }

        $scope.customer.maPhuong = districtid;
        $scope.customer.phuong = selectedOption.textContent

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
                    selectWardCode.appendChild(option); // Add the option to the select element
                }
            })
            .catch((error) => console.error("Error:", error));
    }

    $scope.selectWard = function () {
        const ward_selected = selectWardCode.options[selectWardCode.selectedIndex];
        const ward_attribute = ward_selected.getAttribute("WardCode");
        const code_ward = ward_attribute;

        $scope.customer.maXa = code_ward
        $scope.customer.xa = ward_selected.textContent
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

})

// Button upload image






