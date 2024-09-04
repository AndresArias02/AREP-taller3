// Función para cargar productos desde /Spring/products
function loadProducts() {
    fetch('/Spring/products')
        .then(response => response.json())
        .then(data => {
            const productList = document.getElementById("product-list");
            productList.innerHTML = ""; // Limpiar la lista antes de agregar nuevos elementos

            if (Array.isArray(data) && data.length > 0) {
                data.forEach(product => {
                    const productItem = document.createElement("div");
                    productItem.className = "product-item";

                    const productName = document.createElement("span");
                    productName.className = "product-name";
                    productName.textContent = `Name: ${product.name}`;

                    const productDescription = document.createElement("p");
                    productDescription.className = "product-description";
                    productDescription.textContent = `Description: ${product.description}`;

                    productItem.appendChild(productName);
                    productItem.appendChild(productDescription);
                    productList.appendChild(productItem);
                });
            } else {
                productList.innerHTML = "<p>No products available.</p>";
            }
        })
        .catch(error => {
            console.error("Error fetching products:", error);
            document.getElementById("getrespmsg").textContent = "Failed to load products.";
        });
}

// Cargar productos al cargar la página
window.onload = loadProducts;