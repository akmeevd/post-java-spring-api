package org.skyeng_test.services;

import lombok.extern.slf4j.Slf4j;
import org.skyeng_test.models.Recipient;
import org.skyeng_test.repositories.RecipientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * service for working recipients
 */
@Service
public class RecipientService {
    private final Logger logger = LoggerFactory.getLogger(RecipientService.class);
    private final RecipientRepository recipientRepository;

    public RecipientService(RecipientRepository recipientRepository) {
        this.recipientRepository = recipientRepository;
    }

    /**
     * method for creating recipients or returning existing recipients
     * uses {@link RecipientService#checkIfRecipientExists(String, String)},
     * {@link  RecipientRepository#save(Object)}
     * @param name
     * @param address
     * @param index
     * @return Recipient
     */
    @Transactional
    public Recipient create(String name, String address, int index) {
        Recipient recipient = checkIfRecipientExists(name, address);
        if (recipient == null) {
            logger.info("creating recipient");
            Recipient newRecipient = new Recipient();
            newRecipient.setName(name);
            newRecipient.setAddress(address);
            newRecipient.setIndex(index);
            recipientRepository.save(newRecipient);
            return newRecipient;
        }else {
            return recipient;
        }
    }

    /**
     * private method uses in {@link RecipientService#create(String, String, int)}
     * check if recipient exists in db or not
     * @param name
     * @param address
     * @return Recipient
     */
    private Recipient checkIfRecipientExists(String name, String address) {
        logger.info("check recipient in db");
        Optional<Recipient> recipient = recipientRepository
                        .findByNameAndAddress(name, address);
        return recipient.orElse(null);
    }
}
