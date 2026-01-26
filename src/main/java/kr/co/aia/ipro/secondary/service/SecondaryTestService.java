package kr.co.aia.ipro.secondary.service;

import kr.co.aia.ipro.secondary.mapper.SecondaryTestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SecondaryTestService {

    private final SecondaryTestMapper secondaryTestMapper;

    @Transactional(value = "secondaryTransactionManager", readOnly = true)
    public String getSecondaryDbTime() {
        return secondaryTestMapper.selectNow();
    }
}
