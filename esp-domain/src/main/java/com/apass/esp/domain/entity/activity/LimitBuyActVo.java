package com.apass.esp.domain.entity.activity;
import org.springframework.web.multipart.MultipartFile;
import com.apass.esp.domain.entity.LimitBuyAct;
public class LimitBuyActVo extends LimitBuyAct{
    private String startDay;
    private String startTime;
//    private MultipartFile file;
    public String getStartDay() {
        return startDay;
    }
    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }
    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
//    public MultipartFile getFile() {
//        return file;
//    }
//    public void setFile(MultipartFile file) {
//        this.file = file;
//    }
}