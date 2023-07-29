import {Component, OnInit} from '@angular/core';
import {BoardService} from "../../service/board.service";

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
  private boards = [];

  constructor(private boardService: BoardService) { }

  ngOnInit() {

  }

  getBoards() {
    this.boardService.getAllBoards().subscribe(boards => {
    });
  }
}
