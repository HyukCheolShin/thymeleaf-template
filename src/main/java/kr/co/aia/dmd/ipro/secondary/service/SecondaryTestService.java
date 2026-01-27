package kr.co.aia.dmd.ipro.secondary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import kr.co.aia.dmd.ipro.secondary.mapper.SecondaryTestMapper;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app.datasource.secondary", name = "enabled", havingValue = "true")
public class SecondaryTestService {

    private final SecondaryTestMapper secondaryTestMapper;

    @Transactional(value = "secondaryTransactionManager", readOnly = true)
    public String getSecondaryDbTime() {
        return secondaryTestMapper.selectNow();
    }
}
