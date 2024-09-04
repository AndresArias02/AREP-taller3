
document.getElementById('nameForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const nombre = document.getElementById('name').value;
    const url = `/Spring/helloName?name=${encodeURIComponent(nombre)}`;

    fetch(url)
        .then(response => response.text())
        .then(data => {
            document.getElementById('visualName').innerText = data;
        })
        .catch(error => {
            console.error('Error:', error);
            document.getElementById('visualName').innerText = 'Hubo un error al procesar la solicitud.';
        });
});