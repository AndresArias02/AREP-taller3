document.getElementById('nameForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const nombre = document.getElementById('name').value;
    const url = `/Spring/helloName?name=${encodeURIComponent(nombre)}`;

    fetch(url)
        .then(response => response.text())
        .then(data => {
            // Decodifica el nombre recibido para reemplazar %20 por espacios en blanco
            const decodedData = decodeURIComponent(data);

            // Mensaje de bienvenida
            const welcomeMessage =
                '¡Bienvenido a nuestra aplicación de gestión de productos!' +
                '<br><br>' +
                'Esta aplicación te permite crear una lista de productos pendientes por comprar. Puedes añadir una descripción a cada producto para ayudarte a tener en cuenta aspectos importantes a la hora de realizar la compra.';

            // Mensaje completo con respuesta del servidor seguido del mensaje de bienvenida
            const fullMessage = `<h2>${decodedData}<br><br>${welcomeMessage}</h2>`;

            // Actualiza el div con el mensaje completo
            document.getElementById('responseContainer').innerHTML = fullMessage;
        })
        .catch(error => {
            console.error('Error:', error);
            document.getElementById('responseContainer').innerHTML = '<h2>Hubo un error al procesar la solicitud.</h2>';
        });
});
