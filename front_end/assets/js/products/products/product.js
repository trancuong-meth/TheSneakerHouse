main_app.controller("productController", function ($scope, $http) {

    $scope.currentPage = 1;
    $scope.itemsPerPage = 10;
    $scope.totalItems = 1;
    $scope.keyProduct = ""
    $scope.trang_thai = ""
    $scope.products = []

    //brands
    $scope.brands = []
    $scope.idBrand = ""

    //types
    $scope.types = []
    $scope.idType = ""

    const loadData = function () {
        $http.get('http://localhost:8080/product/find-all-panigation?page=' + ($scope.currentPage - 1) + '&size=' + $scope.itemsPerPage + '&key=' + '&id_type=' + '&id_brand=' + '&trang_thai=',)
            .then(function (response) {
                $scope.products = response.data
                console.log($scope.products)
                $scope.totalItems = response.data.totalElements
            });

        $http.get('http://localhost:8080/brand/get-all')
            .then(function (response) {
                $scope.brands = response.data
            });

        $http.get('http://localhost:8080/type/get-all')
            .then(function (response) {
                $scope.types = response.data
            });
    }

    const fillter = function (keyProduct, trang_thai) {
        $http.get('http://localhost:8080/product/find-all-panigation?page=' + ($scope.currentPage - 1) + '&size=' + $scope.itemsPerPage + '&key=' + keyProduct + '&id_type=' + $scope.idType + '&id_brand=' + $scope.idBrand + '&trang_thai=' + trang_thai,)
            .then(function (response) {
                $scope.products = response.data
                $scope.totalItems = response.data.totalElements
            });
    }

    loadData()

    $scope.pageChanged = function () {
        $http.get('http://localhost:8080/product/find-all-panigation?page=' + ($scope.currentPage - 1) + '&size=' + $scope.itemsPerPage + '&key=' + $scope.keyProduct + '&id_type=' + $scope.idType + '&id_brand=' + $scope.idBrand + '&trang_thai=' + $scope.trang_thai,)
            .then(function (response) {
                $scope.products = response.data
            });
    };

    $scope.fillterByProductKey = function () {
        var key = document.getElementById("searchKey").value
        fillter(key, $scope.trang_thai)
    }

    $scope.loadproduct = function (id) {
        var productUpdateModal = document.querySelector("#productUpdateModal")
        var modal = bootstrap.Modal.getOrCreateInstance(productUpdateModal)

        axios.get('http://localhost:8080/product/get-product/' + id).then(function (response) {
            $scope.product = response.data
            console.log($scope.product)
            document.querySelector("#nameUpdateproduct").value = response.data.ten
            modal.show()
        }).catch(function (error) {
            console.log(error)
            toastr.error("Đã có lỗi trong quá trình tải.Vui lòng liên hệ admin !!!");
        })
    }

    $scope.updateproduct = function () {
        var productUpdateModal = document.querySelector("#productUpdateModal")
        var modal = bootstrap.Modal.getOrCreateInstance(productUpdateModal)

        console.log($scope.product)
        axios.put('http://localhost:8080/product/update', $scope.product)
            .then(function (response) {
                modal.hide()
                toastr.success("Bạn đã thay đổi thông tin thành công !!!");
                loadData()
            }).catch(function (error) {
                console.log(error)
                toastr.error("Tên đã tồn tại trong hệ thống !!!");
            })
    }

    $scope.reset = function () {
        $scope.name_product = ""
    }

    $scope.searchProductDetail = () => {
        var key = document.getElementById("searchKey").value
        fillter(key, $scope.trang_thai)
    }

})