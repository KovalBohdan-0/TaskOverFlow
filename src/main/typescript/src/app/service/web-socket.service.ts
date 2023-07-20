import { Injectable } from '@angular/core';
import {Subject} from "rxjs";
import {Client, Stomp, StompHeaders} from "@stomp/stompjs";
import SockJS from 'sockjs-client';
import {webSocket} from "rxjs/webSocket";

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  // private stompClient: Client;
  private taskListUpdatesSubject: Subject<any> = new Subject<any>();
  private taskListAdditionsSubject: Subject<any> = new Subject<any>();
  private taskListDeletionsSubject: Subject<any> = new Subject<any>();
  private taskUpdatesSubject: Subject<any> = new Subject<any>();
  private taskDeletionsSubject: Subject<any> = new Subject<any>();

  constructor() { }

  connect(): void {
    const client = new Client({
      brokerURL: 'ws://localhost:8080/ws',
      onConnect: () => {
        console.log('WebSocket connected');
        client.subscribe('/topic/task-list-added', (message) => {
          this.taskListAdditionsSubject.next(JSON.parse(message.body));
          // console.log(message.body);
        });
        // this.stompClient.subscribe('/topic/task-list-updated', (message) => {
        //   this.taskListUpdatesSubject.next(JSON.parse(message.body));
        // });
        // this.stompClient.subscribe('/topic/task-list-deleted', (message) => {
        //   this.taskListDeletionsSubject.next(JSON.parse(message.body));
        // });
        // this.stompClient.subscribe('/topic/task-updated', (message) => {
        //   this.taskUpdatesSubject.next(JSON.parse(message.body));
        // });
        // this.stompClient.subscribe('/topic/task-deleted', (message) => {
        //   this.taskDeletionsSubject.next(JSON.parse(message.body));
        // });
      },
    });

    client.activate();
  }

  // sendTaskListAddedMessage(taskList: any): void {
  //   this.stompClient.publish({
  //     destination: '/app/task-list-added',
  //     body: JSON.stringify(taskList)
  //   });
  // }

  getTaskListAdditions(): Subject<any> {
    return this.taskListAdditionsSubject;
  }
}
