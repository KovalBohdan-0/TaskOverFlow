package com.gft.taskoverflow.attachment;

import com.gft.taskoverflow.customer.Customer;
import com.gft.taskoverflow.customer.CustomerService;
import com.gft.taskoverflow.exception.DownloadLimitExceededException;
import com.gft.taskoverflow.exception.UploadLimitExceededException;
import com.gft.taskoverflow.task.Task;
import com.gft.taskoverflow.task.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.time.LocalDate;

@Service
@Slf4j
public class AttachmentService {
    private final UserAttachmentStatsRepository userStatsRepository;
    private final AttachmentRepository attachmentRepository;
    private final CustomerService customerService;
    private final AttachmentMapper attachmentMapper;
    private final TaskService taskService;
    private final S3Client s3Client;
    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    public AttachmentService(UserAttachmentStatsRepository userStatsRepository,
                             AttachmentRepository attachmentRepository, CustomerService customerService,
                             AttachmentMapper attachmentMapper,
                             TaskService taskService,
                             S3Client s3Client) {
        this.userStatsRepository = userStatsRepository;
        this.attachmentRepository = attachmentRepository;
        this.customerService = customerService;
        this.attachmentMapper = attachmentMapper;
        this.taskService = taskService;
        this.s3Client = s3Client;
    }

    public AttachmentResponseDto getAttachment(Long taskId) {
        return attachmentMapper.mapToResponseDto(attachmentRepository.findById(taskId).orElse(null));
    }

    public void addAttachment(Long taskId, MultipartFile attachment, String name) throws IOException {
        Long customerId = customerService.getCurrentCustomerId();
        Customer customer = customerService.getCurrentCustomerEntity();

        if (!isUserAttachmentStatsExists(customerId)) {
            UserAttachmentStats userStats = UserAttachmentStats.builder()
                    .lastUploadDate(LocalDate.now())
                    .lastDownloadDate(LocalDate.now())
                    .build();
            userStats.setCustomer(customer);
            userStatsRepository.save(userStats);
            customer.setUserAttachmentStats(userStats);
            customerService.save(customer);
        }

        if (!isUploadAllowed(customerId, attachment.getSize(), taskId)) {
            throw new UploadLimitExceededException(customerId);
        }

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(taskId + "/" + name)
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(attachment.getBytes()));

        Task task = taskService.getTaskById(taskId);
        Attachment attachmentEntity = new Attachment(taskId, name, attachment.getSize(), task);
        task.setAttachment(attachmentEntity);
        taskService.save(task);

        UserAttachmentStats userStats = userStatsRepository.findById(customerId).orElse(new UserAttachmentStats());
        userStats.setAttachmentCount(userStats.getAttachmentCount() + 1);
        userStats.setUploadCountByDay(userStats.getUploadCountByDay() + 1);
        userStats.setLastUploadDate(LocalDate.now());
        userStatsRepository.save(userStats);
    }

    public void deleteAttachment(Long taskId) {
        if (attachmentRepository.findById(taskId).isPresent()) {
            Attachment attachment = attachmentRepository.findById(taskId).get();
            s3Client.deleteObject(DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(taskId + "/" + attachment.getName())
                    .build());
            attachmentRepository.deleteById(taskId);
            UserAttachmentStats userStats = userStatsRepository.findById(customerService.getCurrentCustomerId())
                    .orElse(new UserAttachmentStats());
            userStats.setAttachmentCount(userStats.getAttachmentCount() - 1);
            userStatsRepository.save(userStats);
            taskService.getTaskById(taskId).setAttachment(null);
            taskService.save(taskService.getTaskById(taskId));
        }
    }

    public String getDownloadURL(Long taskId) {
        if (!isDownloadAllowed(customerService.getCurrentCustomerId())) {
            throw new DownloadLimitExceededException(customerService.getCurrentCustomerId());
        }

        UserAttachmentStats userStats = userStatsRepository.findById(customerService.getCurrentCustomerId())
                .orElse(new UserAttachmentStats());
        Attachment attachment = attachmentRepository.findById(taskId).orElse(new Attachment());

        if (!userStats.getLastDownloadDate().equals(LocalDate.now())) {
            userStats.setDownloadCountByDay(0);
        }

        userStats.setDownloadCountByDay(userStats.getDownloadCountByDay() + 1);
        userStats.setLastDownloadDate(LocalDate.now());
        userStatsRepository.save(userStats);

        try (S3Presigner presigner = S3Presigner.create()) {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(taskId + "/" + attachment.getName())
                    .build();

            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(java.time.Duration.ofSeconds(5))
                    .getObjectRequest(getObjectRequest)
                    .build();

            PresignedGetObjectRequest presignedRequest = presigner.presignGetObject(presignRequest);

            return presignedRequest.url().toString();
        } catch (Exception e) {
            log.error("Error while generating download URL: " + e.getMessage());
        }

        return "";
    }

    public boolean isUploadAllowed(Long userId, Long fileSize, Long taskId) {
        UserAttachmentStats userStats = userStatsRepository.findById(userId).orElse(new UserAttachmentStats());
        Task task = taskService.getTaskById(taskId);
        int MAX_UPLOAD_COUNT = 10;
        int MAX_UPLOAD_SIZE = 1048576;

        if (userStats.getAttachmentCount() > MAX_UPLOAD_COUNT || fileSize > MAX_UPLOAD_SIZE ||
                task.getAttachment() != null) {
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
