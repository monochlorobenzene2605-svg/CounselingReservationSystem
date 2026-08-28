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

function handleRegisterUnavailable(ev) {
    const dataset = ev.currentTarget.dataset;
    const date = dataset.datetime;
    if(confirm("予約不可として登録しますか？")){
        const form = ev.currentTarget.querySelector(".unavailable-register");
        form.querySelector("input.date").value = date.replace(/\//g, "-").replace(" ", "T"); // "yyyy/mm/dd hh:mm" → "yyyy-mm-ddThh:mm"に変換
        form.submit();
    }
}
