package kr.co.aia.dmd.ipro.secondary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.aia.dmd.ipro.secondary.mapper.SecondaryTestMapper;

@Service
@RequiredArgsConstructor
public class SecondaryTestService {

    private final SecondaryTestMapper secondaryTestMapper;

    @Transactional(value = "secondaryTransactionManager", readOnly = true)
    public String getSecondaryDbTime() {
        return secondaryTestMapper.selectNow();
    }
}
