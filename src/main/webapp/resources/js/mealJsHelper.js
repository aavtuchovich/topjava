var ajaxUrl = "ajax/meals/";
var datatableApi;

// $(document).ready(function () {
$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
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
                "data": "actions"
            }
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ]
    });
    makeEditable();

    $("#btnId").click(function (event) {
        event.preventDefault();
        openModel();
    });

    // Open Bootstrap Modal
    function openModel() {
        $("#modalId").modal();
    }

    $(function() {
        /*  Submit form using Ajax */
        $('button[type=submit][name="addMeal"]').click(function(e) {
            $.post({
                url : 'meals',
                data : $('form[name=createOrUpdateMeal]').serialize(),
                success : function(res) {
                    if(res.validated){
                        //Set response
                        $('#resultContainer pre code').text(JSON.stringify(res.employee));
                        $('#resultContainer').show();

                    }
                }
            })
        });
    });
});