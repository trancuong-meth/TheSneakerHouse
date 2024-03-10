main_app.controller("billController", function ($scope, $http) {

    $scope.bills = {}
  
    $scope.loadBills = function () {
      $http.get('http://localhost:8080/bill/get-bill-by-state/' + 0).then(function (response) {
        $scope.bills = response.data
        console.log($scope.bills)
      }).catch(function (error) {
          console.log(error)
      })
    }
  
  })