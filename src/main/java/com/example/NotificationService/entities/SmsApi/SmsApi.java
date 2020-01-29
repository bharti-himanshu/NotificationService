package com.example.NotificationService.entities.SmsApi;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@JsonAutoDetect
@AllArgsConstructor
@Builder
public class SmsApi {

    private final String deliverychannel = "sms";

    private Channels channels;

    private List<Destination> destination = new ArrayList<>();

    public Channels getChannels ()
    {
        return channels;
    }

    public SmsApi(String message,String msisdn,String id) {
        Channels channel = new Channels(message);
        this.channels = channel;
        Destination dest = new Destination(msisdn,id);
        this.destination.add(dest);
    }

    @JsonAutoDetect
    @Data
    @AllArgsConstructor
    class Channels {
        private Sms sms;

        public Channels(String message) {
            Sms sms = new Sms(message);
            this.sms = sms;
        }

    }
    @JsonAutoDetect
    @Data
    @AllArgsConstructor
    class Destination {
        private List<String> msisdn = new ArrayList<>();
        private String correlationid;


        public Destination(String phoneNumber, String correlationid) {
            this.msisdn.add(phoneNumber);
            this.correlationid = correlationid;
        }

    }

    @JsonAutoDetect
    @Data
    @AllArgsConstructor
    public class Sms {
        private String text;
    }

}
