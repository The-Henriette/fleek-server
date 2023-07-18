package run.fleek.common.jpa;

import run.fleek.utils.TimeUtil;

import javax.persistence.PrePersist;
import java.util.Date;

public class CreatedAtListener {
    @PrePersist
    public void setCreatedAt(final SystemMetadata entity) {
        entity.setCreatedAt(TimeUtil.getCurrentTimeMillisUtc());
    }
}
