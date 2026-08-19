

        function openReserveModal(ev) {
            const dateVal = document.getElementById('date-picker').value;
            document.getElementById('modal-counselor').value = counselor.name;
            document.getElementById('modal-time').value = `${dateVal} ${time}`;
            document.getElementById('reserve-topic').value = '';
            document.getElementById('reserve-detail').value = '';
            document.getElementById('reserve-modal').style.display = 'flex';
        document.getElementById('reserve-form').addEventListener('submit', function (e) {
            e.preventDefault();
            const [counselorId, time] = activeKey.split('_');
            myReservations[activeKey] = {
                counselorId, time,
                userName: document.getElementById('user-name').value,
                topic: document.getElementById('reserve-topic').value,
                detail: document.getElementById('reserve-detail').value
            };
            closeReserveModal();;
        });
        }

        function closeReserveModal() {
            document.getElementById('reserve-modal').style.display = 'none';
            activeKey = null;
        }


        function openConfirmModal(key) {
            activeKey = key;
            const res = myReservations[key];
            const counselor = counselors.find(c => c.id === res.counselorId);
            const dateVal = document.getElementById('date-picker').value;

            document.getElementById('confirm-counselor').textContent = counselor.name;
            document.getElementById('confirm-time').textContent = `${dateVal} ${res.time}`;
            document.getElementById('confirm-user-name').textContent = res.userName;
            document.getElementById('confirm-topic').textContent = res.topic;
            document.getElementById('confirm-detail').textContent = res.detail || '（なし）';
            document.getElementById('confirm-modal').style.display = 'flex';
        }

        function closeConfirmModal() {
            document.getElementById('confirm-modal').style.display = 'none';
            activeKey = null;
        }

        function handleCancelReservation() {
            if (confirm('予約をキャンセルしますか？')) {
                delete myReservations[activeKey];
                closeConfirmModal();;
            }
        }

        function handleLogout() {
            if (confirm('ログアウトしますか？')) window.location.href = '/login';
        }

