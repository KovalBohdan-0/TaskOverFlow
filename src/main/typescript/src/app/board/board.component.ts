import {Component, OnInit} from '@angular/core';
import {CustomerService} from "../service/customer.service";
import {BoardService} from "../service/board.service";
import {TaskList} from "./task-list/TaskList";
import {TaskListService} from "../service/task-list.service";
import {WebSocketService} from "../service/web-socket.service";
import {CdkDragDrop, moveItemInArray} from "@angular/cdk/drag-drop";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.css']
})
export class BoardComponent implements OnInit {
  currentBoardId: number = 0;
  selectedBoard: any = {id: 0, title: 'Select board'};
  email: string = "";
  newBoardTitle: string = "";
  newTaskListTitle: string = "";
  boards: any[] = [
    {id: 0, title: 'Not found'},
  ];
  lists: TaskList[] = [];

  constructor(private customerService: CustomerService,
              private boardService: BoardService,
              private taskListService: TaskListService,
              private webSocketService: WebSocketService,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.setEmail();
    this.getBoards();
    this.makeSubscriptions();
    this.setSelectedBoard();
  }

  makeSubscriptions() {
    this.webSocketService.connect();
    this.webSocketService.getTaskListAdditions().subscribe(message =>{
      if (message.boardId == this.currentBoardId) {
        message.tasks = [];
        this.lists.push(message);
      }
    });
  }

  addTaskList() {
    this.taskListService.addTaskList({title: this.newTaskListTitle, boardId: this.currentBoardId});
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
      next: () => {},
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

  setSelectedBoard() {
    this.route.params
      .subscribe(params => {
          this.currentBoardId = params['id'];
          this.getTaskListsByBoardId(this.currentBoardId);
        }
      );
  }

  drop(event: CdkDragDrop<TaskList[]>) {
    moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
  }
}
