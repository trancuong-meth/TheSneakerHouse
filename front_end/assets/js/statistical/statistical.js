main_app.controller("statisticalController", function ($scope, $http) {

  $scope.revenueMonth = {};
  $scope.revenueDay = {};
  $scope.quantityProduct = 0;

  // chart bar
  $scope.labels = []
  $scope.datas = []
  $scope.top5Product = []

  // pie bar
  $scope.labelsPie = []
  $scope.dataPie = []

  $scope.loadBills = function () {
    $http.get('http://localhost:8080/bill/get-revenue-month').then(function (response) {
      $scope.revenueMonth = response.data;
      console.log($scope.revenueMonth)
    }).catch(function (error) {
      console.log(error)
    })

    $http.get('http://localhost:8080/bill/get-revenue-day').then(function (response) {
      $scope.revenueDay = response.data;
    }).catch(function (error) {
      console.log(error)
    })

    $http.get('http://localhost:8080/bill/get-quantity-of-product-with-month').then(function (response) {
      $scope.quantityProduct = response.data;
    }).catch(function (error) {
      console.log(error)
    })

    $http.get('http://localhost:8080/bill/get-best-seller').then(function (response) {
      $scope.top5Product = response.data;
      console.log($scope.top5Product)
    }).catch(function (error) {
      console.log(error)
    })

    $http.get('http://localhost:8080/bill/get-bill-state').then(function (response) {
      for (var i = 0; i < response.data.length; i++) {
        if (response.data[i].trangThai == 1){
          $scope.labelsPie.push("Tạo đơn hàng")
      }else if(response.data[i].trangThai == 2){
          $scope.labelsPie.push("Chờ giao")
      }else if(response.data[i].trangThai == 3){
          $scope.labelsPie.push("Đang giao")
      }else if(response.data[i].trangThai == 4){
          $scope.labelsPie.push("Thành công")
      }
      $scope.dataPie.push(response.data[i].soLuong)
    }
    }).catch(function (error) {
      console.log(error)
    })

    setTimeout(function () {
      $scope.loadPieChart()
    }, 200)

  }

$scope.loadBills()

$scope.formatToVND = function (amount) {
  const formatter = new Intl.NumberFormat("vi-VN", {
    style: "currency",
    currency: "VND",
    minimumFractionDigits: 0, // Set to 0 to display whole numbers
  });
  return formatter.format(amount);
}

$scope.getFormattedDate = (date) => {
  date = new Date(date)
  var year = date.getFullYear();

  var month = (1 + date.getMonth()).toString();
  month = month.length > 1 ? month : '0' + month;

  var day = date.getDate().toString();
  day = day.length > 1 ? day : '0' + day;

  return day + '/' + month + '/' + year;
}

$scope.loadChart = function () {
  console.log($scope.labels)

  let chartStatus = Chart.getChart("myChart"); // <canvas> id
  if (chartStatus != undefined) {
    chartStatus.destroy();
  }
  //-- End of chart destroy   

  var chartCanvas = $('#myChart'); //<canvas> id
  chartInstance = new Chart(chartCanvas, {
    type: 'bar',
    data: {
      labels: $scope.labels,
      datasets: [{
        label: 'hóa đơn',
        data: $scope.datas,
        borderWidth: 1
      }]
    },
    options: {
      scales: {
        y: {
          beginAtZero: true
        }
      }
    }
  });
}

$scope.loadPieChart = function () {

  let pieStatus = Chart.getChart("pieChart"); // <canvas> id
  if (pieStatus != undefined) {
    pieStatus.destroy();
  }
  //-- End of chart destroy   

  var pieCanvas = $('#pieChart'); //<canvas> id
  chartInstance = new Chart(pieCanvas, {
    type: 'pie',
    data: {
      labels: $scope.labelsPie,
      datasets: [{
        label: 'đơn hàng',
        data: $scope.dataPie,
        backgroundColor: [
          'rgb(255, 99, 132)',
          'rgb(54, 162, 235)',
          'rgb(255, 205, 86)',
          'rgb(53, 205, 86)'
        ],
        hoverOffset: 4
      }]
    }
  });
}

$scope.onLoadChart = function (startDate, endDate) {
  $http.get('http://localhost:8080/bill/get-revenue-range-date?startDate=' + startDate + '&endDate=' + endDate).then(function (response) {
    for (var i = 0; i < response.data.length; i++) {
      $scope.labels.push($scope.getFormattedDate(response.data[i].ngay))
      $scope.datas.push(response.data[i].soLuong)
    }

    $scope.loadChart()
  }).catch(function (error) {
    console.log(error)
  })
}

$("input[name='dates']").daterangepicker(
  {
    locale: {
      format: "DD/MM/YYYY",
      applyLabel: "Tìm kiếm",
      cancelLabel: "Hủy bỏ",
    }
  },
  function (start, end, label) {
    let startDate = start.format("YYYY-MM-DD").toString();
    let endDate = end.format("YYYY-MM-DD").toString();
    $scope.onLoadChart(startDate, endDate)

  }
);

$scope.onLoadChart('2024-01-01', '2024-04-10')

})
