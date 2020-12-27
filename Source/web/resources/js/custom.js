/*------------------------------
 * Copyright 2016 Pixelized
 * http://www.pixelized.cz
 *
 * umarket theme v1.1
 ------------------------------*/

/*------------------------------
 WINDOW SCROLL
 ------------------------------*/
$(window).scroll(function () {

    /*------------------------------
     FIXED NAVBAR
     ------------------------------*/
    if ($(window).width() > 767) {
        if ($(window).scrollTop() > 165) {
            $('header.navbar-default').addClass('navbar-small');
            $("#avatarIcon").addClass("hidden");
            if ($('header.navbar-default').hasClass("navbar-static-top")) {
                $('header.navbar-default').removeClass('navbar-static-top');
                $('header.navbar-default').addClass('navbar-fixed-top');
                $('body').css("padding-top", "100px");
            }
        } else if ($(window).scrollTop() > 40) {
            $('header.navbar-default').removeClass('navbar-small');
            $("#avatarIcon").addClass("hidden");
            if ($('header.navbar-default').hasClass("navbar-static-top")) {
                $('header.navbar-default').removeClass('navbar-static-top');
                $('header.navbar-default').addClass('navbar-fixed-top');
                $('body').css("padding-top", "100px");
            }
        } else {
            $("#avatarIcon").removeClass("hidden");
            $('header.navbar-default').removeClass('navbar-fixed-top');
            $('header.navbar-default').addClass('navbar-static-top');
            $('body').css("padding-top", "0px");
        }
    } else {
        if ($(window).scrollTop()) {
            if ($('header.navbar-default').hasClass("navbar-static-top")) {
                $('header.navbar-default').addClass('navbar-offset');
                $('header.navbar-default').removeClass('navbar-static-top');
                $('header.navbar-default').addClass('navbar-fixed-top');
                $('body').css("padding-top", "60px");
            }
        }
    }

    /*------------------------------
     TRANSPARENT NAVBAR
     ------------------------------*/
    if ($(window).width() > 1199) {
        if ($(window).scrollTop() > 300) {
            $('header.navbar-transparent').addClass('navbar-offset');
        } else {
            $('header.navbar-transparent').removeClass('navbar-offset');
        }
    } else if ($(window).width() > 991) {
        if ($(window).scrollTop() > 200) {
            $('header.navbar-transparent').addClass('navbar-offset');
        } else {
            $('header.navbar-transparent').removeClass('navbar-offset');
        }
    } else if ($(window).width() > 767) {
        if ($(window).scrollTop() > 100) {
            $('header.navbar-transparent').addClass('navbar-offset');
        } else {
            $('header.navbar-transparent').removeClass('navbar-offset');
        }
    } else {
        if ($(window).scrollTop()) {
            $('header.navbar-transparent').addClass('navbar-offset');
        }
    }

    /*------------------------------
     SCROLL TOP
     ------------------------------*/
    if ($(window).scrollTop() > 300) {
        $("#scrolltop").addClass("in");
    } else {
        $("#scrolltop").removeClass("in");
    }
});

/*------------------------------
 DOCUMENT READY
 ------------------------------*/
$(document).ready(function () {

    /*------------------------------
     SCROLL FUNCTION
     ------------------------------*/
    function scrollToObj(target, offset, time) {
        $('html, body').animate({scrollTop: $(target).offset().top - offset}, time);
    }

    $("a.scroll[href^='#']").click(function () {
        scrollToObj($.attr(this, 'href'), 80, 1000);
        return false;
    });

    $("#scrolltop").click(function () {
        scrollToObj('body', 0, 1000);
    });

    /*------------------------------
     COMPARE TABLE
     ------------------------------*/
    $('#table-compare').dragtable({
        dragHandle: '.fa-arrows',
        dragaccept: '.accept'
    });

    /*------------------------------
     TOOLTIP INIT
     ------------------------------*/
    $('.widget-color .checkbox label').tooltip();

    /*------------------------------
     SCROLLSPY INIT
     ------------------------------*/
    $('body').scrollspy({target: '#scrollspy-nav', offset: 100});

    /*------------------------------
     GRID/LIST TOGGLE
     ------------------------------*/
    $('#toggle-grid').click(function (e) {
        $(this).addClass('active');
        $('#toggle-list').removeClass('active');
        $('#products').fadeOut(300, function () {
            $(this).addClass('grid').removeClass('list').fadeIn(300);
        });
    });

    $('#toggle-list').click(function (e) {
        $(this).addClass('active');
        $('#toggle-grid').removeClass('active');
        $('#products').fadeOut(300, function () {
            $(this).addClass('list').removeClass('grid').fadeIn(300);
        });
    });

    /*------------------------------
     NAVBAR SEARCH
     ------------------------------*/
    $('.navbar-search').click(function (e) {
        if ($(this).hasClass("open")) {
            $(this).find("i").removeClass("fa-times");
            $(this).find("i").addClass("fa-search");
        } else {
            $(this).find("i").removeClass("fa-search");
            $(this).find("i").addClass("fa-times");
        }
    });

    $('.navbar-search').on('hide.bs.dropdown', function () {
        $(this).find("i").removeClass("fa-times");
        $(this).find("i").addClass("fa-search");
    });

    /*------------------------------
     OWL CAROUSEL
     ------------------------------*/
    $("#homepage-1-carousel").owlCarousel({
        items: 1,
        loop: true,
        autoplay: true,
        nav: true,
        navText: ["<i class='fa fa-angle-left'></i>", "<i class='fa fa-angle-right'></i>"],
        animateOut: 'fadeOut',
        animateIn: 'fadeIn'
    });

    $("#homepage-2-carousel").owlCarousel({
        items: 1,
        loop: true,
        autoplay: true,
        nav: true,
        navText: ["<i class='fa fa-angle-left'></i>", "<i class='fa fa-angle-right'></i>"],
        animateOut: 'fadeOut',
        animateIn: 'fadeIn'
    });

    $("#homepage-3-carousel").owlCarousel({
        items: 1,
        loop: true,
        autoplay: true,
        nav: true,
        navText: ["<i class='fa fa-angle-left'></i>", "<i class='fa fa-angle-right'></i>"],
        animateOut: 'fadeOut',
        animateIn: 'fadeIn'
    });

    $("#homepage-4-carousel").owlCarousel({
        items: 1,
        loop: true,
        autoplay: true,
        animateOut: 'fadeOut',
        animateIn: 'fadeIn'
    });

    $("#homepage-6-carousel").owlCarousel({
        items: 1,
        loop: false,
        autoplay: false,
        dots: false,
    });

    $("#testimonials-carousel").owlCarousel({
        items: 1,
        loop: true,
        autoplay: true,
        animateOut: 'fadeOut',
        animateIn: 'fadeIn'
    });

    $("#blog-post-gallery").owlCarousel({
        items: 1,
        loop: true,
        nav: true,
        dots: false,
        autoplay: true,
        navText: ["<i class='fa fa-angle-left'></i>", "<i class='fa fa-angle-right'></i>"]
    });

    $("#brands-carousel").owlCarousel({
        loop: true,
        autoplayHoverPause: true,
        autoplay: true,
        autoplayTimeout: 2000,
        smartSpeed: 1000,
        dots: false,
        responsive: {
            0: {
                items: 2,
            },
            480: {
                items: 3,
            },
            600: {
                items: 4,
            },
            768: {
                items: 5,
            },
            1200: {
                items: 6,
            }
        }
    });

    $("#product-carousel").owlCarousel({
        items: 1,
        loop: true,
        animateOut: 'fadeOut',
        animateIn: 'fadeIn'
    });

    $('#product-quickview').on('shown.bs.modal', function (e) {

        $("#product-quickview").find(".product-carousel-wrapper").removeClass('hidden');

        $("#product-carousel-modal").owlCarousel({
            items: 1,
            animateOut: 'fadeOut',
            animateIn: 'fadeIn',
        });
    })

    $("#default-carousel").owlCarousel({
        items: 1,
        loop: true,
        autoplay: true,
        nav: true,
        navText: ["<i class='fa fa-angle-left'></i>", "<i class='fa fa-angle-right'></i>"]
    });

    $("#default-carousel-fade").owlCarousel({
        items: 1,
        loop: true,
        autoplay: true,
        nav: true,
        navText: ["<i class='fa fa-angle-left'></i>", "<i class='fa fa-angle-right'></i>"],
        animateOut: 'fadeOut',
        animateIn: 'fadeIn'
    });

    /*------------------------------
     PRODUCT QUANTITY
     ------------------------------*/
    $('#qty-plus').click(function (e) {
        var temp = $('#addCartForm\\:qty').val();
        $('#addCartForm\\:qty').attr("value", parseInt(temp) + 1);
    });

    $('#qty-minus').click(function (e) {
        var temp = $('#addCartForm\\:qty').val();
        if (parseInt(temp) > 1) {
            $('#addCartForm\\:qty').attr("value", parseInt(temp) - 1);
        }
    });

    $('#modal-qty-plus').click(function (e) {
        var temp = $('#modal-qty').val();
        $('#modal-qty').attr("value", parseInt(temp) + 1);
    });

    $('#modal-qty-minus').click(function (e) {
        var temp = $('#modal-qty').val();
        if (parseInt(temp) > 0) {
            $('#modal-qty').attr("value", parseInt(temp) - 1);
        }
    });

    /*------------------------------
     WIDGET - PRICE FILTER
     ------------------------------*/
    var minimum = Number($("#minPrice").val());
    var maximum = Number($("#maxPrice").val());

    $("#slider-range").slider({
        range: true,
        min: minimum,
        max: maximum,
        values: [$("#priceForm\\:from").val(), $("#priceForm\\:to").val()],
        slide: function (event, ui) {
            $("#priceForm\\:amount").val("$" + ui.values[ 0 ]);
            $("#priceForm\\:amount2").val("$" + ui.values[ 1 ]);
            $("#priceForm\\:from").val(ui.values[ 0 ]);
            $("#priceForm\\:to").val(ui.values[ 1 ]);
        },
        change: function (event, ui) {
            window.location = "products.xhtml?from=" + $("#priceForm\\:from").val() + "&to=" + $("#priceForm\\:to").val() + "&page=1&pageSize=" + $("#pageSize").val();
        }
    });

    $("#amount").val("$" + $("#slider-range").slider("values", 0));
    $("#amount2").val("$" + $("#slider-range").slider("values", 1));

    /*------------------------------
     YOUTUBE VIDEO BACKGROUND
     ------------------------------*/
    $(".player").YTPlayer();

    /*------------------------------
     TWITTER QUERY
     ------------------------------*/
    var twitterOptions = {
        "id": '541286991938457600',
        "domId": '',
        "customCallback": handleTweets,
        "maxTweets": 2,
        "enableLinks": true,
        "showUser": false,
        "showImages": false,
        "showInteraction": false
    };

    function handleTweets(tweets) {
        var n = tweets.length;
        var i = 0;
        var element = document.getElementById('twitter-wrapper');
        var html = '<ul class="list-unstyled">';
        while (i < n) {
            html += '<li>' + tweets[i] + '</li>';
            i++;
        }
        html += '</ul>';
        element.innerHTML = html;
    }

    /*twitterFetcher.fetch(twitterOptions);*/

    /*------------------------------
     GOOGLE MAP (online version)
     ------------------------------*/
//    var regions = [
//        {lat: 10.792323, lng: 106.6690623, city: 'HCM'},
//        {lat: 16.0495561, lng: 108.2111317, city: 'DN'},
//        {lat: 12.246479, lng: 109.1915943, city: 'NT'},
//        {lat: 21.031992, lng: 105.8494459, city: 'HN'}
//    ];
//
//    var storeNames = document.getElementsByName("storeName");
//    var storeAddresses = document.getElementsByName("storeAddress");
//    var storeLongs = document.getElementsByName("storeLong");
//    var storeLats = document.getElementsByName("storeLat");
//
//    var coordinates = [];
//
//    if (storeNames.length > 0) {
//        for (var i = 0; i < storeNames.length; i++) {
//            var store = [{lat: Number(storeLats[i].value), lng: Number(storeLongs[i].value)}, storeNames[i].value + ", " + storeAddresses[i].value];
//            coordinates.push(store);
//        }
//    }
//
//    var markers = [];
//
//    var map;
//
//    var zoom = 13;
//    if ($(window).width() < 768) {
//        zoom = zoom - 1;
//    }
//
//    // GOOGLE MAP INIT
//    function initialize($) {
//        var cityIndex = document.getElementById("change-region").value - 1;
//        var mapOptions = {
//            zoom: zoom,
//            center: regions[cityIndex],
//            navigationControl: false,
//            mapTypeControl: false,
//            scaleControl: false,
//            draggable: true,
//            scrollwheel: false
//        }
//        map = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);
//        google.maps.event.addListenerOnce(map, 'idle', putmarkers);
//    }
//
//    function putmarkers($) {
//        for (var i = 0; i < coordinates.length; i++) {
//            addMarkerWithTimeout(coordinates[i][0], coordinates[i][1], i + 1, i * 400);
//        }
//    }
//
//    if ($("#map-canvas").length) {
//        google.maps.event.addDomListener(window, 'load', initialize);
//    }
//
//    function addMarkerWithTimeout(position, text, store, timeout) {
//        window.setTimeout(function () {
//
//            var marker = new google.maps.Marker({
//                position: position,
//                map: map,
//                title: text,
//                url: "#marker-" + store,
//                animation: google.maps.Animation.DROP
//            });
//
//            google.maps.event.addListener(marker, 'click', function () {
//                scrollToObj(marker.url, 80, 800);
//            });
//
//            google.maps.event.addListener(marker, 'mouseover', function (event) {
//                $("#map-tooltip").html("<p>" + marker.title + "</p>");
//            });
//
//            google.maps.event.addListener(marker, 'mouseout', function (event) {
//                $("#map-tooltip").html('');
//            });
//
//            markers.push(marker);
//        }, timeout);
//    }
//
//    $('#change-region').change(function (e) {
//        var res = $(this).val();
//        map.panTo(regions[res - 1]);
//        activaTab("region-" + res.toString());
//    });
//
//    function activaTab(tab) {
//        $('#tabs-regions .nav-tabs a[href="#' + tab + '"]').tab('show');
//    };

    /*------------------------------
     GOOGLE MAP (offline version)
     ------------------------------*/
    $('#change-region').change(function (e) {
        var res = $(this).val().toString();
        activaTab("region-" + res);
        if (res === "1")
            $("#mapImages").attr('src', 'resources/images/Stores/hcmStores.JPG');
        else if (res === "2")
            $("#mapImages").attr('src', 'resources/images/Stores/dnStores.JPG');
        else if (res === "3")
            $("#mapImages").attr('src', 'resources/images/Stores/ntStores.JPG');
        else
            $("#mapImages").attr('src', 'resources/images/Stores/hnStores.JPG');
    });

    function activaTab(tab) {
        $('#tabs-regions .nav-tabs a[href="#' + tab + '"]').tab('show');
    }
    ;

    /*------------------------------
     MODAL ADVERTISING
     ------------------------------*/
    $('#modalAdvertising').on('hide.bs.modal', function (e) {
        if (typeof (Storage) !== "undefined") {
            if ($("#modal-hide").is(':checked')) {
                localStorage.setItem("modalhidden", "hidden");
            }
            ;
        }
    });

    /*------------------------------
     RATING TOOLTIP
     ------------------------------*/
    $('[data-toggle="tooltip"]').tooltip();

    /*------------------------------
     DATEPICKER
     ------------------------------*/
    $('#data_1 .input-group.date').datepicker({
        todayBtn: "linked",
        keyboardNavigation: false,
        forceParse: false,
        autoclose: true,
        format: 'dd/mm/yyyy'
    });

    $('#shipDatePicker').on('change', function (e) {
        $('#shippingInfoForm\\:shipDate').val($('#shipDatePicker').val());
    });

    /*------------------------------
     Payment Method
     ------------------------------*/
    $("#paymentForm\\:method\\:0").on("click", function (e) {
        $("#cashPayment").click();
    });

    $("#paymentForm\\:method\\:1").on("click", function (e) {
        $("#bankTransfer").click();
    });

    $("#paymentForm\\:method\\:2").on("click", function (e) {
        $("#creditCard").click();
    });

    /*------------------------------
     Pagination
     ------------------------------*/
    totalPages = $('#totalPages').val() !== undefined ? Number($('#totalPages').val()) : 1;
    startPage = $('#page').val() !== undefined ? Number($('#page').val()) : 1;
    $('#pagination-demo').twbsPagination({
        totalPages: totalPages,
        visiblePages: 5,
        prev: "Prev",
        startPage: startPage,
        initiateStartPageClick: false,
        onPageClick: function (event, page) {
            window.location = $('#redirectUrl').val() + "page=" + page + "&pageSize=" + $('#pageSize').val();
        }
    });

    $('#pageSize').on('change', function (e, ui) {
        window.location = $('#redirectUrl').val() + "page=1&pageSize=" + this.value;
    });

    /*------------------------------
     Image lazy load
     ------------------------------*/
    registerLazy();
});

/*------------------------------
 WINDOW LOAD
 ------------------------------*/
$(window).load(function () {

    /*------------------------------
     MODAL ADVERTISING
     ------------------------------*/
    if (typeof (Storage) !== "undefined") {
        if (localStorage.getItem("modalhidden") !== "hidden") {
            $('#modalAdvertising').modal('show');
        }
    } else {
        $('#modalAdvertising').modal('show');
    }
});

/*------------------------------
 CUSTOM FUNCTIONS
 ------------------------------*/
function print_window() {
    var e = window;
    e.document.close(),
            e.focus(),
            e.print(),
            e.close();
}

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

var oldQuantity, newQuantity;

function getOldQuantity(value) {
    oldQuantity = Number(value);
}

function changeQuantity(productID, newValue) {
    newQuantity = Number(newValue);
    if (newQuantity !== oldQuantity) {
        if (newQuantity < 1)
            alert("Quantity must be greater than 0.");
        else
            window.location = "cart.xhtml?productID=" + productID + "&newQuantity=" + newQuantity;
    }
}

//function validateQuantity() {
$('#checkoutCart').click(function (e) {
    if ($("input[type='number']").val() < 1) {
        alert("Quantity must be greater than 0.");
        e.preventDefault();
    }else {
        window.location = "checkout.xhtml";
    }
});
//    var aElement = document.getElementById("checkoutCart");
//    var quantity = document.querySelector("input[type='number']");
//    console.log(quantity);
//    console.log(aElement);
//    if (quantity.value < 1) {
//        alert("Quantity must be greater than 0.");
//        aElement.onclick = function (e) {
//            console.log(e.target);
//            e.preventDefault();
//            window.location = "cart.xhtml";
//        }
//    }
//    else {
//        window.location = "cart.xhtml";
//    }
//}



function showConfirm(wishlistID, isShow) {
    if (isShow) {
        $("#form" + wishlistID).removeClass("hidden");
        $("#btnRemove" + wishlistID).addClass("hidden");
    } else {
        $("#form" + wishlistID).addClass("hidden");
        $("#btnRemove" + wishlistID).removeClass("hidden");
    }
}

var instance;

function registerLazy() {
    if (instance)
        instance.destroy();

    instance = $('#lazyContainer .lazy').Lazy({
        appendScroll: $("#lazyContainer"),
        enableThrottle: true,
        throttle: 1000,
        effect: "fadeIn",
        effectTime: 1000,
        threshold: 0,
        chainable: false,
        bind: "event",
        afterLoad: function (element) {
            element.removeClass('lazy');
        }
    });

    $("#lazyContainer").trigger("scroll");
}