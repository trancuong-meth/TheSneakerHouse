main_app.controller("productDetailController", function ($scope, $http, $routeParams) {

    $scope.currentPage = 1;
    $scope.itemsPerPage = 10;
    var idProduct = $routeParams.id
    $scope.totalItems = 1;
    $scope.keyProduct = ""
    $scope.trang_thai = ""
    $scope.productDetails = []

    const loadData = function () {
        $http.get('http://localhost:8080/product-detail/find-all-panigation?page=' + ($scope.currentPage - 1) + '&size=' + $scope.itemsPerPage + '&key=' + '&trang_thai=' + '&id=' + idProduct,)
            .then(function (response) {
                $scope.productDetails = response.data
                console.log($scope.productDetails)
                $scope.totalItems = response.data.totalElements
            });
    }

    const fillter = function (keyProduct, trang_thai) {
        $http.get('http://localhost:8080/product-detail/find-all-panigation?page=' + ($scope.currentPage - 1) + '&size=' + $scope.itemsPerPage + '&key=' + keyProduct + '&trang_thai=' + trang_thai + '&id=' + idProduct,)
            .then(function (response) {
                $scope.productDetails = response.data
                $scope.totalItems = response.data.totalElements
            });
    }

    loadData()

    $scope.pageChanged = function () {
        $http.get('http://localhost:8080/product-detail/find-all-panigation?page=' + ($scope.currentPage - 1) + '&size=' + $scope.itemsPerPage + '&key=' + $scope.keyProduct + '&trang_thai=' + $scope.trang_thai + '&id=' + idProduct,)
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

    $scope.formatToVND = function (amount) {
        const formatter = new Intl.NumberFormat("vi-VN", {
            style: "currency",
            currency: "VND",
            minimumFractionDigits: 0, // Set to 0 to display whole numbers
        });
        return formatter.format(amount);
    }

    $scope.getAllImagesByIDProductDetail = function (id) {
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
        axios.get('http://localhost:8080/image/get-all/' + id).then(function (response) {
            console.log(response.data)
            for (var i = 0; i < response.data.length; i++) {
                if (i == 0) {
                    textCenter += `
                        <div class="carousel-item active">
                            <img src="${response.data[0].duongDan}" class="d-block w-100" alt="...">
                        </div>`
                }else{
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


})