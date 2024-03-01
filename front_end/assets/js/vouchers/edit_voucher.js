main_app.controller("editVoucherController", function($scope, $http, $routeParams){
    var id = $routeParams.id
    var today = new Date();
    $scope.voucher = {}

    $http.get('http://localhost:8080/voucher/get-voucher/' + id).then(
        function(res) {
            $scope.voucher = res.data;
            console.log($scope.voucher)
        },function(error) {
            console.log('Không tìm thấy voucher này.Vui lòng nhập lại id!')
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
})