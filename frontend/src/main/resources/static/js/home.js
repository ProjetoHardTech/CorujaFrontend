//side-bar
const menuItems = document.querySelectorAll('.menu-item');

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
        if(item.id != 'notificatons'){
            document.querySelector('.notification-popup').
            style.display = 'none';
        }else{
            document.querySelector('.notification-popup').
            style.display = 'block';
            document.querySelector('#notifications . notification-count').
            style.display = 'none';
        }
    })
})