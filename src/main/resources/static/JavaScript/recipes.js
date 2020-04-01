var coll = document.getElementsByClassName("recipe-cards");
var i;

for (i = 0; i < coll.length; i++) {
  coll[i].addEventListener("click", function() {
    this.classList.toggle("active");
    var recipeDescription = this.nextElementSibling;
    if (recipeDescription.style.maxHeight){
      recipeDescription.style.maxHeight = null;
    } else {
      recipeDescription.style.maxHeight = recipeDescription.scrollHeight + "px";
    } 
  });
}