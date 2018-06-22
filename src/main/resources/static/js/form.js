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
            form.submit();
        }
    });

    $("#imageFile").change(function () {
        var filePath = $(this).val();
        $("#imageName").val(getFileName(filePath));
    });

    $("#adForm").validate({
        ignore: [],
        rules: {
            name: {
                required: true
            },
            url: {
                required: true
            },
            "param.image": {
                required: true
            }
        },
        messages: {
            "param.image": {
                required: "请上传广告图片"
            }
        },
        submitHandler: function (form) {
            form.submit();
        }
    });

    $("#createUserForm").validate({
        rules: {
            username: {
                required: true
            },
            password: {
                required: true
            },
            money: {
                required: true,
                digits: true,
                min: 1
            }
        }
    });

    $("#editUserForm").validate({
        rules: {
            id: {
                required: true
            },
            user:{
                required: true
            },
            password: {
                minlength: 6
            },
            password2: {
                minlength: 6,
                equalTo: "#password"
            },
            money: {
                required: true,
                digits: true,
                min: 1
            }
        }
    })
});

function getFileName(path) {
    var pos1 = path.lastIndexOf('/');
    var pos2 = path.lastIndexOf('\\');
    var pos = Math.max(pos1, pos2);
    if (pos < 0) {
        return path;
    }
    else {
        return path.substring(pos + 1);
    }
}