//side-bar
const menuItems = document.querySelectorAll('.menu-item');

//theme
const theme = document.querySelector('#theme');
const themeModal = document.querySelector('.customize-theme');
const fontSizes = document.querySelectorAll('.choose-size span');
var root = document.querySelector(':root');

//remover as classes ativas de todas os menus
const changeActiveItem = () => {
    menuItems.forEach(item => {
        item.classList.remove('active');
    })
}

menuItems.forEach(item => {
    item.addEventListener('click', () => {
        changeActiveItem();
        item.classList.add('active');
        
        if (item.id !== 'notifications') {
            document.querySelector('.notification-popup').style.display = 'none';
        } else {
            document.querySelector('.notification-popup').style.display = 'block';
            document.querySelector('#notifications .notification-count').style.display = 'none';
        }
    });
});

const openThemeModal = () =>{
    themeModal.style.display = 'grid';
}
const closeThemeModal = (e) =>{
    if(e.target.classList.contains('customize-theme')){
        themeModal.style.display = 'none';
    }
}

themeModal.addEventListener('click', closeThemeModal);

theme.addEventListener('click', openThemeModal);

fontSizes.forEach(size => {
    let fontSize;

    size.addEventListener('click', () => {
        if(size.classList.contains('font-size-1')){
            fontSize = '10px';
            root.style.setProperty('--stick-top-left', '5.4rem');
            root.style.setProperty('--stick-top-right', '5.4rem');
        } else if(size.classList.contains('font-size-2')){
            fontSize = '13px';
            root.style.setProperty('--stick-top-left', '5.4rem');
            root.style.setProperty('--stick-top-right', '-7rem');
        }else if(size.classList.contains('font-size-3')){
            fontSize = '16px';
            root.style.setProperty('--stick-top-left', '-2rem');
            root.style.setProperty('--stick-top-right', '-17rem');
        } else if(size.classList.contains('font-size-4')){
            fontSize = '19px';
            root.style.setProperty('--stick-top-left', '-5rem');
            root.style.setProperty('--stick-top-right', '-25rem');
        } else if(size.classList.contains('font-size-5')){
            fontSize = '22px';
            root.style.setProperty('--stick-top-left', '-12rem');
            root.style.setProperty('--stick-top-right', '-35rem');
        }
        document.querySelector('html').style.fontSize = fontSize;

    });



})
