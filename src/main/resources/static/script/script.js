/* MAPS */ 
var osmMap = L.tileLayer.provider('OpenStreetMap.Mapnik');
var stadiaMap = L.tileLayer.provider('Stadia.Outdoors');
var imageryMap = L.tileLayer.provider('Esri.WorldImagery');

var baseMaps = {
  OSM: osmMap,
  'Stadia Outdoors' : stadiaMap,
  'World Imagery' : imageryMap
}

var map = L.map('map', {
  center: [21.330 , 77.959],
  zoom: 5,
  layers:[osmMap]
})


/* LAYERS */

var mapLayers = L.control.layers(baseMaps).addTo(map);
var markers = L.markerClusterGroup();
map.addLayer(markers);


/* FUNCTIONS */
function makePopupContent(artisan){

  var htmlString = `
    <div>
      <h2>${artisan.artisanName}</h2>
      </div>
      <h3>Contact:</h3>
      <div class = "email">
        <a href="mailto:${artisan.email}">${artisan.email}</a>
      </div>
      <div class = "phone-number">
        <a href="tel:${artisan.phoneNumber}">${artisan.phoneNumber}</a>
      </div> 
      <br>
      
      <div class="items-container">
    `

  for(const artisanItem of artisan.itemsList){

    htmlString = htmlString + `

        
          <div class="item-container">
            <div class="item-details-container">
              <h3>${artisanItem.itemName}</h3>
              <h4>${artisanItem.itemDescription}</h4>
              <p>Price: ${artisanItem.price}€</p>
              <p>Category: ${artisanItem.category}</p>
              <p>Quantity: ${artisanItem.quantity}</p>
            </div>
            <div class="image-container">
              <img src="./img/${artisanItem.imageid}.jpg" alt="">
            </div>
            <hr class="items-separator">
          </div>
        
  `
  }

  htmlString = htmlString + `</div>`

  return htmlString;
}

function clearAllPoints() {
  markers.clearLayers();
}

function placePoints(data){
  
  for (const artisan of data.artisans ) {
    markers.addLayer(L.marker([artisan.latitude , artisan.longitude]).bindPopup(makePopupContent(artisan), {maxWidth : 400}));
  }
}

/* CALLS TO BACK-END */

async function showAllPoints() {

  try{
    const response = await fetch('http://localhost:8080/getAllPoints');

    if(response.status === 204) {
      showError("No content");
    }

    if(response.status === 500) {
      showError("Internal server error");
    }
    clearAllPoints();

    const data = await response.json();

    placePoints(data);

  } catch (error) {

    if (error.message === "Failed to fetch") {
      window.alert("Connection error");
    }
  }
}


async function showFilteredPoints(){

  //getting checkboxes
  const catElements = document.getElementsByClassName("product-type");
  const categories = [];

  //checking if are checked or not
  for(const catElement of catElements){
    if(catElement.checked){
      categories.push(catElement.value);
    }
  }

  //if all are unchecked, it means all categories selected
  if(categories.length === 0){
    for(const catElement of catElements){
        categories.push(catElement.value);
    }
  }

  //building body
  const filter = {
    "priceMin": document.getElementById("priceMin").value,
    "priceMax": document.getElementById("priceMax").value,
    "categories": categories
  }


  try {
    const response = await fetch("http://localhost:8080/getFilteredPoints", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(filter),
    });

    if(response.status === 204) {
      showError("No contents");
    }

    if(response.status === 400) {
      showError("Parameters not valid");
    }

    if(response.status === 500) {
      showError("Internal server error")
    }

    clearAllPoints();

    const data = await response.json();

    if(data.artisans.length === 0){
      showMessage("No results");
    }
    else{
      placePoints(data);
    }


  } catch (error) {

    if (error.message === "Failed to fetch") {
      window.alert("Connection error");
    }
  }

}


//Adding filter entries from back-end
async function populateFilters(){

  try{

    const response = await fetch('http://localhost:8080/getFilters');

    if(response.status === 500) {
      showError("Internal server error");
    }

    const data = await response.json();

    setPriceRange(data.filters.priceMin, data.filters.priceMax);
    setCategories(data.filters.categories);


  } catch (error) {

    if (error.message === "Failed to fetch") {
      window.alert("Connection error");
    }
  }
}

//setting price range values
function setPriceRange(priceMin, priceMax){

  let priceMinField = document.getElementById("priceMin-field");
  let priceMaxField = document.getElementById("priceMax-field");
  let priceMinRange = document.getElementById("priceMin");
  let priceMaxRange = document.getElementById("priceMax");

  priceMinField.setAttribute("value", priceMin);
  priceMinField.setAttribute("min", priceMin);
  priceMinField.setAttribute("max", priceMax);

  priceMaxField.setAttribute("value", priceMax);
  priceMaxField.setAttribute("min", priceMin);
  priceMaxField.setAttribute("max", priceMax);

  priceMinRange.setAttribute("min", priceMin);
  priceMinRange.setAttribute("max", priceMax);
  priceMinRange.setAttribute("value", priceMin);

  priceMaxRange.setAttribute("min", priceMin);
  priceMaxRange.setAttribute("max", priceMax);
  priceMaxRange.setAttribute("value", priceMax);

  //Price Slider
  let priceGap = 10;

  let rangeInput = document.querySelectorAll(".range-input input");
  let priceInput = document.querySelectorAll(".price-input input");
  let progress = document.querySelector(".price-slider .progress");

  priceInput.forEach(input => {
    input.addEventListener("input", e => {

      let minVal = parseInt(priceInput[0].value),
          maxVal = parseInt(priceInput[1].value);

      if ((maxVal - minVal >= priceGap) && maxVal <= priceMax) {
        if (e.target.className === "price-input-min") {
          rangeInput[0].value = minVal;
          progress.style.left = (minVal / rangeInput[0].max) * 100 + "%";
        }else{
          rangeInput[1].value = maxVal;
          progress.style.right = 100 - (maxVal / rangeInput[1].max) * 100 + "%";
        }
      }
    });
  });


  rangeInput.forEach(input => {
    input.addEventListener("input", e => {

      let minVal = parseInt(rangeInput[0].value),
          maxVal = parseInt(rangeInput[1].value);

      if (maxVal - minVal < priceGap) {
        if (e.target.className === "range-min") {
          rangeInput[0].value = maxVal - priceGap;
        }else{
          rangeInput[1].value = minVal + priceGap;
        }

      } else {
        priceInput[0].value = minVal;
        priceInput[1].value = maxVal;
        progress.style.left = (minVal / rangeInput[0].max) * 100 + "%";
        progress.style.right = 100 - (maxVal / rangeInput[1].max) * 100 + "%";
      }
    });
  });

}

//setting checkboxes and values dynamically
function setCategories(categories){

  let cnt= 1

  for(const category of categories) {


    var ulElement = document.getElementById("categories-container");

    var liElement = document.createElement("li");

    var divElement = document.createElement("div");
    divElement.className = "checkbox-wrapper-1";

    var inputElement = document.createElement("input");
    inputElement.id = "type"+cnt;
    inputElement.className = "product-type";
    inputElement.type = "checkbox";
    inputElement.value = category["id"];
    inputElement.setAttribute("aria-hidden", "true");
    inputElement.setAttribute("autocomplete", "off");

    var labelElement = document.createElement("label");
    labelElement.setAttribute("for", "type"+cnt);
    labelElement.textContent = category["name"];

    divElement.appendChild(inputElement);
    divElement.appendChild(labelElement);

    liElement.appendChild(divElement);

    ulElement.appendChild(liElement);

    cnt++;
  }

}

/* ERRORS */
function showError(text){
  const element = document.getElementById("error-message");
  element.textContent = text;

}

function clearError() {
  const element = document.getElementById("error-message");
  element.textContent = "";
}

/* MESSAGES */
function showMessage(text){
  const element = document.getElementById("nocontent-message");
  element.textContent = text;
}

function clearMessage(){
  const element = document.getElementById("nocontent-message");
  element.textContent = "";
}

function centerMap(){
  const center = [21.330 , 77.959];
  const zoom = 5;
  map.setView(center, zoom);
}


window.onload = clearAllPoints();
window.onload = populateFilters();
window.onload = showAllPoints();

const findButton = document.getElementById("findButton");
findButton.addEventListener("click", showFilteredPoints);
findButton.addEventListener("click", clearError);
findButton.addEventListener("click", clearMessage);
findButton.addEventListener("click", centerMap);
