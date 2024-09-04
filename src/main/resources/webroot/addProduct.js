
// scripts.js

// Cargar productos actuales en el carrito
function loadGetMsg() {
    fetch('/app/getProducts')
        .then(response => response.json())
        .then(data => {
            const productList = document.getElementById("product-list");
            productList.innerHTML = ""; // Limpiar la lista antes de agregar nuevos elementos

            if (data.products && data.products.length > 0) {
                data.products.forEach(product => {
                    const productItem = document.createElement("div");
                    productItem.className = "product-item";

                    const productText = document.createElement("span");
                    productText.className = "product-name";
                    productText.textContent = product;

                    const deleteButton = document.createElement("button");
                    deleteButton.textContent = "Delete";
                    deleteButton.className = "delete-button";
                    deleteButton.onclick = () => deleteProduct(product);

                    productItem.appendChild(productText);
                    productItem.appendChild(deleteButton);
                    productList.appendChild(productItem);
                });
            } else {
                productList.innerHTML = "<p>No hay productos en la lista</p>";
            }
        })
        .catch(error => {
            console.error("Error fetching products:", error);
            document.getElementById("getrespmsg").textContent = "Failed to load products.";
        });
}

// Agregar producto al carrito
function loadPostMsg(event) {
    event.preventDefault(); // Prevenir la recarga de la página

    const name = document.getElementById("postname").value;
    const description = document.getElementById("description").value;
    let url = `/app/addProduct?name=${encodeURIComponent(name)}&descr=${encodeURIComponent(description)}`;

    fetch(url, { method: 'POST' })
        .then(response => response.json()) // Parsear la respuesta como JSON
        .then(data => {
            showPopup(data.message); // Mostrar el mensaje en un popup
            loadGetMsg(); // Recargar la lista de productos después de agregar uno nuevo
        })
        .catch(error => {
            console.error("Error al añadir producto:", error);
            showPopup("Error al añadir producto."); // Mostrar error en el popup
        });
}

// Función para eliminar un producto
function deleteProduct(productName) {
    const url = `/app/deleteProduct?name=${encodeURIComponent(productName)}`;

    fetch(url, { method: 'DELETE' })
        .then(response => response.json())
        .then(data => {
            showPopup(data.message); // Mostrar el mensaje en un popup
            loadGetMsg(); // Recargar la lista de productos después de eliminar uno
        })
        .catch(error => {
            console.error("Error deleting product:", error);
            showPopup("Error al eliminar producto."); // Mostrar error en el popup
        });
}


// Función para mostrar el popup
function showPopup(message) {
    const popup = document.getElementById("postrespmsg");
    popup.textContent = message;
    popup.style.display = 'block'; // Mostrar el popup

    // Ocultar el popup después de 3 segundos
    setTimeout(() => {
        popup.style.display = 'none';
    }, 3000);
}

// Asocia el evento de envío del formulario a la función loadPostMsg
document.getElementById("addProductForm").addEventListener("submit", loadPostMsg);

// Cargar productos al cargar la página
window.onload = loadGetMsg;

