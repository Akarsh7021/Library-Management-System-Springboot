// script.js (safe version)

function loadTableDataIfExists(tableId, dataArray) {
    const table = document.getElementById(tableId);
    if (!table) return;
    const tbody = table.querySelector('tbody');
    if (!tbody) return;
    tbody.innerHTML = ""; // clear previous
    dataArray.forEach(item => {
        const row = document.createElement("tr");
        const cells = Object.values(item).map(val => `<td>${val}</td>`).join("");
        row.innerHTML = cells;
        tbody.appendChild(row);
    });
}

const returnData = [ /* ... */];
const finesData = [ /* ... */];
const genreData = [ /* ... */];
const authorData = [ /* ... */];

window.addEventListener("DOMContentLoaded", () => {
    loadTableDataIfExists("returnsTable", returnData);
    loadTableDataIfExists("finesTable", finesData);
    loadTableDataIfExists("genreTable", genreData);
    loadTableDataIfExists("authorTable", authorData);
});
