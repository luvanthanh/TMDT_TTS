package com.tmdttts.backend.controller;

import com.tmdttts.backend.dto.Requests;
import com.tmdttts.backend.dto.Responses;
import com.tmdttts.backend.entity.ContactMessage;
import com.tmdttts.backend.repository.ContactMessageRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/contact")
public class ContactController {
    private final ContactMessageRepository repository;
    public ContactController(ContactMessageRepository repository){this.repository=repository;}
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Responses.Message send(@Valid @RequestBody Requests.Contact body){
        ContactMessage message=new ContactMessage();message.setContactMessageFullName(body.fullName().trim());message.setContactMessageEmail(body.email().trim().toLowerCase());
        message.setContactMessagePhone(body.phone());message.setContactMessageSubject(body.subject());message.setContactMessageContent(body.content().trim());repository.save(message);
        return new Responses.Message("Đã nhận lời nhắn của bạn");
    }
}
