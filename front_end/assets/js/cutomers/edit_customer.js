main_app.controller("editCustomerController", function($scope, $http, $routeParams){
    var id = $routeParams.id
    var today = new Date();
    var file = "";
    $scope.customer = {}

    // REGEX
    var phone_regex = /(03|05|07|08|09|01[2|6|8|9])+([0-9]{8})\b/;
    var email_regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    // choose gender
    $scope.selectGender = function (gender) {
        $scope.customer.gioiTinh = gender
    }

    $http.get('http://localhost:8080/customer/get-customer/' + id).then(
        function(res) {
            $scope.customer = res.data;
            $scope.getAllprovideByCode($scope.customer.maPhuong, $scope.customer.maXa, $scope.customer.maTinh)
        },function(error) {
            console.log('Không tìm thấy khách hàng này.Vui lòng nhập lại id!')
        }
    )

    $scope.updateVoucher = function(){
        
        if($scope.voucher.ten === "" || 
            $scope.voucher.giaTriToiDa === '' ||
            $scope.voucher.giaTriToiThieu === '' ||
            $scope.voucher.phanTramGiam === '' ||
            $scope.voucher.soLanDung === '' || 
            $scope.voucher.ngayBatDau === '' ||
            $scope.voucher.ngayKetThuc === ''
        ){
            toastr.error('Bạn phải nhập đủ các trường có trên form')
            return;
        }

        if(isNaN($scope.voucher.giaTriToiDa)){
            toastr.error('Giá trị tối đa phải là số')
            return;
        }

        if(isNaN($scope.voucher.giaTriToiThieu)){
            toastr.error('Giá trị đơn tối thiểu phải là số')
            return;
        }

        if(isNaN($scope.voucher.phanTramGiam)){
            toastr.error('Giá trị phần trên phải là số')
            return;
        }

        if(isNaN($scope.voucher.soLanDung)){
            toastr.error('Số lần phải là số')
        }

        if($scope.voucher.giaTriToiDa < 0){
            toastr.error('Giá trị tối đa phải là số duơng')
            return;
        }

        if($scope.voucher.giaTriToiThieu < 0){
            toastr.error('Giá trị đơn tối thiểu phải là số duơng')
            return;
        }

        if($scope.voucher.phanTramGiam < 0){
            toastr.error('Giá trị phần trăm phải là số duơng')
            return;
        }

        if($scope.voucher.phanTramGiam >= 100){
            toastr.error('Giá trị phần trăm phải nhỏ hơn 100%')
            return;
        }

        if($scope.voucher.soLanDung < 0){
            toastr.error('Số lượng phải là số duơng')
            return;
        }

        if($scope.voucher.ngayBatDau > $scope.voucher.ngayKetThuc){
            toastr.error('Ngày bắt đầu phải nhỏ hơn ngày kết thúc')
            return; 
        }

        if($scope.voucher.ngayBatDau < today){
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
        for(var i = 1; i < old_options.length; i++) {
            selectDistrict.removeChild(old_options[i]);
        }

        // remove child wards
        var old_options = selectWardCodeCustomer.querySelectorAll("option");
        for(var i = 1; i < old_options.length; i++) {
            selectWardCodeCustomer.removeChild(old_options[i]);
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
        const selectedOption = selectDistrict.options[selectDistrict.selectedIndex];
        const customAttribute = selectedOption.getAttribute("districtcode");
        const districtid = customAttribute;

        // remove child
        var old_options = selectWardCodeCustomer.querySelectorAll("option");
        for(var i = 1; i < old_options.length; i++) {
            selectWardCodeCustomer.removeChild(old_options[i]);
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
                    selectWardCodeCustomer.appendChild(option); // Add the option to the select element
                }
            })
            .catch((error) => console.error("Error:", error));
    }

    $scope.selectWard = function () {
        const ward_selected = selectWardCodeCustomer.options[selectWardCodeCustomer.selectedIndex];
        const ward_attribute = ward_selected.getAttribute("WardCode");
        const code_ward = ward_attribute;

        $scope.customer.maXa = code_ward
        $scope.customer.xa = ward_selected.textContent

        console.log($scope.customer)
    }

    $scope.getAllprovideByCode = function (district_code, ward_code, province_code) {
    
        // const thisOrder = document.getElementById(`hoaDon${orderId}`);
        fetch( `https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/province`, {
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
                    if(province_code === String(options[i].ProvinceID)){
                        option.selected = true;
                    }
                    selectCityCustomer.appendChild(option); // Add the option to the select element
                }
                $scope.getAllDistrictByCode(ward_code, district_code, province_code)
            })
            .catch((error) => console.error("Error:", error));
    }
    
    $scope.getAllDistrictByCode = function(ward_code, district_code, provinceCode) {

        console.log(ward_code, district_code, provinceCode)
        axios
            .get(`https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/district`, {
                params: {
                    province_id: provinceCode,
                },
                headers: {
                    Accept:  "application/json",
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
                    if(district_code === String(options[i].DistrictID)){
                        option.selected = true;
                    }
                    selectDistrict.appendChild(option); // Add the option to the select element
                }
                $scope.getFullWardCodeByCode(ward_code, district_code)
            })
            .catch((error) => console.error("Error:", error));
    }
    
    $scope.getFullWardCodeByCode = function(ward_code, district_code) {

        axios.get( `https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/ward`, {
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
                    if(ward_code === String(options[i].WardCode)){
                        option.selected = true;
                    }
                    selectWardCodeCustomer.appendChild(option); // Add the option to the select element
                }
                validateAddress()
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

        if(file === ""){

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

        }else{

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
})