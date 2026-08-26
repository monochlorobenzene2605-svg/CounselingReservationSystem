package com.example.demo.service;

import java.time.LocalDate;
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
        List<TimelineDto> timelines = new ArrayList<>();
        List<User> counselors = userRepository.findByRole(User.Role.COUNSELOR);

        for (User c : counselors) {
            TimelineDto timeline = createTimeline(user, c, date);
            timelines.add(timeline);
        }
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
            SlotDto slot = createSlot(t, reservations, unavailables, user);
            timelineDto.addSlotDto(slot);
        }

        return timelineDto;
    }
    
    private SlotDto createSlot(
        SlotTemplate template,
        List<Reservation> reservations,
        List<UnavailableSlot> unavailables,
        User user) {

        SlotDto slot = new SlotDto(template.getStartTime());

        for (Reservation r : reservations) {
            if (!template.getPeriodNo().equals(r.getSlotTemplate().getPeriodNo())) {
                continue;
            }

            if (user.getId().equals(r.getStudent().getId())) {
                slot.setStatus(SlotDto.Status.Mine);
                slot.setSummary(r.getSummary());
                slot.setDetail(r.getDetail());
                slot.setReservationId(r.getId());
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

}
