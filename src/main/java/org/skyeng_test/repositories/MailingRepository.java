package org.skyeng_test.repositories;

import org.skyeng_test.models.Mailing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MailingRepository extends JpaRepository<Mailing, Long> {
    List<Mailing> findByRoutingId(long id);
}
