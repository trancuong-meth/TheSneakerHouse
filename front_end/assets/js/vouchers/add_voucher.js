// add voucher
main_app.controller("addVoucherController", function($scope, $http){

    var today = new Date();
    $scope.voucher = {
        max_price : '',
        min_price : '',
        percent : '',
        quantity: '',
        name : "",
        date_start : "",
        date_end : ""
    }
    
    $scope.addVoucher = function(){
        
        if($scope.voucher.name === "" || 
            $scope.voucher.max_price === '' ||
            $scope.voucher.min_price === '' ||
            $scope.voucher.percent === '' ||
            $scope.voucher.quantity === '' || 
            $scope.voucher.date_start === '' ||
            $scope.voucher.date_end === ''
        ){
            toastr.error('Bạn phải nhập đủ các trường có trên form')
            return;
        }

        if(isNaN($scope.voucher.max_price)){
            toastr.error('Giá trị tối đa phải là số')
            return;
        }

        if(isNaN($scope.voucher.min_price)){
            toastr.error('Giá trị đơn tối thiểu phải là số')
            return;
        }

        if(isNaN($scope.voucher.percent)){
            toastr.error('Giá trị phần trên phải là số')
            return;
        }

        if(isNaN($scope.voucher.quantity)){
            toastr.error('Số lần phải là số')
            return;
        }

        if($scope.voucher.max_price < 0){
            toastr.error('Giá trị tối đa phải là số duơng')
            return;
        }

        if($scope.voucher.min_price < 0){
            toastr.error('Giá trị đơn tối thiểu phải là số duơng')
            return;
        }

        if($scope.voucher.percent < 0){
            toastr.error('Giá trị phần trăm phải là số duơng')
            return;
        }

        if($scope.voucher.percent >= 100){
            toastr.error('Giá trị phần trăm phải nhỏ hon 100%')
            return;
        }

        if($scope.voucher.quantity < 0){
            toastr.error('Số lượng phải là số duơng')
            return;
        }

        if($scope.voucher.date_start > $scope.voucher.date_end){
            toastr.error('Ngày bắt đầu phải nhỏ hơn ngày kết thúc')
            return; 
        }

        if($scope.voucher.date_start < today){
            toastr.error('Ngày bắt đầu phải lớn hơn ngày hôm nay')
            return;
        }
        
        var data = {
            ma : '',
            ten : $scope.voucher.name,
            phanTramGiam : $scope.voucher.percent,
            giaTriToiThieu : $scope.voucher.min_price,
            giaTriToiDa : $scope.voucher.max_price,
            soLanDung : $scope.voucher.quantity,
            ngayBatDau : $scope.voucher.date_start,
            ngayKetThuc : $scope.voucher.date_end
        }
        axios.post('http://localhost:8080/voucher/add-voucher', data).then(
            (response) => {
                toastr.success('Bạn đã tạo voucher thành công')
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