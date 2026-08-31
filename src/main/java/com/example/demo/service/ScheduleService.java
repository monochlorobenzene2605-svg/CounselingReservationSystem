package com.example.demo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.dto.SlotDto;
import com.example.demo.dto.TimelineDto;
import com.example.demo.entity.Reservation;
import com.example.demo.entity.SlotTemplate;
import com.example.demo.entity.UnavailableSlot;
import com.example.demo.entity.User;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.SlotTemplateRepository;
import com.example.demo.repository.UnavailableSlotRepository;
import com.example.demo.repository.UserRepository;

// DBからデータ持ってきて予約の一覧を作る
@Service
public class ScheduleService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SlotTemplateRepository slotTemplateRepository;
    @Autowired
    private UnavailableSlotRepository unavailableSlotRepository;

    // DBから画面表示用のタイムラインを作る
    public List<TimelineDto> createTimelines(User user, LocalDate date) {
        if (user.getRole() == User.Role.STUDENT) {
            return createTimelineForStudent(user, date);
        } else if (user.getRole() == User.Role.COUNSELOR) {
            return creaTimelineForCounselor(user, date);
        } else {
            throw new IllegalArgumentException("存在しないRoleです。");
        }
    }

    private List<TimelineDto> createTimelineForStudent(User user, LocalDate date) {
        List<TimelineDto> timelines = new ArrayList<>();
        List<User> counselors = userRepository.findByRole(User.Role.COUNSELOR);

        for (User c : counselors) {
            TimelineDto timeline = createTimeline(user, c, date);
            timelines.add(timeline);
        }
        return timelines;
    }

    private List<TimelineDto> creaTimelineForCounselor(User user, LocalDate date) {
        List<TimelineDto> timelines = new ArrayList<>();
        timelines.add(createTimeline(user, user, date));
        return timelines;
    }

    private TimelineDto createTimeline(User user, User counselor, LocalDate date) {
        TimelineDto timelineDto = new TimelineDto();
        timelineDto.setDate(date);
        timelineDto.setCounselorName(counselor.getName());
        timelineDto.setCounselorId(counselor.getId());

        List<SlotTemplate> slotTemplates = slotTemplateRepository.findAll(Sort.by("startTime"));
        List<Reservation> reservations = reservationRepository.findByCounselorAndDate(counselor, date);
        List<UnavailableSlot> unavailables = unavailableSlotRepository.findByCounselorAndDate(counselor, date);

        for (SlotTemplate t : slotTemplates) {
            SlotDto slot = createTimelineSlot(t, date, reservations, unavailables, user);
            timelineDto.addSlotDto(slot);
        }

        return timelineDto;
    }

    // タイムライン用のSlotDto生成 空ならEmptyのDtoを生成する
    private SlotDto createTimelineSlot(
            SlotTemplate template,
            LocalDate date,
            List<Reservation> reservations,
            List<UnavailableSlot> unavailables,
            User user) {

        LocalDateTime dateTime = date.atTime(template.getStartTime());
        SlotDto slot = new SlotDto(dateTime);

        for (Reservation r : reservations) {
            if (!template.getPeriodNo().equals(r.getSlotTemplate().getPeriodNo())) {
                continue;
            }

            slot = createReservationSlot(r);
            if (user.getId().equals(r.getStudent().getId())) {
                slot.setStatus(SlotDto.Status.Mine);
            } else {
                slot.setStatus(SlotDto.Status.Reserved);
            }

            return slot;
        }

        for (UnavailableSlot u : unavailables) {
            if (template.getPeriodNo().equals(u.getSlotTemplate().getPeriodNo())) {
                slot.setStatus(SlotDto.Status.Unavailabled);
                return slot;
            }
        }

        slot.setStatus(SlotDto.Status.Empty);
        return slot;
    }

    public List<SlotDto> createReservationSlots(User student) {
        List<SlotDto> slots = new ArrayList<>();
        List<Reservation> reservations = reservationRepository.findByStudent(student);
        // 今日以前のものは消す
        reservations = reservations.stream()
                .filter(r -> !r.getDate().isBefore(LocalDate.now()))
                .toList();
        for (Reservation r : reservations) {
            slots.add(createReservationSlot(r));
        }
        return slots;
    }

    private SlotDto createReservationSlot(Reservation reservation) {
        LocalDate date = reservation.getDate();
        LocalDateTime dateTime = date.atTime(reservation.getSlotTemplate().getStartTime());
        SlotDto slot = new SlotDto(dateTime);
        slot.setSummary(reservation.getSummary());
        slot.setDetail(reservation.getDetail());
        slot.setReservationId(reservation.getId());
        slot.setCounselorName(reservation.getCounselor().getName());
        return slot;
    }

}
