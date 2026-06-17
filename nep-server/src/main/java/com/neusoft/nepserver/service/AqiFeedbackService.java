package com.neusoft.nepserver.service;

import com.neusoft.nepserver.dto.PageResponseDto;
import com.neusoft.nepserver.entity.AqiFeedback;

import java.util.List;

public interface AqiFeedbackService {
    int saveAqiFeedback(AqiFeedback aqiFeedback);
    List<AqiFeedback> listAqiFeedbackByTelId(String telId);
    PageResponseDto<AqiFeedback> listAqiFeedbackPage(String keyword, int pageNum, int pageSize);
    AqiFeedback getAqiFeedbackById(int afId);
    int updateAqiFeedbackAssign(AqiFeedback aqiFeedback);
    List<AqiFeedback> listAqiFeedbackByGmId(String gmId);
    int updateAqiFeedbackState(AqiFeedback aqiFeedback);
}
