import {Component, OnInit} from '@angular/core';
import {BoardService} from "../../service/board.service";
import {SidebarAddMemberComponent} from "./sidebar-add-member/sidebar-add-member.component";
import {MatDialog} from "@angular/material/dialog";
import {SharedService} from "../../service/shared.service";

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
  boards = [];

  constructor(private boardService: BoardService, public dialog: MatDialog, public sharedService: SharedService) { }

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

  closeSidebar() {
    this.sharedService.sidebarOpened = false;
  }

  openAddBoardModal() {
    this.dialog.open(SidebarAddMemberComponent);
  }
}
