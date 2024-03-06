
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
        'idThuongHieu': '',
        'idTheLoai': ''
    }

    // colors
    $scope.colors = [
        { 'id': 1, 'ten': 'Đen' },
        { 'id': 2, 'ten': 'Trắng' },
        { 'id': 3, 'ten': 'ghi' }]
    $scope.name_color = ''

    // sizes
    $scope.sizes = []
    $scope.newSize = ''

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
                $scope.brands = response.data
            });

        $http.get('http://localhost:8080/type/get-all')
            .then(function (response) {
                $scope.types = response.data
            });

        $http.get('http://localhost:8080/color/get-all')
            .then(function (response) {
                $scope.colors = response.data
            });

        $http.get('http://localhost:8080/size/get-all')
            .then(function (response) {
                $scope.sizes = response.data
            });

    }

    $scope.loadData()

    $scope.resetProductDetails = function () {
        $scope.productDetails.splice(0, $scope.productDetails.length);
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
        toastr.success(`Bạn đã thêm kích cỡ thành công  !!!`);
        $scope.addProductDetail()
    }

    $scope.chooseColorAddProduct = function () {
        var e = document.getElementById("colorListProductModal")
        var modal = bootstrap.Modal.getOrCreateInstance(e)
        $scope.listChooseColor.splice(0, $scope.listChooseColor.length);
        modal.hide()
        toastr.success(`Bạn đã thêm màu sắc thành công  !!!`);

        for (var i = 0; i < $scope.listChooseColorId.length; i++) {
            var result = $scope.colors.find(x => x.id == $scope.listChooseColorId[i])
            if (result) {
                $scope.listChooseColor.push(result)
            }
        }
        
        $scope.addProductDetail()
    }

    $scope.removeChooseSize = function (size) {
        for (var i = $scope.listChooseSize.length; i--;) {
            if ($scope.listChooseSize[i] === size)
                $scope.listChooseSize.splice(i, 1);
        }
        $scope.addProductDetail()
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
        $scope.addProductDetail()
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
        var productDetailHtml = document.getElementById("color-size-" + colorId + "-" + size)
        var productDetailTabColor = document.getElementById("modal-product-detail-color-" + colorId)

        productDetailHtml.remove()
        console.log(colorId)
        for(var i = 0 ; i < $scope.productDetails.length; i++){
            if($scope.productDetails[i].color.id == colorId && $scope.productDetails[i].size == size){
                $scope.productDetails.splice(i, 1)
            }
        }
        var productByColor = $scope.productDetails.find(x => x.color.id == colorId)
        var productBySize = $scope.productDetails.find(x => x.size == size)

        if(productByColor === undefined){
            productDetailTabColor.remove()
            $scope.removeChooseColor($scope.colors.find(x => x.id == colorId))
        }

        if(productBySize === undefined){
            $scope.removeChooseSize(size)
        }

    }

    $scope.addProductDetail = function () {
        $scope.resetProductDetails()

        if($scope.listChooseColor.length > 0 && $scope.listChooseSize.length > 0){
            for(var i = 0; i < $scope.listChooseColor.length; i++){
                for(var j = 0; j < $scope.listChooseSize.length; j++){
                    $scope.productDetails.push({
                        'color': $scope.listChooseColor[i],
                        'size': $scope.listChooseSize[j]
                    })
                }
            }
            console.log($scope.productDetails)
        }

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

    $scope.addColorAddProduct = function () {
        var colorModal = document.querySelector("#colorAddProductModal")
        var addModal = bootstrap.Modal.getOrCreateInstance(colorModal)

        var listColorHtml = document.querySelector("#colorListProductModal")
        var listColorModal = bootstrap.Modal.getOrCreateInstance(listColorHtml)

        if ($scope.name_color == "") {
            toastr.error("Bạn phải nhập tên màu sắc !!!");
        } else {
            axios.post('http://localhost:8080/color/add?name=' + $scope.name_color
            ).then(function (response) {
                toastr.success("Bạn đã tạo màu sắc thành công !!!");
                addModal.hide()
                $scope.name_color = ""
                listColorModal.show()
                $scope.loadData()
            }).catch(function (error) {
                toastr.error("Tên màu sắc này đã tồn tại.Vui lòng nhập tên màu sắc khác!!!");
            })
        }
    }

    $scope.addSizeAddProduct = function () {
        var sizeModal = document.querySelector("#sizeAddProductModal")
        var addModal = bootstrap.Modal.getOrCreateInstance(sizeModal)

        var listSizeHtml = document.querySelector("#sizeListProductModal")
        var listSizeModal = bootstrap.Modal.getOrCreateInstance(listSizeHtml)

        if ($scope.newSize == "") {
            toastr.error("Bạn phải nhập kích cỡ giày !!!");
            return;
        }

        if (isNaN($scope.newSize)) {
            toastr.error("Kích cỡ phải là số !!!");
            return;
        }

        if (Number($scope.newSize) < 0) {
            toastr.error("Kích cỡ phải là số nguyên dương !!!");
            return;
        }

        axios.post('http://localhost:8080/size/add?size=' + $scope.newSize
        ).then(function (response) {
            toastr.success("Bạn đã tạo kích cỡ thành công !!!");
            addModal.hide()
            $scope.newSize = ""
            listSizeModal.show()
            $scope.loadData()
        }).catch(function (error) {
            console.log(error)
            toastr.error("Kích cỡ này đã tồn tại.Vui lòng nhập tên thương hiệu khác!!!");
        })
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

        // if (element.files && element.files[0]) {
        //     const formData = new FormData();
        //     formData.append('file', element.files[0]);
        //     file = formData
        //     console.log(formData)
        // }

        // readURL(element)
        // var icon = document.getElementsByClassName('icon-upload-button')[0]
        // icon.classList.add('icon-upload-button-close')

        console.log($scope.product)
    }

    $scope.addProduct = function () {

        if($scope.product.ten === ""){
            toastr.error("Bạn phải nhập tên sản phẩm.")
            return;
        }

        if($scope.product.idThuongHieu === ""){
            toastr.error("Bạn phải chọn thương hiệu.")
            return;
        }

        if($scope.product.idTheLoai === ""){
            toastr.error("Bạn phải chọn thể loại.")
            return;
        }

        if($scope.product.moTa === ""){
            toastr.error("Bạn phải nhập mô tả.")
            return;
        }

        if($scope.listChooseColor.length === 0 || $scope.listChooseColorId.length === 0){
            toastr.error("Bạn phải chọn màu sắc.")
            return;
        }

        if($scope.listChooseSize.length === 0 ){
            toastr.error("Bạn phải chọn kích cỡ.")
            return;
        }
    }

})
