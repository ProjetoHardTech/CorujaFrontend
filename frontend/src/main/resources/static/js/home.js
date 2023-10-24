//side-bar
const menuItems = document.querySelectorAll('.menu-item');


//theme change 
const themeBtn = document.querySelector('.theme-btn');

themeBtn.addEventListener('click',() => {

    document.body.classList.toggle('dark-theme');

    themeBtn.querySelector('span:first-child').classList.toggle('active');
    themeBtn.querySelector('span:last-child').classList.toggle('active');
    



})
//theme
const theme = document.querySelector('#theme');
const themeModal = document.querySelector('.customize-theme');
const fontSizes = document.querySelectorAll('.choose-size span');
var root = document.querySelector(':root');


// trunk-ignore(git-diff-check/error)
const colorPalette = document.querySelectorAll('.choose-color span'); 
const Bg1 = document.querySelector('.bg-1');
const Bg2 = document.querySelector('.bg-2');
const Bg3 = document.querySelector('.bg-3');


//aba de post
const wrapper = document.querySelector(".wrapper"),
editableInput = wrapper.querySelector(".editable"),
readonlyInput = wrapper.querySelector(".readonly"),
placeholder = wrapper.querySelector(".placeholder"),
counter = wrapper.querySelector(".counter"),
button = wrapper.querySelector("button");

//remover as classes ativas de todas os menus
function changeActiveItem() {
    menuItems.forEach(item => {
        item.classList.remove('active');
    });
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

const removeSizeSelector = ()=> {
    fontSizes.forEach(size => {
        size.classList.remove('active');
    })
}

fontSizes.forEach(size => {
    size.addEventListener('click', () => {
        removeSizeSelector();
        let fontSize;
        // trunk-ignore(git-diff-check/error)
        size.classList.toggle('active'); 

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
        //mudar a fonte do root 
        document.querySelector('html').style.fontSize = fontSize;

    });
})

const  changeActiveCollorClass = () => {
    colorPalette.forEach(colorPicker => {
        colorPicker.classList.remove('active');
    })
}


//mudrr cor primaria
colorPalette.forEach(color => {
    color.addEventListener('click', () => {
        let primaryHue;
        changeActiveCollorClass();
        
        if(color.classList.contains('color-1')){
            primaryHue = 252;
        }else if(color.classList.contains('color-2')) {
            primaryHue = 52;
        }else if(color.classList.contains('color-3')) {
            primaryHue = 352;
        }else if(color.classList.contains('color-4')) {
            primaryHue = 152;
        }else if(color.classList.contains('color-5')) {
            primaryHue = 202;
        }
        color.classList.add('active');

        root.style.setProperty('--primary-color-hue', primaryHue);
    })
})

    let lightColorLightness;
    let whiteColorLightness;
    let darkColorLightness;

    const changeBG = () => {
        root.style.setProperty('--light-color-lightness', lightColorLightness);
        root.style.setProperty('--white-color-lightness', whiteColorLightnesstColorLightness);
        root.style.setProperty('--dark-color-lightness', darkColorLightnessColorLightness);
    }
    Bg1.addEventListener('click', () => {
        darkColorLightness = '95%';
        whiteColorLightness = '20%';
        lightColorLightness = '15%';

        Bg1.classList.add('active');
        Bg2.classList.remove('active');
        Bg3.classList.remove('active');
        window.location.reload();
    });

    Bg2.addEventListener('click', () => {
        darkColorLightness = '95%';
        whiteColorLightness = '20%';
        lightColorLightness = '15%';

        Bg2.classList.add('active');
        Bg1.classList.remove('active');
        Bg3.classList.remove('active');
        changeBG();
    });

    Bg3.addEventListener('click', () => {
        darkColorLightness = '95%';
        whiteColorLightness = '20%';
        lightColorLightness = '15%';

        Bg3.classList.add('active');
        Bg1.classList.remove('active');
        Bg2.classList.remove('active');
        changeBG();
 })
 //aba de post 
 editableInput.onfocus = ()=>{
    placeholder.style.color = "#c5ccd3";
  }
  editableInput.onblur = ()=>{
    placeholder.style.color = "#98a5b1";
  }
  editableInput.onkeyup = (e)=>{
    let element = e.target;
    validated(element);
  }
  editableInput.onkeypress = (e)=>{
    let element = e.target;
    validated(element);
    placeholder.style.display = "none";
  }
  function validated(element){
    let text;
    let maxLength = 100;
    let currentlength = element.innerText.length;
    if(currentlength <= 0){
      placeholder.style.display = "block";
      counter.style.display = "none";
      button.classList.remove("active");
    }else{
      placeholder.style.display = "none";
      counter.style.display = "block";
      button.classList.add("active");
    }
    counter.innerText = maxLength - currentlength;
    if(currentlength > maxLength){
      let overText = element.innerText.substr(maxLength); //extracting over texts
      overText = `<span class="highlight">${overText}</span>`; //creating new span and passing over texts
      text = element.innerText.substr(0, maxLength) + overText; //passing overText value in textTag variable
      readonlyInput.style.zIndex = "1";
      counter.style.color = "#e0245e";
      button.classList.remove("active");
    }else{
      readonlyInput.style.zIndex = "-1";
      counter.style.color = "#333";
    }
    readonlyInput.innerHTML = text; //replacing innerHTML of readonly div with textTag value
  }