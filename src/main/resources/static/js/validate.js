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
});