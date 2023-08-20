package org.skyeng_test.services;

import lombok.extern.slf4j.Slf4j;
import org.skyeng_test.models.Post;
import org.skyeng_test.models.Routing;
import org.skyeng_test.repositories.RoutingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * service to work with routings
 */
@Service
public class RoutingService {
    private final Logger logger = LoggerFactory.getLogger(RoutingService.class);
    private final RoutingRepository routingRepository;

    public RoutingService(RoutingRepository routingRepository) {
        this.routingRepository = routingRepository;
    }

    /**
     * method for creating routing or returning existing routing
     * uses {@link RoutingRepository#findByPostFromAndPostTo(Post, Post)},
     * {@link RoutingRepository#save(Object)}
     * @param postFrom
     * @param postTo
     * @param interPost
     * @return
     */
    @Transactional
    public Routing create(Post postFrom, Post postTo, Post interPost) {
        logger.info("finding routings");
        Optional<List<Routing>> routings = routingRepository.findByPostFromAndPostTo(postFrom, postTo);
        if (routings.isEmpty() || routings.get().get(0).getStart() != null) {
            logger.info("creating of routing");
            Routing newRouting = new Routing();
            newRouting.setPostFrom(postFrom);
            newRouting.setIntermediatePost(interPost);
            newRouting.setPostTo(postTo);
            routingRepository.save(newRouting);
            return newRouting;
        }
        return routings.get().get(0);
    }

    /**
     * method starts departure of mailings from start point
     * {@link MailingService#start(long)} uses this method
     * @param id
     * @return Routing
     */
    Routing start(long id) {
        logger.info("starting of routing");
        Routing routing = findById(id);
        routing.setStart(LocalDateTime.now());
        update(routing);
        return routing;
    }

    /**
     * method writes mailings in db to be delivered to intermediate post
     * {@link MailingService#toIntPost(long)} uses this method
     * @param id
     * @return Routing
     */
    Routing toIntPost(long id) {
        logger.info("routing coming to inter post");
        Routing routing = findById(id);
        validate(routing);
        routing.setArrivalTimeToInterPost(LocalDateTime.now());
        update(routing);
        return routing;
    }

    /**
     * method writes mailings in db to be sent from intermediate post
     * {@link MailingService#fromIntPost(long)} uses this method
     * @param id
     * @return Routing
     */
    Routing fromIntPost(long id) {
        logger.info("routing leaving from inter post");
        Routing routing = findById(id);
        validate(routing);
        routing.setDepartureTimeFromInterPost(LocalDateTime.now());
        update(routing);
        return routing;
    }

    /**
     * method finishes departure of mailings
     * {@link MailingService#finish(long)} uses this method
     * @param id
     * @return Routing
     */
    Routing finish(long id) throws EntityNotFoundException {
        logger.info("routing finishes");
        Routing routing = findById(id);
        validate(routing);
        routing.setArrival(LocalDateTime.now());
        update(routing);
        return routing;
    }

    /**
     * private method for finding routing by id
     * uses {@link RoutingRepository#findById(Object)}
     * @param id
     * @return Routing
     * @throws EntityNotFoundException
     */
    private Routing findById(long id) {
        logger.info("find routing by id");
        Optional<Routing> routing = Optional.ofNullable(routingRepository.findById(id))
                .orElseThrow(() -> new EntityNotFoundException("routing is not found"));
        return routing.get();
    }

    /**
     * private method for changing data for certain routing
     * {@link RoutingRepository#save(Object)}
     * @param routing
     */
    private void update(Routing routing) {
        logger.info("updating of routing");
        routingRepository.save(routing);
    }

    /**
     * private method for validating routing
     * {@link RoutingService#finish(long)},
     * {@link RoutingService#toIntPost(long)},
     * {@link RoutingService#fromIntPost(long)} use this method
     * @param routing
     */
    private void validate(Routing routing) {
        logger.info("validating of routing");
        LocalDateTime now = LocalDateTime.now();
        if (routing.getStart() == null) {
            throw new RuntimeException("mailings aren't else sent");
        } else if (routing.getStart().isAfter(now)) {
            throw new RuntimeException("this date can't be before start date");
        }
    }
}
