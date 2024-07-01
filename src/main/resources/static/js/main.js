const getProducts = () => {
    return fetch("/api/products")
        .then(response => response.json());
}

const getCurrentOffer = () => {
    return fetch("/api/current-offer")
        .then(response => response.json());
}

const addProduct = (productId) => {
    return fetch(`/api/add-to-cart/${productId}`, {
        method: "POST"
    });
}

const acceptOffer = (acceptOfferRequest) => {
    return fetch("/api/accept-offer", {
        method: "POST",
        headers: {
            "Content-Type" : "application/json"
        },
        body: JSON.stringify(acceptOfferRequest)
    }).then(response => response.json());
}

const checkoutForm = document.querySelector("#checkout");
checkoutForm.addEventListener("submit", (event) => {
    event.preventDefault();

    const acceptOfferRequest = {
        firstname: checkoutForm.querySelector('input[name="firstname"]').value,
        lastname: checkoutForm.querySelector('input[name="lastname"]').value,
        email: checkoutForm.querySelector('input[name="email"]').value,
    }

    acceptOffer(acceptOfferRequest)
        .then(reservationDetails => window.location.href = reservationDetails.paymentUrl)
});

createProductHtmlEl = (productData) => {
    const template = `
        <div>
            <h4>${productData.name}</h4>
            <span>${productData.description}</span>
            <span>${productData.price}</span>
            <img src="https://www.novochag.ru/upload/img_cache/952/952b10b38ca05d74512055246534679e_ce_880x587x0x11_cropped_666x444.jpg" width=200 height=200 />
            <button data-id="${productData.id}">Add to cart</button>
        </div>
    `;
    const productEl = document.createElement('li');
    productEl.innerHTML = template.trim();

    return productEl;
}

const refreshCurrentOffer = () => {
    const totalEl = document.querySelector('#offer__total');
    const itemsCountEl = document.querySelector('#offer__itemsCount');

    getCurrentOffer()
        .then(offer => {
            totalEl.textContent = `${offer.total} PLN`;
            itemsCountEl.textContent = `${offer.itemsCount} 🛒Items`;
        });
}

const initializeCartHandler = (productHtmlEl) => {
    const addToCartBtn = productHtmlEl.querySelector("button[data-id]");
    addToCartBtn.addEventListener("click", (event) => {
        const productId = event.target.getAttribute("data-id");
        addProduct(productId)
            .then(refreshCurrentOffer());
        console.log("I going to add product: ${productId}");
    });

    return productHtmlEl;
}

document.addEventListener("DOMContentLoaded", () => {
    const productsList = document.querySelector("#productsList");
    getProducts()
        .then(productsAsJsonObj => productsAsJsonObj.map(createProductHtmlEl))
        .then(productsAsHtmlEl => productsAsHtmlEl.map(initializeCartHandler))
        .then(productsAsHtmlEl => {
            productsAsHtmlEl.forEach(productEl => productsList.appendChild(productEl));
        })
    refreshOffer();
});