package com.gft.taskoverflow.attachment;

import com.gft.taskoverflow.customer.CustomerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;

import java.time.LocalDate;

@Service
public class AttachmentService {
    private final UserAttachmentStatsRepository userStatsRepository;
    private final CustomerService customerService;
    private final S3Client s3Client;
    @Value("${aws.s3.bucket.name}")
    private String bucketName;
    private final int MAX_UPLOAD_COUNT = 10;
    private final int MAX_UPLOAD_SIZE = 1048576;

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
                    .lastUploadDate(LocalDate.now())
                    .lastDownloadDate(LocalDate.now())
                    .build();
            userStatsRepository.save(userStats);
        }
    }

    public boolean isUploadAllowed(Long userId, Long fileSize) {
        UserAttachmentStats userStats = userStatsRepository.findById(userId).orElse(new UserAttachmentStats());

        if (userStats.getAttachmentCount() > MAX_UPLOAD_COUNT || fileSize > MAX_UPLOAD_SIZE) {
            return false;
        }

        return userStats.getUploadCountByDay() < 10 || !userStats.getLastUploadDate().equals(LocalDate.now());
    }

    public boolean isDownloadAllowed(Long userId) {
        UserAttachmentStats userStats = userStatsRepository.findById(userId).orElse(new UserAttachmentStats());
        return userStats.getDownloadCountByDay() < 10 || !userStats.getLastDownloadDate().equals(LocalDate.now());
    }

    private boolean isUserAttachmentStatsExists(Long userId) {
        return userStatsRepository.existsById(userId);
    }
}
