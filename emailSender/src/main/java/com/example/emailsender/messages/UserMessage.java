package com.example.emailsender.messages;

import lombok.*;

import java.io.Serializable;
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserMessage implements Serializable {
    private String name;
    private String email;
}
