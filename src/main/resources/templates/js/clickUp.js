$(function () {
    var secectedFile;

    $(".addFile").on("change", function () {
        secectedFile = document.getElementById('nameFile').files[0];
        // console.log(secectedFile.name);
        return false;

    });

    $("#clickUp").on("click", function (event) {
        var documentData = new FormData();
        documentData.append('file', $('#nameFile')[0].files[0]);
        $.ajax({
            url: 'vueupload',
            type: 'POST',
            data: documentData,
            cache: false,
            contentType: false,
            processData: false,
            success: function (response) {
                document.getElementById('namefileserver').innerText = response;
                var hash = response;
                console.log(hash);
                hash = hash.substr(0,hash.length - 4);
                console.log(hash);
                document.getElementById('hash').innerText = hash;
                $("#img").attr("src", "img/" + response)
            },
            error: function (jqXHR, status, errorThrown) {
                console.log('ОШИБКА AJAX запроса: ' + status, jqXHR);
            }

        });
        return false;
    });

});
