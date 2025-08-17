package com.alamara.journalApp.schedular;

import com.alamara.journalApp.Entity.JournalEntry;
import com.alamara.journalApp.Entity.User;
import com.alamara.journalApp.Enum.Sentiment;
import com.alamara.journalApp.cache.AppCache;
import com.alamara.journalApp.model.SentimentData;
import com.alamara.journalApp.repository.UserRepositoryImpl;
import com.alamara.journalApp.service.EmailService;

import com.alamara.journalApp.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserSchedular {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Autowired
    private AppCache appCache;

    @Autowired
    private KafkaTemplate<String, SentimentData> kafkaTemplate;

    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUserAndSendEmail(){
        List<User> users = userRepository.getUserForSA();
        for(User user : users)
        {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x->x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment,Integer> sentimentCounts=new HashMap<>();
            for(Sentiment sentiment:sentiments){
                if(sentiment != null)
                    sentimentCounts.put(sentiment,sentimentCounts.getOrDefault(sentiment,0)+1);
            }
            Sentiment mostFrequestSentiment=null;
            int maxCount=0;
            for(Map.Entry<Sentiment,Integer> entry: sentimentCounts.entrySet()){
                if(entry.getValue() >maxCount){
                    maxCount=entry.getValue();
                    mostFrequestSentiment=entry.getKey();
                }
            }
            if(mostFrequestSentiment!=null) {
                SentimentData sentimentData=SentimentData.builder().email(user.getEmail()).sentiment(mostFrequestSentiment.toString()).build();
                try {
                    kafkaTemplate.send("weekly-sentiments",sentimentData.getEmail(),sentimentData);
                } catch (Exception e) {
                    emailService.sendEmail(sentimentData.getEmail(), "Sentiment for previous week ", sentimentData.getSentiment());
                }
            }
        }
    }


    @Scheduled(cron = "0 0/10 * ? * *")
    public void clearAppCache()
    {
        appCache.init();
    }
}
