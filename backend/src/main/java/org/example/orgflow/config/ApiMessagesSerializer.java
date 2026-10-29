package org.example.orgflow.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.example.orgflow.response.ApiMessages;

import java.io.IOException;
import java.util.Locale;


    @Component
    public class ApiMessagesSerializer extends JsonSerializer<ApiMessages> {

        private final MessageSource messageSource;

        @Autowired
        public ApiMessagesSerializer(MessageSource messageSource) {
            this.messageSource = messageSource;
        }

        @Override
        public void serialize(ApiMessages apiMessages, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

            String code = apiMessages.getCode();

            String messageEn = messageSource.getMessage(code, null, new Locale("en"));
            String messageAr = messageSource.getMessage(code, null, new Locale("ar"));

            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("code", code);
            jsonGenerator.writeStringField("messageEn", messageEn);
            jsonGenerator.writeStringField("messageAr", messageAr);
            jsonGenerator.writeEndObject();
        }


    }

