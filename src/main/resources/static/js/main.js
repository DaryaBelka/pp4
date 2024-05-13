getProducts = () => {
    return fetch("/api/products")
        .then(response => response.json());
}

getCurrentOffer = () => {
    return fetch("/api/current-offer")
         .then(response => response.json());
    }


const createProductHtml = (productData) => {
    const template = `
        <div>
            <h4>${productData.name}</h4>
            <span>${productData.price}</span>
            <img src = "https://images.prismic.io/carwow/e2dbfc3f-127b-4de0-a839-e082dc488eb4_RHD+BMW+M8+Exterior+2.jpg"/>
            <button data-id="${productData.id}">Add to cart</button>
        </div>
    `
    const productEl = document.createElement("li");
    productEl.innerHTML = template.trim();

    return productEl;
}

document.addEventListener("DOMContentLoaded", () => {
    const productsListEl = document.querySelector("#productsList");
    getProducts()
        .then(productsAsJsonObj => productsAsJsonObj.map(createProductHtml))
        .then(productsAsHtmlEl => {
            productsAsHtmlEl.forEach(productEl => productsListEl.appendChild(productEl))
        })
});