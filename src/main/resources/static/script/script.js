//Price Slider

rangeInput = document.querySelectorAll(".range-input input");
priceInput = document.querySelectorAll(".price-input input");
progress = document.querySelector(".price-slider .progress");

priceGap = 1000;

priceInput.forEach(input => {
  input.addEventListener("input", e => {

    let minVal = parseInt(priceInput[0].value),
      maxVal = parseInt(priceInput[1].value);

    if ((maxVal - minVal >= priceGap) && maxVal <= 10000) {
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

    console.log(minVal, maxVal)
  });
});



//Map
var map = L.map('map').setView([51.505, -0.09], 13);

L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png?{foo}', {foo: 'bar', attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'}).addTo(map);

