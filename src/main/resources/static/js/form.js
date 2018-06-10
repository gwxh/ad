$(function () {
    $("#passwordForm").validate({
        rules: {
            oldPassword: {
                required: true,
                minlength: 6
            },
            newPassword1: {
                required: true,
                minlength: 6
            },
            newPassword2: {
                required: true,
                minlength: 6,
                equalTo: "#newPassword1"
            }
        }
    });

    $("#planForm").validate({
        rules: {
            name: {
                required: true
            },
            unitPrice: {
                required: true,
                number: true,
                min: 0.2
            },
            totalPrice: {
                required: true,
                number: true
            }
        }, submitHandler: function (form) {
            var device = "";
            $.each($("input[name=device]:checked"), function () {
                device += $(this).val() + ",";
            });
            $("#device").val(device);
            var area = "";
            $.each($("input[name=area]:checked"), function () {
                area += $(this).val() + ",";
            });
            $("#area").val(area);
            from.submit();
        }
    });
});