
        const counselors = [
            { id: 'c0', name: '田中 太郎', avatar: '田', role: 'キャリアコンサルタント' },
            { id: 'c1', name: '佐藤 美咲', avatar: '佐', role: 'シニアアドバイザー' },
            { id: 'c2', name: '鈴木 健二', avatar: '鈴', role: 'キャリアコンサルタント' }
        ];

        const timeSlots = ['08:00', '10:00', '11:00', '13:00', '14:00', '15:00', '16:00'];

        // 他者の予約
        const otherBookedSlots = new Set(['c0_09:00', 'c2_10:00', 'c2_15:00', 'c3_13:00']);

        // 面談不可
        const unavailableSlots = new Set(['c0_11:00', 'c2_11:00']);

        // 自分の予約
        let myReservations = {
            'c0_14:00': {
                counselorId: 'c0',
                time: '13:00',
                userName: '山田 太郎',
                topic: '職務経歴書の添削と面接対策について',
                detail: 'アピール方法についてアドバイスをいただきたいです。'
            }
        };

        let activeKey = null;

        function renderTimeline() {
            const tbody = document.getElementById('timeline-body');
            tbody.innerHTML = '';

            counselors.forEach(counselor => {
                const tr = document.createElement('tr');

                const tdCounselor = document.createElement('td');
                tdCounselor.className = 'counselor-col';
                tdCounselor.innerHTML = `
					<div class="counselor-info">
						<div class="avatar">${counselor.avatar}</div>
						<div><div class="counselor-name">${counselor.name}</div></div>
					</div>
				`;
                tr.appendChild(tdCounselor);

                timeSlots.forEach(time => {
                    const tdTime = document.createElement('td');
                    const key = `${counselor.id}_${time}`;

                    if (myReservations[key]) {
                        tdTime.className = 'slot-cell slot-mine';
                        tdTime.onclick = () => openConfirmModal(key);
                    } else if (otherBookedSlots.has(key)) {
                        tdTime.className = 'slot-cell slot-other-booked';
                    } else if (unavailableSlots.has(key)) {
                        tdTime.className = 'slot-cell slot-unavailable';
                    } else {
                        tdTime.className = 'slot-cell slot-available';
                        tdTime.onclick = () => openReserveModal(counselor, time, key);
                    }

                    tr.appendChild(tdTime);
                });

                tbody.appendChild(tr);
            });
        }

        function openReserveModal(counselor, time, key) {
            activeKey = key;
            const dateVal = document.getElementById('date-picker').value;
            document.getElementById('modal-counselor').value = counselor.name;
            document.getElementById('modal-time').value = `${dateVal} ${time}`;
            document.getElementById('reserve-topic').value = '';
            document.getElementById('reserve-detail').value = '';
            document.getElementById('reserve-modal').style.display = 'flex';
        }

        function closeReserveModal() {
            document.getElementById('reserve-modal').style.display = 'none';
            activeKey = null;
        }

        document.getElementById('reserve-form').addEventListener('submit', function (e) {
            e.preventDefault();
            const [counselorId, time] = activeKey.split('_');
            myReservations[activeKey] = {
                counselorId, time,
                userName: document.getElementById('user-name').value,
                topic: document.getElementById('reserve-topic').value,
                detail: document.getElementById('reserve-detail').value
            };
            closeReserveModal();
            renderTimeline();
        });

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
                closeConfirmModal();
                renderTimeline();
            }
        }

        function handleLogout() {
            if (confirm('ログアウトしますか？')) window.location.href = '/login';
        }

        renderTimeline();
