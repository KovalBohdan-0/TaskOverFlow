import {Component, OnInit} from '@angular/core';
import {BoardService} from "../../service/board.service";
import {SidebarAddMemberComponent} from "./sidebar-add-member/sidebar-add-member.component";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
  boards = [];

  constructor(private boardService: BoardService, public dialog: MatDialog) { }

  ngOnInit() {
    this.getBoards();
  }

  getBoards() {
    this.boardService.getAllBoards().subscribe({
      next: (response: any) => {
        this.boards = response.body;
      }
    });
  }

  openAddBoardModal() {
    this.dialog.open(SidebarAddMemberComponent);
  }
}
