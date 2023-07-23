import {Component, OnInit} from '@angular/core';
import {CustomerService} from "../service/customer.service";
import {BoardService} from "../service/board.service";
import {TaskList} from "./task-list/TaskList";
import {TaskListService} from "../service/task-list.service";
import {CdkDragDrop, moveItemInArray} from "@angular/cdk/drag-drop";
import {ActivatedRoute} from "@angular/router";
import {RxStompService} from "../service/rx-stomp.service";
import {Message} from "@stomp/stompjs";

@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.css']
})
export class BoardComponent implements OnInit {
  selectedBoard: any = {id: 0, title: 'Select board'};
  email: string = "";
  newBoardTitle: string = "";
  newTaskListTitle: string = "";
  boards: any[] = [
    {id: 0, title: 'Not found'},
  ];
  lists: TaskList[] = [];
  taskListSubscription: any;

  constructor(private customerService: CustomerService,
              private boardService: BoardService,
              private taskListService: TaskListService,
              private rxStompService: RxStompService,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.setEmail();
    this.setLoadedBoards();
  }

  makeSubscriptions() {
    this.taskListSubscription = this.rxStompService.watch('/topic/task-list-added/' + this.selectedBoard.id).subscribe((receivedMessage: Message) => {
      const message = JSON.parse(receivedMessage.body);

      if (message.boardId == this.selectedBoard.id) {
        message.tasks = [];
        this.lists.push(message);
      }
    });
    this.rxStompService.watch('/topic/task-added/' + this.selectedBoard.id).subscribe((receivedMessage: Message) => {
      const message = JSON.parse(receivedMessage.body);

      this.lists.find((list: TaskList) => list.id == message.taskListId).tasks.push(message);
    });
  }

  addTaskList() {
    this.taskListService.addTaskList({
      title: this.newTaskListTitle,
      boardId: this.selectedBoard.id
    }, this.selectedBoard.id);
  }

  getTaskListsByBoardId(boardId: number) {
    this.taskListService.getTaskListsByBoardId(boardId).subscribe({
      next: (response: any) => {
        this.lists = response.body;
      }
    });
  }

  setEmail() {
    this.customerService.getCurrentCustomer().subscribe({
      next: (response: any) => {
        this.email = response.body.email;
      }
    });
  }

  addBoard() {
    this.boardService.addBoard({title: this.newBoardTitle}).subscribe({
      next: () => {
      },
      error: (error: any) => {
        if (error.status == 404) {
          //TODO add error message
        } else {
        }
      }
    });
  }

  setLoadedBoards() {
    this.boardService.getAllBoards().subscribe({
      next: (response: any) => {
        this.boards = response.body;
        this.setSelectedBoard();
      }
    });
  }

  setSelectedBoard() {
    this.route.params
      .subscribe(params => {
          this.selectedBoard = this.boards.find(board => board.id == params['id']);
          this.getTaskListsByBoardId(this.selectedBoard.id);
          this.updateTaskListSubscriptions();
        }
      );

  }

  updateTaskListSubscriptions() {
    if (this.taskListSubscription != undefined) {
      this.taskListSubscription.unsubscribe();
    }

    this.makeSubscriptions();
  }

  drop(event: CdkDragDrop<TaskList[]>) {
    moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
  }
}
