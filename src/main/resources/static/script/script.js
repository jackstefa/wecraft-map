//TODO: aggiungere riempimento categorie dinamico?
//Price Slider

let rangeInput = document.querySelectorAll(".range-input input");
let priceInput = document.querySelectorAll(".price-input input");
let progress = document.querySelector(".price-slider .progress");

let priceGap = 10;

priceInput.forEach(input => {
  input.addEventListener("input", e => {

    let minVal = parseInt(priceInput[0].value),
      maxVal = parseInt(priceInput[1].value);

    if ((maxVal - minVal >= priceGap) && maxVal <= 1000) {
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



//Map

var osmMap = L.tileLayer.provider('OpenStreetMap.Mapnik');
var stadiaMap = L.tileLayer.provider('Stadia.Outdoors');
var opentopoMap = L.tileLayer.provider('OpenTopoMap')
var imageryMap = L.tileLayer.provider('Esri.WorldImagery');

var baseMaps = {
  OSM: osmMap,
  'Stadia Outdoors' : stadiaMap,
  'Open Topo' : opentopoMap,
  'World Imagery' : imageryMap
}

var map = L.map('map', {
  center: [21.330 , 77.959],
  zoom: 5,
  layers:[osmMap]
})


//Layers

var mapLayers = L.control.layers(baseMaps).addTo(map);
var markers = L.markerClusterGroup();
map.addLayer(markers);


//Functions

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
              <p>Price: ${artisanItem.price}</p>
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

//Calls to back-end

async function showAllPoints() {

  try{
    const response = await fetch('http://localhost:8080/getAllPoints');

    if(response.status === 204) {
      showError("Nessun contenuto");
    }

    if(response.status === 500) {
      showError("Errore interno del server")
    }
    clearAllPoints();

    const data = await response.json();

    placePoints(data);

  } catch (error) {

    if (error.message === "Failed to fetch") {
      window.alert("Server non raggiungibile");
    }
  }
}


async function showFilteredPoints(){

  const catElements = document.getElementsByClassName("product-type");
  const categories = [];

  for(const catElement of catElements){

    if(catElement.checked){
      categories.push(catElement.value);
    }
  }

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
      showError("Nessun contenuto");
    }

    if(response.status === 400) {
      showError("Parametri non validi");
    }

    if(response.status === 500) {
      showError("Errore interno del server")
    }

    clearAllPoints();

    const data = await response.json();

    clearAllPoints();

    placePoints(data);

  } catch (error) {

    if (error.message === "Failed to fetch") {
      window.alert("Server non raggiungibile");
    }
  }

}

//Errors
function showError(text){

  const element = document.getElementById("error-message");
  element.textContent = text;

}

function clearError() {
  const element = document.getElementById("error-message");
  element.textContent = "";
}



window.onload = clearAllPoints();
//window.onload = populateFilters();
window.onload = showAllPoints();

const findButton = document.getElementById("findButton");
findButton.addEventListener("click", showFilteredPoints);
findButton.addEventListener("click", clearError);
