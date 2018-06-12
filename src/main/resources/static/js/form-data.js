$(function () {
    var device = $("#device").val();
    var deviceArray = device.split(",");
    for (var i = 0; i < deviceArray.length; i++){
        $("input[name=device][value=" + deviceArray[i] + "]").attr("checked", "checked");
    }
    var area = $("#area").val();
    var areaArray = area.split(",");
    for (var i = 0; i < areaArray.length; i++){
        $("input[name=area][value=" + areaArray[i] + "]").attr("checked", "checked");
    }
});