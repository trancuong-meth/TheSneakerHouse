
main_app.controller("addProductController", function ($scope, $http) {

    // modal
    $scope.listChooseSizeModal = []
    $scope.listChooseColorId = []

    // list
    $scope.listChooseColor = []
    $scope.listChooseSize = []

    // product
    $scope.product = {
        'ten': '',
        'moTa': '',
        'thuongHieu': '',
        'theLoai': ''
    }

    // colors
    $scope.colors = [
        { 'id': 1, 'ten': 'Đen' },
        { 'id': 2, 'ten': 'Trắng' },
        { 'id': 3, 'ten': 'ghi' }]

    // sizes
    $scope.sizes = []

    // brandsF
    $scope.brands = []
    $scope.name_brand = ""

    // types
    $scope.types = []
    $scope.name_type = ""

    // product details
    $scope.productDetails = []

    // load data
    $scope.loadData = function () {
        $http.get('http://localhost:8080/brand/get-all')
            .then(function (response) {
                console.log(response.data)
                $scope.brands = response.data
            });

        $http.get('http://localhost:8080/type/get-all')
            .then(function (response) {
                console.log(response.data)
                $scope.types = response.data
            });

        $http.get('http://localhost:8080/color/get-all')
            .then(function (response) {
                console.log(response.data)
                $scope.colors = response.data
            });

        $http.get('http://localhost:8080/size/get-all')
            .then(function (response) {
                console.log(response.data)
                $scope.sizes = response.data
            });

    }

    $scope.loadData()

    $scope.resetProductDetails = function () {
        $scope.productDetails = []
    }

    $scope.chooseSize = function (size) {

        var html = document.getElementById("size-" + size)

        if (html.classList.contains('btn-primary')) {
            html.classList.remove('btn-primary')
            html.classList.add('btn-outline-primary')
            for (var i = $scope.listChooseSizeModal.length; i--;) {
                if ($scope.listChooseSizeModal[i] === size)
                    $scope.listChooseSizeModal.splice(i, 1);
            }
        } else {
            html.classList.add('btn-primary')
            html.classList.remove('btn-outline-primary')
            $scope.listChooseSizeModal.push(size)
        }
    }

    $scope.chooseColor = function (color) {

        var html = document.getElementById("color-" + color);

        if (html.classList.contains('btn-primary')) {
            html.classList.remove('btn-primary')
            html.classList.add('btn-outline-primary')
            for (var i = $scope.listChooseColorId.length; i--;) {
                if ($scope.listChooseColorId[i] === color)
                    $scope.listChooseColorId.splice(i, 1);
            }
        } else {
            html.classList.add('btn-primary')
            html.classList.remove('btn-outline-primary')
            $scope.listChooseColorId.push(color)
        }
    }

    $scope.chooseSizeAddProduct = function () {
        var e = document.getElementById("sizeListProductModal")
        var modal = bootstrap.Modal.getOrCreateInstance(e)
        $scope.listChooseSize = $scope.listChooseSizeModal
        modal.hide()
    }

    $scope.chooseColorAddProduct = function () {
        var e = document.getElementById("colorListProductModal")
        var modal = bootstrap.Modal.getOrCreateInstance(e)
        modal.hide()

        for (var i = 0; i < $scope.listChooseColorId.length; i++) {
            var result = $scope.colors.find(x => x.id == $scope.listChooseColorId[i])
            if (result) {
                $scope.listChooseColor.push(result)
            }
        }

    }

    $scope.removeChooseSize = function (size) {
        for (var i = $scope.listChooseSize.length; i--;) {
            if ($scope.listChooseSize[i] === size)
                $scope.listChooseSize.splice(i, 1);
        }
    }

    $scope.removeChooseColor = function (color) {
        for (var i = $scope.listChooseColor.length; i--;) {

            if ($scope.listChooseColor[i].id === color.id) {
                $scope.listChooseColor.splice(i, 1);
            }

            if ($scope.listChooseColorId[i] === color.id) {
                $scope.listChooseColorId.splice(i, 1);
            }
        }

    }

    $scope.buttonChooseSize = function () {
        var sizes = document.getElementsByClassName('btn-list-size')

        for (var i = 0; i < sizes.length; i++) {
            var html = sizes[i]

            html.classList.remove('btn-primary')
            html.classList.add('btn-outline-primary')
        }

        for (var i = 0; i < $scope.listChooseSize.length; i++) {
            var size = $scope.listChooseSize[i]
            var html = document.getElementById("size-" + size)

            html.classList.add('btn-primary')
            html.classList.remove('btn-outline-primary')
        }
    }

    $scope.buttonChooseColor = function () {
        var colors = document.getElementsByClassName('btn-list-color')

        for (var i = 0; i < colors.length; i++) {
            var html = colors[i]

            html.classList.remove('btn-primary')
            html.classList.add('btn-outline-primary')
        }

        for (var i = 0; i < $scope.listChooseColor.length; i++) {
            var color = $scope.listChooseColor[i].id
            var html = document.getElementById("color-" + color)

            html.classList.add('btn-primary')
            html.classList.remove('btn-outline-primary')
        }
    }

    $scope.removeChooseByColorAndSize = function (colorId, size) {
        console.log(colorId + "-" + size)
        var productDetailHtml = document.getElementById("color-size-" + colorId + "-" + size)

        productDetailHtml.remove()
    }

    $scope.addProduct = function () {

    }

    $scope.addProductDetail = function () {
        $scope.resetProductDetails()

        // add list of product detail

    }

    $scope.addBrandAddProduct = function () {
        var brandModal = document.querySelector("#brandAddProductModal")
        var addModal = bootstrap.Modal.getOrCreateInstance(brandModal)

        if ($scope.name_brand == "") {
            toastr.error("Bạn phải nhập tên thương hiệu !!!");
        } else {
            axios.post('http://localhost:8080/brand/add?name=' + $scope.name_brand
            ).then(function (response) {
                toastr.success("Bạn đã tạo thương hiệu thành công !!!");
                addModal.hide()
                $scope.loadData()
                $scope.name_brand = ""
            }).catch(function (error) {
                console.log(error)
                toastr.error("Tên thương hiệu này đã tồn tại.Vui lòng nhập tên thương hiệu khác!!!");
            })
        }
    }

    $scope.addTypeAddProduct = function () {
        var typeModal = document.querySelector("#typeAddProductModal")
        var addModal = bootstrap.Modal.getOrCreateInstance(typeModal)

        if ($scope.name_type == "") {
            toastr.error("Bạn phải nhập tên thể loại !!!");
        } else {
            axios.post('http://localhost:8080/type/add?name=' + $scope.name_type
            ).then(function (response) {
                toastr.success("Bạn đã tạo thể loại thành công !!!");
                addModal.hide()
                $scope.loadData()
                $scope.name_type = ""
            }).catch(function (error) {
                console.log(error)
                toastr.error("Tên thể loại này đã tồn tại.Vui lòng nhập tên thể loại khác!!!");
            })
        }
    }

})
