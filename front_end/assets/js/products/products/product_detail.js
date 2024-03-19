main_app.controller("productDetailController", function ($scope, $http, $routeParams) {

    $scope.currentPageProductDetail = 1;
    $scope.itemsPerPageProductDetail = 10;
    var idProduct = $routeParams.id
    $scope.totalItemsProductDetail = 1;
    $scope.trang_thai = ""
    $scope.productDetails = []

    // color and size
    $scope.colors = []
    $scope.sizes = []

    // filter
    $scope.idSize = ""
    $scope.idColor = ""
    $scope.maxPrice = 0;
    $scope.minPrice = 0;
    $scope.valuePrice = 0;

    //product detail
    $scope.product_detail = {}

    //images
    $scope.images = []

    // load data
    $scope.idColorProductDetail = ""
    $scope.idSizeProductDetail = ""

    const loadData = function () {
        $http.get('http://localhost:8080/product-detail/find-all-panigation?page=' + ($scope.currentPageProductDetail - 1) + '&size=' + $scope.itemsPerPageProductDetail + '&trang_thai=' + '&idSize=' + '&idColor=' + '&id=' + idProduct,)
            .then(function (response) {
                $scope.productDetails = response.data
                console.log($scope.productDetails)
                $scope.totalItemsProductDetail = response.data.totalElements

            });

        $http.get('http://localhost:8080/color/get-all')
            .then(function (response) {
                $scope.colors = response.data
            });

        $http.get('http://localhost:8080/size/get-all')
            .then(function (response) {
                $scope.sizes = response.data
            });

        $http.get('http://localhost:8080/product/get-max-don-gia')
            .then(function (response) {
                $scope.maxPrice = response.data
            });

    }

    const fillter = function (trang_thai) {
        $http.get('http://localhost:8080/product-detail/find-all-panigation?page=' + ($scope.currentPageProductDetail - 1) + '&size=' + $scope.itemsPerPageProductDetail + '&trang_thai=' + trang_thai + '&id=' + idProduct + '&idSize=' + $scope.idSize + '&idColor=' + $scope.idColor,)
            .then(function (response) {
                $scope.productDetails = response.data
                $scope.totalItemsProductDetail = response.data.totalElements
            });
    }

    loadData()

    $scope.pageChangedProductDetail = function () {

        $http.get('http://localhost:8080/product-detail/find-all-panigation?page=' + ($scope.currentPageProductDetail - 1) + '&size=' + $scope.itemsPerPageProductDetail + '&trang_thai=' + $scope.trang_thai + '&id=' + idProduct,)
            .then(function (response) {
                $scope.productDetails = response.data
            });
    };

    $scope.fillterByProductKey = function () {
        fillter($scope.trang_thai)
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
        <div id="carousel-${id}" class="carousel slide carousel-fade" data-bs-ride="carousel">
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
            for (var i = 0; i < response.data.length; i++) {
                if (i == 0) {
                    textCenter += `
                        <div class="carousel-item active "  >
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

    $scope.searchProductDetail = () => {
        fillter($scope.trang_thai)
    }

    $scope.setProductDetail = (product) => {
        $scope.product_detail = product
        $scope.idColorProductDetail = product.idMauSac.id
        $scope.idSizeProductDetail = product.idKichCo.id

        var qrcode = new QRCode(document.getElementById("qrcode"), {
            text: product.id,
            width: 128,
            height: 128
        });

        //size
        var sizeHeader = `
        <select class="form-select" style="width: 100%;" id="size" name="size" ng-change="searchProductDetail()"
                ng-model="idSizeProductDetail">
        `
        var sizeMiddler = ""
        var sizeFooter = `
        </select>
        `
        $scope.sizes.forEach(size => {
            if (size.id == $scope.idSizeProductDetail) {
                sizeMiddler += `
                <option selected value=${ size.id } >${ size.kichCo }</option>
                `
            } else {
                sizeMiddler += `
                <option value=${ size.id } >${ size.kichCo }</option>
                `
            }

        })

        document.getElementById("sizeProductDetail").innerHTML = sizeHeader + sizeMiddler + sizeFooter

        // color
        var colorHeader = `
        <select class="form-select" style="width: 100%;" id="color" name="color" ng-change="searchProductDetail()"
                ng-model="idColorProductDetail">
        `
        var colorMiddler = ""
        var colorFooter = `
        </select>
        `
        $scope.colors.forEach(color => {
            if (color.id == $scope.idColorProductDetail) {
                colorMiddler += `
                <option selected value=${ color.id } >${ color.ten }</option>
                `
            } else {
                colorMiddler += `
                <option value=${ color.id } >${ color.ten }</option>
                `
            }

        })

        document.getElementById("colorProductDetail").innerHTML = colorHeader + colorMiddler + colorFooter

        // load image
        axios.get('http://localhost:8080/image/get-all/' + product.id).then(function (response) {
            $scope.images = response.data
            loadData()
        }).catch(function (error) {
            console.log(error)
        })

    }

})