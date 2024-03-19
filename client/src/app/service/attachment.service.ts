import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient, HttpHeaders, HttpResponse} from "@angular/common/http";
import {AuthService} from "./auth.service";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AttachmentService {
  apiUrl = environment.apiUrl;
  headers = new HttpHeaders({
    'Authorization': 'Bearer ' + this.authService.getToken(),
  });

  constructor(private authService: AuthService, private httpClient: HttpClient) { }

  getAttachments(taskId: number): Observable<Object> {
    return this.httpClient.get(this.apiUrl + '/api/v1/attachment/' + taskId, {headers: this.headers, observe: 'response'});
  }

  getDownloadUrl(taskId: number): Observable<Object> {
    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + this.authService.getToken(),
      'Content-Type': 'text/plain; charset=utf-8',
    });

    return this.httpClient.get(this.apiUrl + '/api/v1/attachment/' + taskId + '/download', {headers: headers, responseType: 'text', observe: 'response'});
  }

  addAttachment(taskId: number, file: File): Observable<HttpResponse<Object>> {
    const formData: FormData = new FormData();
    formData.append("file", file);

    return this.httpClient.post(this.apiUrl + '/api/v1/attachment/' + taskId + "?fileName=" + file.name, formData, {headers: this.headers, observe: 'response'});
  }

  deleteAttachment(taskId: number): Observable<HttpResponse<Object>> {
    return this.httpClient.delete(this.apiUrl + '/api/v1/attachment/' + taskId, {headers: this.headers, observe: 'response'});
  }
}
