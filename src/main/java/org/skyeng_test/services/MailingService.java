package org.skyeng_test.services;

import lombok.extern.slf4j.Slf4j;
import org.skyeng_test.dto.MailingDto;
import org.skyeng_test.dto.MailingRegDto;
import org.skyeng_test.mappers.MailingMapper;
import org.skyeng_test.mappers.MailingMapperImpl;
import org.skyeng_test.models.*;
import org.skyeng_test.repositories.MailingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

/**
 * service for different operation to mailing
 */
@Service
public class MailingService {
    private final Logger logger = LoggerFactory.getLogger(MailingService.class);
    private final MailingRepository mailingRepository;
    private final RecipientService recipientService;
    private final PostService postService;
    private final RoutingService routingService;
    private final MailingMapper mailingMapper;

    public MailingService(MailingRepository mailingRepository, RecipientService recipientService,
                          PostService postService, RoutingService routingService, MailingMapper mailingMapper) {
        this.mailingRepository = mailingRepository;
        this.recipientService = recipientService;
        this.postService = postService;
        this.routingService = routingService;
        this.mailingMapper = mailingMapper;
    }

    /**
     * method creates mailing
     * uses {@link RecipientService#create(String, String, int)},
     * {@link PostService#findByIndex(int)},
     * {@link  RoutingService#create(Post, Post, Post)},
     * {@link MailingRepository#save(Object)}
     * @param mailingRegDto
     * @return Mailing
     */
    @Transactional
    public Mailing create(MailingRegDto mailingRegDto) {
        logger.info("creating of mailing");
        Recipient recipient = recipientService.create(mailingRegDto.getRecipientName()
                , mailingRegDto.getRecipientAddress(), mailingRegDto.getRecipientIndex());
        Post postFrom = postService.findByIndex(mailingRegDto.getPostIndexFrom());
        Post postTo = postService.findByIndex(mailingRegDto.getPostIndexTo());
        Post interPost = postService.findByIndex(mailingRegDto.getInterPostIndex());
        Routing routing = routingService.create(postFrom, postTo, interPost);
        Mailing mailing = new Mailing();
        mailing.setRegDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        mailing.setRecipient(recipient);
        mailing.setRouting(routing);
        mailing.setType(mailingRegDto.getType());
        mailing.setStatus(Status.REGISTERED);
        routing.setMailing(List.of(mailing));
        mailingRepository.save(mailing);
        logger.info("mailing's created");
        return mailing;
    }

    /**
     * method starts departure of mailings from start point
     * and changes status of them
     * {@link MailingRepository#findByRoutingId(long)}
     * {@link RoutingService#start(long)}
     * @param id
     * @return Routing
     */
    @Transactional
    public Routing start(long id) {
        logger.info("mailings start");
        List<Mailing> mailings = mailingRepository.findByRoutingId(id);
        if (!mailings.isEmpty()) {
            for (Mailing mailing : mailings) {
                mailing.setStatus(Status.SENT);
            }
        }
        return routingService.start(id);
    }

    /**
     * method finishes departure of mailings
     * and changes status of them
     * {@link MailingRepository#findByRoutingId(long)}
     * {@link RoutingService#finish(long)}
     * @param id
     * @return Routing
     */
    @Transactional
    public Routing finish(long id) {
        logger.info("mailings finish");
        List<Mailing> mailings = mailingRepository.findByRoutingId(id);
        if (!mailings.isEmpty()) {
            for (Mailing mailing : mailings) {
                mailing.setStatus(Status.ARRIVED);
            }
        }
        return routingService.finish(id);
    }
    /**
     * method writes mailings in db to be delivered to intermediate post
     * {@link RoutingService#toIntPost(long)}
     * @param id
     * @return Routing
     */
    @Transactional
    public Routing toIntPost(long id) {
        logger.info("mailings coming to intPost");
        return routingService.toIntPost(id);
    }

    /**
     * method writes mailings in db to be sent from intermediate post
     * {@link RoutingService#fromIntPost(long)}
     * @param id
     * @return Routing
     */
    @Transactional
    public Routing fromIntPost(long id) {
        logger.info("mailings leaving from intPost");
        return routingService.fromIntPost(id);
    }

    /**
     * method for getting of mailing routing
     * give ability to see routing of certain mailing
     * uses {@link MailingRepository#findById(Object)}
     * @param id
     * @return Routing
     */
    public Routing findRouting(long id) {
        logger.info("finding routing");
        Optional<Mailing> mailing = Optional.ofNullable(mailingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("not found")));
        return mailing.get().getRouting();
    }

    /**
     * method for getting common info about mailing (status, type, recipient)
     * uses {@link MailingRepository#findById(Object)}
     * {@link MailingMapper#fromMailingtoMailingDto(Mailing)}
     * @param id
     * @return MailingDto
     */
    public MailingDto findInfo(long id) {
        logger.info("finding info about mailing");
        Optional<Mailing> mailing = Optional.ofNullable(mailingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("not found")));
        return mailingMapper.fromMailingtoMailingDto(mailing.get());
    }
}
