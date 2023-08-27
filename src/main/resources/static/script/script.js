//TODO: aggiungere riempimento categorie dinamico?
//Price Slider

rangeInput = document.querySelectorAll(".range-input input");
priceInput = document.querySelectorAll(".price-input input");
progress = document.querySelector(".price-slider .progress");

priceGap = 10;

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

function makePopupContent(mapPoint){
  return `
    <div>
      <h3>${mapPoint.itemName} </h3>
      <p> ${mapPoint.informations}</p>
      <p> Prezzo: ${mapPoint.price}€</p>
      <p> Quantità: ${mapPoint.quantity}</p>
      <p> Artigiano: ${mapPoint.artisanName}</p>
      <div class="image-container">
        <img src="./img/${mapPoint.imageid}.jpg" alt="">
      </div><br>
      <h4>Contact:</h4>
      <div class = "email">
        <a href="mailto:${mapPoint.email}">${mapPoint.email}</a>
      </div>
      <div class = "phone-number">
        <a href="tel:${mapPoint.phoneNumber}">${mapPoint.phoneNumber}</a>
      </div>
    </div>

`}

function clearAllPoints() {

  markers.clearLayers();
}

function placePoints(data){

  for (const mapPoint of data.mapPoints ) {
    markers.addLayer(L.marker([mapPoint.latitude , mapPoint.longitude]).bindPopup(makePopupContent(mapPoint)));
  }
}

async function showAllPoints() {

  try{
    const response = await fetch('http://localhost:8080/getAllPoints');

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

    placePoints(data);

  } catch (error) {

    if (error.message === "Failed to fetch") {
      window.alert("Server non raggiungibile");
    }
  }
}


async function showPoints(){

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
    const response = await fetch("http://localhost:8080/getPoints", {
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
window.onload = showAllPoints();

const findButton = document.getElementById("findButton");
findButton.addEventListener("click", showPoints);
findButton.addEventListener("click", clearError);
