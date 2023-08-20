package org.skyeng_test.repositories;

import org.skyeng_test.models.Post;
import org.skyeng_test.models.Routing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoutingRepository extends JpaRepository<Routing, Long> {
    Optional<List<Routing>> findByPostFromAndPostTo(Post postFrom, Post postTo);
}
