import { Injectable } from '@angular/core';
import {Subject} from "rxjs";
import {Client} from "@stomp/stompjs";

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  private taskListAdditionsSubject: Subject<any> = new Subject<any>();
  private taskAdditionsSubject: Subject<any> = new Subject<any>();

  constructor() { }

  connect(): void {
    const client = new Client({
      brokerURL: 'ws://localhost:8080/ws',
      onConnect: () => {
        console.log('WebSocket connected');
        client.subscribe('/topic/task-list-added', (message) => {
          this.taskListAdditionsSubject.next(JSON.parse(message.body));
        });
        client.subscribe('/topic/task-added', (message) => {
          this.taskAdditionsSubject.next(JSON.parse(message.body));
        });
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

  getTaskAdditions(): Subject<any> {
    return this.taskAdditionsSubject;
  }
}
