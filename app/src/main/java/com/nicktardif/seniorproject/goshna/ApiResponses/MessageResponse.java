package com.nicktardif.seniorproject.goshna.ApiResponses;

import com.nicktardif.seniorproject.goshna.Message;

import java.util.List;

/**
 * Created by tick on 4/7/15.
 */
public class MessageResponse {
    public List<Message> messages;

    public MessageResponse(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "MessageResponse{" +
                "messages=" + messages +
                '}';
    }
}
