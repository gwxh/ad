$(function () {
    var deviceJson = $("#device").val();
    if (deviceJson !== "") {
        var device = JSON.parse(deviceJson);
        for (var i = 0; i < device.length; i++) {
            $("input[name=device][value=" + device[i] + "]").attr("checked", "checked");
        }
    }
    var areaJson = $("#area").val();
    if (areaJson !== "") {
        var area = JSON.parse(areaJson);
        for (var i = 0; i < area.length; i++) {
            $("input[name=area][value=" + area[i] + "]").attr("checked", "checked");
        }
    }
    var sexJson = $("#sex").val();
    if (sexJson !== "") {
        var sex = JSON.parse(sexJson);
        for (var i = 0; i < sex.length; i++) {
            $("input[name=sex][value=" + sex[i] + "]").attr("checked", "checked");
        }
    }
    var ageJson = $("#age").val();
    if (ageJson !== "") {
        var age = JSON.parse(ageJson);
        for (var i = 0; i < age.length; i++) {
            $("input[name=age][value=" + age[i] + "]").attr("checked", "checked");
        }
    }
    var incomeJson = $("#income").val();
    if (incomeJson !== "") {
        var income = JSON.parse(incomeJson);
        for (var i = 0; i < income.length; i++) {
            $("input[name=income][value=" + income[i] + "]").attr("checked", "checked");
        }
    }
    var consumptionJson = $("#consumption").val();
    if (consumptionJson !== "") {
        var consumption = JSON.parse(consumptionJson);
        for (var i = 0; i < consumption.length; i++) {
            $("input[name=consumption][value=" + consumption[i] + "]").attr("checked", "checked");
        }
    }
    var carJson = $("#car").val();
    if (carJson !== "") {
        var car = JSON.parse(carJson);
        for (var i = 0; i < car.length; i++) {
            $("input[name=car][value=" + car[i] + "]").attr("checked", "checked");
        }
    }
    var marryJson = $("#marry").val();
    if (marryJson !== "") {
        var marry = JSON.parse(marryJson);
        for (var i = 0; i < marry.length; i++) {
            $("input[name=marry][value=" + marry[i] + "]").attr("checked", "checked");
        }
    }
    var childJson = $("#child").val();
    if (childJson !== "") {
        var child = JSON.parse(childJson);
        for (var i = 0; i < child.length; i++) {
            $("input[name=child][value=" + child[i] + "]").attr("checked", "checked");
        }
    }
    var qualityJson = $("#quality").val();
    if (qualityJson !== "") {
        var quality = JSON.parse(qualityJson);
        for (var i = 0; i < quality.length; i++) {
            $("input[name=quality][value=" + quality[i] + "]").attr("checked", "checked");
        }
    }

    $("#areaCheckAll").click(function () {
        if (this.checked) {
            $("[name=area]").attr("checked", true);
        } else {
            $("[name=area]").attr("checked", false);
        }
    });
    $("#deviceCheckAll").click(function () {
        if (this.checked) {
            $("[name=device]").attr("checked", true);
        } else {
            $("[name=device]").attr("checked", false);
        }
    });
    $("#sexCheckAll").click(function () {
        if (this.checked) {
            $("[name=sex]").attr("checked", true);
        } else {
            $("[name=sex]").attr("checked", false);
        }
    });
    $("#ageCheckAll").click(function () {
        if (this.checked) {
            $("[name=age]").attr("checked", true);
        } else {
            $("[name=age]").attr("checked", false);
        }
    });
    $("#incomeCheckAll").click(function () {
        if (this.checked) {
            $("[name=income]").attr("checked", true);
        } else {
            $("[name=income]").attr("checked", false);
        }
    });
    $("#consumptionCheckAll").click(function () {
        if (this.checked) {
            $("[name=consumption]").attr("checked", true);
        } else {
            $("[name=consumption]").attr("checked", false);
        }
    });
    $("#carCheckAll").click(function () {
        if (this.checked) {
            $("[name=car]").attr("checked", true);
        } else {
            $("[name=car]").attr("checked", false);
        }
    });
    $("#marryCheckAll").click(function () {
        if (this.checked) {
            $("[name=marry]").attr("checked", true);
        } else {
            $("[name=marry]").attr("checked", false);
        }
    });
    $("#childCheckAll").click(function () {
        if (this.checked) {
            $("[name=child]").attr("checked", true);
        } else {
            $("[name=child]").attr("checked", false);
        }
    });
    $("#qualityCheckAll").click(function () {
        if (this.checked) {
            $("[name=quality]").attr("checked", true);
        } else {
            $("[name=quality]").attr("checked", false);
        }
    });

    for (var i = 1; i < 26; i++) {
        var mJson = $("#m" + i).val();
        if (mJson !== "") {
            var m = JSON.parse(mJson);
            for (var j = 0; j < m.length; j++) {
                $("input[name=m" + i + "][value=" + m[j] + "]").attr("checked", "checked");
            }
        }
        $("#m" + i + "CheckAll").click(function () {
            var value = $(this).val();
            if (this.checked) {
                $("[name=m" + value + "]").attr("checked", true);
            } else {
                $("[name=m" + value + "]").attr("checked", false);
            }
        });
    }

    for (var i = 1; i < 8; i++) {
        for (var j = 0; j <= 23; j++) {
            $("#day" + i + " .hour").append('<label class="checkbox-inline"><input type="checkbox" name="d' + i + '" value="' + j + '">' + j+'</label>');
        }

        var dJson = $("#d" + i).val();
        if (dJson !== "") {
            var d = JSON.parse(dJson);
            for (var j = 0; j < d.length; j++) {
                $("input[name=d" + i + "][value=" + d[j] + "]").attr("checked", "checked");
            }
        }
        $("#d" + i + "CheckAll").click(function () {
            var value = $(this).val();
            if (this.checked) {
                $("[name=d" + value + "]").attr("checked", true);
            } else {
                $("[name=d" + value + "]").attr("checked", false);
            }
        });
    }
});