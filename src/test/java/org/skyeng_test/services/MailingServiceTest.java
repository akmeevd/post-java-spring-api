package org.skyeng_test.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skyeng_test.dto.MailingDto;
import org.skyeng_test.dto.MailingRegDto;
import org.skyeng_test.mappers.MailingMapper;
import org.skyeng_test.models.*;
import org.skyeng_test.repositories.MailingRepository;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class MailingServiceTest {
    @InjectMocks
    private MailingService mailingService;
    @Mock
    private MailingRepository mailingRepository;
    @Mock
    private RecipientService recipientService;

    @Mock
    private PostService postService;

    @Mock
    private RoutingService routingService;

    @Mock
    private MailingMapper mailingMapper;
    private Recipient recipient;
    private Post post;
    private Routing routing;
    private Mailing mailing;

    @BeforeEach
    public void setup() {
        recipient = new Recipient();
        recipient.setId(1L);
        post = new Post();
        post.setIndex(35000);
        routing = new Routing();
        routing.setId(1);
        mailing = new Mailing();
        mailing.setRegDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        mailing.setRouting(routing);
        mailing.setRecipient(recipient);
        mailing.setStatus(Status.REGISTERED);

    }

    @Test
    public void create() {
        Mockito.doReturn(recipient).when(recipientService).create(any(), any(), anyInt());
        Mockito.doReturn(post).when(postService).findByIndex(anyInt());
        Mockito.doReturn(routing).when(routingService).create(any(), any(), any());
        Mockito.doReturn(mailing).when(mailingRepository).save(any());
        MailingRegDto mailingRegDto = new MailingRegDto();
        Mailing mailing1 = mailingService.create(mailingRegDto);
        Assertions.assertEquals(mailing1, mailing);
    }

    @Test
    public void start() {
        Mockito.doReturn(List.of(mailing)).when(mailingRepository).findByRoutingId(anyLong());
        Mockito.doReturn(routing).when(routingService).start(anyLong());
        Routing actualRouting = mailingService.start(routing.getId());
        Assertions.assertEquals(routing, actualRouting);
    }

    @Test
    public void toIntPost() {
        Mockito.doReturn(routing).when(routingService).toIntPost(anyLong());
        Routing actualRouting = mailingService.toIntPost(routing.getId());
        Assertions.assertEquals(routing, actualRouting);
    }

    @Test
    public void fromIntPost() {
        Mockito.doReturn(routing).when(routingService).fromIntPost(anyLong());
        Routing actualRouting = mailingService.fromIntPost(routing.getId());
        Assertions.assertEquals(routing, actualRouting);
    }

    @Test
    public void finish() {
        Mockito.doReturn(List.of(mailing)).when(mailingRepository).findByRoutingId(anyLong());
        Mockito.doReturn(routing).when(routingService).finish(anyLong());
        Routing actualRouting = mailingService.finish(routing.getId());
        Assertions.assertEquals(routing, actualRouting);
    }

    @Test
    public void findRouting() {
        Mockito.doReturn(Optional.of(mailing)).when(mailingRepository).findById(anyLong());
        Routing actualRouting = mailingService.findRouting(mailing.getId());
        Assertions.assertEquals(routing, actualRouting);
    }

    @Test
    public void findInfo() {
        MailingDto mailingDto = new MailingDto();
        mailing.setStatus(Status.REGISTERED);
        Mockito.doReturn(Optional.of(mailing)).when(mailingRepository).findById(anyLong());
        Mockito.doReturn(mailingDto).when(mailingMapper).fromMailingtoMailingDto(any());
        MailingDto actual = mailingService.findInfo(mailing.getId());
        Assertions.assertEquals(mailingDto, actual);
    }

}
