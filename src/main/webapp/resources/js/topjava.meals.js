let mealAjaxUrl = "profile/meals/";

function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: "profile/meals/filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get("profile/meals/", updateTableByData);
}

$.ajaxSetup({
    converters: {
        "text json": (data) => {
            let json = JSON.parse(data);
            $(json).each((index, value) => {
                value.dateTime = value.dateTime.replace("T", " ").substr(0, 16);
            });
            return json;
        }
    }
});

$(function () {
    makeEditable({
        ajaxUrl: mealAjaxUrl,
        datatableApi: $("#datatable").DataTable({
            "ajax": {
                "url": mealAjaxUrl,
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "",
                    "orderable": false,
                    "render": renderEditBtn,
                },
                {
                    "defaultContent": "",
                    "orderable": false,
                    "render": renderDeleteBtn,
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ],
            "createdRow": (row, data, dataIndex) => {
                $(row).attr("data-mealExcess", data.excess);
            },
            "language": {
                "search": i18n["common.search"],
            },
        }),
        updateTable: updateFilteredTable,
    });
    $("#startDate").datetimepicker({
        timepicker: false,
        format: "Y-m-d",
    });
    $("#endDate").datetimepicker({
        timepicker: false,
        format: "Y-m-d",
    });
    $("#startTime").datetimepicker({
        datepicker: false,
        format: "H:i",
    });
    $("#endTime").datetimepicker({
        datepicker: false,
        format: "H:i",
    });
    $("#dateTime").datetimepicker({
        format: "Y-m-d H:i",
    });
});