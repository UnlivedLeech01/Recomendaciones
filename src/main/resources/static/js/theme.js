// Función para alternar el modo oscuro
function toggleDarkMode() {
    const body = document.body;
    body.classList.toggle('dark-mode');
    // Guardar el estado del tema en localStorage
    if (body.classList.contains('dark-mode')) {
        localStorage.setItem('theme', 'dark');
    } else {
        localStorage.setItem('theme', 'light');
    }
}

// Aplicar el tema guardado al cargar la página
window.onload = function() {
    const savedTheme = localStorage.getItem('theme');
    if (savedTheme === 'dark') {
        document.body.classList.add('dark-mode');
    }
};