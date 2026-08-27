

function openDetailModal(counselorName, time, data) {
    const dateVal = document.getElementById("date-picker").value;
    document.getElementById("detail-counselor").textContent =
        counselorName;
    document.getElementById("detail-time").textContent =
        `${dateVal} ${time}`;
    document.getElementById("detail-user-name").textContent =
        data.userName;
    document.getElementById("detail-topic").textContent =
        data.topic;
    document.getElementById("detail-text").textContent =
        data.detail || "（なし）";
    document.getElementById("detail-modal").style.display = "flex";
}

function closeDetailModal() {
    document.getElementById("detail-modal").style.display = "none";
}

function handleLogout() {
    if (confirm("ログアウトしますか？"))
        window.location.href = "/login";
}
