package com.example.NotificationServiceTry.services.ElasticSearch;

import com.example.NotificationServiceTry.dto.ScrollIdDto;
import com.example.NotificationServiceTry.Exceptions.InvalidScrollIdException;
import com.example.NotificationServiceTry.Exceptions.NoSearchResultException;
import com.example.NotificationServiceTry.entities.SmsRequest;
import com.example.NotificationServiceTry.services.SmsCrudService;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class ElasticSearchService {
    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private SmsCrudService smsCrudService;

    public void saveSmsElastic(SmsRequest sms_request) throws IOException {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("Id", sms_request.getId());
        jsonMap.put("phone_number", sms_request.getPhone_number());
        jsonMap.put("message", sms_request.getMessage());
        jsonMap.put("created_at", sms_request.getCreated_at());
        jsonMap.put("updated_at", sms_request.getUpdated_at());

        IndexRequest indexRequest = new IndexRequest("smselastic", "SmsElasticData")
                .source(jsonMap);

        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);

        System.out.println(indexResponse.toString());
    }


    public ScrollIdDto getAllSms(String prevScrollId,int limit) throws IOException, NoSearchResultException {


        if(prevScrollId!=null) {
            try {
                SearchScrollRequest scrollRequest = new SearchScrollRequest(prevScrollId);
                final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
                scrollRequest.scroll(scroll);
                SearchResponse searchResponse = client.scroll(scrollRequest, RequestOptions.DEFAULT);

                return fetchHelper(searchResponse);
            }
            catch(ElasticsearchException e){
                throw new InvalidScrollIdException(e);
            }
        }
        else{
            final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(10));
            SearchRequest searchRequest = new SearchRequest("smselastic");
            searchRequest.scroll(scroll);
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.size(limit);
            QueryBuilder match = new MatchAllQueryBuilder();
            searchSourceBuilder.query(match);
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            return fetchHelper(searchResponse);
        }

    }


    public ScrollIdDto getSmsInterval(String phoneNumber, LocalDateTime start, LocalDateTime end,String prevScrollId) throws IOException, NoSearchResultException {


        if(prevScrollId!=null) {
            try {
                SearchScrollRequest scrollRequest = new SearchScrollRequest(prevScrollId);
                final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
                scrollRequest.scroll(scroll);
                SearchResponse searchResponse = client.scroll(scrollRequest, RequestOptions.DEFAULT);

                return fetchHelper(searchResponse);
            }
            catch(ElasticsearchException e){
                throw new InvalidScrollIdException(e);
            }
        }
        else{
            final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));

            SearchRequest searchRequest = new SearchRequest("smselastic");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

            RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder("created_at");
            rangeQueryBuilder.gte(start);
            rangeQueryBuilder.lte(end);

            QueryBuilder termQuery = new TermQueryBuilder("phone_number", phoneNumber);

            QueryBuilder boolQuery = new BoolQueryBuilder().must(rangeQueryBuilder).must(termQuery);

            searchSourceBuilder.query(boolQuery);

            searchRequest.source(searchSourceBuilder);

            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            return fetchHelper(searchResponse);
        }
    }

    public ScrollIdDto getSmsContainingText(String text, String prevScrollId) throws IOException, NoSearchResultException {

        if(prevScrollId!=null) {
            try {
                SearchScrollRequest scrollRequest = new SearchScrollRequest(prevScrollId);
                final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
                scrollRequest.scroll(scroll);
                SearchResponse searchResponse = client.scroll(scrollRequest, RequestOptions.DEFAULT);


                return fetchHelper(searchResponse);
            }
            catch(ElasticsearchException e){
                throw new InvalidScrollIdException(e);
            }
        }
        else{
            final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
            SearchRequest searchRequest = new SearchRequest("smselastic");
            searchRequest.scroll(scroll);
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            QueryBuilder queryBuilder = new MatchQueryBuilder("message", text);
            searchSourceBuilder.query(queryBuilder);

            searchRequest.source(searchSourceBuilder);

            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            return fetchHelper(searchResponse);
        }
    }

    public ScrollIdDto fetchHelper(SearchResponse searchResponse) throws NoSearchResultException {
        String scrollId = searchResponse.getScrollId();
        SearchHits hits = searchResponse.getHits();

        ArrayList<Integer> smsIds = new ArrayList<>();
        SearchHit[] searchHits = hits.getHits();
        if(searchHits.length == 0){
            throw new NoSearchResultException();
        }
        for (SearchHit hit : searchHits) {
            Map<String, Object> source = hit.getSourceAsMap();
            Integer id = (Integer) source.get("Id");
            smsIds.add(id);
        }
        ArrayList<SmsRequest> smsList = smsCrudService.getAllSmsById(smsIds);

        return new ScrollIdDto(scrollId,smsList);
    }
}
