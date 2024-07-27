package com.nhnacademy;

import java.time.LocalDateTime;

public class ChangeHistory {
    private LocalDateTime modifiedAt;
    private String changeHistory;
    public ChangeHistory(LocalDateTime modifiedAt, String changeHistory) {
        this.modifiedAt = modifiedAt;
        this.changeHistory = changeHistory;
    }
    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }
    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
    public String getChangeHistory() {
        return changeHistory;
    }
    public void setChangeHistory(String changeHistory) {
        this.changeHistory = changeHistory;
    }
}
