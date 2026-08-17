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

// DBからデータ持ってきてタイムラインを作る
@Service
public class TimelineService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SlotTemplateRepository slotTemplateRepository;
    @Autowired
    private UnavailableSlotRepository unavailableSlotRepository;

    public TimelineDto createTimeline(Integer id, LocalDate date) {
        TimelineDto timelineDto = new TimelineDto();
        timelineDto.setDate(date);

        User counselor = userRepository.findById(id).orElseGet(()->{ throw new RuntimeException(id+" のUserが存在しません"); });
        timelineDto.setCounselorName(counselor.getName());


        // TODO: timeline生成
        // 空のslotsで埋めてからreservationsみて時間が一致するなら置き換え
        List<SlotTemplate> slotTemplates = slotTemplateRepository.findAll(Sort.by("startTime"));
        List<Reservation> reservations = reservationRepository.findByIdAndDate(id, date);
        List<UnavailableSlot> unavailables = unavailableSlotRepository.findByDate(date);
        List<SlotDto> slots = new ArrayList<>();

        for(SlotTemplate t: slotTemplates){
            SlotDto slot = new SlotDto(t.getStartTime());
            slot.setStatus(SlotDto.Status.Empty);

            for(Reservation r: reservations){
                Boolean isReserved = t.getPeriodNo().equals(r.getSlotTemplate().getPeriodNo());
                if(isReserved){
                    slot.setStatus(SlotDto.Status.Reserved);
                    slot.setSummary(r.getSummary());
                    slot.setDetail(r.getDetail());
                    break;
                }
            }
            
            if(slot.getStatus() == SlotDto.Status.Empty){
                for(UnavailableSlot u: unavailables){
                    Boolean isUnavailable = t.getPeriodNo().equals(u.getSlotTemplate().getPeriodNo());
                    if(isUnavailable){
                        slot.setStatus(SlotDto.Status.Unavailabled);
                        break;
                    }
                }
            }
            
            slots.add(slot);
        }
        
        timelineDto.setSlots(slots);
        return timelineDto;
    }
}
