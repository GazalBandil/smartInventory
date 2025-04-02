package com.smartInventory.backend.service;
import com.smartInventory.backend.model.UserActivityLog;
import com.smartInventory.backend.repository.UserActivityLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserActivityLogService {
    @Autowired
    private UserActivityLogRepository userActivityLogRepository;

    public void logActivity(String username, String action, String description) {
        UserActivityLog log = new UserActivityLog();
        log.setUsername(username);
        log.setAction(action);
        log.setDescription(description);
        userActivityLogRepository.save(log);
    }

    public List<UserActivityLog> getUserActivityLogs(String username) {
        return userActivityLogRepository.findByUsernameOrderByTimestampDesc(username);
    }

    public List<UserActivityLog> getAllUserActivity() {
        return userActivityLogRepository.findAll();  // Fetch all logs from DB
    }
}
