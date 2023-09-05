function clickMenu() {

    icon = document.getElementById("burguer");
    logo = document.getElementById("logo");
    if (menuBurguer.style.display == 'flex') {
        menuBurguer.style.display = 'none';
        enableScroll();
        document.querySelector('.icon').src = "images/coruja_images/index/menu_white_36dp (1).svg";

    } else {
        disableScroll();
        menuBurguer.style.display = 'flex';
        document.querySelector('.icon').src = "images/coruja_images/index/close_white_36dp (1).svg";
        ;
    }
}

function disableScroll() {
    document.body.style.overflowY = 'hidden';
}

function enableScroll() {
    document.body.style.overflowY = 'auto';
}

function clickOption() {
    menuBurguer.style.display = 'none';
    document.querySelector('.icon').src = "images/coruja_images/index/menu_white_36dp (1).svg";
    enableScroll();
}
