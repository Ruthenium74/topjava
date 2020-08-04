function toggleUserActivation(checkBox, userId) {
    let enabled = checkBox.is(':checked');
    $.post(context.ajaxUrl + "setEnabled", {"enable": enabled, "id": userId}).done(() => {
        checkBox.closest("tr").attr("data-userEnable", enabled);
        successNoty(enabled ? "enabled" : "disabled");
    }).fail(() => checkBox.prop('checked', !enabled));
}

$(function () {
    makeEditable({
            ajaxUrl: "admin/users/",
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "name"
                    },
                    {
                        "data": "email"
                    },
                    {
                        "data": "roles"
                    },
                    {
                        "data": "enabled"
                    },
                    {
                        "data": "registered"
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
                        "asc"
                    ]
                ],
                "language": {
                    "url": "http://cdn.datatables.net/plug-ins/1.10.21/i18n/Russian.json"
                }
            }),
            updateTable: updateTable
        }
    );
});