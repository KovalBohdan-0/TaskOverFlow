import { Injectable } from '@angular/core';
import {Subject} from "rxjs";
import {Client} from "@stomp/stompjs";

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  private taskListAdditionsSubject: Subject<any> = new Subject<any>();
  private taskAdditionsSubject: Subject<any> = new Subject<any>();
  private client: Client;

  constructor() { }

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
      },
    });

    this.client.activate();
  }

  // sendTaskListAddedMessage(taskList: any): void {
  //   this.client.publish({
  //     destination: '/app/task-list-added',
  //     body: JSON.stringify(taskList)
  //   });
  // }



  getTaskListAdditions(): Subject<any> {
    return this.taskListAdditionsSubject;
  }

  getTaskAdditions(): Subject<any> {
    return this.taskAdditionsSubject;
  }

  getClient(): Client {
    return this.client;
  }
}
