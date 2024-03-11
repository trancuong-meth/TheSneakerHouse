main_app.controller("billController", function ($scope, $http) {

    $scope.bills = []
    $scope.currentPage = 1;
    $scope.itemsPerPage = 10;
    $scope.totalItems = 1;
  
    $scope.loadBills = function (state) {
      $http.get('http://localhost:8080/bill/get-bill-panigation?page=' + ($scope.currentPage - 1) + '&size=' + $scope.itemsPerPage + '&state=' + state).then(function (response) {
        $scope.bills = response.data
        console.log($scope.bills)
      }).catch(function (error) {
          console.log(error)
      })
    }

    $scope.loadBills(-1)

    $scope.formatToVND = function (amount) {
      const formatter = new Intl.NumberFormat("vi-VN", {
          style: "currency",
          currency: "VND",
          minimumFractionDigits: 0, // Set to 0 to display whole numbers
      });
      return formatter.format(amount);
  }
  
  })