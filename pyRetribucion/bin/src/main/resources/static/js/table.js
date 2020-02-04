
function formatearTabla(tabla) {
    $('#' + tabla + ' tfoot th').each(function (i) {
        var title = $('#' + tabla + ' tfoot th').eq($(this).index()).text();
        $(this).html('<input type="text" placeholder="' + title + '" data-index="' + i + '" />');
    });

    // DataTable
    var table = $('#' + tabla).dataTable({
        fixedColumns: true
    });

    // Filter event handler
    $(table.table().container()).on('keyup', 'tfoot input', function () {
        table
                .column($(this).data('index'))
                .search(this.value)
                .draw();
    });
}

jQuery(document).ready(function ($) {

    $('#tablePeriodo-1').dataTable(
            {
                "SEmptyTable": "Carga de datos desde el servidor",
                "aoColumns": [
                    {"bSortable": false},
                    null, null, null, null,
                    {"bSortable": false}
                ],
                "aaSorting": [],
            });

 });


