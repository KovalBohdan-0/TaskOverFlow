import {Component, OnInit} from '@angular/core';
import {CustomerService} from "../service/customer.service";
import {BoardService} from "../service/board.service";
import {Priority} from "./task-list/task/Priority";
import {TaskList} from "./task-list/TaskList";
import {Subscription} from "rxjs";
import {TaskListService} from "../service/task-list.service";
import {WebSocketService} from "../service/web-socket.service";
import {CdkDragDrop, moveItemInArray} from "@angular/cdk/drag-drop";

@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.css']
})
export class BoardComponent implements OnInit {
  private subscriptions: Subscription[] = [];
  currentBoardId: number = 0;
  selectedBoard: any = {id: 0, title: 'Select board'};
  email: string = "";
  newBoardTitle: string = "";
  newTaskListTitle: string = "";
  boards: any[] = [
    {id: 0, title: 'Not found'},
  ];
  lists: TaskList[] = [
    {
      id: 0,
      boardId: 1,
      title: 'Not found 1',
      task: [
        {title: 'Not found 1', done: false, id: 0, priority: Priority.LOW, taskListId: 0},
        {title: 'Not found', done: false, id: 0, priority: Priority.HIGH, taskListId: 0}
      ]
    },

    {
      id: 0,
      boardId: 1,
      title: 'Done',
      task: [
        {title: 'Not found 1', done: false, id: 0, priority: Priority.MEDIUM, taskListId: 0},
        {title: 'Not found', done: true, id: 0, priority: Priority.LOW, taskListId: 0},
        {title: 'Not found 1', done: false, id: 0, priority: Priority.MEDIUM, taskListId: 0},
        {title: 'Not found', done: true, id: 0, priority: Priority.LOW, taskListId: 0},
        {title: 'Not found 1', done: false, id: 0, priority: Priority.MEDIUM, taskListId: 0},
        {title: 'Not found', done: true, id: 0, priority: Priority.LOW, taskListId: 0},
        {title: 'Not found 1', done: false, id: 0, priority: Priority.MEDIUM, taskListId: 0},
        {title: 'Not found', done: true, id: 0, priority: Priority.LOW, taskListId: 0},
        {title: 'Not found 1', done: false, id: 0, priority: Priority.MEDIUM, taskListId: 0},
        {title: 'Not found', done: true, id: 0, priority: Priority.LOW, taskListId: 0}
      ]
    },
    {
      id: 0,
      boardId: 1,
      title: 'To do',
      task: [
        {title: 'Not found 1', done: false, id: 0, priority: Priority.MEDIUM, taskListId: 0},
        {title: 'Not found', done: true, id: 0, priority: Priority.LOW, taskListId: 0}
      ]
    }
  ];


  constructor(private customerService: CustomerService,
              private boardService: BoardService,
              private taskListService: TaskListService,
              private webSocketService: WebSocketService) {
  }

  ngOnInit(): void {
    this.setEmail();
    this.getBoards();
    this.makeSubscriptions();
  }

  makeSubscriptions() {
    this.webSocketService.connect();
    this.webSocketService.getTaskListAdditions().subscribe(message =>{
      console.log(message);
      this.pushTaskList(message);
    });
  }

  addTaskList() {
    this.taskListService.addTaskList({title: this.newTaskListTitle, boardId: this.currentBoardId}).subscribe({
      next: (response: any) => {
        // this.pushTaskList(response.body);
      },
      error: (error: any) => {
        if (error.status == 404) {
          //TODO add error message
        } else {
        }
      }
    });
  }

  getTaskListsByBoardId(boardId: number) {
    this.taskListService.getTaskListsByBoardId(boardId).subscribe({
      next: (response: any) => {
        this.lists = response.body;
      },
      error: (error: any) => {
        if (error.status == 404) {
          //TODO add error message
        } else {
        }
      }
    });
  }

  setEmail() {
    this.customerService.getCurrentCustomer().subscribe({
      next: (response: any) => {
        this.email = response.body.email;
      },
      error: (error: any) => {
        if (error.status == 404) {
          //TODO add error message
        } else {
        }
      }
    });
  }

  addBoard() {
    this.boardService.addBoard({title: this.newBoardTitle}).subscribe({
      next: (response: any) => {
        console.log(response);
      },
      error: (error: any) => {
        if (error.status == 404) {
          //TODO add error message
        } else {
        }
      }
    });
  }

  getBoards() {
    this.boardService.getAllBoards().subscribe({
      next: (response: any) => {
        this.boards = response.body;
      },
      error: (error: any) => {
        if (error.status == 404) {
          //TODO add error message
        } else {
        }
      }
    });
  }

  openSelectedBoard() {
    this.currentBoardId = this.selectedBoard;
    this.getTaskListsByBoardId(this.currentBoardId);
  }

  pushTaskList(taskList: TaskList) {
    this.lists.push(taskList);
  }

  drop(event: CdkDragDrop<string[]>) {
    moveItemInArray(this.lists, event.previousIndex, event.currentIndex);
  }
}
