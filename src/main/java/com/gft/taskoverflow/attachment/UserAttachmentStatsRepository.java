package com.gft.taskoverflow.attachment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAttachmentStatsRepository extends JpaRepository<UserAttachmentStats, Long> {
}
