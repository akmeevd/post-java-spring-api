package org.skyeng_test.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skyeng_test.models.Post;
import org.skyeng_test.models.Routing;
import org.skyeng_test.repositories.RoutingRepository;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class RoutingServiceTest {

    @InjectMocks
    private RoutingService routingService;

    @Mock
    private RoutingRepository routingRepository;

    private final Routing routing = new Routing();
    private final Post postFrom = new Post();
    private final Post interPost = new Post();
    private final Post postTo = new Post();

    @BeforeEach
    public void setup() {
        routing.setId(1L);
        routing.setPostTo(postTo);
        routing.setPostFrom(postFrom);
        routing.setIntermediatePost(interPost);
    }

    @Test
    public void create1() {
        Mockito.doReturn(Optional.of(List.of(routing))).when(routingRepository).findByPostFromAndPostTo(any(), any());
        Routing actualRouting = routingService.create(postFrom, postTo, interPost);
        Assertions.assertEquals(routing, actualRouting);
    }

    @Test
    public void create2() {
        Mockito.doReturn(Optional.empty()).when(routingRepository).findByPostFromAndPostTo(any(), any());
        Mockito.doReturn(routing).when(routingRepository).save(any());
        routingService.create(postFrom, postTo, interPost);
        Mockito.verify(routingRepository, Mockito.times(1)).save(any());
    }

    @Test
    public void start() {
        Mockito.doReturn(Optional.of(routing)).when(routingRepository).findById(anyLong());
        Routing actualRouting = routingService.start(routing.getId());
        Assertions.assertEquals(routing, actualRouting);
    }

    @Test
    public void finish() {
        routing.setStart(LocalDateTime.now());
        Mockito.doReturn(Optional.of(routing)).when(routingRepository).findById(anyLong());
        Routing actualRouting = routingService.finish(routing.getId());
        Assertions.assertEquals(routing, actualRouting);
    }

    @Test
    public void toIntPost() {
        routing.setStart(LocalDateTime.now());
        Mockito.doReturn(Optional.of(routing)).when(routingRepository).findById(anyLong());
        Routing actualRouting = routingService.toIntPost(routing.getId());
        Assertions.assertEquals(routing, actualRouting);
    }

    @Test
    public void fromIntPost() {
        routing.setStart(LocalDateTime.now());
        Mockito.doReturn(Optional.of(routing)).when(routingRepository).findById(anyLong());
        Routing actualRouting = routingService.fromIntPost(routing.getId());
        Assertions.assertEquals(routing, actualRouting);
    }

    @Test
    public void findById() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Mockito.doReturn(Optional.of(routing)).when(routingRepository).findById(anyLong());
        Class[] parameters = new Class[1];
        parameters[0] = long.class;
        Method method = routingService.getClass().getDeclaredMethod("findById", parameters);
        method.setAccessible(true);
        Object[] objects = new Object[1];
        objects[0] = routing.getId();
        Routing actualRouting = (Routing) method.invoke(routingService, objects);
        Assertions.assertEquals(routing, actualRouting);
    }

    @Test
    public void update() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Mockito.doReturn(routing).when(routingRepository).save(any());
        Class[] parameters = new Class[1];
        parameters[0] = Routing.class;
        Method method = routingService.getClass().getDeclaredMethod("update", parameters);
        method.setAccessible(true);
        Object[] objects = new Object[1];
        objects[0] = routing;
        method.invoke(routingService, objects);
        Mockito.verify(routingRepository, Mockito.times(1)).save(any());
    }

    @Test
    public void doesThrowRuntimeExceptionInMethodValidate() {
        Mockito.doReturn(Optional.of(routing)).when(routingRepository).findById(anyLong());
        Assertions.assertThrows(RuntimeException.class, () -> routingService.finish(routing.getId()));
        Assertions.assertThrows(RuntimeException.class, () -> routingService.fromIntPost(routing.getId()));
        Assertions.assertThrows(RuntimeException.class, () -> routingService.toIntPost(routing.getId()));
    }
}
