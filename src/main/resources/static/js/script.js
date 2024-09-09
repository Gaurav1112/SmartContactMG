console.log("Script loaded");

let currentTheme = getTheme();
//initially 
document.addEventListener("DOMContentLoaded", () => {

  changeTheme();
});


function changeTheme(currentTheme){

  let changeThemeButton = document.querySelector('#theme_change_button');
  changeThemeButton.querySelector('span').innerHTML = currentTheme == "light" ? "Dark" : "Light";
  changeThemeButton.addEventListener("click", () => {
    console.log("Button Click");
    //remove the current theme
 
    document.querySelector('html').classList.remove(currentTheme);
    if (currentTheme == "dark"){
      currentTheme = "light";
    } else {
      currentTheme = "dark";
    }
    //local Storage update
    setTheme(currentTheme);
    document.querySelector('html').classList.add(currentTheme);
    //change the teext
    changeThemeButton.querySelector('span').innerHTML = (currentTheme == "light") ? "Dark" : "Light";
  })
}

//set theme to local storage

function setTheme(theme){
  localStorage.setItem("theme", theme);
}


// get theme form local storage
function getTheme(){
  let theme = localStorage.getItem("theme");
  return theme ? theme : "light";
}