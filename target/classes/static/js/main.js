

$(document).ready(function () {

    //Modal for adding a new user !
    $(' .nBtn').on('click', function (event) {

        $('.myForm #exampleModal').modal();
    });

    //Modal for editing an existing user !
    $('.table .eBtn').on('click', function (event) {

            event.preventDefault();
            let href = $(this).attr('href');
            $.get(href, function (User, status) { // Get data which we ll assign to the pop up form fields.

                $('.myFormEdit #id ').val(User.id); // set the id field
                $('.myFormEdit #username').val(User.userName); // set the username field
                $('.myFormEdit #email').val(User.email);
                $('.myFormEdit #Firstname').val(User.firstName);
                $('.myFormEdit #Lastname').val(User.lastName);
                $('.myFormEdit #pwd').val('');
            })

             $('.myFormEdit #editting').modal()

    });

    //Modal for deleting an existing user ; getting the confirmation !
    $('.table .delBtn').on('click', function (event) {
        event.preventDefault();
        let href = $(this).attr('href');
        $('#myModal #delRef').attr('href',href);
        $('#myModal').modal();
    })


    //Modal for showing traçabilité !
    $('.table .rBtn').on('click', function (event) {

        event.preventDefault();
        let href = $(this).attr('href');
        $.get(href, function (Reclamation, status) {

            $('.Tracabilite_Detail #civilityToSet ').val(Reclamation.complainer.civility); // set the id field
            $('.Tracabilite_Detail #firstNameToSet ').val(Reclamation.complainer.firstName);
            $('.Tracabilite_Detail #lastNameToSet ').val(Reclamation.complainer.lastName);
            $('.Tracabilite_Detail #cinToSet ').val(Reclamation.complainer.cin);
            $('.Tracabilite_Detail #emailToSet ').val(Reclamation.complainer.email);
            $('.Tracabilite_Detail #addressToSet ').val(Reclamation.complainer.address);
            $('.Tracabilite_Detail #phoneToSet ').val(Reclamation.complainer.phone);

            $('.Tracabilite_Detail #firstNameResToSet ').val(Reclamation.complainee.firstName);
            $('.Tracabilite_Detail #lastNameResToSet ').val(Reclamation.complainee.lastName);
            $('.Tracabilite_Detail #adressResToSet ').val(Reclamation.complainee.adress);

            $('.Tracabilite_Detail #ObejtToSet ').val(Reclamation.object);
            $('.Tracabilite_Detail #ReclToSet ').val(Reclamation.reclamation);
        })

        $('.Tracabilite_Detail #tr_detail').modal()

    });
});


