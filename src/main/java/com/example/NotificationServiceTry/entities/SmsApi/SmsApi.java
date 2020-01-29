package com.example.NotificationServiceTry.entities.SmsApi;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@JsonAutoDetect
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
    @Builder
    class Channels {
        private Sms sms;

        public Channels(String message) {
            Sms sms = new Sms(message);
            this.sms = sms;
        }

    }
    @JsonAutoDetect
    @Builder
    @Data
    class Destination {
        private List<String> msisdn = new ArrayList<>();
        private String correlationid;


        public Destination(String phoneNumber, String correlationid) {
            this.msisdn.add(phoneNumber);
            this.correlationid = correlationid;
        }

    }

    @JsonAutoDetect
    @Builder
    @Data
    public class Sms {
        private String text;
    }

}
