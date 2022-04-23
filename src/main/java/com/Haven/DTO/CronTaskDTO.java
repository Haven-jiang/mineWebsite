package com.Haven.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.quartz.JobKey;
import org.quartz.TriggerKey;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CronTaskDTO {
    private String content;
    private JobKey jobKey;
    private TriggerKey triggerKey;
    private String cron;
}
