function openReserveModal(ev) {
    const dataset = ev.currentTarget.dataset;
    const dateVal = document.getElementById("date-picker").value;

    document.getElementById("modal-counselor-id").value = dataset.counselorId;
    document.getElementById("modal-counselor").value = dataset.counselorName;
    document.getElementById("modal-time").value = dateVal + " " + dataset.time;
    document.getElementById("student-name").value = dataset.studentName;
    document.getElementById("reserve-summary").value = "";
    document.getElementById("reserve-detail").value = "";

    document.getElementById("reserve-modal").style.display = "flex";
}

function closeReserveModal() {
    document.getElementById("reserve-modal").style.display = "none";
}

function openConfirmModal(ev) {
    const dataset = ev.currentTarget.dataset;
    const dateVal = document.getElementById("date-picker").value;

    document.getElementById("modal-reservation-id").value = dataset.reservationId;
    document.getElementById("confirm-counselor").textContent = dataset.counselorName;
    document.getElementById("confirm-time").textContent = dateVal + " " + dataset.time;
    document.getElementById("confirm-user-name").textContent = dataset.studentName;
    document.getElementById("confirm-summary").textContent = dataset.summary;
    document.getElementById("confirm-detail").textContent = dataset.detail;

    document.getElementById("confirm-modal").style.display = "flex";
}

function closeConfirmModal() {
    document.getElementById("confirm-modal").style.display = "none";
}

function handleCancelReservation() {
    if (confirm("予約をキャンセルしますか？")) {
        document.getElementById("cancel-form").submit();
    }
}

function handleLogout() {
    if (confirm("ログアウトしますか？")) window.location.href = "/login";
}
