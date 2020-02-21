function h(e) {
    $(e).css({'height': 'auto', 'overflow-y': 'hidden'}).height(e.scrollHeight);
}
$('textarea').each(function () {
    h(this);
}).on('input', function () {
    h(this);
});
function h(e) {
    $(e).css({'height': 'auto', 'overflow-y': 'hidden'}).height(e.scrollHeight);
}


function validanumerosnegativos(noperacion) {
    $('.' + noperacion).keypress(function (e) {
        var menos = e.target.value.indexOf("-");
        if (e.which == 45 && menos != -1) {
            if (menos == 0) {
                e.target.value = "-" + e.target.value.replace(/-/g, "");
            }
            return false;
        }
        if (e.which != 45 && e.which != 8 && e.which != 0 && ((e.which < 48) || (e.which > 57))) {
            return false;
        }
    });
}
function smconcomma(pnumero) {
    var nuevoNumero = '';
    // Variable que contendra el resultado final
    var resultado = "";
    var numero = '' + pnumero;
    // Si el numero empieza por el valor "-" (numero negativo)
    if (numero[0] == "-")
    {
        // Cogemos el numero eliminando los posibles commas que tenga, y sin
        // el signo negativo
        nuevoNumero = numero.replace(/\,/g, '').substring(1);
    } else {
        // Cogemos el numero eliminando los posibles commas que tenga
        nuevoNumero = numero.replace(/\,/g, '');
    }
    // Ponemos un comma cada 3 caracteres
    for (var j, i = nuevoNumero.length - 1, j = 0; i >= 0; i--, j++)
        resultado = nuevoNumero.charAt(i) + ((j > 0) && (j % 3 == 0) ? "," : "") + resultado;
    if (numero[0] == "-")
    {
        // Devolvemos el valor añadiendo al inicio el signo negativo
        return "-" + resultado;
    } else {
        return resultado;
    }
}
function separadorDeMilesporClase(clase) {
    $("." + clase).each(function () {
        this.value = (smconcomma($(this).val()));
    });
}
function separadorDeMilesporId(id) {
    var valor = $("#" + id).val();
    var formateado = smconcomma(valor);
    $("#" + id).val(formateado);
}
function separadorDeMilesKeyPressPorClase(porClase) {
    $("." + porClase).each(function () {
        this.addEventListener('keyup', function () {
            agregarEventoSeparaDorMiles(this);
        });
    });
}
function separadorDeMilesKeyPressPorId(porId) {
    $('#' + porId).on('keyup', function () {
        agregarEventoSeparaDorMiles(this);
    });
}
function agregarEventoSeparaDorMiles(input) {
    var nuevoNumero = '';
    var numero = input.value;
    numero = numero.replace(/[^0-9\-]/g, '');
    if (numero != "")
    {
        nuevoNumero = numero.indexOf("-") != -1 ? numero.replace(/\-/g, '') : numero;
        nuevoNumero = smconcomma(nuevoNumero);
    } else {
        input.value = nuevoNumero.substring(0, nuevoNumero.length);
    }
    input.value = numero.indexOf("-") != -1 ? '-' + nuevoNumero : nuevoNumero;
}
function testDecimals(currentVal) {
    var count;
    currentVal.match(/\./g) === null ? count = 0 : count = currentVal.match(/\./g);
    return count;
}
function replaceCommas(yourNumber) {
    var components = yourNumber.toString().split(".");
    if (components.length === 1)
        components[0] = yourNumber;
    components[0] = components[0].replace(/\D/g, "").replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    if (components.length === 2)
        components[1] = components[1].replace(/\D/g, "");
    return components.join(".");
}
function numeroDecimales(clase) {
    $('.' + clase).keyup(function () {
        var valActual = $(this).val();
        var nuevoValor = conComas(valActual);
        $(this).val(nuevoValor);
    });


    /*  $('.' + clase).keyup(function (event) {
     // skip for arrow keys
     if (event.which >= 37 && event.which <= 40)
     event.preventDefault();
     var currentVal = $(this).val();
     var testDecimal = testDecimals(currentVal);
     if (testDecimal.length > 1)
     currentVal = currentVal.slice(0, -1);
     $(this).val(replaceCommas(currentVal));
     });*/
}

function conComas(valor) {
    var nums = new Array();
    var simb = ","; //Éste es el separador
    valor = valor.toString();
    valor = valor.replace(/\D/g, "");   //Ésta expresión regular solo permitira ingresar números
    nums = valor.split(""); //Se vacia el valor en un arreglo
    var long = nums.length - 1; // Se saca la longitud del arreglo
    var patron = 3; //Indica cada cuanto se ponen las comas
    var prox = 2; // Indica en que lugar se debe insertar la siguiente coma
    var res = "";

    while (long > prox) {
        nums.splice((long - prox), 0, simb); //Se agrega la coma
        prox += patron; //Se incrementa la posición próxima para colocar la coma
    }

    for (var i = 0; i <= nums.length - 1; i++) {
        res += nums[i]; //Se crea la nueva cadena para devolver el valor formateado
    }

    return res;
}


function numeroDecimaleslbl(currentVal) {
    currentVal = currentVal.toString();
    var testDecimal = testDecimals((currentVal));
    if (testDecimal.length > 1)
        currentVal = currentVal.slice(0, -1);
    return  replaceCommas(currentVal);

}

function numerosMenosSeparador() {


    if (arguments.length == 1)
    {
        var V = arguments[0].value;
        V = V.replace(/,/g, '');
        var R = new RegExp('(-?[0-9]+)([0-9]{3})');
        while (R.test(V))
        {
            V = V.replace(R, '$1,$2');
        }
        var nmonto = V.replace(/[^0-9\,\-]/g, '');
        if (nmonto.indexOf("-") !== -1) {
            nmonto = '-' + nmonto.replace(/\-/g, '');

        }
        arguments[0].value = nmonto;


    } else if (arguments.length == 2)
    {
        var V = document.getElementById(arguments[0]).value;
        var R = new RegExp('(-?[0-9]+)([0-9]{3})');
        while (R.test(V))
        {
            V = V.replace(R, '$1,$2');
        }
        document.getElementById(arguments[1]).innerHTML = V;
    } else
        return false;


}
function number_format(amount, decimals) {

    amount += ''; // por si pasan un numero en vez de un string
    amount = parseFloat(amount.replace(/[^0-9\.\-]/g, '')); // elimino cualquier cosa que no sea numero o punto

    decimals = decimals || 0; // por si la variable no fue fue pasada

    // si no es un numero o es igual a cero retorno el mismo cero
    if (isNaN(amount) || amount === 0)
        return parseFloat(0).toFixed(decimals);

    // si es mayor o menor que cero retorno el valor formateado como numero
    amount = '' + amount.toFixed(decimals);

    var amount_parts = amount.split('.'),
            regexp = /(\d+)(\d{3})/;

    while (regexp.test(amount_parts[0]))
        amount_parts[0] = amount_parts[0].replace(regexp, '$1' + ',' + '$2');

    return amount_parts.join('.');
}
function quitarCommas(currentVal) {
    if (currentVal == "undefined" || currentVal == undefined) {
        return "";
    } else {
        while (currentVal.toString().indexOf(',') != - 1)
            currentVal = currentVal.toString().replace(',', '');
        return currentVal;
    }



}
function validanumerosRP(noperacion) {
    $('.' + noperacion).keypress(function (e) {
        if (e.which != 8 && e.which != 0 && ((e.which != 45 && e.which < 48) || (e.which != 45 && e.which > 57))) {
            return false;
        }
    });
}

function mensajeError(msg) {
	$.alert({
	    title: 'Aviso',
	    content: msg,
	    type: 'red'
	});
}
function mensajeError2(stitle, msg) {
    bootbox.dialog({
        title: stitle,
        message: "" + msg,
        closeButton: false,
        buttons: {
            success: {
                label: "OK",
                className: "btn-sm btn-danger",
                callback: function () {

                }
            }

        },
        callback: function (result) {
            console.log('This was logged in the callback: ' + result);
        }
    });
}
function mensaje(msg) {
	$.alert({
	    title: 'Información',
	    content: msg,
	    type: 'green',
	    
	});
}
function mensajeServidor(msg) {
    	$.confirm({
    	    title: 'Aviso',
    	    content: msg,
    	    type: 'green',
    	    buttons: {
    	    	text: 'OK',
	    	    OK: function(){
	    	        window.location.reload();
	    	    }
    	    }
    	});
}
function confirmarDesactivar(modulo, codigo) {
    confirmar(modulo, codigo, '0')
}
function confirmarActivar(modulo, codigo) {
    confirmar(modulo, codigo, '1')
}
function confirmar(modulo, codigo, estado) {
    bootbox.dialog({
        message: "esta seguro de " + (estado === '1' ? 'ACTIVAR' : 'DESACTIVAR') + " el registro?",
        buttons: {
            "success": {
                "label": "OK",
                "className": "btn-sm btn-danger",
                callback: function () {
                    cambiarEstado(modulo, codigo, estado);

                }
            }

        }
    });
}
function cambiarEstado(modulo, codigo, estado) {
    if (modulo == "pagos") {
        $.ajax({
            type: 'POST',
            url: 'tipopago',
            data: {"do": "eliminarPagoSA", "ncodigo": codigo, "estado": estado},
            dataType: 'json',
            success: function (json) {
                mensajeServidor(json);
            }
        });
    } else if (modulo == "credito") {
        $.ajax({
            type: 'POST',
            url: 'Credito',
            data: {"do": "eliminarcredito", "ncodigo": codigo, "estado": estado},
            dataType: 'json',
            success: function (json) {
                mensajeServidor(json);
            }
        });

    } else if (modulo == "representante") {
        $.ajax({
            type: 'POST',
            url: 'representante',
            data: {"do": "eliminarRepresentante", "ncodigo": codigo, "estado": estado},
            dataType: 'json',
            success: function (json) {
                mensajeServidor(json);

            }
        });

    } else if (modulo == "formatoanual") {
        $.ajax({
            type: 'POST',
            url: 'otrasDeclaraciones',
            data: {"do": "eliminarArchivo", "ncodigo": codigo, "estado": estado},
            dataType: 'json',
            success: function (json) {
                mensajeServidor(json);
            }
        });

    }
}
function dblclickblockeo() {
    $('a, button').on('click', function (e) {
        var $link = $(e.target);
        if (!$link.data('lockedAt')) {
            $link.data('lockedAt', +new Date());
        } else if (+new Date() - $link.data('lockedAt') > 500) {
            $link.data('lockedAt', +new Date());
        } else {
            e.preventDefault();
            e.stopPropagation();
            e.stopImmediatePropagation();
        }
    });
}
function agregarIconos(cad, idtable) {
    var at = cad.substring(0, cad.length - 1);
    var a = at.split(",");
    var tablaHTML = "";
    for (var m = 0; m < a.length; m++) {
        tablaHTML += '<tr>';
        var exten = "";
        if (a[m].indexOf(".doc") != -1 || a[m].indexOf(".docx") != -1) {
            exten = "assets/img/word-icon.png";
        } else if (a[m].indexOf(".pdf") != -1) {
            exten = "assets/img/pdf_ico.png";
        } else if (a[m].indexOf(".xls") != -1 || a[m].indexOf(".xlsx") != -1) {
            exten = "assets/img/Excel-2-icon.png";
        } else {
            exten = "assets/img/imagen.png";
        }
        var id = idtable.replace("Table", "");
        tablaHTML += "<td class=\"center\"><a href=\"Adjunto?do=" + id + "&ruta=" + a[m] + "\" target=\"_blank\"><img src=\"" + exten + "\"  width=\"50\" height=\"50\"/></a></td>";
        tablaHTML += '</tr>';
    }

    $("#" + idtable).html("");
    $("#" + idtable).append(tablaHTML);

}
function setArchivoshdd(arr, idinputfile) {
    console.log(arr.ruta);
    var arch = $("#" + idinputfile + "hdd").val();
    arch += arr.ruta + ",";
    $("#" + idinputfile + "hdd").val(arch);
    agregarIconos($("#" + idinputfile + "hdd").val(), idinputfile + "Table");
}
updArchivos = function () {
    $.ajax({
        type: 'POST',
        url: 'Documento?do=updArchivosAporte'
                + '&codAportehdd=' + $("#codAportehdd").val()
                + '&sustentohdd=' + $("#sustentohdd").val()
        ,
        dataType: 'json',
        success: function (json) {
            location.reload();
        }
    });
}
function subirarchivo(nameUpload, maxtamanio, acceptFileTypes) {
    $("#" + nameUpload + "upload").click(function () {
        $("#" + nameUpload + "Table").html("");
        $("." + nameUpload + "fileCancel").click();
        $("#" + nameUpload + "subirtodo").css("display", "none");
        $("#" + nameUpload + "cancelatodo").css("display", "none");
        $("#" + nameUpload + "hdd").val("");
    });
    $("#" + nameUpload + "subirtodo").click(function () {
        if ($("." + nameUpload + "fileUpload").length > 0) {
            $("." + nameUpload + "fileUpload").click();
            $("." + nameUpload + "fileUpload").css("display", "none");
            $("." + nameUpload + "fileCancel").css("display", "none");
            $("#" + nameUpload + "subirtodo").css("display", "none");
            $("#" + nameUpload + "cancelatodo").css("display", "none");
            $("#" + nameUpload + "hdd").val("");
        }
    });
    $("#" + nameUpload + "cancelatodo").click(function () {
        $("." + nameUpload + "fileCancel").click();
        $("#" + nameUpload + "hdd").val("");
        $("#" + nameUpload + "subirtodo").css("display", "none");
        $("#" + nameUpload + "cancelatodo").css("display", "none");
    });
    $('#' + nameUpload + "upload").fileupload({
        autoUpload: false,
        formData: [],
        add: function (e, fdata) {
            var uploadErrors = [];
            //var acceptFileTypes = ['pdf', 'doc', 'docx','PDF', 'DOC', 'DOCX'];
            var nombre = fdata.files[0]['name'].split(".");
            var mayuscArracceptFileTypes = String.prototype.toUpperCase.apply(acceptFileTypes).split(",");

            var extension = (nombre[nombre.length - 1]).toUpperCase();
            if (mayuscArracceptFileTypes.indexOf(extension) == -1) {
                uploadErrors.push('Solo se acepta archivos de formato ' + mayuscArracceptFileTypes);
            }
            if (fdata.files[0].size > ((parseInt(maxtamanio) + 1) * (1024 * 1024))) {
                uploadErrors.push('El tama\u00f1o del archivo solo puede ser hasta ' + maxtamanio + ' mb');
            }
            if (uploadErrors.length > 0) {
                alert(uploadErrors.join("\n"));
            } else {
                var file = fdata.files[0];
                var vOutput = "";
                vOutput += "<tr><td>" + file.name + "</td>";
                vOutput += "<td><button class='" + (nameUpload + "fileUpload") + " btn btn-success btn-xs' style='display:none'><i class='ace-icon fa fa-upload'></i></button></td>";
                vOutput += "<td><button class='" + (nameUpload + "fileCancel") + " btn btn-danger btn-xs' ><i class='ace-icon fa fa-trash'></i></button></td></tr>";
                $("#" + nameUpload + "Table").append(vOutput);
                $("#" + nameUpload + "subirtodo").css("display", "inline");
                $("#" + nameUpload + "cancelatodo").css("display", "inline");
                $(".conceptoimporteconcepto").attr("disabled", "disabled");
                $(".conceptoimporte").prop("disabled", "disabled");
                $("." + nameUpload + "fileUpload").eq(-1).on("click", function () {

                    fdata.submit();


                });
                $("." + nameUpload + "fileCancel").eq(-1).on("click", function () {
                    $(this).parent().parent().remove();
                });

            }
        },
        done: function (e, data) {
            var arr = JSON.parse(data.result);
            setArchivoshdd(arr, nameUpload);


            if (nameUpload=='sustento'){
                var $fileInput = $('#sustentoupload');
                var activeUploads = $fileInput.fileupload('active');
                if(activeUploads==1){
                    updArchivos();
                }
            }

        }
    });

}
function verArchivos(cad) {
    var at = cad.substring(0, cad.length - 1);
    var a = at.split(",");
    var tablaHTML = '<table id="tableDocAsig" class="table table-striped table-bordered table-hover">';
    tablaHTML += ' <thead><tr><th class="center">N°</th><th class="center">DOCUMENTO</th></tr></thead>';
    tablaHTML += ' <tbody>';
    if (a.length === 0) {
        tablaHTML += '<tr> <td > No se han registrado documentos.</td></tr>';
    } else {
        for (var m = 0; m < a.length; m++) {
            tablaHTML += '<tr>';
            tablaHTML += "<td class=\"center\">" + (m + 1) + "</td>"
            var exten = a[m].indexOf(".doc") != -1 || a[m].indexOf(".docx") != -1 ? "assets/img/word-icon.png" : "assets/img/pdf_ico.png";
            tablaHTML += "<td class=\"center\"><a href=\"Adjunto?do=representante&ruta=" + a[m] + "\" target=\"_blank\"><img src=\"" + exten + "\"  width=\"50\" height=\"50\"/></a></td>";
            tablaHTML += '</tr>';
        }
    }
    tablaHTML += '</tbody></table>';
    $("#modalArchivosBody").html(tablaHTML);
    $("#modalVerArchivosRep").modal("show");
}
function getReprentantesxTabla(idtabla, dataresponse) {
    var cnfdocasigna = $("#cnfdocasigna").val();
    if (dataresponse != null) {
                    $("#" + idtabla + " tbody").find("tr:gt(0)").remove();
                        var table1 = $("#" + idtabla + " tbody").html("");
        var a = 1;
           $.each(dataresponse, function (key, value) {
            var tbdy = "<tr>" +
                    "<td></td>" +
                    "<td></td>" +
                    "<td></td>" +
                    "<td></td>" +
                    "<td></td>" +
                    "<td></td>" +
                    "<td></td>" +
                    "<td></td>" +
                    (cnfdocasigna == "1" ? "<td></td>" : "")
            "</tr>";
            var rowNew = $(tbdy);
            var col = 0;
            rowNew.children().eq(col++).text(a++);
            rowNew.children().eq(col++).text(value['tipoDoc']);                      
            rowNew.children().eq(col++).text(value['numDoc']);
            rowNew.children().eq(col++).text(value['nombres']);
            rowNew.children().eq(col++).text(value['apePat']);
            rowNew.children().eq(col++).text(value['apeMat']);                                                                       
            rowNew.children().eq(col++).text(value['cargo']);
            rowNew.children().eq(col++).text(value['fechainipoder']);
            if (cnfdocasigna == "1") {
                if (value['docasigpoder'] != "") {
                    rowNew.children().eq(col++).html("<a href=\"#\" onclick=\"verArchivos('" + value['docasigpoder'] + "')\" >Ver</a>");
                } else {
                    rowNew.children().eq(col++).html("");
                }
            }
            rowNew.appendTo(table1);                        
                        });
    }
}


function getHistorialBC(idtabla, dataresponse) {

    if (dataresponse != null) {
                    $("#" + idtabla + " tbody").find("tr:gt(0)").remove();
                        var table1 = $("#" + idtabla + " tbody").html("");
        var a = 1;
           $.each(dataresponse, function (key, value) {
            var tbdy = "<tr>" +
                    "<td></td>" +
                    "<td></td>" +
                    "<td></td>" +
                    "<td></td>" +
                    "</tr>";
            var rowNew = $(tbdy);
            var col = 0;
            rowNew.children().eq(col++).text(a++);
            rowNew.children().eq(col++).text(value['buencontribuyente']);                      
            rowNew.children().eq(col++).text(value['fechamod']);
            rowNew.children().eq(col++).text(value['usumod']);
            rowNew.appendTo(table1);                        
                        });
    }
}

String.prototype.htmlEscape = function () {
    return this.replace(/&/g, "&amp;").replace(/>/g, "&gt;").replace(/</g, "&lt;").replace(/"/g, "&quot;");
}


function imprimirControlPrevio(cod, periodo, ruc, razon, mes, anio, codcalificacionlog, principal) {

    var myUrl = 'reporteControlPrevio?mcodAporte=' + cod
            + "&mcodPeriodo=" + periodo
            + "&ruc=" + ruc
            + "&razon=" + razon
            + "&codcalificacionlog=" + codcalificacionlog
            + "&principal=" + principal;
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
        link.download = mes + '-' + anio + '_' + new Date() + ".pdf";

        link.click();
    };

}





$(document).ajaxStart(function () {
    $('#wait').show();
});
$(document).ajaxStop(function () {
    $('#wait').hide();
});
$(document).ajaxError(function () {
    $('#wait').hide();
});
jQuery(document).ready(function ($) {
    dblclickblockeo();
 
    $('.date-picker').datepicker({
        autoclose: true,
        todayHighlight: true
    });

    $('textarea').each(function () {
        h(this);
    }).on('input', function () {
        h(this);
    });
    
    $('input,textarea ').keypress(function(event){
        if(event.keyCode == 13){
  	      event.preventDefault();
        }
  });
    
   
    
});