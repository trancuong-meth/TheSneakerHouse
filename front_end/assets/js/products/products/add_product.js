var data = ["Apple", "Banana", "Cherry", "Date", "ElderberriesElderberry"]; // Programatically-generated options array with > 5 options
var placeholder = "select";
$("#choices-multiple-size").select2({
    data: data,
    placeholder: placeholder,
    allowClear: false,
    minimumResultsForSearch: 5
});