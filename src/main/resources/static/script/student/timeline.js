function openReserveModal(ev) {
    const dataset = ev.currentTarget.dataset;
    const dateVal = document.getElementById("date-picker").value;
    document.getElementById("modal-counselor").value = dataset.counselorName;
    document.getElementById("modal-time").value =
        dateVal + " " + dataset.time;
    document.getElementById("reserve-summary").value = "";
    document.getElementById("reserve-detail").value = "";
    document.getElementById("reserve-modal").style.display = "flex";
}

function closeReserveModal() {
    document.getElementById("reserve-modal").style.display = "none";
    activeKey = null;
}

function openConfirmModal(key) {
    activeKey = key;
    const res = myReservations[key];
    const counselor = counselors.find((c) => c.id === res.counselorId);
    const dateVal = document.getElementById("date-picker").value;

    document.getElementById("confirm-counselor").textContent = counselor.name;
    document.getElementById("confirm-time").textContent =
        `${dateVal} ${res.time}`;
    document.getElementById("confirm-user-name").textContent = res.userName;
    document.getElementById("confirm-summary").textContent = res.summary;
    document.getElementById("confirm-detail").textContent =
        res.detail || "（なし）";
    document.getElementById("confirm-modal").style.display = "flex";
}

function closeConfirmModal() {
    document.getElementById("confirm-modal").style.display = "none";
    activeKey = null;
}

function handleCancelReservation() {
    if (confirm("予約をキャンセルしますか？")) {
        delete myReservations[activeKey];
        closeConfirmModal();
    }
}

function handleLogout() {
    if (confirm("ログアウトしますか？")) window.location.href = "/login";
}
