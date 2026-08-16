package com.example.demo.service;

// DBからデータ持ってきてタイムラインを作る
@Service
public class TimelineService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private UserRepository userRepository;

    public TimelineDto createTimeline(Integer id, LocalDate date, List<SlotTemplate> slotTemplates) {
        TimelineDto timelineDto = new TimelineDto();
        timelineDto.setCounselorName(counselorName);
        timelineDto.setDate(date);

        User counselor = userRepository.findById(id).orElseGet(()->{ throw Exception(id+" のユーザーが存在しません"); });
        List<Reservation> reservations = reservationRepository.findByUserIdAndDate();

        // TODO: timeline生成
        slotTemplatesを取得
        for(t: slotTemplates){
            if(t.starttime()と等しいreservationがあれば){
                slotDtosに追加
            } else {
                空のslotを追加
            }
        }
        List<SlotDto> slotDtos = new ArrayList<>();
        for (SlotTemplate slotTemplate : slotTemplates) {
            SlotDto slotDto = new SlotDto();
            slotDto.setStartTime(slotTemplate.getStartTime());
            slotDto.setIsAvailable(slotTemplate.isAvailable());
            slotDto.setIsReserved(slotTemplate.isReserved());
            slotDto.setSummary(slotTemplate.getSummary());
            slotDto.setDetail(slotTemplate.getDetail());
            slotDtos.add(slotDto);
        }
        
        timelineDto.setSlots(slotDtos);
        return timelineDto;
    }
}
