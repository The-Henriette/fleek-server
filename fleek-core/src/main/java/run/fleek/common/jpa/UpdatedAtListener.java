package run.fleek.common.jpa;

import run.fleek.utils.TimeUtil;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class UpdatedAtListener {
    @PreUpdate
    @PrePersist
    public void setUpdatedAt(final SystemMetadata entity) {
        entity.setUpdatedAt(TimeUtil.getCurrentTimeMillisUtc());
    }
}
