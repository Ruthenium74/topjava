let currentFilter;

function updateFilteredTable() {
    currentFilter = $('#filter').serialize();
    if (currentFilter) {
        $.get(context.ajaxUrl + "filter", currentFilter).done(function (data) {
            context.datatableApi.clear().rows.add(data).draw();
        })
    }
}

function clearFilter() {
    $('#filter')[0].reset();
    context.updateTable();
}

$(function () {
    makeEditable({
            ajaxUrl: "ajax/meals/",
            datatableApi: $("#datatable").DataTable({
                "paging": true,
                "info": true,
                "columns": [
                    {
                        "data": "dateTime"
                    },
                    {
                        "data": "description"
                    },
                    {
                        "data": "calories"
                    },
                    {
                        "defaultContent": "Edit",
                        "orderable": false
                    },
                    {
                        "defaultContent": "Delete",
                        "orderable": false
                    }
                ],
                "order": [
                    [
                        0,
                        "desc"
                    ]
                ],
                "language": {
                    "url": "http://cdn.datatables.net/plug-ins/1.10.21/i18n/Russian.json"
                }
            }),
            updateTable: updateFilteredTable
        }
    );
});