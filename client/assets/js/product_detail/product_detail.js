clientApp.controller('singleProductController',
    function ($scope, $http, $routeParams) {
        window.scrollTo(0, 0)
        var id = $routeParams.id
        var today = new Date();
        $scope.productDetails = []
        $scope.product = {}

        $scope.customer = JSON.parse(localStorage.getItem("user"))

        // entity
        $scope.colorProductDetails = []
        $scope.productDetailChooses = []

        // current entity
        $scope.productChooseCurrent = {}

        // images current
        $scope.imageCurrents = []

        // currentPage
        $scope.currentPageProductDetail = 1;
        $scope.itemsPerPageProductDetail = 100;
        $scope.quantity_of_cart = 1;

        $scope.loadSizes = () => {
            $http.get('http://localhost:8080/size/get-all')
                .then(function (response) {
                    $scope.sizes = response.data
                });
        }

        $scope.formatToVND = function (amount) {
            const formatter = new Intl.NumberFormat("vi-VN", {
                style: "currency",
                currency: "VND",
                minimumFractionDigits: 0, // Set to 0 to display whole numbers
            });
            return formatter.format(amount);
        }


        $scope.loadData = function () {
            var colorHtml = document.getElementById("color-product-detail")
            colorHtml.innerHTML = ""

            $http.get('http://localhost:8080/product-detail/find-all-panigation?page=' + ($scope.currentPageProductDetail - 1) + '&size=' + $scope.itemsPerPageProductDetail + '&trang_thai=' + '&idSize=' + '&idColor=' + '&id=' + id,)
                .then(function (response) {
                    $scope.productDetails = response.data.content
                    console.log(response.data)
                    $scope.product = response.data.content[0].idSanPham
                    $scope.totalItemsProductDetail = response.data.totalElements
                }).catch(function (error) {
                    console.log(error)
                });

            $http.get('http://localhost:8080/product-detail/get-color-identity/' + id)
                .then(function (response) {
                    $scope.colorProductDetails = response.data
                    console.log(response.data)
                    var text = ""
                    response.data.forEach(color => {
                        text += `
                        <button data-tooltip="${color.ten}" id="color-product-detail-${color.id}" class="color-product-detail"
                                onclick="angular.element(this).scope().chooseColor(${color.id})"
                                style="background-image: url(${color.duongDan});display:inline-block;postition: relative;
                                width: 100px;height: 67px;background-size: cover;border-radius: 5px;margin-right: 10px;margin-bottom: 10px;">
                               
                                <div class="background-product">
                                  <i class="ti ti-checks icon-product"></i>    
                                </div>
                        </button>
                        `
                    });
                    colorHtml.innerHTML = text;
                    setTimeout(() => {
                        $scope.chooseColor(response.data[0].id)
                    }, 10)
                }).catch(function (error) {
                    console.log(error)
                });

            $http.get('http://localhost:8080/product-detail/get-product-newests').then(response => {
                $scope.productNewests = response.data
            }).catch(error => {
                console.log(error)
            })


        }

        $scope.getImageByProductId = function (id) {
            axios.get('http://localhost:8080/image/get-all/' + id).then(function (response) {
                console.log(response)
                return response.data[0].duongDan
            }).catch(function (error) {
                console.log(error)
            })
        }

        $scope.clearMarkColor = () => {
            var colorHtml = document.getElementById("color-product-detail").getElementsByTagName("button")

            for (e of colorHtml) {
                e.classList.remove("color-product-detail-active")
                e.classList.add("color-product-detail")
            }
        }

        $scope.clearMarkSize = () => {
            var sizeHtml = document.getElementById("size-product-detail").getElementsByTagName("button")

            for (e of sizeHtml) {
                e.classList.remove("size-product-detail-active")
                e.classList.add("size-product-detail")
            }
        }

        $scope.chooseColor = (id) => {
            // clear mark
            $scope.clearMarkColor();

            // active current choose color
            var currentColor = document.getElementById("color-product-detail-" + id)
            currentColor.classList.add("color-product-detail-active")
            currentColor.classList.remove("color-product-detail")

            $scope.productDetailChooses = $scope.productDetails.filter(productDetail => productDetail.idMauSac.id === id)
            $scope.loadSizes()

            setTimeout(() => {
                $scope.chooseSize($scope.productDetailChooses[0])
            }, 10)
        }

        $scope.chooseSize = (productDetail) => {
            $scope.clearMarkSize()

            // active current choose color
            var currentSize = document.getElementById("size-product-detail-" + productDetail.idKichCo.id)
            currentSize.classList.add("size-product-detail-active")
            currentSize.classList.remove("size-product-detail")

            $scope.productChooseCurrent = productDetail
            console.log($scope.productChooseCurrent)
            setTimeout(() => {
                $scope.getAllImagesByIDProductDetail($scope.productChooseCurrent.id)
            }, 10)
        }

        $scope.getAllImagesByIDProductDetail = function (id) {
            axios.get('http://localhost:8080/image/get-all/' + id).then(function (response) {
                $scope.imageCurrents = response.data;
                $scope.loadSizes()
            }).catch(function (error) {
                console.log(error)
            })
        }

        $scope.addToCart = () => {
            console.log($scope.productChooseCurrent)

            $http.post('http://localhost:8080/cart/add-to-cart', {
                sanPhamChiTiet: $scope.productChooseCurrent,
                soLuong: $scope.quantity_of_cart == 1 ? -1 : $scope.quantity_of_cart,
                idKhachHang: $scope.customer == null ? -1 : $scope.customer.id
            }).then(function (response) {
                toastr.options = {
                    "closeButton": false,
                    "debug": false,
                    "newestOnTop": false,
                    "progressBar": false,
                    "positionClass": "toast-bottom-right",
                    "preventDuplicates": false,
                    "onclick": null,
                    "showDuration": "300",
                    "hideDuration": "1000",
                    "timeOut": "5000",
                    "extendedTimeOut": "1000",
                    "showEasing": "swing",
                    "hideEasing": "linear",
                    "showMethod": "fadeIn",
                    "hideMethod": "fadeOut"
                }
                toastr.success("Thêm vào giỏ hàng thành công")
                setTimeout(() => {
                    loadQuantityByIdProduct(-1)
                    $scope.showMiniCart()
                }, 300)

            }).catch(function (error) {
                console.log(error)
            })

        }

        $scope.minusQuantity = () => {
            if ($scope.quantity_of_cart > 0) {
                $scope.quantity_of_cart -= 1
            } else {
                $scope.quantity_of_cart = 0
            }
        }

        $scope.plusQuantity = () => {
            $scope.quantity_of_cart += 1
        }

        $scope.getAllImagesByIDProductDetailSwiper = function (id) {
            var html = document.getElementById("image-" + id);
            axios.get('http://localhost:8080/image/get-all/' + id).then(function (response) {
                html.src = response.data[0].duongDan
            }).catch(function (error) {
                console.log(error)
            })
        }

        $scope.loadData()

    });