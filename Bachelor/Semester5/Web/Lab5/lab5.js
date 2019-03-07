$("#btn").click(function () {
    $("#grayWrap").show();
    $("#modal").show();

    $("#main :input").prop('disabled', true);
    /* $("#firstName").prop('disabled', true);
    $("#lastName").prop('disabled', true);
    $("#email").prop('disabled', true);
    $("#city").prop('disabled', true);
    $("#etc").prop('disabled', true); */
    $("#btn").prop('disabled', true);

    return false;
});

$("#modalBtn").click(function () {


    $("#main :input").prop('disabled', false);
    /* $("#firstName").prop('disabled', false);
    $("#lastName").prop('disabled', false);
    $("#email").prop('disabled', false);
    $("#city").prop('disabled', false);
    $("#etc").prop('disabled', false); */
    $("#btn").prop('disabled', false);

    var ourText = "";
    $("#modal :input").each(function () {
        ourText = ourText + $(this).val() + " ";
        $(this).val("");
    });

    if (ourText.length>4 && ourText[0]!=" ") {
        $("#ourText").text(ourText);
        $("#grayWrap").hide();
        $("#modal").hide();
    } else
        alert("Fields are empty!");

    return false;
});