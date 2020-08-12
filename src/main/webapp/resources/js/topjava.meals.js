let mealAjaxUrl = "profile/meals/";

function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: mealAjaxUrl + "filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter .form-control").each((index, val) => {
        $(val).datetimepicker('reset');
    });
    $.get(mealAjaxUrl, updateTableByData);
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

    $.datetimepicker.setLocale(language.substr(0, 2));

    $("#startDate").datetimepicker({
        timepicker: false,
        format: "Y-m-d",
        onShow: function () {
            let endDateVal = $("#endDate").val();
            this.setOptions({
                maxDate: endDateVal ? endDateVal : false
            });
        },
    });
    $("#endDate").datetimepicker({
        timepicker: false,
        format: "Y-m-d",
        onShow: function () {
            let startDateVal = $("#startDate").val();
            this.setOptions({
                minDate: startDateVal ? startDateVal : false
            });
        },
    });
    $("#startTime").datetimepicker({
        datepicker: false,
        format: "H:i",
        onShow: function () {
            let endTimeVal = $("#endTime").val();
            this.setOptions({
                maxTime: endTimeVal ? endTimeVal : false
            });
        },
    });
    $("#endTime").datetimepicker({
        datepicker: false,
        format: "H:i",
        onShow: function () {
            let startTimeVal = $("#startTime").val();
            this.setOptions({
                minTime: startTimeVal ? startTimeVal : false
            });
        },
    });
    $("#dateTime").datetimepicker({
        format: "Y-m-d H:i",
    });
});