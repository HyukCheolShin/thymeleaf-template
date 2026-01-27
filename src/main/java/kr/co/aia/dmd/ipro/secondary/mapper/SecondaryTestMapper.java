package kr.co.aia.dmd.ipro.secondary.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SecondaryTestMapper {
    String selectNow();
}
