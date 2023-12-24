import {Component, Input, OnInit} from '@angular/core';
import {AttachmentService} from "../../../../../service/attachment.service";
import {Attachment} from "./Attachment";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-add-attachment',
  templateUrl: './add-attachment.component.html',
  styleUrls: ['./add-attachment.component.css']
})
export class AddAttachmentComponent implements OnInit {
  @Input() taskId: number;
  selectedFile: File = null;
  attachment: Attachment;

  constructor(private attachmentService: AttachmentService, private snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    this.attachmentService.getAttachments(this.taskId).subscribe((response: any) => {
      this.attachment = response.body as Attachment;
    });
  }

  selectFile(): void {
    const fileInputElement = document.getElementById('file');

    if (fileInputElement) {
      fileInputElement.click();
    }
  }

  onFileSelected(event: Event): void {
    const inputElement: HTMLInputElement = event.target as HTMLInputElement;

    if (inputElement.files && inputElement.files.length > 0) {
      this.selectedFile = inputElement.files[0];
    }
  }

  removeFile(): void {
    this.selectedFile = null;
  }

  formatBytes(bytes: number): string {
    const sizes: string[] = ['Bytes', 'KB', 'MB', 'GB', 'TB'];

    if (bytes == 0) {
      return '0 Byte';
    }

    const i: number = Math.floor(Math.log(bytes) / Math.log(1024));
    return Math.round(bytes / Math.pow(1024, i)) + ' ' + sizes[i];
  }

  attachFile(): void {
    if (this.selectedFile.size > 1048576) {
      this.snackBar.open("Max upload size is 1Mb", "Close", {duration: 6000});
      return;
    }

    this.attachmentService.addAttachment(this.taskId, this.selectedFile).subscribe({
      next: () => {
        this.selectedFile = null;
      }, error: (error) => {
        if (error.status === 403) {
          this.snackBar.open("Upload limit exceeded", "Close", {duration: 6000});
        }
      }});
  }

  removeAttachment(): void {
    this.attachmentService.deleteAttachment(this.taskId).subscribe(() => {
      this.attachment = null;
    });
  }

  downloadAttachment(): void {
    this.attachmentService.getDownloadUrl(this.taskId).subscribe({
      next: (response: any) => {
        const downloadUrl: string = response.body as string;
        window.open(downloadUrl);
      }, error: (error) => {
        if (error.status === 403) {
          this.snackBar.open("Download limit exceeded", "Close", {duration: 6000});
        }
      }
    });
  }
}
