import {Component, OnInit} from '@angular/core';
import {CustomerService} from "../service/customer.service";
import {BoardService} from "../service/board.service";
import {Priority} from "./task-list/task/Priority";
import {TaskList} from "./task-list/TaskList";

@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.css']
})
export class BoardComponent implements OnInit {
  email: string = "";
  newBoardTitle: string = "";
  boards: any[] = [
    {id: 0, title: 'Not found'},
  ];
  lists: TaskList[] = [
    {
      id: 0,
      title: 'Not found 1',
      task: [
        {title: 'Not found 1', done: false, id: 0, priority: Priority.LOW},
        {title: 'Not found', done: false, id: 0, priority: Priority.HIGH}
      ]
    },

    {
      id: 0,
      title: 'Not found 2',
      task: [
        {title: 'Not found 1', done: false, id: 0, priority: Priority.MEDIUM},
        {title: 'Not found', done: true, id: 0, priority: Priority.LOW}
      ]
    }
  ];


  constructor(private customerService: CustomerService, private boardService: BoardService) {
  }

  ngOnInit(): void {
    this.setEmail();
    this.getBoards();
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
}
