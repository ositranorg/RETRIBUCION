function total_banco() {

    var importe_total = 0;
    var importe_banco = 0;
    $(".bancoImporte").each(
            function (index, value) {

                var codigo = $(this).parent().parent().parent().attr("data-id");
                var estado = $("#bancoEstado-" + codigo).val();
                var sincommas = quitarCommas($(this).val());
                importe_total = importe_total + eval(sincommas === "" ? "0" : sincommas);

                if (estado === "DP") {
                    importe_banco = importe_banco + eval(sincommas === "" ? "0" : sincommas);
                }
            }
    );

    $("#totalBanco").html(smconcomma(importe_total));
    $("#totalpago").val(smconcomma(importe_banco));
    separadorDeMilesporId('#totalBanco');
}

function total_sunat() {

    var importe_total = 0;
    $(".sunatImporte").each(
            function (index, value) {
                var sincommas = quitarCommas($(this).val());
                importe_total = importe_total + eval(sincommas === "" ? "0" : sincommas);
            }
    );
    $("#totalSunat").html(smconcomma(importe_total));

}

function colorResultados(id, monto) {
    $("#" + id).css("color", (monto > 0 ? "red" : "blue"));
    $("#" + id).css("font-weight", "bold");
}
total = function () {
    total_sunat();
    totalBase = parseFloat($("#totalbase").val() === "" ? 0 : $("#totalbase").val());
    totalBanco = parseFloat($("#tPagoPrevhdd").val() === "" ? 0 : $("#tPagoPrevhdd").val());
    porcentage = Math.round((totalBase * 0.01));
    credito = parseFloat($("#creditoPerAnthdd").val() === "0" ? 0 : $("#creditoPerAnthdd").val());
    mora = parseFloat($("#interes").val() === "" ? 0 : quitarCommas($("#interes").val()));
    //mora = mora - parseFloat($("#tPPInthdd").val() === "" ? 0 : $("#tPPInthdd").val());
    $("#tBase").html(smconcomma(totalBase));
    $("#tPorbase").html(smconcomma(porcentage));
    $("#porcentajePago").val(smconcomma(porcentage));
    totalPagoA = parseFloat(totalBanco + credito);
    totalPagar = parseFloat(porcentage + mora);
    if (parseFloat(porcentage - totalPagoA) >= 0) {
        $("#sumAportePorPagar").html(smconcomma(porcentage - totalPagoA));
    } else {
        $("#sumAportePorPagar").html("(" + smconcomma((porcentage - totalPagoA) * -1) + ")");
    }
    colorResultados("sumAportePorPagar", (porcentage - totalPagoA));
    var importeAPagar = parseFloat(totalPagar - totalPagoA);
    $("#importeaPagarhdd").val(importeAPagar);
    if (importeAPagar >= 0) {
        $("#tImporte").html(smconcomma(importeAPagar));
    } else {
        creditoHtml = "(" + smconcomma((importeAPagar) * -1) + ")";
        $("#tImporte").html((creditoHtml));
    }
    //fuera de fecha de pago en el Banco

    var deudasaldo = importeAPagar;
    if (deudasaldo >= 0) {
        $("#creditoN").html(smconcomma(deudasaldo));
        $("#deudaoSaldoN").val(smconcomma(deudasaldo));
    } else {
        var ds = "(" + smconcomma((deudasaldo) * -1) + ")";
        $("#creditoN").html(ds);
        $("#deudaoSaldoN").val(smconcomma(deudasaldo));
    }
    colorResultados("tImporte", importeAPagar);
    colorResultados("creditoN", deudasaldo);

}

function bajar(tema) {
    var elem = document.getElementById('containerconcepto-' + tema);
    if (elem != null)
        elem.scrollTop = elem.scrollHeight;
}

function validanumeros(noperacion) {
    //called when key is pressed in textbox
    $('.' + noperacion).keypress(function (e) {
        //if the letter is not digit then display error and don't type anything
        if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
            return false;
        }
    });
}



function redirecDeclararEditar() {
    //
    $.ajax({
        type: 'POST',
        url: 'Documento?do=buscarDJ&sMes=' + $('#sMes').val() + '&sAnio=' + $('#sAnio').val(),
        dataType: 'json',
        success: function (json) {
            var bdjcodigo = json[0].codigo;
            var bdjpmes = $("#sMes").val();
            var bdjanio = $("#sAnio").val();
            if (bdjcodigo != 0 && bdjpmes != null && bdjanio != null) {
                location.href = "editardeclaracion.jsp?codAporte=" + bdjcodigo + "&iniuno=1&finuno=10&inidos=1&findos=10";
            }
        }
    });
}
function eliminarSustento(codaporte, codruta) {

    $.ajax({
        type: 'POST',
        url: 'Documento?do=eliminarSustento&codaporte=' + codaporte + '&ruta=' + codruta,
        dataType: 'json',
        success: function (json) {
            location.reload(true);
        }
    });
}
function cargarTipoDeclaracion() {
    var s = "#cbocodTipoDeclaracion option:eq(" + (parseInt($("#codTipoDeclaracionhdd").val()) - 1) + ")";
    $(s).prop('selected', true);
}
function eliminarArchivo(codaporte, codruta) {

    $.ajax({
        type: 'POST',
        url: 'Documento?do=eliminarArchivo&codaporte=' + codaporte + '&ruta=' + codruta,
        dataType: 'json',
        success: function (json) {
            location.reload(true);
        }
    });
}
validarfrm = function () {
    $("#frmval").val("I");
    $('.conceptoimporteconcepto').each(function () {
        $(this).rules("add",
                {
                    required: true
                })

    });

    $('.conceptoimporte').each(function () {
        $(this).rules("add",
                {
                    required: true,
                    number: true
                })
    });

    $('.pagobanco').each(function () {
        $(this).rules("add",
                {
                    required: true
                })
    });

    $('.bancoImporte').each(function () {
        $(this).rules("add",
                {
                    required: true,
                    number: true
                })
    });
    $('.codTipoPago').each(function () {
        $(this).rules("add",
                {
                    required: true
                })
    });

    $("#frmval").val("V");



}
actualizaSunat = function () {

    $.ajax({
        type: 'POST',
        url: 'Documento?do=actualizarSunatxid'
                + '&codAportehdd=' + $("#codAportehdd").val()
                + '&codigosunathdd=' + $("#codigosunathdd").val()
                + '&nordenSunat=' + $("#nordenSunat").val()
                + '&fechaSunat=' + $("#fechaSunat").val()
                + '&vgravadaSunat=' + $("#vgravadaSunat").val()
                + '&vnogravadaSunat=' + $("#vnogravadaSunat").val()
                + '&votrasSunat=' + $("#votrasSunat").val(),
        dataType: 'json',
        success: function (json) {
            // location.reload();
        }
    });
}
eliminarItembi = function (i) {
    $.ajax({
        type: 'POST',
        url: 'Documento?do=eliminarbi&codigobihdd=' + i,
        dataType: 'json',
        success: function (json) {
            location.reload();
        }
    });
}

//function abrirNuevoModificarBaseImponible(b, m, i, con, imp) {
//    $("#codaportebihdd").val($("#codAportehdd").val());
//    $("#titulobi").html((m === "N" ? "AGREGAR" : "EDITAR") + " BASE IMPONIBLE");
//    $("#guardarbi").html((m === "N" ? "AGREGAR" : "EDITAR"));
//    $("#codigobihdd").val(m === "N" ? 0 : i);
//    $("#biconcepto").val(m === "M" ? con : "");
//    $("#biimporte").val(m === "M" ? imp : "");
//    separadorDeMilesporId("biimporte");
//    $("#lbldetallebi").html(b == 1 ? "DETALLE DE TODOS LOS INGRESOS FACTURADOS DEL PERIODO A DECLARAR" : "DETALLE DE LOS INGRESOS FACTURADOS NO INCLUIDOS EN LA BASE IMPONIBLE");
//    $("#modalBaseImponible").modal("show");
//
//}
grabarBIxId = function () {
    $.ajax({
        type: 'POST',
        url: 'Documento?do=grabarBIUnoaUno',
        data: $('#frmBiImportes').serialize(),
        dataType: 'json',
        success: function (json) {
            location.href = "editardeclaracion.jsp?iniuno=0&finuno=10&inidos=1&findos=10&codAporte=" + $("#codAportehdd").val();
        }
    });
}
eliminarbixtema = function (a, t) {
    $.ajax({
        type: 'POST',
        url: 'Documento?do=eliminarbixtema&codaportebihdd=' + a + '&tema=' + t,
        dataType: 'json',
        success: function (json) {
            location.reload();
        }
    });
}


function imprimirVistaPrevia() {

    var myUrl = 'verReporteSGP?codAportehdd=' + $("#codAportehdd").val()
            + "&sMeshdd=" + $("#sMeshdd").val()
            + "&sAniohdd=" + $("#sAniohdd").val()
            + "&nordenSunat=" + $("#nordenSunat").val()
            + "&totalbase=" + $("#totalbase").val()
            + "&porcentajePago=" + $("#porcentajePago").val()
            + "&tPagoPrevhdd=" + $("#tPagoPrevhdd").val()
            + "&creditoPerAnthdd=" + $("#creditoPerAnthdd").val()
            + "&interes=" + $("#interes").val()
            + "&importeaPagarhdd=" + $("#importeaPagarhdd").val()
            + "&cbocodTipoDeclaracion=" + $("#cbocodTipoDeclaracion").val()

            + "&fechaSunat=" + $("#fechaSunat").val()
            + "&vgravadaSunat=" + $("#vgravadaSunat").val()
            + "&vnogravadaSunat=" + $("#vnogravadaSunat").val()
            + "&votrasSunat=" + $("#votrasSunat").val()
            + "&eddjjruchdd=" + $("#eddjjruchdd").val()
            + "&eddjjnomhdd=" + $("#eddjjnomhdd").val()

            + "&importe=" + $("#importe").val()

            ;

    var req = new XMLHttpRequest();
    req.open("POST", myUrl, true);
    req.responseType = "blob";
    req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    req.send();

    req.onreadystatechange = function () {//Call a function when the state changes.
        if (req.readyState == 4 && req.status == 200) {
            //console.log(req.responseText);
        }
    }

    req.onload = function (event) {
        var blob = req.response;
        console.log(blob.size);
        var link = document.createElement('a');
        link.href = window.URL.createObjectURL(blob);
        window.open(link.href);
        //        link.download = mes + '-' + anio + '_' + new Date() + ".pdf";

        //link.click();
    };

}




