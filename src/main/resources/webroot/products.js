// Función para cargar productos desde /Spring/products
function loadProducts() {
    fetch('/Spring/products')
        .then(response => response.json())
        .then(data => {
            const productList = document.getElementById("product-list");
            productList.innerHTML = ""; // Limpiar la lista antes de agregar nuevos elementos

            if (Array.isArray(data) && data.length > 0) {
                data.forEach((product, index) => {
                    const productItem = document.createElement("tr"); // Cambiado a 'tr' para las filas de la tabla

                    const productIndex = document.createElement("td");
                    productIndex.textContent = index + 1; // Numeración de productos

                    const productName = document.createElement("td");
                    productName.textContent = product.name; // Nombre del producto

                    const productDescription = document.createElement("td");
                    productDescription.textContent = product.description; // Descripción del producto

                    productItem.appendChild(productIndex);
                    productItem.appendChild(productName);
                    productItem.appendChild(productDescription);
                    document.getElementById("product-list").appendChild(productItem);
                });
            } else {
                productList.innerHTML = "<tr><td colspan='3'>No hay productos agregados.</td></tr>"; // Mensaje en la tabla
            }
        })
        .catch(error => {
            console.error("Error fetching products:", error);
            document.getElementById("getrespmsg").textContent = "Error al agregar productos.";
        });
}

// Cargar productos al cargar la página
window.onload = loadProducts;
