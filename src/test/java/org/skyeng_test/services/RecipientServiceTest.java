package org.skyeng_test.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skyeng_test.models.Recipient;
import org.skyeng_test.repositories.RecipientRepository;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class RecipientServiceTest {

    @InjectMocks
    private RecipientService recipientService;

    @Mock
    private RecipientRepository recipientRepository;

    private final Recipient recipient = new Recipient();

    @BeforeEach
    public void setup() {
        recipient.setId(1L);
        recipient.setName("dima");
        recipient.setAddress("street");
        recipient.setIndex(353535);
    }

    @Test
    public void create1() {
        Mockito.doReturn(Optional.of(recipient)).when(recipientRepository).findByNameAndAddress(any(), any());
        Recipient actualRecipient = recipientService.create(recipient.getName(),
                recipient.getAddress(), recipient.getIndex());
        Assertions.assertEquals(recipient, actualRecipient);
    }

    @Test
    public void create2() {
        Mockito.doReturn(Optional.empty()).when(recipientRepository).findByNameAndAddress(any(), any());
        Mockito.doReturn(recipient).when(recipientRepository).save(any());
        recipientService.create(recipient.getName(),
                recipient.getAddress(), recipient.getIndex());
        Mockito.verify(recipientRepository, Mockito.times(1)).save(any());
//
    }

    @Test
    public void checkIfRecipientExists() throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {
        Mockito.doReturn(Optional.of(recipient)).when(recipientRepository).findByNameAndAddress(any(), any());
        Class[] parameters = new Class[2];
        parameters[0] = String.class;
        parameters[1] = String.class;
        Method method = recipientService.getClass().getDeclaredMethod("checkIfRecipientExists", parameters);
        method.setAccessible(true);
        Object[] objects = new Object[2];
        objects[0] = recipient.getName();
        objects[1] = recipient.getAddress();
        Recipient actualRecipient = (Recipient) method.invoke(recipientService, objects);
        Assertions.assertEquals(recipient, actualRecipient);
    }
}
