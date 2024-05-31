package com.pch7128.keepercloset.svc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pch7128.keepercloset.dto.Reservation;
import com.pch7128.keepercloset.dto.StorageDTO;
import com.pch7128.keepercloset.dto.Member;
import com.pch7128.keepercloset.repository.MemberRepository;
import com.pch7128.keepercloset.repository.RvRepository;
import com.pch7128.keepercloset.repository.StoreRepository;

@Service
public class RvSvc {
	
	
	@Autowired
	private MemberRepository mre;
	@Autowired
	private RvRepository rvre;
	@Autowired
	private static final AtomicLong counter = new AtomicLong(0);
	@Autowired
	private StoreRepository stre;
	
	
	public Reservation saveRv(int unum, Reservation rv) {
		
		Member m=mre.findById(unum).get();
		rv.setMember(m);
		rv.setReservation_no(reservationNumber());
		Reservation rvresult=rvre.save(rv);
		
		
		return rvresult;
	}

	
	public String reservationNumber() {
		
        // 예약번호는 오늘 날짜와 일련 번호를 조합하여 생성될 수 있습니다.
        LocalDate today = LocalDate.now();
        String datePart = today.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // 일련 번호 생성
        long number = counter.incrementAndGet();

        // 예약번호 생성 (예: 20240530-00001, 20240530-00002, ...)
        return datePart + "-" + String.format("%05d", number); // 5자리 숫자로 포맷팅
		
	}
	
//	public StorageDTO getStore(int stnum) {
//		
//		StorageDTO store=stre.findById(stnum).get();
//		
//		return store;
//	}
}
