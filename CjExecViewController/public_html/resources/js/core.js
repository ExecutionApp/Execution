$(function () {

    $("#cb1").click(function () {
        if ($("#inputText1 input").val() == "") {
            $(".login-alert").removeClass("login-alert-hide");
        }
        if ($("#inputText2 input").val() == "") {
            $(".login-alert").removeClass("login-alert-hide");
        }
    });

    $("#inputText1 input").change(function () {
        if ($("#inputText1 input").val() != "" && $("#inputText2 input").val() != "") {
            $(".login-alert").addClass("login-alert-hide");
        }
    });

    $("#inputText2 input").change(function () {
        if ($("#inputText1 input").val() != "" && $("#inputText2 input").val() != "") {
            $(".login-alert").addClass("login-alert-hide");
        }
    });

    $(window).bind('scroll', function () {
        if ($(window).scrollTop() > 160) {
            $('.MenuPanel').addClass('fixedMenu');
            //$('.apply_btn').addClass('fixedPro_btn');
            $('.apply_gl_btn, .apply_btn').addClass('fixedGl_btn');
            $('.back_gl_btn').addClass('fixedGl_back_btn');
            $('.delete_gl_btn').addClass('fixedGl_delete_btn');
        }
        else {
            $('.MenuPanel').removeClass('fixedMenu');
            $('.apply_gl_btn, .apply_btn').removeClass('fixedGl_btn');
            $('.back_gl_btn').removeClass('fixedGl_back_btn');
            $('.delete_gl_btn').removeClass('fixedGl_delete_btn');
        }
    });

});

function showErrorMsg(selector) {
    $(selector).removeClass("login-alert-hide");
}