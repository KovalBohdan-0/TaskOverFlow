import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {AuthService} from "./auth.service";

@Injectable({
  providedIn: 'root'
})
export class BoardService {
  apiUrl = environment.apiUrl;
  headers = new HttpHeaders({
    'Authorization': 'Bearer ' + this.authService.getToken(),
  });

  constructor(private httpClient: HttpClient, private authService: AuthService) { }

  getAllBoards() {
    return this.httpClient.get(this.apiUrl + '/api/v1/board', {headers: this.headers, observe: 'response'});
  }

  addBoard(board: any) {
    return this.httpClient.post(this.apiUrl + '/api/v1/board', board, {headers: this.headers, observe: 'response'});
  }

  addBoardMember(boardId: number, email: string) {
    return this.httpClient.post(this.apiUrl + '/api/v1/board/addCustomer/' + boardId, {email: email}, {headers: this.headers, observe: 'response'});
  }
}
