clientApp.controller('cartController',
    function ($scope, $http) {
        // id
        $scope.cartDetails = [];
        $scope.subTotal = 0;

        // customer
        $scope.customer = JSON.parse(localStorage.getItem("user"));

        $scope.loadDataProductDetail = () => {
            var id = $scope.customer == null ? -1 : $scope.customer.id
            $http.get('http://localhost:8080/cart/get-cart-detail-by-id/' + id).then(function (response) {
                $scope.cartDetails = response.data
                $scope.subTotal = 0
                for (var i = 0; i < $scope.cartDetails.length; i++) {
                    $scope.subTotal += $scope.cartDetails[i].idSanPhamChiTiet.idDotGiamGia == null ? $scope.cartDetails[i].soLuong * $scope.cartDetails[i].idSanPhamChiTiet.donGia : $scope.cartDetails[i].soLuong * (100 - $scope.cartDetails[i].idSanPhamChiTiet.idDotGiamGia.phanTramGiam) / 100 * $scope.cartDetails[i].idSanPhamChiTiet.donGia
                }
            }).catch(function (error) {
                console.log(error)
            })
        }

        $scope.loadDataProductDetail();

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

        $scope.removeCartDetail = (e) => {
            Swal.fire({
                title: "Xác nhận xóa sản phẩm này?",
                icon: "warning",
                showCancelButton: true,
                confirmButtonColor: "#3085d6",
                cancelButtonColor: "#d33",
                confirmButtonText: "Xác nhận",
                cancelButtonText: "Hủy"
            }).then((result) => {
                if (result.isConfirmed) {
                    axios.delete('http://localhost:8080/cart-detail/remove-cart-detail/' + e.id).then(function (response) {
                        toastr.success("Xóa sản phẩm thành công");
                        window.location.reload()
                    }).catch(function (error) {
                        console.log(error)
                    })
                }
            })
        }

        $scope.minusQuantity = (cartDetail) => {
            if (cartDetail.soLuong > 1) {
                cartDetail.soLuong -= 1
                $scope.addToCart(cartDetail)
            } else {
                axios.delete('http://localhost:8080/cart-detail/remove-cart-detail/' + cartDetail.id).then(function (response) {
                    window.location.reload()
                }).catch(function (error) {
                    console.log(error)
                })
            }
        }

        $scope.plusQuantity = (cartDetail) => {
            cartDetail.soLuong += 1
            $scope.addToCart(cartDetail)
        }

        $scope.addToCart = (cartDetail) => {

            $http.post('http://localhost:8080/cart/add-to-cart', {
                sanPhamChiTiet: cartDetail.idSanPhamChiTiet,
                soLuong: cartDetail.soLuong,
                idKhachHang: -1
            }).then(function (response) {
                $scope.loadDataProductDetail();
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
                toastr.success("Thay đổi giỏ hàng thành công")

            }).catch(function (error) {
                console.log(error)
            })

        }

    });