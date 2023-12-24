package com.gft.taskoverflow.attachment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@AllArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/v1/attachment")
public class AttachmentController {
    private final AttachmentService attachmentService;

    @Operation(summary = "Get attachment by task id")
    @ApiResponse(responseCode = "200", description = "Attachment found")
    @GetMapping("/{taskId}")
    public AttachmentResponseDto getAttachment(@PathVariable Long taskId) {
        return attachmentService.getAttachment(taskId);
    }

    @Operation(summary = "Get attachment download url by task id")
    @ApiResponse(responseCode = "200", description = "Attachment found")
    @ApiResponse(responseCode = "403", description = "Download limit exceeded")
    @GetMapping("/{taskId}/download")
    public String getAttachmentDownloadUrl(@PathVariable Long taskId) {
        return attachmentService.getDownloadURL(taskId);
    }

    @Operation(summary = "Add attachment to task")
    @ApiResponse(responseCode = "200", description = "Attachment added")
    @ApiResponse(responseCode = "403", description = "Upload limit exceeded")
    @PostMapping("/{taskId}")
    public void addAttachment(@PathVariable Long taskId, @RequestParam("file") MultipartFile attachment,
                              @RequestParam("fileName") String name) throws IOException {
        attachmentService.addAttachment(taskId, attachment, name);
    }

    @Operation(summary = "Delete attachment by task id")
    @ApiResponse(responseCode = "200", description = "Attachment deleted")
    @DeleteMapping("/{taskId}")
    public void deleteAttachment(@PathVariable Long taskId) {
        attachmentService.deleteAttachment(taskId);
    }
}
