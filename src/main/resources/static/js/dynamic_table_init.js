// function fnFormatDetails(oTable, nTr) {
//     var aData = oTable.fnGetData(nTr);
//     var sOut = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';
//     sOut += '<tr><td>Rendering engine:</td><td>' + aData[1] + ' ' + aData[4] + '</td></tr>';
//     sOut += '<tr><td>Link to source:</td><td>Could provide a link here</td></tr>';
//     sOut += '<tr><td>Extra info:</td><td>And any further details here (images etc)</td></tr>';
//     sOut += '</table>';
//     return sOut;
// }

// $(document).ready(function () {
//     var nCloneTh = document.createElement('th');
//     var nCloneTd = document.createElement('td');
//     nCloneTd.innerHTML = '<img src="/images/details_open.png">';
//     nCloneTd.className = "center";
//
//     $('#planList thead tr').each(function () {
//         this.insertBefore(nCloneTh, this.childNodes[0]);
//     });
//
//     $('#planList tbody tr').each(function () {
//         this.insertBefore(nCloneTd.cloneNode(true), this.childNodes[0]);
//     });
//
//     var table = $('#planList').dataTable({
//         "aoColumnDefs": [
//             {"bSortable": false, "aTargets": [0]}
//         ],
//         "aaSorting": [[1, 'asc']],
//
//     });
//
//     $(document).on('click', '#planList tbody td img', function () {
//         var nTr = $(this).parents('tr')[0];
//         if (table.fnIsOpen(nTr)) {
//             /* This row is already open - close it */
//             this.src = "/images/details_open.png";
//             table.fnClose(nTr);
//         }
//         else {
//             /* Open this row */
//             this.src = "/images/details_close.png";
//             table.fnOpen(nTr, fnFormatDetails(table, nTr), 'details');
//         }
//     });
// });

var dataTablesSettings = {
    lengthChange: false,
    searching: false,
    ordering: false,
    language: {
        lengthMenu: "每页显示 _MENU_记录",
        zeroRecords: "没有匹配的数据",
        info: "第_PAGE_页/共 _PAGES_页 _TOTAL_条",
        infoEmpty: "没有符合条件的记录",
        search: "查找",
        infoFiltered: "(从 _MAX_条记录中过滤)",
        paginate: {"first": "首页 ", "last": "末页", "next": "下一页", "previous": "上一页"}
    }
};

// function planData(plans) {
//     var data = [];
//     var operate = "操作";
//     $.each(plans, function (i, plan) {
//         var unitPrice = plan.unitPrice;
//         data.totalPrice = plan.totalPrice;
//         data.status = plan.status;
//         data.push([plan.name, plan.unitPrice, plan.totalPrice, plan.status, operate]);
//     });
//     return data;
// }

$(function () {
    // var plans = JSON.parse($("#plans").val());
    // var data = planData(plans);
    // dataTablesSettings.data = data;
    $('#planList').dataTable(dataTablesSettings);
});


