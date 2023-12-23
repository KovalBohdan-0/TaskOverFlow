import { Component } from '@angular/core';

@Component({
  selector: 'app-add-attachment',
  templateUrl: './add-attachment.component.html',
  styleUrls: ['./add-attachment.component.css']
})
export class AddAttachmentComponent {
  constructor() {}

  selectedFile: File = null;

  selectFile() {
    document.getElementById('file').click();
    document.onchange = () => {
      this.selectedFile = (<HTMLInputElement>document.getElementById('file')).files[0];
      console.log(this.selectedFile);
    }
  }
}
