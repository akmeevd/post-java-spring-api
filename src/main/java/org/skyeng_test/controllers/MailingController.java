package org.skyeng_test.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.skyeng_test.dto.MailingDto;
import org.skyeng_test.dto.MailingRegDto;
import org.skyeng_test.models.Mailing;
import org.skyeng_test.models.Routing;
import org.skyeng_test.services.MailingService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("mailings")
@Tag(name = "mailings")
public class MailingController {

    private final MailingService mailingService;

    public MailingController(MailingService mailingService) {
        this.mailingService = mailingService;
    }

    @Operation(summary = "creating mailing and adding him to db")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "creating of mailing was done successfully",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Mailing.class))}),
                    @ApiResponse(responseCode = "400", description = "bad request"),
                    @ApiResponse(responseCode = "500", description = "server trouble")})
    @PostMapping
    public ResponseEntity<Mailing> create(@RequestBody MailingRegDto mailingRegDto) {
        return ResponseEntity.ok(mailingService.create(mailingRegDto));
    }

    @Operation(summary = "mailings start from initial post")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200",
                    description = "starting of mailings from initial post was done successfully",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Routing.class))}),
                    @ApiResponse(responseCode = "400", description = "bad request"),
                    @ApiResponse(responseCode = "500", description = "server trouble")})
    @PatchMapping("/routing/{id}/start")
    public ResponseEntity<Routing> start(@PathVariable long id) {
        return ResponseEntity.ok(mailingService.start(id));
    }

    @Operation(summary = "mailings come to intermediate post")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200",
                    description = "mailings have successfully come to intermediate post ",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Routing.class))}),
                    @ApiResponse(responseCode = "400", description = "bad request"),
                    @ApiResponse(responseCode = "500", description = "server trouble")})
    @PatchMapping("/routing/{id}/to-int-post")
    public ResponseEntity<Routing> toIntPost(@PathVariable long id) {
        return ResponseEntity.ok(mailingService.toIntPost(id));
    }

    @Operation(summary = "mailings leave from intermediate post")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200",
                    description = "mailings have successfully left from intermediate post ",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Routing.class))}),
                    @ApiResponse(responseCode = "400", description = "bad request"),
                    @ApiResponse(responseCode = "500", description = "server trouble")})
    @PatchMapping("/routing/{id}/from-int-post")
    public ResponseEntity<Routing> fromIntPost(@PathVariable long id) {
        return ResponseEntity.ok(mailingService.fromIntPost(id));
    }

    @Operation(summary = "mailings come to end point")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200",
                    description = "mailings have successfully come to end point ",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Routing.class))}),
                    @ApiResponse(responseCode = "400", description = "bad request"),
                    @ApiResponse(responseCode = "500", description = "server trouble")})
    @PatchMapping("/routing/{id}/finish")
    public ResponseEntity<Routing> finish(@PathVariable long id) {
        return ResponseEntity.ok(mailingService.finish(id));
    }

    @Operation(summary = "find routing by id")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200",
                    description = "routing has successfully found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Routing.class))}),
                    @ApiResponse(responseCode = "400", description = "bad request"),
                    @ApiResponse(responseCode = "404", description = "not found"),
                    @ApiResponse(responseCode = "500", description = "server trouble")})
    @GetMapping("/{id}/routing")
    public ResponseEntity<Routing> findRouting(@PathVariable long id) {
        return ResponseEntity.ok(mailingService.findRouting(id));
    }

    @Operation(summary = "get info about mailing by id")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200",
                    description = "mailing has successfully found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MailingDto.class))}),
                    @ApiResponse(responseCode = "400", description = "bad request"),
                    @ApiResponse(responseCode = "404", description = "not found"),
                    @ApiResponse(responseCode = "500", description = "server trouble")})
    @GetMapping("/{id}")
    public ResponseEntity<MailingDto> getInfo(@PathVariable long id) {
        return ResponseEntity.ok(mailingService.findInfo(id));
    }
}
