package com.netty.practice.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netty.practice.domain.Payload;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class MapperUtil {
    private static final ObjectMapper objectMapper=new ObjectMapper();

    public static <T> T readValueOrThrow(String content,Class<T> type) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(content, type);
    }

    public static <T> T readValueOrThrow(Object content,Class<T> type){
        Object object;
        try{
            return objectMapper.readValue(objectMapper.writeValueAsString(content), type);
        }catch (JsonParseException | JsonMappingException e){
            log.error("json parse error");
        }catch (IOException e){
            log.error("IOException error");
        }
        return null;
    }

    public static Object returnMessage(Object data){
        Object object=null;
        try{
            object=new TextWebSocketFrame(objectMapper.writeValueAsString(data));
        }catch (JsonParseException | JsonMappingException e){
            log.error("json parse error");
        } catch (IOException e){
            log.error("IOException error");
        }
        return object;
    }
}
