package com.neusoft.nepserver.mapper;

import com.neusoft.nepserver.entity.AqiFeedback;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface AqiFeedbackMapper {
    int saveAqiFeedback(AqiFeedback aqiFeedback);
    List<AqiFeedback> listAqiFeedbackByTelId(@Param("telId") String telId);
    List<AqiFeedback> listAqiFeedbackPage(@Param("keyword") String keyword, @Param("offset") int offset, @Param("pageSize") int pageSize);
    int getAqiFeedbackCount(@Param("keyword") String keyword);
    AqiFeedback getAqiFeedbackById(@Param("afId") int afId);
    int updateAqiFeedbackAssign(AqiFeedback aqiFeedback);
    List<AqiFeedback> listAqiFeedbackByGmId(@Param("gmId") String gmId);
    int updateAqiFeedbackState(AqiFeedback aqiFeedback);
}
