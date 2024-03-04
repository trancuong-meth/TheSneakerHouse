
main_app.controller("addProductController", function ($scope, $http) {

    // modal
    $scope.listChooseSizeModal = []
    $scope.listChooseColorId = []

    // list
    $scope.listChooseColor = []
    $scope.listChooseSize = []

    // product
    $scope.product = {
        'ten' : '',
        'moTa' : '',
        'thuongHieu' : '',
        'theLoai' : ''
    }

    $scope.colors = [
        { 'id': 1, 'name': 'Đen' }, 
        { 'id': 2, 'name': 'Trắng' }, 
        { 'id': 3, 'name': 'ghi' }]

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
        console.log($scope.listChooseColorId)

        for (var i = 0; i < $scope.listChooseColorId.length; i++) {
            console.log(i)
            var result = $scope.colors.find(x => x.id == $scope.listChooseColorId[i])
            console.log(result)
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

            if ($scope.listChooseColor[i].id === color.id){
                $scope.listChooseColor.splice(i, 1);
            }

            if ($scope.listChooseColorId[i] === color.id){
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

})
