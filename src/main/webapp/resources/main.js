/**
 * Created by ryuga on 28/02/17.
 */

var ws;

$(document).ready(function() {

    ws = new WebSocket('ws://localhost:8080/table/socket');
    ws.onmessage = function (e) {
        $('#table').DataTable().row(function (i, data, node) {
            var removedData = JSON.parse(e.data);
            return removedData.id == data.id;
        }).remove().draw();
    };

    $.ajax( 'table' )
        .done(function(json) {
            $('#table').DataTable({
                'data': json,
                'columns': [
                    { 'data': 'firstName' },
                    { 'data': 'lastName' },
                    { 'data': 'salary' },
                    { 'render': function () {
                        return '<a class="btn btn-danger glyphicon glyphicon-remove" onclick="removerLinha(this)"></a>';
                    }}
                ]
            });
        });
});

function removerLinha(removeButton) {
    var tr = $(removeButton).parents('tr');
    var row = $('#table').DataTable().row(tr);
    var person = row.data();
    $.ajax({
        url: 'remove/person',
        method: 'DELETE',
        contentType: 'application/json',
        data: JSON.stringify(person)
    });
}