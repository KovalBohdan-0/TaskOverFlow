import {Component, HostListener, OnDestroy, OnInit} from '@angular/core';
import {CustomerService} from "../service/customer.service";
import {BoardService} from "../service/board.service";
import {TaskList} from "./task-list/TaskList";
import {TaskListService} from "../service/task-list.service";
import {CdkDragDrop, moveItemInArray} from "@angular/cdk/drag-drop";
import {ActivatedRoute, Router} from "@angular/router";
import {RxStompService} from "../service/rx-stomp.service";
import {Message} from "@stomp/stompjs";
import {SharedService} from "../service/shared.service";
import {ShortTask} from "./task-list/task/ShortTask";
import {MatSnackBar} from "@angular/material/snack-bar";
import {NotificationService} from "../service/notification.service";
import {Notification} from "./notifications/notification/Notification";

@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.css']
})
export class BoardComponent implements OnInit, OnDestroy {
  selectedBoard: any = {id: 0, title: 'Select board'};
  email: string = "";
  newBoardTitle: string = "";
  newTaskListTitle: string = "";
  boards: any[] = [
    {id: 0, title: 'Not found'},
  ];
  lists: TaskList[] = [];
  subscriptions = [];
  currentNotifications: Notification[] = [];
  isMobile: boolean = window.innerWidth < 576;

  constructor(private customerService: CustomerService,
              private boardService: BoardService,
              private taskListService: TaskListService,
              private rxStompService: RxStompService,
              private route: ActivatedRoute,
              private sharedService: SharedService,
              private router: Router,
              private snackBar: MatSnackBar,
              private notificationService: NotificationService) {
  }

  @HostListener("window:resize", []) updateList() {
    this.isMobile = window.innerWidth < 576;

    if (!this.isMobile && this.sharedService.sidebarOpened) {
      this.sharedService.sidebarOpened = false;
    }
  }

  ngOnInit(): void {
    this.setEmail();
    this.setLoadedBoards();
    this.sharedService.boardId = this.selectedBoard.id;
    this.getCurrentNotifications();
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
        this.lists.sort((a, b) => a.position - b.position);
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
      next: (response) => {
        this.router.navigate(['/board/' + response.body]);
        this.setLoadedBoards();
        this.snackBar.open("Board created", "Close", {duration: 2000});
      }
    });
  }

  getCurrentNotifications() {
    this.notificationService.getCurrentNotifications().subscribe({
      next: (response: any) => {
        this.currentNotifications = response.body;
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
              if (this.selectedBoard != undefined) {
                this.sharedService.boardId = this.selectedBoard.id;
                this.getTaskListsByBoardId(this.selectedBoard.id);
                this.updateTaskListSubscriptions();
              }
            }
        );
  }

  openSidebar() {
    this.sharedService.sidebarOpened = true;
  }

  concentrateOnCreateBoard() {
    document.getElementById("add-board").focus();
  }

  updateTaskListSubscriptions() {
    if (this.subscriptions.length > 0) {
      this.subscriptions.forEach(sub => sub.unsubscribe());
    }

    this.makeSubscriptions();
  }

  countUnreadNotifications() {
    return this.currentNotifications.filter(notification => !notification.isRead).length;
  }

  drop(event: CdkDragDrop<TaskList[]>) {
    moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    const taskListBefore = event.container.data[event.currentIndex - 1];
    const taskListAfter = event.container.data[event.currentIndex + 1];
    this.taskListService.moveTaskList(taskListBefore ? taskListBefore.id : -1,
        taskListAfter ? taskListAfter.id : -1,
        event.container.data[event.currentIndex].id, this.selectedBoard.id);
  }

  makeSubscriptions() {
    const subscriptions = [
      {topic: '/topic/task-list-added/', handler: this.handleTaskListAdd.bind(this)},
      {topic: '/topic/task-added/', handler: this.handleTaskAdd.bind(this)},
      {topic: '/topic/task-updated/', handler: this.handleTaskUpdate.bind(this)},
      {topic: '/topic/task-deleted/', handler: this.handleTaskDelete.bind(this)},
      {topic: '/topic/task-moved/', handler: this.handleTaskMove.bind(this)},
      {topic: '/topic/task-list-renamed/', handler: this.handleTaskListRename.bind(this)},
      {topic: '/topic/task-list-deleted/', handler: this.handleTaskListDelete.bind(this)},
      {topic: '/topic/task-list-moved/', handler: this.handleTaskListMove.bind(this)},
      {topic: '/topic/task-list-updated-sort/', handler: this.handleTaskListUpdateSort.bind(this)}
    ];

    subscriptions.forEach(sub => {
      const subscription = this.rxStompService.watch(sub.topic + this.selectedBoard.id).subscribe((receivedMessage: Message) => {
        sub.handler(receivedMessage);
      });
      this.subscriptions.push(subscription);
    });
  }

  handleTaskListAdd(receivedMessage: Message) {
    const message = JSON.parse(receivedMessage.body);
    if (message.boardId == this.selectedBoard.id) {
      message.tasks = [];
      this.lists.push(message);
    }
  }

  handleTaskAdd(receivedMessage: Message) {
    const message = JSON.parse(receivedMessage.body);
    this.lists.find((list: TaskList) => list.id == message.taskListId).tasks.push(message);
  }

  handleTaskUpdate(receivedMessage: Message) {
    const message = JSON.parse(receivedMessage.body);
    const taskList = this.lists.find((list: TaskList) => list.id == message.taskListId);
    const taskIndex = taskList.tasks.findIndex((task: any) => task.id == message.id);
    if (taskIndex !== -1) {
      taskList.tasks[taskIndex] = message;
    }
  }

  handleTaskDelete(receivedMessage: Message) {
    const message = JSON.parse(receivedMessage.body);
    const taskList = this.lists.find((list: TaskList) => list.id == message.taskListId);
    taskList.tasks = taskList.tasks.filter((task: ShortTask) => task.id != message.id);
  }

  handleTaskMove(receivedMessage: Message) {
    const message = JSON.parse(receivedMessage.body);
    const taskList = this.lists.find((list: TaskList) => list.id == message.taskListId);
    const task = taskList.tasks.find((task: ShortTask) => task.id == message.id);

    if (task == undefined) {
      taskList.tasks.push(message);
      const prevTaskList = this.lists.find((list: TaskList) => list.id == message.previousTaskListId);
      prevTaskList.tasks = prevTaskList.tasks.filter((task: ShortTask) => task.id != message.id);
    } else {
      Object.assign(task, message);
    }

    taskList.tasks.sort((a, b) => a.position - b.position);
  }

  handleTaskListRename(receivedMessage: Message) {
    const message = JSON.parse(receivedMessage.body);
    this.lists.find((list: TaskList) => list.id == message.taskListId).title = message.title;
  }

  handleTaskListDelete(receivedMessage: Message) {
    this.lists = this.lists.filter((list: TaskList) => list.id != JSON.parse(receivedMessage.body));
  }

  handleTaskListMove(receivedMessage: Message) {
    const message = JSON.parse(receivedMessage.body);
    const taskList = this.lists.find((list: TaskList) => list.id == message.id);
    Object.assign(taskList, message);
    this.lists.sort((a, b) => a.position - b.position);
    taskList.tasks.sort((a, b) => a.position - b.position);
  }

  handleTaskListUpdateSort(receivedMessage: Message) {
    const message = JSON.parse(receivedMessage.body);
    const taskList = this.lists.find((list: TaskList) => list.id == message.id);
    Object.assign(taskList, message);
    this.taskListService.sortTaskList(taskList);
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }
}
