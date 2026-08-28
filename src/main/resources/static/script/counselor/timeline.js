function openReservationModal(ev) {
    const dataset = ev.currentTarget.dataset;
    document.getElementById("reseevation-counselor").textContent = dataset.counselorName;
    document.getElementById("reseevation-datetime").textContent = dataset.datetime;
    document.getElementById("reseevation-student-name").textContent = dataset.studentName;
    document.getElementById("reseevation-summary").textContent = dataset.summary;
    document.getElementById("reseevation-detail").textContent = dataset.detail;
    document.getElementById("reseevation-modal").style.display = "flex";
}

function closeReservationModal() {
    document.getElementById("reseevation-modal").style.display = "none";
}

function handleLogout() {
    if (confirm("ログアウトしますか？")) window.location.href = "/login";
}
