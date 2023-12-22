package com.gft.taskoverflow.attachment;

import com.gft.taskoverflow.customer.CustomerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;

import java.time.LocalDateTime;

@Service
public class AttachmentService {
    private final UserAttachmentStatsRepository userStatsRepository;
    private final CustomerService customerService;
    private final S3Client s3Client;
    @Value("${aws.s3.bucket}")
    private String bucketName;

    public AttachmentService(UserAttachmentStatsRepository userStatsRepository, CustomerService customerService,
                             S3Client s3Client) {
        this.userStatsRepository = userStatsRepository;
        this.customerService = customerService;
        this.s3Client = s3Client;
    }

    public void addAttachment() {
//        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
//                .bucket("s3-test-3433")
//                .key("id")
//                .build();
//
//        s3Client.putObject(putObjectRequest, RequestBody.fromString("test here"));
        Long customerId = customerService.getCurrentCustomerId();

        if (!isUserAttachmentStatsExists(customerId)) {
            UserAttachmentStats userStats = UserAttachmentStats.builder()
                    .id(customerId)
                    .lastUploadDate(LocalDateTime.now())
                    .lastDownloadDate(LocalDateTime.now())
                    .build();
            userStatsRepository.save(userStats);
        }
    }

    private boolean isUserAttachmentStatsExists(Long userId) {
        return userStatsRepository.existsById(userId);
    }
}
