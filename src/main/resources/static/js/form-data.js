$(function () {
    var device = JSON.parse($("#device").val());
    for (var i = 0; i < device.length; i++){
        $("input[name=device][value=" + device[i] + "]").attr("checked", "checked");
    }
    var area = JSON.parse($("#area").val());
    for (var i = 0; i < area.length; i++){
        $("input[name=area][value=" + area[i] + "]").attr("checked", "checked");
    }
});