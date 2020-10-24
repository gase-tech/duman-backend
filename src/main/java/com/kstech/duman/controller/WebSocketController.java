package com.kstech.duman.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Slf4j
@Controller
public class WebSocketController implements ApplicationListener<SessionConnectEvent> {
    private final SimpMessagingTemplate simpMessagingTemplate;

    WebSocketController(SimpMessagingTemplate simpMessagingTemplate){
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping(value = "/send/message")
    public void onReceivedMessage(@Payload String message, MessageHeaders messageHeaders) {
        LinkedMultiValueMap linkedMultiValueMap = messageHeaders.get("nativeHeaders", LinkedMultiValueMap.class);
        String messageFrom = (String)linkedMultiValueMap.get("from").get(0);
        int messageGroup = Integer.parseInt((String)linkedMultiValueMap.get("group").get(0));

        if (messageFrom != null || !messageFrom.equals(" ") || message != null || message.equals(" ") || messageGroup>0) {
            //Message msg = chatService.createMessage(message, messageFrom, messageGroup);
            //messageRepository.save(msg);
            //msg.setSendDate(msg.getSendDate().minusHours(2));

            /*
            simpMessagingTemplate.convertAndSend(
                    "/chat",
                    ChatSocketResponse
                            .builder()
                            .messageType(SocketMessageType.MESSAGE)
                            .message(msg)
                            .build()
            );

             */
        }
    }

    @Override
    public void onApplicationEvent(SessionConnectEvent event) {
        log.info("Session Connect ! Event => " + event.toString());
        /*
        ConnectedNativeHeadersDTO connectedNativeHeadersDTO = gson.fromJson(event.getMessage().getHeaders().get("nativeHeaders").toString(),ConnectedNativeHeadersDTO.class);
        String sessionId = event.getMessage().getHeaders().get("simpSessionId").toString();

        //add online list
        OnlineUserService.addSession(sessionId, connectedNativeHeadersDTO.getNickname().get(0));
         */
    }

    @EventListener
    public void onSocketDisconnected(SessionDisconnectEvent event) {
        log.info("Session Disconnect ! Event => " + event.toString());
        /*
        String sessionId = event.getSessionId();

        String nickname = OnlineUserService.findNameBySessionId(sessionId);

        OnlineUserService.removeSession(sessionId);
        lastSeenService.changeLastSeen(nickname);
         */
    }
}
