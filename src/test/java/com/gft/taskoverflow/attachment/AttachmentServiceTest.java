package com.gft.taskoverflow.attachment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.mockito.Mockito.when;

public class AttachmentServiceTest {
    @Mock
    private UserAttachmentStatsRepository userStatsRepository;

    @InjectMocks
    private AttachmentService attachmentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void isUploadNotAllowedWhenSizeIsBigger() {
        when(userStatsRepository.findById(1L)).thenReturn(java.util.Optional.of(new UserAttachmentStats()));

        boolean result = attachmentService.isUploadAllowed(1L, 1048577L);

        assert !result;
    }

    @Test
    public void isUploadNotAllowedWhenCountIsBigger() {
        UserAttachmentStats userStats = UserAttachmentStats.builder()
                .attachmentCount(11)
                .lastUploadDate(LocalDate.MIN)
                .lastDownloadDate(LocalDate.MIN)
                .build();
        when(userStatsRepository.findById(1L)).thenReturn(java.util.Optional.of(userStats));

        boolean result = attachmentService.isUploadAllowed(1L, 10L);

        assert !result;
    }

    @Test
    public void isUploadNotAllowedWhenCountIsBiggerAndSizeIsBigger() {
        UserAttachmentStats userStats = UserAttachmentStats.builder()
                .attachmentCount(11)
                .lastUploadDate(LocalDate.MIN)
                .lastDownloadDate(LocalDate.MIN)
                .build();
        when(userStatsRepository.findById(1L)).thenReturn(java.util.Optional.of(userStats));

        boolean result = attachmentService.isUploadAllowed(1L, 1048577L);

        assert !result;
    }

    @Test
    public void isUploadNotAllowedWhenCountPerDayIsBigger() {
        UserAttachmentStats userStats = UserAttachmentStats.builder()
                .uploadCountByDay(10)
                .lastUploadDate(LocalDate.now())
                .lastDownloadDate(LocalDate.MIN)
                .build();
        when(userStatsRepository.findById(1L)).thenReturn(java.util.Optional.of(userStats));

        boolean result = attachmentService.isUploadAllowed(1L, 10L);

        assert !result;
    }

    @Test
    public void isUploadAllowedWhenCountPerDayIsBiggerAndDifferentDay() {
        UserAttachmentStats userStats = UserAttachmentStats.builder()
                .uploadCountByDay(10)
                .lastUploadDate(LocalDate.MIN)
                .lastDownloadDate(LocalDate.MIN)
                .build();
        when(userStatsRepository.findById(1L)).thenReturn(java.util.Optional.of(userStats));

        boolean result = attachmentService.isUploadAllowed(1L, 10L);

        assert result;
    }

    @Test
    public void isUploadNotAllowedWhenCountIsSmallerAndSizeIsBigger() {
        UserAttachmentStats userStats = UserAttachmentStats.builder()
                .attachmentCount(9)
                .lastUploadDate(LocalDate.MIN)
                .lastDownloadDate(LocalDate.MIN)
                .build();
        when(userStatsRepository.findById(1L)).thenReturn(java.util.Optional.of(userStats));

        boolean result = attachmentService.isUploadAllowed(1L, 1048577L);

        assert !result;
    }

    @Test
    public void isUploadAllowedWhenSizeAndCountAreSmaller() {
        UserAttachmentStats userStats = UserAttachmentStats.builder()
                .attachmentCount(5)
                .lastUploadDate(LocalDate.MIN)
                .lastDownloadDate(LocalDate.MIN)
                .build();
        when(userStatsRepository.findById(1L)).thenReturn(java.util.Optional.of(userStats));

        boolean result = attachmentService.isUploadAllowed(1L, 1048575L);

        assert result;
    }

    @Test
    public void isUploadAllowedWhenSizeAndCountAreSmallerAndDifferentDay() {
        UserAttachmentStats userStats = UserAttachmentStats.builder()
                .attachmentCount(5)
                .lastUploadDate(LocalDate.MIN)
                .lastDownloadDate(LocalDate.now())
                .build();
        when(userStatsRepository.findById(1L)).thenReturn(java.util.Optional.of(userStats));

        boolean result = attachmentService.isUploadAllowed(1L, 1048575L);

        assert result;
    }

    @Test
    public void isUploadAllowedWhenNoStats() {
        when(userStatsRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        boolean result = attachmentService.isUploadAllowed(1L, 1048575L);

        assert result;
    }

    @Test
    public void isDownloadNotAllowedWhenCountIsBigger() {
        UserAttachmentStats userStats = UserAttachmentStats.builder()
                .downloadCountByDay(11)
                .lastUploadDate(LocalDate.MIN)
                .lastDownloadDate(LocalDate.now())
                .build();
        when(userStatsRepository.findById(1L)).thenReturn(java.util.Optional.of(userStats));

        boolean result = attachmentService.isDownloadAllowed(1L);

        assert !result;
    }

    @Test
    public void isDownloadAllowedWhenCountIsBiggerAndDifferentDay() {
        UserAttachmentStats userStats = UserAttachmentStats.builder()
                .downloadCountByDay(11)
                .lastUploadDate(LocalDate.MIN)
                .lastDownloadDate(LocalDate.MIN)
                .build();
        when(userStatsRepository.findById(1L)).thenReturn(java.util.Optional.of(userStats));

        boolean result = attachmentService.isDownloadAllowed(1L);

        assert result;
    }

    @Test
    public void isDownloadAllowedWhenCountIsSmaller() {
        UserAttachmentStats userStats = UserAttachmentStats.builder()
                .downloadCountByDay(9)
                .lastUploadDate(LocalDate.MIN)
                .lastDownloadDate(LocalDate.MIN)
                .build();
        when(userStatsRepository.findById(1L)).thenReturn(java.util.Optional.of(userStats));

        boolean result = attachmentService.isDownloadAllowed(1L);

        assert result;
    }

    @Test
    public void isDownloadAllowedWhenCountIsSmallerAndDifferentDay() {
        UserAttachmentStats userStats = UserAttachmentStats.builder()
                .downloadCountByDay(9)
                .lastUploadDate(LocalDate.MIN)
                .lastDownloadDate(LocalDate.now())
                .build();
        when(userStatsRepository.findById(1L)).thenReturn(java.util.Optional.of(userStats));

        boolean result = attachmentService.isDownloadAllowed(1L);

        assert result;
    }

    @Test
    public void isDownloadAllowedWhenNoStats() {
        when(userStatsRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        boolean result = attachmentService.isDownloadAllowed(1L);

        assert result;
    }
}
