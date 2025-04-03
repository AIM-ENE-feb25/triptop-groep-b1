package org.prototype.demo;

import lombok.extern.slf4j.Slf4j;
import org.prototype.demo.common.model.ApiResponse;
import org.prototype.demo.common.model.SearchCriteria;
import org.prototype.demo.common.service.ExternalServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
public class FacadeDemo implements CommandLineRunner {

    @Autowired
    private ExternalServiceFacade serviceFacade;

    @Override
    public void run(String... args) {
        SearchCriteria criteria = new SearchCriteria(
            "Amsterdam",
            LocalDate.now().plusDays(1).toString(),
            LocalDate.now().plusDays(7).toString(),
            2 // Number of guests
        );

        serviceFacade.executeAllRequests(criteria)
            .thenAccept(responses -> {
                for (ApiResponse response : responses) {
                    log.info("Response: {}", response);
                }
            })
            .exceptionally(throwable -> {
                log.error("Error processing responses: ", throwable);
                return null;
            });
    }
} 