package com.example.NotificationServiceTry.data.entities.SmsApi;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IMIConnectSMSReq {

    @JsonProperty("deliverychannel")
    private String deliverychannel;

    @JsonProperty("channels")
    private Channels channels;

    @JsonProperty("destination")
    private List<Destination> destination;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Channels {

        @JsonProperty("sms")
        private Sms sms;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class Sms {

            @JsonProperty("text")
            private String text;
        }

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Destination {

        @JsonProperty("msisdn")
        private List<String> msisdn;

        @JsonProperty("correlationid")
        private String correlationid;

    }

}
