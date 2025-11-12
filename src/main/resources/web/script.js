// Sample data for Upcoming Returns
const returnData = [
    {
        borrowId: "101",
        fullName: "Alice Johnson",
        bookTitle: "Data Structure 101",
        borrowedDate: "2025-10-01",
        dueDate: "2025-10-15",
        status: "Active"
    },
    {
        borrowId: "102",
        fullName: "John Smith",
        bookTitle: "Intro to Python",
        borrowedDate: "2025-10-02",
        dueDate: "2025-10-16",
        status: "Active"
    },
    {
        borrowId: "103",
        fullName: "Emma Lee",
        bookTitle: "AI in Libraries",
        borrowedDate: "2025-10-03",
        dueDate: "2025-10-17",
        status: "Active"
    },
    {
        borrowId: "104",
        fullName: "Liam Patel",
        bookTitle: "C Programming Basics",
        borrowedDate: "2025-10-04",
        dueDate: "2025-10-18",
        status: "Active"
    }
];

// Sample data for Fines
const finesData = [
    {accountId: "A001", fullName: "John Smith", numberOfFines: 4, fines: "$20.00", lastFineDate: "2025-10-20"},
    {accountId: "A002", fullName: "Alice Johnson", numberOfFines: 3, fines: "$15.00", lastFineDate: "2025-10-19"},
    {accountId: "A003", fullName: "Emma Lee", numberOfFines: 2, fines: "$10.00", lastFineDate: "2025-10-21"},
    {accountId: "A004", fullName: "Liam Patel", numberOfFines: 1, fines: "$5.00", lastFineDate: "2025-10-22"}
];

// Sample data for Genre Popularity
const genreData = [
    {genre: "Technology", borrowed: 14},
    {genre: "Science", borrowed: 9},
    {genre: "Fiction", borrowed: 22},
    {genre: "History", borrowed: 5},
    {genre: "Biography", borrowed: 11}
];

// Sample data for Author Popularity
const authorData = [
    {author: "Dan Brown", borrowed: 10},
    {author: "Jane Austen", borrowed: 8},
    {author: "Yuval Noah Harari", borrowed: 12},
    {author: "Robert C. Martin", borrowed: 6},
    {author: "J.K. Rowling", borrowed: 15}
];

// Generic function to load data into any table
function loadTableData(tableId, dataArray) {
    const tbody = document.querySelector(`#${tableId} tbody`);
    dataArray.forEach(item => {
        const row = document.createElement("tr");
        row.innerHTML = Object.values(item).map(val => `<td>${val}</td>`).join("");
        tbody.appendChild(row);
    });
}

window.addEventListener("DOMContentLoaded", () => {
    loadTableData("returnsTable", returnData);
    loadTableData("finesTable", finesData);
    loadTableData("genreTable", genreData);
    loadTableData("authorTable", authorData);
});
