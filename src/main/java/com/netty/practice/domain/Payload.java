package com.netty.practice.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Data
public class Payload implements Serializable {
    private Command command;
    private Object body;

    public Command getCommand() {
        return command;
    }

    public Object getBody() {
        return body;
    }
}
