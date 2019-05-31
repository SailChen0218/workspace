package monitor;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MonitorLogHandler implements WorkHandler<List<MonitorLogDto>> {
    private static Logger log = LoggerFactory.getLogger(Worker.class);

    @Override
    public void doWork(Work<List<MonitorLogDto>> work) {
        if (work == null) {
            throw new IllegalArgumentException("work must not be null.");
        } else {
            List<MonitorLogDto> monitorLogDtoList = work.getWorkContent();
            if (monitorLogDtoList != null && monitorLogDtoList.size() > 0) {
                for (int i = 0; i < monitorLogDtoList.size(); i++) {
                    MonitorLogDto monitorLogDto = monitorLogDtoList.get(i);
                    if (monitorLogDto != null) {
//                        MonitorWriter monitorWriter =
//                                BondeInterfaceFactory.getInstance().getInterface(MonitorWriter.class);
//                        monitorWriter.trace(logs.get(i));
                        log.error(JSON.toJSONString(monitorLogDto));
                    } else {
                        log.error("monitorLogDto is null.");
                    }
                }
            }
        }
    }

}
