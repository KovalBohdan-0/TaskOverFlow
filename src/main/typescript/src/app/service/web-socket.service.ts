import { Injectable } from '@angular/core';
import {Subject} from "rxjs";
import {Client} from "@stomp/stompjs";

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  private taskListAdditionsSubject: Subject<any> = new Subject<any>();
  private taskAdditionsSubject: Subject<any> = new Subject<any>();
  private auth2Subject: Subject<any> = new Subject<any>();
  private client: Client;

  constructor() {
  }

  connect(): void {
    this.client = new Client({
      brokerURL: 'ws://localhost:8080/ws',
      onConnect: () => {
        console.log('WebSocket connected');
        this.client.subscribe('/topic/task-list-added', (message) => {
          this.taskListAdditionsSubject.next(JSON.parse(message.body));
        });
        this.client.subscribe('/topic/task-added', (message) => {
          this.taskAdditionsSubject.next(JSON.parse(message.body));
        });
        this.client.subscribe('/topic/oauth2-google-callback', (message) => {
          this.auth2Subject.next(JSON.parse(message.body));
        });
      },
    });

    this.client.activate();
  }

  sendMessage(destination: string, body: string): void {
    this.client.publish({
      destination: destination,
      body: body
    });
  }

  addSubscription(destination: string, callback: (message: any) => void) {
    return this.client.subscribe(destination, callback);
  }

  getTaskListAdditions(): Subject<any> {
    return this.taskListAdditionsSubject;
  }

  getTaskAdditions(): Subject<any> {
    return this.taskAdditionsSubject;
  }

  getAuth2(): Subject<any> {
    return this.auth2Subject;
  }

  disconnect(): void {
    this.client.deactivate();
  }
}
