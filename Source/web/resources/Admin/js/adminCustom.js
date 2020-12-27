$(document).ready(function () {

    /*------------------------------
     DataTable
     ------------------------------*/
    $('.dataTables-example').DataTable({
        pageLength: 10,
        responsive: true,
        dom: '<"html5buttons"B>lTfgitp',
        buttons: [
            {extend: 'copy'},
            {extend: 'csv'},
            {extend: 'excel', title: 'ExampleFile'},
            {extend: 'print',
                customize: function (win) {
                    $(win.document.body).addClass('white-bg');
                    $(win.document.body).css('font-size', '10px');

                    $(win.document.body).find('table')
                            .addClass('compact')
                            .css('font-size', 'inherit');
                }
            }
        ]

    });

    $('#reportData').DataTable({
        order: [[2, 'desc']],
        scrollY: "165px",
        scrollCollapse: true,
        paging: false,
        searching: true,
        info: false,
        pageLength: 10,
        responsive: true,
        dom: '<"html5buttons"B>lTfgitp',
        buttons: [
            {extend: 'excel', title: 'Income Report', footer: true}
        ]
    });


    /*------------------------------
     Choose Avatar
     ------------------------------*/
    $('#changeAvatar').on('click', function (e) {
        e.preventDefault();
        $('#profileForm\\:chooseAvatarFile').click();
    });
    $('#profileForm\\:chooseAvatarFile').on('change', function (e) {
        if (this.files && this.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                $('#avatar').attr('src', e.target.result).fadeIn('slow');
            };
            reader.readAsDataURL(this.files[0]);
        }
    });
    
    /*------------------------------
     Reset Avatar
     ------------------------------*/
    $('#resetAvatar').on('click', function (e) {
        $('#avatar').attr('src', '../resources/images/Avatars/default-avatar.png');
        $('#profileForm\\:editedAvatar').val('images/Avatars/default-avatar.png');
    });


});


/*------------------------------
 Sweet Alert
 ------------------------------*/
function disable(pageName, id, state) {
    swal({
        title: "Are you sure?",
        text: "This action will change the status",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "Yes, let do it!",
        cancelButtonText: "No, cancel plx!",
        closeOnConfirm: false,
        closeOnCancel: false},
    function (isConfirm) {
        if (isConfirm) {
            window.location = pageName + ".xhtml?action=disable&id=" + id + "&state=" + state;
        } else {
            swal("Cancelled", "You cancel the action", "error");
        }
    });
}

function disableOD(pageName, id, odID, state) {
    swal({
        title: "Are you sure?",
        text: "This will be disabled if you click on [Yes] button",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "Yes, let do it!",
        cancelButtonText: "No, cancel plx!",
        closeOnConfirm: false,
        closeOnCancel: false},
    function (isConfirm) {
        if (isConfirm) {
            window.location = pageName + ".xhtml?action=disable&id=" + id + "&odID=" + odID + "&state=" + state;
        } else {
            swal("Cancelled", "You cancel the action", "error");
        }
    });
}


/*------------------------------
 Choose Product's Images
 ------------------------------*/
function chooseProductImage(event, imgNum) {
    event.preventDefault();
    $('#profileForm\\:chooseProductImage' + imgNum).click();
}
;

function changeImage(e, ele, imgNum) {
    if (ele.files && ele.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $('#productImage' + imgNum).attr('src', e.target.result).fadeIn('slow');
        };
        reader.readAsDataURL(ele.files[0]);
    }
}
;
